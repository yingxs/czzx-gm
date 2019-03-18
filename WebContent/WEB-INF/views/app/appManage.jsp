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
	href="${ctx}/static/easyui-1.4/themes/icon.css" />
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
</style>

</head>
<body>
<br/>
	<div id="tb" style="padding: 2px 5px; font-size: 13px">
		应用名称: <input class="easyui-textbox" name="taiName" id="query_taiName" style="width: 110px;height: 30px" data-options="prompt:'请输入应用名称'" /> &nbsp;
		创建时间: <input class="easyui-datetimebox" style="width: 110px;height: 30px" id="startDate" data-options="prompt:'开始时间'"/> 
		—<input class="easyui-datetimebox" style="width: 110px;height: 30px" id="endDate" data-options="prompt:'结束时间'" /> 
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" onclick="cha()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="newdlg()">新增</a>
	</div>
	<br/>
	<table class="easyui-datagrid" id="dg" title="应用管理"
		data-options="singleSelect:true,pagination:true,collapsible:false,url:'${ctx}/admin/applicaton/list_json.json',method:'get'"
		pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'taiIcon',width:120,align:'center',formatter:formatOper1">图标</th>
				<th data-options="field:'taiName',width:120,align:'center'">应用名称</th>
				<th data-options="field:'taiWebAddr',width:300,align:'center'">应用地址</th>
				<th data-options="field:'taiAddCount',width:50,align:'center'">浏览量</th>
				<th data-options="field:'taiAddDate',width:120,align:'center',formatter:formatterdate">创建时间</th>
				<th data-options="field:'_operate',width:300,align:'center',formatter:formatOper">操作</th>
			</tr>

		</thead>
	</table>

	<!--提示框-->
	<div id="messageTips" class="easyui-window add-product-window"
		title="提示" data-options="modal:true,closed:true"
		style="width: 320px; height: 180px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<p id="messageTips-txt" class="messageTips-txt icon-help"></p>
			<p class="messageTips-btn">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
					href="javascript:void(0)" onclick="javascript:messageTipsEnter()"
					style="width: 80px">确认</a> <a class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
					onclick="$('#messageTips').window('close')" style="width: 80px">取消</a>
			</p>
		</div>
	</div>

	<script type="text/javascript">
		//工具条
		function formatOper(val, rows, index) {
			
			return '<a href="#" onclick="initUpdate(' + rows.taiId
					+ ')">编辑</a>&nbsp;&nbsp;<a href="#" onclick="delteById('
					+ rows.taiId + ',\'' + rows.taiName
					+ '\')">删除</a>&nbsp;&nbsp;<a href="#" onclick="downloadInfo('
					+ rows.taiId + ')">查看下载</a>';
		}
		//图片地址
		function formatOper1(val, rows, index) {
			var htm ='<img src="${ctx}upload/'+rows.taiIcon+'" id="appImg" width="80" height="80" class="border appImg"  alt="'+rows.taiName+'" />'
			return htm;
		}
		//修改标题
		function formatterdate(val, row) {
			if (val != null) {
			var date = new Date(val);
			return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
			}
		}
		//弹出新增层
		function newdlg() {
			/* $("#dlg").dialog("open").dialog('setTitle', '新增应用');
			$("#taiName").textbox('setValue','');
			$("#taiWebAddr").textbox('setValue','');
			$('#taColumnId').combobox('setValues','');
			KindEditor.instances[0].html('');
			$("#fm").form("clear");
			$("#hidtype").value = "submit"; */
			parent.addTabFun({
				src : "${ctx}/admin/applicaton/paper/add",
				title : "新增应用"
			}); 
		}


		//条件查询
		function cha() {
			$("#dg").datagrid('load', {
				"keywords" : $("#query_taiName").val(),
				"startDate" : $("#startDate").datebox('getValue'),
				"endDate" : $("#endDate").datebox('getValue')
			});
		}

		//根据Id删除
		function delteById(id, name) {
			if (id != "" || id != null) {
				$.messager.confirm("操作提示", "确定要删除应用名称为\"  " + name
						+ "  \"应用吗？删除后不可恢复。", function(data) {
					if (data) {
						$.ajax({
							type : "POST",
							url : '${ctx}/admin/applicaton/del.htm',
							dataType : 'json',
							data : {
								"id" : id
							},
							success : function(data) {
								if (data == "1") {
									$.messager.alert('系统提示', '删除成功');
									$("#dg").datagrid("load");
								} else {
									$.messager.alert('系统提示', '删除失败');
								}
							},
							error : function() {
								alert("系统错误");
							}
						})
					} else {
						$('#messageTips').window('close');
					}
				});
			} else {
				$.messager.alert("提示信息", "删除选中数据不能为空");
			}
		}

		/**
		 * 初始化修改信息
		 */
		function initUpdate(id) {
		    location.href="${ctx}/admin/applicaton/initUpdate.htm?id="+id;
		}

		//初始化查看下载	
		function downloadInfo(id){
			location.href = "${ctx}/admin/applicaton/detail.htm?id="+id;
		}

		//关闭编辑页刷新数据
		$('#editdlg').dialog({
			onClose : function() {
				$("#dg").datagrid("load");
			},
		});

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
