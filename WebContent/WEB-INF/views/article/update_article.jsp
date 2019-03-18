<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String ctx = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
	request.setAttribute("ctx", ctx);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改文章</title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui1.4.2/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui1.4.2/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui1.4.2/demo.css" />
<script type="text/javascript"
	src="${ctx}/static/easyui1.4.2/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui1.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui1.4.2/locale/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet"
	href="${ctx}static/kindeditor/themes/default/default.css" />
<link rel="stylesheet"
	href="${ctx}static/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="${ctx}static/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ctx}static/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8"
	src="${ctx}static/kindeditor/plugins/code/prettify.js"></script>
<script>
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="taContent"]', {
		cssPath : '${ctx}static/kindeditor/plugins/code/prettify.css',
		uploadJson : '${ctx}static/kindeditor/jsp/upload_json.jsp',
		allowFileManager : true,
		afterBlur : function() {
			var self = this;
			self.sync();
		}
	});
	prettyPrint();
});
</script>
<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
	vertical-align: top;
}

.adBtn {
	width: 56px;
	height: 30px;
	border: 1px solid #ccc;
	background: green;
	color: #fff;
}

.box .myfiles {
	width: 69px;
	height: 28px;
	margin-left: -74px;
	opacity: 0;
}

.aptitude {
	width: 69px;
	height: 28px;
	margin-left: -74px;
	opacity: 0;
}
.submit-btn {
	display: inline-block;
	width: 76px;
	height:28px;
	text-decoration: none;
	background-color: green;
	text-align: center;
	color: white;
	border-radius: 3px;
	line-height: 28px;
}
.cancel {
	display: inline-block;
	width: 52px;
	height:26px;
	text-decoration: none;
	background-color: white;
	border:1px #a9a9a9 solid;
	text-align: center;
	color: #333;
	line-height: 26px;
}
.submit-btn:hover {
	color : white;
}
.cancel:hover {
	color: #333;
}
.fitem label + div {
	display: inline-block;
	vertical-align: top;
}
/* #preview{width:260px;height:190px;border:1px solid #000;overflow:hidden;} */
</style>

</head>
<body>
<div>
		<div class="panel-header">
			<div class="panel-title">编辑</div>
			<div class="panel-tool"></div>
		</div>
	<div style="margin: 10px;">
	<form id="editfm" method="post" enctype="multipart/form-data">
		<div class="fitem">
			<label>文章标题:</label> <input name="taTitle" id="up_taTitle"
				class="easyui-textbox" style="width: 171px;"
				data-options="prompt:'请输入文章标题'" value="${tbArticle.taTitle }"/><span style="color: red">*</span>
		</div>
		<div class="fitem">
			<label>文章分类:</label> 
			<select id="taColumnIds" class="easyui-combobox" name="taColumnIds" style="width: 175px;" data-options="prompt:'请选择文章分类'" >
			</select><span style="color: red">*</span>
			<input type="hidden" id = "taColumnId" name = "taColumnId" />
			<input type="hidden" id = "taColumnId_" value="${tbColumn.tcId}"/>
			<input type="hidden" id = "taColumnId_str" value="${tbColumn.tcName}"/>
		</div>
		<div class="fitem">
			<label>封面图片:</label> 
			<div id="preview" style="height:140px;">
				<img <c:if test="${tbArticle.taPicture!=null}">src="${tbArticle.taPicture}</c:if> " id="up_apImg" width="185"
				height="130" class="border apImg" style="position: relative; top:10px"/>
			</div>
			<button style="margin-top: 110px" class="adBtn">浏览</button>
			<input type="file" class="aptitude" name="fileUrl" id="up_fileUrl"
				accept="image/*" data-required="true" onchange="previewImage(this,'')"
				data-descriptions="detail_drawing" /> 
		</div>

		<div class="fitem">
			<label>详细内容:</label>
			<textarea name="taContent" id="up_taContent" style="width: 600px; height: 480px; visibility: hidden;">${tbArticle.taContent }</textarea>
		</div>
		<div class="fitem" style="">
			<input style="vertical-align: sub;" type="checkbox" name="taTop" value="1"<c:if test="${tbArticle.taTop==1}">checked</c:if> />置顶&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input style="vertical-align: sub;" type="checkbox" name="taHeadline" value="1" <c:if test="${tbArticle.taHeadline==1}">checked</c:if>/>设为校园头条
		</div>
		<hr style="border:1px #eee solid;margin-top: 10px;"></hr>
		<input type="hidden" name="action" id="edit_hidtype" />
		 <input type="hidden" id="edit_id" />
		<div id="editdlg-buttons" style="">
		<a href="javascript:void(0)" class="submit-btn"
			onclick="update(${tbArticle.taId})">提交</a>
	</div>
	</form>
	</div>
	</div>

	<script type="text/javascript">
	
	$('#taColumnIds').combobox({
        valueField:'tcId', //值字段
        textField:'tcName', //显示的字段
        url:'${ctx}/admin/column/getColumn.json',
        width : "170px",
        editable:true,//不可编辑，只能选择
        value:'${tbColumn.tcName}'
	})
		
		//提交修改
		function update(id) {
			var taTitle = $("#up_taTitle").textbox('getValue');
			var taColumnIds = $("#taColumnIds").combobox('getValue');
			var taColumnId_str = $("#taColumnId_str").val();
			var taColumnId_ = $("#taColumnId_").val();
			
			if(taColumnIds == taColumnId_str){
				$("#taColumnId").val(taColumnId_);
			}else{
				$("#taColumnId").val(taColumnIds);
			}
			
			
			var taContent = $("#up_taContent").val();
			var filename = $("#up_fileUrl").val();
			if(isEmpty(taTitle)){
				$.messager.alert("提示信息", "请输入文章标题");
				return false;
			}
			if(taTitle.length>50){
				$.messager.alert("提示信息", "请输入少于50字的文章标题");
				return false;
			}
			if(isEmpty(taColumnId)){
				$.messager.alert("提示信息", "请选择所属分类");
				return false;
			}
			if(isEmpty(taContent)){
				$.messager.alert("提示信息", "请输入详细内容");
				return false;
			}
			if(isEmpty(filename)==false){
				if(!/\.(jpg|png|jpeg|JPG|PNG|JPEG)$/.test(filename)){
					$.messager.alert("提示信息","请上传格式为JPG、JPEG、PNG的图片");
					return false;
				}
			}
			$("#editfm").form("submit", {
				url : '${ctx}/admin/article/update/'+id+'.htm?',
				onsubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					if (result == "1") {
						location.href="${ctx}/admin/article/init_page.htm";
					} else {
						$.messager.alert("提示信息", "操作失败");
					}
				}
			});
			
		}
		
		/**
		 * 判断是否为空
		 * 
		 * @param str
		 * @returns {Boolean}
		 */
		function isEmpty(str){
			if(str.length<=0 || str == null){
				return true ;
			}
			return false ;
		}
		//图片上传预览，IE用了滤镜
		 function previewImage(file,indexs)
		 {
		 var MAXWIDTH  = 260;
		 var MAXHEIGHT = 180;
		 var div = document.getElementById('preview'+indexs);
		 if (file.files && file.files[0])
		 {
		     div.innerHTML ='<img id=up_apImg'+indexs+'>';
		     var img = document.getElementById('up_apImg'+indexs);
		     img.onload = function(){
		       var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
		       img.width  =  rect.width;
		       img.height =  rect.height;
		       img.style.marginTop = rect.top+'px';
		     }
		     var reader = new FileReader();
		     reader.onload = function(evt){img.src = evt.target.result;}
		     reader.readAsDataURL(file.files[0]);
		 }
		 else //兼容IE
		 {
		   var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
		   file.select();
		   var src = document.selection.createRange().text;
		   div.innerHTML = '<img id=up_apImg'+indexs+'>';
		   var img = document.getElementById('up_apImg'+indexs);
		   img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		   var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
		   status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
		   div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
		 }
		 }
		 function clacImgZoomParam( maxWidth, maxHeight, width, height ){
		   var param = {top:0, left:0, width:width, height:height};
		   if( width>maxWidth || height>maxHeight )
		   {
		       rateWidth = width / maxWidth;
		       rateHeight = height / maxHeight;
		       if( rateWidth > rateHeight )
		       {
		           param.width =  maxWidth;
		           param.height = Math.round(height / rateWidth);
		       }else
		       {
		           param.width = Math.round(width / rateHeight);
		           param.height = maxHeight;
		       }
		   }
		   param.left = Math.round((maxWidth - param.width) / 2);
		   param.top = Math.round((maxHeight - param.height) / 2);
		   return param;
		 }
	</script>
</body>
</html>
