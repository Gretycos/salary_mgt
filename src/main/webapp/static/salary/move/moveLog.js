/**
 * 调动日志管理初始化
 */
var MoveLog = {
    id: "MoveLogTable",	//表格id
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
MoveLog.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (MoveLog.pageNumber-1)*MoveLog.pageSize +1 +index ;}},
            {title: '操作编码', field: 'operationId', align: 'center',halign:'center'},
            {title: '操作员工号', field: 'operatorId', align: 'center',halign:'center'},
            {title: '操作员姓名', field: 'operatorName', align: 'center',halign:'center'},
            {title: '调动员工工号', field: 'moveId', align: 'center',halign:'center'},
            {title: '调动员工姓名', field: 'moveName', align: 'center',halign:'center'},
            {title: '原部门', field: 'oldDepartmentName', align: 'center',halign:'center'},
            {title: '原职位', field: 'oldPositionName', align: 'center',halign:'center'},
            {title: '新部门', field: 'newDepartmentName', align: 'center',halign:'center'},
            {title: '新职位', field: 'newPositionName', align: 'center',halign:'center'},
            {title: '操作时间', field: 'operationTime', align: 'center',halign:'center'}
    ];
};

/**
 * 检查是否选中
 */
MoveLog.check = function () {
    var selected = $('#MoveLogTable').bootstrapTable('getSelections');
    if(selected.length == 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MoveLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加调动日志
 */
MoveLog.openAddMoveLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加调动记录',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/move/moveLog_add'
    });
    this.layerIndex = index;
};


/**
 * 查询调动日志列表
 */
MoveLog.search = function () {
    $('#MoveLogTable').bootstrapTable('refresh');
};

$(function () {
    var defaultColunms = MoveLog.initColumn();

    $('#MoveLogTable').bootstrapTable({
            dataType:"json",
            url:'/move/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){MoveLog.pageNumber = number ; MoveLog.pageSize = size}
        });
})

});