$(function(){
	loadDate();
});
function loadDate(){
	var tpName = $("#tpName").val();
	var tpType = $("#tpType").val();
	/*var tcId = $("#tcId").combotree('getValue');*/
	$('#product-list').datagrid({
	    url:ctx+'/product/list',
	    columns:[[
	        {field:'tpLogo',title:'图片',width:100,align:'center',
	        	formatter:function formatStatus(value,row,index){
	        		if (row.tpLogo !== null && row.tpLogo !== '' && row.tpLogo !== undefined) {
		        		return '<img height="30" width="60" src="' +row.tpLogo + '"/>';
		        	} else{
		        		return '<img height="30" width="60" src=""/>';
		        	}
	        	}},
	        {field:'tpName',title:'产品名称',width:120,align:'center'},
//	        {field:'tpcName',title:'所属分类',width:100,align:'center'},
	        {field:'tpNumber',title:'货号',width:100,align:'center'},
	        {field:'tpUnitId',title:'单位',width:100,align:'center'},
	        {field:'tpTradePrice',title:'订货单价(元)',width:80,align:'center',
	        	formatter:function(value,rec){
	        	return value.toFixed(2);
	        }},

	        {field:'pay',title:'操作',width:150,align:'center',formatter:function(value,rec){
	        	var btn = '';  
	        	btn += '<a class="editcls" onclick="onEditSubmit(\''+rec.tpId + '\')" href="javascript:void(0)">选购</a>';
        		return btn;
	        }},
	    ]],
	    queryParams:{tpName:tpName,tpType:tpType},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
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
function onEditSubmit(tpId){
	
	parent.addTabFun({
		src : ctx + "/product/index/productedit?tpId="+tpId,
		title : "选购"
	});

	
}