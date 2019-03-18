function expoerAction(btn){
    var tidiName = $("#tidiName").val();
  /*  var tfbName = $("#tfbName").combobox("getText");
    var tfPhone = $("#tfPhone").val();
    var tfStatus = $("#tfStatus").combobox("getValue");
    var tfType = $("#tfType").combobox("getValue");
    var AddDate = $("#AddDate").datebox("getValue");
    var AddDateEnd = $("#AddDateEnd").datebox("getValue");*/
    var obj={
       /* 'tfId':tfId,
        'tfbName':tfbName,
        'tfPhone':tfPhone,
        'AddDate':AddDate,
        'AddDateEnd':AddDateEnd,*/
    }
    exportExcel(obj,"tbFeedbackController",'反馈管理表格',"tbFeedbackList","tfId");
}
$(function(){
	//所属学院名称
	 $("#tiiName").combobox({
	        url: ctx + '/tbinstituteDepartmentInfo/Ajax_Get_TbinstituteInfo_Name',
	        method: 'POST',
	        valueField: 'tiiId',
	        textField: 'tiiName'
	    });
	
    findDetail();   //初始化加載

});

function findDetail(){
	  var tidiName = $("#tidiName").textbox("getValue");
	  var tiiName = $("#tiiName").textbox("getValue");
	   var AddDate = $("#AddDate").datebox("getValue");
       var AddDateEnd = $("#AddDateEnd").datebox("getValue");
	  


    $('#TbinstituteDepartmentInfoDatagrid').datagrid({
        url:ctx+'/tbinstituteDepartmentInfo/Ajax_Get_TbinstituteDepartmentInfo_List',
        columns:[[
            {field:'box',checkbox:true,width:'50px',align:'center',
                formatter:function(val,row,index){
                    return '<input type="checkbox" />';
                }
            },
                                                                                      //取值      一行             航标 
             {field:'tfType',title:'序号',width:60,align:'center',formatter: function (value,rowData,rowIndex) {
            
        	return rowIndex+1;
            }},
            {field:'tidi_name',title:'系别名称',width:130,align:'center'},
            {field:'tii_name',title:'所属学院',width:200,align:'center'},
            {field:'tidi_icon',title:'系别图标',width:100,align:'center',formatter : function(value, rec) {
				if (value != null || value == '') {
					var btn = '';
					btn += '<img height="30" width="60" src="' + value + '"/>';
					return btn;
				} else {
					return '<img height="30" width="60" src="'+ ctx+ '/static/images/yhm.jpg">';
				}

			}},              
            {field:'tidi_order',title:'排序',width:60,align:'center'},
            {field:'dates',title:'创建时间',width:150,align:'center'},
            {field: 'opt', title: '操作', width: 150, align: 'center',formatter: function (value,rowData,rowIndex) {
				var btnHtml='';
					//btnHtml += '<a style="color: #2d88bf" onclick="info(this,\''+rowData.tidi_id+'\')"  href="javascript:void(0)">[详情]</a>&nbsp;&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="dalete(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">删除|</a>&nbsp;&nbsp;';
					btnHtml += '<a style="color: #2d88bf" onclick="update(this,\''+rowData.tidi_id+'\')" href="javascript:void(0)">|修改</a>&nbsp;&nbsp;';
					return btnHtml;
				}

            }

        ]],
        queryParams:{
            'tidiName':tidiName,
            'tiiName':tiiName,
            'AddDate':AddDate,
  			'AddDateEnd':AddDateEnd,
           
        },
        rownumbers:false,
        singleSelect:false,
        fit:true,
        striped : true,
        fitColumns : false,
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
            $('.searchcls').linkbutton({text:'&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;',plain:true,iconCls:''});
            $('.addcls').linkbutton({text:'&nbsp;&nbsp;新&nbsp;&nbsp;&nbsp;&nbsp;增&nbsp;&nbsp;',plain:true,iconCls:''});  
            $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
            //$('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
           // $('.exportcls').linkbutton({text:'&nbsp;&nbsp;导&nbsp;&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;',plain:true,iconCls:''});
           
        }


    });
}

/*新增*/
function addRow(){
    parent.addTabFun({
        src : ctx + "/tbinstituteDepartmentInfo/index/add",
        title : "添加"
    });
}



/*修改*/
function update(btn,id){
    parent.addTabFun({
        src : ctx + "/tbinstituteDepartmentInfo/index/edit?tbinstituteDepartmentInfoId="+id,
        title : "修改"
    });
}




function approve(btn,id){
    if ($("member_resetPwd_form").form('validate') == false) {
        return false;
    }
    $('#member_resetPwd_form').form('clear');
    $('#member_resetPwd').window('open');
    $("#member_resetPwd_form").data('tfId',id);

}
function resetPwd(status){

    if ($("#member_resetPwd_form").form('validate') == false) {
        return false;
    }
    var tfId =  $("#member_resetPwd_form").data('tfId');
    var tfDealContent = $("#tefrMemo").val();
    var menuId=getQueryString('menuId');
    $.ajax({
        type:"POST",
        dataType:'json',
        url:ctx+ "/tbFeedback/approve?status="+status+"&tfId="+tfId,
        data:{"tfDealContent":tfDealContent},
        success:function(data,rowData,rowIndex){
            if(data.code == '100'){
                parent.addTabFun({
                    src : ctx + "/tbFeedback/index/list?menuId="+menuId,
                    title : "反馈管理"
                });
                parent.closeTab("反馈管理");
            }
            else{
                $.messager.alert('提示',data.message,'warning');
            }
        }
    });
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
