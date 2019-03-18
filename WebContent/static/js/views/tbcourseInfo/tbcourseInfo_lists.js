// $(function(){
//
//     //1、学院
//     $("#tiiName").combobox({
//         url: ctx + '/tbcourseInfo/Ajax_Get_TbcourseInfo_TiiName',
//         method: 'POST',
//         valueField: 'tiiId',
//         textField: 'tiiName',
//         mode:'remote',
//         onChange: function (n,o) {
//             //2、系名称
//             $("#tidiNames").combobox({
//                 url:ctx+'/tbcourseInfo/Ajax_Get_tb_institute_department_Info_tidi_Name?tbcourseInfoId='+ n,
//                 method:'POST',
//                 valueField:'tidi_id',
//                 textField:'tidi_name',
//                 mode:'remote',
//                 onChange: function (n,o) {
//                     //3、专业
//                     $("#tmiName").combobox({
//                         url:ctx+'/tbcourseInfo/Ajax_Get_Tb_Major_Info_tidi_Name?tbcourseInfoId='+ n,
//                         method:'POST',
//                         valueField:'tmi_name',
//                         textField:'tmi_name',
//                         mode:'remote',
//                     });
//                 }
//             });
//         }
//     });
//
//     insertDetai();
//     delName(1);
//     function addzhaunye(){
//         alert("aaa");
//         $("#testId").show();
//         $("#testTb").show();
//         $("#testId").dialog({title:'专业'}).dialog('open');
//
//         findDetail(strName);   //初始化加載
//     };
//
//
// });
//
// function findDetail(strName){
//     var tiiName = $("#tiiName").textbox("getValue");
//     var tidiNames = $("#tidiNames").textbox("getValue");
//     var tmiName = $("#tmiName").textbox("getValue");
//
//     //console.info("tiiName"+tiiName+"tidiNames"+tidiNames+"tmiName"+tmiName);
//     if (strName==null && strName==""){
//         strName="1";
//     }
//     $('#tbcourseInfo_Datagrids').datagrid({
//
//         url:ctx+'/tbcourseInfo/Ajax_Get_TbinstituteDepartmentInfo_Lists?strName='+strName,
//         idfield:'Ts_id',
//         columns:[[
//             {field:'box',checkbox:true,width:'50px',align:'center',
//                 formatter:function(val,row,index){
//                     return '<input class="tab_fxk" type="checkbox" name="testIds" value="'+val+'" />';
//                 }
//             },
//             //取值      一行             航标
//             {field:'tfType',title:'序号',width:60,align:'center',formatter: function (value,rowData,rowIndex) {
//                     var index = rowIndex;
//                     return index+1;
//                 }},
//             {field:'tii_name',title:'所属学院',width:130,align:'center'},
//             {field:'tidi_name',title:'所属系别',width:200,align:'center'},
//             {field:'tmi_name',title:'专业名称',width:150,align:'center'},
//             {field:'tmi_code',title:'专业代码',width:100,align:'center'},
//         ]],
//         queryParams:{
//             'tciName':tiiName, //学院
//             'tsiName':tidiNames,   //系
//             'tmiName':tmiName,  //专业
//
//
//         },
//         rownumbers:false,
//         singleSelect:false,
//         fit:true,
//         striped : true,
//         fitColumns : false,
//         pagination : true,
//         fitColumns : true,
//         striped : true, // 隔行变色特性S
//         loadMsg : '数据正在加载,请耐心的等待...',
//        // idField : 'tfType',
//         /*sortName : 'AddDate',
//         sortOrder: 'desc',*/
//         remoteSort: true,
//         rowStyler:function(index,row){
//             if((index % 2) != 0) {
//                 return 'background-color:#FAFAD2;';
//             }
//         },
//         pageSize : 15,
//         pageList : [10, 15, 20, 30, 40, 50 ],
//         onLoadSuccess:function(data){
//             $('#tb').append('&nbsp; <a class="easyui-linkbutton addcls"  plain="true" onclick="addRow()">新增</a>');
//             $('.searchcls').linkbutton({text:'&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;',plain:true,iconCls:''});
//             $('.delcls').linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
//             $('.infocls').linkbutton({text:'详情',plain:true,iconCls:'icon-search'});
//             $('.exportcls').linkbutton({text:'&nbsp;&nbsp;导&nbsp;&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;',plain:true,iconCls:''});
//             $('.addcls').linkbutton({text:'&nbsp;&nbsp;新&nbsp;&nbsp;&nbsp;&nbsp;增&nbsp;&nbsp;',plain:true,iconCls:''});
//         }
//     });
// }
//
// /*新增*/
// function insertDetai(){
//
//     var checkedItems = $('#tbcourseInfo_Datagrids').datagrid('getChecked');
//     for ( var i = 0;i<checkedItems.length;i++){
//         var name = checkedItems[i].tmi_name;
//         var ids = checkedItems[i].tmi_id;
//
//         var html = '';
//         html+='<lable id="'+ids+"a"+'">'+name+'</lable>&nbsp;&nbsp;<label><input type="text" id="'+ids+"b"+'" name="testName" value="'+ids+'" hidden="true"><a id="'+ids+'" onclick="delName(this)">删除&nbsp;&nbsp;</a></label>'
//         $("#testDiv").append(html)
//
//     }
// }
// function delName(ids){
//     $("#"+ids.id+"a").remove();
//     $("#"+ids.id+"b").remove();
//     $("#"+ids.id).remove();
// }

