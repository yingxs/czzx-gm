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
<title>栏目管理</title>

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
</style>

</head>
<body>
	<div class="panel-header">
			<div class="panel-title">编辑</div>
			<div class="panel-tool"></div>
		</div>
		<div style="margin: 10px;">
	<form id="addfm" method="post">
	
	    <input type="hidden" name="tcId" value="${tbColumn.tcId}"/>
		<div class="fitem">
			<label> 栏目名称:</label>
			<input name="tcName" id="tcName" class="easyui-textbox"  style="width:171px;" value="${tbColumn.tcName}" required="true" data-options="prompt:'请输入栏目名称'"/><span style="color: red">*</span>
		</div>
		<div class="fitem">
			<label>上级栏目:</label>
			<select id="tcParentIds" class="easyui-combobox" name="tcParentIds" style="width:170px;">
			</select>
			<input type="hidden" name="tcParentId" id="tcParentId" />
			<input type="hidden" name = "tcParentId_" id = "tcParentId_" value = "${pareId}"/>
			<input type="hidden" name = "tcParentId_str" id = "tcParentId_str" value = "${pareName}"/>
		</div>
		<div class="fitem">
			<label>栏目类型:</label>
			<select id="tcType" class="easyui-combobox" name="tcType" style="width:170px;">
				<option value="1">栏目组</option>
				<option value="2">单页</option>
				<option value="3">文章列表</option>
				<option value="4">相册列表</option>
			</select>
		</div>
		<div class="fitem">
			<label> 栏目序号:</label>
			<input name="tcIndex" id="tcIndex" class="easyui-textbox" value="${tbColumn.tcIndex}" data-options="prompt:'请输入栏目序号'" required="true"/>
		</div>
		<div class="fitem">
			<label> 栏目说明:</label> 
			<input name="tcContent" id="tcContent" class="easyui-textbox" value="${tbColumn.tcContent}"  data-options="multiline:true,prompt:'请输入栏目说明'" style="width: 170px; height: 35px"/>
		</div>
		
		<input type="hidden" name="action" id="hidtype" />
		<div id="editdlg-buttons" style="position:relative; bottom: -20px;">
			<hr style="border: 1px #eee solid;margin: 5px 0;"/>
			<a href="javascript:void(0)" class="submit-btn" onclick="update()">确认修改</a>
		</div>
	</form>
		
	<script type="text/javascript">
		$(function(){
			var type= '${tbColumn.tcType}';
			var typeName;
			if(type==1){
				typeName='栏目组';
			}else if(type==2){
				typeName='单页';
			}else if(type==3){
				typeName='文章列表';
			}else if(type==4){
				typeName='相册列表';
			}
			$('#tcType').combobox('setValue',type).combobox('setText',typeName)
		})
		//查询上级栏目
		$('#tcParentIds').combobox({
	        valueField:'tcId', //值字段
	        textField:'tcName', //显示的字段
	        url:'${ctx}/admin/column/get_first_column.json',
	        width : "170px",
	        editable:true,//不可编辑，只能选择
	        value:'${pareName}'
		})
		
		//修改
		function update() {
			var tcName = $("#tcName").textbox('getValue');
			var tcIndex = $("#tcIndex").textbox('getValue');
			var tcType = $("#tcType").combobox('getValue');
			
			var tcParentIds = $("#tcParentIds").combobox('getValue');
			var tcParentId_str = $("#tcParentId_str").val();
			var tcParentId_ = $("#tcParentId_").val();
			
			
			if (isEmpty(tcName)||tcName.length>10) {
				$.messager.alert("提示信息", "栏目名称的长度不能为空且长度不超过10个汉字，请检查");
				return false;
			}
			if (isEmpty(tcType)) {
				$.messager.alert("提示信息", "请选择栏目类型");
				return false;
			}
			if (isEmpty(tcIndex)) {
				$.messager.alert("提示信息", "请输入栏目序号");
				return false;
			}
			
			if(tcParentIds==tcParentId_str){
				$("#tcParentId").val(tcParentId_);
			}else{
				$("#tcParentId").val(tcParentIds);
			}
			
			$("#addfm").form("submit", {
				url : '${ctx}/admin/column/update.htm',
				onsubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					if (result == "1") {
						location.href="${ctx}/admin/column/init_page.htm";
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
		function isEmpty(str) {
			if (str.length <= 0 || str == null) {
				return true;
			}
			return false;
		}
	</script>
</body>
</html>
