//属性个数
var getvalLen = 0;//获取属性组个数 
var valueIds = '';//属性值ids
var specIds = '';//规格ids
//选中规格值对应skuId
var _skuId = 0;
var product_store = 0;//指定下的商品库存
$(function(){
	getProductPic();
	getformatAttribute();
	getggsx();
	//限制商品数量输入上限
	var text = document.getElementById("addcount");
	//获取改变数量前的数量
	var beforeValue = text.value;
	var parentId = $('#salesparentId').val();
	if(parentId != 0 || parentId != "0"){
		text.onkeyup = function(){
			this.value=this.value.replace(/\D/g,'');
			if(text.value>product_store){
			  text.value = beforeValue;
			}
		}
	}
})			
function setSwipe() {
	var bullets = document.getElementById('position').getElementsByTagName('span');
	window.uiejSwipe = new Swipe(document.getElementById('uiejSwipe'), {
		startSlide : 0,
		speed : 400,
		auto : 3000,
		continuous : true,
		disableScroll : true,
		stopPropagation : false,
		callback : function(pos) {
			var i = bullets.length;
			while (i--) {
				bullets[i].className = '';
			}
			bullets[pos].className = 'on';
		},
		transitionEnd : function(index, elem) {
		}
	});
}
//获取商品图片
function getProductPic(){
	var Pics = $("#Pics").val();
	var html = '';
	var dotHtml = '';
	var strPics = Pics.split(',');
	for (var i=0;i<strPics.length;i++){	
		if(i%2 == 0){
//			html += '<div data-index="'+i+'" style="width: 100%; left: 0px; transition-duration: 400ms; -webkit-transition-duration: 400ms; -webkit-transform: translate(-482px, 0px) translateZ(0px);">';
//			html += '<img src="'+strPics[i]+'"></div>';
			html += '<div data-index="'+i+'" style="width: 100%; transition-duration: 400ms; -webkit-transition-duration: 400ms; -webkit-transform: translate(-482px, 0px) translateZ(0px);">';
			html += '<img src="'+strPics[i]+'"></div>';
		}else{
//			html += '<div style="width: 100%; left: -482px; transition-duration: 400ms; -webkit-transition-duration: 400ms; -webkit-transform: translate(-482px, 0px) translateZ(0px);  data-index="'+i+'">';
//			html += '<img src="'+strPics[i]+'"></div>'; 
			html += '<div style="width: 100%; transition-duration: 400ms; -webkit-transition-duration: 400ms; -webkit-transform: translate(-482px, 0px) translateZ(0px);  data-index="'+i+'">';
			html += '<img src="'+strPics[i]+'"></div>'; 
		}
		if(i == 0){
			dotHtml += '<span class="on"></span>';
		}else{
			dotHtml += '<span class=""></span>';
		}
	}
	
	$('#productPicDetail').html(html);
	$('#position').html(dotHtml);
	if(strPics.length > 0){
		setSwipe();
	}
}
			// 获取商品的规格属性
			function getformatAttribute() {
				var salesId = $('#salesId').val();
				var type = 2;//1：客户 2：经销商
				var tpId = $('#tpId').val();
				$.ajax({
				type : 'POST',
				url : ctx+'/product_spec/sku',
				dataType : 'json',
				cache : true,
				async : false,
				data : {tpId:tpId,salesId:salesId,type:type},
				success : function(data) {
					var dlHtml = '';
					// var valueNames = '';
					var inventory = 0;// 库存
					$.each(data.spec,function(index, item) {
//						inventory = item.shangjikucun;
						getvalLen = index;
						getvalLen++;
						if(specIds == ''){
							specIds = item.tps_spec_id;
//							alert("specIds:"+specIds);
						}else{
							specIds = specIds+','+item.tps_spec_id;
//							alert("specIds:"+specIds);
						}
						// valueNames = item.values;
						dlHtml += '<dl class="clearfix iteminfo_parameter_ sys_item_specpara" data-sid="'
								+ index + '">';//此处用index，getvalLen，item.tps_spec_id都可以
						dlHtml += '<dt>'
								+ item.tps_spec_name
								+ '：</dt><dd><ul class="sys_spec_text">';
						$.each(item.values,function(i, value) {
								if (i == 0) {
									dlHtml += '<li data-aid="'+ value.tps_spec_value + '" class="selected"><a href="javascript:void(0);" title="'+ value.tps_sku_id+ '">'
										+ value.tps_spec_value + '</a><i></i></li>';
								} else {
									//需要一个点击事件获取选中的规格值
									dlHtml += '<li data-aid="'+ value.tps_spec_value+ '"><a href="javascript:void(0);" title="'
										+ value.tps_sku_id + '">'+ value.tps_spec_value+ '</a><i></i></li>';
								}
							})
							dlHtml += '</ul></dd></dl>';
						})
					dlHtml += '<dl id="addCount"  class="clearfix iteminfo_parameter_ sys_item_specpara" >';
					dlHtml += '<dt>数量：</dt><dd><ul class="sys_in">';
//					dlHtml += '<a href="javascript:subNum();"><img src="'+ctx+'/static/product/images/sl-.jpg" /></a>';
					dlHtml += '<input id="addcount" name="addcount" type="tel" value="1" ';
					dlHtml += 'style="ime-mode:disabled" onkeydown="if(event.keyCode==13)event.keyCode=9" ';
					dlHtml += 'onKeyPress="if ((event.keyCode<48 || event.keyCode>57)) event.returnValue=false"/>';
//					dlHtml += '<a href="javascript:addNum('
//							+ inventory
//							+ ');"><img src="'+ctx+'/static/product/images/sl1.jpg" /></a>';
					dlHtml += '</ul></dd></dl>';
					// dlHtml += '<dl id="freight" class="clearfix
					// iteminfo_parameter sys_item_specpara" >';
					// dlHtml += '<dt>运费</dt><dd><ul class="sys_in1">';
					// dlHtml += '<input id="yunfei" type="text" name="yunfei"
					// value="00.00" />元</ul></dd></dl>';
					$('#formatAttribute').html(dlHtml);
				}
			});
}
			// 价格json
			// 获取默认商品规格属性
			function getggsx() {
				/*
				 * var i = $(".sys_item_spec .sys_item_specpara").find("ul>li")
				 * alert("i:"+i); //$.each(data.list,function(index,item){
				 * i.each(function(){ alert($(this));
				 * if($(this).hasClass("selected")){ $(".sys_item_spec
				 * .sys_item_specpara").attr("data-attrval",$(this).attr("data-aid")) }
				 * $(".sys_item_spec
				 * .sys_item_specpara").attr("data-attrval",$(this).attr("data-aid"))
				 *  })
				 */
//				alert("获取商品默认选中规格");
				$(".sys_item_spec .sys_item_specpara").each(function() {
					var i = $(this);
					var p = i.find("ul>li");
					p.each(function() {
						if ($(this).hasClass("selected")) {
							i.attr("data-attrval", $(this).attr("data-aid"))
						}
						getattrprice() //输出价格
					})
				})
				//获取对应属性的价格
				function getattrprice() {
					var defaultstats = true;
					var _val = '';
					var _resp = {
						mktprice : ".sys_item_mktprice",
					} //输出对应的class
					$(".sys_item_spec .sys_item_specpara").each(function() {
						var i = $(this);
						var v = i.attr("data-attrval");
						if (!v) {
							defaultstats = false;
						} else {
							_val += _val != "" ? "_" : "";
							_val += v;
						}
					})
					//输出价格
					valueIds = _val;
					getproductskuId(valueIds);
//					return;
				}
			}
			//商品规格选择
			$(function() {
				$(".sys_item_spec .sys_item_specpara").each(
						function() {
							var i = $(this);
							var p = i.find("ul>li");
							p.click(function() {
								if (!!$(this).hasClass("selected")) {
									$(this).removeClass("selected");
									i.removeAttr("data-attrval");
								} else {
									$(this).addClass("selected").siblings("li")
											.removeClass("selected");
									i.attr("data-attrval", $(this).attr(
											"data-aid"))
								}
								getattrprice() //输出价格
							})
						})
				//获取对应属性的价格
				function getattrprice() {
					var defaultstats = true;
					var _val = '';
					var _resp = {
						mktprice : ".sys_item_mktprice",
					} //输出对应的class
					$(".sys_item_spec .sys_item_specpara").each(function() {
						var i = $(this);
						var v = i.attr("data-attrval");
						if (!v) {
							defaultstats = false;
						} else {
							_val += _val != "" ? "_" : "";
							_val += v;
						}
					})
					//输出价格（输出规格值）
					valueIds = _val;
					getproductskuId(valueIds)
				}
			})
			//获取商品skuId，库存
			function getproductskuId(p_value){
				var arrSpecIds = new Array();
				if(getvalLen > 1){//当商品规格数量大于1时，规格逗号拼接字段（specIds）可用逗号分割
					arrSpecIds = specIds.split(',');
				}else{
					arrSpecIds = [specIds];
				}
				specIdsLen_ck = arrSpecIds.length;//规格id数组的长度即getvalLen
				var arr = new Array();
				arr = p_value.split('_');
				valLen_ck = arr.length;
				var salesId = $('#salesId').val();
				var type = 2;//1：客户 2：经销商
				var tpId = $('#tpId').val();
//				alert("p_value:"+p_value);
//				alert("getvalLen:"+getvalLen);
//				alert("valLen_ck:"+valLen_ck+"------specIdsLen_ck:"+specIdsLen_ck);
				if (getvalLen == valLen_ck) {
//					alert("规格选全后进入获取skuId步骤");
					$.ajax({
						type : 'POST',
						url : ctx+'/product_spec/sku',
						dataType : 'json',
						cache : true,
						async : false,
						data : {tpId:tpId,salesId:salesId,type:type},
						success:function(data){
							if(data.code == 100){
								$.each(data.sku,function(index, item) {
//									var specValboolean = true;//规格值是否相等
									var specValEqual = 0;//规格值相等数量
									$.each(item.spec,function(specIndex, value) {
										//sku中spec中tps_spec_id与spec中tps_spec_id一一对应
										for(var i=0;i<specIdsLen_ck;i++){
											if(specIndex==i){
//												alert("tps_spec_value:"+value.tps_spec_value);
//												alert("arr["+i+"]:"+arr[i]);
												if(value.tps_spec_value==arr[i]){
													specValEqual++;
//													alert("需要相等个数："+getvalLen+"-----实际相等个数"+specValEqual);
												}else{
													return;
												}
											}
										}
									})
									if(specValEqual==specIdsLen_ck){
										//规格值相等数量==specIdsLen_ck可以获取该skuId,库存
										_skuId = item.tps_id;
										product_store = item.tps_store;
//										alert("_skuId:"+_skuId);
//										alert("product_store:"+product_store);
										return;
									}
								})
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							
						}
					})
				}
				
			}
			//加入购物车
			function add_car() {
				addCount = $('#addcount').val();
				var salesId = $('#salesId').val();
				var salesparentId = $('#salesparentId').val();
				var type = 2;//1：客户 2：经销商
				var tpId = $('#tpId').val();
				var skuId = _skuId;
				var arr = new Array();
				arr = valueIds.split('_');
				valLen_ck = arr.length;
				//getvalLen++;
				//alert("valLen_ck:"+valLen_ck);
				//alert("getvalLen:"+getvalLen);
				if (getvalLen !== valLen_ck) {
					alert("属性选择不全");
					return;
				}

				if (addCount == '') {
					alert("请填写订单数量");
					return;
				}
				if (addCount == '0') {
					alert("订单数量不可为零");
					return;
				}
				if (skuId == 0) {
					$.messager.alert('提示','skuId参数不正确','warning');
					return;
				}
//
//				window.location.href = "${ctx}/agent/addCaigouOrder?valueIds="
//						+ valueIds + "&addcount=" + addCount + "&yunfei="
//						+ yunfei + "&productId=" + productId;
				//经销商添加购物车不需要cid，cid随便赋值1
				var cid = 1;
				$.ajax({
					type:'POST',
					dataType:'json',
					url:ctx+'/product_spec/car/add',
					data:{tsIds:salesparentId,tpIds:tpId,skuIds:skuId,counts:addCount,cid:cid,memberId:salesId,type:type},
					success:function(data){
						if(data.code == 100){
							var amount = data.amount;
							var tpId = data.tpId;
//							window.location.href = "${ctx}/agent/sumbitCaigouOrder?valueIds="+valueIds+"&addcount="+addCount+"&yunfei="+yunfei+"&productId="+productId+"&amount="+amount+"&orderId="+tpId+"&addrId="+addrId;
							var msg = data.message;
							$.messager.alert('提示',msg,'warning');
							return;
						}else if(data.code == 103){
							var msg = data.message;
							$.messager.alert('提示',msg,'warning');
							return;
						}else if(data.code == 104){
							var msg = data.message;
							$.messager.alert('提示',msg,'warning');
							return;
						}else{
							var msg = data.message;
//							alert(msg);
							$.messager.alert('提示',msg,'warning');
							return;
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						
					}
				})
			}
			function subNum() {
				addCount = $('#addcount').val();
				if (addCount == '1') {
					return;
				}
				$('#addcount').val(parseInt(addCount) - 1);
			}
			function addNum(count) {
				$('#addcount').val(parseInt($('#addcount').val()) + 1);

			}
			//查看购物车（查看商品或物料）
			function shoppingcar(){
				var tpType = $('#tpType').val();//1:商品 2:物料
				if(tpType=='1'){
					parent.addTabFun({
						src : ctx + "/shoppingCar/index/shoppingCarList",
						title : "购物车"
					});	
				}
				if(tpType=='2'){
					parent.addTabFun({
						src : ctx + "/product_spec/index/shoppingCarList_wuliao",
						title : "购物车"
					});	
				}
				
			}
			