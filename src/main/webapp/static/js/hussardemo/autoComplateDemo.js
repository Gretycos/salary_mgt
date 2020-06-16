/**
 * @Description: 定义联想Demo脚本文件
 * @Author: ChenChang
 * @Date: 2018/6/6.
 */
layui.use(['Hussar','element','jqueryui'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;

    $("#userName").autocomplete({
        source: Hussar.ctxPath + "/autocomplete/auto",
        select: function(event, ui){
            // 这里的this指向当前输入框的DOM元素
            // event参数是事件对象
            // ui对象只有一个item属性，对应数据源中被选中的对象
            $("#userName").val( ui.item.label );
            // 必须阻止事件的默认行为，否则autocomplete默认会把ui.item.value设为输入框的value值
            event.preventDefault();
        }
    });


});




