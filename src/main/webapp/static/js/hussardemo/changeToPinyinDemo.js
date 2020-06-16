/**
 * @Description: 汉字转拼音Demo脚本文件
 * @Author: xinxuelei
 * @Date: 2018/10/17
 */
layui.use(['jquery','layer','Hussar','HussarAjax','form','element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var element = layui.element;

    /**
     * 汉字转拼音Demo的单例
     */
    var changeToPinyinDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    changeToPinyinDemo.initButtonEvnts = function () {
        $("#changeToPinyinUp").click(function(){
            changeToPinyinDemo.changeToPinyinUp();
        });
        $("#changeToPinyinLow").click(function(){
            changeToPinyinDemo.changeToPinyinLow();
        });
        $("#getFirstLettersUp").click(function(){
            changeToPinyinDemo.getFirstLettersUp();
        });
        $("#getFirstLettersLow").click(function(){
            changeToPinyinDemo.getFirstLettersLow();
        });
    }

    /**
     *  将汉字转为拼音，全部大写
     */
    changeToPinyinDemo.changeToPinyinUp = function () {
        var beforeChangeStr = $('#beforeChangeOne').val();
        if("" != beforeChangeStr && null != beforeChangeStr){
            var ajax = new $ax(Hussar.ctxPath + "/changeToPinyinDemo/changeToPinyinUp",
                function(data) {
                    if(data.length==0){
                        $("#afterChangeOne").val("无");
                    }else{
                        $("#afterChangeOne").val(data);
                    }
                },
                function(data) {
                    Hussar.error("汉字转拼音转换失败!");
                });
            ajax.set("beforeChangeOne",beforeChangeStr);
            ajax.start();
        }else{
            Hussar.info("待转换内容不能为空！");
        }
    }

    /**
     *  将汉字转为拼音，全部小写
     */
    changeToPinyinDemo.changeToPinyinLow = function () {
        var beforeChangeStr = $('#beforeChangeTwo').val();
        if("" != beforeChangeStr && null != beforeChangeStr) {
            var ajax = new $ax(Hussar.ctxPath + "/changeToPinyinDemo/changeToPinyinLow",
                function (data) {
                    if (data.length == 0) {
                        $("#afterChangeTwo").val("无");
                    } else {
                        $("#afterChangeTwo").val(data);
                    }
                },
                function (data) {
                    Hussar.error("汉字转拼音转换失败!");
                });
            ajax.set("beforeChangeTwo", beforeChangeStr);
            ajax.start();
        }else{
            Hussar.info("待转换内容不能为空！");
        }
    }

    /**
     *  获取每个汉字的首字母大写
     */
    changeToPinyinDemo.getFirstLettersUp = function () {
        var beforeChangeStr = $('#beforeChangeThree').val();
        if("" != beforeChangeStr && null != beforeChangeStr) {
            var ajax = new $ax(Hussar.ctxPath + "/changeToPinyinDemo/getFirstLettersUp",
                function (data) {
                    if (data.length == 0) {
                        $("#afterChangeThree").val("无");
                    } else {
                        $("#afterChangeThree").val(data);
                    }
                },
                function (data) {
                    Hussar.error("汉字转拼音转换失败!");
                });
            ajax.set("beforeChangeThree", beforeChangeStr);
            ajax.start();
        }else{
            Hussar.info("待转换内容不能为空！");
        }
    }

    /**
     *  获取每个汉字的首字母小写
     */
    changeToPinyinDemo.getFirstLettersLow = function () {
        var beforeChangeStr = $('#beforeChangeFour').val();
        if("" != beforeChangeStr && null != beforeChangeStr) {
            var ajax = new $ax(Hussar.ctxPath + "/changeToPinyinDemo/getFirstLettersLow",
                function (data) {
                    if (data.length == 0) {
                        $("#afterChangeFour").val("无");
                    } else {
                        $("#afterChangeFour").val(data);
                    }
                },
                function (data) {
                    Hussar.error("汉字转拼音转换失败!");
                });
            ajax.set("beforeChangeFour", beforeChangeStr);
            ajax.start();
        }else{
            Hussar.info("待转换内容不能为空！");
        }
    }

    $(function () {
        changeToPinyinDemo.initButtonEvnts();  //初始化按钮事件
    });
});