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
					$("#tsPhoto").val(data.substring(2));// 要上传的路径
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

//回显手机号
function showLoginUser() {
	// alert("我不好");
	var loginUser = $('#tsPhone').val();
	$('#tsLoginUser').textbox('setValue', loginUser);
	$('#tsLoginUser').textbox('setText', loginUser);
}

//回显用户密码，取得用户身份证号码，截12，和生日
function showBirthdayAndPWD() {
	// alert("我也不好");
	var tsCardNum = $('#tsCardNum').val();
	var birthday= tsCardNum.substr(6,8);
	var pwd= tsCardNum.substr(12,6);
	$('#tsLoginPass').textbox('setValue', pwd);
	$('#tsLoginPass').textbox('setText', pwd);
	$('#tsBirthday').textbox('setValue', birthday);
	$('#tsBirthday').textbox('setText', birthday);
	alert(tsCardNum);
}


$(function() {
	//给性别单选赋值
	
	
	
	// 民族下拉框
	$('#tsNation').combobox({
		url : ctx + '/words/ajax_get_tsNationList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	// 政治面貌下拉框
	$('#tsPolitical').combobox({
		url : ctx + '/words/ajax_get_tsPoliticalList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	// 户口性质下拉框
	$('#tsHjProperty').combobox({
		url : ctx + '/words/ajax_get_tsHjPropertyList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	// 入学学历下拉框
	$('#tsInDegree').combobox({
		url : ctx + '/words/ajax_get_tsInDegreeList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	// 家庭收入来源下拉框
	$('#tsFamilyIncome').combobox({
		url : ctx + '/words/ajax_get_tsFamilyIncomeList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	// 所属专业下拉框
	// $('#tsMajorId').combobox({
	// url : ctx + '/words/ajax_get_tsFamilyIncomeList',
	// valueField : 'tw_id',
	// textField : 'tw_name'
	// });

	// 所属学院下拉框
	$('#tb_institute_info')
			.combobox(
					{
						url : ctx
								+ '/TbInstituteInfo/ajax_get_instituteIdAndNameList',
						valueField : 'tii_id',
						textField : 'tii_name',
						// 所属系别下拉框
						onSelect : function(rec) {
							$('#tc_institute_department_id')
							.combobox(
									{
										url : ctx
												+ '/tbinstituteDepartmentInfo/getInstituteDepartmentInfo_bytiiId?tidi_institute_id='
												+ rec.tii_id,
										valueField : 'tidi_id',
										textField : 'tidi_name',
										// 所属年级下拉框
										onSelect : function() {
											$('#tsGrade')
											.combobox(
													{
														url : ctx
																+ '/tbGrade/ajax_get_gradeList',
														valueField : 'tg_id',
														textField : 'tg_name',
														//所属班级下拉框
														onSelect : function() {
															//得先获得前面选中框的值，才能合在一起选最后一个
															var tiiId= $('#tb_institute_info').combobox('getValue');
															var tidiId= $('#tc_institute_department_id').combobox('getValue');
															var tgId= $('#tsGrade').combobox('getValue');
															$('#tsClassId')
															.combobox(
																	{
																		url : ctx
																				+ '/TbClass/ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId?tc_institute_id='
																				+tiiId +'&tc_institute_department_id='+tidiId+'&tc_grade='+tgId,
																		valueField : 'tc_grade',
																		textField : 'tc_name',
																	});
														}
													});
										}
									});
						}
					});

	// 地址联动
	$("#province")
			.combobox(
					{
						url : ctx + '/AcquisitionArea/chinaCityService',
						valueField : 'pid',
						textField : 'pname',
						filter : function(q, row) {
							var opts = $(this).combobox('options');
							return row[opts.textField].indexOf(q) == 0;
						},
						onSelect : function(rec) {
							$('#city')
									.combobox(
											{
												url : ctx
														+ '/AcquisitionArea/chinaCityServiceByCity?id='
														+ rec.pid,
												valueField : 'cid',
												textField : 'cname',
											});
						}
					});
	$("#city")
			.combobox(
					{
						url : ctx
								+ '/AcquisitionArea/chinaCityServiceByCity?id=',
						valueField : 'cid',
						textField : 'cname',
						filter : function(q, row) {
							var opts = $(this).combobox('options');
							return row[opts.textField].indexOf(q) == 0;
						},
						onSelect : function(rec) {
							$("#county")
									.combobox(
											{
												url : ctx
														+ '/AcquisitionArea/chinaCityServiceByCountTy?id='
														+ rec.cid,
												valueField : 'oid',
												textField : 'oname',
											});
						}
					});
});
