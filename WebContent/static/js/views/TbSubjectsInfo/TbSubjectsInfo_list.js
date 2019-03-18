function loadData() {

	
	var subjectNameLike = $("#subjectNameLike").textbox('getValue');// 用户输入框输入的名字
	
	
	$('#tb_subject_info')
			.datagrid({
					url : ctx + '/TbsubjectsInfo/ajax_get_subjectList',
						columns : [ [
								{
									field : 'tsi_name',
									title : '科目名称',
									width : 150,
									align : 'center',
									resizable : 'false',
									sortable : true,
								},
								{
									field : 'tsi__add_date',
									title : '创建时间',
									width : 150,
									align : 'center',
									sortable : true,
									formatter:formatDatebox
								},
								{
									field : 'opt',
									title : '操作',
									width : 150,
									align : 'center',
									formatter : function(value, rec) {
										var btnHtml = "";
										//if (btnList) {
											//for (var i = 0; i < btnList.length; i++) {
												//if (btnList[i].name == "编辑") {
													btnHtml += '<a  onclick="editSubjectInfo(this,\''
															+ rec.tsi_id
															+ '\')" href="javascript:void(0)">编辑&nbsp;|&nbsp;</a>';
															//data-path="'
															//+ btnList[i].url
															//+ '" data-menuId="'
															//+ btnList[i].menuId
															//+ '" 
															
												//}
											//	if (btnList[i].name == "删除") {
													btnHtml += '<a  onclick="delSubjectInfo(this,\''
															+ rec.tsi_id
															+ '\',\''
															+ rec.tsi_name
															+ '\')" href="javascript:void(0)">删除</a>';
															//data-path="'
															//+ btnList[i].url
															//+ '" data-menuId="'
															//+ btnList[i].menuId
															//+ '" 
															
												//}
											//}
										//}
										return btnHtml;
									}
								} ] ],
						queryParams : {
							'subjectNameLike' : subjectNameLike
						},// 查询
						rownumbers : true,
						singleSelect : true,
						fit : true,
						striped : true,
						fitColumns : true,
						pagination : true,
						sortName : 'tsi__add_date',
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
							$('.searchcls')
									.linkbutton(
											{
												text : '&nbsp;&nbsp查&nbsp;&nbsp;询&nbsp;&nbsp;',
												plain : true,
												iconCls : 'icon-search'
											});
						}
					});
}




function clearInputName(){
	$("#subjectNameLike").textbox("reset");
	$("#a").hide();
	parent.addTabFun({
		src : ctx + "/TbsubjectsInfo/index/list",
		//+ path + "&menuId="
				//+ menuId,
		title : "科目管理"
	});
}







//前台返回时间戳，转日期
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()  
        // millisecond  
    }  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "")  
            .substr(4 - RegExp.$1.length));  
    for (var k in o)  
        if (new RegExp("(" + k + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
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


function delSubjectInfo(btn, tsi_id, tsi_name) {
	//var path = $(btn).attr('data-path');
	$.messager.confirm("提示", "确定要删除【" + tsi_name + "】吗？", function(r) {
		if (r) {
			$.ajax({
				type : "POST",
				dataType : 'JSON',
				url : ctx + '/' +'/TbsubjectsInfo/ajax_del_subjectForId',
				data : {
					tsi_id : tsi_id
				},
				success : function(data) {
					if (data["code"] == '100') {
						parent.addTabFun({
							src : ctx + "/TbsubjectsInfo/index/list",
							title : "科目管理"
						});
					} else if (data["code"] == '101') {
						$.messager.alert('提示', data["msg"], 'info');
						return;
					} else if (data["code"] == '102') {
						$.messager.alert('提示', data["msg"], 'warning');
						return;
					} else if (data["code"] == '103') {
						$.messager.alert('提示', data["msg"],
								'warning');
					}
				}

			});
		}

	});
}

$(function() {
	
	loadData();



	// 设置按钮
	//if (btnList) {
		//for (var i = 0; i < btnList.length; i++) {
			//if (btnList[i].name == "新增") {
				$('#div_toobar')
						.append(
								'&nbsp; <a class="easyui-linkbutton addcls" plain="true" onclick="addInstituteInfo()">新增</a>');
								//data-path="'
										//+ btnList[i].url
										//+ '" data-menuId="'
										//+ btnList[i].menuId
										//+ '"
								
			//}
		//}
	//}
});