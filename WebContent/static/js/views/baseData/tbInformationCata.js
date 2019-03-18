
$(function(){
	loadData();
});

function loadData(){
	var ticName = $("#ticName").val();//分类名称
	var tmAddDate = $("#tmAddDate").datebox('getValue');//创建时间--开始
	var tmAddDateEnd = $("#tmAddDateEnd").datebox('getValue');//创建时间---结束
	$('#data-list').datagrid({
	    url:ctx+'/tbInformationCata/list',
	    columns:[[
	              {field:'ticName',title:'分类名称',width:150,align:'center',resizable:'false'},
	              {field:'ticMean',title:'分类描述',width:150,align:'center',resizable:'false'},
	              {field:'ticAddPreson',title:'创建人',width:150,align:'center',resizable:'false'},
			      {field:'ticAddDate',title:'创建时间',width:150,align:'center',sortable:true},
			      {field:'pay',title:'操作',width:100,align:'center',width:200,resizable:'false',formatter:function(value,rec){
	        		var btn = '';  
	        			btn += '<a class="editcls" onclick="edit(\''+rec.ticId+'\')" href="javascript:void(0)">编辑</a>';
	        			btn += '<a class="delcls" onclick="del(\''+rec.ticId + '\',\''+rec.ticName+'\')" href="javascript:void(0)">删除</a>';
	        		return btn;
	        	}},
		        
	  	    ]],
	  	    queryParams:{ticName:ticName,tmAddDate:tmAddDate,tmAddDateEnd:tmAddDateEnd},
	  	    rownumbers:true,
		    singleSelect:true,
		    fit:true,
		    striped : true,
	        fitColumns : true,
		    pagination : true,
		    sortName : 'tic_add_date',
		    sortOrder: 'desc', 
		    sortable:true,
		    nowrap:false,
		    remoteSort: true,
		    rowStyler:function(index,row){
				if((index % 2) != 0) {
					return 'background-color:#FAFAD2;';
				} 
			},
			pageSize : 15,
			pageList : [10, 15, 20, 30, 40, 50 ],
	  		onLoadSuccess:function(data){  
			       $('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});  
			       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
			       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
			       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
			       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'}); 
			} 
	  	});
}



function addRow(){
	parent.addTabFun({
		src : ctx + "/tbInformationCata/index/add",
		title : "新增文章分类"
	});
}


function edit(ticId){
	parent.addTabFun({
		src : ctx + "/tbInformationCata/index/edit?ticId=" + ticId,
		title : "编辑文章分类"
	});
}

function del(ticId,ticName){
	$.messager.confirm("提示","您确认要删除内容《"+ticName+"》的内容分类吗?，删除不可恢复哦!",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/tbInformationCata/delete',
				data:{ticId:ticId},
				success:function(data){
					if(data=='100'){
						$.messager.alert('提示','删除成功!','info');
						$('#data-list').datagrid("load");
					}
					if(data=='101'){
						$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
						return false;
					}
					if(data=='102'){
						$.messager.alert('提示',"该资讯分类下存在资讯内容,请勿删除!",'warning');
						return false;
					}
				}
				
			});
		}
		
	});	
}