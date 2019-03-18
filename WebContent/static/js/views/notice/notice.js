$(function(){
	$("#tniType").combobox({
		data:[{id:1,text:"内部公告"},
		      {id:2,text:"外部公告"}
		      ],
		valueField:'id',
		textField:'text',
		});
	
	loadData();
});


function loadData()
{
	var AddDate = $("#AddDate").datebox('getValue');
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');
	var tniTitle = $("#tniTitle").val();
	var tniType = $("#tniType").textbox("getValue");
	
	$('#channel-list').datagrid({
	    url:ctx+'/notice/list',
	    columns:[[
	        {field:'tni_title',title:'公告标题',width:150,align:'center',formatter:function(value,rec){
	        	return '<a href=javascript:infoHtml(\''+rec.tni_id+'\') plain:true>'+value+'</a>';
	        }},
	        {field:'tni_type',title:'公告类型',width:100,align:'center',
	        	formatter:function(value,rec){
	        		var tniType = rec.tni_type;
	        		if(tniType==1) return '内部公告';
	        		if(tniType==2) return '外部公告';
	        	}},
	        {field:'tni_recv_type',title:'接受类型',width:100,align:'center',
	        	formatter:function(value,rec){
	        		var type = rec.tni_recv_type;
	        		if(type==0) return '全部';
	        		if(type==2) return '教职工';
	        		if(type==3) return '家长';
	        	}},
	        {field:'name',title:'创建人',width:100,align:'center'},
	        {field:'tniAddDate',title:'创建时间',width:100,align:'center',sortable:true},
	        {field:'opt',title:'操作',width:250,align:'center',  
	        	 formatter:function(value,rec){  
	        		var btn = '';
	        		if(rec.tni_type ==2){
	        		btn = '<a class="editcls"  onclick="eidtRow(\''+rec.tni_id+'\')" href="javascript:void(0)">编辑</a>&nbsp;';
	        		}else{
	        			btn +='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	        		}
	        		
	        		if(rec.tni_top==0){
	        			btn += '<a class="totop"  onclick="setTop(\''+rec.tni_id+'\',\''+rec.tni_title+'\')" href="javascript:void(0)">置顶</a>&nbsp;';
	        		}else if(rec.tni_top==1){
	        			btn += '<a class="totop_no"  onclick="Top(\''+rec.tni_id+'\',\''+rec.tni_title+'\')" href="javascript:void(0)">取消置顶</a>&nbsp;';
	        		}
	        		if(rec.tni_head==0){
	        			btn += '<a class="totoutiao" onclick="setHead(\''+rec.tni_id+'\',\''+rec.tni_title+'\')" href="javascript:void(0)">设为头条</a>';
	        		}else if(rec.tni_head==1){
	        			btn += '<a class="totoutiao_no"  onclick="Head(\''+rec.tni_id+'\',\''+rec.tni_title+'\')" href="javascript:void(0)">取消头条</a>';
	        		}
	        		btn += '<a class="delcls"  onclick="delRow(\''+rec.tni_id+'\',\''+rec.tni_title+'\')" href="javascript:void(0)">删除</a>&nbsp;';
	                return btn;  						
	             }				
	        }  
	    ]],
	    queryParams:{'tniTitle':tniTitle,'tniType':tniType,'AddDate':AddDate,'AddDateEnd':AddDateEnd},
	    rownumbers : true,
	    singleSelect:true,
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
		pageSize : 15,
		pageList : [10, 15, 20, 30, 40, 50 ],
		onLoadSuccess:function(data){  
		       $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});  
		       $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});  
		       $('.addcls').linkbutton({text:'新增',plain:true,iconCls:'icon-add'});  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		       $('.totop').linkbutton({text:'置  顶',plain:true,iconCls:'icon-to-top'});
		       $('.totoutiao').linkbutton({text:'设置头条',plain:true,iconCls:'icon-to-toutiao'});
		       $('.totop_no').linkbutton({text:'取消置顶',plain:true,iconCls:'icon-to-top'});
		       $('.totoutiao_no').linkbutton({text:'取消头条',plain:true,iconCls:'icon-to-toutiao'});
		}
	});
}
 var toolbar = [{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
            	parent.addTabFun({
					src : ctx + "/channel/index/add",
					title : "新增公告"
				});
            	
            }
        },{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){
            	var selection = $('#words-list').datagrid("getSelected");
            	if(selection == null){
            		$.messager.alert('提示','请先选择一个字典!');
            		return;
            	}
            	
            	parent.addTabFun({
					src : ctx + "/channel/paper/edit?tgId="+selection.tspId,
					title : "编辑产品"
				});
            }
        },{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
            	delUser();
            }
        }];

function delRow(tniId,tniName){
	$.messager.confirm("提示","您确定要删除名称为【"+tniName+"】的公告吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/notice/del',
				data:{tniId,tniId},
				success:function(data){
					if(data=='100'){
						$('#channel-list').datagrid("reload");
					}
					if(data=='101'){
						$messager.alert("提示","请选择要删除的行","error");
					}
					if(data=='110'){
						$messager.alert("提示","数据有误，请联系技术人员核查!","error");
					}
				}
			})
		}
	})
}

function setTop(tniId,tniName){
	$.messager.confirm("提示","您确定要设置名称为【"+tniName+"】置顶吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/notice/setTop',
				data:{tniId,tniId},
				success:function(data){
					if(data=='100'){
						$('#channel-list').datagrid("load");
					}
					if(data=='101'){
						$messager.alert("提示","请选择要删除的行","error");
					}
					if(data=='110'){
						$messager.alert("提示","数据有误，请联系技术人员核查!","error");
					}
				}
			})
		}
	})
}


function Top(tniId,tniName){
	$.messager.confirm("提示","您确定要取消名称为【"+tniName+"】置顶吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/notice/setTop',
				data:{tniId,tniId},
				success:function(data){
					if(data=='100'){
						$('#channel-list').datagrid("load");
					}
					if(data=='101'){
						$messager.alert("提示","请选择要删除的行","error");
					}
					if(data=='110'){
						$messager.alert("提示","数据有误，请联系技术人员核查!","error");
					}
				}
			})
		}
	})
}

function setHead(tniId,tniName){
	$.messager.confirm("提示","您确定要设置名称为【"+tniName+"】头条吗？？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/notice/setHead',
				data:{tniId,tniId},
				success:function(data){
					if(data=='100'){
						$('#channel-list').datagrid("load");
					}
					if(data=='101'){
						$messager.alert("提示","请选择要删除的行","error");
					}
					if(data=='110'){
						$messager.alert("提示","数据有误，请联系技术人员核查!","error");
					}
				}
			})
		}
	})
}


function Head(tniId,tniName){
	$.messager.confirm("提示","您确定要取消名称为【"+tniName+"】头条吗？？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/notice/setHead',
				data:{tniId,tniId},
				success:function(data){
					if(data=='100'){
						$('#channel-list').datagrid("load");
					}
					if(data=='101'){
						$messager.alert("提示","请选择要删除的行","error");
					}
					if(data=='110'){
						$messager.alert("提示","数据有误，请联系技术人员核查!","error");
					}
				}
			})
		}
	})
}


function eidtRow(tniId)
{
	parent.addTabFun({
		src : ctx + "/notice/index/edit?tniId="+tniId,
		title : "编辑公告"
	});
}

function shio(){
	if($("#tniType").attr('checked')==undefined){
		document.getElementById("tniType").value= 2;
	}else {
		document.getElementById("tniType").value= 1;
	}
}

function addRow()
{
	parent.addTabFun({
		src : ctx + "/notice/index/add",
		title : "新增公告"
	});
}

function infoHtml(tniId){
	parent.addTabFun({
		src : ctx + "/notice/index/detail?tniId="+tniId,
		title : "公告详情 "
	});
}
