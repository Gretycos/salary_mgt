/**
 * 入职日志管理初始化
 */
var EntryLog = {
    id: "EntryLogTable",	//表格id
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
EntryLog.initColumn = function () {
    return [
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (EntryLog.pageNumber-1)*EntryLog.pageSize +1 +index ;}},
            {title: '操作编码', field: 'operationId', align: 'center',halign:'center'},
            {title: '操作员工号', field: 'operator', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffId
                }},
            {title: '操作员工姓名', field: 'operator', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffName
                }},
            {title: '入职人员工号', field: 'entrant', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffId
                }},
            {title: '入职人员姓名', field: 'entrant', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffName
                }},
            {title: '操作时间', field: 'operationTime', align: 'center',halign:'center'}
    ];
};


/**
 * 查询入职日志列表
 */
EntryLog.search = function () {
    $('#EntryLogTable').bootstrapTable('refresh');
};

$(function () {
    var defaultColunms = EntryLog.initColumn();

    $('#EntryLogTable').bootstrapTable({
            dataType:"json",
            url:'/entry/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){EntryLog.pageNumber = number ; EntryLog.pageSize = size}
        });
})

});