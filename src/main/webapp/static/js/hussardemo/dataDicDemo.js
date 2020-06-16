/**
 * Created by LiangDong on 2018/5/28.
 */

/**
 * @Description: 定义字典管理Demo脚本文件
 * @Author: liangdong
 * @Date: 2018/5/28.
 */
layui.use(['jquery','layer','Hussar','jstree','HussarAjax','form', 'element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var element = layui.element;

    /**
     * 字典管理Demo的单例
     */
    var dataDicdemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    dataDicdemo.initButtonEvnts = function () {
        $("#btnList").click(function(){
            dataDicdemo.searchListData();
        });
        $("#btnMap").click(function(){
            dataDicdemo.searchMapData();
        });
        $("#btnCertain").click(function(){
            dataDicdemo.searchCertainData();
        });
        $("#btnGetLabel").click(function(){
            dataDicdemo.getLabel();
        });
        $("#btnGetValue").click(function(){
            dataDicdemo.getvalue();
        });

    }

    /**
     *  根据字典类型和单个选项值获取选项标签
     */
    dataDicdemo.getLabel = function () {
        var dicType = $('#dicType3').val();
        var value = $('#value').val();
        var ajax = new $ax(Hussar.ctxPath + "/dataDicDemo/getLabel",
            function(data) {
                if ( data != "") {
                    $('#label').val(data);
                } else {
                    Hussar.info("该字典类型下没有该选项！");
                }
            },
            function(data) {
                Hussar.error("根据字典类型和单个选项值获取选项标签失败!");
            });
        ajax.set("dicType",dicType);
        ajax.set("value",value);
        ajax.start();
    }

    /**
     *  根据字典类型和选项标签获取选项值
     */
    dataDicdemo.getvalue = function () {
        var dicType = $('#dicType4').val();
        var label = $('#label2').val();
        var ajax = new $ax(Hussar.ctxPath + "/dataDicDemo/getValue",
            function(data) {
                if ( data != "") {
                    $('#value2').val(data);
                } else {
                    Hussar.info("该字典类型下没有该选项！");
                }
            },
            function(data) {
                Hussar.error("根据字典类型和选项标签获取选项值失败!");
            });
        ajax.set("dicType",dicType);
        ajax.set("label",label);
        ajax.start();
    }

    /**
     *  根据字典类型和选项值数组获取指定的选项
     */
    dataDicdemo.searchCertainData = function () {
        var dicType = $('#dicType2').val();
        var optionValues = $('#optionValues').val();
        var ajax = new $ax(Hussar.ctxPath + "/dataDicDemo/certainData",
            function(data) {
                $('#listData').val(JSON.stringify(data));   //将数据展示到textarea中
                dataDicdemo.initListSelect(data);   //加载list数据下拉框
            },
            function(data) {
                Hussar.error("获取指定的字典选项失败!");
            });
        ajax.set("dicType",dicType);
        ajax.set("optionValues",optionValues);
        ajax.start();
    }

    /**
     *  根据字典类型获得List数据
     */
    dataDicdemo.searchListData = function () {
        var dicType = $('#dicType').val();
        var ajax = new $ax(Hussar.ctxPath + "/dataDicDemo/listData",
            function(data) {
                $('#listData').val(JSON.stringify(data));   //将数据展示到textarea中
                dataDicdemo.initListSelect(data);   //加载list数据下拉框
            },
            function(data) {
                Hussar.error("获取List数据失败!");
            });
        ajax.set("dicType",dicType);
        ajax.start();
    }

    /**
     *  根据字典类型获得Map数据
     */
    dataDicdemo.searchMapData = function () {
        var dicType = $('#dicType').val();
        var ajax = new $ax(Hussar.ctxPath + "/dataDicDemo/mapData",
            function(data) {
                $('#mapData').val(JSON.stringify(data));    //将数据展示到textarea中
            },
            function(data) {
                Hussar.error("获取Map数据失败!");
            });
        ajax.set("dicType",dicType);
        ajax.start();
    }

    /**
     * 加载list数据下拉框
     * @param data
     */
    dataDicdemo.initListSelect = function (data) {
        $("select[name='listSelect']").html("<option value=''>请选择</option>");
        for (var i = 0; i < data.length; i++) {
            $("select[name='listSelect']").append(
                "<option value='" + data[i].VALUE + "'>"
                + data[i].LABEL + "</option>");
        }
        form.render();
    }

    $(function () {
        dataDicdemo.initButtonEvnts();  //初始化按钮事件
    });

});





