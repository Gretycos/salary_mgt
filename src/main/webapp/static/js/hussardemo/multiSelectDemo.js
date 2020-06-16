/**
 * @Description: 多选下拉框
 * @Author: zhaopengcheng
 * @Date: 2018/11/30
 */

layui.use(['multiSelect','element','form','Hussar','jquery'],function() {
    var $ = layui.jquery
        ,Hussar = layui.Hussar;

    // 取值
    $("#btn").click(function() {
        var msg = $("#select1").val();
        Hussar.info(msg.toString());
    });

    // 取值
    $("#btn1").click(function() {
        var msg1 = $("#select2").val();
        Hussar.info(msg1.toString());
    })
});