/**
 * @Description: 数字转换为人民币Demo脚本文件
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
     * 数字转换为人民币Demo的单例
     */
    var changeNumToWordDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    changeNumToWordDemo.initButtonEvnts = function () {
        $("#changeBtn").click(function(){
            changeNumToWordDemo.change();
        });
    }

    /**
     *  转换
     */
    changeNumToWordDemo.change = function () {
        var amountNum = $('#amountNum').val();
        if("" != amountNum && null != amountNum){
            var integer = amountNum.split(".")[0];
            if(integer.length > 16){
                Hussar.info("数字超出最大范围！");
            }else{
                var ajax = new $ax(Hussar.ctxPath + "/changeNumToWordDemo/change",
                    function(result) {
                        if(result.length==0){
                            $("#amountWord").val("无");
                        }else{
                            $("#amountWord").val(result);
                        }
                    },
                    function(result) {
                        Hussar.error("转换失败!");
                    });
                ajax.set("amountNum",amountNum);
                ajax.start();
            }
        }else{
            Hussar.info("数字不能为空！");
        }
    }

    $(function () {
        changeNumToWordDemo.initButtonEvnts();  //初始化按钮事件
    });
});