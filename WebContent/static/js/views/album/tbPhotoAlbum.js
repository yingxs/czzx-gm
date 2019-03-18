$(function(){
	loadData();
});

function loadData(){
	$('#album-list').datagrid({
	    url:ctx+'/photo/list',
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'tpa_id',hidden:true},
	        {field:'tpa_type',title:'文件类型',width:150,align:'center',
	        	 formatter:function(value,row){
	        		 if(value == "1"){
	        			 return "图片文件";
	        		 }else if(value == "2"){
	        			 return  "视频文件"; 
	        		 }
	        	}},
	        {field:'tpa_name',title:'文件名称',width:200,align:'center'},
	        {field:'tp_url',title:'封面图',width:200,align:'center',
	        	 formatter:function(value,row){
	        		 if(row.tp_url == null){
	        			 return "<img src='"+ctx+"/static/images/czzxdefault.jpg' height='100' width='150' />";
	        		 }else if(row.tpa_type == 1){
	        			 return  "<img src='"+ctx+row.tp_url+"' height='100' width='150' />"; 
	        		 }else if(row.tpa_type == 2){
	        			 return  "<video height='100' width='150' controls> <source src='"+ctx+row.tp_url+"'  type='video/mp4'><video>"; 
	        		 }
	        	}
	        },
	        {field: 'opt', title: '操作', width: 180, align: 'center',formatter: function (value,rowData) {
	        		//function里面的三个参数代表:当前字段值，当前行数据对象，行号（行号从0开始）
           	 		return  "<a class='infocls' href=javascript:info("+rowData.tpa_id+") style='cursor:hand'>详情</a>";
                }  
            } 
	    ]],
	    fit:true,
	    fitColumns : true,
		pagination : true,
	    pageSize : 10,
		pageList : [10, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
   		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'});
		       $('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-detail'});
		}
	});
}
function info(id){
	parent.addTabFun({
		src : ctx + "/admin/photo/xq?id="+id,
		title : "文件管理"
	});
}
