var all_amount = 0.00;//购物车中物料总金额
var tscIds_wuliao = '';//物料购物车id数组，用逗号拼接
$(function(){
	loadDate();
	sum_amount();
});
function loadDate(){
	$('#tbShoppingCar-list').datagrid({
	    url:ctx+'/product_spec/shoppingCar_wuliao/list',
	    columns:[[
  	        {field:'tpPic',title:'图片',width:120,align:'center',formatter:function(value,rec){
	        	return '<img src="'+value+'" width="60" height="30">';
	        }},
	        {field:'tpName',title:'物料名称',width:200,align:'center'},
	        {field:'tpNumber',title:'货号',width:100,align:'center'},    
	        {field:'specValues',title:'规格',width:180,align:'center'},
	        {field:'tscCount',title:'数量',width:100,align:'center',formatter:function(value,rec){
	        	return '<input type="text" style="width:80px;text-align:center;" id="'+rec.tscId+'" onblur="changeCount('+rec.tscId+','+value+')" value="'+value+'">';
	        }},
	        {field:'tscPrice',title:'订货单价(元)',width:100,align:'center',formatter:function(value,rec){
	        	return value.toFixed(2);
	        }}, 
	        {field:'Amount',title:'金额小计',width:100,align:'center',formatter:function(value,rec){
	        	return value.toFixed(2);
	        }},
	        {field:'pay',title:'操作',width:150,align:'center',formatter:function(value,rec){
	        	var btn = '';  
	        
	        	btn += '<a class="delcls" onclick="onDeleteSubmit(\''+rec.tscId + '\')" href="javascript:void(0)">删除</a>';
	        	
        		return btn;
	        }},
	    ]],  
	});
}

function onDeleteSubmit(tscId){
	$.messager.confirm("提示","删除记录后不可恢复，确定继续吗？",function(r){
		if(r){
			$.ajax({
		        type: 'POST',
		        url: ctx+"/shoppingCar/delete",
		        data:{tscId:tscId},
		        dataType: 'text',
		        success: function (msg) {
		        	if(msg == "100"){
//		    			$('#tbShoppingCar-list').datagrid('load');
		        		location.reload();
		        	}else if(msg=="102"){
						$.messager.alert('提示','购物车不存在');
					}else{
		        		$.messager.alert('提示','删除失败','error');
		        	}
		        }
			});
		}
	});
}
//计算购物车商品总金额
function sum_amount(){
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/shoppingCar_wuliao/list',
		success:function(data){
			$.each(data.rows,function(index, item) {
				if(tscIds_wuliao == ''){
					tscIds_wuliao = item.tscId;
				}else{
					tscIds_wuliao = tscIds_wuliao + ',' + item.tscId;
				}
				if(all_amount==0.00){
	        		all_amount = item.tscPrice*item.tscCount;
	        	}else{
	        		all_amount = all_amount+item.tscPrice*item.tscCount;
	        	}
			});
			var html = '';
			html += '合计：￥<span id="amount_sum_iput" style="color:red;font-size: 12pt;font-weight:bold;margin-right:10px;">'+all_amount.toFixed(2)+'元</span>';
			html += '<input style="width:85px;font-size: 12pt;" class="btn btn-primary btn-sm" type="button" value="确定" onclick="confirm_orderWuliao()"></input>';
			$("#amount_sum").html(html);
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			
		},
	})
}
//确认物料是否已超出可允许范围
function confirm_orderWuliao(){
//	alert("tscIds:"+tscIds_wuliao);
	
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/shoppingCar_wuliao_amount_confirm',
		cache : true,
		async : false,
		data : {all_amount_wuliao:all_amount},
		success:function(data){
			if(data.code == 100){
				parent.addTabFun({
					src : ctx + "/product_spec/index/order_add?tscIds_wuliao="+tscIds_wuliao,//确认购物车中物料后跳转立即下单界面
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
//修改物料数量
function changeCount(tscId,oldCount){
	var wlCount = $('#'+tscId+'').val();
//	alert("修改前物料数量:"+oldCount);
//	alert("修改物料数量:"+wlCount);
	//判断新的数量与原来的是否相同
	if(wlCount == oldCount){
		return;
	}
	$.ajax({
		type:'POST',
		dataType:'json',
		url:ctx+'/product_spec/changeCount',
		cache : true,
		async : false,
		data : {tscId:tscId,count:wlCount},
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