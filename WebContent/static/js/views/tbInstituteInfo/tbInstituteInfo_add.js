//$(function() {
//	// 下拉列表加载学校列表
//	$('#tiiSchoolId').combobox({
//		url : ctx + '/TbSchoolInfo/ajax_get_schoolInfoList',
//		valueField : 'tci_id',
//		textField : 'tci_name'
//	});
//
//});

//取消按钮触发事件
function resetTbInstituteInfoForm(){
	$("#add-TbInstituteInfo-form").form("reset");
	parent.addTabFun({
		src : ctx + "/tbInstituteInfo/index/list",
		title : "学院列表"
	});
}


/**
 * 手机版图片上传
 */
function ajaxFileUpload(type) {
	// 执行上传文件操作的函数
	if (type == 1) {
		/**
		 * phone版图片上传
		 */
		$.ajaxFileUpload({
			// 处理文件上传操作的服务器端地址(可以传参数)
			url : ctx + '/publicUploadTwo/fileUpload',
			secureuri : false, // 是否启用安全提交,默认为false
			fileElementId : 'phone', // 文件选择框的id属性
			dataType : 'text', // 服务器返回的格式,可以是json或xml等
			data : {
				"folder" : "phone"
			}, // 上传文件传到后台参数
			success : function(data, status) { // 服务器响应成功时的处理函数
				data = data.split(">")[1];
				data = data.replace("<pre>", ''); // ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
				data = data.replace("</PRE>", '');
				data = data.replace("<pre>", '');
				data = data.replace("</pre", ''); // 本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
				if (data.substring(0, 1) == 0) { // 0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
					$("img[id='phoneImage']").attr("src", data.substring(2));
					// document.getElementById("tacPhoneImage").value =
					// "${ctx}"+data.substring(2);
					$("#tacPhoneImage").val(data.substring(2));// 要上传的路径
					$('#result').html(
							"图片上传成功! 图片名称："
									+ data.substring(data.lastIndexOf("/") + 1,
											data.length) + "<br/>");
				} else {
					$('#result').html(data.substring(2));
				}
			},
			error : function(data, status, e) { // 服务器响应失败时的处理函数
				$('#result').html(data);
			}
		});
	} else {
		/**
		 * web版图片上传
		 */
		$.ajaxFileUpload({
			// 处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
			url : ctx + '/publicUploadTwo/fileUpload',
			secureuri : false, // 是否启用安全提交,默认为false
			fileElementId : 'web', // 文件选择框的id属性
			dataType : 'text', // 服务器返回的格式,可以是json或xml等
			data : {
				"folder" : "web"
			}, // 上传文件传到后台参数
			success : function(data, status) { // 服务器响应成功时的处理函数
				data = data.split(">")[1];
				data = data.replace("<pre>", ''); // ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
				data = data.replace("</PRE>", '');
				data = data.replace("<pre>", '');
				data = data.replace("</pre", ''); // 本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
				if (data.substring(0, 1) == 0) { // 0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
					$("img[id='webImage']").attr("src", data.substring(2));
					$("#tii_icon").val(data.substring(2));// 要上传的路径
					$('#result1').html(
							"图片上传成功! 图片名称："
									+ data.substring(data.lastIndexOf("/") + 1,
											data.length) + "<br/>");
				} else {
					$('#result1').html(data.substring(2));
				}
			},
			error : function(data, status, e) { // 服务器响应失败时的处理函数
				$('#result1').html(data);
			}
		});
	}
}





function addTbInstituteInfo() {
	// 开启验证框强制验证
		
			var bool = $("#add-TbInstituteInfo-form").form('enableValidation').form('validate');
			if (bool) {
				//true就直接通过了
			} else {
				// 提示数据不完整
				$.messager.alert('提示', '您填写的数据格式有误或不完整，请检查!', 'warning');
				return false;
			}
		


	// 表单提交时禁用按钮，防止表单重复提交
	$('#formSubmit_button').attr("disabled", true);
	// inday = $('#inDay').datebox('getValue');
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/tbInstituteInfo/ajax_add_tbInstituteInfo',
		data : $("#add-TbInstituteInfo-form").serialize(),
		success : function(data) {
			// 解除按钮禁用
			$('#formSubmit_button').attr("disabled", false);
			if (data["code"] == '100') {
				parent.addTabFun({
					src : ctx + "/tbInstituteInfo/index/list",
					title : "学院列表"
				});
			} else if (data["code"] == '101') {
				$.messager.alert('提示', data["msg"], 'warning');
			} else if (data["code"] == '102') {
				$.messager.alert('提示', data["msg"], 'warning');
			} else if (data["code"] == '103') {
				$.messager.alert('提示', '本次操作出现不明错误，请联系技术人员解决!', 'warning');
			}
		}
	});
}
