
$(function(){
	$('#version-list').datagrid({
	    url:ctx+'/tbversion/list',
	    columns:[[
	        {field:'tvId',title:'',width:1,hidden:true},
	        {field:'tvName',title:'APP名称',width:120,align:'center'},
	        {field:'tvType',title:'APP类型',width:120,align:'center',
	        	formatter:function(value,row,index){  
	        		if(value == 1)
	        			return'客户端';
	        		if(value == 2)
	        			return'水工端';
	        		if(value == 3)
	        			return'经销商端';
	        	}
            },
	        {field:'tvUrl',title:'APP路径',width:350,align:'center'},
	        {field:'tvDesp',title:'更新描述',width:250,align:'center'},
            {field:'tvVersion',title:'版本号',width:100,align:'center'},
	        {field:'tvAddDate',title:'创建时间',width:150,align:'center',sortable:'true'},
            {field:'tvStatus',title:'操作',
	        formatter:
	        	function(value,row,index){
        			return "<a class='edit_' href=javascript:del("+row.tvId+",0) style='cursor:han'>删除</a>";
			},width:60,align:'center'}
	    ]],
	    rownumbers:true,
	    singleSelect:true,
	    striped : true,
	    fit:true,
	    fitColumns : false,
	    pagination : true,
	    sortName:'tvAddDate',
	    sortOrder:'desc',
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			}
	    },
	    pageSize : 15,
		pageList : [10, 15, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		     $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		     $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		}  
	});
});

function cdate(date) {
	var regEx = new RegExp("\\-","gi");
	return date.replace(regEx,"/");
}

function change(id, status) {
	$.messager.confirm("确认","确认要编辑状态",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/member/change",
		        data:{tvId:id,status:status},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "0"){
		    			$('#member-list').datagrid('reload');
		        	}
		        }
			});
		}
	});
}

function del(id, status) {
	$.messager.confirm("确认","确认要刪除",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/tbversion/change",
		        data:{tvId:id,status:status},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "0"){
		    			$('#version-list').datagrid('reload');
		        	}
		        }
			});
		}
	});
}

function addRow()
{
	parent.addTabFun({
		src : ctx + "/tbversion/index/add",
		title : "新增APP"
	});
}


