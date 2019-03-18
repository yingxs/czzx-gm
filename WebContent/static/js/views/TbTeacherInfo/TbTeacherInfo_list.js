
   $(function(){
	
      findDetail();   //初始化加載     
      //性别下拉框
      $('#ttiSex').combobox({   
 	     required:true, 
 	     editable:false,
 	     valueField:'value',    
 	     textField:'text',
 	    	 data:[
 	    		 {'value':1, 'text':'男' },
 	    		 {'value':0, 'text':'女' }
 	    			 ],
 	 
 	    
 	});
      
      

         });

   
//加载表格$("#userName").textbox('setValue',"xiaoming");
function findDetail(){
	var ttiName=$("#ttiName").textbox("getValue");
	var ttiSex = $('#ttiSex').combobox('getValue');
	
	
	var ttiPhone=$("#ttiPhone").textbox("getValue");
	var AddDate= $("#AddDate").datebox("getValue"); 
	var AddDateEnd=$("#AddDateEnd").datebox("getValue");

    $('#TbTeacherInfoDatagrid').datagrid({
        url:ctx+'/TbTeacherInfo/Ajax_Get_TbTeacherInfo_List',
        columns:[[
            {field:'box',checkbox:true,width:'50px',align:'center',
                formatter:function(val,row,index){
                    return '<input type="checkbox" />';
                }
            },
                                                                                      //取值      一行             航标 
             {field:'tfType',title:'序号',width:"100px",align:'center',formatter: function (value,rowData,rowIndex) {
            
        	return rowIndex+1;
            }},
            {field:'tti_number',title:'人员编号',width:"150px",sortable:true,align:'center'},
            {field:'tti_name',title:'姓名',width:"200px",align:'center'},
            {field:'tti_sex',title:'性别',width:"80px",align:'center',formatter : function(value, rec) {
				if (value == '1') {
				return '男';
		
				} else if(value == '0') {
					return '女';	
				}else{
					return '未知';
				}

			}},              
            {field:'tti_phone',title:'手机号码',width:"150px",align:'center'},
            {field:'tti_score',title:'成长积分',width:"120px",sortable:true,align:'center'},
            {field:'dates',title:'创建时间',width:"200px",sortable:true,align:'center'},
            {field: 'opt', title: '操作', width: 460, align: 'center',formatter: function (value,rowData,rowIndex) {
				var btnHtml='';
				btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">编&nbsp;辑</a>&nbsp;&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="dalete(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|&nbsp;删&nbsp;除</a>&nbsp;&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|&nbsp;详&nbsp;情</a>&nbsp;&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|&nbsp;重&nbsp;置&nbsp;密&nbsp;码</a>&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|&nbsp;更&nbsp;换&nbsp;账&nbsp;号</a>&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|&nbsp;成&nbsp;长&nbsp;记&nbsp;录</a>&nbsp;';
					return btnHtml;
				}

            }

        ]],
        queryParams:{
            'ttiName':ttiName,   // 教师名称
            'ttiSex':ttiSex,     // 性别
        	'ttiPhone':ttiPhone, // 手机号码
            'AddDate':AddDate,   // 开始时间
  			'AddDateEnd':AddDateEnd, //结束时间
           
        },
        rownumbers:false,
        singleSelect:false,
        fit:true,
        striped : true,
        fitColumns : true,   //铺满全屏
        pagination : true,
        sortName : 'dates',
        sortOrder: 'desc',
        remoteSort: true,
        rowStyler:function(index,row){
            if((index % 2) != 0) {
                return 'background-color:#FAFAD2;';
            }
        },
        pageSize : 15,
        pageList : [10, 15, 20, 30, 40, 50 ],
        onLoadSuccess:function(data){
          
        }


    });
}

/*新增*/
function addRow(){
    parent.addTabFun({
        src : ctx + "/TbTeacherInfo/index/add",
        title : "添加"
    });
}



/*修改*/
function update(btn,id){
    parent.addTabFun({
        src : ctx + "/TbTeacherInfo/index/edit",
        title : "编辑"
    });
}




function fun(){
	var a=$("#put").val();
	
}




function approve(btn,id){
    if ($("member_resetPwd_form").form('validate') == false) {
        return false;
    }
    $('#member_resetPwd_form').form('clear');
    $('#member_resetPwd').window('open');
    $("#member_resetPwd_form").data('tfId',id);

}




/*删除*/
function dalete(btn,id){
    var content = "确定要删除该记录吗？";
    $.messager.confirm("提示",content,function(r){
        if(r){
            $.ajax({
                type:"POST",
                dateType:'JOSN',
                url:ctx+'/tbinstituteDepartmentInfo/del',
                data:{tbinstituteDepartmentInfoId:id},
                success:function(data){
                	$.messager.alert('提示',data.Message);
                    if(data.Code == 100){
                    	window.location.href=ctx+"/tbinstituteDepartmentInfo/index/list";  
                    }
                },
                error:function(){
                	$.messager.alert('提示','连接中断，请稍后再试','info');
                }
            });
        }
    });

}
