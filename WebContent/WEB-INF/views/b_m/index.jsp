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
<title>部门管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/base.css" />
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
.confirm {
	display: inline-block;
	width: 52px;
	height: 24px;
	line-height: 24px;
	text-decoration: none;
	text-align: center;
	color: white;
	background-color: green;
	font-size: 12px;
	border-radius: 3px;
}
.cancel {
	display: inline-block;
	width: 52px;
	height: 22px;
	line-height: 22px;
	text-decoration: none;
	text-align: center;
	color: #333;
	border:1px #a9a9a9 solid;
	background-color: white;
	font-size: 12px;
	border-radius: 3px;
}
</style>

</head>
<body>
<br/>
	<div id="tb" style="font-size: 13px" class="datagrid-toolbar">
		部门名称: <input class="easyui-textbox" name="depaName" id="depaName"
			style="width: 110px;height: 30px" data-options="prompt:'请输入部门名称'"/> 
			创建时间: <input class="easyui-datetimebox"
			style="width: 110px;height: 30px" id="startDate" data-options="prompt:'开始时间'"/> —<input
			class="easyui-datetimebox" style="width: 110px;height: 30px" id="endDate" data-options="prompt:'结束时间'"/>
		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
			onclick="cha()">查询</a> <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add"  onclick="newDepa()">新增</a>
	</div>
	<br/>
	<table class="easyui-datagrid" id="dg" title="部门管理"
		data-options="singleSelect:true,pagination:true,collapsible:false,url:'${ctx}/admin/department/list_json.json',method:'get'"
		pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'name',width:150,align:'center'">部门名称</th>
				<th data-options="field:'desp',width:400,align:'center'">部门职能</th>
				<th data-options="field:'addtimeString',width:80,align:'center'">创建时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">操作</th>
			</tr>

		</thead>
	</table>

	<!-- 添加弹出层 -->
	<div id="dlg" class="easyui-dialog"
		style="width: 390px; height: 260px; padding: 10px 20px;" closed="true"
		buttons="#dlg-buttons">
		<form id="addfm" method="post">
			<div class="fitem">
				<label> 部门名称:<span style="color: red">*</span></label>
				<input name="name" id="addName" class="easyui-textbox" data-options="prompt:'请输入部门名称'"/>
			</div>
			<div class="fitem">
				<label> 部门介绍:</label> 
				<input name="desp" id="addDesp" class="easyui-textbox" data-options="multiline:true,prompt:'请输入部门介绍'" style="width: 170px; height: 90px" />
			</div>
			<input type="hidden" name="action" id="hidtype" />
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveDepa()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>

	<!-- 修改弹出层 -->
	<div id="editdlg" class="easyui-dialog"
		style="width: 390px; height: 260px; padding: 10px 20px;" closed="true"
		buttons="#editdlg-buttons">
		<form id="editfm" method="post">
			<div class="fitem">
				<label> 部门名称: <span style="color: red">*</span></label>
				<input name="name" id="edit_name" class="easyui-textbox" data-options="prompt:'请输入部门名称'" style="width: 200px;"/>
			</div>
			<div class="fitem">
				<label> 部门介绍:</label>
				<input name="desp" id="edit_desp" class="easyui-textbox" data-options="multiline:true,prompt:'请输入部门介绍'" style="width: 200px; height: 90px"/>
			</div>
			<input type="hidden" name="action" id="edit_hidtype" /> <input
				type="hidden" id="edit_id" />

		</form>
	</div>
	<div id="editdlg-buttons">
		<a href="javascript:void(0)" class="confirm"
			onclick="updateDepa()" >确定</a> <a
			href="javascript:void(0)" class="cancel"
			onclick="javascript:$('#editdlg').dialog('close')">取消</a>
	</div>
	<!--删除提示框-->
	<div id="messageTips" class="easyui-window add-product-window"
		title="提示" data-options="modal:true,closed:true"
		style="width: 320px; height: 180px; padding: 5px;">
		<div class="easyui-layout" data-options="fit:true">
			<p id="messageTips-txt" class="messageTips-txt icon-help"></p>
			<p class="messageTips-btn">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
					href="javascript:void(0)" onclick="javascript:messageTipsEnter()"
					style="width: 80px">确认</a>
				<a class="easyui-linkbutton"
				data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
				onclick="$('#messageTips').window('close')" style="width: 80px">取消</a>
			</p>
		</div>
	</div>

	<script type="text/javascript">
		function formatOper(val, rows, index) {
			return '<a href="#" onclick="initUpdate(' + rows.id+ ')">修改</a>&nbsp;<a href="#" onclick="member(' + rows.id+ ')">成员管理</a>&nbsp;<a href="#" onclick="delteById('+ rows.id + ',\''+ rows.name +'\')">删除</a>';
		}
		var url;
		var type;
		var memberTableGrid;

		//弹出新增层
		function newDepa() {
			/* $("#dlg").dialog("open").dialog('setTitle', '添加部门');
			$("#fm").form("clear");
			$("#addName").textbox('setValue','');
			$("#addDesp").textbox('setValue','');
			$("#hidtype").value = "submit"; */
			parent.addTabFun({
				src : "${ctx}/admin/department/paper/add",
				title : "新增部门"
			}); 
		}

		//条件查询
		function cha() {
			$("#dg").datagrid('load', {
				"keywords" : $("#depaName").val(),
				"startDate" : $("#startDate").datebox('getValue'),
				"endDate" : $("#endDate").datebox('getValue'),
				"depaName" : 1,
			});
		}

		//修改
		function updateDepa() {
			var edit_name = $("#edit_name").textbox('getValue');
			var edit_desp = $("#edit_desp").textbox('getValue');
			if (edit_name == null || edit_name == "") {
				$.messager.alert("提示信息", "请输入部门名称");
				return false;
			} else {
				if (edit_name.length > 10) {
					$.messager.alert("提示信息", "请输入小于10字的部门名称");
					return false;
				}
				if (edit_desp.length > 100) {
					$.messager.alert("提示信息", "请输入小于100字的部门职能");
					return false;
				} else {
					var id = $("#edit_id").val();
					$("#editfm").form("submit", {
						url : '${ctx}/admin/department/update.htm?id=' + id,
						onsubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result == "1") {
								$.messager.alert("提示信息", "操作成功");
								$("#editdlg").dialog("close");
								$("#dg").datagrid("load");
							} else {
								$.messager.alert("提示信息", "操作失败");
							}
						}
					})
				}
			}
			
		}

		//根据Id删除
		function delteById(id,name) {
			if (id != "" || id != null) {
				$.messager.confirm("操作提示", "确定要删除"+name+"么？删除后不可恢复。", function(data) {
					if (data) {
						$.ajax({
							type : "POST",
							url : '${ctx}/admin/department/del.htm',
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
			$.ajax({
				type : "POST",
				url : '${ctx}/admin/department/initUpdate.htm',
				data : {
					"id" : id
				},
				dataType : 'json',
				success : function(data) {
					console.log(data);
					//弹出新增层
					if (data != null) {
						$("#editdlg").dialog("open").dialog('setTitle',
								'编辑部门');
						$("#edit_name").textbox('setValue', data.name);
						$("#edit_desp").textbox('setValue', data.desp);
						$("#edit_id").val(id);
						$("#edit_hidtype").value = "submit";
						//var val = $("#userdlg_useraccount").textbox('getValue')//取值
					}

				},
				error : function() {
					alert("系统错误");
				}
			});
		}
		
		
		//跳转至成员管理界面
		function member(id) {
		    location.href="${ctx}/admin/department/paper/detail?id="+id;
		}
		
		
		
	</script>
</body>
</html>
