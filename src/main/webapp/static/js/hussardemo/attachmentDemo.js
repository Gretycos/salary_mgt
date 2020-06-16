/**
 * @Description: 附件典管理Demo脚本文件
 * @Author: chenxin
 * @Date: 2018/05/30.
 */

layui.use(['upload','element','Hussar'], function(){
  var $ = layui.jquery
  ,upload = layui.upload,element = layui.element
  ,Hussar = layui.Hussar;
 
  
  //普通图片上传
  var uploadInst = upload.render({
    elem: '#test1'
    ,url: Hussar.ctxPath+'/attachmentDemo/uploadfilewithdrag'
      ,data: {
        businessId: '123234'
      }
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
    	  $('#demo1').attr('src', result);
    	//$('#demo1').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" height="200" width="200">')
      });
    }
    ,done: function(res, index, upload){
      //如果上传失败
      if(res.code > 0){
        return layer.msg('上传失败');
      }
      //上传成功
      layer.msg("上传成功！");
      var demoText = $('#demoText');
      demoText.html('<a class="layui-btn layui-btn-mini demo-reload" href="'+Hussar.ctxPath+'/attachmentDemo/fileDownload?fileId='+res.id+'">下载</a>');
    }
    ,error: function(){
      //演示失败状态，并实现重传
      var demoText = $('#demoText');
      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
      demoText.find('.demo-reload').on('click', function(){
        uploadInst.upload();
      });
    }
    ,accept: 'images'//允许上传的文件类型
  });
  
  //多图片上传
  upload.render({
    elem: '#test2'
    ,url:Hussar.ctxPath+ '/attachmentDemo/uploadfilewithdrag'
    ,multiple: true
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
      obj.preview(function(index, file, result){
        $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" height="200" width="200">')
      });
    }
    ,allDone: function(obj){
      //上传完毕
      layer.msg("上传成功个数："+obj.successful+"<br>上传失败个数："+obj.aborted);
    }
    ,accept: 'images'//允许上传的文件类型
  });
  
  //选完文件后不自动上传
  upload.render({
    elem: '#test8'
    ,url:Hussar.ctxPath+ '/attachmentDemo/uploadfilewithdrag'
    ,auto: false
    ,multiple: true
    ,bindAction: '#test9'
	,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		/*  layer.load(1); //上传loading  */
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '516px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: '<div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="proBar"><div class="layui-progress-bar layui-bg-blue" lay-percent="0%"></div></div>'
			});
	      var n = 0, timer = setInterval(function(){
	          n = n + Math.random()*10|0;  
	          if(n>100){
	            n = 100;
	            clearInterval(timer);
	          }
	          element.progress('proBar', n+'%');
			}, 300+Math.random()*1000);
	}	
    ,done: function(res){
    	element.progress('proBar', '100%');//给进度条一个任意。尽量大一点
		console.log(res)
      	layer.closeAll(); //关闭loading 
		layer.msg("上传成功！",{time:1000});
		var demoText1 = $('#demoText1');
	    demoText1.append('<a class="layui-btn layui-btn-mini demo-reload" href="'+Hussar.ctxPath+'/attachmentDemo/fileDownload?fileId='+res.id+'">下载</a>');
    }
	,accept: 'file'//允许上传的文件类型
  });
  
  upload.render({
	    elem: '#test10'
	    ,url: Hussar.ctxPath+'/attachmentDemo/uploadfilewithdrag'
	    ,auto: false
	    ,multiple: true
	    ,bindAction: '#test11'
		,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
			layer.load(3); //上传loading  
			/* layer.msg('文件上传中', {
				  icon: 16
				  ,shade: 0.01
				}); */
		}
	    ,done: function(res){
	    	
			console.log(res)
	      	layer.closeAll(); //关闭loading 
			layer.msg("上传成功！",{time:1000});
	    }
		,accept: 'file'//允许上传的文件类型
	  });
  
  //多文件列表示例
  var demoListView = $('#demoList')
  ,uploadListIns = upload.render({
    elem: '#testList'
    ,url: Hussar.ctxPath+'/attachmentDemo/uploadfilewithdrag'
    ,accept: 'file'
    ,multiple: true
    ,auto: false
    ,bindAction: '#testListAction'
    ,choose: function(obj){   
      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
      //读取本地文件
      obj.preview(function(index, file, result){
        var tr = $(['<tr id="upload-'+ index +'">'
          ,'<td>'+ file.name +'</td>'
          ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
          ,'<td>等待上传</td>'
          ,'<td>'
            ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
            ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
          ,'</td>'
        ,'</tr>'].join(''));
        
        //单个重传
        tr.find('.demo-reload').on('click', function(){
          obj.upload(index, file);
        });
        
        //删除
        tr.find('.demo-delete').on('click', function(){
          delete files[index]; //删除对应的文件
          tr.remove();
          uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        });
        
        demoListView.append(tr);
      });
    }
    ,done: function(res, index, upload){
     
        var tr = demoListView.find('tr#upload-'+ index)
        ,tds = tr.children();
        tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
        tds.eq(3).html(''); //清空操作
        return delete this.files[index]; //删除文件队列已经上传成功的文件
     
    }
    ,error: function(index, upload){
      var tr = demoListView.find('tr#upload-'+ index)
      ,tds = tr.children();
      tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
      tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
    }
  });
  
});