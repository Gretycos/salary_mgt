/**
 * 初始化人员管理详情对话框
 */
var StaffInfoDlg = {
    staffInfoData : {}
};

layui.use(['layer', 'Hussar', 'HussarAjax', 'laydate','form'], function(){
	var layer = layui.layer
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,laydate = layui.laydate
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

	//监听添加按钮
    form.on('submit(staffAddSubmit)',function (data) {
        StaffInfoDlg.addSubmit();
    });
    //监听编辑按钮
    form.on('submit(staffEditSubmit)',function (data) {
        StaffInfoDlg.editSubmit();
    })
    //表单验证
    form.verify({
        username: function (value,item) {
            if(value.length===0){
                return '姓名不能为空';
            }
            if(!/^[\u4E00-\u9FA5]{2,4}$/.test(value)){
                return '姓名只能是2-4位长度的中文'
            }
        }
    });
    form.render();

/**
 * 清除数据
 */
StaffInfoDlg.clearData = function() {
    this.staffInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffInfoDlg.set = function(key, val) {
    this.staffInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StaffInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
StaffInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.Staff.layerIndex);
};

/**
 * 收集数据---添加
 */
StaffInfoDlg.collectData = function() {
    this
    .set('staffName')
    .set('gender')
    .set('departmentId')
    .set('positionId')
};

/**
* 收集数据---修改
*/
StaffInfoDlg.collectData_ = function() {
    this
        .set('staffId')
        .set('staffName')
        .set('gender')
        .set('departmentId')
        .set('positionId')
};

/**
 * 提交添加
 */
StaffInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (this.staffInfoData.positionId===2 && this.staffInfoData.departmentId !== 99){
        Hussar.error("入职失败!总经理只能入职高管层");
    }else{
        //提交信息
        var ajax = new $ax(Hussar.ctxPath + "/staff/add", function(data){
            if(data.code === 200){
                window.parent.layui.Hussar.success("新员工入职成功!");
                window.parent.$('#StaffTable').bootstrapTable('refresh');
                StaffInfoDlg.close();
            }else{
                Hussar.error("新员工入职成功!" + data.message + "!");
            }
        },function(data){
            Hussar.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.staffInfoData);
        ajax.start();
    }
};

/**
 * 提交修改
 */
StaffInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData_();

    if (this.staffInfoData.positionId===2 && this.staffInfoData.departmentId !== 99){
        Hussar.error("变动失败!总经理只能存在于高管层");
    } else {
        //提交信息
        var ajax = new $ax(Hussar.ctxPath + "/staff/update", function(data){
            if(data.code === 200){
                window.parent.layui.Hussar.success("员工信息修改成功!");
                window.parent.$('#StaffTable').bootstrapTable('refresh');
                StaffInfoDlg.close();
            }else{
                Hussar.error("员工信息修改失败!" + data.message + "!");
            }

        },function(data){
            Hussar.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.staffInfoData);
        ajax.start();
    }
};

/**
 * 初始化时间控件
 */
StaffInfoDlg.initLaydate = function() {
    var dateDom = $(".dateType");
    $.each($(".dateType"), function (i,dom) {
        laydate.render({
            elem: dom,
            type : 'datetime',
            trigger: 'click'
        });
    });
}

/**
* 初始化下拉框
*/
StaffInfoDlg.initSelector = function(){
    var ajax1 = new $ax(Hussar.ctxPath + "/department/list", function(data){
        // console.log(data);
        $.each(data.rows,function (index,item) {
            // console.log(item);
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#departmentId").append(new Option(item.departmentName,item.departmentId));
            // form.render('select');
        });
        $("#departmentId").val(typeof departmentId_value== "undefined"? 10: departmentId_value);
    },function(data){
        Hussar.error("部门选项加载失败!" + data.responseJSON.message + "!");
    });
    ajax1.start();

    var ajax2 = new $ax(Hussar.ctxPath + "/position/list", function(data){
        // console.log(data);
        $.each(data.rows,function (index,item) {
            // console.log(item);
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#positionId").append(new Option(item.positionName,item.positionId));
        });
        $("#positionId").val(typeof positionId_value== "undefined"? 0: positionId_value);
        form.render('select');
    },function(data){
        Hussar.error("职位选项加载失败!" + data.responseJSON.message + "!");
    });
    ajax2.start();

}

$(function() { // document的ready状态
    StaffInfoDlg.initLaydate();   //初始化时间控件
    StaffInfoDlg.initSelector(); //初始化部门下拉框、职位下拉框
});

});