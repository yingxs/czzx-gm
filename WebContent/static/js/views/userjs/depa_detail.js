
$(function(){
	
	loadDate();
});
function loadDate(){
	var tdId = $("#tdId").val();
	$('#teacher-list').datagrid({
	    url:ctx+'/admin/department/teacherList',
	    columns:[[
	        {field:'ttPhoto',title:'照片',width:100,align:'center',formatter:function(value,rec){
	        	return '<img src="'+value+'"  width="60px" height="30px">';
	        }},
	        {field:'ttName',title:'姓名',width:100,align:'center'},
	        {field:'ttNumber',title:'工号',width:100,align:'center'},
	        {field:'ttPhone',title:'手机号码',width:100,align:'center'},
	        {field:'tdtPosition',title:'岗位',width:100,align:'center'},
	    ]],
	    queryParams:{tdId:tdId},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:false,
	    fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 15,
		pageList : [15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
	    
	});
}
