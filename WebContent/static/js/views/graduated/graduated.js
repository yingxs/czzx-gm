$(function(){
	loadData();
});


function loadData()
{
	var AddDate = $("#AddDate").textbox('getValue');
	var tsName = $("#tsName").textbox('getValue');
//	var AddDate = $("#AddDate").datebox('getValue');
//	var AddDateEnd = $("#AddDateEnd").datebox('getValue');
//	var tlName = $("#tlName").val();
//	var tlCategory = $("#tl_category").textbox("getValue");
	$('#channel-list').datagrid({
	    url:ctx+'/graduated/list',
	    columns:[[
	        {field:'ts_number',title:'学号',width:150,align:'center'},
	        {field:'ts_name',title:'姓名',width:100,align:'center'},
	        {field:'ts_sex',title:'性别',width:100,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "男";
	        	}else{
	        		return "女";
	        	}
	        }},
	        {field:'tcAddtime',title:'毕业年份',width:100,align:'center',sortable:true},
	        {field:'tscr_teacher_name',title:'班主任',width:100,align:'center'},
	        {field:'tsf_name',title:'家长姓名',width:100,align:'center'},
	        {field:'tsf_phone',title:'家长电话',width:100,align:'center'},
	        {field:'ts_address',title:'联系地址',width:100,align:'center'},
	        {field:'ts_status',title:'状态',width:100,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "毕业";
	        	}else{
	        		return "休学";
	        	}
	        }},
	       /* {field:'opt',title:'操作',width:150,align:'center',  
	        	 formatter:function(value,rec){  
	        		var btn = '';
	        		btn = '<a  onclick="eidtRow(\''+rec.tl_id+'\')" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
	        		
	        		btn += '<a  onclick="delRow(\''+rec.tl_id+'\',\''+rec.tl_name+'\')" href="javascript:void(0)">删除</a>';
	                return btn;  						
	             }				
	        }  */
	    ]],
	    queryParams:{'tsName':tsName,'AddDate':AddDate},
	    rownumbers : true,
	    singleSelect:true,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tb',
	    sortName : 'tcAddtime',
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

function delRow(tlId,tlName){
	$.messager.confirm("提示","您确定要删除名称为【"+tlName+"】的链接吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				dataType:'JSON',
				url:ctx+'/link/del',
				data:{tlId,tlId},
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
		src : ctx + "/link/index/add",
		title : "新增"
	});
}
