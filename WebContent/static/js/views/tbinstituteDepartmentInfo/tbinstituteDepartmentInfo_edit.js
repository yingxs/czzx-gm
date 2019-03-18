        //点击
        var clickImg = function(obj) {
            $(obj).parent().find('.upload_input').click();
        }
        //删除
        var deleteImg = function(obj) {
            $("#tpLogo").val(null);
            $('.item .addImg').css('display', 'block');
            $("img[id='mImage']").css('display', 'none');
            $('.item .delete').css('display', 'none');
            $("img[id='mImage']").attr('src', '');
            $("#"+obj).replaceWith('<input type="file" id="'+obj+'" class="upload_input" name="myfiles" style="display:none"  onchange="ajaxFileUpload('+1+')"  />');
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
	 * 页面初始化
	 */
	$(function () {
		
		//所属学院名称
		 $("#tiiName").combobox({
		        url: ctx + '/tbinstituteDepartmentInfo/Ajax_Get_TbinstituteInfo_Name',
		        method: 'POST',
		        valueField: 'tiiId',
		        textField: 'tiiName'
		    });
		
		 
	});
	
	
	//修改
	function edidUser(){
		if($("#add-form").form('validate') == false){
			return false;
		}
		$.ajax({
			type:"POST",
			dataType:'json',
			url:ctx+"/tbinstituteDepartmentInfo/edit",
			data:$("#add-form").serialize(),
			success:function(data){
				if(data.Code == '100')
				{
					$.messager.confirm('修改成功?',data.Message, function(r){
						if (r){
							//$(location).attr('href', '/czzx-gm/tbinstituteDepartmentInfo/index/list');
							  window.location.href=ctx+"/tbinstituteDepartmentInfo/index/list";  
							
							
						}else{
							$.messager.confirm('修改失败?',"Error");
						}
					});
					
				}
				else if(data=='101'){
					$.messager.alert('提示','参数提交不完整!','warning');
				}
				else if(data=='102'){
					$.messager.alert('提示','编码已经存在,请核对数据!','warning');
				}
				else if(data=='103'){
					$.messager.alert('提示','编码必须是数字,请核对数据!','warning');
				}
				else if(data=='104'){
					$.messager.alert('提示','编码的长度必须是2位,请核对数据!','warning');
				}
				else if(data=='112'){
					$.messager.alert('提示','编码名称已存在,请核对数据!','warning');
				}
				else if(data=='110'){
					$.messager.alert('提示','本次操作出现不明错误，请联系技术人员解决!','warning');
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

