$(function(){
	init_UI();
	
	
	
	
	classRoom();
	

	$("#button-remove-classRoom").click(function(){
		var ClassRoom_list = $("#room-cell-list a[name='b']");
		var arrClassRoom = new Array();
		ClassRoom_list.each(function(){
			arrClassRoom.push($(this).attr("id"));
		});
		var str_title;
		if(arrClassRoom.length == 1){
			str_title = "确认要删除【"+$($(ClassRoom_list[0]).find(".className")[0]).text()+"】吗?";
		}else if(arrClassRoom.length > 1){
			str_title = "确认要删除选中的所有教室吗?";
		}
		console.log(ClassRoom_list);
		console.log(ClassRoom_list[0]);
		console.log($(ClassRoom_list[0]).find(".className")[0]);
		console.log();
		
		if (arrClassRoom.length > 0) {
			$.messager.confirm("删除",str_title,function(boolean){
				if(boolean){
						// 确认删除
						$.ajax({
							url:ctx+"/TbBuildingInfo/delClassRoom",
							type:"get",
							dataType:'json',
							data:{
								class_room:arrClassRoom
							},
							success:function(response){
								if (response.code==100) {
									$.messager.alert("提示",response.message,"info",function(){
										$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
									});
								} else if (response.code==108) {
									var message = "";
									response.data.map(function(item){
										message += "【 "+item.tciClassname+"】,"
									});
									message += "正在被使用，不能删除！"
									$.messager.alert("提示",message,"error");
								} else  {
									$.messager.alert("提示",response.message,"error");
								}
							},
							error:function(){
								$.messager.alert("提示","删除失败,请求错误","error");
							}
						});
				}
			});
		} else {
			$.messager.alert("提示","请先选择要删除的教室","error");
			return ;
		}
		
	});
	
	
	
	$("#button-add-classRoom").click(function(){
		addRoomInfo();
	});
	
	
	
	$("#button-relieve-classRoom").click(function(){
		var ClassRoom_list = $("#room-cell-list a[name='b']");
		var arrClassRoom = new Array();
		ClassRoom_list.each(function(){
			arrClassRoom.push($(this).attr("id"));
		});
		
		if (arrClassRoom.length  > 0){
			$.messager.confirm("解绑","确定解绑所有选中的教室吗？",function(boolean){
				if(boolean){
					// 确认解绑
					$.ajax({
						url:ctx+"/TbBuildingInfo/relieveClassRoom",
						type:"get",
						dataType:'json',
						data:{
							class_room:arrClassRoom
						},
						success:function(response){
							if (response.code==100) {
								$.messager.alert("提示",response.message,"info",function(){
									$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
								});
							} else if (response.code==108) {
								var message = "";
								response.data.map(function(item){
									message += "【"+item.tciClassname+"】,"
								});
								message += "未被占用，请重新选择！"
									$.messager.alert("提示",message,"error");
							} else  {
								$.messager.alert("提示",response.message,"error");
							}
						},
						error:function(){
							$.messager.alert("提示","解绑失败,请求错误","error");
						}
					});
					
				}
			});
		}else{
			$.messager.alert("提示","请选择要解绑的教室","error");
			return ;
		}
		
		
	});
	
	//绑定单击事件
	$("#room-cell-list").on("click","a",function(){
		if($(this).attr("name") == 'a'){
			$(this).attr("name","b");
			$(this).css({
				//未选中
				'border':'solid 1px green'
			});
		}else{
			$(this).attr("name","a");
			if($(this).attr("class") == "roomA"){
				$(this).css({
					'border':'solid 1px #ddd',
					'color':'#000000',
					'background-color': 'white'
				});
			}else{
				$(this).css({
					'border':'solid 1px #ddd',
					'color':'#000000',
					'background-color': '#88C4ED'
				});
			}
			
		}
	});
	
	
	
	
	
	
	
	
	
});




function addRoomInfo(){
	parent.addTabFun({
		src:ctx+'/TbClassroomInfo/index/add',
		title:'新增教室'
	});
}


var centerTabs;

function addTabFun(opts) {
	var options = $.extend({
		title : '',
		content : '<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
		closable : true,
		iconCls : ''
	}, opts);
	if (centerTabs.tabs('exists', options.title)) {
		centerTabs.tabs('close', options.title);
	}
	centerTabs.tabs('add', options);
};

function closeTab(title){
	centerTabs.tabs('close', title);
}






function classRoom(){
	$('#room-cell-list .room-cell').live('click',function(){
		$(this).toggleClass("room-select");
		$(this).next().trigger('click');
	});

}



//楼宇表格列表公用数据请求方法
var ajax_loader_BuildingInfo_list = function(param,success,error){
	$.ajax({
		url: ctx+'/TbBuildingInfo/list',
		dataType: 'json',
		type:"get",
		data:param,
		success: function(response){
				if(response.code==100){
					success(response.data);
				}else{
					// 数据查询失败
					$.messager.alert("提示",response.message,"error");
					$("#table_BuildingInfo_list").datagrid("loaded");
					return ;
				}
			},
	        error: function(){
	        	// 请求错误
	        	$.messager.alert("提示","请求失败","error");
	        	$("#table_BuildingInfo_list").datagrid("loaded");
				return ;
			}
		});
}

//楼层表格加载数据公用方法
var ajax_loader_floorInfo_list = function (param,success,error){
	$.ajax({
		url: ctx+'/TbBuildingInfo/ajax_get_floor_list_BybuildId',
		dataType: 'json',
		type:"get",
		data:param,
		success: function(response){
				if(response.code==100){
					success(response.data);
				}else{
					// 数据查询失败
					$.messager.alert("提示",response.message,"error");
					$("#table_floorInfo_list").datagrid("loaded");
					return ;
				}
			},
	        error: function(){
	        	// 请求错误
	        	$.messager.alert("提示","请求失败","error");
	        	$("#table_FloorInfo_list").datagrid("loaded");
				return ;
			}
		});
}



//根据楼宇ID和楼层ID显示教室列表
function funShowRoomCell(building_id,floor_id,buildName){
	$("#room-cell-list").html("");
	$.ajax({
		url: ctx+'/TbBuildingInfo/ajax_get_classRoom_list_ByBuildIdAndFloorId',
		dataType: 'json',
		type:"get",
		data:{
			buildId:building_id,
			floorId:floor_id
		},
		success: function(response){
				if(response.code==100){
					var roomHtml='';
		        	for(var i=0;i<response.data.length;i++){
		        		if(response.data[i].tciUseStatus == '1'){
		        			roomHtml+='<a id="'+response.data[i].tciId+'" class="roomHavaA" name="a" href="javascript:void(0)">'+  "<span class='className'>"+response.data[i].tciClassname+"</span>"+" 容纳量：["+response.data[i].tciCount+']</a>';
		        		}else{
		        			roomHtml+='<a id="'+response.data[i].tciId+'" class="roomA" name="a" href="javascript:void(0)">'+  "<span class='className'>"+response.data[i].tciClassname+"</span>"+" 容纳量：["+response.data[i].tciCount+']</a>';
		        		}
		        		
		        	}
		        	$("#room-cell-list").append(roomHtml);
				}else{
					// 数据查询失败
					$.messager.alert("提示",response.message,"error");
					return ;
				}
			},
	        error: function(){
	        	// 请求错误
	        	$.messager.alert("提示","请求失败","error");
				return ;
			}
		});
	
	
	
	
}

//绘制班级区块图标
var fun_select_floorInfo = function(rowIndex, rowData,buildName){
	funShowRoomCell(rowData.tfiBuildingId,rowData.tfiId,buildName);
}

function init_UI(){
	$('body').layout();
	
	
	
	//楼宇搜索按钮
	$('#input-button-search').searchbox({
		searcher:function(){
			$('#table_BuildingInfo_list').datagrid("load",{
				buildName:$.trim( $("#input-button-search").val())
			});
		}
	});
	
	//楼宇添加按钮
	$('#button_add_build').linkbutton({
		iconCls:'icon-add',
		plain:true
	}).click(function(){
		$("#addBuild_window").window('open');
		$("#inputText-add-buildName").textbox("reset");
	});
	
	
	
	//楼宇添加文本框
	$("#inputText-add-buildName").textbox({
		required:true,
		missingMessage:'不能为空！',
		prompt:'请输入楼宇名称',
	});
	//楼宇添加文本框
	$("#inputText-edit-buildName").textbox({
		required:true,
		missingMessage:'不能为空！',
		prompt:'请输入楼宇名称',
	});
	
	
	
	
	
	
	
	//楼宇删除按钮
	$('#button_remove_build').linkbutton({
		iconCls:'icon-delete',
		plain:true
	}).click(function(){
		var selections = $("#table_BuildingInfo_list").datagrid("getSelections");
		$.messager.confirm("删除","确定要删除【"+selections[0].tbiName+"】吗？",function(boolean){
			if(boolean){
				// 确认删除
				$.ajax({
					url:ctx+"/TbBuildingInfo/del",
					type:"get",
					dataType:'json',
					data:{
						build_id:selections[0].tbiId
					},
					success:function(response){
						if (response.code==100) {
							$.messager.alert("提示",response.message,"info",function(){
								$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
							});
						} else {
							$.messager.alert("提示",response.message,"error");
						}
					},
					error:function(){
						$.messager.alert("提示","删除失败,请求错误","error");
					}
				});
			}
		});
		
		
		
		
	});
	
	//楼宇新增提交按钮
	$('#button-addBuild-submit').linkbutton({
		plain:true
	}).click(function(e){
		$('#button-addBuild-submit').attr("disabled",true);
		e.preventDefault();
//		addBuild_validate();
		ajax_addBuild();
		
	});
	
	//取消楼宇新增
	$('#button-addBuild-clear').linkbutton({
		plain:true
	}).click(function(){
		$("#addBuild_window").window('close');
	});
	
	//楼宇编辑按钮
	$('#button_edit_build').linkbutton({
		iconCls:'icon-edit',
		plain:true
	}).click(function(e){
		var selections = $("#table_BuildingInfo_list").datagrid("getSelections");
		if (selections.length==1) {
			console.log(selections[0]);
			$("#editBuild_window").window("open");
			$("#inputText-edit-buildName").textbox("setText",selections[0].tbiName);
			$("#inputText-edit-buildName").attr("build_id",selections[0].tbiId);
			$("#editBuild_message").text("");
		} else if (selections.length==0) {
			$.messager.alert("提示","请先选择一个楼宇！","error");
		} else {
			$.messager.alert("提示","楼宇选择异常！","error");
		}
	});
	
	//楼宇编辑提交按钮
	$('#button-editBuild-submit').click(function(e){
		$('#button-editBuild-submit').attr("disabled",true);
		e.preventDefault();
//		editBuild_validate();
		ajax_editBuild();
		
	});
	
	//取消楼宇编辑
	$('#button-editBuild-clear').click(function(){
		$("#editBuild_window").window('close');
	});
	
	
	
	function ajax_addBuild(){
		var build_name = $("#inputText-add-buildName").val();
		
		$.ajax({
			url:ctx+"/TbBuildingInfo/add",
			type:"get",
			dataType:'json',
			data:{
				build_name:build_name
			},
			success:function(response){
				if (response.code==100) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
					});
					
				} else if (response.code==120) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/login');
					});
				} else if (response.code == 140) {
					$("#addBuild_message").text(response.message);
				} else {
					$.messager.alert("提示",response.message,"error");
				}
				$("#button-addBuild-submit").attr("disabled",false);
			},
			error:function(){
				$.messager.alert("提示","新增失败,请求错误","error");
				$("#button-addBuild-submit").attr("disabled",false);
			}
		});
	}
	
	function ajax_editBuild(){
		var build_name = $("#inputText-edit-buildName").textbox("getText");
		var build_id = $("#inputText-edit-buildName").attr("build_id");
		$.ajax({
			url:ctx+"/TbBuildingInfo/edit",
			type:"get",
			dataType:'json',
			data:{
				build_name:build_name,
				build_id:build_id
			},
			success:function(response){
				if (response.code==100) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
					});
					
				} else if (response.code==120) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/login');
					});
				} else if (response.code == 140) {
					$("#editBuild_message").text(response.message);
				} else {
					$.messager.alert("提示",response.message,"error");
				}
				$("#button-editBuild-submit").attr("disabled",false);
			},
			error:function(){
				$.messager.alert("提示","新增失败,请求错误","error");
				$("#button-editBuild-submit").attr("disabled",false);
			}
		});
	}
	
	//新增楼宇表单数据验证
	function addBuild_validate(){
		$('#add_build_form').form('submit',{
			onSubmit:function(){
				var bool= $(this).form('enableValidation').form('validate');
				if(!bool){
					$.messager.alert("提示","请提交符合规范的数据！","error");
					$("#button-addBuild-submit").attr("disabled",false);
				}else{
					ajax_addBuild();
				}
			}
		});
	}

	
	//楼宇编辑表单数据验证
	function editBuild_validate(){
		$('#edit_build_form').form('submit',{
			onSubmit:function(){
				var bool= $(this).form('enableValidation').form('validate');
				if(!bool){
					$.messager.alert("提示","请提交符合规范的数据！","error");
					$("#button-editBuild-submit").attr("disabled",false);
				}else{
					ajax_editBuild();
				}
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//楼宇表格初始化
	$('#table_BuildingInfo_list').datagrid({
		loader:ajax_loader_BuildingInfo_list,
		title:false,
		idfield:'tbiId',
		columns:[[
			{
				field:'tbiName',
				title:'楼名称',
				width:150,
				align:'center',
			}
		]],
		queryParams:{
			buildName:$.trim($("#input-button-search").val())
		},
		fitColumns: true,
	    scrollbarSize: 0,
	    toolbar:'#div_left_datagrid_toolbar',
	    singleSelect:true,
	    fit:true,
	    striped : true,
	    fitColumns : true,
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
		},
		onSelect : function(rowIndex, buildRowData){
			$("#room-cell-list").html("");
			//楼层数据表格
			$('#table_floorInfo_list').datagrid({
				loader:ajax_loader_floorInfo_list,
				title:false,
				idfield:'tfiId',
				showHeader:false,
				columns:[[
					{
						field:'tfiName',
						title:'楼层名称',
						width:150,
						align:'center',
					}
					]],
					queryParams:{
						buildId:buildRowData.tbiId
					},
					fitColumns: true,
					scrollbarSize: 0,
					toolbar:'#div_center_datagrid_toolbar',
					singleSelect:true,
					fit:true,
					striped : true,
					fitColumns : true,
				    sortName : 'tfiNameId',
				    sortOrder: 'asc',
				    remoteSort: true,
					rowStyler:function(index,row){
						if((index % 2) != 0) {
							return 'background-color:#FAFAD2;';
						} 
					},//选中楼层时
					onSelect:function(rowIndex, floorRowData){
						funShowRoomCell(buildRowData.tbiId,floorRowData.tfiId,buildRowData.tbiName);
					},
					onLoadSuccess:function(){
						$('#table_floorInfo_list').datagrid("selectRow",0);
					}
			});
		},
		onLoadSuccess:function(data){
			if(data.rows.length>0){
				$("#room-cell-list").html("");
				$('#table_BuildingInfo_list').datagrid("selectRow",0);
			}else{
				$("#room-cell-list").html("");
				$.messager.alert("提示","未查询到相关数据，请重试","error");
				
			}
		}
	});
	
	//楼层添加按钮
	$('#button_add_floor').linkbutton({
		iconCls:'icon-add',
		plain:true
	}).click(function(){
		var selections = $("#table_BuildingInfo_list").datagrid("getSelections");
		$("#combobox-addFoor-buildID").combobox("select",selections[0].tbiId);
		$("#addFloor_window").window('open');
		$("#combobox-addFoor-FoorName").textbox("reset");
	});
	
	//取消楼层新增
	$('#button-addFloor-clear').linkbutton({
		plain:true
	}).click(function(){
		$("#addFloor_window").window('close');
	});
	
	//楼层删除按钮
	$('#button_remove_floor').linkbutton({
		iconCls:'icon-delete',
		plain:true
	}).click(function(){
		
		var buildingInfoSelections = $("#table_BuildingInfo_list").datagrid("getSelections");
		var floorInfoSelections = $("#table_floorInfo_list").datagrid("getSelections");
		
		
		if (buildingInfoSelections.length==1) {
			$("#inputText-edit-buildName").textbox("setText",buildingInfoSelections[0].tbiName);
			$("#inputText-edit-buildName").attr("build_id",buildingInfoSelections[0].tbiId);
		} else if (buildingInfoSelections.length==0) {
			$.messager.alert("提示","请先选择一个楼宇！","error");
			return ;
		} else {
			$.messager.alert("提示","楼宇选择异常！","error");
			return ;
		}
		
		if (floorInfoSelections.length==1) {
			$("#inputText-edit-buildName").textbox("setText",floorInfoSelections[0].tbiName);
			$("#inputText-edit-buildName").attr("build_id",floorInfoSelections[0].tbiId);
		} else if (floorInfoSelections.length==0) {
			$.messager.alert("提示","请先选择一个楼层！","error");
			return ;
		} else {
			$.messager.alert("提示","楼层选择异常！","error");
			return ;
		}
		
		
		
		$.messager.confirm("删除","确定要删除此楼层吗？",function(boolean){
			if(boolean){
				// 确认删除
				
				$.ajax({
					url:ctx+"/TbBuildingInfo/delFloor",
					type:"get",
					dataType:'json',
					data:{
						buildId:buildingInfoSelections[0].tbiId,
						floorId:floorInfoSelections[0].tfiId
					},
					success:function(response){
						if (response.code==100) {
							$.messager.alert("提示",response.message,"info",function(){
								$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
							});
						} else if (response.code==120) {
							$.messager.alert("提示",response.message,"info",function(){
								$(location).attr('href', ctx+'/login');
							});
						} else {
							$.messager.alert("提示",response.message,"error");
						}
					},
					error:function(){
						$.messager.alert("提示","删除失败,请求错误","error");
					}
				});
			}
		});
		
		
		
		
	});
	
	//楼宇下拉框
	$("#combobox-addFoor-buildID").combobox({
		loader:ajax_loader_build_list,
		valueField:'tbiId',
		textField:'tbiName',
		method:'get',
		mode:'remote',
		panelHeight:'auto',
		prompt:'请选择所属楼宇',
		onLoadSuccess:function(){
			
		}
	});
	
	
	//楼层新增提交按钮
	$('#button-addFloor-submit').linkbutton({
		plain:true
	}).click(function(e){
		$('#button-addFloor-submit').attr("disabled",true);
		e.preventDefault();
//		ajax_addFloor();
		
//		var isComboboxEmpty = $.trim($("#combobox-addFoor-buildID").combobox("getValue")) != '' 
//			&& $.trim($("#combobox-addFoor-FoorName").textbox("getText")) != '' ;
//		if(isComboboxEmpty){
////			alert("通过");
//			ajax_addFloor();
//		}else{
//			$.messager.alert("提示","请提交符合规范的数据！","error");
//			$("#button-addFloor-submit").attr("disabled",false);
//			return ;
//		}
		
		ajax_addFloor();
		
		
	});
	
	//新增楼层表单数据验证
	function addFloor_validate(){
		$('#add_floor_form').form('submit',{
			onSubmit:function(){
				var bool= $(this).form('enableValidation').form('validate');
				
				
//				alert(bool);
				
			}
		});
	}
	
	function ajax_addFloor(){
		var buildId = $.trim($("#combobox-addFoor-buildID").combobox("getValue"));
		var floorName = $.trim($("#combobox-addFoor-FoorName").textbox("getText"));
		
		$.ajax({
			url:ctx+"/TbBuildingInfo/addFloor",
			type:"get",
			dataType:'json',
			data:{
				buildId:buildId,
				floorName:floorName
			},
			success:function(response){
				if (response.code==100) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/TbBuildingInfo/index/list');
					});
				} else if (response.code==120) {
					$.messager.alert("提示",response.message,"info",function(){
						$(location).attr('href', ctx+'/login');
					});
				} else if (response.code == 140) {
					$("#addFloor_message").text(response.message);
				} else {
					$.messager.alert("提示",response.message,"error");
				}
				$("#button-addFloor-submit").attr("disabled",false);
			},
			error:function(){
				$.messager.alert("提示","新增失败,请求错误","error");
				$("#button-addFloor-submit").attr("disabled",false);
			}
		});
	}
	
}

//请求数据
var ajax_loader_build_list = function(param,success,error){
	$.ajax({
		url: ctx+'/TbBuildingInfo/ajax_get_budiling_list',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				// 数据查询成功
				
				var items = $.map(response.data,function(item){
					return {
						tbiId:item.tbiId,
						tbiName:item.tbiName
					}
				});
				success(items);
			}else{
				// 数据查询失败
				$.messager.alert("提示",response.message,"error");
				return ;
			}
		},
        error: function(){
        	// 请求错误
        	$.messager.alert("提示","请求失败","error");
			return ;
		}
	});
}









