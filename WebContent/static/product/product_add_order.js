var all_amount = 0.00;//购物车中总金额
var _tsMaterialRatio = 0.0000;
//购物车id数组用a拼接
var tscIds = '';
$(function(){
	loadDate();
	getmaterial_ratio();
});
function loadDate(){
	$('#tbShoppingCar-order-list').datagrid({
	    url:ctx+'/shoppingCar/list',
	    columns:[[
  	        {field:'tpPic',title:'图片',width:120,align:'center',formatter:function(value,rec){
	        	return '<img src="'+value+'" width="60" height="60">';
	        }},
	        {field:'tpName',title:'商品名称',width:200,align:'center'},
	        {field:'tpNumber',title:'货号',width:100,align:'center'},    
	        {field:'specValues',title:'规格',width:350,align:'center'},
	        {field:'tscCount',title:'数量',width:100,align:'center',formatter:function(value,rec){
	        	return '<input type="text" style="width:80px;text-align:center;" id="'+rec.tscId+'" onblur="change('+rec.tscId+')" value="'+value+'">';
	        }},
	        {field:'tscPrice',title:'订货单价(元)',width:100,align:'center',formatter:function(value,rec){
	        	return value.toFixed(2);
	        }}, 
	        {field:'Amount',title:'金额小计',width:100,align:'center',formatter:function(value,rec){
	        	return value.toFixed(2);
	        }},
//	        {field:'pay',title:'操作',width:150,align:'center',formatter:function(value,rec){
//	        	var btn = '';  
//	        
//	        	btn += '<a class="delcls" onclick="onDeleteSubmit(\''+rec.tscId + '\')" href="javascript:void(0)">删除</a>';
//	        	
//        		return btn;
//	        }},
	    ]],  
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb',
	    fit:false,
	    fitColumns : true,
	    pagination : false,
	});
}

//计算购物车商品总金额
function sum_amount(){
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/shoppingCar/list',
		success:function(data){
			$.each(data.rows,function(index, item) {
				if(tscIds == ''){
					tscIds = item.tscId;
				}else{
					tscIds = tscIds + ',' + item.tscId;
				}
				if(all_amount==0.00){
	        		all_amount = item.tscPrice*item.tscCount;
	        	}else{
	        		all_amount = all_amount+item.tscPrice*item.tscCount;
	        	}
			});
			var html = '';
			var wlHtml = '';
			var tjHtml = '';//购物车中商品提交订单
			html += '合计：￥<span id="amount_sum_iput" style="color:red;font-size: 12pt;font-weight:bold;margin-right:10px;"> '+all_amount.toFixed(2)+' 元</span>';
//			html += '<input style="width:85px;font-size: 12pt;" class="btn btn-primary btn-sm" type="button" value="立即下单" onclick="add_order('+tscIds+')"></input>';
			var tsMaterialRatio = _tsMaterialRatio;
			var wuliao_amount = all_amount*tsMaterialRatio;//获取经销商物料比例，计算物料金额范围
			wlHtml += '您还可以选购￥<span style="color:red;font-size: 12pt;">'+wuliao_amount.toFixed(2)+'</span>元的宣传物料支持&nbsp;';
			wlHtml += '<input style="width:85px;font-size: 12pt;margin-right:10px;" class="btn btn-primary btn-sm" type="button" value="立即选购" onclick="choose_wuliao('+wuliao_amount.toFixed(2)+')"></input>';
			wlHtml += '<input style="width:85px;font-size: 12pt;" class="btn btn-primary btn-sm" type="button" value="我的选购" onclick="my_wuliao()"></input>';
			tjHtml += '&nbsp;<input style="width:70px;" class="btn btn-primary btn-sm" type="button" value="提交" onclick="submit_car_order()"></input>';
			$("#amount_sum").html(html);
			$("#wuliao").html(wlHtml);
			$("#car_submit").html(tjHtml);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		},
	})
}
//获取经销商物料比例
function getmaterial_ratio(){
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/system',
		success:function(data){
			if(data.code==100){
//				alert("data.system.tsMaterialRatio:"+data.system.tsMaterialRatio);
				_tsMaterialRatio = data.system.tsMaterialRatio;//经销商物料比例
				sum_amount();
				return ;
			}else{
				var msg = data.message;
				$.messager.alert('提示',msg,'warning');
				return ;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		},
	})
}
//立即选购
function choose_wuliao(wuliao_amount){
	parent.addTabFun({
		src : ctx + "/product_spec/index/choose_wuliao?wuliao_amount="+wuliao_amount,
		title : "物料选购"
	});	
}
//我的选购
function my_wuliao(){
	parent.addTabFun({
		src : ctx + "/product_spec/index/shoppingCarList_wuliao",
		title : "我的选购"
	});	
}
//购物车商品提交订单
function submit_car_order(){
	var _tscIds = tscIds;//购物车数组id
	var addressId = $('#addrId').val();//收货地址
	var desp = $('#desp').val();//备注信息
	var tscIds_wuliao = $('#tscIds_wuliao').val();//物料购物车id数组
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/submit_car_order',
		cache : true,
		async : false,
		data : {tscIds:_tscIds,addressId:addressId,desp:desp,tscIds_wuliao:tscIds_wuliao},
		success:function(data){
			if(data.code==100){
				var msg = data.message;
				$.messager.alert('提示',msg,'warning');
				return ;
			}else{
				var msg = data.message;
				$.messager.alert('提示',msg,'warning');
				return ;
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		},
	})
}
//修改购物车数量
function change(tscId){
	var pCount = $('#'+tscId+'').val();
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/changeCount',
		cache : true,
		async : false,
		data : {tscId:tscId,count:pCount},
		success:function(data){
			if(data.code == 100){
				location.reload();
				return;
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
//修改地址
function modify_addr(){
	var tscIds_wuliao = $('#tscIds_wuliao').val();//物料购物车id数组
//	alert("tscIds_wuliao:"+tscIds_wuliao);
	parent.addTabFun({
		src : ctx + "/product_spec/index/modify_addr?tscIds_wuliao="+tscIds_wuliao,
		title : "修改收货信息"
	});	
}