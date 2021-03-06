/**
 * 调动日志管理初始化
 */
var MoveLog = {
    id: "MoveLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1,
    loadIndex:-2
};

// 筛选列表
var selectList={
    oldD:'',
    oldP:'',
    newD:'',
    newP:'',
    operationTime:''
}

layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

    var loadingData = layer.load(1, {shade: [0.5,'#fff'], time:0}); //遮罩

    // 监听select的变化
    form.on('select(searchBar)',function (data) {
        // console.log(data);
        selectList[data.elem.id] = data.value;
        // console.log(selectList);
        // selectList.empty();
        $('#MoveLogTable').bootstrapTable('destroy');
        MoveLog.pageNumber = 1;
        MoveLog.pageSize = 20;
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        $('#MoveLogTable').bootstrapTable( {
            dataType:"json",
            url:'/move/list/condition',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            queryParams:function(params){
                //console.log(params)
                return{
                    condition1:condition1,
                    condition2:condition2,
                    condition3:condition3,
                    selectList:JSON.stringify(selectList),
                    pageNumber:params.pageNumber,
                    pageSize:params.pageSize
                }

            },
            columns: MoveLog.initColumn(),
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){MoveLog.pageNumber = number ; MoveLog.pageSize = size},
        });
    });


/**
 * 初始化表格的列
 */
MoveLog.initColumn = function () {
    var columns;
    columns =  [

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
    return columns;
};


/**
 * 查询调动日志列表
 */
MoveLog.search = function () {
    var condition1 = $('#condition1').val();
    var condition2 = $('#condition2').val();
    var condition3 = $('#condition3').val();
    // 添加一层遮罩
    MoveLog.loadIndex = layer.load({
        icon :1,
        shade: [0.5, '#1cbbb4'],
        area:[$("body").height(),$("body").width()],
        time:0 // 需要手动关闭
    });

    $('#MoveLogTable').bootstrapTable('destroy');
    MoveLog.pageNumber = 1;
    MoveLog.pageSize = 20;
    $('#MoveLogTable').bootstrapTable({
        dataType:"json",
        url:"/move/list?condition1="+condition1+"&condition2="+condition2+"&condition3="+condition3,
        pagination:true,
        pageList:[10,15,20,50,100],
        striped:true,
        pageSize:20,
        queryParamsType:'',
        columns: MoveLog.initColumn(),
        height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
        sidePagination:"server",
        onPageChange:function(number, size){MoveLog.pageNumber = number ; MoveLog.pageSize = size},
        onLoadSuccess:function () {
            if (MoveLog.loadIndex>=0){
                // 数据加载完成之后 关闭遮罩
                layer.close(MoveLog.loadIndex);
                MoveLog.loadIndex = -2
            }
        }
    });
};

//清空筛选下拉框
    selectList.empty = function(){
        $("#oldD option").not('option:first').remove();
        $("#oldP option").not('option:first').remove();
        $("#newD option").not('option:first').remove();
        $("#newP option").not('option:first').remove();
        $("#operationTime option").not('option:first').remove();
    }

// 筛选下拉框初始化
    selectList.init = function(){
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        selectList.empty();
        var ajax = new $ax(Hussar.ctxPath + "/move/list/select",function (data) {
            // console.log(data);
            //$("#oldD").append(new Option('请选择原部门',""));
            //$("#oldD").val("");
            $.each(data.oldDs,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#oldD").append(new Option(item.departmentName,item.departmentId));
            });
            //$("#oldP").append(new Option('请选择原职位',""));
            //$("#oldP").val("");
            $.each(data.oldPs,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#oldP").append(new Option(item.positionName,item.positionId));
            });
            //$("#newD").append(new Option('请选择新部门',""));
            //$("#newD").val("");
            $.each(data.newDs,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#newD").append(new Option(item.departmentName,item.departmentId));
            });
            //$("#newP").append(new Option('请选择新职位',""));
            //$("#newP").val("");
            $.each(data.newPs,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#newP").append(new Option(item.positionName,item.positionId));
            });
            //$("#operationTime").append(new Option('请选择调动时间',""));
            //$("#operationTime").append(new Option('所有','所有'));
            //$("#operationTime").val("");
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
            onPageChange:function(number, size){MoveLog.pageNumber = number ; MoveLog.pageSize = size},
        onLoadSuccess:function () {
            layer.close(loadingData);
        }
        });
    selectList.init();
})

});