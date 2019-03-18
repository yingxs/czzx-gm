
$(function(){
	
	init_UI();
	
	// 验证并查询
	loadData_validate();
	
	//批量删除触发
	$("#batch_remove").click(function(){
		$(this).attr("disabled",true);
		batch_del();
	});
});

// 数据提交前验证
function loadData_validate(){
	$('#search_form').form('submit',{
		onSubmit:function(){
			//数据验证，符合规范返回true,否则返回false
			var bool= $(this).form('enableValidation').form('validate');
			if(!bool){
				$.messager.alert("提示","请提交符合规范的数据！","error");
			}else{
				loadData();
			}
		}
	});
}

// 表格初始化与属性设置
function loadData(){
	
	var majorName = $.trim($("#textbox_major_name").val());
	var departmenId = $.trim($("#combobox_departmen_id").combobox("getValue"));
	 departmenId == undefined ? " ": departmenId;
	var dateStart = $.trim($("#date_start").datebox("getValue"));
	var dateEnd = $.trim($("#date_end").datebox("getValue"));
	
	$("#table_TbMajorInfo_list").datagrid({
		loader:loaderStore,
		title:"基础数据 > 专业管理",
		idfield:'Ts_id',
		columns:[[
			{
				field:'tii_id',
				title:'序号',
				width:44,
				height:30,
				align:'center',
				formatter:function(val,row,index){
					 var options = $("#table_TbMajorInfo_list").datagrid('getPager').data("pagination").options; 
				     var currentPage = options.pageNumber;
				     var pageSize = options.pageSize;
				     return (pageSize * (currentPage -1))+(index+1);
				}
			},
			{
				field:'tii_name',
				title:'所属学院',
				width:150,
				align:'center',
//				checkbox:true
			},
			{
				field:'tidi_name',
				title:'所属系别',
				width:150,
				align:'center',
				sortable:true
			},
			{	
				field:'tmi_name',
				title:'专业名称',
				width:150,
				align:'center'
			},
			{	
				field:'tw_name',
				title:'专业年限',
				width:100,
				align:'center'
			},
			{	
				field:'tmi_code',
				title:'专业代码',
				width:100,
				align:'center',
				sortable:true,
				formatter:function(val,row){
					
					return val == null ? '-' : val;
				}
			},
			{	
				field:'tmi_add_date',
				title:'创建时间',
				width:250,
				align:'center',
				sortable:true,
				formatter:function(val,row){
					return formatDateTime(val);
				}
			},
			{
				field:'opt',
				title:'操作',
				width:300,
				align:'center',  
	        	formatter:function(value,rec){
	        		// 设置按钮
	        		var btn='';
				    btn += '<a class="btn-del" onclick="delMajorInfo(\''+rec.tmi_name+'\','+rec.tmi_id+')" href="javascript:void(0)">删除</a>'; 
				    btn +='&nbsp;|&nbsp;';
				    btn += "<a class='btn-edit' href='"+ctx+"/TbMajorInfo/index/edit?TbMajorInfoId="+rec.tmi_id+"'>编辑</a>"
		            return btn;  
	           } 
	        } 
			
		]],
		queryParams:{
	    	'majorName':majorName,
	    	'departmenId':departmenId,
	    	'dateStart':dateStart,
	    	'dateEnd':dateEnd
	    },
	    idField:'tmi_id',
		fitColumns: true,
	    scrollbarSize: 0,
//	    rownumbers:true,
	    toolbar:'#div_datagrid_toolbar',
//	    singleSelect:true,
	    fit:true,
	    striped : true,
	    fitColumns : true,
	    pagination : true,
	    sortName : 'tmi_add_date',
	    sortOrder: 'desc',
	    remoteSort: true,
	    rowStyler:function(index,row){
			if((index % 2) != 0) {
				return 'background-color:#FAFAD2;';
			} 
		},
		pageSize : 15,
		pageList : [5,10,15],
		onLoadSuccess:function(data){
			$('.btn-del').linkbutton({
		    	   text:'删除',
		    	   plain:true,
	    	   });  
	       $('.btn-edit').linkbutton({
	    	   text:'编辑',
	    	   plain:true,
	    		   }); 
		}
	});
}

// 请求数据
var loaderStore = function(param,success,error){
	
	$.ajax({
		url: ctx+'/TbMajorInfo/list',
		dataType: 'json',
		type:"get",
		data:param,
		success: function(response){
				if(response.code==100){
					// 数据查询成功
					success(response.data);
				}else{
					// 数据查询失败
					$.messager.alert("提示",response.message,"error");
					$("#table_TbMajorInfo_list").datagrid("loaded");
					return ;
				}
			},
	        error: function(){
	        	// 请求错误
	        	$.messager.alert("提示","请求失败","error");
	        	$("#table_TbMajorInfo_list").datagrid("loaded");
				return ;
			}
		});
}

function delMajorInfo(MajorInfoName,TbMajorInfoId){
	$("#table_TbMajorInfo_list").datagrid("loading");
	$.messager.confirm("删除","确定要删除名称为【"+MajorInfoName+"】的专业吗？",function(boolean){
		if(boolean){
			// 确认删除
			$.ajax({
				url:ctx+"/TbMajorInfo/del",
				type:"post",
				dataType:'json',
				data:{
					TbMajorInfoId:TbMajorInfoId,
				},
				success:function(response){
					if(response.code==100){
						$.messager.alert("提示",response.message,"info",function(){
							$('#table_TbMajorInfo_list').datagrid('reload');
						});
					}else{
						$.messager.alert("提示",response.message,"error");
						$('#table_TbMajorInfo_list').datagrid('reload');
					}
				},
				error:function(){
					$.messager.alert("提示","请求错误,请检查参数与路径","error");
					$('#table_TbMajorInfo_list').datagrid('reload');
				}
			});
		}
	});
}


// 时间戳格式化
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
};







//请求所有系别数据
function fun_Loader_department_list(param,success,error){
	$.ajax({
		url: ctx+'/TbMajorInfo/ajax_get_Department_list',
		dataType: 'json',
		type:'get',
		data:param,
		success: function(response){
			if(response.code==100){
				var items = $.map(response.data,function(item){
					return {
						tidiId:item.tidiId,
						tidiName:item.tidiName
					}
				});
				success(items);
			}else{
				$.messager.alert("提示",response.message,"error");
				return ;
			}
		},
     error: function(){
     	$.messager.alert("提示","请求错误,请检查路径或参数","error");
     }
	});
}


function addMajorTabs(){
	parent.addTabFun({
		src:ctx+'/TbMajorInfo/index/add',
		title:'新增专业'
	});
}

var centerTabs;

function addTabFun(opts) {
	var options = $.extend({
		title : '',
		content : '<iframe src="' + opts.src + '" frameborder="0" style="border:0;width:100%;height:99.2%;"></iframe>',
		closable : true,
		iconCls : ''
	}, opts);
	if (centerTabs.tabs('exists', options.title)) {
		centerTabs.tabs('close', options.title);
	}
	centerTabs.tabs('add', options);
};

function closeTab(title){
	centerTabs.tabs('close', title);
}




function init_UI(){
	$("#textbox_major_name").textbox({
		prompt:'请输入专业名称',
		validType:['Special','length[0,20]']
	});
	
	$("#combobox_departmen_id").combobox({
		loader:fun_Loader_department_list,
		valueField:'tidiId',
		textField:'tidiName',
		method:'get',
		panelHeight:'auto',
		panelHeight:'auto',
		prompt:'请选择所属系别',
		validType:['Special','length[0,10]']
	});
	
	
	$("#date_start").datebox({
		prompt:'开始时间',
		validType:['Special','length[0,20]'],
	});
	
	$("#date_end").datebox({
		prompt:'结束时间',
		validType:['Special','length[0,20]']
	});
	
	$("#button_search").linkbutton().click(function(){
			loadData_validate();
		});
	
	$("#button_add").linkbutton().click(function(){
		addMajorTabs();
	});
			
	
}






