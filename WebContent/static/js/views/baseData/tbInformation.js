function loadData(){
	var tiTitle = $("#tiTitle").val();//文章标题
	var ticName = $("#ticName").combobox("getValue");//文章分类
	var tiAddDate = $("#tiAddDate").datebox('getValue');//创建时间--开始
	var tiAddDateEnd = $("#tiAddDateEnd").datebox('getValue');//创建时间---结束
	$('#data-list').datagrid({
	    url:ctx+'/tbInformation/list',
	    columns:[[
	              {field:'ti_title',title:'文章标题',width:300,align:'center',resizable:'false'},
	             
	              {field:'ticName',title:'所属分类',width:150,align:'center',resizable:'false'},
	              {field:'tmName',title:'创建人',width:100,align:'center',resizable:'false'},
			      {field:'Tbu_addDate',title:'创建时间',width:160,align:'center',sortable:true},
			      {field:'pay',title:'操作',align:'center',width:200,resizable:'false',formatter:function(value,rec){
	        		var btn = '';  
	        		btn += '<a class="infocls" onclick="dials(\''+rec.ti_id+'\')" href="javascript:void(0)">详情</a>';
	        		btn += '<a class="updatecls" onclick="updRow(\''+rec.ti_id+'\')" href="javascript:void(0)">修改</a>';
	        		btn += '<a class="delcls" onclick="del(\''+rec.ti_id + '\',\''+rec.ti_title+'\')" href="javascript:void(0)">删除</a>';
	        		return btn;
	        	}},
		        	
	  	    ]],
	  	    queryParams:{tiTitle:tiTitle,tiAddDate:tiAddDate,tiAddDateEnd:tiAddDateEnd,ticName:ticName},
	  	    rownumbers:true,
		    singleSelect:true,
		    fit:true,
		    striped : true,
	        fitColumns : true,
		    pagination : true,
		    sortName : 'ti_add_date',
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
			       $('.updatecls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'}); 
			} 
	  	});
}



function addRow(){
	parent.addTabFun({
		src : ctx + "/tbInformation/index/add",
		title : "新增资讯内容"
	});
}

function updRow(id){
	parent.addTabFun({
		src : ctx + "/tbInformation/update?id=" + id,
		title : "编辑资讯内容"
	});
}

function dials(id){
	parent.addTabFun({
		src : ctx + "/tbInformation/dials?id=" + id,
		title : "资讯内容详情"
	});
}




function del(id,name){
	$.messager.confirm("提示","您确认要删除标题《"+name+"》的内容吗?，删除不可恢复哦!",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+ '/tbInformation/delete',
				data:{id:id,status:0},
				success:function(data){
					if(data=='100'){
						$.messager.alert('提示','删除成功!');
						$('#data-list').datagrid("load");
					}
					if(data=='101'){
						alert("数据有误，请联系技术人员核查-101!");
					}
				}
				
			});
		}
		
	});	
}

$(function(){
	$("#ticName").combobox({   
  		url:ctx+'/tbInformationCata/listAllParent',
  		valueField:'ticId',   
  		textField:'ticName',
  		/*onChange : function(n,o){
  			$("#tcCityid").combobox({   
    	  		url:ctx+'/words/getCity?id='+n,
    	  		valueField:'cid',   
    	  		textField:'cname'
    		});  
  		}  */
	}); 
	loadData();
});