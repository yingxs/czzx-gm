
$(function(){
		//留言栏目
		$("#tlmType").combobox({
			data:[{id:"1",text:"校长信箱"},
			      {id:"2",text:"家校留言"},
			      {id:"3",text:"心理咨询"}],
			valueField:'id',
			textField:'text',
			panelHeight:"auto",
		});
		
		
		loadDate();
		
});



function loadDate(){
    //alert("进入loadDate()方法!");	 
	var tlmAskAnswerId = $("#tlmAskAnswerId").val();//留言人
	var ltmName = $("#ltmName").val();//姓名
	var AddDate = $("#AddDate").datebox('getValue');//创建开始时间
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');//创建结束时间	
	var tlmType = $("#tlmType").combobox('getValue');//留言栏目
//	alert(tlmType);
	$('#tbLeaveMessage').datagrid({
	    url:ctx+'/tbLeaveMessage/list',
	    columns:[[
            {field:'answerName',title:'留言人',width:50,align:'center',formatter:function(value,rec){
	        	if(rec.tlmAskAnswerId == 0){
	        		return "游客";
	        	}else{
	        		return value;
	        	}
	        }},
			{field:'ltmName',title:'联系人',width:50,align:'center'},
	        {field:'tlmPhone',title:'联系电话',width:60,align:'center'},
	        {field:'tlmMail',title:'邮箱',width:80,align:'center'},
	        {field:'tlmType',title:'留言栏目',width:60,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "校长信箱";
	        	}else if(value == 2){
	        		return "家校留言";
	        	}else if(value == 3){
	        		return "心理咨询";
	        	}
	        }},
	        {field:'tlmContent',title:'留言内容',width:70,align:'center'},
	        {field:'tlmStatus',title:'状态',width:50,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "待审核";
	        	}else if(value == 2){
	        		
	        		if(rec.tlmIfSecret==1){
	        			
	        			return "<font color='red'>待回复 </font>";
	        		}else{
	        			
	        			return "待回复";
	        		}
	        		
	        		
	        	}else if(value == 3){
	        		return "审核失败";
	        	}else if(value == 4){
	        		return "已回复";
	        	}
	        }},
	        {field:'tlmAddDate',title:'创建时间',width:90,align:'center',sortable:true},
	        {field:'pay',title:'操作',width:160,align:'center',formatter:function(value,rec){
	        	if(rec.tlmStatus ==1){
	        		var btn = '';
		        	btn += '<a class="detail" onclick="toDetail(\''+rec.tlmId+'\')" href="javascript:void(0)">详情</a>&nbsp;';
		        	btn += '<a class="pass" onclick="toDelete(\''+rec.tlmId+'\',\''+rec.ltmName+'\',2)" href="javascript:void(0)">审核通过</a>&nbsp;';
		        	btn += '<a class="false" onclick="toRefuse(\''+rec.tlmId+'\',\''+rec.ltmName+'\',3)" href="javascript:void(0)">审核失败</a>&nbsp;';
	        		return btn;
	        	}
	        	if(rec.tlmStatus ==2){
	        		var btn = '';
		        	btn += '<a class="detail" onclick="toDetail(\''+rec.tlmId+'\')" href="javascript:void(0)">详情</a>&nbsp;';
	        		return btn;
	        	}
	        	if(rec.tlmStatus ==3){
	        		var btn = '';
		        	btn += '<a class="detail" onclick="toDetail(\''+rec.tlmId+'\')" href="javascript:void(0)">详情</a>&nbsp;';
		        	btn += '<a class="delete" onclick="toDelete(\''+rec.tlmId+'\',\''+rec.ltmName+'\',0)" href="javascript:void(0)">删除</a>&nbsp;';
		        	return btn;
	        	}
	        	if(rec.tlmStatus ==4){
	        		var btn = '';
		        	btn += '<a class="detail" onclick="toDetail(\''+rec.tlmId+'\')" href="javascript:void(0)">详情</a>&nbsp;';
	        		return btn;
	        	}
	        	
	        }},
	    ]],
	    queryParams:{tlmAskAnswerId:tlmAskAnswerId,ltmName:ltmName,AddDate:AddDate,AddDateEnd:AddDateEnd,ltmName:ltmName,tlmType:tlmType},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 15,
		pageList : [10, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
	    
	    onLoadSuccess:function(data){  
		       $('.search').linkbutton({text:'查询',plain:true,iconCls:'icon-search'});
		      // $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delete').linkbutton({text:'删  除',plain:true,iconCls:'icon-clear'});  
		       $('.pass').linkbutton({text:'审核通过',plain:true,iconCls:'icon-add'});  
		       $('.false').linkbutton({text:'审核失败',plain:true,iconCls:'icon-remove'});  
		      // $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'});  
		       $('.detail').linkbutton({text:'详情',plain:true,iconCls:'icon-detail'}); 
		}
	});
}



//删除
function toDelete(tlmId,taiName,status){
	//alert(status);
	var text="";
	if(status==0){
		text="确定删除联系人 "+taiName+" 吗？";
	}
	if(status==2){
		text="确定通过联系人 "+taiName+" 吗？";
	}
	if(status==3){
		text="确定拒绝联系人 "+taiName+" 吗？";
	}
	$.messager.confirm("提示",text,function(r){
		if(r){
			
			$.ajax({
				type: 'POST',
				url: ctx+"/tbLeaveMessage/delete",
				data:{tlmId:tlmId,status:status},
				dataType: 'text',
				success: function (msg) {
					if(msg == "100"){
						$.messager.alert('提示','成功!');
						//删除成功,重新加载页面
						$('#tbLeaveMessage').datagrid('load');
					}else if(msg == "101"){
						$.messager.alert('提示','失败！','error');
					}
				}
			});
		}
	});
 }

	
//跳转留言管理详情页面
function toDetail(tlmId){
	parent.addTabFun({
		//detail   controller里面的方法名
		src : ctx + "/tbLeaveMessage/index/detail?tlmId="+tlmId,
		title : "留言详情"
	});
}

function toRefuse(tlmId,c,status){
	$.messager.prompt('提示信息', '确认拒绝'+c+'吗?  拒绝原因：', function(r){
		if (r){
			$.ajax({
				type: 'POST',
				url: ctx+"/tbLeaveMessage/refuse",
				data:{tlmId:tlmId,status:status,r:r},
				dataType: 'text',
				success: function (msg) {
					if(msg == "100"){
						$.messager.alert('提示','拒绝成功!');
						//删除成功,重新加载页面
						$('#tbLeaveMessage').datagrid('load');
					}else if(msg == "101"){
						$.messager.alert('提示','拒绝失败！','error');
					}
				}
			});
		}
	});
}




