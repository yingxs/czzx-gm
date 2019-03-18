
$(function(){
	
	loadDate();
});
function loadDate(){
	var tbGrade = $("#tbGrade").combobox("getValue");
	var tbClass = $("#tbClass").combobox("getValue");
	var tsName = $("#tsName").val();
	var AddDate = $("#AddDate").datebox('getValue');
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');
	$('#tbStudent-list').datagrid({
	    url:ctx+'/admin/students/list',
	    columns:[[
	        {field:'tsNumber',title:'学号',width:120,align:'center'},
	        {field:'tsName',title:'姓名',width:80,align:'center'},
	        {field:'tsSex',title:'性别',width:50,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "男";
	        	}else{
	        		return "女";
	        	}
	        }},
	        {field:'tgName',title:'所属年级',width:80,align:'center'},
	        {field:'tcName',title:'所在班级',width:80,align:'center'},
	        {field:'tsfName',title:'家长姓名',width:80,align:'center'},
	        {field:'tsfPhone',title:'家长电话',width:100,align:'center'},
	        {field:'tmName',title:'联系地址',width:150,align:'center',formatter:function(value,rec){
	        	return rec.pName + rec.cName + rec.oName + rec.tsAddress;
	        }},
	        {field:'tsStatus',title:'状态',width:80,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "正常";
	        	}else{
	        		return "休学";
	        	}
	        }},
	        {field:'pay',title:'操作',width:120,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	
	        	btn += '<a class="editcls" onclick="onEditSubmit(\''+rec.tsId + '\')" href="javascript:void(0)">编辑</a>&nbsp;';
	        	btn += '<a class="detailcls" onclick="onDetailSubmit(\''+rec.tsId + '\')" href="javascript:void(0)">详情</a>';
	        	
        		return btn;
	        }},
	    ]],
	    queryParams:{tbGrade:tbGrade,tbClass:tbClass,tsName:tsName,AddDate:AddDate,AddDateEnd:AddDateEnd},
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
		       $('.detailcls').linkbutton({text:'详  情',plain:true,iconCls:'icon-detail'});  
		}
	    
	});
}

function addStudent(){
	parent.addTabFun({
		src : ctx + "/admin/students/paper/add",
		title : "新增学生"
	});
}

function onEditSubmit(tsId){
	parent.addTabFun({
		src : ctx + "/admin/students/paper/edit?tsId="+tsId,
		title : "编辑学生"
	});
}

function onDetailSubmit(tsId){
	parent.addTabFun({
		src : ctx + "/admin/students/paper/detail?tsId="+tsId,
		title : "学生详情"
	});
}

