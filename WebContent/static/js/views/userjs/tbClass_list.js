
$(function(){
	$("#menuTree").tree({   
 		url:ctx+'/admin/gac/gradeTree',
 		onClick : function(node) {
 			loadData();
 		},
 		onLoadSuccess : function(node, data) {
			var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed') {
						t.tree('expandAll');
					}
				});
			}
		}
	}); 
 
loadData();
	
});


function loadData(){
	var tbGrade = null;
	var  selected = $("#menuTree").tree('getSelected');
	if(selected!=null){
		tbGrade = selected.id;
	}
	$('#tbClass-list').datagrid({
	    url:ctx+'/admin/gac/classList',
	    columns:[[
	        {field:'tcName',title:'班级名称',width:80,align:'center'},
	        {field:'tgName',title:'所属年级',width:80,align:'center'},
	        {field:'tscrTeacherName',title:'班主任',width:80,align:'center'},
	        {field:'ttPhone',title:'手机号码',width:80,align:'center'},
	        {field:'pay',title:'操作',width:150,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	
	        	btn += '<a class="datalistcls" onclick="onStudentSubmit(\''+rec.tcId + '\')" href="javascript:void(0)">学生列表</a>';
	        	btn += '<a class="setcls" onclick="onEditTeacherSubmit(\''+rec.tcId + '\',\''+rec.tgId + '\',\''+rec.tcName + '\',\''+rec.ttId + '\')" href="javascript:void(0)">设置班主任</a>';
	        	btn += '<a class="editcls" onclick="onEditSubmit(\''+rec.tcId + '\',\''+rec.tgId + '\',\''+rec.tcName + '\',\''+rec.ttId + '\')" href="javascript:void(0)">编辑</a>';
	        	btn += '<a class="delcls" onclick="onDeleteSubmit(\''+rec.tcId + '\',\''+rec.tcName + '\')" href="javascript:void(0)">删除</a>';
	        	
        		return btn;
	        }},
	    ]],
	    queryParams:{tbGrade:tbGrade},
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
		       $('.datalistcls').linkbutton({text:'学生列表',plain:true,iconCls:'icon-data-list'}); 
		       $('.setcls').linkbutton({text:'设置班主任',plain:true,iconCls:'icon-set'}); 
		}
	    
	});
}


//新增班级
function onAddSubmit(){
	if($("#add-form").form('validate') == false){
		return false;
	}
	
	var tbGrades = $("#tbGrades").combobox('getValue');
	var tbClassName = $("#tbClassName").val();
	var tbTeacher = $("#tbTeacher").combobox('getValue');
	
	$.ajax({
        type: 'POST',
        url: ctx+"/admin/gac/add",
        data:{tbGrades:tbGrades,tbClassName:tbClassName,tbTeacher:tbTeacher},
        dataType: 'text',
        success: function (msg) {
        	if(msg == '100'){
        		parent.addTabFun({
        			src : ctx + "/admin/gac/list",
        			title : "班级管理"
        		});
        	}else if(msg == '101'){
        		$.messager.alert('提示','添加失败！','error');
        	}
        }
	});
}


//编辑班级
function edit_onAddSubmit(){
	if($("#edit-form").form('validate') == false){
		return false;
	}
	
	var tcId = $("#edit_tcId").val();
	var tbGrade = $("#edit_tbGrade").combobox('getValue');
	var tbClassName = $("#edit_tbClassName").val();
	var tbTeacher = $("#edit_tbTeacher").combobox('getValue');
	$.ajax({
        type: 'POST',
        url: ctx+"/admin/gac/edit",
        data:{tcId:tcId,tbGrade:tbGrade,tbClassName:tbClassName,tbTeacher:tbTeacher},
        dataType: 'text',
        success: function (msg) {
        	if(msg == '100'){
        		$.messager.alert('提示','提交成功！','info');
        		$("#edit-win").window("close");
        		$("#edit-win").hide();
        		$('#tbClass-list').datagrid('reload');
        		
        		/*parent.addTabFun({
        			src : ctx + "/admin/gac/list",
        			title : "班级管理"
        		});*/
        		
        	}else if(msg == '101'){
        		$.messager.alert('提示','编辑失败！','error');
        	}else if(msg == '102'){
        		$.messager.alert('提示','班级不存在！','warning');
        	}
        }
	});
}

//设置班主任
/*function set_onAddSubmit(){
	if($("#edit-form").form('validate') == false){
		return false;
	}
	var tcId = $("#edit_tcId").val();
	var tbGrade = $("#edit_tbGrade").combobox('getValue');
	var tbClassName = $("#edit_tbClassName").val();
	var tbTeacher = $("#edit_tbTeacher").combobox('getValue');
	$.ajax({
        type: 'POST',
        url: ctx+"/admin/gac/set",
        data:{tcId:tcId,tbGrade:tbGrade,tbClassName:tbClassName,tbTeacher:tbTeacher},
        dataType: 'text',
        success: function (msg) {
        	if(msg == '100'){
        		$.messager.show({
        			title:'我的消息',
        			msg:'设置成功!',
        			timeout:2000,
        			showType:'slide'
        		});
        		$("#setTeacher-win").window("close");
        		$("#setTeacher-win").hide();
        		$('#tbClass-list').datagrid('reload');
        		
        		parent.addTabFun({
        			src : ctx + "/admin/gac/list",
        			title : "班级管理"
        		});
        	}else if(msg == '101'){
        		$.messager.alert('提示','编辑失败！','error');
        	}else if(msg == '102'){
        		$.messager.alert('提示','班级不存在！','warning');
        	}
        }
	});
}*/

//打开设置班主任窗口
function onEditTeacherSubmit(tcId,tgId,tcName,ttId){
	$("#setTeacher-form").form("clear");
	$("#setTeacher-win").show();
	$("#setTeacher-win").window("open");
	
	$("#set_tcId").val(tcId);
	$("#set_tbGradeId").val(tgId);
	$.ajax({
		type: 'POST',
        url: ctx+"/admin/gac/getGradeName",
        data:{tgId:tgId},
        dataType: 'text',
        success: function (name){
        	$("#setTeacher_tbGrade").html(name+"年级");
        }
	});
	
	$("#setTeacher_tbClassName").html(tcName);
	$("#set_tbClassName").val(tcName);
	if(ttId == "null" || ttId == null || ttId == ""){
		$("#set_tbTeacher").combobox('setValue',"");
	}else{
		$("#set_tbTeacher").combobox('setValue',ttId);
	}
}

//设置班主任返回
function set_myReset(){
	$("#setTeacher-win").window("close");
	$("#setTeacher-win").hide();
}

//设置老师
function set_onAddSubmit(){
	if($("#setTeacher-form").form('validate') == false){
		return false;
	}
	
	var tcId = $("#set_tcId").val();
	var tbGrade = $("#set_tbGradeId").val();
	var tbClassName = $("#set_tbClassName").val();
	var tbTeacher = $("#set_tbTeacher").combobox('getValue');
	
	$.ajax({
        type: 'POST',
        url: ctx+"/admin/gac/edit",
        data:{tcId:tcId,tbGrade:tbGrade,tbClassName:tbClassName,tbTeacher:tbTeacher},
        dataType: 'text',
        success: function (msg) {
        	if(msg == '100'){
        		$.messager.alert('提示','设置成功！','info');
        		$("#setTeacher-win").window("close");
        		$("#setTeacher-win").hide();
        		$('#tbClass-list').datagrid('reload');
        	}else if(msg == '101'){
        		$.messager.alert('提示','编辑失败！','error');
        	}else if(msg == '102'){
        		$.messager.alert('提示','班级不存在！','warning');
        	}
        }
	});
}

//返回
function edit_myReset(){
	$("#edit-win").window("close");
	$("#edit-win").hide();
}


function onDeleteSubmit(tcId,tcName){
	$.messager.confirm("提示","您确认要删除名称为【"+tcName+"】的班级吗",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/admin/gac/del",
		        data:{tcId:tcId},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "100"){
		    			$('#tbClass-list').datagrid('load');
		        	}else if(msg=="101"){
		        		$.messager.alert('提示','删除失败！','error');
					}else if(msg=="102"){
						$.messager.alert('提示','班级不存在！','warning');
					}else if(msg=="103"){
						$.messager.alert('提示','班级不存该班级下有学生存在，暂时不能删除，请检查！','warning');
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