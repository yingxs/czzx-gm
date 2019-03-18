
$(function(){
		loadDate();
});

function loadDate(){
    //alert("进入loadDate()方法!");	 
	var tdName = $("#tdName").val();//部门名称
	
	$('#tbDepartmentManage').datagrid({
	    url:ctx+'/departmentManage/list',
	    columns:[[
			{field:'tdName',title:'部门名称',width:100,align:'center'},
	        {field:'tdDesp',title:'部门介绍',width:100,align:'center'},
	        {field:'tgLogo',title:'部门图标',width:100,align:'center',
		        formatter:function(value,rec){  
		        	if(value != null || value==''){
		        		var btn='';
		                btn += '<img height="32" width="32" src="' +value+ '"/>';
		        		return btn;
		        	}else{
		        		return '<img height="32" width="32" src="'+ctx+'/static/images/czzxdefault1.jpg">';
		        	}
		        	
			        }
		        },
	        {field:'tdAddtime',title:'创建时间',width:100,align:'center',sortable:true},
	        {field:'pay',title:'操作',width:100,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	btn += '<a class="editcls" onclick="toUpdate(\''+rec.tdId+'\')" href="javascript:void(0)">编辑</a>';
        		btn += '<a class="delcls" onclick="toDelete(\''+rec.tdId+'\',\''+rec.tdName+'\')" href="javascript:void(0)">删除</a>';
        		btn += '<a class="detailcls" onclick="toManage(\''+rec.tdId+'\')" href="javascript:void(0)">成员管理</a>';

        		return btn;
	        }},
	    ]],
	    queryParams:{tdName:tdName},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 20,
		pageList : [10, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
	    
	    onLoadSuccess:function(data){  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		       $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删  除',plain:true,iconCls:'icon-remove'});
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'}); 
		       $('.detailcls').linkbutton({text:'成员管理',plain:true,iconCls:'icon-detail'});  
		}
	});
}


//跳转新增部门页面
function addHtml(){
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageAdd",
		title : "新增部门"
	});
}


//跳转部门编辑页面
function toUpdate(tdId){
	//alert("123");
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageUpdate?tdId="+tdId,
		title : "部门编辑"
	});
}
//删除
function toDelete(tdId,tbName){
//	alert(tbName);
	$.messager.confirm("提示","您确认要删除名称为["+tbName+"]的部门？",function(r){
		if(r){
			$.ajax({
				type: 'POST',
				url: ctx+"/departmentManage/findMemberCount",
				data:{tdId:tdId},
				dataType: 'text',
				success: function (msg) {
					if(msg == "100"){
						 deleteTwo(tdId);
					}else if(msg == "101"){
						$.messager.alert('提示','该部门下还有教职工，请先删除部门下的教职工信息！','error');
					}else{
						$.messager.alert('提示','失败！','error');
					}
				}
			});
		}
	});
}

//删除
function deleteTwo(tdId){
	$.ajax({
		type: 'POST',
		url: ctx+"/departmentManage/delete",
		data:{tdId:tdId},
		dataType: 'text',
		success: function (msg) {
			if(msg == "100"){
				$.messager.alert('提示','删除成功!');
				//删除成功,重新加载页面
				$('#tbDepartmentManage').datagrid('load');
			}else if(msg == "101"){
				$.messager.alert('提示','删除失败！','error');
			}
		}
	});
}


//跳转到运营管理 >部门管理 >成员管理页面
function toManage(tdId){
	//alert(tdId);
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageCYGL?tdId="+tdId,
		title : "成员管理"
	});
}


