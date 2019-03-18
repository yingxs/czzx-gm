$(function(){
	

	var init_falg = false;
	$(document).click(function(){
		if(!init_falg){
			init_falg=true;
		}
		
	});
	
	
	
	//学院列表
	$("#combobox_institute_list").combobox({
		loader:fun_Loader_Institute_list,
		valueField:'tiiId',
		textField:'tiiName',
		method:'get',
		panelHeight:'auto',
		onSelect:function(re){
			if(init_falg){
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
	$("#button_save").click(function(e){
		e.preventDefault();
		$("#button_submit").attr("disabled",true);
		add_validate();
	});
	
	
	
	//选择学院、系别、年限
	var InstituteId = $("#hidden_tmiInstituteId").val();
	var DepartmentId = $("#hidden_tmiDepartmentId").val();
	var MajorYear = $("#hidden_tmiMajorYear").val();
	$('#combobox_institute_list').combobox('select',InstituteId);
	$('#combobox_department_list').combobox('select',DepartmentId);
	$('#combobox_major_year').combobox('select',MajorYear);
	
	
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
						twValue:item.twValue,
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
		type:'get',
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





// 保存更改后的专业
function saveMajorInfo(){
	var id = $("#hidden_tmiId").val();
	var instituteId = $("#combobox_institute_list").combobox("getValue");
	var departmentId = $("#combobox_department_list").combobox("getValue");
	var majorName = $("#textbox_major_name").val();
	var majorYear = $("#combobox_major_year").combobox("getValue");
	var majorCode = $("#textbox_major_code").val();
	
	$.ajax({
		url:ctx+"/TbMajorInfo/edit",
		type:"post",
		dataType:'json',
		data:{
			id:id,
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

//console.log($('#combobox_institute_list').combobox("getValue"));
//console.log($('#combobox_department_list').combobox("getValue"));
//console.log($('#combobox_major_year').combobox("getValue"));

//新增表单数据验证
function add_validate(){
	$('#form_add').form('submit',{
		onSubmit:function(){
			var bool= $(this).form('enableValidation').form('validate');
			var isComboboxEmpty = $.trim($("#combobox_department_list").combobox("getValue")) != '' 
				&& $.trim($("#combobox_major_year").combobox("getValue")) != '' 
			
			if (bool && isComboboxEmpty ) {
				saveMajorInfo();
			} else {
				$.messager.alert("提示","请提交符合规范的数据！","error");
				$("#button_submit").attr("disabled",false);
			}
		}
	});
}

