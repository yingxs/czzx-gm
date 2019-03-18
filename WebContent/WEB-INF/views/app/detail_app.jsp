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
<title>应用管理</title>

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
body{
height: 400px;
}
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
	vertical-align: top;
}
.submit-btn {
	display: inline-block;
	width: 52px;
	height:28px;
	text-decoration: none;
	background-color: green;
	text-align: center;
	color: white;
	line-height: 28px;
}
</style>

</head>
<body>
<div>
	<form id="editfm" method="post" enctype="multipart/form-data">
			<div class="fitem">
				<label>应用名称:</label> <input name="taiName" id="taiName"
					class="easyui-textbox" style="width: 171px;"
					data-options="prompt:'请输入应用名称'" value="${applicatonInfo.taiName }" />
			</div>
			<div class="fitem">
				<label>应用地址:</label> <input name="taiWebAddr" id="taiWebAddr"
					class="easyui-textbox" style="width: 171px;"
					data-options="prompt:'请输入应用地址'" value="${applicatonInfo.taiWebAddr}"/>
			</div>
			<div class="fitem">
				<label>应用小图标:</label> <img <c:if test="${applicatonInfo.taiIcon!=null}">src="${ctx}upload/${applicatonInfo.taiIcon}</c:if> " id="apImg" width="120" height="120"
					class="border apImg" />
					
				<!-- <button class="adBtn">浏览</button> -->
				<input type="file" class="aptitude" name="fileUrl" id="fileUrl"
					accept="image/*" data-required="true"
					data-descriptions="detail_drawing" /> <input type="hidden"
					id="taPicture" class="diUrl" value="" />
					<!-- <span style="color: red">(图标尺寸:120*120)</span> -->
			</div>

			<div class="fitem">
				<label>应用介绍:</label><br/><br/>
				 <%-- <textarea name="taContent" id="taContent" cols="100" rows="8" readonly="readonly" style="width: 700px; height: 300px;">${applicatonInfo.taContent}</textarea> --%> 
				 
				<label style="width: 700px; height: 300px;">${applicatonInfo.taContent}</label>
			</div>
		<%-- 	<input type="hidden" name="action" id="hidtype" />
			<div id="editdlg-buttons" style="position:relative; bottom: -20px;">
				<a href="javascript:void(0)" class="submit-btn"
					onclick="update(${applicatonInfo.taiId })" >提交</a>
			</div> --%>
		</form>
	</div>

	<script type="text/javascript">
		
	</script>
</body>
</html>
