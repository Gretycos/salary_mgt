/**
 * Created by ChenXin on 2019/2/13.
 */
layui.use(['jquery','Hussar','layer','ztree','element','ztree_excheck'], function(){

    var Hussar = layui.Hussar;
    var layer = layui.layer;
    var $ = layui.jquery;
    var ZTreeObj;

    var ZTree = {};
    var $zTree = $("#showOrgTree");

    var setting = {
        async: {
            enable: true,
            type: "post",
            dataType: "json",
            url: Hussar.ctxPath + "/ztreeDemo/getNodes",
            autoParam: ["id"] //异步加载时需要自动提交父节点属性的参数
        },
        data : {
            simpleData : {
                enable : true,
                idKey : "id", //定义的节点属性名
                pIdKey : "pId", //定的义父节点属性名
                rootPId : 11 //父节点id
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox", //readio
            //Y 属性定义 checkbox 被勾选后的情况；N 属性定义 checkbox 取消勾选后的情况；"p" 表示操作会影响父级节点；"s" 表示操作会影响子级节点。
            chkboxType: { "Y": "ps", "N": "ps" }
            //radioType: "level" //区分大小写!!!! "level" 时，在每一级节点范围内当做一个分组, "all" 时，在整棵树范围内当做一个分组。
        },
        callback: {
             /**
              * TODO 用于捕获异步加载出现异常错误的事件回调函数
              * event js event 对象
              * treeId 对应 zTree 的 treeId，便于用户操控
              * treeNode 进行异步加载的父节点 JSON 数据对象，针对根进行异步加载时，treeNode = null
              * XMLHttpRequest 标准 XMLHttpRequest 对象
              * textStatus 请求状态：success，error
              * errorThrown errorThrown 只有当异常发生时才会被传递
              */
            onAsyncError: function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {

            },
             /**
              * TODO 用于捕获异步加载正常结束的事件回调函数，注意，为异步加载时，展开节点也会触发
              * treeNode 进行异步加载的父节点 JSON 数据对象
              * msg 异步获取的节点数据字符串
              */
            onAsyncSuccess: function (event, treeId, treeNode, msg) {

            },
             /**
              * TODO 点击事件
              */
            onClick: function (event, treeId, treeNode) {
                layer.msg("节点id："+treeNode.id+"</br>节点名称："+treeNode.name)
            },
             /**
              * TODO 右键事件
              */
            onRightClick: function (event, treeId, treeNode){
                if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                    ZTreeObj.cancelSelectedNode();
                    ZTree.showRMenu("treeNode", event.clientX, event.clientY);
                } else if (treeNode && !treeNode.noR) {
                    ZTreeObj.selectNode(treeNode);
                    ZTree.showRMenu(treeNode.id, event.clientX, event.clientY);
                }
            },
            /**
             * TODO 节点展开事件
             */
            onExpand: function (event, treeId, treeNode) {
                layer.msg("您展开了</br>节点名称："+treeNode.name)
            },
            /**
             * TODO 节点折叠事件
             */
            onCollapse: function (event, treeId, treeNode) {
                layer.msg("您折叠了</br>节点名称："+treeNode.name)
            }
        }
    };

    ZTree.showRMenu = function (type, x, y) {
        //右键不是节点时不显示菜单
        if (type == "treeNode") {
            $("#add").hide();
            $("#edit").hide();
            $("#del").hide();
        } else {
            $("#rMenu ul").show();
            //根节点显示的菜单
            if(type == "11"){
                $("#add").show();
                $("#edit").hide();
                $("#del").hide();
            }else{
                $("#edit").show();
                $("#add").show();
                $("#del").show();
            }
            y += document.body.scrollTop;
            x += document.body.scrollLeft;
            $("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});

            $("body").bind("mousedown", ZTree.onBodyMouseDown);
        }

    };

     /**
      * 隐藏右键菜单
      */
    ZTree.onBodyMouseDown = function(event){
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
            $("#rMenu").css({"visibility" : "hidden"});
        }
    };

    ZTree.initButton = function () {

        $("#orgTree").click(function(){
            layer.open({
                type: 1,
                area: ['350px','500px'],
                fix: false, //不固定
                maxmin: true,
                shadeClose: false,
                shade: 0.4,
                title: "组织机构zTree树",
                content: $("#orgTreeDiv")
            });
        });

        $("#add").click(function () {
            ZTree.hideRMenu();
            layer.msg("您点击了新增！");
        });

        $("#del").click(function () {
            ZTree.hideRMenu();
            layer.msg("您点击了删除！");
        });

        $("#edit").click(function () {
            ZTree.hideRMenu();
            layer.msg("您点击了修改！");
        });
        
        $("#treeSave").click(function () {
            var nodes = ZTreeObj.getCheckedNodes(true);
            var checkIds = [];
            for(node in nodes){
                if(!nodes[node].isParent){
                    checkIds.push(nodes[node].id)
                }
            }
            layer.msg("您选中的子节点id：</br>"+checkIds.join(','));
        });

        //重置
        $("#treeRest").click(function () {
            ZTreeObj.checkAllNodes(false);
        })
    };

     /**
      * 点击菜单按钮，隐藏右键菜单
      */
    ZTree.hideRMenu = function () {
        if ($("#rMenu")) $("#rMenu").css({"visibility": "hidden"});
        $("body").unbind("mousedown", ZTree.onBodyMouseDown);
    };


    $(function() {
        /**
         * zTree 初始化方法：$.fn.zTree.init(t, setting, zNodes)
         * t:用于展现 zTree 的 DOM 容器
         * setting:zTree 的配置数据
         * zNodes:zTree 的节点数据，异步加载数据时可不写
         *
         */
        $.fn.zTree.init($zTree, setting);
        ZTreeObj = $.fn.zTree.getZTreeObj("showOrgTree");
        ZTree.initButton();
    });

});