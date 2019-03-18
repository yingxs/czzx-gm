$(function(){
	
	init_UI();
	
	//学院列表
	$("#combobox_institute_list").combobox({
		loader:fun_Loader_Institute_list,
		valueField:'tiiId',
		textField:'tiiName',
		method:'get',
		panelHeight:'auto',
		prompt:'请选择所属学院',
		onSelect:function(re){
			//系联动
			$("#combobox_department_list").combobox({
				loader:fun_Loader_department_list_ByInstituteId,
				valueField:'tidi_id',
				textField:'tidi_name',
				method:'get',
				panelHeight:'auto',
				onBeforeLoad: function(param){
					param.InstituteId = re.tiiId;
				}
			});
		}
	});
	
	
	
	//专业年份列表
	$("#combobox_major_year").combobox({
		loader:fun_Loader_majorYear_list,
		valueField:'twValue',
		textField:'twName',
		method:'get',
		panelHeight:'auto',
		validType:['Special','length[0,10]'],
		prompt:'请输入专业年限'
	});
	
	// 系别
	$("#combobox_department_list").combobox({
		loader:fun_Loader_department_list,
		valueField:'tidiId',
		textField:'tidiName',
		method:'get',
		panelHeight:'auto'
	});
	
	
	
	
	// 新增按钮触发
	$("#button_submit").click(function(e){
		e.preventDefault();
		$(this).attr("disabled",true);
//		add_validate();
		addMajorInfo();
	});
	
	// 取消按钮触发
	$("#button_clear").click(function(){
		parent.closeTab('新增专业');
	});
	
	
	
	
});

// 根据学院id请求其下属所有系别
function fun_Loader_department_list_ByInstituteId(param,success,error){
	$.ajax({
		url: ctx+'/TbMajorInfo/ajax_get_Department_list_ByInstituteId',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				var items = $.map(response.data.rows,function(item){
					return {
						tidi_id:item.tidi_id,
						tidi_name:item.tidi_name
					}
				});
				success(items);
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

// 请求所有系别数据
function fun_Loader_department_list(param,success,error){
	$.ajax({
		url: ctx+'/TbMajorInfo/ajax_get_Department_list',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				var items = $.map(response.data,function(item){
					return {
						tidiId:item.tidiId,
						tidiName:item.tidiName
					}
				});
				success(items);
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

// 请求专业年份列表
function fun_Loader_majorYear_list(param,success,error){
	$.ajax({
		url: ctx+'/TbMajorInfo/ajax_get_major_year',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				var items = $.map(response.data,function(item){
					return {
						twValue:item.twId,
						twName:item.twName
					}
				});
				success(items);
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




// 请求学院列表数据
function fun_Loader_Institute_list(param,success,error){
	$.ajax({
		url: ctx+'/TbMajorInfo/ajax_get_Institute_list',
		dataType: 'json',
		method:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				var items = $.map(response.data,function(item){
					return {
						tiiId:item.tiiId,
						tiiName:item.tiiName
					}
				});
				success(items);
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





//添加新的专业
function addMajorInfo(){
	var instituteId = $("#combobox_institute_list").combobox("getValue");
	var departmentId = $("#combobox_department_list").combobox("getValue");
	var majorName = $("#textbox_major_name").val();
	var majorYear = $("#combobox_major_year").combobox("getValue");
	var majorCode = $("#textbox_major_code").val();
	
	$.ajax({
		url:ctx+"/TbMajorInfo/add",
		type:"post",
		dataType:'json',
		data:{
			instituteId:instituteId,
			departmentId:departmentId,
			majorName:majorName,
			majorYear:majorYear,
			majorCode:majorCode
		},
		success:function(response){
			if(response.code=='100'){
				$.messager.alert("提示",response.message,"info",function(){
					$(location).attr('href', ctx+'/TbMajorInfo/index/list');
				});
				
			}else if(response.code=='120'){
				$.messager.alert("提示",response.message,"info",function(){
					$(location).attr('href', ctx+'/login');
				});
			}else{
				$.messager.alert("提示",response.message,"error");
			}
			$("#button_submit").attr("disabled",false);
		},
		error:function(){
			$.messager.alert("提示","新增失败,请求错误","error");
			$("#button_submit").attr("disabled",false);
		}
	});
}

//新增表单数据验证
function add_validate(){
	$('#form_add').form('submit',{
		onSubmit:function(){
			var bool= $(this).form('enableValidation').form('validate');
			var isComboboxEmpty = $.trim($("#combobox_institute_list").combobox("getValue")) != '' 
				&& $.trim($("#combobox_department_list").combobox("getValue")) != '' 
				&& $.trim($("#combobox_major_year").combobox("getValue")) != '' 
			
			if (bool && isComboboxEmpty ) {
				addMajorInfo();
			} else {
				$.messager.alert("提示","请提交符合规范的数据！","error");
				$("#button_submit").attr("disabled",false);
			}
		}
	});
}

function init_UI(){
	
	$("#textbox_major_name").textbox({
		prompt:'请输入专业名称'
	})
	
	$("#textbox_major_code").textbox({
		prompt:'请输入专业代码'
	})
}

