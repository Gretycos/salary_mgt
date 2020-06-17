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
            {title: '操作ID', field: 'operationId', align: 'center',halign:'center'},
            {title: '操作员ID', field: 'operatorId', align: 'center',halign:'center'},
            {title: '被操做人员ID', field: 'moveId', align: 'center',halign:'center'},
            {title: '原部门ID', field: 'oldDepartmentId', align: 'center',halign:'center'},
            {title: '新部门ID', field: 'newDepartmentId', align: 'center',halign:'center'},
            {title: '原职务ID', field: 'oldPositionId', align: 'center',halign:'center'},
            {title: '新职务ID', field: 'newPositionId', align: 'center',halign:'center'},
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
        title: '添加调动日志',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/MoveLog/moveLog_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改调动日志
 */
MoveLog.openMoveLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '调动日志详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/moveLog/moveLog_update/' +  MoveLog.seItem.operationId
               });
        this.layerIndex = index;
    }
};

/**
 * 删除调动日志
 */
MoveLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Hussar.ctxPath + "/moveLog/delete", function (data) {
            Hussar.success("删除成功!");
            $('#MoveLogTable').bootstrapTable('refresh');
        }, function (data) {
            Hussar.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("moveLogId", MoveLog.seItem.operationId       );
        ajax.start();
    }
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
            url:'/moveLog/list',
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