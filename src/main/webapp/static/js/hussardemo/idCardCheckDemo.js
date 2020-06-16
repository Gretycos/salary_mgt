/**
 * @Description: 身份证号验证Demo脚本文件
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
     * 身份证号验证Demo的单例
     */
    var idCardCheckDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    idCardCheckDemo.initButtonEvnts = function () {
        $("#checkBtn").click(function(){
            idCardCheckDemo.check();
        });
    }

    /**
     *  校验
     */
    idCardCheckDemo.check = function () {
        var idCardNum = $('#idCardNum').val();
        if("" != idCardNum && null != idCardNum){
            var ajax = new $ax(Hussar.ctxPath + "/idCardCheckDemo/check",
                function(result) {
                    if(result.length==0){
                        $("#result").val("无");
                    }else{
                        if(!result){
                            $("#result").val("错误");
                        }else{
                            $("#result").val("正确");
                        }
                    }
                },
                function(result) {
                    Hussar.error("校验失败!");
                });
            ajax.set("idCardNum",idCardNum);
            ajax.start();
        }else{
            Hussar.info("身份证号不能为空！");
        }
    }

    $(function () {
        idCardCheckDemo.initButtonEvnts();  //初始化按钮事件
    });
});