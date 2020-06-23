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

// 筛选列表
var selectList={
    department:'',
    position:'',
    operationTime:''
}

layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

    form.on('select(searchBar)',function (data) {
        // console.log(data);
        selectList[data.elem.id] = data.value;
        // console.log(selectList);
        // selectList.empty();
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        var opt = {
            url: Hussar.ctxPath + "/departure/list/condition",
            silent: true,
            method: 'POST',
            contentType:"application/x-www-form-urlencoded",
            queryParams:function(params){
                console.log(params)
                return{
                    condition1:condition1,
                    condition2:condition2,
                    condition3:condition3,
                    selectList:JSON.stringify(selectList),
                    pageNumber:params.pageNumber,
                    pageSize:params.pageSize
                }

            },
            queryParamsType:'',
        }
        $('#DepartureLogTable').bootstrapTable('selectPage', 1);
        $('#DepartureLogTable').bootstrapTable('refreshOptions',opt);
    });


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
        {title: '原属部门', field: 'departure', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.department.departmentName
            }},
        {title: '原属岗位', field: 'departure', align: 'center',halign:'center',
            formatter: function(value, item, index){
                return value.position.positionName
            }},
        {title: '操作时间', field: 'operationTime', align: 'center',halign:'center'}
    ];
};



/**
 * 查询离职日志列表
 */
DepartureLog.search = function () {

    var condition1 = $('#condition1').val();
    var condition2 = $('#condition2').val();
    var condition3 = $('#condition3').val();
    var opt = {
        url: Hussar.ctxPath + "/departure/list",
        silent: true,
        query:{
            condition1:condition1,
            condition2:condition2,
            condition3:condition3
        }
    }
    $('#DepartureLogTable').bootstrapTable('refresh',opt);
};


//清空筛选下拉框
selectList.empty = function(){
    $("#department").empty();
    $("#position").empty();
    $("#operationTime").empty();
}

// 筛选下拉框初始化
    selectList.init = function(){
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        selectList.empty();
        var ajax = new $ax(Hussar.ctxPath + "/departure/list/select",function (data) {
            // console.log(data);
            $("#department").append(new Option('请选择原属部门',""));
            $("#department").val("");
            $.each(data.departments,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#department").append(new Option(item.departmentName,item.departmentId));
            });
            $("#position").append(new Option('请选择原属岗位',""));
            $("#position").val("");
            $.each(data.positions,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#position").append(new Option(item.positionName,item.positionId));
            });
            $("#operationTime").append(new Option('请选择员工离职时间',""));
            $("#operationTime").append(new Option('所有','所有'));
            $("#operationTime").val("");
            $.each(data.operationTimes,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#operationTime").append(new Option(item,item));
            });
            form.render('select','search');
        },function (data) {
            Hussar.error("获取筛选列表失败!");
        });
        ajax.set("condition1",condition1);
        ajax.set("condition2",condition2);
        ajax.set("condition3",condition3);
        ajax.start();
    }

$(function () {
    var defaultColunms = DepartureLog.initColumn();
    selectList.init();
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