/**
 * 初始化薪资权限管理---黑名单维护详情对话框
 */
var BlackListInfoDlg = {
    blackListInfoData : {}
};

layui.use(['layer', 'Hussar', 'HussarAjax', 'laydate'], function(){
	var layer = layui.layer
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,laydate = layui.laydate
	    ,$ax = layui.HussarAjax;

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

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/blackList/update", function(data){
        window.parent.layui.Hussar.success("修改成功!");
        window.parent.$('#BlackListTable').bootstrapTable('refresh');
        BlackListInfoDlg.close();
    },function(data){
        Hussar.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blackListInfoData);
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
    BlackListInfoDlg.initLaydate();   //初始化时间控件
});

});