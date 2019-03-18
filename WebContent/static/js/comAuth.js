function com_auth(url,cla){
	$.ajax({
		url:ctx + '/perms/com_auth',
		type:'post',
		data:{url:url},
		dataType:'text',
		cache:false,
		anysc:false,
		success:function(data){
			if(data == "0"){
				$('.' + cla).hide();
			}else{
				$('.' + cla).show();
			}
		}
	});
}