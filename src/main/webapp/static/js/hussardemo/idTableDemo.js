/**
 * @Description: 最大号表Demo脚本文件
 * @Author: chenxin
 * @Date: 2018/05/29.
 */
layui.use(['jquery','layer','Hussar','HussarAjax','form','element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var element = layui.element;

    /**
     * 字典管理Demo的单例
     */
    var idTableDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    idTableDemo.initButtonEvnts = function () {
        $("#search").click(function(){
        	form.on('submit(verify)', function(data){
        		idTableDemo.getNumber();
        	});
        });
        
        $("#getLevel").click(function(){
        	form.on('submit(getLevel)', function(data){
        		idTableDemo.getLevel();
        	});
        });
    }

    /**
     *  获取序号
     */
    idTableDemo.getNumber = function () {
        var idTable = $('#idTable').val();
        var operation = $('#operation').val();
        var ajax = new $ax(Hussar.ctxPath + "/idTableDemo/getNumber",
            function(data) {
        	if(data.length==0){
        		$("#idValue").val("无");
        	}else{
        		$("#idValue").val(data)
        	}
            },
            function(data) {
                Hussar.error("获取最大化表的序号失败!");
            });
        ajax.set("idTable",idTable);
        ajax.set("operation",operation);
        ajax.start();
    }

    /**
     *  获取层级码
     */
    idTableDemo.getLevel = function () {
        var idTable = $('#idTableTwo').val();
        var operation = $('#operationTwo').val();
        var parentNumber = $('#parentNumber').val();
        var ajax = new $ax(Hussar.ctxPath + "/idTableDemo/getLevel",
            function(data) {
        	if(data.length==0){
        		$("#idValueTwo").val("无");
        	}else{
        		$("#idValueTwo").val(data)
        	}
            },
            function(data) {
                Hussar.error("获取最大化表的序号失败!");
            });
        ajax.set("idTable",idTable);
        ajax.set("operation",operation);
        ajax.set("parentNumber",parentNumber);
        ajax.start();
    }



    $(function () {
        idTableDemo.initButtonEvnts();  //初始化按钮事件
    });

});





