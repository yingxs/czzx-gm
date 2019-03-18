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
<title>修改公告</title>

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
*{
    font-size: 13px
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
		<div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'">
         <form id="nns" method="post" enctype="multipart/form-data">
         <input  type="hidden" name="tniId" value="${notice.tniId}" style="height:30px;"></input>
            <table cellpadding="5">
                <tr>
                    <td>文章标题:</td>
                    <td><input class="easyui-textbox" type="text" name="tniTitle" id="tniTitle" value="${notice.tniTitle}" style="height:30px;" data-options="prompt:'请输入文章标题'"></input></td>
                </tr>
                <tr>
                    <td>公告类型:</td>
                    <td>

                        <input type="radio" class="changg_ra" name="tniType" value="1"<c:if test="${notice.tniType==1 }">checked</c:if>/><span>内部公告</span> 
                        <input type="radio" class="changg_ra"  name="tniType" value="2"<c:if test="${notice.tniType==2 }">checked</c:if>/><span>外部公告</span>
                    </td>
                </tr>
                <tr>
                
                     <td><label>封面图片:</label> </td>
						<td colspan="1">
						   <img <c:if test="${notice.tniLogo!=null}">src="${ctx}upload/${notice.tniLogo}"</c:if> id="apImg" width="185" height="85" class="border apImg" style="vertical-align: bottom;" />
						<button class="adBtn">浏览</button>
						<input type="file" class="aptitude" name="fileUrl" id="fileUrl" accept="image/*" data-required="true" data-descriptions="detail_drawing" /> 
						<input type="hidden" id="taPicture" class="diUrl" value="" />
						</td>
                </tr>
                 <tr>
                    <td>详细内容:</td>
				    <td colspan="3"><textarea name="tniContent" id="taContent" style="width: 100px; height: 300px; visibility: hidden;">${notice.tniContent}</textarea></td>
                </tr>
                <tr id="toUser">
                    <td>接收人:</td>
                    <td><select id="depaName" class="easyui-combobox" name="tniRecvType" style="width:100px;height:30px;">
							<option value="0">全部</option>
							<option value="1">家长</option>
							<option value="2">教师</option></select>
							<!-- &nbsp;&nbsp;<button>选择</button>&nbsp;&nbsp;<button>全部</button> -->
							</td>
                </tr>
            </table>
         </form>
          
        
         <div style="margin:20px 0;"></div>
         <div class="padded table_area_h" id="table_area_h">
         <div class="inline">
             <label for="">姓名:</label>
             <input class="easyui-textbox" type="text" name="thname" data-options="prompt:'请输入姓名'" id="thname" style="height:30px;"></input>
         </div>

         <div class="inline">
             <label for="">登录账号:</label>
             <input class="easyui-textbox" type="text" name="loguse" id="loguse"  data-options="prompt:'请输入登录账号'" style="height:30px;"></input>
         </div>
         <div class="inline"> <button class="btn" onclick="chaTh()">查询</button></div>
        
        <div style="height:10px;"></div>

         <table id="dgs" title="" class="easyui-datagrid" style="width:500px;height:250px" url="${ctx}admin/notice/th_list_json.json" toolbar="#toolbar" pagination="true" rownumbers="true" fitColumns="true" singleSelect="false">
        <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="name">姓名</th>
                <th field="loginUser">登录账号</th>
                <th field="phone">手机号</th>
            </tr>
        </thead>
        </table>
        </div>

        <div class="padded">
          <div class="line"></div>

        </div>
    </div>

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
	<div id="dlg-buttons">
		<button  class="btn btn-cor"
			onclick="saven()" iconcls="icon-save">保存</button>
			 <button  class="btn btn-cor"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</button>
	</div>
 <script>		
 		
 		
		//条件查询
		function chaTh() {
			$("#dgs").datagrid('load', {
				"keywords" : $("#thname").val(),
				"logUse" : $("#loguse").val()
			});
		}

		
		//根据Id删除
		function delteById(id,name) {
			if (id != "" || id != null) {
				$.messager.confirm("操作提示", "确定要删除\"  "+name+"  \"么？删除后不可恢复。", function(data) {
					if (data) {
						$.ajax({
							type : "POST",
							url : '${ctx}/admin/teacher/del.htm',
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
			
			$("#myfrom").attr("src","${ctx}/admin/teacher/initUpdate/"+id+".htm");
			$("#editdlg").dialog("open").dialog('setTitle','编辑部门');
		}
		
		//关闭编辑页刷新数据
		$('#editdlg').dialog({  
		    onClose:function(){  
		    	$("#dg").datagrid("load");  
		    },  
		});
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
		
		 function hide(){
			 var tniType = ${notice.tniType};
		 		if(tniType==2){
		 			$(":radio[name='tniType']").click();
		 		}
				
		 }
		 
		 setTimeout(hide,1000);
		
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
		 //登录名校验
		 function saven(){
			 var tniTitle = $("#tniTitle").textbox('getValue');
			 var taContent = $("#taContent").val();
			 if(isEmpty(tniTitle)){
				$.messager.alert("提示信息", "请输入公告标题");
				return false;
			 }
			 if(tniTitle.length>50){
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
					url : '${ctx}/admin/notice/update.htm?ids='+ids,
					onsubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "1") {
							location.href="${ctx}/admin/notice/init_page.htm";
						} else {
							$.messager.alert("提示信息", "操作失败");
						}
					}
				});
		 }
		
	</script>
</body>
</html>
