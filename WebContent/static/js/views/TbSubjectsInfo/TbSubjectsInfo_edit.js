// 修改科目
function editSubject() {
	
	// 开启验证框强制验证
	
	var bool = $("#edit_subject_windowForm").form('enableValidation').form('validate');
	if (bool) {
		//true就直接通过了
	} else {
		// 提示数据不完整
		$.messager.alert('提示', '您填写的数据格式有误或不完整，请检查!', 'warning');
		return false;
	}

	
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbsubjectsInfo/ajax_edit_TbSubjectsInfo',
		data : $("#edit_subject_windowForm").serialize(),
		success : function(data) {
			// $('#formSubmit_button').attr("disabled", false);
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
// 取消编辑科目
function resetEditSubject() {
	$('#editWindow').window('close');
}
// 跳出编辑窗口
function editSubjectInfo(btn, tsi_id) {
	// var path = $('.addcls').attr('data-path');
	// var menuId = $('.addcls').attr('data-menuId');
	// 加载编辑窗口中科目回显
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbsubjectsInfo/ajax_get_subjectName',
		data : {
			"tsi_id" : tsi_id
		},
		success : function(result) {
			var tsiId = result.tsiId;
			var tsiName = result.tsiName;
			$('#tsiId').val(tsiId);
			$('#tsiName').textbox('setValue', tsiName);
			
			// $("#tsiId").attr({ value: tsiId});
			// $("#tsiName").attr({ text: tsiName});
			// $("#tsiName").attr({ value: tsiName});
			$('#editWindow').window('open');
			// document.getElementsById("tsiId").setAttribute("value",result.tsiId);
			// document.getElementsById("tsiName").setAttribute("value",result.tsiName);
		}
	});
}