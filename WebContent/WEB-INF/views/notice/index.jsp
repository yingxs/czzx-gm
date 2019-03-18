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
<title>公告管理</title>
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
	var editor1 = K.create('textarea[name="tniContent"]', {
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

.padded {padding: 0px 10px;}
            .inline { display: inline-block;}
            .line { background: #ccc; height: 1px; width: 100%; margin: 10px 0px; }
            .btn {
                display: inline-block;
                padding: 6px 12px;
                margin-bottom: 0;
                font-size: 14px;
                font-weight: 400;
                line-height: 1.42857143;
                text-align: center;
                white-space: nowrap;
                vertical-align: middle;
                -ms-touch-action: manipulation;
                touch-action: manipulation;
                cursor: pointer;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                user-select: none;
                background-image: none;
                border: 1px solid transparent;
                border-radius: 4px;
            }

        .btn-cor { background: #028A39; color: #fff;}
       
       /* .table_area_h { display: none;}*/
       
       
       #fm {
	margin: 0;
	padding: 10px 30px;
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
		文章标题: <input class="easyui-textbox" name="tniTitle" id="tniTitle" style="width: 110px;height:30px;" data-options="prompt:'请输入文章标题'"/>
			 所属分类: <select id="depaName" class="easyui-combobox" name="depaName" style="width:100px;height:30px;" data-options="prompt:'请选择分类'">
				<option value="">选择分类</option>
				<option value="1">对内公告</option>
				<option value="2">对外公告</option>
			</select>
			创建时间: <input class="easyui-datetimebox" style="width: 110px;height:30px;" id="startDate" data-options="prompt:'开始时间'"/> —<input class="easyui-datetimebox" style="width: 110px;height:30px;" id="endDate" data-options="prompt:'结束时间'"/>
		<a href="#" class="easyui-linkbutton" icon="icon-search" plain="true"
			onclick="cha()">查询</a> <a href="#" class="easyui-linkbutton"
			icon="icon-add" plain="true" onclick="newTeacher()">新增</a>
	</div>
	<br/>
	<table class="easyui-datagrid" id="dg" title="公告管理"
		data-options="singleSelect:true,pagination:true,collapsible:false,url:'${ctx}admin/notice/list_json.json',method:'get'"
		pagination="true" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'tniTitle',width:150,align:'center'">文章标题</th>
				<th data-options="field:'tniType',width:60,align:'center',formatter:formatObj2">所属分类</th>
				<th data-options="field:'phone',width:150,align:'center',formatter:formatObj1">创建人</th>
				<th data-options="field:'tni_add_date_str',width:280,align:'center'">创建时间</th>
				<th data-options="field:'_operate',width:100,align:'center',formatter:formatOper">操作</th>
			</tr>

		</thead>
	</table>

	<script>
        //alert($)
     function formatObj1(value,rec) {
		return "Admin";
		}
     function formatObj2(value,rec) {
 		 if(value=="2"){
 			   return "对外公告";
 		  }else{
 			return "对内公告";
 		  }
 		}
    </script>
	<!--删除提示框-->
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
	
    	
		function formatOper(val, rows, index) {
			/*  return '<a href="#" onclick="initUpdate(' + rows.tniId
					+ ')">编辑</a>&nbsp;&nbsp;<a href="#" onclick="delteById('
					+ rows.tniId + ',\''+ rows.tniTitle +'\')">删除</a>&nbsp;&nbsp;<a href="#" onclick="d('
					+ rows.tniId + ',\''+ rows.tniTitle +'\')">置顶</a>&nbsp;&nbsp;<a href="#" onclick="e('
					+ rows.tniId + ',\''+ rows.tniTitle +'\')">设为头条</a>';  */
					
					
					var opStr = '';
					if (rows.tniHead == '0') {
						opStr += '<a href="#" onclick="headSet(' + rows.tniId
								+ ',1)">设为头条</a>&nbsp;&nbsp;';
					} else {
						opStr += '<a href="#" onclick="headSet(' + rows.tniId
								+ ',0)">取消头条</a>&nbsp;&nbsp;';
					}

					if (rows.tniTop == '0') {
						opStr += '<a href="#" onclick="topSet(' + rows.tniId
								+ ',1)">设为置顶</a>&nbsp;&nbsp;';
					} else {
						opStr += '<a href="#" onclick="topSet(' + rows.tniId
								+ ',0)">取消置顶</a>&nbsp;&nbsp;';
					}
					
			 return '<a href="#" onclick="initUpdate(' + rows.tniId
			+ ')">编辑</a>&nbsp;&nbsp;<a href="#" onclick="delteById('
			+ rows.tniId + ',\''+ rows.tniTitle +'\')">删除</a>&nbsp;&nbsp;'; 
		}
		var url;
		var type;
		var memberTableGrid;

		//弹出新增层
		function newTeacher() {
			/* $("#dlg").dialog("open").dialog('setTitle', '添加公告');
			$("#add_tniTitle").textbox('setValue','');
			KindEditor.instances[0].html('');
			$("#thname").textbox('setValue','');
			$("#loguse").textbox('setValue','');
			$("#fm").form("clear");
			$("#hidtype").value = "submit"; */
			parent.addTabFun({
				src : "${ctx}/admin/notice/paper/add",
				title : "新增公告"
			}); 
		}

		

		//条件查询
		function cha() {
			$("#dg").datagrid('load', {
				"keywords" : $("#tniTitle").val(),
				"startDate" : $("#startDate").datebox('getValue'),
				"endDate" : $("#endDate").datebox('getValue'),
				"depaName" : $("#depaName").combobox('getValue')
			});
		}
		
		//条件查询
		/* function chaTh() {
			$("#dgs").datagrid('load', {
				"keywords" : $("#thname").val(),
				"logUse" : $("#loguse").val()
			});
		} */
		
		$(":radio[name='tniType']").click(function(){
	        //alert(1);
	        console.log($(this).val())
	        if($(this).val()==2){
	            $("#toUser").hide();
	            $("#table_area_h").hide();
	        }

	        if($(this).val()==1){
	            $("#toUser").show();
	            $("#table_area_h").show();
	        }


	     });

		
		//根据Id删除
		function delteById(id,name) {
			if (id != "" || id != null) {
				$.messager.confirm("操作提示", "确定要删除\"  "+name+"  \"么？删除后不可恢复。", function(data) {
					if (data) {
						$.ajax({
							type : "POST",
							url : '${ctx}/admin/notice/del.htm',
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
			location.href="${ctx}/admin/notice/initUpdate/"+id+".htm";
		}
		
		//关闭编辑页刷新数据
		$('#editdlg').dialog({  
		    onClose:function(){  
		    	$("#dg").datagrid("load");  
		    },  
		});
		
		 
		 /* function saven(){
			 var add_tniTitle = $("#add_tniTitle").textbox('getValue');
			 var taContent = $("#taContent").val();
			 if(isEmpty(add_tniTitle)){
				$.messager.alert("提示信息", "请输入公告标题");
				return false;
			 }
			 if(add_tniTitle.length>50){
				$.messager.alert("提示信息", "公告标题长度不能超过50个字符");
				return false;
			 }
			 if(isEmpty(taContent)){
				$.messager.alert("提示信息", "请输入公告内容");
				return false;
			 }
			 
			 var ids = [];
			 var rows = $('#dgs').datagrid('getSelections');
			 for(var i=0; i<rows.length; i++){
			 	ids.push(rows[i].id);
			 }
			 
			 $("#nns").form("submit", {
					url : '${ctx}/admin/notice/save.htm?ids='+ids,
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
		 } */
		 
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
