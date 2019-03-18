
$(function(){
	
	$(".trWidth3").hide();
	boxShowAll1();
	
	$("#tmType").combobox({
		data:[{id:1,text:"普通会员"},
		      {id:2,text:"经销商"},
			  {id:3,text:"水工"}],
		valueField:'id',
		textField:'text',
	});
	loadData();
});

function loadData(){
	
	var tmType = $("#tmType").combobox('getValue');//会员类型
	var tmAccount = $("#tmAccount").val();//登录账号
	
	$('#data-list').datagrid({
	    url:ctx+'/message/memberList',
	    columns:[[
	              {field:'box',checkbox:true},
	              {field:'tmName',title:'姓名',width:100,align:'center',resizable:'false'},
	              {field:'tmAccount',title:'登录账号',width:100,align:'center',resizable:'false'},	          
	  	          {field:'toSalesStatus',title:'会员类型',width:60,align:'center',
	  	        	formatter:function(value,rec){      		
	  	        		if(rec.tmType==1){
	          			    return "普通会员";
	  	        		}else if(rec.tmType==2){
	  	        			return "经销商";
	  	        		}else if(rec.tmType==3){
	  	        			return "水工";
	  	        		}
	  	        	}},
	  	        	{field:'tmPhone',title:'手机号码',width:100,align:'center',resizable:'false'},
	  	    ]],
	  	    queryParams:{tmType:tmType,tmAccount:tmAccount},
	  	    idField:"tmAccount",
	  	    rownumbers:false,
		    singleSelect:false,
		    fit:false,
		    striped : true,
	        fitColumns : true,
		    pagination : true,
		    nowrap:false,
		    sortable:true,
		    onSelect:function(rowIndex, rowData){
		    	var choice = $("#choice");
		    	if($("#"+rowData.tmAccount+"").html()==undefined){
		    		choice.append("<span id=" + rowData.tmAccount + ">" + rowData.tmName + "&nbsp;&nbsp;</span>");
		    		choice.append("<input type='hidden' name='tmMemberIdList' id=" + rowData.tmAccount + " value=" + rowData.tmAccount + " />");
		    	}
		    },
		    onUnselect:function(rowIndex, rowData){
		    	$("#choice #" + rowData.tmAccount).remove();
		    },
		    remoteSort: true,
		    rowStyler:function(index,row){
				if((index % 2) != 0) {
					return 'background-color:#FAFAD2;';
				} 
			},
			onCheckAll:function(rows){
				$.each(rows,function(n,value) {  
					var choice = $("#choice");
					if($("#"+value.tmAccount+"").html()==undefined){
						choice.append("<span id=" + value.tmAccount + ">" + value.tmName + "&nbsp;&nbsp;</span>");
						choice.append("<input type='hidden' name='tmMemberIdList' id=" + value.tmAccount + " value=" + value.tmAccount + " />");
					}
			     });  
			},
			onUncheckAll:function(rows){
				$.each(rows,function(n,value) {  
					$("#choice #" + value.tmAccount).remove();
				});  
			},
			pageSize : 10,
			pageList : [10, 15, 20, 30, 40, 50 ],
	  		onLoadSuccess:function(data){  
			       $('.editcls').linkbutton({text:'详情',plain:true,iconCls:'icon-edit'});  
			       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
			       $('.addcls').linkbutton({text:'添加',plain:true,iconCls:'icon-add'});  
			       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
			       $('.updatecls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'}); 
			       if(data){
			    	   if($("#choice").html()!=null&&$("#choice").html()!=""){
			    		   var  object = document.getElementsByName("tmMemberIdList");
			    		   $.each(data.rows, function(index, item){
			    			   for( var  i = 0 ;i < object.length;i ++ ){
			    				   if(object[i].value.indexOf(item.tmId)>=0){
			    					   $('#data-list').datagrid('checkRow', index);
			    				   }
			    			   }
			    		   })
			    	   }
					}
			} 
	  	});
}

function addBtn(){
	if($("#add-form").form('validate') == false){
		return false;
	}
	
	
	var selection = $('#data-list').datagrid('getChecked');
	if(selection==''){
		$.messager.alert('提示','请先选择接收人员!');
		return;
	}
	var tmTitle = $("#tmTitle").val();
	var tmContent = $("#tmContent").val();
	var ids = "";
	var types = "";
	$.each(selection,function(index,item){
		ids += ""+item.tmId;
		types += ""+item.tmType;
		if(index+1<selection.length){
			ids+=",";
			types+=",";
		}
	})
	
	$.messager.progress({ 
		title:'请稍后', 
		msg:'消息推送中...' 
	}); 
	
	$.ajax({
		type:"POST",
		dataType:'JSON',
		url:ctx+ '/message/add',
		data:{tmTitle:tmTitle,tmContent:tmContent,ids:ids,types:types},
		success:function(data){
			if(data=='100'){
				parent.addTabFun({
					src : ctx + "/message/index/list",
					title : "消息管理"
				});
				parent.closeTab("消息推送");
			}else if(data=='101'){
				$.messager.alert("不存在接收者!");
				return;
			}else{
				$.messager.alert("数据有误，请联系技术人员核查!");
			}
		}
	});
}

function boxShow1(){
	//显示列表  进行选择性发送
	$("#boxShowAll").removeAttr("disabled");
	$("#boxShowAll").removeAttr("checked");
	$("#boxShow").attr("disabled","disabled");
	$("#boxHide").show();
	loadData();
	$(".trWidth3").show();
}

function boxShowAll1(){
	var newTable = $("#newTable");
	//清空筛选
	$("#newTable tr").remove();
	//添加TR  达到筛选
	newTable.append("<tr id='choice'></tr>");
	//隐藏列表 
	$("#boxShowAll").attr("disabled","disabled");
	$("#boxShow").removeAttr("checked");
	$("#boxShow").removeAttr("disabled");
	$("#boxHide").hide();
	$(".trWidth3").hide();
	
}