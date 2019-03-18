$(function(){
	//初始化广告板块combobox
	$('#tacCata').combobox({
		url:ctx+ '/advertise/findCatalogName',
		method:'POST',
		valueField:'tacCataId',
		textField:'tacCataName'
	});
	
	loadData();
});

function loadData(){
	var tacCata = $("#tacCata").combobox("getValue");
	var AddDate = $("#AddDate").datebox("getValue");
	var AddDateEnd = $("#AddDateEnd").datebox("getValue");
	var tacContent  = $("#tacContent").val();
	$('#advertise-list').datagrid({
	    url:ctx+'/advertise/list',
	    columns:[[
	        {field:'tacCataName',title:'板块名称',width:150,align:'center'},
	        {field:'tacContent',title:'广告名称',width:200,align:'center'},
	        {field:'tacPhoneImage',title:'手机版',width:200,align:'center',
	        	 formatter:function(value,row,index){
	        		 if(row.tacPhoneImage == null){
	        			 return "";
	        		 }else{
	        			 return  "<img src='"+ctx+row.tacPhoneImage+"' height='100' width='150' />"; 
	        		 }
	        		 
	        	}
	        },
	        {field:'tacWebImage',title:'web版',width:200,align:'center',
	        	formatter:function(value,row,index){
	        		 if(row.tacWebImage == null){
	        			 return "";
	        		 }else{
	        			 return  "<img src='"+ctx+row.tacWebImage+"' height='100' width='150'/>"; 
	        		 }
	        		 
	        	}
	        },
	        {field:'tacAddPerson',title:'创建人',width:100,align:'center'}, 
	        {field: 'opt', title: '操作', width: 180, align: 'center',formatter: function (value,rowData,rowIndex) {
	        		//function里面的三个参数代表:当前字段值，当前行数据对象，行号（行号从0开始）
           	 		return  "<a class='infocls' href=javascript:info("+rowData.tacId+") style='cursor:hand'>详情</a>" +
           	 		"<a class='editcls' href=javascript:onEditSubmit("+rowData.tacId+") style='cursor:hand'>详情</a>" +
           	 		"<a class='delcls' href=javascript:onDeleteSubmit("+rowData.tacId+") style='cursor:hand'>删除</a>";
                }  
            } 
	    ]],
	    queryParams:{'tacCata':tacCata,'tacContent':tacContent,AddDateEnd:AddDateEnd,AddDate:AddDate},
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
		       $('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
		}
	});
}

function onEditSubmit(tacId){
	parent.addTabFun({
		src : ctx + "/advertise/index/edit?tacId="+tacId,
		title : "编辑广告内容"
	});
}

/**
 * 打开详情页面
 * @return
 */
function info(id){
	parent.addTabFun({
		src : ctx + "/advertise/index/info?tacId="+id,
		title : "广告内容详情"
	});
}

/**
 * 删除广告内容
 * @return
 */
function onDeleteSubmit(id){
	$.messager.confirm("提示","确定要删除该广告吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/advertise/delete',
				data:{tacId:id},
				success:function(data){
					if(data == '100'){
						$('#advertise-list').datagrid("reload");
					}
				}
				
			});
		}
	});
}

	