$(function(){
	load();
	loadData();
});


function loadData()
{
	var tniCode = $("#tniCode").val();
	$('#channel-list').datagrid({
	    url:ctx+'/notice/recvList',
	    columns:[[
	        {field:'tsf_name',title:'家长姓名',width:100,align:'center'},
	        {field:'tsf_phone',title:'手机号码',width:100,align:'center'}
	    ]],
	    queryParams:{'tniCode':tniCode},
	    rownumbers : true,
	    singleSelect:false,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tb',
	    sortName : 'tniAddDate',
	    sortOrder: 'desc',
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			}
			},
		pageSize : 5,
		pageList : [5, 10, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	});
}

function load()
{
	var tniCode = $("#tniCode").val();
	
	$('#teacher-list').datagrid({
	    url:ctx+'/notice/recvTeacherList',
	    columns:[[
	        {field:'tt_name',title:'教师姓名',width:150,align:'center'},
	        {field:'tt_login_user',title:'登录账号',width:100,align:'center'},
	        {field:'tt_phone',title:'手机号码',width:100,align:'center'}
	    ]],
	    queryParams:{'tniCode':tniCode},
	    rownumbers : true,
	    singleSelect:false,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tc',
	    sortName : 'tniAddDate',
	    sortOrder: 'desc',
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			}
			},
		pageSize : 5,
		pageList : [5, 10, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	});
}
