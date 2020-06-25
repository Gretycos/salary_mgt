/**
 * 初始化薪资权限管理---黑名单添加
 * author : 王明凯
 */
var BlackListInfoDlg = {
    selectDepartmentAndPermission:{
        departmentName:"",
        permissionName:"",
        staffName:"",
        staffId:null,
        departmentId:null,
        permissionId:null
    },
    select_data : [],
    multiSelector:null,
    loadIndexInAdd:-1
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
    BlackListInfoDlg.selectDepartmentAndPermission[id] = val;
    console.log("当前selectDepartmentAndPermission");
    console.log(BlackListInfoDlg.selectDepartmentAndPermission);
});

// 监听员工ID下拉选择 对员工姓名进行绑定
form.on('select(staffId)', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    BlackListInfoDlg.selectDepartmentAndPermission[id] = val;
    // 如果当前选择的是提示option 那么val值是空的 此时不去查询staff 而是使用createSelectOfStaff初始化
    if (val=="")
    {
        BlackListInfoDlg.createSelectOfStaff();
        BlackListInfoDlg.selectDepartmentAndPermission['staffId'] = null;
        BlackListInfoDlg.selectDepartmentAndPermission['staffName'] = "";
    }
    else {
        // 去请求数据库 得到staffId = val 的员工姓名 重新生成staffName下拉框
        var ajax = new $ax(Hussar.ctxPath + "/blackList/getStaffById", function(data){
            // 得到data中的权限数组和部门信息数组
            var staffName = data.staff.staffName;
            // 下面开始拼接option
            var str1="",str2="",str3="";
            str1 += "<option value=\""+staffName+"\">"+staffName+"</option>";
            BlackListInfoDlg.selectDepartmentAndPermission['staffName'] = staffName;

            // 构造权限列表 和 部门列表
            var permissionList = data.permissionList;
            var departmentList = data.departmentList;
            if (permissionList.length==1){
                // str2 += "<option value=\""+permissionList[0].permissionName+"\">"+permissionList[0].permissionName+"</option>";
                BlackListInfoDlg.select_data=[{
                    name: permissionList[0].permissionName,
                    value:permissionList[0].permissionName
                }];
                str3 += "<option value=\""+departmentList[0].departmentName+"\">"+departmentList[0].departmentName+"</option>";
                // BlackListInfoDlg.selectDepartmentAndPermission['permissionName'] = permissionList[0].permissionName;
                BlackListInfoDlg.selectDepartmentAndPermission['departmentName'] = departmentList[0].departmentName;
            } else {
                // str2 += "<option value=''>请搜索或选择权限</option>";
                BlackListInfoDlg.select_data=[];
                str3 += "<option value=''>请搜索或选择部门</option>";
                for (var key in permissionList) {
                    // str2 += "<option value=\""+permissionList[key].permissionName+"\">"+permissionList[key].permissionName+"</option>";
                    var tmp={
                        name: permissionList[key].permissionName,
                        value:permissionList[key].permissionName
                    };
                    BlackListInfoDlg.select_data.push(tmp);
                }
                for (var key in departmentList) {
                    str3 += "<option value=\""+departmentList[key].departmentName+"\">"+departmentList[key].departmentName+"</option>";
                }
            }
            // 通过JQuery给相应的select添加上option
            $('#staffName').empty().append(str1);
            // $('#permissionName').empty().append(str2);
            BlackListInfoDlg.multiSelector.update({data:BlackListInfoDlg.select_data});
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
    console.log(BlackListInfoDlg.selectDepartmentAndPermission);

});

// 监听员工姓名下拉选择 对员工ID进行绑定
form.on('select(staffName)', function(data){
    var id = data.elem.id; //select标签的id
    var val = data.value;
    BlackListInfoDlg.selectDepartmentAndPermission[id] = val;

    if (val=="")
    {
        BlackListInfoDlg.createSelectOfStaff();
        BlackListInfoDlg.selectDepartmentAndPermission['staffId'] = null;
        BlackListInfoDlg.selectDepartmentAndPermission['staffName'] = "";
    }
    else {
        // 去请求数据库 得到staffName = val 的员工ID 重新生成staffID下拉框
        var ajax = new $ax(Hussar.ctxPath + "/blackList/getStaffsByName", function(data){
            // 得到data中的权限数组和部门信息数组
            var staffList = data.staffList;

            // 下面开始拼接option
            var str1="",str2="",str3="";
            if (staffList.length==1){
                str1 += "<option value=\""+staffList[0].staffId+"\">"+staffList[0].staffId+"</option>";
                BlackListInfoDlg.selectDepartmentAndPermission['staffId'] = staffList[0].staffId;

                // 构造权限列表 和 部门列表
                var permissionList = data.permissionList;
                var departmentList = data.departmentList;
                if (permissionList.length==1){
                    // str2 += "<option value=\""+permissionList[0].permissionName+"\">"+permissionList[0].permissionName+"</option>";
                    BlackListInfoDlg.select_data=[{
                       name:permissionList[0].permissionName,
                       value:permissionList[0].permissionName
                    }];
                    str3 += "<option value=\""+departmentList[0].departmentName+"\">"+departmentList[0].departmentName+"</option>";
                    // BlackListInfoDlg.selectDepartmentAndPermission['permissionName'] = permissionList[0].permissionName;
                    BlackListInfoDlg.selectDepartmentAndPermission['departmentName'] = departmentList[0].departmentName;
                } else {
                    // str2 += "<option value=''>请搜索或选择权限</option>";
                    BlackListInfoDlg.select_data=[];
                    str3 += "<option value=''>请搜索或选择部门</option>";
                    for (var key in permissionList) {
                        // str2 += "<option value=\""+permissionList[key].permissionName+"\">"+permissionList[key].permissionName+"</option>";
                        var tmp = {
                            name:permissionList[key].permissionName,
                            value:permissionList[key].permissionName
                        };
                        BlackListInfoDlg.select_data.push(tmp);
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
            BlackListInfoDlg.multiSelector.update({data:BlackListInfoDlg.select_data});
            $('#departmentName').empty().append(str3);
            form.render('select');

        },function(data){
            Hussar.error("生成下拉选择框失败!" );
        });
        ajax.set("staffName",val);
        ajax.start();
    }

    console.log("当前selectDepartmentAndPermission");
    console.log(BlackListInfoDlg.selectDepartmentAndPermission);
});


/**
 * 初始化部门下拉选择框和权限下拉选择框
 */
BlackListInfoDlg.createSelectOfDP= function(){
    var ajax = new $ax(Hussar.ctxPath + "/blackList/getAllDepartmentAndPermission", function(data){
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
        BlackListInfoDlg.select_data = [];
        for (var key in permissionList){
            var tmp ={
                name : permissionList[key].permissionName,
                value : permissionList[key].permissionName
            };
            BlackListInfoDlg.select_data.push(tmp);
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

/**
 * 初始化员工工号和姓名选择框
 * 需要注意的是 工号和姓名应该双向绑定
 */
BlackListInfoDlg.createSelectOfStaff= function(){
    var ajax = new $ax(Hussar.ctxPath + "/blackList/getAllStaff", function(data){
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
BlackListInfoDlg.close = function() {
    parent.layui.layer.close(window.parent.BlackList.layerIndex);
};


/**
 * 提交添加
 */
BlackListInfoDlg.addSubmit = function() {
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
    if (BlackListInfoDlg.multiSelector.getValue("value").length==0){
        Hussar.error("请选择权限!");
        return;
    }
    // 都没有问题之后进行提交
    var ajax = new $ax(Hussar.ctxPath + "/blackList/add", function(data){
        if (data!=true&&data!=false) {
            Hussar.error(data+"权限已授予,不可重复添加");
            return;
        }
        if (data==true){
            window.parent.layui.Hussar.success("添加成功!");
            window.parent.BlackList.loadIndex = window.parent.layer.load({
                icon :1,
                shade: [0.5, '#1cbbb4'],
                time:0 // 需要手动关闭
            });
            window.parent.$('#BlackListTable').bootstrapTable('refresh');
            BlackListInfoDlg.close();
        }else {
            Hussar.error("添加失败!");
        }
    },function(data){
        Hussar.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set({
        "paramStr":JSON.stringify(param),
        "permissionNameListStr":JSON.stringify(BlackListInfoDlg.multiSelector.getValue("value"))
    });
    ajax.start();
};



/**
 * 初始化时间控件
 */
BlackListInfoDlg.initLaydate = function() {
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
BlackListInfoDlg.reset = function(){
    // // 增加遮罩
    // BlackListInfoDlg.loadIndexInAdd = layer.load({
    //     icon :1,
    //     shade: [0.5, '#1cbbb4'],
    //     time:0 // 需要手动关闭
    // });
    BlackListInfoDlg.createSelectOfStaff();
    BlackListInfoDlg.createSelectOfDP();
    BlackListInfoDlg.multiSelector = BlackListInfoDlg.multiSelect(BlackListInfoDlg.select_data);
    BlackListInfoDlg.selectDepartmentAndPermission= {
        departmentName:"",
        permissionName:"",
        staffName:"",
        staffId:null,
        departmentId:null,
        permissionId:null
    };
    // 关闭遮罩
    // layer.close(BlackListInfoDlg.loadIndexInAdd);

};

// xm-select 渲染下拉多选框
BlackListInfoDlg.multiSelect = function(data){
    return  xmSelect.render({
        // 这里绑定css选择器
        el: '#permissionName',
        direction: 'down',
        theme:{
            color:'#1cbbb4'
        },
        tips: '请选择要授予的权限',
        autoRow:true,
        // 渲染的数据
        data: data
    });
};

$(function() {
    BlackListInfoDlg.reset();
    BlackListInfoDlg.initLaydate();   //初始化时间控件
});

});