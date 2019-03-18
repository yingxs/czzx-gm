$(function(){
	loadData();
});

function loadData(){
	var tacName = $("#tacName").val();
	
	$('#advertiseCata-list').datagrid({
	    url:ctx+'/advertiseCata/list',
	    columns:[[
	        {field:'tacName',title:'板块名称',width:150,align:'center'},
	        {field:'tacDesp',title:'板块描述',width:300,align:'center'},
	        {field:'tacAddPersonName',title:'创建人',width:100,align:'center'}, 
	        {field:'tacAddDate',title:'创建时间',width:160,align:'center',sortable:'true'}, 
	        {field:'tacStatus',title:'状态',width:100,align:'center',
	        	formatter:function formatStatus(value,row,index){
		        	if(row.tacStatus == 1){
		        		return "正常";
		        	}else if(row.tacStatus == 0){
		        		return "删除";
		        	}else{
		        		return "<font color='red'>未知</font>";
		        	}
		        }
	        },
	        {field:'opt',title:'操作',width:150,align:'center',
	        	formatter:function formatStatus(value,row,index){
	        		return "<a class='editcls' href=javascript:onEditSubmit('"+row.tacId+"') style='cursor:hand'>编辑</a>" +
	        				"<a class='delcls' href=javascript:onDeleteSubmit('"+row.tacId+"') style='cursor:hand'>删除</a>";
		        }
	        }
	        
	    ]],
	    queryParams:{'tacName':tacName},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar : '#tb',
	    fit:true,
	    striped : true,
	    fitColumns : true,
	    pagination : true,
	    sortName : 'tac_add_date',
	    sortOrder: 'desc',
	    remoteSort: true,
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
		},
		pageSize : 15,
		pageList : [10, 15, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	});
}


function onEditSubmit(tacId){
	parent.addTabFun({
		src : ctx + "/advertiseCata/index/edit?tacId="+tacId,
		title : "编辑广告板块"
	});
}


/**
 * 删除广告板块
 */
function onDeleteSubmit(id){
	var selection = $('#advertiseCata-list').datagrid('getSelected');
	$.messager.confirm("提示","确定要删除【"+selection.tacName+"】吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/advertiseCata/delete',
				data:{tacId:id},
				success:function(data){
					if(data == '100'){
						$('#advertiseCata-list').datagrid("reload");
					}
					else if(data == '101'){
						$.messager.alert('提示','该广告板块下存在广告内容，请勿删除!','warning');
						return;
					}
					else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}

	