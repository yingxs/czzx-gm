
$(function(){
		loadDate();
});

function loadDate(){
   // alert("进入loadDate()方法!");	 
	var tdName = $("#tdName").val();//部门名称
	var tdId = $("#tdId").val();
//	alert(tdId);
	
	$('#tbDepartmentManageCYGL').datagrid({
	    url:ctx+'/departmentManage/list2',
	    columns:[[
	        {field:'ttPhoto',title:'照片',width:100,align:'center',
		        formatter:function(value,rec){  
		        	var btn='';
	                btn += '<img height="32" width="32" src="' +value+ '"/>';
	        		return btn;
			        }
		        },
		    {field:'ttName',title:'姓名',width:100,align:'center'},
		    {field:'ttNumber',title:'工号',width:100,align:'center'},
		    {field:'ttPhone',title:'手机号码',width:100,align:'center'},
		    {field:'tdtPosition',title:'岗位',width:100,align:'center'},
	        {field:'pay',title:'操作',width:100,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	btn += '<a class="editcls" onclick="save(\''+rec.tdtId+'\',\''+rec.ttName+'\')" href="javascript:void(0)">编辑</a>';
        		btn += '<a class="delcls" onclick="toDelete(\''+rec.tdtId+'\')" href="javascript:void(0)">删除</a>';
        		return btn;
	        }},
	    ]],
	    queryParams:{tdName:tdName,tdId:tdId},
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
		       
		}
	});
}


//跳转添加成员页面
function addHtml(tdId){
//	alert(tdId);
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageCYGLAdd?tdId="+tdId,
		title : "添加成员"
	});
}




//打开编辑弹窗
function save(tdtId,ttName){
	//alert(tdtId);
	$("#tdtId").val(tdtId);
	$("#ttNames").text(ttName);
	$("#updateResume-form").form("clear");
	$("#updateResume-win").window("open");
}
//返回
function return1(){
	$("#updateResume-win").window("close");
}

//提交
function addtend(){
	var tdtId=$("#tdtId").val();
	var tdtTeacherDesp=$("#tdtTeacherDesp").val();
	$.ajax({
        type: 'POST',
        url: ctx+"/departmentManage/updateResume",
        data:{tdtId:tdtId,tdtTeacherDesp:tdtTeacherDesp},
        dataType: 'text',
        success: function (msg) {
        	if(msg == "100"){
        		var tdId=$("#tdId").val();
        		toManage(tdId);
        	
        	}
        }
	});
		
}


//删除
function toDelete(tdtId){
	//alert(tdtId);
	$.ajax({
		type: 'POST',
		url: ctx+"/departmentManage/deleteTwo",
		data:{tdtId:tdtId},
		dataType: 'text',
		success: function (msg) {
			if(msg == "100"){
				$.messager.alert('提示','删除成功!');
				//删除成功,重新加载页面
				$('#tbDepartmentManageCYGL').datagrid('load');
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

