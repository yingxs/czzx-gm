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
*{
font-size: 12px;
}
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
}
.fitem label + div {
	display: inline-block;
	vertical-align: top;
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
</style>
<script type="text/javascript">
$(function(){
	if('${tbArticle.taTop }' == "0"){
		document.getElementById("taTop").value="否";
	}else{
		document.getElementById("taTop").value="是";
	}
	
	
	if('${tbArticle.taHeadline}' == '0'){
		document.getElementById("taHeadline").value="否";
	}else{
		document.getElementById("taHeadline").value="是";
	}
	
})

</script>
</head>
<body>
<div>
	<form id="editfm" method="post" enctype="multipart/form-data">
		<div class="fitem">
			<label>文章标题:</label> 
			<label style="width:200px">${tbArticle.taTitle }</label>
		</div>
		<br/>
		<div class="fitem">
			<label>文章分类:</label> 
			<label>${tbColumn.tcName}</label>
		</div>
		<br/>
		<div class="fitem">
			<label>封面图片:</label> <img <c:if test="${tbArticle.taPicture!=null}">src="${ctx}upload/${tbArticle.taPicture}</c:if> " id="up_apImg" width="185"
				height="130" class="border apImg" style="position: relative; top:10px"/>
		</div>
		<br/>
		
		<div class="fitem">
			<label>是否置顶:</label>
			<div><input type="text" style="border-style: solid; border-width: 0" id="taTop" value="" readonly="readonly"/></div>
			<label></label>
		</div>
		<br/>
		<div class="fitem">
		<label>是否头条:</label>
		<label><input type="text" style="border-style: solid; border-width: 0" id="taHeadline" value="" readonly="readonly" /></label>
		</div>
		<br/>
		<div class="fitem">
		<label>发布时间:</label>
		<label style="width:200px;">${tbArticle.taAddtime }</label>
		</div>
		<br/>
		<div class="fitem">
		<label>浏览次数:</label>
		<label>${tbArticle.taCount}</label>
		</div>
		<br/>
		<div class="fitem">
			<label>详细内容:</label>
			<div style="width:900px">${tbArticle.taContent }</div>
		</div>
		<br/>
		<hr style="border:1px #eee solid;margin-top: 10px;"></hr>
		<input type="hidden" name="action" id="edit_hidtype" />
		 <input type="hidden" id="edit_id" />
		<div id="editdlg-buttons" style="">
	</div>
	</form>
	</div>
</body>
</html>
