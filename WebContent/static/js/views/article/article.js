

$(function(){
	$('#taColumnId').combotree({
		}).combotree("tree").tree({
			url : ctx + '/admin/column/getColumnForTree',
			cascadeCheck : true,
			onlyLeafCheck : false,
			checkbox : false,
			animate : true,
			onBeforeExpand : function(node, param) {
				//$('#selColumn').combotree("tree").tree("options").url = "${ctx}/admin/column/get_all_column.json";
			}
		});
	
	loadData();
});

function add(){
	parent.addTabFun({
		src : ctx + "/admin/article/index/add",
		title : "新增文章"
	});
}



function edit(id){
	parent.addTabFun({
		src : ctx + "/admin/article/initUpdate?id=" + id,
		title : "编辑文章"
	});
}





function del(id,name){
	$.messager.confirm("提示","确定要删除标题为【"+name+"】的文章吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/admin/article/del',
				data:{id:id},
				success:function(data){
					if(data == '1'){
						$('#data-list').datagrid("reload");
					}else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}

/*置顶操作*/
function to_top(id,name){
	$.messager.confirm("提示","确定要将标题为【"+name+"】的文章置顶吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/admin/article/update_set',
				data:{id:id,taTop:1},
				success:function(data){
					if(data == '1'){
						$('#data-list').datagrid("reload");
					}else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}


/*取消置顶操作*/
function to_top_no(id,name){
	$.messager.confirm("提示","确定要将标题为【"+name+"】的文章取消置顶吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/admin/article/update_set',
				data:{id:id,taTop:0},
				success:function(data){
					if(data == '1'){
						$('#data-list').datagrid("reload");
					}else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}


function to_headline(id,name){
	$.messager.confirm("提示","确定要将标题为【"+name+"】的文章设置为头条吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/admin/article/update_set',
				data:{id:id,taHeadline:1},
				success:function(data){
					if(data == '1'){
						$('#data-list').datagrid("reload");
					}else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}


function to_headline_no(id,name){
	$.messager.confirm("提示","确定要将标题为【"+name+"】的文章取消头条吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/admin/article/update_set',
				data:{id:id,taHeadline:0},
				success:function(data){
					if(data == '1'){
						$('#data-list').datagrid("reload");
					}else{
	 					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
	 				}
				}
				
			});
		}
	});
}



function loadData()
{
	var taTitle = $("#taTitle").val();
	var taColumnId = $("#taColumnId").combobox('getValue');
	var addDate = $("#AddDate").datebox('getValue');//创建时间--开始
	var endDate = $("#endDate").datebox('getValue');//创建时间---结束
	
	$('#data-list').datagrid({
	    url:ctx+'/admin/article/list',
	    columns:[[
	        {field:'column_name',title:'栏目名称',width:120,align:'center'},
	        {field:'ta_title',title:'文章标题',width:300,align:'center'},
	        /*{field:'ta_headline',title:'是否头条',width:80,align:'center',
	        	formatter:function formatStatus(value,row,index){
		        	if(row.ta_headline == 1){
		        		return "是";
		        	}
		        	else{
		        		return "否";
		        	}
		        }},*/
	        {field:'ta_top',title:'是否置顶',width:80,align:'center',
		        	formatter:function formatStatus(value,row,index){
			        	if(row.ta_top == 1){
			        		return "是";
			        	}
			        	else{
			        		return "否";
			        	}
			        }},
	        {field:'adddate',title:'创建时间',width:180,align:'center',sortable:'true'}, 
	        {field:'ta_status',title:'状态',width:70,align:'center',
	        	formatter:function formatStatus(value,row,index){
		        	if(row.ta_status == 1){
		        		return "正常";
		        	}
		        }
	        },
	        {field:'opt',title:'操作',width:300,align:'center',
	        	formatter:function formatStatus(value,row,index){
	        		var html ="";
	        		html ="<a class='editcls' href=javascript:edit('"+row.ta_id+"') style='cursor:hand'>编辑</a>";
	        		
	        		if(row.ta_top == 0 ) 
	        			html +="<a class='totop" +"' href=javascript:to_top('"+row.ta_id+"','" + row.ta_title +"') style='cursor:hand'>置顶</a>"
	        		else
	        			html +="<a class='totop_no" +"' href=javascript:to_top_no('"+row.ta_id+"','" + row.ta_title +"') style='cursor:hand'>置顶</a>"
	        		
	        		/*if(row.ta_headline == 0)//是头条
	        			html +="<a class='totoutiao" +"' href=javascript:to_headline('"+row.ta_id+"','" + row.ta_title +"') style='cursor:hand'>头条</a>"
	        		else
	        			html +="<a class='totoutiao_no" +"' href=javascript:to_headline_no('"+row.ta_id+"','" + row.ta_title +"') style='cursor:hand'>头条</a>"*/
	        		if(row.ta_status == 1){
	        			html +="<a class='delcls' href=javascript:del('"+row.ta_id+"','" + row.ta_title +"') style='cursor:hand'>删除</a>";
		        	}
	        		return html;
		        }
	        }
	        
	    ]],
	    queryParams:{'taTitle':taTitle,'taColumnId':taColumnId,'addDate':addDate,'endDate':endDate},
	    rownumbers:false,
	    singleSelect:true,
	    toolbar : '#tb',
	    fit:true,
	    striped : true,
	    fitColumns : false,
	    pagination : true,
	    sortName : 'tc_addtime',
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
		       $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删  除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'});
		       $('.totop').linkbutton({text:'置  顶',plain:true,iconCls:'icon-to-top'});
		       $('.totoutiao').linkbutton({text:'设置头条',plain:true,iconCls:'icon-to-toutiao'});
		       $('.totop_no').linkbutton({text:'取消置顶',plain:true,iconCls:'icon-to-top'});
		       $('.totoutiao_no').linkbutton({text:'取消头条',plain:true,iconCls:'icon-to-toutiao'});
		      
		}
	});
	
	
	
	
	
	
	
	
}


