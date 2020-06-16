/**
 * 薪资权限管理---黑名单维护管理初始化
 */
var BlackList = {
    id: "BlackListTable",	//表格id
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
BlackList.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (BlackList.pageNumber-1)*BlackList.pageSize +1 +index ;}},
            {title: '员工ID', field: 'staffId', align: 'center',halign:'center'},
            {title: '部门ID', field: 'departmentId', align: 'center',halign:'center'},
            {title: '权限ID', field: 'permissionId', align: 'center',halign:'center'}
    ];
};

/**
 * 检查是否选中
 */
BlackList.check = function () {
    var selected = $('#BlackListTable').bootstrapTable('getSelections');
    if(selected.length == 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BlackList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加薪资权限管理---黑名单维护
 */
BlackList.openAddBlackList = function () {
    var index = layer.open({
        type: 2,
        title: '添加薪资权限管理---黑名单维护',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/blackList/blackList_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改薪资权限管理---黑名单维护
 */
BlackList.openBlackListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '薪资权限管理---黑名单维护详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/blackList/blackList_update/' +  BlackList.seItem.staffId
          });
        this.layerIndex = index;
    }
};

/**
 * 删除薪资权限管理---黑名单维护
 */
BlackList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Hussar.ctxPath + "/blackList/delete", function (data) {
            Hussar.success("删除成功!");
            $('#BlackListTable').bootstrapTable('refresh');
        }, function (data) {
            Hussar.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("blackListId", BlackList.seItem.staffId  );
        ajax.start();
    }
};

/**
 * 查询薪资权限管理---黑名单维护列表
 */
BlackList.search = function () {
    $('#BlackListTable').bootstrapTable('refresh');
};

$(function () {
    var defaultColunms = BlackList.initColumn();

    $('#BlackListTable').bootstrapTable({
            dataType:"json",
            url:'/blackList/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){BlackList.pageNumber = number ; BlackList.pageSize = size}
        });
})

});