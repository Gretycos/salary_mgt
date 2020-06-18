/**
 * 薪资权限管理--白名单维护管理初始化
 */
var WhiteList = {
    id: "WhiteListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1
};
layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax
        ,form = layui.form;


    WhiteList.createSelect = function(){
        var ajax = new $ax(Hussar.ctxPath + "/whiteList/select", function (data) {
            console.log(data);
            //得到员工工号列表、姓名列表、权限部门名称列表、权限名称列表
            var staffIdList = data.staffIdList,
                staffNameList = data.staffNameList,
                departmentNameList = data.departmentNameList,
                permissionNameList = data.permissionNameList

            var str1="",str2="",str3="",str4="";
            str1 += "<option value=''>请搜索或选择员工工号</option>";
            str2 += "<option value=''>请搜索或选择员工姓名</option>";
            str3 += "<option value=''>请搜索或选择部门名称</option>";
            str4 += "<option value=''>请搜索或选择权限名称</option>";
            //工号select
            for (var key in staffIdList)
                //key是list的下标
                str1 += "<option value=\""+staffIdList[key]+"\">"+staffIdList[key]+"</option>";

            //姓名select
            for (var key in staffNameList)
                //key是list的下标
                str2 += "<option value=\""+staffNameList[key]+"\">"+staffNameList[key]+"</option>";

            // 部门select
            for (var key in departmentNameList)
                //key是list的下标
                str3 += "<option value=\""+departmentNameList[key]+"\">"+departmentNameList[key]+"</option>";

            // 权限select
            for (var key in permissionNameList)
                //key是list的下标
                str4 += "<option value=\""+permissionNameList[key]+"\">"+permissionNameList[key]+"</option>";

            $('#staffId').append(str1);
            $('#staffName').append(str2);
            $('#departmentName').append(str3);
            $('#permissionName').append(str4);
            form.render('select')

        }, function (data) {
            Hussar.error("获取下拉列表失败!");
        });
        ajax.start();
    };

/**
 * 初始化表格的列
 */
WhiteList.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (WhiteList.pageNumber-1)*WhiteList.pageSize +1 +index ;}},
            {title: '员工工号', field: 'staffId', align: 'center',halign:'center'},
            {title: '员工姓名', field: 'staffName', align: 'center',halign:'center'},
            {title: '管理的部门', field: 'departmentName', align: 'center',halign:'center'},
            {title: '拥有的权限', field: 'permissionName', align: 'center',halign:'center'}
    ];
};

/**
 * 检查是否选中
 */
WhiteList.check = function () {
    var selected = $('#WhiteListTable').bootstrapTable('getSelections');
    if(selected.length == 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WhiteList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加薪资权限管理--白名单维护
 */
WhiteList.openAddWhiteList = function () {
    var index = layer.open({
        type: 2,
        title: '添加薪资权限管理--白名单维护',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/whiteList/whiteList_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改薪资权限管理--白名单维护
 */
WhiteList.openWhiteListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '薪资权限管理--白名单维护详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/whiteList/whiteList_update/' +  WhiteList.seItem.staffId
          });
        this.layerIndex = index;
    }
};

/**
 * 删除薪资权限管理--白名单维护
 */
WhiteList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Hussar.ctxPath + "/whiteList/delete", function (data) {
            Hussar.success("删除成功!");
            $('#WhiteListTable').bootstrapTable('refresh');
        }, function (data) {
            Hussar.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("whiteListId", WhiteList.seItem.staffId  );
        ajax.start();
    }
};

/**
 * 查询薪资权限管理--白名单维护列表
 */
WhiteList.search = function () {
    $('#WhiteListTable').bootstrapTable('refresh');
};

$(function () {
    var defaultColunms = WhiteList.initColumn();
    WhiteList.createSelect();

    $('#WhiteListTable').bootstrapTable({
            dataType:"json",
            url:'/whiteList/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){WhiteList.pageNumber = number ; WhiteList.pageSize = size}
        });
})

});