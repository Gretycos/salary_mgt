/**
 * Created by LiangDong on 2018/6/4.
 */

layui.use(['jquery','layer','Hussar','jstree','HussarAjax','form', 'laydate', 'element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;
    var editMap={};
    var addMap={};
    var delMap={};
    var del=[];

    var EditTable = {
        id: "ruleTable",	//表格id
        seItem: null,		//选中的条目
        table: $("#ruleTable"),
        layerIndex: -1,
        yes_no: []
    };


    EditTable.initColumn = function () {
        var columns;
        columns = [
            [
                {
                    title: '用户信息',
                    align: 'center',
                    halign: 'center',
                    colspan: 10,
                }
            ],
            [
                {checkbox: true},
                {   
                	title: '序号',
                    align: 'center',
                    formatter: function (value, row, index) {
                        //return index+1; //序号正序排序从1开始
                        var pageSize=$('#ruleTable').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                        var pageNumber=$('#ruleTable').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                        return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
                    }
                },
                {   field: 'userId',
                    title: '用户账号',
                    align: 'center',
                    visible:false
                    
                },
                {
                    field: 'userName',
                    title: '用户名称',
                    align: 'center',
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    },
                    editable: {
                    	mode:'inline',
                        type: 'text',
                        showbuttons:false,
                        validate: function (v) {
                            if (!v) return '用户名称不能为空';
                        }
                    }
                },
                {
                    field: 'mobile',
                    title: '手机',
                    align: 'center',
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    },
                    editable: {
                    	mode:'inline',
                    	showbuttons:false,
                        type: 'text',
                        validate: function (v) {
                            if (!v) return '手机号不能为空';
                        }
                    }

                },
                {
                    field: 'isSys',
                    title: '是否管理员',
                    align: 'center',
                    formatter: function (value, row, index) {
                        if(value==undefined){
                            return "";
                        }
                        else{
                            return value;
                        }
                    },
                    editable: {
                    	mode:'inline',
                    	showbuttons:false,
                        type: 'select',
                        source: EditTable.yes_no
                    }

                },
                {   field: 'eMail',
                    title: '邮箱',
                    align: 'center',
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    },
                    editable: {
                    	mode:'inline',
                    	showbuttons:false,
                        type: 'text',
                        validate: function (v) {
                            if (!v) return '邮箱不能为空';
                        }
                    },
                },
                {   field: 'userOrder',
                	title: '排序',
                	align: 'center',
                	editable: {
                		mode:'inline',
                    	showbuttons:false,
                        type: 'text',
                        validate: function (v) {
                            if (!v) return '排序不能为空';
                        }
                    },
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    }
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    align: 'center',
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    },
                    editable: {
                        showbuttons:false,
                        type: 'datetime',
                        format: 'yyyy-mm-dd hh:ii:ss',
                        placement: 'left',
                        clear: '清除',
                        datetimepicker: {
                            format: 'yyyy-mm-dd hh:ii:ss',
                        }
                    }
                }/*,
                {
                    title: '操作列',
                    align: 'center',
                    //
                    formatter: function (value, row, index) {
                    	return '<button type="button" class="layui-btn  layui-btn-xs" onclick=delButton(\"'+row.userId+'\") ><i class="layui-icon">&#xe640;</i>删除</button>'
                    }

                }*/
            ]


        ];
        return columns;
    };

    EditTable.initTableView = function () {
        var self = this;
        self.table.bootstrapTable({
            url: Hussar.ctxPath+'/bootstrapEditTable/list',			//请求地址
            method: "post",	//ajax方式,post还是get
            toolbar: "#ruleTableToolbar" ,//顶部工具条
            striped: true,     			//是否显示行间隔色
            cache: false,      			//是否使用缓存,默认为true
            sortable: true,      		//是否启用排序
            sortOrder: "desc",     		//排序方式
            pageNumber: 1,      			//初始化加载第一页，默认第一页
            pageSize: 10,      			//每页的记录行数（*）
            pageList: [10, 20, 50, 100],  	//可供选择的每页的行数（*）
            queryParamsType: EditTable.queryParams(), 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            search: false,      		//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
            strictSearch: true,			//设置为 true启用 全匹配搜索，否则为模糊搜索
            showColumns: false,     		//是否显示所有的列
            showRefresh: false,     		//是否显示刷新按钮
            minimumCountColumns: 2,    	//最少允许的列数
            clickToSelect: true,    	//是否启用点击选中行
            searchOnEnterKey: true,		//设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
            columns: EditTable.initColumn(),		//列数组
            pagination: true,			//是否显示分页条
            fixedColumns: true,         //是否进行列的冻结
            fixedNumber: +4,            //冻结几列
            uniqueId: 'userId',
            height: $("body").height() - $(".layui-form").outerHeight(true)-80,
            icons: {
                refresh: 'glyphicon-repeat',
                toggle: 'glyphicon-list-alt',
                columns: 'glyphicon-list'
            },
            iconSize: 'outline',
            onEditableSave: function (field, row, oldValue, $el) {
            	var id=row.userId;
            	if(id.split("-")[0]=='HUSSAR'){
            		modifiedAddValue = addMap[row.userId] || {};
            		modifiedAddValue[field] = row[field];
            		addMap[row.userId] = modifiedAddValue;
            		//layer.msg(JSON.stringify(addMap))
            	}else{
        			modifiedEditValue = editMap[row.userId] || {};
        			modifiedEditValue[field] = row[field];
            		editMap[row.userId] = modifiedEditValue;
            		//layer.msg(JSON.stringify(editMap))
            	}
            	layer.msg("修改字段："+field+"<br>修改前的数据："+oldValue+"<br>修改后的数据："+row[field])
            }

        });
    }

    EditTable.queryParams = function() {
        var userAccount =$('#userAccount').val();
        var userName =$('#userName').val();
        var temp = {
            pageSize: this.pageSize,   //页面大小
            pageNumber: this.pageNumber, //页码
            query:{
                userAccount: userAccount,
                userName: userName
            }
        };
        return temp;
    }

    EditTable.initButtonEvents = function () {
        //	查询按钮事件
        $("#btnSearch").click(function(){
            $('#ruleTable').bootstrapTable('refresh', {
                query: EditTable.queryParams()
            });
        });
        $("#getCheckLength").click(function(){
        	var rows = $("#ruleTable").bootstrapTable('getSelections');
        	layer.msg('选中了：'+ rows.length + ' 个');
        });
        $("#getCheckData").click(function(){
        	var rows = $("#ruleTable").bootstrapTable('getSelections');
        	if(rows.length==0){
        		layer.msg('请至少选择一条数据');
        	}else{
        		layer.msg(JSON.stringify(rows));
        	}
        });
        $('#addRowbtn').click(function(){
        	function S4(){
        		return (((1+Math.random())*0x10000)|0).toString(16).substring(1);  
        	}
        	newId=S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4();
            var data = {'userId':'HUSSAR-'+newId};
            $('#ruleTable').bootstrapTable('append',data);    
        });
        $('#saveRowbtn').click(function(){
        	var date=[];
        	date.push(addMap);
        	date.push(delMap);
        	date.push(editMap);
        	layer.msg(JSON.stringify(date))
        });
    	$("#delRowbtn").click(function() {
    		var rows = $('#ruleTable') .bootstrapTable('getAllSelections');
            var ids = [];
            if(rows.length == 0){
            	layer.msg("请至少选择一条记录！")
            }else{
                var operation = function () {
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.userId);
                    }
                    var eId = ids.join(',');
                    layer.msg(eId)
                };
            	Hussar.confirm("确定要删除吗？",operation);
            }
    	});
                
    };
    

    

/*    delButton=function(id){
    	if(id.split("-")[0]=='HUSSAR'){
    	    delete addMap[id];
    	}else{
    		delete editMap[id];
    		del.push(id);
    		delMap['id']=del;
    		layer.msg(JSON.stringify(delMap))
    	}
    	$('#ruleTable').bootstrapTable('removeByUniqueId',id);          
    };*/
    
    EditTable.initSelectOption = function () {
            var dicType = "yes_no";
            $.ajax({
                type: "POST",
                data:{
                    dicType:dicType
                },
                contentType:"application/x-www-form-urlencoded",
                url: Hussar.ctxPath+"/dataDicDemo/listData",
                dataType:"json",
                async: false,
                success:function(data) {
                    $.each(data, function (key, option) {
                        EditTable.yes_no.push({ value: option.VALUE, text: option.LABEL });
                    });
                }
            });

    };


    $(function () {
        EditTable.initSelectOption();   //初始化下拉框option(列表编辑用)
        EditTable.initTableView();  //初始化表格
        EditTable.initButtonEvents();   //初始化按钮事件
    });

});
