
$(function(){
	$("#menu-list").treegrid({
		url:ctx + '/menu/listTree',
		onLoadSuccess:function(){
			$('#add-parentId').combotree({
				url:ctx + '/menu/get_json',
			});
			
			$('#update-parentId').combotree({
				url:ctx + '/menu/get_json',
			});
		},
	});
	
});

function openAdd(){
	var selection = $('#menu-list').treegrid('getSelected');
	$("#add-form").form("reset");
	$("#add-win").window("open");
	if(selection!=null){
		if(selection.parentId==0){
			$("#add-parentId").combotree("setValue",selection.id);
		}else{
			$("#add-parentId").combotree("setValue",selection.parentId);
		}
	}
}

function openUpdate(){
	var selection = $('#menu-list').treegrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择一项!');
		return;
	}
	//alert(selection.name);
	$("#update-id").val(selection.id);
	$("#update-name").textbox('setValue',selection.name);
	$("#update-parentId").combotree("setValue",selection.parentId);
	$("#update-url").textbox('setValue',selection.url);
	$("#update-sortcode").numberbox('setValue',selection.sortCode);
	$("#update-win").window("open");
}

function addMenu(){
	if($("#add-form").form('validate') == false){
		return false;
	}
	
	var name = $("#add-name").val();
	var parentId = $("#add-parentId").combotree("getValue");
	var url = $("#add-url").val();
	var sortCode = $("#add-sortcode").val();
	
	
	if(parentId==null)parentId = 0;
	if(sortCode==null)sortCode = 0;
	
	$.post(ctx+"/menu/add",{name:name,url:url,parentId:parentId,sortCode:sortCode},function(msg){
		if(msg=="1"){
			$("#add-win").window("close");
			$('#menu-list').treegrid('reload');
		}else if(msg=="2"){
			$.messager.alert('提示','提交参数不对!');
		}else{
			$.messager.alert('提示','添加失败，请稍候再试!');
		}
	},"text");
}

function updateMenu(){
	if($("#update-form").form('validate') == false){
		return false;
	}
	
	var id = $("#update-id").val();
	var name = $("#update-name").val();
	var parentId = $("#update-parentId").combotree("getValue");
	var url = $("#update-url").val();
	var sortCode = $("#update-sortcode").val();
	
	if(parentId==null)parentId = 0;
	if(sortCode==null)sortCode = 0;
	
	$.post(ctx+"/menu/update",{id:id,name:name,url:url,parentId:parentId,sortCode:sortCode},function(msg){
		if(msg=="1"){
			$("#update-win").window("close");
			$('#menu-list').treegrid('reload');
		}else if(msg=="2"){
			$.messager.alert('提示','提交参数不对!');
		}else{
			$.messager.alert('提示','更新失败，请稍候再试!');
		}
	},"text");
}

function delMenu(){
	
	var selection = $('#menu-list').treegrid('getSelected');
	if(selection==null){
		$.messager.alert('提示','请先选择一项!');
		return;
	}
	
	$.messager.confirm("提示","确定要删除该项吗？",function(r){
		if(r){
			
			$.ajax({
				url:ctx+"/menu/del",
				data:{id:selection.id},
				type:"POST",
				dataType:'text',
				success:function(msg){
					if(msg=="1"){
						$('#menu-list').treegrid('reload');
					}else if(msg==2){
						$.messager.alert('提示','该项不存在!');
					}else if(msg==3){
						$.messager.alert('提示','该菜单下还有子菜单，不能删除!');
					}else{
						$.messager.alert('提示','删除失败，请稍候再试!');
					}
				}
			});
		}
	});
	
}