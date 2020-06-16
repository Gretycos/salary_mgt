/**
 * Created by ChenXin on 2018/6/13.
 */

layui.use(['jquery','layer','Hussar','jstree','HussarAjax','form', 'laydate', 'element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;

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
                {	checkbox: true,
                	footerFormatter:'<span>合计</span>',
                	align: 'center',
                	},
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

                },
                {
                    field: 'isSys',
                    title: '是否管理员',
                    align: 'center',
                    formatter: EditTable.dictEach,

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
                },
                {   field: 'userOrder',
                	title: '排序',
                	align: 'center',
                    formatter: function (value, row, index) {
                    	if(value==undefined){
                    		return "";
                    	}
                    	else{
                    		return value;
                    	}
                    },
                    footerFormatter: function (value) {
                        var count = 0;
                        for (var i in value) {
                            count += Number(value[i].userOrder);
                        }
                        return count;
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
                    }
                }
            ]


        ];
        return columns;
    };

    EditTable.dictEach = function(value, row, index){
        for(i in isAdmin){
            if(isAdmin[i].VALUE == value){
                return isAdmin[i].LABEL;
            }
        }
    };

    EditTable.initTableView = function () {
        var self = this;
        self.table.bootstrapTable({
        	showFooter: true,
            url: Hussar.ctxPath+'/totalTableDemo/list',			//请求地址
            method: "post",	//ajax方式,post还是get
            toolbar: "#ruleTableToolbar" ,//顶部工具条
            striped: true,     			//是否显示行间隔色
            cache: false,      			//是否使用缓存,默认为true
            pagination: true,     		//是否显示分页（*）
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
            exportDataType: "basic",//导出类型
            height: $("body").height() - $(".layui-form").outerHeight(true)-80,
            icons: {
                refresh: 'glyphicon-repeat',
                toggle: 'glyphicon-list-alt',
                columns: 'glyphicon-list'
            },
            iconSize: 'outline'
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
                
    };
    
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

    }


    $(function () {
        EditTable.initSelectOption();   //初始化下拉框option(列表编辑用)
        EditTable.initTableView();  //初始化表格
        EditTable.initButtonEvents();   //初始化按钮事件
    });

});
