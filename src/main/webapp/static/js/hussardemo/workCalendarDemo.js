/**
 * Created by LiangDong on 2018/5/29.
 */

/**
 * @Description: 定义工作日历Demo脚本文件
 * @Author: liangdong
 * @Date: 2018/5/28.
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
     * 工作日历Demo的单例
     */
    var workCalendarDemo = {
        layerIndex: -1
    };

    /**
     *  初始化时间控件
     */
    workCalendarDemo.initLaydate = function () {
        laydate.render({
            elem: '#day',
            type : 'date',
        });
        laydate.render({
            elem: '#month1',
            type : 'month',
        });
        laydate.render({
            elem: '#month2',
            type : 'month',
            trigger:'click'
        });
    }

    /**
     *  初始化下拉框option
     */
    workCalendarDemo.initSelectOption = function () {
        var ajax = new $ax(Hussar.ctxPath + "/workCalendarDemo/options",
            function(data) {
                $("select[name='whether']").html("<option value=''>请选择</option>");
                for (var i = 0; i < data.length; i++) {
                    $("select[name='whether']").append(
                        "<option value='" + data[i].VALUE + "'>"
                        + data[i].LABEL + "</option>");
                }
            }, function(data) {
                Hussar.error("获取是否下拉框options失败!");
            });
        ajax.start();
        form.render();
    }

    /**
     *  初始化按钮事件
     */
    workCalendarDemo.initButtonEvnts = function () {
        $("#btn1").click(function(){
            form.on('submit(isWorkDay)', function(data){
                workCalendarDemo.isWorkDay();
            });
        });
        $("#btn2").click(function(){
            form.on('submit(workNum)', function(data){
                workCalendarDemo.getWorkDayNum();
            });
        });
        $("#btn3").click(function(){
            form.on('submit(holidayNum)', function(data){
                workCalendarDemo.getHolidayNum();
            });
        });
    }

    /**
     *  判断某一天是否是工作日
     */
    workCalendarDemo.isWorkDay = function () {
        var date = $('#day').val();
        var ajax = new $ax(Hussar.ctxPath + "/workCalendarDemo/isWorkDay",
            function(data) {
                if (data) {
                    //如果是工作日
                    $('#whether').val("1");
                } else {
                    //如果是非工作日
                    $('#whether').val("0");
                }
                form.render();
            },
            function(data) {
                Hussar.error("判断失败！");
            });
        ajax.set("date", date);
        ajax.start();

    }

    /**
     *  获取某月工作日的天数
     */
    workCalendarDemo.getWorkDayNum = function () {
        var month = $('#month1').val();
        var ajax = new $ax(Hussar.ctxPath + "/workCalendarDemo/workDayNum",
            function(data) {
                $('#workNum').val(data + "天");
            },
            function(data) {
                Hussar.error("获取当月工作日天数失败！");
            });
        ajax.set("month", month);
        ajax.start();
    }

    /**
     *  获取某月休息日的天数
     */
    workCalendarDemo.getHolidayNum = function () {
        var month = $('#month2').val();
        var ajax = new $ax(Hussar.ctxPath + "/workCalendarDemo/holidayNum",
            function(data) {
                $('#holidayNum').val(data + "天");
            },
            function(data) {
                Hussar.error("获取当月休息日天数失败！");
            });
        ajax.set("month", month);
        ajax.start();
    }

    $(function () {
        workCalendarDemo.initButtonEvnts();  //初始化按钮事件
        workCalendarDemo.initLaydate();     //初始化时间控件
        workCalendarDemo.initSelectOption();    //初始化下拉框option
    });

});





