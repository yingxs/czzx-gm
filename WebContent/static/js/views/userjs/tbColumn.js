
$(function(){
	
	loadDate();
});
function loadDate(){
	$('#column-list').treegrid({
	    url:ctx+'/admin/column/columnList',
	    method: 'POST',
	    singleSelect:false,
        rownumbers: true,
        idField: 'tcId',
        treeField: 'tcName',
        fitColumns:true,
        checkOnSelect:true,
	    columns:[[
	        {field:'ckbox',title:'选择',checkbox:true,width:120,align:'center'},
	        {field:'tcId',title:'栏目编号',width:120,align:'center',hidden:true},
	        {field:'tcParentId',title:'上级栏目',width:120,align:'center',hidden:true},
	        {field:'tcName',title:'栏目名称',width:120,align:'left'},
	        {field:'tcIndex',title:'栏目序号',width:100,align:'left',formatter:function(value,rec){
	        	if(rec.tcParentId == 0){
	        		return rec.tcId;
	        	}else{
	        		var parent = $('#column-list').treegrid('getParent',rec.tcId);
	        		var formatterIndex = parent.tcId+"-"+value;
	        		return formatterIndex;
	        	}
	        }},
	        {field:'tcType',title:'栏目类型',width:80,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return '栏目组';
	        	}else if (value == 2){
	        		return '单页';
	        	}else if (value == 3){
	        		return '文章列表';
	        	}else{
	        		return '相册列表';
	        	}
	        }},
	        {field:'tcContent',title:'栏目说明',width:280,align:'center'},
	        {field:'opt',title:'操作',width:120,align:'center',formatter:function(value,row){
	        	var btn = '';
	        	
	        	btn += '<a class="editcls" onclick="onEditSubmit(\''
	        			+row.tcId +'\',\''
	        			+row.tcName + '\',\''
	        			+row.tcParentId + '\',\''
	        			+row.tcType + '\',\''
	        			+row.tcIndex + '\',\''
	        			+row.tcContent + '\')" href="javascript:void(0)">编辑</a>&nbsp;';
	        	
        		return btn;
	        }}
	    ]],
	    onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删  除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'});  
		       $('.allcls').linkbutton({text:'全  选',plain:true,iconCls:'icon-all'});  
		}
	    
	    //---- 选择一个层级 ----//
	    /*onSelect:function(row){
	    	if(row.tcParentId == 0){
	    		var children = $('#column-list').treegrid('getChildren',row.tcId);
	    		for(var i=0;i<children.length;i++){
	    			$('#column-list').treegrid('select',children[i].tcId);
	    		}
	    	}
	    },
	    onUnselect:function(row){
	    	if(row.tcParentId == 0){
	    		var children = $('#column-list').treegrid('getChildren',row.tcId);
	    		for(var i=0;i<children.length;i++){
	    			$('#column-list').treegrid('unselect',children[i].tcId);
	    		}
	    	}
	    }*/
	    
	});
	

}


//弹出新增层
function newDlg() {
	$("#add-win").show();
	$("#add-form").form("clear");
	$("#add-win").window("open");
}

//取消
function myReset(){
	$("#add-win").window("close");
	$("#add-win").hide();
}

//取消
function editReset(){
	$("#edit-win").window("close");
	$("#edit-win").hide();
}

//全选
var selectBoolean = false;
function selectAll(){
	if(selectBoolean == false){
		$('#column-list').treegrid('selectAll');
		selectBoolean = true;
	}else{
		$('#column-list').treegrid('unselectAll');
		selectBoolean = false;
	}
	
	
}



//批量删除栏目
function removeColumn(){
	var tcIds = "";
	var tcNames="";
	var select = $("#column-list").treegrid('getSelections');
	if(select == null || select.length==0){
		$.messager.alert('提示','请先选择一个栏目!','info');
	}else{	
		
		for(var i=0;i<select.length;i++){
			if(i ==select.length-1 ){
				tcIds +=select[i].tcId;
				tcNames += select[i].tcName;
				//alert("2:"+tcNames);

			}else{
				tcIds+=select[i].tcId+",";
				tcNames += select[i].tcName+",";
				//alert("3:"+tcNames);

			}
		}
		//alert("4:"+tcNames);

		$.messager.confirm("提示","确定要删除【"+tcNames+"】吗？",function(r){
			if(r){
				$.messager.progress({
	                title:'请等待..',
	                msg:'正在删除栏目'
	            });
				
				$.ajax({
					type:"POST",
					dataType:'text',
					url:ctx+"/admin/column/removeColumns",
					data:{
						tcIds:tcIds,
						tcParentId:select.tcParentId
					},
					success:function(msg){
						$.messager.progress('close');
						if(msg == "0"){
							$.messager.alert("提示",'删除成功！','info');
							parent.addTabFun({
			        			src : ctx + "/admin/column/init_page",
			        			title : "栏目管理"
			        		});
						}else if($.trim(msg)=='unauthorized'){
							$.messager.alert('提示','您没有权限!','warning');
						}else if(msg == "112"){
							$.messager.alert('提示','栏目下有关联的数据，暂时不能删除!','warning');
						}else{
							$.messager.alert('提示','设置失败，请稍候再试!','error');
						}
					}
				});
			}
		});
	}
	
}


