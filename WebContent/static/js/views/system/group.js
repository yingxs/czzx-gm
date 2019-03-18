
$(function(){
	$('#torgan-list').datagrid({
	    url:ctx+'/group/list?date='+new Date(),
	    columns:[[
	        {field:'name',title:'用户组名称',width:150,align:'center'},
	        {field:'remark',title:'用户组描述',width:400,align:'center'},
	        {field:'createTime',title:'添加时间',width:150,align:'center'},
	        {field:'status',title:'状态',width:50,align:'center',formatter:function formatStatus(value,row,index){
	        	if(row.status==1){
	        		return "启用";
	        	}else{
	        		return "禁用";
	        	}
	        }}
	    ]],
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
		pageSize : 15,
		pageList : [15, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	    
	});
});

function openUpdate(){
	var selection = $('#torgan-list').datagrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择一项!');
		return;
	}
	
	$("#update-form").form("clear");
	
	$("#update-id").val(selection.id);
	$("#update-name").textbox('setValue',selection.name);
	$("#update-remark").textbox('setValue',selection.remark);
	$("#update-win").window("open");
}

function onAddSubmit(){
	if($("#add-form").form('validate') == false){
		return false;
	}
	
	var name = $("#add-name").val();
	var remark = $("#add-remark").val();
	
	//alert(name);
	$.post(ctx+"/group/add",{name:name,remark:remark},function(msg){
		if(msg==0){
			$("#add-win").window("close");
			$('#torgan-list').datagrid('load');
		}else if(msg==-1){
			$.messager.alert('提示','提交参数不对!');
		}else if(msg==-2){
			$.messager.alert('提示','该用户组已存在!');
		}else{
			$.messager.alert('提示','添加失败，请稍候再试!');
		}
	},"text");
}

function onUpdateSubmit(){
	if($("#update-form").form('validate') == false){
		return false;
	}
	
	var id = $("#update-id").val();
	var name = $("#update-name").val();
	var remark = $("#update-remark").val();
	
	$.ajax({
		url:ctx+"/group/update",
		data:{id:id,name:name,remark:remark},
		type:"post",
		success:function(msg){
			if(msg==0){
				$("#update-win").window("close");
				$('#torgan-list').datagrid('load');
			}else if(msg==-2){
				$.messager.alert('提示','该用户组已存在!');
			}else{
				$.messager.alert('提示','更新失败，请稍候再试!');
			}
		}
	});
}

function onDeleteSubmit(){
	var selection = $('#torgan-list').datagrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择一条信息!','info');
		return;
	}
	
	var id = selection.id;
	
	$.messager.confirm("提示","确定要删除该条信息吗？",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/group/delete.action?id="+id,
		        data:{id:id},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "0"){
		    			$('#torgan-list').datagrid('load');
		        	}else if(msg == "-1"){
		        		$.messager.alert('提示','删除失败，该用户组下存在用户，请确认!','warning');
		        	}else if(msg == "-2"){
		        		$.messager.alert('提示','登录已过期，请重新登录!','warning',function () {
		        			window.location.href=basePath+"login.jsp";
		                });
		        	}else if($.trim(msg)=='unauthorized'){
						$.messager.alert('提示','您没有权限!','warning');
					}else{
		        		$.messager.alert('提示','信息删除失败','error');
		        	}
		        }
			});
		}
	});
}