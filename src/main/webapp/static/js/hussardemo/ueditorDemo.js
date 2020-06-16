/**
 * Created by LiangDong on 2018/5/29.
 */

/**
 * @Description: 定义工作日历Demo脚本文件
 * @Author: liangdong
 * @Date: 2018/5/28.
 */
layui.use([ 'jquery','ueditorConfig','ueditor','element' ], function() {
	var $ = layui.jquery;

	var Ueditor = {
		id: "editor",	//divId
		ue:"",
		layerIndex: -1
	};

	// 建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	Ueditor.initEditor = function(){
		Ueditor.ue = UE.getEditor('editor');
	}

	// 初始化页面按钮事件<示例>
	Ueditor.initButtonEvents = function(){
		/*
		 * 获取整个html的内容
		 */
		$("#getAllHtml").click(function(){
			alert(Ueditor.ue.getAllHtml());
			return false;
		});

		/**
		 * 获得编辑框内容
		 */
		$("#getContent").click(function(){
			var arr = [];
			arr.push("使用editor.getContent()方法可以获得编辑器的内容");
			arr.push("内容为：");
			arr.push(Ueditor.ue.getContent());
			alert(arr.join("\n"));
			return false;
		});

		/**
		 * 写入内容
		 */
		$("#setContent").click(function(){
			var arr = [];
			arr.push("使用editor.setContent('欢迎使用轻骑兵开发平台  V8 ')方法可以设置编辑器的内容");
			Ueditor.ue.setContent('欢迎使用轻骑兵开发平台  V8 ', false);
			alert(arr.join("\n"));
			return false;
		});

		/**
		 * 追加写入内容
		 */
		$("#appendContent").click(function(){
			var arr = [];
			arr.push("使用editor.setContent('欢迎使用轻骑兵开发平台  V8 ')方法可以设置编辑器的内容");
			Ueditor.ue.setContent('欢迎使用轻骑兵开发平台  V8 ', true);
			alert(arr.join("\n"));
			return false;
		});

		/**
		 * 以获得编辑器的纯文本内容
		 */
		$("#getContentTxt").click(function(){
			var arr = [];
			arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
			arr.push("编辑器的纯文本内容为：");
			arr.push(Ueditor.ue.getContentTxt());
			alert(arr.join("\n"));
			return false;
		});

		/**
		 * 获得带格式的纯文本
		 */
		$("#getPlainTxt").click(function(){
			var arr = [];
			arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
			arr.push("内容为：");
			arr.push(Ueditor.ue.getPlainTxt());
			alert(arr.join('\n'));
			return false;
		});

		/**
		 * 判断编辑器里是否有内容
		 */
		$("#hasContent").click(function(){
			var arr = [];
			arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
			arr.push("判断结果为：");
			arr.push(Ueditor.ue.hasContents());
			alert(arr.join("\n"));
			return false;
		});

		/**
		 * 使编辑器获得焦点
		 */
		$("#setFocus").click(function(){
			Ueditor.ue.focus();
			return false;
		});

		/**
		 * 编辑器是否获得焦点
		 */
		$("#isFocus").mouseover(function(e){
			alert(Ueditor.ue.isFocus());
			UE.dom.domUtils.preventDefault(e);
			return false;
		});

		/**
		 * 编辑器失去焦点
		 */
		$("#setblur").mouseover(function(e){
			Ueditor.ue.blur();
			UE.dom.domUtils.preventDefault(e);
			return false;
		});

		/**
		 * 获得当前选中的文本
		 */
		$("#getText").click(function(e){
			// 当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
			var range = Ueditor.ue.selection.getRange();
			range.select();
			var txt = Ueditor.ue.selection.getText();
			alert(txt);
			return false;
		});

		/**
		 * 插入给定的内容
		 */
		$("#insertHtml").click(function(e){
			var value = prompt('插入html代码', '');
			Ueditor.ue.execCommand('insertHtml', value);
			return false;
		});

		/**
		 * 隐藏编辑器
		 */
		$("#setHide").click(function(e){
			Ueditor.ue.setHide();
			return false;
		});

		/**
		 * 显示编辑器
		 */
		$("#setShow").click(function(e){
			Ueditor.ue.setShow();
			return false;
		});


		/**
		 * 设置高度为300
		 */
		$("#setHeight").click(function(e){
			Ueditor.ue.setHeight(300);
			return false;
		});


		/**
		 * 获取草稿箱内容
		 */
		$("#getLocalData").click(function(e){
			alert(Ueditor.ue.execCommand("getlocaldata"));
			return false;
		});

		/**
		 * 清空草稿箱
		 */
		$("#clearLocalData").click(function(e){
			Ueditor.ue.execCommand("clearLocalData");
			alert("已清空草稿箱");
			return false;
		});

	}

	$(function () {
		Ueditor.initEditor();   //初始化UEditor
		Ueditor.initButtonEvents();   //初始化按钮事件
	});
});


