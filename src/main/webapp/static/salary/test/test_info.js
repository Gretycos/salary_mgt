/**
 * 初始化测试详情对话框
 */
var TestInfoDlg = {
    testInfoData : {}
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
TestInfoDlg.clearData = function() {
    this.testInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.set = function(key, val) {
    this.testInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
TestInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.Test.layerIndex);
};

/**
 * 收集数据
 */
TestInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('phone');
};

/**
 * 提交添加
 */
TestInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/test/add", function(data){
        window.parent.layui.Hussar.success("添加成功!");
        window.parent.$('#TestTable').bootstrapTable('refresh');
        TestInfoDlg.close();
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
TestInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/test/update", function(data){
        window.parent.layui.Hussar.success("修改成功!");
        window.parent.$('#TestTable').bootstrapTable('refresh');
        TestInfoDlg.close();
    },function(data){
        Hussar.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.testInfoData);
    ajax.start();
};

/**
 * 初始化时间控件
 */
TestInfoDlg.initLaydate = function() {
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
    TestInfoDlg.initLaydate();   //初始化时间控件
});

});