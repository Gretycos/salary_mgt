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
layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax;

/**
 * 初始化表格的列
 */
Staff.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: 50},
        {title: '序号',align:"center" ,halign:'center',width:50 ,formatter: function (value, row, index) {return (Staff.pageNumber-1)*Staff.pageSize +1 +index ;}},
            {title: '员工ID', field: 'staffId', align: 'center',halign:'center'},
            {title: '姓名', field: 'staffName', align: 'center',halign:'center'},
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
            {title: '入职时间', field: 'entryTime', align: 'center',halign:'center',
                formatter: function(value, item, index){
                    return value.split(' ')[0]
                }},
            {title: '离职时间', field: 'departureTime', align: 'center',halign:'center',
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
                    Hussar.success("离职成功!");
                    $('#StaffTable').bootstrapTable('refresh');
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
    $('#StaffTable').bootstrapTable('refresh');
};

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
})

});