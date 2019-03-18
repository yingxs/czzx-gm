//添加富文本框
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="tniContent"]', {
      resizeType : 1,
      formatUploadUrl:false,
      allowPreviewEmoticons : false,
      allowImageUpload : true,
      uploadJson : ctx+'/tbInformation/imageUpload',
      filePostName:'file',
     	items : [
     	 		'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', '|', 'justifyleft', 'justifycenter', 'justifyright',
     	 		'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
     	 		'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
     	 		'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
     	 		'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
     	 		'anchor', 'link', 'unlink', '|', 'about'
     	 	],
	});

});


$(function(){
	$("#tgId").combobox({
		data:[{id:1,text:"高一"},
		      {id:2,text:"高二"},
		      {id:3,text:"高三"}
		      ],
		valueField:'id',
		textField:'text',
		});
	
	$('#classId').combobox({
		url:ctx+ '/class/getClassName',
		method:'POST',
		valueField:'tcId',
		textField:'tcName',
		mode : 'remote',
		editable : false
	});
	
	
});


function loadData()
{
	var tgId = $("#tgId").textbox("getValue");
	var tsfName = $("#tsfName").val();
//	var tcId = $("#tcId").textbox("getValue");
	$('#channel-list').datagrid({
	    url:ctx+'/notice/recvPersons',
	    columns:[[
	        {field:'tsf_id',checkbox:true},
	        {field:'ts_name',title:'学生姓名',width:150,align:'center'},
	        {field:'tsf_name',title:'家长姓名',width:100,align:'center'},
	        {field:'tg_name',title:'所属年级',width:100,align:'center'},
	        {field:'tc_name',title:'所属班级',width:100,align:'center'},
	        {field:'tsf_phone',title:'手机号码',width:100,align:'center'}
	    ]],
	    queryParams:{'tgId':tgId,'tsfName':tsfName},
	    rownumbers : true,
	    singleSelect:false,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tb',
	    sortName : 'tniAddDate',
	    sortOrder: 'desc',
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			}
			},
		pageSize : 5,
		pageList : [5, 10, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	});
}

function load()
{
	
	$('#teacher-list').datagrid({
	    url:ctx+'/notice/recvTheacher',
	    columns:[[
	        {field:'tt_id',checkbox:true},
	        {field:'tt_name',title:'教师姓名',width:10,align:'center'},
	        {field:'td_name',title:'所属部门',width:20,align:'center'},
	        {field:'tt_phone',title:'手机号码',width:20,align:'center'}
	    ]],
	    queryParams:{},
	    rownumbers : true,
	    singleSelect:false,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tc',
	    sortName : 'tniAddDate',
	    sortOrder: 'desc',
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			}
			},
		pageSize : 5,
		pageList : [5, 10, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		}
	});
}

function addBtn(){
	editor.sync();
	if ($("#add-form").form('validate') == false) {
		return false;
	}
	var type = $("#recvType").textbox("getValue");
	if(type==2){
		var selection = $('#teacher-list').datagrid('getChecked');
	}else if(type==3){
		var selection = $('#channel-list').datagrid('getChecked');
	}
	
	if(selection==''){
		$.messager.alert('提示','请先选择接收人员!');
		return;
	}
	var ids = "";
	var types = "";
	if(type != null && type !=0){
		$.each(selection,function(index,item){
			if(type==2){
				ids += ""+item.tt_id;
			}else if(type==3){
				ids += ""+item.tsf_id;
			}
	//		types += ""+item.tmType;
			if(index+1<selection.length){
				ids+=",";
	//			types+=",";
			}
		})
	}
	
	$.ajax({
		type:"POST",
		dataType:'json',
		url:ctx+ '/notice/add?ids='+ids,
		data:$("#add-form").serialize(),
		success:function(data){
			if(data == '100')
			{
				parent.addTabFun({
					src : ctx + "/notice/index/notice_list",
					title : "公告管理"
				});
				parent.closeTab("新增公告");
			}
			else if(data=='101'){
				$.messager.alert('提示','参数提交不完整!','warning');
			}
		}
	});
}
