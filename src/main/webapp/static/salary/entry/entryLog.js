/**
 * 入职日志管理初始化
 */
var EntryLog = {
    id: "EntryLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1,
    loadIndex:-2
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

    var loadingData = layer.load(1, {shade: [0.5,'#fff'], time:0}); //遮罩

    // 监听select的变化
    form.on('select(searchBar)',function (data) {
        // console.log(data);
        selectList[data.elem.id] = data.value;
        // console.log(selectList);
        // selectList.empty();
        $('#EntryLogTable').bootstrapTable('destroy');
        EntryLog.pageNumber = 1;
        EntryLog.pageSize = 20;
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        $('#EntryLogTable').bootstrapTable( {
            dataType:"json",
            url:'/entry/list/condition',
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
            columns: EntryLog.initColumn(),
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){EntryLog.pageNumber = number ; EntryLog.pageSize = size},
        });

    });


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
            {title: '入职部门', field: 'entrant', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.department.departmentName
                }},
            {title: '入职岗位', field: 'entrant', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.position.positionName
                }},
            {title: '操作时间', field: 'operationTime', align: 'center',halign:'center'}
    ];
};


/**
 * 查询入职日志列表
 */
EntryLog.search = function () {
    var condition1 = $('#condition1').val();
    var condition2 = $('#condition2').val();
    var condition3 = $('#condition3').val();
    //var opt = {
    //    url: Hussar.ctxPath + "/entry/list",
    //    silent: true,
    //    query:{
    //        condition1:condition1,
    //        condition2:condition2,
    //        condition3:condition3
    //    }
    //}

    //$('#EntryLogTable').bootstrapTable('refresh',opt);
    // 添加一层遮罩
    EntryLog.loadIndex = layer.load({
        icon :1,
        shade: [0.5, '#1cbbb4'],
        area:[$("body").height(),$("body").width()],
        time:0 // 需要手动关闭
    });

    $('#EntryLogTable').bootstrapTable('destroy');
    EntryLog.pageNumber = 1;
    EntryLog.pageSize = 20;
    $('#EntryLogTable').bootstrapTable({
        dataType:"json",
        url:"/entry/list?condition1="+condition1+"&condition2="+condition2+"&condition3="+condition3,
        pagination:true,
        pageList:[10,15,20,50,100],
        striped:true,
        pageSize:20,
        queryParamsType:'',
        columns: EntryLog.initColumn(),
        height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
        sidePagination:"server",
        onPageChange:function(number, size){EntryLog.pageNumber = number ; EntryLog.pageSize = size},
        onLoadSuccess:function () {
            if (EntryLog.loadIndex>=0){
                // 数据加载完成之后 关闭遮罩
                layer.close(EntryLog.loadIndex);
                EntryLog.loadIndex = -2
            }
        }
    });
};

//清空筛选下拉框
    selectList.empty = function(){
        $("#department option").not('option:first').remove();
        $("#position option").not('option:first').remove();
        $("#operationTime option").not('option:first').remove();
    }

// 筛选下拉框初始化
    selectList.init = function(){
        var condition1 = $('#condition1').val();
        var condition2 = $('#condition2').val();
        var condition3 = $('#condition3').val();
        //selectList.empty();
        var ajax = new $ax(Hussar.ctxPath + "/entry/list/select",function (data) {
            // console.log(data);
            //$("#department").append(new Option('请选择入职部门',""));
            //$("#department").val("");
            $.each(data.departments,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#department").append(new Option(item.departmentName,item.departmentId));
            });
            $("#position").append(new Option('请选择入职岗位',""));
            $("#position").val("");
            $.each(data.positions,function (index,item) {
                //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
                $("#position").append(new Option(item.positionName,item.positionId));
            });
            //$("#operationTime").append(new Option('请选择员工入职时间',""));
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
            onPageChange:function(number, size){EntryLog.pageNumber = number ; EntryLog.pageSize = size},
        onLoadSuccess:function () {
            layer.close(loadingData);
        }
        });
    selectList.init();
})

});