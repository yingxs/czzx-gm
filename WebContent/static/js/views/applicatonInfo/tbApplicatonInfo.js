
$(function(){
		loadDate();
});

function loadDate(){
    //alert("进入loadDate()方法!");	 
	var taiName = $("#taiName").val();//应用名称
	var AddDate = $("#AddDate").datebox('getValue');//创建开始时间
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');//创建结束时间	
	$('#tbApplicatonInfo').datagrid({
	    url:ctx+'/tbApplicatonInfo/list',
	    columns:[[
            {field:'taiIcon',title:'图标',width:100,align:'center',
	           formatter:function(value,rec){  
	        	   if(value != null || value==''){
		        		var btn='';
		                btn += '<img height="32" width="32" src="' +value+ '"/>';
		        		return btn;
		        	}else{
		        		return '<img height="32" width="32" src="'+ctx+'/static/images/czzxdefault1.jpg">';
		        	}
		        	
			        }
		        },
			{field:'taiName',title:'应用名称',width:100,align:'center',formatter:function(value,rec){
	        	if(rec.taiName!=null&&rec.taiName!=""){
		        	//	alert(rec.ttmMemberId);
			        	var btn = '';  
		        		btn += '<a onclick="toApplicatonDetail(\''+rec.taiId+'\')" href="javascript:void(0)">'+rec.taiName+'</a>';
		        		return btn;
		        	}
		        }},
			
	        {field:'taiWebAddr',title:'应用地址',width:100,align:'center'},
	        {field:'taiPageCount',title:'浏览量',width:100,align:'center'},
	        {field:'taiAddDate',title:'创建时间',width:100,align:'center',sortable:true},
	        {field:'pay',title:'操作',width:100,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	btn += '<a class="editcls" onclick="toUpdate(\''+rec.taiId+'\')" href="javascript:void(0)">编辑</a>&nbsp;';
        		btn += '<a class="delcls" onclick="toDelete(\''+rec.taiId+'\',\''+rec.taiName+'\')" href="javascript:void(0)">删除</a>&nbsp;';
        		return btn;
	        }},
	    ]],
	    queryParams:{taiName:taiName,AddDate:AddDate,AddDateEnd:AddDateEnd},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	    fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 15,
		pageList : [10, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
	    
	    onLoadSuccess:function(data){  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'});
		       $('.editcls').linkbutton({text:'编  辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删  除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'});  

		}
	});
}


//跳转新增应用页面
function addHtml(){
	parent.addTabFun({
		src : ctx + "/tbApplicatonInfo/index/tbApplicatonInfoAdd",
		title : "新增应用"
	});
}



//跳转到应用管理编辑页面
function toUpdate(taiId){
	//alert(taiId);
	parent.addTabFun({
		src : ctx + "/tbApplicatonInfo/index/tbApplicatonInfoUpdate?taiId="+taiId,
		title : "应用编辑"
	});
}



//删除
function toDelete(taiId,taiName){
	//alert(taiName);
	$.messager.confirm("提示","您确认要删除应用名称为["+taiName+"]的部门？",function(r){
		if(r){
			
			$.ajax({
				type: 'POST',
				url: ctx+"/tbApplicatonInfo/delete",
				data:{taiId:taiId},
				dataType: 'text',
				success: function (msg) {
					if(msg == "100"){
						$.messager.alert('提示','删除成功!');
						//删除成功,重新加载页面
						$('#tbApplicatonInfo').datagrid('load');
					}else if(msg == "101"){
						$.messager.alert('提示','删除失败！','error');
					}
				}
			});
		}
	});
 }

	
//跳转应用管理详情页面
function toApplicatonDetail(taiId){
	parent.addTabFun({
		src : ctx + "/tbApplicatonInfo/index/tbApplicatonInfoDetail?taiId="+taiId,
		title : "应用详情"
	});
}


