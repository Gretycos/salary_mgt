/**
 * @Description: 可编辑列表Demo脚本文件
 * @Author: ChenXin
 * @Date: 2018/05/30.
 */
layui.use(['jquery','layer','Hussar','HussarAjax','table','element'], function(){
    var Hussar = layui.Hussar;
    var $ = layui.jquery;
    var layer = layui.layer;
    var $ax = layui.HussarAjax;
    var table = layui.table;
    var element = layui.element;
    var arr = [];
    var arrary={};
    
		table.render({
			elem : '#typeTable',
			url : Hussar.ctxPath+'/orgType/list',
			cols : [ [ 
			           {type : 'checkbox',width : 40}, 
			           {title : '序号',type : 'numbers',align : 'center',width:50}, 
			           {field : 'ORGANTYPE',title : '组织类型代码',width : 200,align : 'center'}, 
			           {field : 'TYPENAME',title : '组织类型名称',width : 200,align : 'center',edit:'text'}, 
			           {field : 'PARENTTYPE',title : '上级组织类型',align : 'center',edit:'text'}
			           ] ],
			id : 'testReload',
			even: true
		});

		
		table.on('edit(test)', function(obj){
			var key=obj.data.ORGANTYPE,field = obj.field;
			arrary["id："+key+"&nbsp;&nbsp;field："+field]=obj.value;
		    /*var value = obj.value //得到修改后的值
		    ,data = obj.data //得到所在行所有键值
		    ,field = obj.field; //得到字段
		    */
		  });
		$("#save").click(function(){
			layer.msg(JSON.stringify(arrary));
		})
});





