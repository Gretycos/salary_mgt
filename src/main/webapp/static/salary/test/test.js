/**
 * 测试管理初始化
 */
var Test = {
    id: "TestTable",	//表格id
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
Test.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (Test.pageNumber-1)*Test.pageSize +1 +index ;}},
            {title: '主键', field: 'id', align: 'center',halign:'center'},
            {title: '姓名', field: 'name', align: 'center',halign:'center'},
            {title: '电话', field: 'phone', align: 'center',halign:'center'}
    ];
};

/**
 * 检查是否选中
 */
Test.check = function (flag) {
    var selected = $('#TestTable').bootstrapTable('getSelections');
    if(selected.length == 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else{
        if (flag == 2) { //1代表修改,2代表删除
            Test.seItem = selected;
        } else {
            if(selected.length > 1){
                Hussar.info("请选中表格中的某一记录！");
                return false;
            }else {
                Test.seItem = selected[0];
            }
        }
        return true;
      /*  Test.seItem = selected[0];
        return true;*/
    }
};

/**
 * 点击添加测试
 */
Test.openAddTest = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/test/test_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改测试
 */
Test.openTestDetail = function () {
    if (this.check(1)) {
        var index = layer.open({
            type: 2,
            title: '测试详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/test/test_update/' +  Test.seItem.id
          });
        this.layerIndex = index;
    }
};

/**
 * 删除测试
 */
Test.delete = function () {
    if (this.check(2)) {
        var ajax = new $ax(Hussar.ctxPath + "/test/delete", function (data) {
            Hussar.success("删除成功!");
            $('#TestTable').bootstrapTable('refresh');
        }, function (data) {
            Hussar.error("删除失败!" + data.responseJSON.message + "!");
        });
        var ids = "";
        for(var i = 0;i < Test.seItem.length;i++){
            ids += Test.seItem[i].id + ","
        }
        ajax.set("testId", ids  );
        ajax.start();
    }
};

/**
 * 查询测试列表
 */
Test.search = function () {
    $('#TestTable').bootstrapTable('refresh');
};

Test.queryParams = function() {
    var temp = {
        pageSize : this.pageSize,  //每条页数
        pageNumber : this.pageNumber, //页码
        name : $('#name').val()
    };
    return temp;
};

$(function () {
    var defaultColunms = Test.initColumn();

    $('#TestTable').bootstrapTable({
            dataType:"json",
            url:'/test/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            queryParams: Test.queryParams,
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){Test.pageNumber = number ; Test.pageSize = size}
        });
})

});