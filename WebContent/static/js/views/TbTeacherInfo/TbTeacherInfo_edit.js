/**
 *
 * 页面初始化  -----课程管理修改
 * @return
 */
$(function () {
	
	//民族
	  $("#ttiNation").combobox({
	        url:ctx+'/TbTeacherInfo/Ajax_Get_ttiNation_FindAll',
	        method:'POST',
	        valueField:'twId',
	        textField:'twName',
	        mode:'remote',
	    });
	
	
	//政治面貌
	  $("#ttiPolitical").combobox({
	        url:ctx+'/TbTeacherInfo/Ajax_Get_tti_Political_FindAll',
	        method:'POST',
	        valueField:'twId',
	        textField:'twName',
	        mode:'remote',
	    });
    
	  
		//学历层次
	  $("#ttiInDegree").combobox({
	        url:ctx+'/TbTeacherInfo/Ajax_Get_tti_ttiInDegree_FindAll',
	        method:'POST',
	        valueField:'twId',
	        textField:'twName',
	        mode:'remote',
	    });
	  
	  
	//职务职称
	  $("#ttiPosition").combobox({
	        url:ctx+'/TbTeacherInfo/Ajax_Get_ttiPosition_FindAll',
	        method:'POST',
	        valueField:'twId',
	        textField:'twName',
	        mode:'remote',
	    });
	  
	  

		//教职工类别
	  $("#ttiPositionType").combobox({
	        url:ctx+'/TbTeacherInfo/Ajax_Get_ttiPositionType_FindAll',
	        method:'POST',
	        valueField:'twId',
	        textField:'twName',
	        mode:'remote',
	    });
	  
	  //现任工作
	  $("#ttiPost").combobox({
		  url:ctx+'/TbTeacherInfo/Ajax_Get_ttiPost_FindAll',
		  method:'POST',
		  valueField:'twId',
		  textField:'twName',
		  mode:'remote',
	  });
	  
	  
	

});









function run(){
	 $("#ttiCardNum").click(function(){
		 alert("1");
	 })

}



/**
 * 添加
 * @returns {boolean}
 */
function addUsers(){
	
	
	console.info("进来了---------------------------");
    if($("#add-form").form('validate') == false){
        return false;
    }
    $.ajax({
        type:"POST",
        dataType:'json',
        url:ctx+"/TbTeacherInfo/ajax_get_tbteacherinfo_add",
        data:$("#add-form").serialize(),
        success:function(data){
            var datas=data.code;
            if(datas == '100') {
                {
                    $.messager.confirm('添加成功?',data.message, function(r){
                        if (r){
                            alert("success");
                        	//window.location.href=ctx+"/tbcourseInfo/index/list";
                           // $(location).attr('href', '/czzx-gm/tbinstituteDepartmentInfo/index/list');
                        }else{
                            $.messager.confirm('添加失败?',"Error");
                        }
                    });

                }

            }
            else if(datas=='101'){
                $.messager.alert('提示',data.message,'warning');
            }
            else if(datas=='102'){
                $.messager.alert('提示','编码已经存在,请核对数据!','warning');
            }
            else if(datas=='103'){
                $.messager.alert('提示','编码必须是数字,请核对数据!','warning');
            }
            else if(datass=='104'){
                $.messager.alert('提示','编码的长度必须是2位,请核对数据!','warning');
            }
            else if(datas=='112'){
                $.messager.alert('提示','编码名称已存在,请核对数据!','warning');
            }
            else{
            }
        }
    });
}






function resetr(){
    $("#add-fatherType").combobox("setValue","");
    $("#twName").textbox("setValue","");
    $("#superCode").html("");
    $("#twCode").textbox("setValue","");
    $("#twMemo").textbox("setMemo","");
}