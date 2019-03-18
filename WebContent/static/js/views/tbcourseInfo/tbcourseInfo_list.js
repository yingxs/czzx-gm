$(function(){
    //所属课程
    $("#tidiName").combobox({
        url: ctx + '/tbcourseInfo/Ajax_Get_TbcourseInfo_TciNames',
        method: 'POST',
        valueField: 'tciName',
        textField: 'tciName',
    });

    //科目
    $("#tiiName").combobox({
        url: ctx + '/tbcourseInfo/Ajax_Get_TbsubjectsInfo_tsiId',
        method: 'POST',
        valueField: 'tsiName',
        textField: 'tsiName',
    });
    findDetail();   //初始化加載

});

function findDetail(){
    var tidiName = $("#tidiName").textbox("getValue");
    var tiiName = $("#tiiName").textbox("getValue");
    var AddDate = $("#AddDate").datebox("getValue");
    var AddDateEnd = $("#AddDateEnd").datebox("getValue");

    $('#tbcourseInfo_Datagrid').datagrid({
        url:ctx+'/tbcourseInfo/Ajax_Get_TbinstituteDepartmentInfo_List',
        columns:[[
            {field:'box',checkbox:true,width:'50px',align:'center',
                formatter:function(val,row,index){
                    return '<input type="checkbox" />';
                }
            },
                                                                             //取值      一行             航标
            {field:'tfType',title:'序号',width:60,align:'center',formatter: function (value,rowData,rowIndex) {
                    var index = rowIndex;
                    return index+1;
                }},
                {field:'tsi_name',title:'所属科目',width:100,align:'center'},
            {field:'tci_name',title:'课程名称',width:130,align:'center'},
            {field:'tci_code',title:'课程代码',width:200,align:'center'},
            {field:'dates',title:'创建时间',width:150,align:'center'},
            {field: 'opt', title: '操作', width: 150, align: 'center',formatter: function (value,rowData,rowIndex) {
                    var btnHtml='';
                    //btnHtml += '<a style="color: #2d88bf" onclick="info(this,\''+rowData.tidi_id+'\')"  href="javascript:void(0)">[详情]</a>&nbsp;&nbsp;';
                    btnHtml += '<a style="color: #2d88bf" onclick="dalete(this,\''+rowData.tci_id+'\')" href="javascript:void(0)">删除|</a>&nbsp;&nbsp;';
                    btnHtml += '<a style="color: #bf422b" onclick="update(this,\''+rowData.tci_id+'\')" href="javascript:void(0)">|修改</a>&nbsp;&nbsp;';
                    return btnHtml;
                }

            }

        ]],
        queryParams:{
            'tciName':tidiName, //课程
            'tsiName':tiiName,   //科目
            'AddDate':AddDate,
            'AddDateEnd':AddDateEnd,

        },
        rownumbers:false,
        singleSelect:false,
        fit:true,
        striped : true,
        fitColumns : false,
        pagination : true,
        fitColumns : true,
        striped : true, // 隔行变色特性S
        loadMsg : '数据正在加载,请耐心的等待...',
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
//            $('#tb').append('&nbsp; <a class="easyui-linkbutton addcls"  plain="true" onclick="addRow()">新增</a>');
            $('.searchcls').linkbutton({text:'&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;',plain:true,iconCls:''});
            $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
            $('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
            $('.exportcls').linkbutton({text:'&nbsp;&nbsp;导&nbsp;&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;',plain:true,iconCls:''});
            $('.addcls').linkbutton({text:'&nbsp;&nbsp;新&nbsp;&nbsp;&nbsp;&nbsp;增&nbsp;&nbsp;',plain:true,iconCls:''});
        }


    });
}

/*新增*/
function addRow(){
    parent.addTabFun({
        src : ctx + "/tbcourseInfo/index/add",
        title : "添加"
    });
}


/*修改*/
function update(btn,id){
    parent.addTabFun({
        src : ctx + "/tbcourseInfo/index/edit?tbcourseInfoId="+id,
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


/*删除*/
function dalete(btn,id){
    var content = "您确定要删除课程吗？？";
    $.messager.confirm("提示",content,function(r){
        if(r){
            $.ajax({
                type:"POST",
                dateType:'JOSN',
                url:ctx+'/tbcourseInfo/ajax_get_tbcourseInfo_del',
                data:{tbcourseInfoId:id},
                success:function(data){
                    $.messager.alert('提示',data.message);
                    if(data.code == 100){
                       // $('#tbcourseInfo_Datagrid').datagrid("load");//加载客户列表
                    	window.location.href=ctx+"/tbcourseInfo/index/list";
                    }else if (data.code==101) {
                        $.messager.alert('提示',data.message);
                    }else if (data.code==102) {
                        $.messager.alert('提示',data.message);
                    }else if (data.code==103) {
                        $.messager.alert('提示',data.message);
                    }
                },
                error:function(){
                    $.messager.alert('提示','连接中断，请稍后再试','info');
                }
            });
        }
    });

}
