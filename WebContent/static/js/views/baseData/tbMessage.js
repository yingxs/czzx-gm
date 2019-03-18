
function loadData(){
	var tmTitle = $("#tmTitle").val();//消息标题
	var tmAddDate = $("#tmAddDate").datebox('getValue');//创建时间--开始
	var tmAddDateEnd = $("#tmAddDateEnd").datebox('getValue');//创建时间---结束
	$('#data-list').datagrid({
	    url:ctx+'/message/list',
	    columns:[[
	              {field:'tmTitle',title:'消息标题',width:200,align:'center',resizable:'false'},
	              {field:'tmAddperson',title:'创建人',width:150,align:'center',resizable:'false'},
			      {field:'tmAddtime',title:'创建时间',width:150,align:'center',sortable:true}, 
			      {field:'pay',title:'操作',width:150,align:'center',width:200,resizable:'false',formatter:function(value,rec){
	        		var btn = '';  
	        			btn += '<a class="infocls" onclick="detail(\''+rec.tmId+'\')" href="javascript:void(0)">详情</a>';
	        			btn += '<a class="delcls" onclick="del(\''+rec.tmNumber+'\')" href="javascript:void(0)">删除</a>';
	        		return btn;
	        	}},
		        	
	  	    ]],
	  	    queryParams:{tmTitle:tmTitle,tmAddDate:tmAddDate,tmAddDateEnd:tmAddDateEnd},
	  	    rownumbers:true,
		    singleSelect:true,
		    fit:true,
		    striped : true,
	        fitColumns : true,
	        sortName : 'tmAddDate', 
	        sortOrder: 'desc', 
	        nowrap:false,
		    pagination : true,
		    sortable:true,
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
			       $('.addcls').linkbutton({text:'发送消息',plain:true,iconCls:'icon-add'});  
			       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
			       $('.updatecls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'}); 
			} 
	  	});
}




function send(){
	parent.addTabFun({
		src : ctx + "/message/index/send",
		title : "发送消息"
	});
	
}
function detail(tmId){
	parent.addTabFun({
		src : ctx + "/message/index/info?tmId="+tmId,
		title : "消息详情"
	});
	
}


function del(tmNumber){
	$.messager.confirm("提示","您确认要删除吗，删除不可恢复哦!",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/message/delete',
				data:{tmNumber:tmNumber},
				success:function(data){
					if(data=='100'){
						$.messager.alert('提示','删除成功!');
						$('#data-list').datagrid("load");
					}else if(data=='101'){
						$.messager.alert("登录超时!");
					}else{
						$.messager.alert("数据有误，请联系技术人员核查!");
					}
				}
				
			});
		}
		
	});	
}

$(function(){
	loadData();
});