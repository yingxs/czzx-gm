var userType = 1;
//var toolbar = [{
//            text:'设置',
//            iconCls:'icon-edit',
//            handler:function(){
//            	onsubmitClick();
//            }
//        }];

var groupTree,userTree;
$(function(){
	groupTree = $('#groupTree').tree({
		url : ctx+'/group/treelist?date='+new Date,
		animate : false,
		lines : true,
		onClick : function(node) {
			var torganId = node.id;
			//获取项目列表 type=0表示获取组织结构的权限
			$("#menu-list").treegrid({
				url:ctx+"/perms/list?userId="+node.id+"&type="+0
			});
			
			//根据组织获取该组织所有所属用户 userType=1表示获取用户的权限
			getUserList(torganId);
		}
	});
});

function getUserList(torganId){
	$('#userTree').datagrid({
	    url:ctx+'/user/list?group='+torganId+'&date='+new Date,
	    columns:[[
	        {field:'groupName',title:'所属组织',width:100,align:'center'},
	        {field:'account',title:'登录名',width:120,align:'center'},
	        {field:'name',title:'真实姓名',width:120,align:'center'}
	    ]],
	    rownumbers:true,
	    singleSelect:true,
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
		},
	    onClickRow:function(rowIndex, rowData){
			$("#menu-list").treegrid({
				url:ctx+"/perms/list?userId="+rowData.id+"&type="+1
			});
	    }
	});
}


function formatChecked(val,row){
	var eid = "",ename="",tag="";

	if(row.parentId==0){
		eid = "parentbox-"+row.id;
		tag = eid;
	}else{
		eid = "childrenbox-"+row.id;
		tag = "parentTag-"+row.parentId;
	}
	ename = eid;
	if(val){
		return "<input type='checkbox' name='checked' id='"+eid+"' value='"+row.id+"' tag='"+tag+"' data-info='{\"parentId\":"+row.parentId+"}' checked='checked' />";
	}else{
		return "<input type='checkbox' name='checked' id='"+eid+"' value='"+row.id+"' tag='"+tag+"' data-info='{\"parentId\":"+row.parentId+"}' />";
	}
}

/**
 * 全选
 */
function allChecked(){
	var text =$("#all").text();
	if(text == '全选'){
		$("#all").click(function(){ 
			$("input:checkbox[name='checked']").attr("checked",'true');//全选 
			$("#all").text('取消');
		});
	}else{
		$("#all").click(function(){ 
			$("[name='checked']").removeAttr("checked");//取消全选  
			$("#all").text('全选');
		})
	}
}


function onsubmitClick(){
	var userId = "";
	
	var userNode = $('#userTree').datagrid('getSelected');
	var groupNode = $('#groupTree').tree("getSelected");
	if( null == userNode){
		userType = 2;
		userId = groupNode.id;
	}else{
		userId = userNode.id;
	}
	
	
	var checked = $("input:checkbox[name='checked']:checked");
	
	if(userId==""){
		$.messager.alert('提示','请先选择一个组织机构或用户!','info');
		return;
	}
	
	var menuIds = "";
	for(var i=0;i<checked.length;i++){
		if(menuIds!="")
			menuIds += ","; 
			menuIds += checked[i].value;
	}	

	$.messager.confirm("提示","确定要设置吗？",function(r){
		if(r){
			$.messager.progress({
                title:'请等待..',
                msg:'正在设置权限'
            });
			$.ajax({
				type:"POST",
				dataType:'text',
				url:ctx+"/perms/setting",
				data:{
					userId:userId,
					userType:userType,
					menuIds:menuIds
				},
				success:function(msg){
					$.messager.progress('close');
					if(msg == "0"){
						$.messager.alert("提示",'设置成功！','info');
						$("#menu-list").treegrid('load');
					}else if($.trim(msg)=='unauthorized'){
						$.messager.alert('提示','您没有权限!','warning');
					}else{
						$.messager.alert('提示','设置失败，请稍候再试!','error');
					}
				}
			});
		}
	});
}