//添加科目
function addSubject() {
	var bool = $("#add_subject_windowForm").form('enableValidation').form('validate');
	if (bool) {
		//true就直接通过了
	} else {
		// 提示数据不完整
		$.messager.alert('提示', '您填写的数据格式有误或不完整，请检查!', 'warning');
		return false;
	}
	
	// 新增按钮触发时，禁用按钮防止表单重复提交
	$("#formSubmit_button").attr("disabled", true);
	var tName = $('#tName').textbox('getValue');
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbsubjectsInfo/ajax_add_TbSubjectsInfo',
		data : {
			"tsiName" : tName
		},
		success : function(data) {
			// 接触按钮禁用
			$('#formSubmit_button').attr("disabled", false);
			if (data["code"] == '100') {
				// 添加成功
				$.messager.alert('提示', data["msg"], 'warning');
				// 跳转列表刷新
				parent.addTabFun({
					src : ctx + "/TbsubjectsInfo/index/list",
					title : "科目管理"
				});

			} else if (data["code"] == '101') {
				$.messager.alert('提示', data["msg"], 'warning');
			} else if (data["code"] == '102') {
				$.messager.alert('提示', data["msg"], 'warning');
			}

		}
	});

}

// 取消添加科目
function resetAddSubject() {
	$('#addWindow').window('close');
}

// 跳出添加窗口
function addInstituteInfo() {
	// var path = $('.addcls').attr('data-path');
	// var menuId = $('.addcls').attr('data-menuId');
	$('#addWindow').window('open');
}