var res;
$(document).ready(function(){
		$("#oldpwd").click(function(){
			$("#oldpwd").next().html(" 请输入旧密码");
		});
		$("#oldpwd").blur(function(){
			validTypeOldPwd();
		});
		
		$("#newpwd").click(function(){
			$("#newpwd").next().html(" 请输入新密码");
		});
		$("#newpwd").blur(function(){
			validTypeNewPwd();
		});
		
		$("#newpwd2").click(function(){
			$("#newpwd2").next().html(" 请再次输入新密码");
		});
		$("#newpwd2").blur(function(){
			validTypeNewPwd2();
		});
});

function validTypeOldPwd(){
	var oldpwd = $("#oldpwd").val();
	var len = $("#oldpwd").val().length;
	 if(len >= 6 && len < 11){
		 var res = checkOldpwd(oldpwd);
		 if(res == "0"){
			 $("#oldpwd").next().html("*");
			 return true;
		 }else if (res == "-1"){
			 $("#oldpwd").next().html(" 旧密码输入错误");
			 return false;
		 }
	 }else{
		 $("#oldpwd").next().html(" 密码为6-10位之间");
		return false;
	 }
}

function validTypeNewPwd(){
	 var len = $("#newpwd").val().length;
	 if(len >= 6 && len < 11){
		 $("#newpwd").next().html("*");
		return true;
	 }else{
		 $("#newpwd").next().html(" 新密码为6-10位之间");
		return false;
	 }
	 
	 if ($("#newpwd").val().indexOf(" ") != -1) {
		 $("#newpwd").next().html('密码不能含有空格!');
	    return false;
	 }else{
		 $("#newpwd").next().html("*");
		return true;
	 }
}

function validTypeNewPwd2(){
	var len = $("#newpwd2").val().length;
	var newpwd = $("#newpwd").val();
	var newpwd2 = $("#newpwd2").val();
	 if(len >= 6 && len < 11){
		if(newpwd == newpwd2){
			$("#newpwd2").next().html("*");	
			return true;
		}else{
			$("#newpwd2").next().html(" 两次密码输入不一致");	
			return false;
		}
	 }else{
		 $("#newpwd2").next().html(" 新密码为6-10位之间");	
		return false;
	 }
}

function checkOldpwd(oldpwd){
	var url = ctx + "/user/modify_pass?time=" + new Date();
	$.ajax({
		url : url,
		type: "POST",
		dataType:'text',
		data:{"oldpassword":oldpwd},
		async : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		success : function(text) {
			if(text == "1"){//旧密码匹配成功
				res = text;
			}else if (text == "-1"){//旧密码匹配失败
				res = text;
			}else if($.trim(text)=='unauthorized'){
				$.messager.alert('提示','您没有权限!','warning');
			}else if (text == "-2"){//登录失效
				$.messager.alert('提示','登录信息已失效，请重新登录!','warning',function(){
					location.href=basePath+"login.jsp";
				});
			}
		}
	});
	return res;
}

function checkSubmit(){

	var oldpwd = $("#oldpwd").val();
	var newpwd = $("#newpwd").val();
	var newpwd2 = $("#newpwd2").val();
	var url = ctx + "/user/modify_pass?time=" + new Date();
	$.ajax({
		url : url,
		type: "POST",
		dataType:'text',
		data:{"oldPassword":oldpwd,"password":newpwd,"repassword":newpwd2},
		async : false,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		success : function(text) {
			if(text == "1"){//修改密码成功
				$.messager.alert('提示','密码修改成功!','info',function(){
					location.reload();
				});
			}else if (text == "-1"){//两次输入密码不一致
				$.messager.alert('提示','两次输入密码不一致!','warning',function(){
					location.reload();
				});
			}else if (text == "-2"){//旧密码输入错误
				$.messager.alert('提示','旧密码输入错误!','warning',function(){
					location.reload();
				});
			}else if (text == "-3"){//系统异常，修改密码失败
				$.messager.alert('提示','系统异常，修改密码失败，请重新登录!','error',function(){
					location.href=basePath+"login.jsp";
				});
			}else if (text == "0"){//提交参数错误
				$.messager.alert('提示','提交参数错误!','error',function(){
					location.href=basePath+"login.jsp";
				});
			}
		}
	});
}