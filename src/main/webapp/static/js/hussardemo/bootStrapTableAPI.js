/**
 * @Description: BootStrap列表API
 * @Author: xinxuelei
 * @Date: 2019/01/04.
 */
var QueryList = {
    layerIndex: -1,
    seItem: null,	//选中的条目
    tableParamList: $("#tableParameter"),//表格参数列表
    columnParamList: $("#columnParameter"),//列参数列表
    tableFunctionList: $("#functionTable"),//表格方法列表
    tableEventList: $("#eventTable"),//表格事件列表
};
layui.use(['jquery','bootstrap_table_edit','layer','Hussar','element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;



    QueryList.queryParams = function(id) {
        var value = $('#searchContent').val();
        $("#" + id).bootstrapTable({
            searchText: value
        });
    }

    /**
     * 初始化列表参数表格
     */
    QueryList.initTableParamList = function () {
        QueryList.tableParamList.bootstrapTable({
            queryParamsType: QueryList.queryParams("tableParameter"), 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        });
    }

    /**
     * 初始化列参数表格
     */
    QueryList.initColumnParamList = function () {
        QueryList.columnParamList.bootstrapTable({
            queryParamsType: QueryList.queryParams("columnParameter"), 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        });
    }

    /**
     * 初始化方法表格
     */
    QueryList.initFunctionList = function () {
        QueryList.tableFunctionList.bootstrapTable({
            queryParamsType: QueryList.queryParams("functionTable"), 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        });
    }

    /**
     * 初始化事件表格
     */
    QueryList.initEventList = function () {
        QueryList.tableEventList.bootstrapTable({
            queryParamsType: QueryList.queryParams("eventTable"), 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        });
    }
    $(function () {
        QueryList.initTableParamList();	//初始化列表参数表格
        QueryList.initColumnParamList();//初始化列参数表格
        QueryList.initFunctionList();//初始化方法表格
        QueryList.initEventList();//初始化事件表格
    });

});