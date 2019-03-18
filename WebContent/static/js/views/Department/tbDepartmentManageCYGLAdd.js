
$(function(){
	//性别
	$("#ttSex").combobox({
		data:[{id:"1",text:"男"},
		      {id:"0",text:"女"}],
		valueField:'id',
		textField:'text',
		panelHeight:"auto",
	});
	
		loadDate();
});

function loadDate(){
    //alert("进入loadDate()方法!");	
	var ttName = $("#ttName").val();//姓名
	var ttSex = $("#ttSex").combobox("getValue");//性别
	var tdId = $("#tdId").val();//部门表Id
    //alert(tdId);
	$('#tbDepartmentManageCYGLAdd').datagrid({
	    url:ctx+'/departmentManage/list3',
	    columns:[[
	        {field:'ttPhoto',title:'照片',width:130,align:'center',
		        formatter:function(value,rec){  
		        	var btn='';
	                btn += '<img height="32" width="32" src="' +value+ '"/>';
	        		return btn;
			        }
		        },
		    {field:'ttName',title:'姓名',width:130,align:'center'},
		    {field:'ttPhone',title:'手机号码',width:130,align:'center'},
		    {field:'ttSex',title:'性别',width:130,align:'center',formatter:function(value,rec){
	        	if(value == 1){
	        		return "男";
	        	}else if(value == 0){
	        		return "女";
	        	}
	        }},
	        {field:'pay',title:'操作',width:130,align:'center',formatter:function(value,rec){
	        	var btn = '';
	        	btn += '<a onclick="toSelect(\''+rec.ttId+'\',\''+rec.ttName+'\',\''+rec.ttPhone+'\')" href="javascript:void(0)">选择</a>';
        		return btn;
	        }},
	        
	    ]],
	    queryParams:{ttName:ttName,ttSex:ttSex,tdId:tdId},
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:true,
	   // fitColumns : true,
	    pagination : true,
	    sortOrder: 'desc', 
		pageSize : 20,
		pageList : [10, 15, 20, 30, 40, 50 ],
		rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
   		},
	    
	    onLoadSuccess:function(data){  
		       $('.searchcls').linkbutton({text:'查询',plain:true,iconCls:'icon-search'}); 
		       $('.addcls').linkbutton({text:'新  增',plain:true,iconCls:'icon-add'}); 
		}
	});
}


//跳转添加成员页面
function addHtml(){
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageCYGLAdd",
		title : "添加成员"
	});
}

var arr= new Array();


//点击成员管理中的添加成员,操作中的选择把相对应的姓名和工号显示到选择列表中.
function toSelect(ttId,ttName,ttPhone){
	
	var flag=true;
	//alert(ttId+","+ttName+","+ttNumber);
	
	
	for(var i=0;i<arr.length;i++){
		if(arr[i]==ttId){
      //	var removed = arr.splice(i,1); 
	 //		alert("删除的元素"+removed);
     //		alert("123aaa"+ttId);
			flag=false;
			return false;
		}
	}

	if(flag){
		$("#one").append('<li id="one"><span class="name0000001">'+ttName+'</span><span id="" class="no0000001">'+ttPhone+'</span><div class="clearfix"></div></li>');
		arr[arr.length]=ttId;
	}
	
	

}

//成员管理中的>添加成员>选择列表中的添加方法
function add(){
	
	var ids="";
	
	for(var i=0;i<arr.length;i++){

    // 		alert("添加的ttid:"+arr[i]);
     		if(i==arr.length-1){
     			ids=ids+arr[i];
     		}else{
     			ids=ids+arr[i]+",";
     		}
  
	}
	
//	alert(ids);
	
	var tdId = $("#tdId").val();//部门表Id
	
//	alert(tdId);

	$.ajax({
		url:ctx+"/departmentManage/addMore",
		data:{tdId:tdId,ids:ids},
		type:'POST',
		success:function(msg) {
			
			if(arr.length==0){
				$.messager.alert('提示','请选择添加的教师信息！','error');
			}else{
			    if(msg == "100"){
					$.messager.alert("操作提示", "添加成功！", "info", function () {  
				    test(tdId);
				  });
				}else{
					$.messager.alert('提示','添加失败！','error');
				}
			}
			
		}
	});
	
}


//重进页面
function test(tdId){
	parent.addTabFun({
		src : ctx + "/departmentManage/index/tbDepartmentManageCYGL?tdId="+tdId,
		title : "添加成员"
	});
}



