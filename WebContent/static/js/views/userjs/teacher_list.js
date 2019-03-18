
$(function(){
	$('#ttSex').combobox({
		data:[{id:"1",text:"男"},
		      {id:"0",text:"女"}],
		valueField:'id',
		textField:'text',
		panelHeight:'auto',
	});
	
	loadDate();
});
function loadDate(){
	var ttName = $("#ttName").val();
	var ttSex = $("#ttSex").combobox("getValue");
	var department = $("#department").combobox("getValue");
	$('#teacher-list').datagrid({
	    url:ctx+'/admin/teacher/teacherList',
	    columns:[[
	        {field:'ttName',title:'姓名',width:100,align:'center'},
	        {field:'ttNumber',title:'工号',width:80,align:'center'},
	        {field:'ttPhoto',title:'头像',width:80,align:'center',
	        	formatter:function(value,rec){
	        		if(value != null || value==''){
		        		var btn='';
		                btn += '<img height="30" width="60" src="' +value+ '"/>';
		        		return btn;
		        	}else{
		        		return '<img height="30" width="60" src="'+ctx+'/static/images/czzxdefault1.jpg">';
		        	}
		        	
			        }
		        },
	        {field:'ttSex',title:'性别',width:80,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "男";
	        	}else if(value == 0){
	        		return "女";
	        	}
	        }},
	        {field:'ttPhone',title:'手机号码',width:80,align:'center'},
	        {field:'ttMail',title:'电子邮箱',width:80,align:'center'},
	        {field:'ttPolitical',title:'政治面貌',width:80,align:'center'},
	        {field:'ttMarriage',title:'婚姻状况',width:80,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "已婚";
	        	}else if(value == 0){
	        		return "未婚";
	        	}
	        }},
	        {field:'ttBirth',title:'出生日期',width:80,align:'center'},
	        {field:'pay',title:'操作',width:180,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	
	        	btn += '<a class="editcls" onclick="onEditSubmit(\''+rec.ttId + '\')" href="javascript:void(0)">编辑</a>';
	        	btn += '<a class="delcls" onclick="onDeleteSubmit(\''+rec.ttId + '\',\''+rec.ttName + '\')" href="javascript:void(0)">删除</a>';
	        	btn += '<a class="resetcls" onclick="onUpdatePwd(\''+rec.ttId + '\',\''+rec.ttName + '\')" href="javascript:void(0)">重置密码</a>';
	        	
        		return btn;
	        }},
	    ]],
	    queryParams:{ttName:ttName,ttSex:ttSex,department:department},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 15,
		pageList : [15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删  除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'});
		       $('.resetcls').linkbutton({text:'重置密码',plain:true,iconCls:'icon-reset'});
		}
	    
	});
}

//删除
function onDeleteSubmit(ttId,ttName){
	$.messager.confirm("提示","您确认要删除名称为【"+ttName+"】的教师吗？",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/admin/teacher/del",
		        data:{ttId:ttId},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "100"){
		    			$('#teacher-list').datagrid('load');
		        	}else if(msg=="101"){
		        		$.messager.alert('提示','删除失败！','error');
					}else if(msg=="102"){
						$.messager.alert('提示','教师不存在！','warning');
					}
		        }
			});
		}
	});
}

//重置密码
function onUpdatePwd(ttId,ttName){
	$.messager.confirm("提示","您确认要重置教师【"+ttName+"】的密码吗？",function(r){
		if(r){
			$.ajax({
				type: 'POST',
				url: ctx+"/admin/teacher/updatePwd",
				data:{ttId:ttId},
				dataType: 'text',
				success: function (msg) {
					if(msg == "100"){
						$.messager.alert('提示','重置成功！','info');
					}else if(msg=="101"){
						$.messager.alert('提示','重置失败！','error');
					}else if(msg=="102"){
						$.messager.alert('提示','教师不存在！','warning');
					}
				}
			});
		}
	});
}

function onStudentSubmit(tcId){
	parent.addTabFun({
		src : ctx + "/admin/gac/studentList?tcId="+tcId,
		title : "学生列表"
	});
}

function addHtml(){
	parent.addTabFun({
		src : ctx + "/admin/teacher/paper/add",
		title : "新增教师"
	});
}

function onEditSubmit(ttId){
	parent.addTabFun({
		src : ctx + "/admin/teacher/paper/update?id="+ttId,
		title : "编辑教师"
	});
}