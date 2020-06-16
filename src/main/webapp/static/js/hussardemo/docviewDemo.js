/**
 * <p>
 * svg预览脚本文件
 * </p>
 * @author LiangDong
 * date：2019-03-12 14:13
 */
var DocviewDemo = {
    pageSize: 5    //每次加载的页数
};

layui.use(['jquery','Hussar','element','flow'], function() {
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var flow = layui.flow;


    /**
     * 文档预览懒加载
     */
    DocviewDemo.lazyDoc = function(){
        //获取当前host
        var host = window.location.protocol + '//'+window.location.host;

        flow.load({
            elem: '#viewer' //指定列表容器
            ,isAuto: false
            ,isLazyimg: true
            ,done: function(page, next){ //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get(Hussar.ctxPath + '/doc/img?page='+ page +'&size='+ DocviewDemo.pageSize, function(res){
                    layui.each(res.data, function(index, item){
                        lis.push('<img lay-src="'+ host + item +'" class="wenku-viewer-img">');
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //total为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < Math.ceil(res.total/DocviewDemo.pageSize));
                    var unread = res.total - page*DocviewDemo.pageSize;
                    $('.layui-flow-more a').html('<div class="help-block">还有<span class="wenku-unread-pages">'+unread+'</span>页可以预览，<span class="text-primary">继续阅读</span></div>');
                });
            }
            ,end: '<div class="wenku-viewer-more text-center"><div>下载到电脑，方便使用</div></div>'
        });
    };




    /**
     * 脚本入口
     */
    $(function() {
        DocviewDemo.lazyDoc();
    });

});

