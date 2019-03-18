$(function(){
	/*$("#tl_category").combobox({
		data:[{id:1,text:"杭州市属各学校"},
		      {id:2,text:"杭州各县区教育"},
		      {id:3,text:"杭州各教育网站"},
		      {id:4,text:"杭州各学校"}],
		valueField:'id',
		textField:'text',
		});*/
	
	loadData();
});


function loadData()
{
	var AddDate = $("#AddDate").datebox('getValue');
	var AddDateEnd = $("#AddDateEnd").datebox('getValue');
	var tlName = $("#tlName").val();
	/*var tlCategory = $("#tl_category").textbox("getValue");*/
	$('#channel-list').datagrid({
	    url:ctx+'/link/list',
	    columns:[[
	        {field:'tl_picture',title:'图片',width:150,align:'center',formatter:function(value,row,index){
	       		 if(row.tl_picture == null){
	       			 return "无";
	       		 }else{
	       			 return  "<img src='"+ctx+row.tl_picture+"' height='50' width='80' />"; 
	       		 }
		        }},
	        {field:'tl_name',title:'链接名称',width:100,align:'center'},
	        {field:'tl_url',title:'链接地址',width:100,align:'center'},
	        /*{field:'tl_category',title:'所属分类',width:100,align:'center',formatter:function(value,rec){
	        	var category = rec.tl_category;
	        	if(category==1) return '杭州市属各学校';
	        	if(category==2) return '杭州各县区教育';
	        	if(category==3) return '杭州各教育网站';
	        	if(category==4) return '杭州各学校';
	        }},*/
	        {field:'tl_index',title:'排序',width:100,align:'center'},
	        {field:'tlAddtime',title:'创建时间',width:200,align:'center',sortable:true},
	        {field:'opt',title:'操作',width:150,align:'center',  
	        	 formatter:function(value,rec){  
	        		var btn = '';
	        		btn = '<a class="editcls"  onclick="eidtRow(\''+rec.tl_id+'\')" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
	        		
	        		btn += '<a class="delcls"  onclick="delRow(\''+rec.tl_id+'\',\''+rec.tl_name+'\')" href="javascript:void(0)">删除</a>';
	                return btn;  						
	             }				
	        }  
	    ]],
	    queryParams:{'tlName':tlName,'AddDate':AddDate,'AddDateEnd':AddDateEnd},
	    rownumbers : true,
	    singleSelect:true,
	    fit:true,
	    striped : true,
	    pagination : true,
	    fitColumns : true,
	    toolbar:'#tb',
	    sortName : 'tlAddtime',
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
		title : "编辑链接"
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
		title : "新增链接"
	});
}
