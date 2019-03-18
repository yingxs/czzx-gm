function onSubmit(){
	//判断是否被选为默认地址
	var ifDefault = '0'//是否默认   0:否 1:是
	var ifdefault = document.getElementById("ifDefault");
	if(ifdefault.checked){
		ifDefault = '1';
	}
	var pid = $('#tsAgentProvinceId').combobox('getValue');
	var cid = $('#tsAgentCityId').combobox('getValue');
	var oid = $('#tsAgentCountyId').combobox('getValue');
	var tmPhone = $('#tmPhone').val();
	var tmAddress = $('#tmAddress').val();
	var tmName = $('#tmName').val();
	var addressName = $('#addressName').val();
	if(trim(tmPhone).length>0){
		if(!checkedMobile(tmPhone)){
			$("#labmobile").html("联系号码格式不对");
			isval = false;
		}else{
			$("#labmobile").html("*");
		}
	}
	if(trim(tmAddress).length<1){
		$("#labaddress").html("请输入详细地址");
//		isval = false;
	}else{
		$("#labaddress").html("*");
	}
	var tscIds_wuliao = $('#tscIds_wuliao').val();//物料购物车id数组
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/modify_sales_address',
		cache : true,
		async : false,
		data : {ifDefault:ifDefault,pid:pid,cid:cid,oid:oid,tmPhone:tmPhone,tmAddress:tmAddress,addressName:addressName,tmName:tmName},
		success:function(data){
			if(data.code == 100){
				var addrId = data.addrId;
				parent.addTabFun({
					src : ctx + "/product_spec/index/order_add?addrId="+addrId+"&tscIds_wuliao="+tscIds_wuliao,
					title : "立即下单"
				});	
			}else{
				var msg = data.message;
				$.messager.alert('提示',msg,'error');
				return;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		},
	})
}