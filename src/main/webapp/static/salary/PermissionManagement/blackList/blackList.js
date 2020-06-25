/**
 * 薪资权限管理---黑名单维护管理初始化
 */
var BlackList = {
    id: "BlackListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    pageSize:20,
    pageNumber:1,
    isFold : true,
    loadIndex:-2
};

/**
 *  保存四个select选择的当前值
 * @type {{staffId: string, staffName: string, departmentName: string, permissionName: string}}
 */
var SelectValDict={
    staffId:"",
    staffName:"",
    departmentName:"",
    permissionName:""
};

layui.use(['layer','bootstrap_table_edit','Hussar', 'HussarAjax','form'], function(){
	var layer = layui.layer
	    ,table = layui.table
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

    /**
     * 监听select的选择事件
     */
    form.on('select', function(data){

        // console.log(data.elem.id); //得到select原始DOM对象的id
        // console.log(data.value); //得到被选中的值
        // console.log(data.othis); //得到美化后的DOM对象

        var id = data.elem.id;
        var val = data.value;
        SelectValDict[id] = val;
        console.log(SelectValDict);
        // 接下来根据当前的SelectValDict查询相应结果 更新bootstrapTable
        // 因为之前的翻页有bug所以改成了get请求的方式
        var query={
            staffId:SelectValDict.staffId,
            staffName:SelectValDict.staffName,
            departmentName:SelectValDict.departmentName,
            permissionName:SelectValDict.permissionName
        };
        var str = "?staffId="+query.staffId+"&staffName="+query.staffName
            +"&departmentName="+query.departmentName
            +"&permissionName="+query.permissionName;
        var u ="";
        if (BlackList.isFold)
            u += "/blackList/merge_showBySelect"+str;
        else
            u += "/blackList/showBySelect"+str;
        BlackList.createNewTable(u);

        // var opt={
        //     url: Hussar.ctxPath + u +str,
        //     silent: true
        // };
        // $('#BlackListTable').bootstrapTable('refresh',opt);

    });

    /**
     *  生成select
     */
    BlackList.createSelect = function(){
    var ajax = new $ax(Hussar.ctxPath + "/blackList/select", function (data) {
        console.log(data);
        //得到员工工号列表、姓名列表、权限部门名称列表、权限名称列表
        // var staffIdList = data.staffIdList,
        //     staffNameList = data.staffNameList,
        var departmentNameList = data.departmentNameList,
            permissionNameList = data.permissionNameList

        var str1="",str2="",str3="",str4="";
        // str1 += "<option value=''>请搜索或选择员工工号</option>";
        // str2 += "<option value=''>请搜索或选择员工姓名</option>";
        str3 += "<option value=''>请搜索或选择部门名称</option>";
        str4 += "<option value=''>请搜索或选择权限名称</option>";
        // //工号select
        // for (var key in staffIdList)
        //     //key是list的下标
        //     str1 += "<option value=\""+staffIdList[key]+"\">"+staffIdList[key]+"</option>";
        //
        // //姓名select
        // for (var key in staffNameList)
        //     //key是list的下标
        //     str2 += "<option value=\""+staffNameList[key]+"\">"+staffNameList[key]+"</option>";

        // 部门select
        for (var key in departmentNameList)
            //key是list的下标
            str3 += "<option value=\""+departmentNameList[key]+"\">"+departmentNameList[key]+"</option>";

        // 权限select
        for (var key in permissionNameList)
            //key是list的下标
            str4 += "<option value=\""+permissionNameList[key]+"\">"+permissionNameList[key]+"</option>";

        // $('#staffId').empty().append(str1);
        // $('#staffName').empty().append(str2);
        $('#departmentName').empty().append(str3);
        $('#permissionName').empty().append(str4);
        form.render('select')

    }, function (data) {
        Hussar.error("获取下拉列表失败!");
    });
    ajax.start();
};

/**
 * 获取table各列的宽度
 */
BlackList.getTableColWidth = function(){
  var table = $('#WhiteListTable').width();
  console.log(table)

};


/**
 * 初始化表格的列
 */
BlackList.initColumn = function () {
    return [
        {checkbox:true, halign:'center',align:"center",width: "4%"},
        {title: '序号',align:"center" ,halign:'center',width: "4%",formatter: function (value, row, index) {return (BlackList.pageNumber-1)*BlackList.pageSize +1 +index ;}},
            {title: '员工工号', field: 'staff.staffId', align: 'center',halign:'center',width: "23%"},
            {title: '员工姓名', field: 'staff.staffName', align: 'center',halign:'center',width: "23%"},
            {title: '管理的部门', field: 'department.departmentName', align: 'center',halign:'center',width: "23%"},
            {title: '被限制的权限', field: 'permissionName', align: 'center',halign:'center',width: "23%"}
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
        BlackList.seItem = selected; // 这里修改成了保存整个selected数组
        return true;
    }
};

/**
 *  展开合并的选项
 */
BlackList.unfold = function(){
    BlackList.isFold = false;
    $('#add').hide();
    $('#edit').hide();
    $('#unfold').hide();
    $('#fold').show();
    $('#del').show();
    // 重置下拉选择框
    BlackList.createSelect();
    SelectValDict ={
        staffId:"",
        staffName:"",
        departmentName:"",
        permissionName:""
    };

    // var opt={
    //     url: Hussar.ctxPath + "/blackList/list",
    //     silent: true
    // };
    // $('#BlackListTable').bootstrapTable('refresh',opt);
    BlackList.createNewTable("/blackList/list");
};

/**
 *  收起合并的数据
 */
BlackList.fold = function(){
    $('#add').show();
    $('#edit').show();
    $('#unfold').show();
    $('#fold').hide();
    $('#del').hide();
    BlackList.isFold = true;
    BlackList.reset();
};

/**
 * 点击添加薪资权限管理---黑名单维护
 */
BlackList.openAddBlackList = function () {

    var index = layer.open({
        type: 2,
        title: '黑名单--添加',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Hussar.ctxPath + '/blackList/blackList_add'
    });
    this.layerIndex = index;
};

/**
 *  修改黑名单中某个员工的部门和权限
 */
BlackList.openBlackListDetail = function () {
    if (this.check()) {
        if (BlackList.seItem.length>1){
            Hussar.error("不可选择多条数据");
            return
        }

        // BlackList.seItem[0] 转换请求参数字符串
        var modify_item = "?staffId="+String(BlackList.seItem[0].staff.staffId)
            +"&departmentId="+String(BlackList.seItem[0].department.departmentId)
            +"&staffName="+BlackList.seItem[0].staff.staffName
            +"&departmentName="+BlackList.seItem[0].department.departmentName;


        var index = layer.open({
            type: 2,
            title: '黑名单--修改',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Hussar.ctxPath + '/blackList/blackList_update/' +  modify_item
          });
        this.layerIndex = index;
    }
};

/**
 * 删除薪资权限管理---黑名单维护
 */
BlackList.delete = function () {
    if (this.check()) {
        Hussar.confirm("确定要删除所选项吗？",function () {
            var ajax = new $ax(Hussar.ctxPath + "/blackList/delete", function (data) {
                Hussar.success("删除成功!");
                $('#BlackListTable').bootstrapTable('refresh');
            }, function (data) {
                Hussar.error("删除失败!" + data.responseJSON.message + "!");
            });
            // 构造BlackList对象数据 并且转化为JSON格式的字符串
            var delete_list_arr = [];
            for (var key in BlackList.seItem){
                var tmp ={
                    staffId:BlackList.seItem[key].staff.staffId,
                    departmentId:BlackList.seItem[key].department.departmentId,
                    permissionId:BlackList.seItem[key].permissionList[0].permissionId
                };
                delete_list_arr.push(tmp)
            }
            var delete_list = JSON.stringify(delete_list_arr);
            ajax.set("delete_list",delete_list);
            ajax.start();
            // 不管是否成功都将 BlackList.seItem重新设置为Null
            BlackList.seItem = null;
        });
    }
};

/**
 * 查询薪资权限管理---黑名单维护列表
 */
BlackList.search = function () {
    var search_condition = $('#condition').val();
    // var opt={
    //     url: Hussar.ctxPath + "/blackList/merge_list",
    //     silent: true,
    //     query:{
    //         search_condition:search_condition
    //     }
    // };
    // $('#BlackListTable').bootstrapTable('refresh',opt);
    BlackList.createNewTable("/blackList/merge_list?search_condition="+search_condition)
};



/**
 * 重置功能 先将 $('#condition').val()设置为""
 * 然后调用WhiteList.search
 * 最后需要重四个下拉选择框 并将之前保存的数据清空
 */
BlackList.reset = function(){
    $('#condition').val("");
    BlackList.search();
    BlackList.createSelect();
    SelectValDict={
        staffId:"",
        staffName:"",
        departmentName:"",
        permissionName:""
    };
};

/**
 * 销毁之前的表格 创建新的表格
 */
BlackList.createNewTable = function(url){
    // 重新创建表格时添加遮罩
    BlackList.loadIndex = layer.load({
        icon :1,
        area:[$("body").height(),$("body").width()],
        shade: [0.5, '#1cbbb4'],
        time:0 // 需要手动关闭
    });
    $('#BlackListTable').bootstrapTable('destroy');
    BlackList.pageNumber = 1;
    BlackList.pageSize = 20;
    var defaultColunms = BlackList.initColumn();
    $('#BlackListTable').bootstrapTable({
        dataType:"json",
        url:url,
        pagination:true,
        pageList:[10,15,20,50,100],
        striped:true,
        pageSize:20,
        queryParamsType:'',
        columns: defaultColunms,
        height:$("body").height() - $(".layui-form").outerHeight(true) - $("#select-group").outerHeight(true) - 26,
        sidePagination:"server",
        onPageChange:function(number, size){BlackList.pageNumber = number ; BlackList.pageSize = size},
        onLoadSuccess:function () {
            // 数据加载完成后触发
            if (BlackList.loadIndex>=0){
                layer.close(BlackList.loadIndex);
                BlackList.loadIndex = -2
            }
        }
    });
};


    $(function () {
        BlackList.fold();
    })

});