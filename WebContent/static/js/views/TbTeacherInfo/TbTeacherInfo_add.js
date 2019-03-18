//点击
        var clickImg = function(obj) {
            $(obj).parent().find('.upload_input').click();
        }
       
        /**
         * 图片上传
         */
        function ajaxFileUpload(obj) {
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url : ctx + '/publicUploadTwo/fileUpload',
                secureuri : false, //是否启用安全提交,默认为false
                fileElementId : 'photo', //文件选择框的id属性
                dataType : 'text', //服务器返回的格式,可以是json或xml等
                data : {
                    "folder" : "member"
                }, //上传文件传到后台参数
                success : function(data, status) { //服务器响应成功时的处理函数
                    data = data.split(">")[1];
                    data = data.replace("<pre>", ''); //ajaxFileUpload会对服务器响应回来的text内容加上<pre>text</pre>前后缀
                    data = data.replace("</PRE>", '');
                    data = data.replace("<pre>", '');
                    data = data.replace("</pre", ''); //本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
                    if (data.substring(0, 1) == 0) { //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
                        $("img[id='mImage']").attr("src",data.substring(2));
                        $("#tpLogo").val(data.substring(2));
                        $('.item .addImg').css('display', 'none');
                        $('.item .delete').css('display', 'block');
                        $("img[id='mImage']").css('display', 'block');
                       // $("#"+obj.id).replaceWith('<input type="file" id="'+obj.id+'" class="upload_input" name="myfiles" style="display:none"  onchange="ajaxFileUpload('+1+')"  />');
                       // $("#uploadFile"+num).replaceWith('<input type="file" id="uploadFile'+num+'" name="uploadFile" style="display:none"  onchange="fileChange('+num+')"  />');
                    } else {
                        $('#result').html(data.substring(2));
                    }
                },
                error : function(data, status, e) { //服务器响应失败时的处理函数
                    $('#result').html(data);
                }
            });
        }







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
	  
	
		$("#ttiBirthday").textbox({disabled:true});

});






//拿到手机号
function chkvalue(){
	var ttiPhone = $('#ttiPhone').val();
	
	$('#ttiLoginUser').textbox('setValue', ttiPhone);
}

//身份证后六位
function ttiCardNums(){
	var ttiCardNum = $('#ttiCardNum').val();
	var num=ttiCardNum.substring(6,14);
	var pass=ttiCardNum.substring(8,14);
	$("#ttiBirthday").textbox('setValue',num);
	$("#ttLoginPass").textbox('setValue',pass);
	

	
}




/**
 * 添加
 * @returns {boolean}
 */
function addUsers(){
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