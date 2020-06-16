/**
 * @Description: 表单验证Demo脚本文件
 * @Author: ChenXin
 * @Date: 2018/06/4.
 */

layui.use(['form', 'layedit', 'laydate','element','sliderVerify'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  , element = layui.element;
  //日期
  laydate.render({
    elem: '#date'
  });
  laydate.render({
    elem: '#date1'
  });
  //滑块验证
  var sliderVerify = layui.sliderVerify,
	form = layui.form;
  var slider = sliderVerify.render({
	elem: '#slider',
	onOk: function(){//当验证通过回调
		layer.msg("滑块验证通过");
	}
  });
  //创建一个编辑器
  var editIndex = layedit.build('LAY_demo_editor');
 
  //自定义验证规则
  form.verify({
    title: function(value){
      if(value.length < 5){
        return '标题至少得5个字符';
      }
    }
    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
    ,content: function(value){
      layedit.sync(editIndex);
    }
  });
  
  //监听指定开关
  form.on('switch(switchTest)', function(data){
    layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
      offset: '6px'
    });
    layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
  });
  
  //监听提交
  form.on('submit(demo1)', function(data){
    if(slider.isOk()){
		layer.msg(JSON.stringify(data.field));
	}else{
		layer.msg("请先通过滑块验证");
	}
    layer.msg(JSON.stringify(data.field));
    return false;
  });

  
  
});
