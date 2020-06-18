/**
 * 初始化调动日志详情对话框
 */
var MoveLogInfoDlg = {
    moveLogInfoData : {}
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
MoveLogInfoDlg.clearData = function() {
    this.moveLogInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MoveLogInfoDlg.set = function(key, val) {
    this.moveLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MoveLogInfoDlg.get = function(key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
MoveLogInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.MoveLog.layerIndex);
};

/**
 * 收集数据
 */
MoveLogInfoDlg.collectData = function() {
    this
    .set('operationId')
    .set('operatorId')
    .set('moveId')
    .set('oldDepartmentId')
    .set('newDepartmentId')
    .set('oldPositionId')
    .set('newPositionId')
    .set('operationTime');
};

/**
 * 提交添加
 */
MoveLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Hussar.ctxPath + "/move/add", function(data){
        window.parent.layui.Hussar.success("添加成功!");
        window.parent.$('#MoveLogTable').bootstrapTable('refresh');
        MoveLogInfoDlg.close();
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.moveLogInfoData);
    ajax.start();
};


/**
 * 初始化时间控件
 */
MoveLogInfoDlg.initLaydate = function() {
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
    MoveLogInfoDlg.initLaydate();   //初始化时间控件
});

});