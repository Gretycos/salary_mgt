/**
 * Created by LiangDong on 2018/5/29.
 */

/**
 * @Description: 定义Redis缓存管理Demo脚本文件
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
     * Redis缓存管理Demo的单例
     */
    var RedisCacheDemo = {
        layerIndex: -1
    };

    /**
     *  初始化按钮事件
     */
    RedisCacheDemo.initButtonEvnts = function () {
        $("#btnSave").click(function(){
            form.on('submit(save)', function(data){
                RedisCacheDemo.saveCache();
            });
        });
        $("#btnDeleteOne").click(function(){
            form.on('submit(deleteOne)', function(data){
                RedisCacheDemo.deleteOneCache();
            });
        });
        $("#btnDeleteMore").click(function(){
            form.on('submit(deleteMore)', function(data){
                RedisCacheDemo.deleteMoreCache();
            });
        });
    }

    /**
     *  缓存数据
     */
    RedisCacheDemo.saveCache = function () {
        var key = $('#key1').val();
        var value = $('#value').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/saveRedisCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("缓存数据失败！");
            });
        ajax.set("key", key);
        ajax.set("value", value);
        ajax.start();
    }

    /**
     *  根据确定的缓存名称和key删除一个缓存数据
     */
    RedisCacheDemo.deleteOneCache = function () {
        var key = $('#key2').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/deleteOneRedisCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("删除单个缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("删除单个缓存数据失败！");
            });
        ajax.set("key", key);
        ajax.start();
    }

    /**
     *  根据确定的缓存名称和模糊key查询删除多个缓存数据
     */
    RedisCacheDemo.deleteMoreCache = function() {
        var key = $('#key3').val();
        var ajax = new $ax(Hussar.ctxPath + "/cacheDemo/deleteMoreRedisCache",
            function(data) {
                if (data.code == '200') {
                    Hussar.success("删除多个缓存数据成功！");
                }
            },
            function(data) {
                Hussar.error("删除多个缓存数据失败！");
            });
        ajax.set("key", key);
        ajax.start();

    }

    $(function () {
        RedisCacheDemo.initButtonEvnts();  //初始化按钮事件
    });

});





