function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
};

// 跳出更改状态的窗口，并回显
function ajaxGetStudentIdNameAndStatus(btn, ts_id) {
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbStudent/ajax_get_SutdentIdNameAndStatus',
		data : {
			"ts_id" : ts_id
		},
		success : function(result) {
			var ts_id = result.tsId;
			var ts_name = result.tsName;
			var ts_student_status = result.tsStudentStatus;
			var ts_number = result.tsNumber;
			$('#ts_id').val(ts_id);
			$('#ts_name').textbox('setValue', ts_name);
			$('#ts_number').textbox('setValue', ts_number);
			$('#ts_student_status').combobox('select', ts_student_status);
			$('#editStatusWindow').window('open');
		}
	});
}

// 更改状态
function editStudentStatus() {
	// 开启验证框强制验证
	var bool = $("#edit_studentStatus_windowForm").form('enableValidation')
			.form('validate');
	if (bool) {
		// true就直接通过了
	} else {
		// 提示数据不完整
		$.messager.alert('提示', '您填写的数据格式有误或不完整，请检查!', 'warning');
		return false;
	}
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbStudent/ajax_edit_studentStatus',
		data : $("#edit_studentStatus_windowForm").serialize(),
		success : function(data) {
			// $('#formSubmit_button').attr("disabled", false);
			if (data["code"] == '100') {
				// 添加成功
				$.messager.alert('提示', data["msg"], 'warning');
				// 跳转列表刷新
				parent.addTabFun({
					src : ctx + "/TbStudent/index/list",
					title : "学生管理"
				});
			} else if (data["code"] == '101') {
				$.messager.alert('提示', data["msg"], 'warning');
			} else if (data["code"] == '102') {
				$.messager.alert('提示', data["msg"], 'warning');
			}
		}
	});
}
// 取消更改状态
function cancelEditStatusStudent() {
	$('#editStatusWindow').window('close');
}

// 跳出重置密码的窗口，并回显
function beforeResetPWD(btn, ts_id) {
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbStudent/ajax_get_SutdentIdNameAndStatus',
		data : {
			"ts_id" : ts_id
		},
		success : function(result) {
			var ts_id = result.tsId;
			var ts_name = result.tsName;
			var ts_number = result.tsNumber;
			$('#reid').val(ts_id);
			$('#rename').textbox('setValue', ts_name);
			$('#renumber').textbox('setValue', ts_number);
			$('#resetPWDWindow').window('open');
		}
	});
}

// 重置密码
function resetPWD() {
	// 获得要重置密码的学生的id
	var reid = $('#reid').val();
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : ctx + '/TbStudent/ajax_reset_studentPWD',
		data : {
			"ts_id" : reid
		},
		success : function(data) {
			// $('#formSubmit_button').attr("disabled", false);
			if (data["code"] == '100') {
				// 密码重置成功
				$.messager.alert('提示', data["msg"], 'warning');
				$('#resetPWDWindow').window('close');
			} else if (data["code"] == '101') {
				$.messager.alert('提示', data["msg"], 'warning');
			} else if (data["code"] == '102') {
				$.messager.alert('提示', data["msg"], 'warning');
			}
		}
	});
}

// 取消重置密码
function cancelRecetPWD() {
	$('#resetPWDWindow').window('close');
}

// list表格载入数据
function loadData() {

	// 开启强制验证
	var bool = $("#list_student_form").form('enableValidation')
			.form('validate');
	if (bool) {
		// true就直接通过了
	} else {
		// 提示数据不完整
		$.messager.alert('提示', '您提交的数据格式有误，请检查!', 'warning');
		return false;
	}

	var AddDate = $("#AddDate").datebox('getValue');// 创建时间--开始
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');// 创建时间---结束
	// 如果有结束时间没有开始时间
	if ((AddDate == '') && (AddDateEnd != '')) {
		// 提醒用户
		$.messager.alert("提示", "请选择开始时间", "info");
		return false;
	}
	// 如果有开始时间没有结束时间
	if ((AddDate != '') && (AddDateEnd == '')) {
		$("#AddDateEnd").datebox('setText', formatDate(new Date()));
		$("#AddDateEnd").datebox('setValue', formatDate(new Date()));
		// 结束时间设为现在
		AddDateEnd = formatDate(new Date());
	}
	// 开始时间和技术时间都不为空
	if ((AddDate != '') && (AddDateEnd != '')) {
		// 结束时间大于开始时间
		if (AddDateEnd < AddDate) {
			$.messager.alert("提示", "结束时间不能小于开始时间", "info");
			$("#AddDateEnd").datebox('setText', '');
			$("#AddDateEnd").datebox('setValue', '');
			return false;
		}
		// 时间跨度不能超过1年
		var start = new Date(AddDate.replace(/-/g, "/")).getTime();
		var end = new Date(AddDateEnd.replace(/-/g, "/")).getTime();
		if (end - start > 12 * 24 * 30.416666 * 60 * 60 * 1000) {
			$.messager.alert("提示", "时间跨度不能超过一年", "info");
			// 不合格把结束日期设置为空
			$("#AddDate").datebox('setText', '');
			$("#AddDate").datebox('setValue', '');
			$("#AddDateEnd").datebox('setText', '');
			$("#AddDateEnd").datebox('setValue', '');
			return false;
		}
	}
	// 学号
	var stuNumber = $("#stuNumber").textbox('getValue');
	// 姓名
	var likeName = $("#likeName").textbox('getValue');
	// 性别
	var sexLike = $("#sexLike").textbox('getValue');
	// 班级
	var likeClass = $("#likeClass").textbox('getValue');
	// 身份证号码
	var idCard = $("#idCard").textbox('getValue');
	// 学生状态
	var studentStatus = $("#studentStatus").textbox('getValue');

	$('#tb_student_info')
			.datagrid(
					{
						url : ctx + '/TbStudent/ajax_get_studentList',
						columns : [ [
								{
									field : 'ts_id',
									title : '序列号',
									align : 'center',
									resizable : 'false',
									hidden : true
								},
								{
									field : 'ts_number',
									title : '学号',
									width : 250,
									align : 'center',
									resizable : 'false',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'ts_name',
									title : '姓名',
									width : 150,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'ts_sex',
									title : '性别 ',
									width : 150,
									align : 'center',
									sortable : true,
									formatter : function(value, rec) {
										if (value == 1) {
											return "男";
										} else if (value == 0) {
											return "女";
										}
									}
								},
								{
									field : 'ts_card_num',
									title : '身份证号',
									width : 200,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'tc_name',
									title : '所属班级',
									width : 116,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'ts_phone',
									title : '手机号码',
									width : 138,
									align : 'center',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'tw_name',
									title : '学生状态',
									width : 93,
									align : 'center',
									sortable : true
								},
								{
									field : 'ts_addtime',
									title : '创建时间',
									width : 151,
									align : 'center',
									sortable : true,
									formatter : formatDatebox
								},
								{
									field : 'opt',
									title : '操作',
									width : 400,
									align : 'center',
									formatter : function(value, rec) {
										var btnHtml = "";
										// if (btnList) {
										// for (var i = 0; i < btnList.length;
										// i++) {
										// if (btnList[i].name == "编辑") {
										btnHtml += '<a  onclick="editStudentInfo(this,\''
												+ rec.ts_id
												+ '\')" href="javascript:void(0)">编辑&nbsp;|&nbsp;</a>';
										// data-path="'
										// + btnList[i].url
										// + '" data-menuId="'
										// + btnList[i].menuId
										// + '"

										// }
										// if (btnList[i].name == "删除") {
										btnHtml += '<a  onclick="delInstituteInfo(this,\''
												+ rec.ts_id
												+ '\',\''
												+ rec.ts_name
												+ '\')" href="javascript:void(0)">删除&nbsp;|&nbsp;</a>';
										// data-path="'
										// + btnList[i].url
										// + '" data-menuId="'
										// + btnList[i].menuId
										// + '"

										// }
										// }
										// }
										btnHtml += '<a  onclick="delInstituteInfo(this,\''
												+ rec.ts_id
												+ '\',\''
												+ rec.ts_name
												+ '\')" href="javascript:void(0)">详情&nbsp;|&nbsp;</a>';

										btnHtml += '<a  onclick="beforeResetPWD(this,\''
												+ rec.ts_id
												+ '\',\''
												+ rec.ts_name
												+ '\')" href="javascript:void(0)">重置密码&nbsp;|&nbsp;</a>';

										btnHtml += '<a  onclick="ajaxGetStudentIdNameAndStatus(this,\''
												+ rec.ts_id
												+ '\',\''
												+ rec.ts_name
												+ '\')" href="javascript:void(0)">更改状态&nbsp;|&nbsp;</a>';

										btnHtml += '<a  onclick="ajaxGetStudentIdNameAndStatus(this,\''
												+ rec.ts_id
												+ '\',\''
												+ rec.ts_name
												+ '\')" href="javascript:void(0)">家长信息</a>';

										return btnHtml;
									}
								} ] ],
						queryParams : {
							'AddDate' : AddDate,
							'AddDateEnd' : AddDateEnd,
							'stuNumber' : stuNumber,
							'likeName' : likeName,
							'sexLike' : sexLike,
							'likeClass' : likeClass,
							'idCard' : idCard,
							'studentStatus' : studentStatus
						},// 查询
						rownumbers : true,
						singleSelect : true,
						fit : true,
						striped : true,
						fitColumns : false,
						pagination : true,
						sortName : 'ts_addtime',
						sortOrder : 'desc',
						remoteSort : true,
						rowStyler : function(index, row) {
							if ((index % 2) != 0) {
								return 'background-color:#FAFAD2;';
							}
						},
						pageSize : 20,
						pageList : [ 10, 15, 20, 30, 40, 50 ],
						onLoadSuccess : function(data) {
							$('.addcls')
									.linkbutton(
											{
												text : '&nbsp;&nbsp;新&nbsp;&nbsp;增&nbsp;&nbsp;',
												plain : true,
												iconCls : 'icon-add'
											});
							$('.searchcls').linkbutton({
								text : '&nbsp;&nbsp查&nbsp;&nbsp;询&nbsp;&nbsp;',
								plain : true,
								iconCls : 'icon-search'
							});
							$('.manyImport').linkbutton({
								text : '&nbsp;&nbsp批量导入&nbsp;&nbsp;',
								plain : true,
							});
						}
					});
}

// a标签清楚方法
// function clearInputName() {
// $("#InstituteNameLike").textbox("reset");
// $("#a").hide();
// }

// 前台返回时间戳，转日期第1部分
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
// 前台返回时间戳，转日期第2部分
function formatDatebox(value) {
	if (value == null || value == '') {
		return '';
	}
	var dt;
	if (value instanceof Date) {
		dt = value;
	} else {
		dt = new Date(value);
	}
	return dt.format("yyyy-MM-dd hh:mm:ss");
}

function openAddPage() {
	// var path = $('.addcls').attr('data-path');
	// var menuId = $('.addcls').attr('data-menuId');
	parent.addTabFun({
		src : ctx + "/TbStudent/index/add",
		// + path + "&menuId="
		// + menuId,
		title : "学生新增"
	});
}

function editStudentInfo(btn, ts_id) {
	parent.addTabFun({
		src : ctx + "/TbStudent/index/edit?ts_id=" + ts_id,
		title : "学生编辑"
	});
}

function delInstituteInfo(btn, ts_id, ts_name) {
	// var path = $(btn).attr('data-path');
	$.messager.confirm("提示", "确定要删除【" + ts_name + "】吗？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				dataType : 'JSON',
				url : ctx + '/' + '/TbStudent/ajax_del_studentForId',
				data : {
					ts_id : ts_id
				},
				success : function(data) {
					if (data["code"] == '100') {
						alert("删除成功");
						parent.addTabFun({
							src : ctx + "/TbStudent/index/list",
							title : "学生管理"
						});
					} else if (data["code"] == '101') {
						$.messager.alert('提示', data["msg"], 'info');
						return;
					} else if (data["code"] == '102') {
						$.messager.alert('提示', data["msg"], 'warning');
						return;
					} else if (data["code"] == '103') {
						$.messager.alert('提示', data["msg"], 'warning');
					}
				}

			});
		}
	});
}

$(function() {
	// 更改状态时的下拉框
	$('#ts_student_status').combobox({
		url : ctx + '/words/ajax_get_statusList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});

	loadData();
	// 设置按钮
	// 条件检索框下拉加载数据库数据
	// 搜索框请选择学生状态
	$('#studentStatus').combobox({
		url : ctx + '/words/ajax_get_statusList',
		valueField : 'tw_id',
		textField : 'tw_name'
	});
	// 请选择状态性别
	$('#sexLike').combobox({
		valueField : 'tci_id',
		textField : 'tci_name',
		data : [ {
			tci_id : '1',
			tci_name : '男'
		}, {
			tci_id : '0',
			tci_name : '女'
		} ]
	});
	// 请选择班级
	$('#likeClass').combobox({
		url : ctx + '/class/ajax_get_classList',
		valueField : 'tc_id',
		textField : 'tc_name'
	});
});
