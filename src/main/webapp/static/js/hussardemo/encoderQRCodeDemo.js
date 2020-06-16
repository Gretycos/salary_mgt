/**
 * @Description: 二维码生成Demo脚本文件
 * @Author: xinxuelei
 * @Date: 2018/10/18
 */
layui.use(['jquery','layer','Hussar','HussarAjax','form','element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var element = layui.element;

    /**
     * 二维码生成Demo的单例
     */
    var encoderQRCodeDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    encoderQRCodeDemo.initButtonEvnts = function () {
        $("#createAndSaveBtn").click(function(){
            encoderQRCodeDemo.createAndSave();
        });
        $("#createAndDisplayBtn").click(function(){
            encoderQRCodeDemo.createAndDisplay();
        });
    }

    /**
     *  生成二维码并保存到指定路径
     */
    encoderQRCodeDemo.createAndSave = function () {
        var content = $('#content').val();
        if("" != content && null != content){
            var ajax = new $ax(Hussar.ctxPath + "/encoderQRCodeDemo/createAndSave",
                function(result) {
                    if(result.length==0){
                        $("#imgPath").val("无");
                    }else{
                        $("#imgPath").val(result);
                    }
                },
                function(result) {
                    Hussar.error("生成失败!");
                });
            ajax.set("content", content);
            ajax.set("path", "D:/用户目录/我的图片");
            ajax.start();
        }else{
            Hussar.info("字符串不能为空！");
        }
    }
    /**
     *  生成
     */
    encoderQRCodeDemo.createAndDisplay = function () {
        var content = $('#contentTwo').val();
        var src = Hussar.ctxPath +'/encoderQRCodeDemo/createAndDisplay?content=' + content;
        if("" != content && null != content){
            document.getElementById("demoImg").src = src;
        }else{
            Hussar.info("字符串不能为空！");
        }
    }

    $(function () {
        encoderQRCodeDemo.initButtonEvnts();  //初始化按钮事件
    });
});