/**
 * @Description: 组织机构树demo脚本文件
 * @Author: chenxin
 * @Date: 2018/5/28.
 */

layui.use(['jquery','layer','Hussar','jstree','HussarAjax','element'], function(){

	var Hussar = layui.Hussar;
	var $ = layui.jquery;
	var layer=layui.layer;
	var jstree=layui.jstree;
	var $ax=layui.HussarAjax;
	var element = layui.element;
	var layerView;
	var OrgTreeDemo = {};
	var userTreeArry=[];
	var RoleUserTreeArry=[];


	$("#orgTree").click(function(){
		layerView=layer.open({
			type: 1,
			area: ['350px','500px'],
			fix: false, //不固定
			maxmin: true,
			shadeClose: false,
			shade: 0.4,
			title: "组织机构树",
			content: $("#orgTreeDiv"),
			success:function(){
				var data;
				var ajax = new $ax(Hussar.ctxPath + "/orgTreeDemo/orgTree",function(result) {
					data = result;
				}, function(data) {
					Hussar.error("获取组织机构树失败！");
				});
				ajax.set("treeType", 1);
				ajax.start();
				OrgTreeDemo.initOrgTreeDemo(data);
			}
		});
	});

	$("#employeeTree").click(function(){
		layerView=layer.open({
			type: 1,
			area: ['350px','500px'],
			fix: false, //不固定
			maxmin: true,
			shadeClose: false,
			shade: 0.4,
			title: "人员树",
			content: $("#employeeTreeDiv"),
			success:function(){
				var data;
				var ajax = new $ax(Hussar.ctxPath + "/orgTreeDemo/employeeTree",function(result) {
					data = result;
				}, function(data) {
					Hussar.error("获取人员失败！");
				});
				ajax.set("treeType", 2);
				ajax.start();
				OrgTreeDemo.initEmployeeTree(data);
			}
		});
	});

	$("#search").click(function(){
		layerView=layer.open({
			type: 1,
			area: ['350px','500px'],
			fix: false, //不固定
			maxmin: true,
			shadeClose: false,
			shade: 0.4,
			title: "当前角色用户树",
			content: $("#roleUserTreeDiv"),
			success:function(){
				var data;
				var ajax = new $ax(Hussar.ctxPath + "/orgTreeDemo/roleUserTree",function(result) {
					OrgTreeDemo.initRoleUserTree(result);
				}, function(data) {
					Hussar.error("获取当前角色用户树失败！");
				});
				ajax.set("roleId", $("#roleId").val());
				ajax.start();
			}
		});
	});

	$("#userTree").click(function(){
		layerView=layer.open({
			type: 1,
			area: ['350px','500px'],
			fix: false, //不固定
			maxmin: true,
			shadeClose: false,
			shade: 0.4,
			title: "用户树",
			content: $("#userTreeDiv"),
			success:function(){
				var data;
				var ajax = new $ax(Hussar.ctxPath + "/orgTreeDemo/userTree",function(result) {
					data = result;
				}, function(data) {
					Hussar.error("获取用户树失败！");
				});
				ajax.set("treeType", 3);
				ajax.start();
				OrgTreeDemo.initUserTree(data);
			}
		});
	});

	$("#userTreeRest").click(function(){
		var $tree = $("#showUserTree");
		var ref =$tree.jstree(true);
		ref.uncheck_node(userTreeArry[0]);
		userTreeArry=[];
	})

	$("#userTreeSave").click(function(){
		var nodes = $("#showUserTree").jstree(true).get_checked();//使用get_checked方法
		if(nodes.length==0){
			layer.msg("请选择用户！");
		}else{
			layer.msg("你选中了："+userTreeArry[1]+"&nbsp;&nbsp;节点id："+userTreeArry[0])
			layer.close(layerView);
		}
	})

	$("#roleUserTreeRest").click(function(){
		var $tree = $("#showRoleUserTree");
		var ref =$tree.jstree(true);
		ref.uncheck_node(RoleUserTreeArry[0]);
		RoleUserTreeArry=[];
	})

	$("#roleUserTreeSave").click(function(){
		var nodes = $("#showRoleUserTree").jstree(true).get_checked();//使用get_checked方法
		if(nodes.length==0){
			layer.msg("请选择用户！");
		}else{
			layer.msg("你选中了："+RoleUserTreeArry[0]+"&nbsp;&nbsp;节点id："+RoleUserTreeArry[1])
			layer.close(layerView);
		}
	})

	OrgTreeDemo.initOrgTreeDemo = function(data) {
		var $tree = $("#showOrgTree");
		$tree.jstree({
			core: {
				data: data
			},
			plugins: ['types'],
			types:{
				"1":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/com.png"},
				"2":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/dept.png"},
				"3":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/station.png"},
				"9":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/empl.png"}
			}
		});
		$tree.bind('activate_node.jstree', function (obj,e){
			layer.msg("你点击了："+e.node.original.text+"&nbsp;&nbsp;节点id："+e.node.original.id)
		})
	}

	OrgTreeDemo.initEmployeeTree = function(data) {
		var $tree = $("#showEmployeeTree");
		$tree.jstree({
			core: {
				data: data
			},
			plugins: ['types'],
			types:{
				"1":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/com.png"},
				"2":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/dept.png"},
				"3":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/station.png"},
				"9":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/empl.png"}
			}
		});
		$tree.bind('activate_node.jstree', function (obj,e){
			layer.msg("你点击了："+e.node.original.text+"&nbsp;&nbsp;节点id："+e.node.original.id)
		})
	}

	OrgTreeDemo.initRoleUserTree = function(data) {
		var $tree = $("#showRoleUserTree");
		$tree.jstree({
			core: {
				data: null
			},
			plugins: ['types','checkbox'],
			checkbox: {
				keep_selected_style : false,
				three_state : true,
				tie_selection : false
			},
			types:{
				"1":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/com.png"},
				"2":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/dept.png"},
				"3":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/station.png"},
				"9":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/empl.png"},
				"USER":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/user.png"},
			}
		});
		$tree.jstree(true).settings.core.data=data;
		$tree.jstree(true).refresh();
		$tree.bind('activate_node.jstree', function (obj,e){
			OrgTreeDemo.getCheckedId($tree);
			//layer.msg("你选中了："+OrgTreeDemo.getCheckedId($tree)[1]+"&nbsp;&nbsp;节点id："+OrgTreeDemo.getCheckedId($tree)[0])
		})
	}

	OrgTreeDemo.initUserTree = function(data) {
		var $tree = $("#showUserTree");
		$tree.jstree({
			core: {
				data: data
			},
			plugins: ['types','checkbox'],
			checkbox: {
				keep_selected_style : false,
				three_state : true,
				tie_selection : false
			},
			types:{
				"1":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/com.png"},
				"2":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/dept.png"},
				"3":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/station.png"},
				"9":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/empl.png"},
				"USER":{'icon' : Hussar.ctxPath+"/static/assets/img/treeContext/" +theme +"/user.png"}
			}
		});
		$tree.bind('activate_node.jstree', function (obj,e){
			var ref =$tree.jstree(true);
			if(e.node.type=="USER"){
				var nodes = ref.get_checked(); //使用get_checked方法
				if(nodes.length>1){
					$.each(nodes, function(i, nd) {
						if(nd != e.node.id){
							otherNode = nd;
						}
					})
					ref.uncheck_node(otherNode);
				}
				OrgTreeDemo.getUserCheckedId($tree);
				//layer.msg("你选中了："+OrgTreeDemo.getUserCheckedId($tree)[1]+"&nbsp;&nbsp;节点id："+OrgTreeDemo.getUserCheckedId($tree)[0])
			}else{
				ref.uncheck_node(e.node.id);
				return;
			}
		})
	}

	/**
	 * 获取选中资源id
	 */
	OrgTreeDemo.getCheckedId = function (tree) {
		var ref = tree.jstree(true);
		var nodes = ref.get_checked(true);  //使用get_checked方法
		var nds =[];
		var names='';
		$.each(nodes, function(i, nd) {
			var type=nd.original.type;
			if(type=="USER"){
				nds.push(nd.id);
				names +=","+nd.text
			}
		});
		RoleUserTreeArry=[];
		RoleUserTreeArry.push(nds);
		RoleUserTreeArry.push(names.substring(1));
		return RoleUserTreeArry;
	}

	/**
	 * 获取选中资源id
	 */
	OrgTreeDemo.getUserCheckedId = function (tree) {
		var ref = tree.jstree(true);
		var nodes = ref.get_checked(true);  //使用get_checked方法
		var nds =[];
		var names='';
		$.each(nodes, function(i, nd) {
			var type=nd.original.type;
			if(type=="USER"){
				nds.push(nd.id);
				names +=","+nd.text
			}
		});
		userTreeArry=[];
		userTreeArry.push(nds);
		userTreeArry.push(names.substring(1));
		return userTreeArry;
	}
});