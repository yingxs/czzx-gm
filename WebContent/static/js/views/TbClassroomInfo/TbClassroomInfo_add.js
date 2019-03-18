$(function(){
	
	
	init_UI();
	
	
	
});


function init_UI(){
	//楼宇列表
	$("#combobox_build_list").combobox({
		loader:ajax_Loader_build_list,
		valueField:'tbiId',
		textField:'tbiName',
		method:'get',
		panelHeight:'auto',
		onSelect:function(re){
			//楼层联动
			$("#combobox_floor_list").combobox({
				loader:fun_Loader_floor_list_ByBuildId,
				valueField:'tfiId',
				textField:'tfiName',
				method:'get',
				panelHeight:'auto',
				onBeforeLoad: function(param){
					param.buildId = re.tbiId;
				}
			});
		}
	});
	
	$("#combobox_floor_list").combobox();
	
	$("#button_add_submit").click(function(e){
		e.preventDefault();
		$(this).attr("disabled",true);
		ajax_addClassRoom_submit()
//		add_validate();
	});
	$("#button_add_clear").click(function(e){
		parent.closeTab('新增教室');
	});
	
	
}

var fun_Loader_floor_list_ByBuildId = function(param,success,error){
	$.ajax({
		url: ctx+'/TbBuildingInfo/ajax_get_floor_list_BybuildId',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				success(response.data);
			}else{
				$.messager.alert("提示",response.message,"error");
				return ;
			}
		},
        error: function(){
        	$.messager.alert("提示","请求错误,请检查路径或参数","error");
		}
	});
}


var ajax_Loader_build_list = function(param,success,error){
	
	$.ajax({
		url: ctx+'/TbBuildingInfo/ajax_get_budiling_list',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				success(response.data);
			}else{
				$.messager.alert("提示",response.message,"error");
				return ;
			}
		},
        error: function(){
        	$.messager.alert("提示","请求错误,请检查路径或参数","error");
		}
	});
	
}




//新增表单数据验证
function add_validate(){
	$('#add_classRoom_form').form('submit',{
		onSubmit:function(){
			var bool= $(this).form('enableValidation').form('validate');
			var isComboboxEmpty = $.trim($("#combobox_build_list").combobox("getValue")) != '' 
				&& $.trim($("#combobox_floor_list").combobox("getValue")) != '' ;
			if (bool && isComboboxEmpty ) {
//				alert("通过");
				ajax_addClassRoom_submit();
			} else {
				$.messager.alert("提示","请提交符合规范的数据！","error");
				$("#button_submit").attr("disabled",false);
			}
		}
	});
}








var ajax_addClassRoom_submit = function(){
	
	var build_id = $("#combobox_build_list").combobox("getValue");
	var floor_id = $("#combobox_floor_list").combobox("getValue");
	var classRoom_name = $("#textbox_classRoom_name").val();
	var classRoom_count = $("#textbox_classRoom_count").val();
	
	
	$.ajax({
		url:ctx+"/TbBuildingInfo/addClassRoom",
		type:"post",
		dataType:'json',
		data:{
			build_id:build_id,
			floor_id:floor_id,
			classRoom_name:classRoom_name,
			classRoom_count:classRoom_count
		},
		success:function(response){
			if(response.code=='100'){
				$.messager.alert("提示",response.message,"info",function(){
					$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
				});
				
			}else if(response.code=='120'){
				$.messager.alert("提示",response.message,"info",function(){
					$(location).attr('href', ctx+'/login');
				});
			}else{
				$.messager.alert("提示",response.message,"error");
			}
			$("#button_add_submit").attr("disabled",false);
		},
		error:function(){
			$.messager.alert("提示","新增失败,请求错误","error");
			$("#button_add_submit").attr("disabled",false);
		}
	});
}


