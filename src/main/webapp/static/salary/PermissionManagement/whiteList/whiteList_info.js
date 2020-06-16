/**
 * 初始化薪资权限管理--白名单维护详情对话框
 */
var WhiteListInfoDlg = {
    whiteListInfoData : {}
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
WhiteListInfoDlg.clearData = function() {
    this.whiteListInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WhiteListInfoDlg.set = function(key, val) {
    this.whiteListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WhiteListInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
WhiteListInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.WhiteList.layerIndex);
};

/**
 * 收集数据
 */
WhiteListInfoDlg.collectData = function() {
    this
    .set('staffId')
    .set('departmentId')
    .set('permissionId');
};

/**
 * 提交添加
 */
WhiteListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/whiteList/add", function(data){
        window.parent.layui.Hussar.success("添加成功!");
        window.parent.$('#WhiteListTable').bootstrapTable('refresh');
        WhiteListInfoDlg.close();
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.whiteListInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
WhiteListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/whiteList/update", function(data){
        window.parent.layui.Hussar.success("修改成功!");
        window.parent.$('#WhiteListTable').bootstrapTable('refresh');
        WhiteListInfoDlg.close();
    },function(data){
        Hussar.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.whiteListInfoData);
    ajax.start();
};

/**
 * 初始化时间控件
 */
WhiteListInfoDlg.initLaydate = function() {
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
    WhiteListInfoDlg.initLaydate();   //初始化时间控件
});

});