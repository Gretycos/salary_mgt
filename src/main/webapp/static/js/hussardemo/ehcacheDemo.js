/**
 * Created by LiangDong on 2018/5/29.
 */

/**
 * @Description: 定义Ehcache缓存管理Demo脚本文件
 * @Author: liangdong
 * @Date: 2018/5/29.
 */
layui.use(['jquery','layer','Hussar','jstree','HussarAjax','form', 'laydate', 'element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;

    /**
     * Ehcache缓存管理Demo的单例
     */
    var EhcacheDemo = {
        layerIndex: -1
    };


    /**
     *  初始化下拉框option
     */
    EhcacheDemo.initSelectOption = function () {
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/cacheNames",
            function(data) {
                $("select[name='cacheName']").html("<option value=''>缓存名称</option>");
                for (var i = 0; i < data.length; i++) {
                    $("select[name='cacheName']").append(
                        "<option value='" + data[i]+ "'>"
                        + data[i] + "</option>");
                }
            }, function(data) {
                Hussar.error("获取缓存名称下拉框option失败！");
            });
        ajax.start();
        form.render();
    }

    /**
     *  初始化按钮事件
     */
    EhcacheDemo.initButtonEvnts = function () {
        $("#btnSave").click(function(){
            form.on('submit(save)', function(data){
                EhcacheDemo.saveCache();
            });
        });
        $("#btnDeleteOne").click(function(){
            form.on('submit(deleteOne)', function(data){
                EhcacheDemo.deleteOneCache();
            });
        });
        $("#btnDeleteMore").click(function(){
            form.on('submit(deleteMore)', function(data){
                EhcacheDemo.deleteMoreCache();
            });
        });
    }

    /**
     *  缓存数据
     */
    EhcacheDemo.saveCache = function () {
        var cacheName = $('#cacheName1').val();
        var key = $('#key1').val();
        var value = $('#value').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/saveCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("缓存数据失败！");
            });
        ajax.set("cacheName", cacheName);
        ajax.set("key", key);
        ajax.set("value", value);
        ajax.start();
    }

    /**
     *  根据确定的缓存名称和key删除一个缓存数据
     */
    EhcacheDemo.deleteOneCache = function () {
        var cacheName = $('#cacheName2').val();
        var key = $('#key2').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/deleteOneCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("删除单个缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("删除单个缓存数据失败！");
            });
        ajax.set("cacheName", cacheName);
        ajax.set("key", key);
        ajax.start();
    }

    /**
     *  根据确定的缓存名称和模糊key查询删除多个缓存数据
     */
    EhcacheDemo.deleteMoreCache = function() {
        var cacheName = $('#cacheName3').val();
        var key = $('#key3').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/deleteMoreCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("删除多个缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("删除多个缓存数据失败！");
            });
        ajax.set("cacheName", cacheName);
        ajax.set("key", key);
        ajax.start();

    }

    $(function () {
        EhcacheDemo.initButtonEvnts();  //初始化按钮事件
        EhcacheDemo.initSelectOption();    //初始化下拉框option
    });

});





