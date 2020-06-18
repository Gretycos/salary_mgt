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
 * 收集数据
 */
StaffInfoDlg.collectData = function() {
    this
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

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/staff/add", function(data){
        window.parent.layui.Hussar.success("添加成功!");
        window.parent.$('#StaffTable').bootstrapTable('refresh');
        StaffInfoDlg.close();
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
StaffInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/staff/update", function(data){
        window.parent.layui.Hussar.success("修改成功!");
        window.parent.$('#StaffTable').bootstrapTable('refresh');
        StaffInfoDlg.close();
    },function(data){
        Hussar.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.staffInfoData);
    ajax.start();
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

StaffInfoDlg.initSelector = function(){
    var ajax1 = new $ax(Hussar.ctxPath + "/department/list", function(data){
        console.log(data);
        $.each(data.rows,function (index,item) {
            console.log(item);
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#departmentId").append(new Option(item.departmentName,item.departmentId));
            $("#departmentId").val(10);
            // form.render('select');
        });
    },function(data){
        Hussar.error("查询失败!" + data.responseJSON.message + "!");
    });
    ajax1.start();

    var ajax2 = new $ax(Hussar.ctxPath + "/position/list", function(data){
        console.log(data);
        $.each(data.rows,function (index,item) {
            console.log(item);
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#positionId").append(new Option(item.positionName,item.positionId));
            $("#positionId").val(0);
            form.render('select');
        });
    },function(data){
        Hussar.error("查询失败!" + data.responseJSON.message + "!");
    });
    ajax2.start();

}

$(function() { // document的ready状态
    StaffInfoDlg.initLaydate();   //初始化时间控件
    StaffInfoDlg.initSelector(); //初始化部门下拉框、职位下拉框
});



});