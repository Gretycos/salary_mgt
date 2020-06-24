/**
 * 初始化薪资权限管理--白名单添加
 * author : 王明凯
 */
var WhiteListInfoDlg = {
    selectDepartmentAndPermission:{
        departmentName:"",
        permissionName:"", // 因为权限可以是多选了 这里改成了数组
        staffName:"",
        staffId:null,
        departmentId:null,
        permissionId:null
    },
    select_data : [],
    multiSelector:null
};

layui.use(['layer', 'Hussar', 'HussarAjax', 'laydate','form'], function(){
	var layer = layui.layer
        ,Hussar = layui.Hussar
	    ,$ = layui.jquery
	    ,laydate = layui.laydate
	    ,$ax = layui.HussarAjax
        ,form = layui.form;

/**
 * 监听部门和权限的选择
 */
form.on('select(dp)', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    WhiteListInfoDlg.selectDepartmentAndPermission[id] = val;
    console.log("当前selectDepartmentAndPermission");
    console.log(WhiteListInfoDlg.selectDepartmentAndPermission);
});

// 监听员工ID下拉选择 对员工姓名进行绑定
form.on('select(staffId)', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    WhiteListInfoDlg.selectDepartmentAndPermission[id] = val;
    // 如果当前选择的是提示option 那么val值是空的 此时不去查询staff 而是使用createSelectOfStaff初始化
    if (val=="")
    {
        WhiteListInfoDlg.createSelectOfStaff();
        WhiteListInfoDlg.selectDepartmentAndPermission['staffId'] = null;
        WhiteListInfoDlg.selectDepartmentAndPermission['staffName'] = "";
    }
    else {
        // 去请求数据库 得到staffId = val 的员工姓名 重新生成staffName下拉框
        var ajax = new $ax(Hussar.ctxPath + "/whiteList/getStaffById", function(data){
            // 得到data中的权限数组和部门信息数组
            var staffName = data.staff.staffName;
            // 下面开始拼接option
            var str1="",str2="",str3="";
            str1 += "<option value=\""+staffName+"\">"+staffName+"</option>";
            WhiteListInfoDlg.selectDepartmentAndPermission['staffName'] = staffName;

            // 构造权限列表 和 部门列表
            var permissionList = data.permissionList;
            var departmentList = data.departmentList;
            if (permissionList.length==1){
                // str2 += "<option value=\""+permissionList[0].permissionName+"\">"+permissionList[0].permissionName+"</option>";
                WhiteListInfoDlg.select_data=[
                    {
                        name : permissionList[0].permissionName,
                        value : permissionList[0].permissionName
                    }
                ];
                // 刷新multiSelector 刷新数据
                str3 += "<option value=\""+departmentList[0].departmentName+"\">"+departmentList[0].departmentName+"</option>";
                // 修改保存的当前的要提交的数据
                WhiteListInfoDlg.selectDepartmentAndPermission['permissionName'] = "";
                // WhiteListInfoDlg.selectDepartmentAndPermission['permissionName'].push(permissionList[0].permissionName);
                WhiteListInfoDlg.selectDepartmentAndPermission['departmentName'] = departmentList[0].departmentName;
            } else {
                // str2 += "<option value=''>请搜索或选择权限</option>";
                str3 += "<option value=''>请搜索或选择部门</option>";
                // for (var key in permissionList) {
                //     str2 += "<option value=\""+permissionList[key].permissionName+"\">"+permissionList[key].permissionName+"</option>";
                // }
                for (var key in departmentList) {
                    str3 += "<option value=\""+departmentList[key].departmentName+"\">"+departmentList[key].departmentName+"</option>";
                }
                // 0623 修改 权限多选
                WhiteListInfoDlg.select_data=[];
                for (var key in permissionList) {
                    var tmp ={
                        name : permissionList[key].permissionName,
                        value : permissionList[key].permissionName
                    };
                    WhiteListInfoDlg.select_data.push(tmp);
                }
            }
            // 通过JQuery给相应的select添加上option
            $('#staffName').empty().append(str1);
            // $('#permissionName').empty().append(str2);
            WhiteListInfoDlg.multiSelector.update({data:WhiteListInfoDlg.select_data});
            $('#departmentName').empty().append(str3);

            
            form.render('select');

        },function(data){
            Hussar.error("生成下拉选择框失败!" );
        });
        ajax.set("staffId",parseInt(val));
        ajax.start();
    }
     //不要忘记render 只重新渲染staffName
    console.log("当前selectDepartmentAndPermission");
    console.log(WhiteListInfoDlg.selectDepartmentAndPermission);

});

// 监听员工姓名下拉选择 对员工ID进行绑定
form.on('select(staffName)', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    WhiteListInfoDlg.selectDepartmentAndPermission[id] = val;

    if (val=="")
    {
        WhiteListInfoDlg.createSelectOfStaff();
        WhiteListInfoDlg.selectDepartmentAndPermission['staffId'] = null;
        WhiteListInfoDlg.selectDepartmentAndPermission['staffName'] = "";
    }
    else {
        // 去请求数据库 得到staffName = val 的员工ID 重新生成staffID下拉框
        var ajax = new $ax(Hussar.ctxPath + "/whiteList/getStaffsByName", function(data){
            var staffList = data.staffList;
            // 下面开始拼接option
            var str1="",str2="",str3="";
            if (staffList.length==1){
                str1 += "<option value=\""+staffList[0].staffId+"\">"+staffList[0].staffId+"</option>";
                WhiteListInfoDlg.selectDepartmentAndPermission['staffId'] = staffList[0].staffId;

                // 构造权限列表 和 部门列表
                var permissionList = data.permissionList;
                var departmentList = data.departmentList;
                if (permissionList.length==1){
                    // str2 += "<option value=\""+permissionList[0].permissionName+"\">"+permissionList[0].permissionName+"</option>";
                    WhiteListInfoDlg.select_data=[
                        {
                            name:permissionList[0].permissionName,
                            value:permissionList[0].permissionName
                        }
                    ];
                    str3 += "<option value=\""+departmentList[0].departmentName+"\">"+departmentList[0].departmentName+"</option>";
                    // WhiteListInfoDlg.selectDepartmentAndPermission['permissionName'] = permissionList[0].permissionName;
                    // WhiteListInfoDlg.selectDepartmentAndPermission['permissionName'] = null; // 将当前的permissionName数组清空
                    WhiteListInfoDlg.selectDepartmentAndPermission['departmentName'] = departmentList[0].departmentName;
                } else {
                    // str2 += "<option value=''>请搜索或选择权限</option>";
                    str3 += "<option value=''>请搜索或选择部门</option>";
                    WhiteListInfoDlg.select_data= [];
                    for (var key in permissionList) {
                        // str2 += "<option value=\""+permissionList[key].permissionName+"\">"+permissionList[key].permissionName+"</option>";
                        var tmp = {
                            name:permissionList[key].permissionName,
                            value:permissionList[key].permissionName
                        };
                        WhiteListInfoDlg.select_data.push(tmp);
                    }
                    for (var key in departmentList) {
                        str3 += "<option value=\""+departmentList[key].departmentName+"\">"+departmentList[key].departmentName+"</option>";
                    }
                }

            }
            else{
                str1 += "<option value=''>请搜索或选择工号</option>";
                for (var key in staffList)
                    str1 += "<option value=\""+staffList[key].staffId+"\">"+staffList[key].staffId+"</option>";
            }

            // 通过JQuery给相应的select添加上option
            $('#staffId').empty().append(str1);
            // $('#permissionName').empty().append(str2);
            WhiteListInfoDlg.multiSelector.update({data:WhiteListInfoDlg.select_data});
            $('#departmentName').empty().append(str3);
            form.render('select');

        },function(data){
            Hussar.error("生成下拉选择框失败!" );
        });
        ajax.set("staffName",val);
        ajax.start();
    }

    console.log("当前selectDepartmentAndPermission");
    console.log(WhiteListInfoDlg.selectDepartmentAndPermission);
});


/**
 * 初始化部门下拉选择框和权限下拉选择框
 */
WhiteListInfoDlg.createSelectOfDP= function(){
    var ajax = new $ax(Hussar.ctxPath + "/whiteList/getAllDepartmentAndPermission", function(data){
        // 得到data中的权限数组和部门信息数组
        var departmentList = data.departmentList,
            permissionList = data.permissionList;

        // 下面开始拼接option
        var str1="",str2="";
        str1 += "<option value=''>请搜索或选择部门</option>";
        // str2 += "<option value=''>请搜索或选择权限</option>";
        for (var key in departmentList){
            str1 +=  "<option value=\""+departmentList[key].departmentName+"\">"
                        +departmentList[key].departmentName+"</option>";
        }

        // 构造权限的下拉多选框 的data = [{name:"",value:""},{name:"",value:""}...]
        WhiteListInfoDlg.select_data=[];
        for (var key in permissionList){
            var tmp ={
                name : permissionList[key].permissionName,
                value : permissionList[key].permissionName
            };
            WhiteListInfoDlg.select_data.push(tmp);
        }

        // 通过JQuery给相应的select添加上option
        $('#departmentName').empty().append(str1);
        // $('#permissionName').empty().append(str2);

        form.render('select') //不要忘记render
    },function(data){
        Hussar.error("生成下拉选择框失败!" );
    });
    ajax.start();
};

// 监听权限的下拉多选框
    

/**
 * 初始化员工工号和姓名选择框
 * 需要注意的是 工号和姓名应该双向绑定
 */
WhiteListInfoDlg.createSelectOfStaff= function(){
    var ajax = new $ax(Hussar.ctxPath + "/whiteList/getAllStaff", function(data){
        // 得到data中的员工 List
        var staffList = data.staffList;

        // 下面开始拼接option
        var str1="",str2="";
        str1 += "<option value=''>请搜索或选择工号</option>";
        str2 += "<option value=''>请搜索或选择姓名</option>";

        for (var key in staffList){
            str1 +=  "<option value=\""+staffList[key].staffId+"\">"
                +staffList[key].staffId+"</option>";
            str2 +=  "<option value=\""+staffList[key].staffName+"\">"
                +staffList[key].staffName+"</option>";
        }

        // 通过JQuery给相应的select添加上option
        $('#staffId').empty().append(str1);
        $('#staffName').empty().append(str2);

        form.render('select') //不要忘记render
    },function(data){
        Hussar.error("生成下拉选择框失败!" );
    });
    ajax.start();
};


/**
 * 关闭此对话框
 */
WhiteListInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.WhiteList.layerIndex);
};


/**
 * 提交添加
 */
WhiteListInfoDlg.addSubmit = function() {
    var param = this.selectDepartmentAndPermission;
    if (param.staffName==""){
        Hussar.error("请选择员工姓名!");
        return;
    }
    if (param.staffId==null){
        Hussar.error("请选择员工工号!");
        return;
    }
    if (param.departmentName==""){
        Hussar.error("请选择部门!");
        return;
    }
    if (WhiteListInfoDlg.multiSelector.getValue("value").length==0){
        Hussar.error("请选择权限!");
        return;
    }
    // 都没有问题之后进行提交
    var ajax = new $ax(Hussar.ctxPath + "/whiteList/add", function(data){

        if (data!=true&&data!=false) {
            Hussar.error(data+"权限已授予,不可重复添加");
            return;
        }
        if (data==true){
            window.parent.layui.Hussar.success("添加成功!");
            window.parent.$('#WhiteListTable').bootstrapTable('refresh');
            WhiteListInfoDlg.close();
        }else {
            Hussar.error("添加失败!");
        }
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    // ajax.setContentType("application/x-www-form-urlencoded");
    ajax.set({
        "paramStr":JSON.stringify(param),
        "permissionNameListStr":JSON.stringify(WhiteListInfoDlg.multiSelector.getValue("value"))
    });
    ajax.start();
};


/**
 * 初始化时间控件
 */
WhiteListInfoDlg.initLaydate = function() {
    var dateDom = $(".dateType");
    $.each($(".dateType"), function (i,dom) {
        laydate.render({
            elem: dom,
            type : 'datetime',
            trigger: 'click'
        });
    });
};

/**
 * 重置四个select
 */
WhiteListInfoDlg.reset = function(){
    WhiteListInfoDlg.createSelectOfStaff();
    WhiteListInfoDlg.createSelectOfDP();
    WhiteListInfoDlg.multiSelector = WhiteListInfoDlg.multiSelect(WhiteListInfoDlg.select_data);
    WhiteListInfoDlg.selectDepartmentAndPermission= {
        departmentName:"",
        permissionName:"",
        staffName:"",
        staffId:null,
        departmentId:null,
        permissionId:null
    }

};

WhiteListInfoDlg.multiSelect = function(cur_data){
  return  xmSelect.render({
      // 这里绑定css选择器
      el: '#permissionName',
      direction: 'down',
      tips: '请选择要授予的权限',
      autoRow:true,
      theme:{
          color:'#1cbbb4'
      },
      // 渲染的数据
      data: cur_data,
      // on:function (data) {
      //     console.log("当前权限");
      //     console.log(data.arr);
      // }
  });
};

$(function() {
    WhiteListInfoDlg.initLaydate();   //初始化时间控件
    WhiteListInfoDlg.reset();
});

});