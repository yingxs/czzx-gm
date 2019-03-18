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
<title>文章管理</title>

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
		文章标题: <input class="easyui-textbox" name="quereyTitle" id="quereyTitle" style="width: 110px;height: 30px" data-options="prompt:'请输入文章标题'"/>
		 所属分类: <select id="selColumn"class="easyui-combotree" name="selColumn" style="width: 200px;height: 30px" data-options="prompt:'请选择所属分类'"></select> 
		创建时间: <input class="easyui-datetimebox" style="width: 110px;height: 30px" id="startDate" data-options="prompt:'开始时间'"/>
		 —<input class="easyui-datetimebox"style="width: 110px;height: 30px" id="endDate" data-options="prompt:'结束时间'"/> 
		<a href="#" class="easyui-linkbutton" icon="icon-search" plain="true" onclick="cha()">查询</a>
		<a href="#" class="easyui-linkbutton" icon="icon-add" plain="true" onclick="newArticle()">新增</a>
	</div>
	<br/>
	<table class="easyui-datagrid" id="dg" title="文章管理"
		data-options="singleSelect:true,pagination:true,collapsible:true,url:'${ctx}/admin/article/list_json.json',method:'get'"
		pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'taTitle',width:180,align:'center',formatter:formatOper1">文章标题</th>
				<th data-options="field:'columnName',width:120,align:'center'">所属分类</th>
				<th data-options="field:'taHeadlineStr',width:50,align:'center'">头条</th>
				<th data-options="field:'taTopStr',width:50,align:'center'">置顶</th>
				<th data-options="field:'addUserName',width:90,align:'center'">创建人</th>
				<th data-options="field:'addtimeString',width:120,align:'center'">创建时间</th>
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
		//树形下拉
		$('#selColumn').combotree({

		}).combotree("tree").tree({
			url : '${ctx}/admin/column/get_column.json',
			cascadeCheck : true,
			onlyLeafCheck : true,
			checkbox : true,
			animate : true,
			onBeforeExpand : function(node, param) {
				//$('#selColumn').combotree("tree").tree("options").url = "${ctx}/admin/column/get_all_column.json";
			}
		});
		
		//工具条
		function formatOper(val, rows, index) {
			var opStr = '';
			if (rows.taHeadline == '0') {
				opStr += '<a href="#" onclick="headSet(' + rows.taId
						+ ',1)">设为头条</a>&nbsp;&nbsp;';
			} else {
				opStr += '<a href="#" onclick="headSet(' + rows.taId
						+ ',0)">取消头条</a>&nbsp;&nbsp;';
			}

			if (rows.taTop == '0') {
				opStr += '<a href="#" onclick="topSet(' + rows.taId
						+ ',1)">设为置顶</a>&nbsp;&nbsp;';
			} else {
				opStr += '<a href="#" onclick="topSet(' + rows.taId
						+ ',0)">取消置顶</a>&nbsp;&nbsp;';
			}

			return '<a href="#" onclick="initUpdate(' + rows.taId
					+ ')">编辑</a>&nbsp;&nbsp;<a href="#" onclick="delteById('
					+ rows.taId + ',\'' + rows.taTitle
					+ '\')">删除</a>&nbsp;&nbsp;' + opStr;
		}
		//修改标题
		function formatOper1(val, rows, index) {
			var htm = '<a href="#"style="color:blue" onclick="artInfo(' + rows.taId + ')">'+rows.taTitle+'</a>'
			return htm;
		}
		//弹出新增层
		function newArticle() {
			parent.addTabFun({
				src : "${ctx}/admin/article/paper/add",
				title : "新增文章"
			}); 
		}

		//设置置顶
		function topSet(id, val) {
			var msg = "";
			if (val == 0) {
				msg += "确定要取消置顶该内容么?";
			} else {
				msg += "确定要置顶该内容么?";
			}

			$.messager.confirm("操作提示", msg, function(data) {
				if (data) {
					$.ajax({
						type : "POST",
						url : '${ctx}/admin/article/update_set.htm',
						dataType : 'json',
						data : {
							"id" : id,
							"taTop" : val
						},
						success : function(data) {
							if (data == "1") {
								$.messager.alert('系统提示', '置顶修改成功');
								$("#dg").datagrid("load");
							} else {
								$.messager.alert('系统提示', '置顶修改失败');
							}
						},
						error : function() {
							alert("系统错误");
						}
					})
				} else {
					$('#messageTips').window('close');
				}
			})

		}

		//设置头条
		function headSet(id, val) {
			$.ajax({
				type : "POST",
				url : '${ctx}/admin/article/update_set.htm',
				dataType : 'json',
				data : {
					"id" : id,
					"taHeadline" : val
				},
				success : function(data) {
					if (data == "1") {
						$.messager.alert('系统提示', '头条修改成功');
						$("#dg").datagrid("load");
					} else {
						$.messager.alert('系统提示', '头条修改失败');
					}
				},
				error : function() {
					alert("系统错误");
				}
			})

		}

		//新增保存
		function save() {
			var taTitle = $("#taTitle").textbox('getValue');
			var column = $("#taColumnId").combobox('getValue');
			var taContent = $("#taContent").val();
			var filename = $("#fileUrl").val();
			if(isEmpty(taTitle)){
				$.messager.alert("提示信息", "请输入文章标题");
				return false;
			}
			if(taTitle.length>50){
				$.messager.alert("提示信息", "请输入少于50字的文章标题");
				return false;
			}
			if(isEmpty(column)){
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
			$("#addfm").form("submit", {
				url : '${ctx}/admin/article/save.htm',
				onsubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					if (result == "1") {
						$.messager.alert("提示信息", "操作成功");
						$("#dlg").dialog("close");
						$("#dg").datagrid("load");
					} else {
						$.messager.alert("提示信息", "操作失败");
					}
				}
			});
		}

		//条件查询
		function cha() {
			$("#dg").datagrid('load', {
				"keywords" : $("#quereyTitle").val(),
				"startDate" : $("#startDate").datebox('getValue'),
				"endDate" : $("#endDate").datebox('getValue'),
				"selColumn" : $("#selColumn").combotree('getValue')
			});
		}

		//根据Id删除
		function delteById(id, name) {
			if (id != "" || id != null) {
				$.messager.confirm("操作提示", "确定要删除\"  " + name
						+ "  \"么？删除后不可恢复。", function(data) {
					if (data) {
						$.ajax({
							type : "POST",
							url : '${ctx}/admin/article/del.htm',
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
			location.href="${ctx}/admin/article/initUpdate.htm?id="+id;
		}
		/*
			文章详情
		*/
		function artInfo(id) {
			location.href="${ctx}/admin/article/detail.htm?id="+id;
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
