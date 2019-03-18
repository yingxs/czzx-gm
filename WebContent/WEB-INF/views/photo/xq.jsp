<%@page import="com.spring.common.entity.TbPhoto"%>
<%@page import="java.util.List"%>
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
<title>文件管理</title>

<link rel="stylesheet" type="text/css"
	href="${ctx }/static/easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/static/easyui-1.4/demo.css" />
<script type="text/javascript"
	src="${ctx }/static/easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx }/static/easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/static/easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx }/static/js/comAuth.js" charset="UTF-8" type="text/javascript"></script>

<style type="text/css">

*{
font-size: 13px;
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

.chk_1,.chk_2,.chk_3,.chk_4 {
    display: none;
}

/*******STYLE 1*******/
.chk_1 + label {
	background-color: #FFF;
	border: 1px solid #C1CACA;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05);
	padding: 9px;
	border-radius: 5px;
	display: inline-block;
	position: relative;
	margin-right: 30px;
}
.chk_1 + label:active {
	box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px 1px 3px rgba(0,0,0,0.1);
}

.chk_1:checked + label {
	background-color: #ECF2F7;
	border: 1px solid #92A1AC;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05), inset 0px -15px 10px -12px rgba(0, 0, 0, 0.05), inset 15px 10px -12px rgba(255, 255, 255, 0.1);
	color: #243441;
}

.chk_1:checked + label:after {
	content: '\2714';
	position: absolute;
	top: -10px;
	left: 0px;
	color: #758794;
	width: 100%;
	text-align: center;
	font-size: 1.4em;
	padding: 1px 0 0 0;
	vertical-align: text-top;
}
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
#preview{width:260px;height:190px;border:1px solid #000;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
input{
   border: solid 1px #ccc;
}
</style>

</head>
<body>
 <br/>
	<div id="tb" style="padding: 2px 5px; font-size: 13px">
			<button  class="btn btn-cor"
			onclick="setCoverPhoto()" iconcls="icon-save">设置为封面文件</button>
			<button  class="btn btn-cor"
			onclick="dels()" iconcls="icon-save">删除文件</button>
			<button  class="btn btn-cor"
			onclick="checkeds()" iconcls="icon-save">全选</button>
	</div>
	<hr style="border: solid 1px #ccc"/>
	    <c:forEach var="list" items="${tbList}" varStatus="index">
	         <div style="float: left; margin: 10px;">
			      <div style="position: relative;top:20px;z-index: 99;">
			         <input type="checkbox" id="checkbox_a${index.index}" name="keys" class="chk_1" value="${list.tpId}"/><label for="checkbox_a${index.index}"></label>
			      </div>
			        <c:if test="${list.type == 1}">
			          <img src="${ctx}${list.tpUrl}" width="250px;" height="250px" style="border: solid 1px #ccc"/>
					</c:if>
					<c:if test="${list.type == 2}">
						<video width="250px;" height="250px" style="border: solid 1px #ccc; z-index: 9;"  src="${ctx}${list.tpUrl}" controls="controls"></video>
						<%-- <video width="250px;" height="250px" style="border: solid 1px #ccc" controls>
						  <source src="${ctx}${list.tpUrl}"  type="video/mp4">
						</video> --%>
					</c:if>
			      <p style="width: 250px">${list.tpName}</p>
			  </div>
	    </c:forEach>
			  
	<!-- 添加弹出层 -->
<%-- 	<div id="dlgImgs" class="easyui-dialog"
		style="width: 920px; height: 640px; padding: 10px 20px;" closed="true"
		buttons="#dlg-buttons" modal="true" hidden="hidden">
		<form id="nns" method="post" enctype="multipart/form-data" action="${ctx}/admin/photo/save_photo.htm?type=1">
		  <input type="hidden" name="photoId" value="${photoId}"/>
			<a href="javascript:void(0)" class="btn btn-cor"
			onclick="addTo()" iconcls="icon-save">添加</a> 请选择要上传的图片
			<input type="hidden" name="action" id="hidtypes" />
			<hr style="border: solid 1px #ccc"/>
			<br/>
		<div id="htmlss">
		     <div style="float: left; margin-right: 10px;"><div id="preview1">
				<img id="imghead1" width="260" height="180"  src='${ctx}static/images/timg.jpg'/>
		     </div>
	         <input type="file" name="fileUrl" onchange="previewImage(this,1)" /><button onclick="yichu('preview1')">删除</button><br/>
	         <input type="text" style="height: 30px; width: 200px;" name="photoName" placeholder="请输入图片名称"/></div>
	         
		</div>
         </form>
		</div> 
	<div id="dlg-buttons" hidden="hidden">
		<button  class="btn btn-cor" iconcls="icon-save" onclick="submitImg()">开始上传</button> 
		<button  class="btn btn-cor" iconcls="icon-save" onclick="javascript:$('#dlgImgs').dialog('close')">取消</button>
	</div> --%>

	<!--删除提示框-->
	<!-- <div id="messageTips" class="easyui-window add-product-window"
		title="提示" data-options="modal:true,closed:true" hidden="hidden"
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
	</div> -->

<script type="text/javascript">
//图片上传预览，IE用了滤镜
function previewImage(file,indexs)
{
var MAXWIDTH  = 260;
var MAXHEIGHT = 180;
var div = document.getElementById('preview'+indexs);
if (file.files && file.files[0])
{
    div.innerHTML ='<img id=imghead'+indexs+'>';
    var img = document.getElementById('imghead'+indexs);
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
  div.innerHTML = '<img id=imghead'+indexs+'>';
  var img = document.getElementById('imghead'+indexs);
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

	<script type="text/javascript">
	
	
      var msg = "${msg}";
      if(msg!=""){
    	 confrim(msg); 
      }
	
		//弹出新增层
		function newDepas() {
			$("#dlgImgs").dialog("open").dialog('setTitle', '上传文件');
			$("#fms").form("clear");
			$("#hidtypes").value = "submit";
		}
		var tt = 1;
		function addTo(){
			tt++;
			var htmls = ' <div style="float: left; margin-right: 10px;" id="divs'+tt+'"><div id="preview'+tt+'"><img id="imghead'+tt+'" width="260" height="180"  src="${ctx}static/images/timg.jpg"/></div><input type="file" name="fileUrl" onchange="previewImage(this,'+tt+')" /><button onclick="yichu(\'divs'+tt+'\')">删除</button><br/><input type="text" style="height: 30px; width: 200px;" name="photoName" placeholder="请输入图片名称"/></div>';
            $("#htmlss").append(htmls);
		}
		
		function yichu(ids){
			$("#"+ids).remove();
		}
		
		var isChek = false;
		function checkeds(){
			var  ckb=document.getElementsByTagName("INPUT");
			if(isChek == false){
			  for(i=0;i<ckb.length;i++)   
			  {   
				 if(ckb[i].type=="checkbox")   
					 ckb[i].checked=true; 
			  }   
			  isChek = true;
			}else{
				for(i=0;i<ckb.length;i++)   
				  {   
					 if(ckb[i].type=="checkbox")   
						 ckb[i].checked=false; 
				  }   
				  isChek = false;
			}
			/* alert()
			 if(isChek==false){
				isChek = true;
				$(":checkbox").each(function(){
					alert($(this));
					   $(this).prop("checked",true);
					}); 
			}else{
				isChek = false;
				$(":checkbox").each(function(){
					   $(this).prop("checked",false);
					});
				
			}  */
		}
		function submitImg(){
			var flag = eachFile();
			
			if(flag==false){
				$.messager.alert("提示信息", "请选择要上传的文件");
				return false;
			}
			
			$("#nns").submit();
		}
		
		function eachFile(){
			var flag = true;
			$(":input[name='fileUrl']").each(function(){
				if(isEmpty($(this).val())){
					flag = false; 
				}
			})
			return flag;
		}
		
		function dels(){
			var  ckb=document.getElementsByTagName("INPUT");
			var size=0;
			var check_val = [];
			for(i=0;i<ckb.length;i++)   
			  {   
				 if(ckb[i].type=="checkbox" && ckb[i].checked == true){ 
					 size +=1;
					 check_val.push(ckb[i].value);
				 }
			  }  
			if(size == 0){
				confirm("请选中文件");
			}else{
				if(confirm("确认删除吗？")){
					 window.location.href="${ctx}/admin/photo/update.htm?type=2&photoId=${photoId}&ids="+check_val;
				}
			}
			/* var chk_value =[];
			$("input[type='checkbox']:checked").each(function(){
				alert("1");
			      chk_value.push($(this).val());
			}); 
			if(chk_value.length==0){
        		$.messager.alert("提示信息", "请选中图片!");
        		return false;
        	}else{
			 $.messager.confirm("确认", "确认删除吗?", function (r) {  
			        if (r) {  
						location.href="${ctx}/admin/photo/update.htm?type=2&photoId=${photoId}&ids="+chk_value;
						        }  
				 }); 
        	} */
		}
		
		//设置为封面图片
		function setCoverPhoto(){
			var  ckb=document.getElementsByTagName("INPUT");
			var size=0;
			var ids = "";
			if(ckb == null || ckb.length <= 0){
				confirm("请选中需要设置为封面的文件！");
				return;
			}
			for(i=0;i<ckb.length;i++){   
				 if(ckb[i].type=="checkbox" && ckb[i].checked == true){ 
					 size +=1;
					 ids = ckb[i].value
				 }
			}
			if(size <= 0){
				confirm("请选中需要设置为封面的文件！");
				return;
			}
			if(size != 1){
				confirm("只可设置一张图片为封面文件！");
				return;
			}
			if(confirm("确认设置此封图片为封面文件吗？")){
				//window.location.href="${ctx}/admin/photo/setCoverPhoto?photoId=${photoId}&ids="+check_val;
				$.ajax({
					type:"POST",
					url:"${ctx}/admin/photo/setCoverPhoto",
					data:{
						photoId:${photoId},
						ids:ids
					},
					success:function(data){
						if(data.code == '100'){
							parent.addTabFun({
			                    src : "${ctx}/photo/index/list",
			                    title : "文件管理"
			                });
			            }else{
			                $.messager.alert('提示',data.message,'warning');
			            }
					}
				})
			}
		}
		
		function isEmpty(str){
			if(str.length<=0 || str == null){
				return true ;
			}
			return false ;
		}
	</script>
</body>
</html>
