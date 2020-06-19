/**
 * 离职日志管理初始化
 */
var DepartureLog = {
    id: "DepartureLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1
};
layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax;

/**
 * 初始化表格的列
 */
DepartureLog.initColumn = function () {
    return [
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (DepartureLog.pageNumber-1)*DepartureLog.pageSize +1 +index ;}},
            {title: '操作编码', field: 'operationId', align: 'center',halign:'center'},
        {title: '操作员工号', field: 'operator', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.staffId
            }},
        {title: '操作员工姓名', field: 'operator', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.staffName
            }},
        {title: '离职人员工号', field: 'departure', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.staffId
            }},
        {title: '离职人员姓名', field: 'departure', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.staffName
            }},
            {title: '操作时间', field: 'operationTime', align: 'center',halign:'center'}
    ];
};

/**
 * 查询离职日志列表
 */
DepartureLog.search = function () {
    $('#DepartureLogTable').bootstrapTable('refresh');
};

$(function () {
    var defaultColunms = DepartureLog.initColumn();

    $('#DepartureLogTable').bootstrapTable({
            dataType:"json",
            url:'/departure/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){DepartureLog.pageNumber = number ; DepartureLog.pageSize = size}
        });
})

});