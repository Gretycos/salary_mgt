/**
 * 人员管理管理控制器
 */

var Staff = {
    id: "StaffTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1
};

// 筛选列表
var selectList={
    gender:'',
    department:'',
    position:'',
    entryTime:'',
    departureTime:''
}

layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

	form.on('select',function (data) {
        // console.log(data);
        selectList[data.elem.id] = data.value;
        // console.log(selectList);
        // selectList.empty();
        var condition = $('#condition').val();
        var opt = {
            url: Hussar.ctxPath + "/staff/list/condition",
            silent: true,
            method: 'POST',
            contentType:"application/x-www-form-urlencoded",
            queryParams:function(params){
                // console.log(params)
                return {
                    condition:condition,
                    selectList:JSON.stringify(selectList),
                    pageNumber:params.pageNumber,
                    pageSize:params.pageSize
                }
            },
            queryParamsType:'',
        }
        $('#StaffTable').bootstrapTable('selectPage', 1);
        $('#StaffTable').bootstrapTable('refreshOptions',opt);
    });


/**
 * 初始化表格的列
 */
Staff.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (Staff.pageNumber-1)*Staff.pageSize +1 +index ;}},
            {title: '员工工号', field: 'staffId', align: 'center',halign:'center'},
            {title: '员工姓名', field: 'staffName', align: 'center',halign:'center'},
            {title: '性别', field: 'gender', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    if(value === '0'){
                        return '女'
                    }else {
                        return '男'
                    }
                }},
            {title: '部门', field: 'department', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.departmentName
                }},
            {title: '职位', field: 'position', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.positionName
                }},
            {title: '入职时间', field: 'entryTime', align: 'center',halign:'center',width: '145',
                formatter: function(value, item, index){
                    return value.split(' ')[0]
                }},
            {title: '离职时间', field: 'departureTime', align: 'center',halign:'center',width: '145',
                formatter: function(value, item, index){
                    if(value){
                        return value.split(' ')[0]
                    }
                }}
    ];
};

/**
 * 检查是否选中
 */
Staff.check = function () {
    var selected = $('#StaffTable').bootstrapTable('getSelections');
    if(selected.length === 0){
        Hussar.info("请先选中表格中的某一记录！");
        return false;
    }else if(selected.length === 1){
        Staff.seItem = selected[0];
        // console.log(Staff.seItem);
        if(Staff.seItem.departureTime !== ""){
            Hussar.info("选中存在离职人员！");
            return false;
        }
        return true;
    }else{
        Hussar.info("请选中表格中的仅一项记录！");
        return false;
    }
};

/**
* 检查是否选中
*/
Staff.check_ = function () {
        var selected = $('#StaffTable').bootstrapTable('getSelections');
        if(selected.length === 0){
            Hussar.info("请先选中表格中的某一记录！");
            return false;
        }else{
            Staff.seItem = selected;
            for(var i in Staff.seItem){
                if(Staff.seItem[i].departureTime !== ""){
                    Hussar.info("选中存在离职人员！");
                    return false;
                }
            }
            return true;
        }
    };

/**
 * 点击添加人员管理
 */
Staff.openAddStaff = function () {
    var index = layer.open({
        type: 2,
        title: '添加人员管理',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/staff/staff_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改人员管理
 */
Staff.openStaffDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '人员管理详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/staff/staff_update/' +  Staff.seItem.staffId
              });
        this.layerIndex = index;
    }
};

/**
 * 删除人员管理
 */
Staff.delete = function () {
    if (this.check_()) {
        layer.confirm('您确定要让选中的员工离职吗',{
            title:'提示',
            btn:['确定','取消']
        },function () {
            var listDeparture=[]
            var departure=false;
            for (var i in Staff.seItem) {
                if(Staff.seItem[i].departureTime){
                    departure=true;
                    break;
                }else{
                    var single = {
                        staffId:Staff.seItem[i].staffId
                    }
                    listDeparture.push(single);
                }
            }
            if(departure){
                Hussar.error("离职失败!选中的员工存在已离职!");
                return false;
            }else{
                console.log(listDeparture)
                var ajax = new $ax(Hussar.ctxPath + "/staff/delete", function (data) {
                    if (data.code===200){
                        Hussar.success("离职成功!");
                        $('#StaffTable').bootstrapTable('refresh');
                    } else {
                        Hussar.error("离职失败!" + data.message + "!");
                    }
                }, function (data) {
                    Hussar.error("离职失败!" + data.responseJSON.message + "!");
                });
                ajax.set("staff", JSON.stringify(listDeparture));
                ajax.set("jstime",new Date().getTime());
                ajax.start();
            }
        },function (index) {
            layer.close(index);
        });

    }
};

/**
 * 查询人员管理列表
 */
Staff.search = function () {
    var condition = $('#condition').val();
    var opt = {
        url: Hussar.ctxPath + "/staff/list",
        silent: true,
        query:{
            condition:condition
        }
    }
    $('#StaffTable').bootstrapTable('refresh',opt);
};

/**
* 清空筛选下拉框
*/
selectList.empty = function(){
    $("#gender").empty();
    $("#department").empty();
    $("#position").empty();
    $("#entryTime").empty();
    $("#departureTime").empty();
}

// 筛选下拉框初始化
selectList.init = function(){
    var condition = $('#condition').val();
    selectList.empty();
    var ajax = new $ax(Hussar.ctxPath + "/staff/list/select",function (data) {
            // console.log(data);
        $("#gender").append(new Option('性别',""));
        $("#gender").val("");
        $.each(data.genders,function (index,item) {
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#gender").append(new Option(item==='0'?'女':'男',item));
        });
        $("#department").append(new Option('部门',""));
        $("#department").val("");
        $.each(data.departments,function (index,item) {
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#department").append(new Option(item.departmentName,item.departmentId));
        });
        $("#position").append(new Option('职位',""));
        $("#position").val("");
        $.each(data.positions,function (index,item) {
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#position").append(new Option(item.positionName,item.positionId));
        });
        form.render('select','toolbar');
        $("#entryTime").append(new Option('入职时间',""));
        $("#entryTime").val("");
        $.each(data.entryTimes,function (index,item) {
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#entryTime").append(new Option(item,item));
        });
        $("#departureTime").append(new Option('离职时间',""));
        $("#departureTime").append(new Option('在职','在职'));
        $("#departureTime").append(new Option('已离职','已离职'));
        $("#departureTime").val("");
        $.each(data.departureTimes,function (index,item) {
            //option 第一个参数是页面显示的值，第二个参数是传递到后台的值
            $("#departureTime").append(new Option(item,item));
        });
        form.render('select','searchBar');
    },function (data) {
        Hussar.error("获取筛选列表失败!");
    });
    ajax.set("condition",condition);
    ajax.start();
}

$(function () {
    var defaultColunms = Staff.initColumn();

    $('#StaffTable').bootstrapTable({
            dataType:"json",
            url:'/staff/list',
            pagination:true,
            pageList:[10,15,20,50,100],
            striped:true,
            pageSize:20,
            queryParamsType:'',
            columns: defaultColunms,
            height:$("body").height() - $(".layui-form").outerHeight(true) - 26,
            sidePagination:"server",
            onPageChange:function(number, size){Staff.pageNumber = number ; Staff.pageSize = size}
        });
    selectList.init();

})

});