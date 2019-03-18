function formatDate(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	m = m < 10 ? '0' + m : m;
	var d = date.getDate();
	d = d < 10 ? ('0' + d) : d;
	return y + '-' + m + '-' + d;
};

function loadData() {
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
		if (end - start > 12 * 30.416666 * 24 * 60 * 60 * 1000) {
			$.messager.alert("提示", "时间跨度不能超过一年", "info");
			// 不合格把结束日期设置为空
			$("#AddDate").datebox('setText', '');
			$("#AddDate").datebox('setValue', '');
			$("#AddDateEnd").datebox('setText', '');
			$("#AddDateEnd").datebox('setValue', '');
			return false;
		}
	}
	// 用户输入框输入的学院名字
	var InstituteNameLike = $("#InstituteNameLike").textbox('getValue');
	// 如果用户输入了学院名
	if (InstituteNameLike != '') {
		// 判断用户输入的长度
		if (InstituteNameLike.length > 10) {
			$.messager.alert("提示", "您好，学院名称只允许输入10字以内的汉字!", "info");
			return false;
		}
	}
	$('#tb_institute_info')
			.datagrid(
					{
						url : ctx + '/tbInstituteInfo/ajax_get_instituteList',
						columns : [ [
								{
									field : 'tii_id',
									title : '序列号',
									width : 150,
									align : 'center',
									resizable : 'false',
									sortable : true,
									hidden : true
								},
								{
									field : 'tii_name',
									title : '学院名称',
									width : 150,
									align : 'center',
									resizable : 'false',
									sortable : true,
									formatter : function(value, row, index) {
										return '<span title=' + value + '>'
												+ value + '</span>'
									}
								},
								{
									field : 'tii_icon',
									title : '学院图标',
									width : 150,
									align : 'center',
									formatter : function(value, rec) {
										if (value != null || value == '') {
											var btn = '';
											btn += '<img height="22" width="22" src="'
													+ value + '"/>';
											return btn;
										}
										// 没有图片的情况下默认的图片
										// else {
										// return '<img height="22" width="22"
										// src="'
										// + ctx
										// +
										// '/static/images/czzxdefault1.jpg">';
										// }

									}
								},
								{
									field : 'tii_order',
									title : '排序',
									width : 150,
									align : 'center',
									sortable : true
								},
								{
									field : 'tii_add_date',
									title : '创建时间',
									width : 150,
									align : 'center',
									sortable : true,
									formatter : formatDatebox
								},
								{
									field : 'opt',
									title : '操作',
									width : 150,
									align : 'center',
									formatter : function(value, rec) {
										var btnHtml = "";
										// if (btnList) {
										// for (var i = 0; i < btnList.length;
										// i++) {
										// if (btnList[i].name == "编辑") {
										btnHtml += '<a  onclick="editInstituteInfo(this,\''
												+ rec.tii_id
												+ '\')" href="javascript:void(0)">编辑&nbsp;|&nbsp;</a>';
										// data-path="'
										// + btnList[i].url
										// + '" data-menuId="'
										// + btnList[i].menuId
										// + '"

										// }
										// if (btnList[i].name == "删除") {
										btnHtml += '<a  onclick="delInstituteInfo(this,\''
												+ rec.tii_id
												+ '\',\''
												+ rec.tii_name
												+ '\')" href="javascript:void(0)">删除</a>';
										// data-path="'
										// + btnList[i].url
										// + '" data-menuId="'
										// + btnList[i].menuId
										// + '"

										// }
										// }
										// }
										return btnHtml;
									}
								} ] ],
						queryParams : {
							'AddDate' : AddDate,
							'AddDateEnd' : AddDateEnd,
							'InstituteNameLike' : InstituteNameLike
						},// 查询
						rownumbers : true,
						singleSelect : true,
						fit : true,
						striped : true,
						fitColumns : true,
						pagination : true,
						sortName : 'tii_add_date',
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
						}
					});
}

function clearInputName() {
	$("#InstituteNameLike").textbox("reset");
	$("#a").hide();
}

// 前台返回时间戳，转日期
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

function addInstituteInfo() {
	// var path = $('.addcls').attr('data-path');
	// var menuId = $('.addcls').attr('data-menuId');
	parent.addTabFun({
		src : ctx + "/tbInstituteInfo/index/add",
		// + path + "&menuId="
		// + menuId,
		title : "学院新增"
	});
}

function editInstituteInfo(btn, tii_id, tii_name) {
	parent.addTabFun({
		src : ctx + "/tbInstituteInfo/index/edit?tii_id=" + tii_id,
		title : "学院编辑"
	});
}

function delInstituteInfo(btn, tii_id, tii_name) {
	// var path = $(btn).attr('data-path');
	$.messager.confirm("提示", "确定要删除【" + tii_name + "】吗？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				dataType : 'JSON',
				url : ctx + '/' + '/tbInstituteInfo/ajax_del_instituteForId',
				data : {
					tii_id : tii_id
				},
				success : function(data) {
					if (data["code"] == '100') {
						parent.addTabFun({
							src : ctx + "/tbInstituteInfo/index/list",
							title : "学院管理"
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

	loadData();

	// 设置按钮
	// if (btnList) {
	// for (var i = 0; i < btnList.length; i++) {
	// if (btnList[i].name == "新增") {
	$('#div_toobar')
			.append(
					'&nbsp; <a class="easyui-linkbutton addcls" plain="true" onclick="addInstituteInfo()">新增</a>');
	// data-path="'
	// + btnList[i].url
	// + '" data-menuId="'
	// + btnList[i].menuId
	// + '"

	// }
	// }
	// }
});