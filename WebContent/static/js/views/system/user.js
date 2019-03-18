var toolbar = [{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
            	parent.addTabFun({
					src : ctx + "/user/paper/add",title : "新增用户"
				});
            	}
            },{
            	text:'修改密码',
                iconCls:'icon-edit',
                handler:function(){
                	var selection = $('#user-list').datagrid('getSelected');
                	if(selection==null){
                		$.messager.alert('提示','请先选择一条信息!','info');
                		return;
                	}else{
                		$('#modify-form').form('clear');
                		$("#userid").val(selection.id);
                		$('#modify-account').html(selection.account);
                    	$('#modify-win').window('open');
                	}
                }
        }];

$(function(){
	$('#user-list').datagrid({
	    url:ctx+'/user/list',
	    columns:[[
	        {field:'name',title:'姓名',width:100,align:'center'},
	        {field:'account',title:'用户名',width:100,align:'center'},
	        {field:'groupName',title:'用户组',width:100,align:'center'},
	        {field:'sex',title:'性别',width:100,align:'center',
	        	formatter: function (value,row,index) {
					if(value == 1){
						return '男';
					}else{
						return "女";
					}
			    }  
	        },
	        {field:'email',title:'邮箱',width:150,align:'center'},
	        {field:'createTime',title:'添加时间',width:150,align:'center',sortable:'true'},
	        {field:'remark',title:'备注',width:200,align:'center'},
	        {field: 'opt', title: '操作', width: 150, align: 'center', 
			    formatter: function (value,row,index) {
					//function里面的三个参数代表:当前字段值，当前行数据对象，行号（行号从0开始）
				 		return  "<a class='editcls' href=javascript:openUpdate('"+row.id+"') style='cursor:hand'>修改</a>" +
				 			    "<a class='delcls'  href=javascript:onDeleteSubmit('"+row.id+"','"+row.account+"') style='cursor:hand'>删除</a>";
			    }  
			} 
	    ]],
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    sortName:'createTime',
	    sortOrder:'desc',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
		pageSize : 15,
		pageList : [15, 20, 30, 40, 50 ],
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
	
	//初始化用户组
	$('#group').combobox({
		url:ctx+ '/user/findUserGroup',
	    valueField:'groupid',
	    textField:'groupName'
	});
});

function openModify(){
	var selection = $('#user-list').datagrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择一条信息!','info');
		return;
	}else{
		$('#modify-form').form('clear');
		$("#userid").val(selection.id);
		$('#modify-account').html(selection.account);
		$("#psw1").textbox('setValue','');
		$("#psw2").textbox('setValue','');
    	$('#modify-win').window('open');
	}
}

/**
 * 打开修改页面
 * @return
 */
function openUpdate(){
	var selection = $('#user-list').datagrid("getSelected");
	
	if(selection == null){
		$.messager.alert('提示','请先选择需要修改的信息!');
		return;
	}
	
	parent.addTabFun({
		src : ctx + "/user/paper/edit?id="+selection.id,
		title : "编辑用户"
	});
}

/**
 * 删除用户
 * @return
 */
function onDeleteSubmit(id,account){
	var selection = $('#user-list').datagrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择需要删除的信息!','info');
		return;
	}
	
	$.messager.confirm("提示","确定要删除【"+account+"】吗？",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/user/delete",
		        data:{id:id},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "0"){
		        		$('#user-list').datagrid('reload');
		        	}else if(msg == "-1"){
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

/**
 * 修改密码
 */
function onModify(){
	if($("#modify-form").form('validate') == false){
		return false;
	}
	
	var id = $("#userid").val();
	var psw1 = $("#psw1").val();
	var psw2 = $("#psw2").val();
	
	$.post(ctx+"/user/modify",{
		id:id,
		psw1:psw1,
		psw2:psw2
		},function(msg){
	if(msg==0){
		$.messager.alert('提示','密码修改成功!','info');
		$("input:password").val("");
		$("#modify-win").window("close");
		$('#user-list').datagrid('load');	
	}else if(msg==-1){
		$.messager.alert('提示','两次密码不一致!','warning');
		$("input:password").val("");
	}else if($.trim(msg)=='unauthorized'){
		$.messager.alert('提示','您没有权限!','warning');
	}else{
		$.messager.alert('提示','密码修改失败！','error');
		$("input:password").val("");
	}
	},"text");
}