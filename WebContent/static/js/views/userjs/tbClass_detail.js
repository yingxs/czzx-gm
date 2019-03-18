
$(function(){
	
	loadDate();
});
function loadDate(){
	var tcId = $("#tcId").val();
	$('#student-list').datagrid({
	    url:ctx+'/admin/gac/onStudentList',
	    columns:[[
	        {field:'tsNumber',title:'学号',width:120,align:'center'},
	        {field:'tsName',title:'姓名',width:80,align:'center'},
	        {field:'tsSex',title:'性别',width:80,align:'center',formatter:function(value,rec){
        		if(value == 1){
        			return "男";
        		}else if (value == 2){
        			return "女";
        		}
	        }},
	        {field:'tgName',title:'所属年级',width:80,align:'center'},
	        {field:'tcName',title:'所在班级',width:80,align:'center'},
	        {field:'tsfName',title:'家长姓名',width:80,align:'center'},
	        {field:'tsfPhone',title:'家长电话',width:80,align:'center'},
	        {field:'taAddress',title:'联系地址',width:120,align:'center',formatter:function(value,rec){
        		return rec.pName + rec.cName + rec.oName + rec.taAddress;
	        }},
	        {field:'tsStatus',title:'状态',width:80,align:'center',formatter:function(value,rec){
	        	if(value == 1){
        			return "正常";
        		}else if (value == 2){
        			return "<a style='color'>休学</a>";
        		}
	        }},
	    ]],
	    queryParams:{tcId:tcId},
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
