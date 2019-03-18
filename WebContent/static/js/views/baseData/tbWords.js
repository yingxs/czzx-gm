
 var toolbar = [{
            text:'添加',
            iconCls:'icon-add',
            handler:function(){
            	parent.addTabFun({
					src : ctx + "/words/index/add",
					title : "新增数据字典"
				});
            	
            }
        },{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){
            	var selection = $('#words-list').datagrid("getSelected");
            	if(selection == null){
            		$.messager.alert('提示','请先选择一个字典!');
            		return;
            	}
            	
            	parent.addTabFun({
					src : ctx + "/words/paper/edit?tgId="+selection.tspId,
					title : "编辑数据字典"
				});
            }
        },{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
            	delUser();
            }
        }];


function loadData()
{
	
	var twCode = $("#twCode").val();
	var twName = $("#twName").val();
	$('#words-list').datagrid({
	    url:ctx+'/words/list',
	    
	    columns:[[
	        {field:'tw_code',title:'编码',width:150,align:'center',resizable:'false',sortable:true},
	        {field:'tw_name',title:'名称',width:150,align:'center'},
	        {field:'tw_add_date',title:'创建日期',width:200,align:'center',sortable:true
            },  
	    
	        {field:'tw_status',title:'状态',width:60,align:'center',formatter:function(value,rec){ return "正常";}},
	        {field:'opt',title:'操作',width:150,align:'center',  
	        	 formatter:function(value,rec){  
	                 var btn = '<a class=\"editcls" onclick="editRow(\''+rec.tw_id+'\')" href="javascript:void(0)">修改</a>';  
	                 btn += '<a class=\"delcls" onclick="delRow(\''+rec.tw_id+'\',\''+rec.tw_name+'\')" href="javascript:void(0)">删除</a>'; 
	                 return btn;  
	             } 
	        }  
	    ]],
	    queryParams:{'twCode':twCode,'twName':twName},
	    rownumbers:true,
	    singleSelect:true,
	    fit:true,
	    striped : true,
	    fitColumns : true,
	    pagination : true,
	    sortName : 'tw_add_date',
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


function editRow(tspId)
{
	parent.addTabFun({
		src : ctx + "/words/index/edit?twId="+tspId,
		title : "编辑数据字典"
	});
}

function delRow(twId,twName){
	$.messager.confirm("提示","确定要删除字典【"+twName+"】吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/words/del',
				data:{twId:twId},
				success:function(data){
					if(data=='100'){
						$('#words-list').datagrid("load");
					}
					else if(data=='101'){
						$.messager.alert('提示',"请选择要删除的数据行!",'info');
						return;
					}
					else if(data=='102'){
						$.messager.alert('提示',"该目录下面存在子目录，不能删除!",'warning');
						return;
					}
					else if(data=='110'){
						$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
					}
				}
				
			});
		}
		
	});	
}

function addRow(){

	parent.addTabFun({
		src : ctx + "/words/index/add",
		title : "新增数据字典"
	});
}


$(function(){
	loadData();
});