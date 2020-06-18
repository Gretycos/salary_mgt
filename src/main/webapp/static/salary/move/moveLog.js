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

        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (MoveLog.pageNumber-1)*MoveLog.pageSize +1 +index ;}},
            {title: '操作编码', field: 'operationId', align: 'center',halign:'center'},
            {title: '操作员工号', field: 'operator', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffId
                }},
            {title: '操作员姓名', field: 'operator', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffName
                }},
            {title: '调动员工工号', field: 'move', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffId
                }},
            {title: '调动员工姓名', field: 'move', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.staffName
                }},
            {title: '原部门', field: 'oldDepartment', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.departmentName
                }},
            {title: '原职位', field: 'oldPosition', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.positionName
                }},
            {title: '新部门', field: 'newDepartment', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.departmentName
                }},
            {title: '新职位', field: 'newPosition', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.positionName
                }},
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

MoveLog.queryParams = function(){
    var temp = {
        pageSize: this.pageSize,
        pageNumber: this.pageNumber,
        name: $("#moveName").val()
    };
    return temp;
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
            queryParams: MoveLog.queryParams(),
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){MoveLog.pageNumber = number ; MoveLog.pageSize = size}
        });
})

});