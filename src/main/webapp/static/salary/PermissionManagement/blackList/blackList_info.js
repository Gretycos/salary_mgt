/**
 * 初始化薪资权限管理---黑名单维护详情对话框
 */
var BlackListInfoDlg = {
    blackListInfoData : {},
    selectDepartmentAndPermission:{
        departmentName:"",
        permissionName:"",
        staffId:null,
        departmentId:null,
        permissionId:null
    }
};

layui.use(['layer', 'Hussar', 'HussarAjax', 'laydate','form'], function(){
	var layer = layui.layer
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,laydate = layui.laydate
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

//监听select
form.on('select', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    BlackListInfoDlg.selectDepartmentAndPermission[id] = val;
    console.log("当前selectDepartmentAndPermission")
    console.log(BlackListInfoDlg.selectDepartmentAndPermission);
});

/**
 * 初始化部门下拉选择框和权限下拉选择框
 */
BlackListInfoDlg.createSelect= function(){
    var ajax = new $ax(Hussar.ctxPath + "/blackList/getAllDepartmentAndPermission", function(data){
        // 得到data中的权限数组和部门信息数组
        var departmentList = data.departmentList,
            permissionList = data.permissionList;

        // 下面开始拼接option
        var str1="",str2="";
        str1 += "<option value=''>请搜索或选择部门</option>";
        str2 += "<option value=''>请搜索或选择权限</option>";
        for (var key in departmentList){
            str1 +=  "<option value=\""+departmentList[key].departmentName+"\">"
                +departmentList[key].departmentName+"</option>";
        }

        for (var key in permissionList){
            str2 +=  "<option value=\""+permissionList[key].permissionName+"\">"
                +permissionList[key].permissionName+"</option>";
        }

        // 通过JQuery给相应的select添加上option
        $('#departmentName').empty().append(str1);
        $('#permissionName').empty().append(str2);

        form.render('select') //不要忘记render
    },function(data){
        Hussar.error("生成下拉选择框失败!" );
    });
    ajax.start();
};

/**
 * 清除数据
 */
BlackListInfoDlg.clearData = function() {
    this.blackListInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlackListInfoDlg.set = function(key, val) {
    this.blackListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlackListInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
BlackListInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.BlackList.layerIndex);
};

/**
 * 收集数据
 */
BlackListInfoDlg.collectData = function() {
    this
    .set('staffId')
    .set('departmentId')
    .set('permissionId');
};

/**
 * 提交添加
 */
BlackListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/blackList/add", function(data){
        window.parent.layui.Hussar.success("添加成功!");
        window.parent.$('#BlackListTable').bootstrapTable('refresh');
        BlackListInfoDlg.close();
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blackListInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
BlackListInfoDlg.editSubmit = function() {

    // 得到修改后的信息 部门和权限的名称保存在了selectDepartmentAndPermission中
    // 需要再获取到员工的ID（旧信息）
    var staffId = $('#staffId').val();
    var departmentId = $('#departmentId').val();
    var permissionId = $('#permissionId').val();
    BlackListInfoDlg.selectDepartmentAndPermission['staffId'] = staffId;
    BlackListInfoDlg.selectDepartmentAndPermission['departmentId'] = departmentId;
    BlackListInfoDlg.selectDepartmentAndPermission['permissionId'] = permissionId;

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/blackList/update", function(data){
        window.parent.layui.Hussar.success("修改成功!");
        window.parent.$('#BlackListTable').bootstrapTable('refresh');
        BlackListInfoDlg.close();
    },function(data){
        Hussar.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.selectDepartmentAndPermission);
    ajax.start();
};


/**
 * 初始化时间控件
 */
BlackListInfoDlg.initLaydate = function() {
    var dateDom = $(".dateType");
    $.each($(".dateType"), function (i,dom) {
        laydate.render({
            elem: dom,
            type : 'datetime',
            trigger: 'click'
        });
    });
}

$(function() {
    BlackListInfoDlg.createSelect();
    BlackListInfoDlg.initLaydate();   //初始化时间控件
});

});