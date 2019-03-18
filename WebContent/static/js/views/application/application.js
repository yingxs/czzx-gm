$(function(){
	$("#tl_category").combobox({
		data:[{id:1,text:"杭州市属各学校"},
		      {id:2,text:"杭州各县区教育"},
		      {id:3,text:"杭州各教育网站"},
		      {id:4,text:"杭州各学校"}],
		valueField:'id',
		textField:'text',
		});
	
	loadData();
});


function loadData()
{
	var AddDate = $("#AddDate").datebox('getValue');
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');
	var ttName = $("#ttName").val();
	$('#channel-list').datagrid({
	    url:ctx+'/application/list',
	    columns:[[
	        {field:'tt_name',title:'资源所属',width:150,align:'center'},
	        {field:'tai_web_addr',title:'资源地址',width:100,align:'center'},
	        {field:'taiAddDate',title:'创建时间',width:200,align:'center',sortable:true},
	        {field:'opt',title:'操作',width:150,align:'center',  
	        	 formatter:function(value,rec){  
	        		var btn = '';
	        		btn = '<a  onclick="delRow(\''+rec.tai_id+'\',\''+rec.tai_name+'\')" href="javascript:void(0)">删除</a>';
	                return btn;  						
	             }				
	        }  
	    ]],
	    queryParams:{'ttName':ttName,'AddDate':AddDate,'AddDateEnd':AddDateEnd},
	    rownumbers : true,
	    singleSelect:true,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tb',
	    sortName : 'taiAddDate',
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
		}
	});
}
 var toolbar = [{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
            	parent.addTabFun({
					src : ctx + "/channel/index/add",
					title : "新增"
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

function delRow(taiId,taiName){
	$.messager.confirm("提示","您确定要删除名称为【"+taiName+"】的资源吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/application/del',
				data:{taiId,taiId},
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



function eidtRow(tlId)
{
	parent.addTabFun({
		src : ctx + "/link/index/edit?tlId="+tlId,
		title : "编辑"
	});
}

function shio(){
	if($("#tlhStatus").attr('checked')==undefined){
		document.getElementById("tlhStatus").value= 2;
	}else {
		document.getElementById("tlhStatus").value= 1;
	}
}

function addRow()
{
	parent.addTabFun({
		src : ctx + "/application/index/add",
		title : "新增"
	});
}
