function loadData(){
	var tmNumber = $("#tmNumber").val();
	$('#data-list').datagrid({
	    url:ctx+'/message/infoList',
	    columns:[[
	              {field:'tmName',title:'姓名',width:200,align:'center',resizable:'false'},
			      {field:'tmAccount',title:'登录账号',width:200,align:'center',resizable:'false'},
			      {field:'tmType',title:'会员类型',width:200,align:'center',resizable:'false',formatter:function(value,rec){
			    	  if(value==1){
			    		  return "注册会员";
			    	  }
			    	  if(value==2){
			    		  return "经销商";
			    	  }
			    	  if(value==3){
			    		  return "水工";
			    	  }
			      }},
			      {field:'tmPhone',title:'手机号码',width:200,align:'center',resizable:'false'},     	
	  	    ]],
	  	    queryParams:{tmNumber:tmNumber},
	  	    rownumbers:true,
		    singleSelect:true,
		    fit:false,
		    striped : true,
	        fitColumns : true,
	        nowrap:false,
		    pagination : true,
		    sortOrder: 'desc', 
		    sortable:true,
		    remoteSort: true,
		    rowStyler:function(index,row){
				if((index % 2) != 0) {
					return 'background-color:#FAFAD2;';
				} 
			},
			pageSize : 15,
			pageList : [10, 15, 20, 30, 40, 50 ],
	  	});
}

$(function(){
	loadData();
});