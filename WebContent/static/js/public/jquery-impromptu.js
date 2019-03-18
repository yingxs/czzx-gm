/*
 * jQuery Impromptu
 * By: Trent Richardson [http://trentrichardson.com]
 * Version 2.3
 * Last Modified: 2/24/2009
 * 
 * Copyright 2009 Trent Richardson
 * Dual licensed under the MIT and GPL licenses.
 * http://trentrichardson.com/Impromptu/GPL-LICENSE.txt
 * http://trentrichardson.com/Impromptu/MIT-LICENSE.txt
 * 
 * button 按钮值为100时按钮置灰，并disabled
 */
 
jQuery.extend({	
	ImpromptuDefaults: { 
		prefix:'jqi', 
		buttonsaglin : 'center',
		buttons:{ '确定':true }, 
		style:{},
		alertType:'',
		loaded:function(){}, 
		submit:function(){return true;}, 
		callback:function(){}, 
		trueCallBack:function(){},
		lazyCallBack:function(){},
		cancelCallBack:function(){},
		checkClose:function(){return true},
		opacity:0, 
		zIndex: 999, 
		parentFocus:'',
		focusIFrame:'',
		overlayspeed:'slow', 
		promptspeed:'fast', 
		show:'fadeIn', 
		title:'提示',
		focus:function(){
		if(this.blur){ //如果支持 this.blur
			this.blur();
		}
		}, 
		isDrag:true,
		useiframe:false, 
		top:"15%", 
		width:'450px',
		ieAddHight:30,
		persistent: true 
		},
	ImpromptuStateDefaults: {
		 html: '', 
		 buttons: { '确定':true }, 
		 focus: function(){
			if(this.blur){ //如果支持 this.blur
				this.blur();
			}
			}, 
		 submit: 
		 function(){return true;} 
		 },
	SetImpromptuDefaults: function(o){ 
		jQuery.ImpromptuDefaults = jQuery.extend({},jQuery.ImpromptuDefaults,o);
	},
	SetImpromptuStateDefaults: function(o){ 
		jQuery.ImpromptuStateDefaults = jQuery.extend({},jQuery.ImpromptuStateDefaults,o);
	},
	ImpromptuGoToState: function(state){
		jQuery('.'+ jQuery.ImpromptuCurrentPrefix +'_state').hide();
		jQuery('#'+ jQuery.ImpromptuCurrentPrefix +'_state_'+ state).show(1,function(){
			jQuery(this).find('.'+ jQuery.ImpromptuCurrentPrefix +'defaultbutton').focus();
		});
	},
	ImpromptuClose: function(){
	  	jQuery('#'+ jQuery.ImpromptuCurrentPrefix +'box').fadeOut('fast',function(){ jQuery(this).remove(); });
		jQuery(window).focus();
		jQuery.ChangeIe6Select('visible');
	},
	ImpromptuCloseById:function(id){
		jQuery('#'+ jQuery.ImpromptuCurrentPrefix +'box'+id).fadeOut('fast',function(){ jQuery(this).remove(); });
		jQuery(window).focus();
		jQuery.ChangeIe6Select('visible');
	},
	rolePerNeedClose: function(roleId,type){
	    jQuery('#'+ jQuery.ImpromptuCurrentPrefix +'box').fadeOut('fast',function(){ jQuery(this).remove(); });
		jQuery(window).focus();
		jQuery.ChangeIe6Select('visible');
		if(type=='per'){
		  manageEmp(roleId, type);
		  return;
		}
		if(type=='emp'){
		managePer(roleId);
		}
	   
	},
	ImpromptuCurrentPrefix: 'jqi',
	ChangeIe6Select:function(css){
		var ie6 = (jQuery.browser.msie && jQuery.browser.version < 7);	
		if(ie6){
			jQuery('select').css('visibility',css);
			if(jQuery('iframe').size()>0){
				jQuery('iframe').each(function(){
					$(this).contents().find('select').css('visibility',css);
					$(this).contents().find('iframe').each(function(){
						$(this).contents().find('select').css('visibility',css);
					});
				});	
			}
		}
	},
	prompt: function(m,o,id){
		o = jQuery.extend({},jQuery.ImpromptuDefaults,o);
		jQuery.ImpromptuCurrentPrefix = o.prefix;
		
		var ie6 = (jQuery.browser.msie && jQuery.browser.version < 7);	
		var b = jQuery(document.body);
		var w = jQuery(window);
		var rightMsg = '<br/><table width="100%"><tr><td width="20%" align="right"><img src = "'+basePath+'/images/right.gif"/></td><td align="left" class ="spans">';
		var askMsg = '<br/><table width="100%"><tr><td width="20%" align="right"><img src = "'+basePath+'/images/ask.gif"/></td><td align="left" class ="spans">';
		var msgMsg = '<br/><table width="100%"><tr><td width="20%" align="right"><img src = "'+basePath+'/images/mess.gif"/></td><td align="left" class ="spans">';
		var warnMsg = '<br/><table width="100%"><tr><td width="20%" align="right"><img src = "'+basePath+'/images/mess.gif"/></td><td align="left" class ="spans">';
		var errorMsg = '<br/><table width="100%"><tr><td width="20%" align="right"><img src = "'+basePath+'/images/error.gif"/></td><td align="left" class ="spans">';
		var tableLast = '</td></tr></table>';
		if(o.alertType=='msg'){
			m = msgMsg+m+tableLast;
		}else if(o.alertType=='warn'){
			m = warnMsg+m+tableLast;
		}else if(o.alertType=='ask'){
			m = askMsg+m+tableLast;
		}else if(o.alertType=='right'){
			m = rightMsg+m+tableLast;
		}else if(o.alertType=='error'){
			m = errorMsg+m+tableLast;
		}
			
		//build the box and fade
		var msgbox ="";
		if(id){
			msgbox = '<div class="'+ o.prefix +'box" id="'+ o.prefix +'box'+id+'">';
		}else{
			msgbox = '<div class="'+ o.prefix +'box" id="'+ o.prefix +'box">';
		}		
		if(o.useiframe && ((jQuery('object, applet').length > 0) || ie6))
			msgbox += '<iframe src="javascript:;" class="'+ o.prefix +'fade" id="'+ o.prefix +'fade"></iframe>';
		else{ 
			jQuery.ChangeIe6Select('hidden');
			msgbox +='<div class="'+ o.prefix +'fade" id="'+ o.prefix +'fade"></div>';
		}
		
		msgbox += '<div class="blankskin '+ o.prefix +'" id="'+ o.prefix +'"><div class="blankskin_header relative"><span class="b">'+o.title+'</span><a href="#" id="closeb" class="absolute blankskin_header_close"></a>';		
		msgbox += '</div></div></div>';
		
		var jqib = $(msgbox).appendTo(b);
		var jqi = jqib.children('#'+ o.prefix);
		var jqif = jqib.children('#'+ o.prefix +'fade');
		
		//if a string was passed, convert to a single state
		if(m.constructor == String){
			m = { state0: { html: m , buttons: o.buttons, focus: o.focus, submit: o.submit } };
		}
		
		//build the states
		var states = '';
		
		jQuery.each(m,function(statename,stateobj){
			stateobj = jQuery.extend({},jQuery.ImpromptuStateDefaults,stateobj);
			m[statename] = stateobj;
			states = '<div class="blankskin_bodyer">'+ stateobj.html +'<div id='+o.prefix +'_state_'+ statename+' align="'+o.buttonsaglin+'"><p><input type="hidden" id="focus_button"/>';
			var flag = 0;
			jQuery.each(stateobj.buttons,function(k,v){flag++;});
			jQuery.each(stateobj.buttons,function(k,v){
				states += '<button onmouseover="mouseover($(this));" onmouseout="mouseout($(this));" name="'+ o.prefix +'_'+ statename +'_button'+ k +'" id="'+ o.prefix +'_'+ statename +'_button'+ k+'"';
				if(v) {
					states += ' class="blankskin_button_on"';
				} else {
					states += ' class="blankskin_button"';
				}
				
				states += ' value="'+ v +'">'+ k +'</button>&nbsp;&nbsp;';
			});
			states += '</p><div class="clear" style="height:5px"></div></div></div>';
		});
		
		states += '<div class="blankskin_footer"></div>';
		
		//insert the states...
		jqi.append(states);
		
		//Events
		jQuery.each(m,function(statename,stateobj){
			var state = jqi.find('#'+ o.prefix +'_state_'+ statename);
			
			state.children('p').children('button').click(function(){
				var msg = state.children('.blankskin_bodyer');
				var clicked = stateobj.buttons[jQuery(this).text()];
				var forminputs = {};
				
				//collect all form element values from all states
				jQuery.each(jqi.find('#'+ o.prefix +'states :input').serializeArray(),function(i,obj){		
						if (forminputs[obj.name] == undefined)
							forminputs[obj.name] = obj.value;
						else if (typeof forminputs[obj.name] == Array) 
							forminputs[obj.name].push(obj.value);
						else forminputs[obj.name] = [forminputs[obj.name],obj.value];
				});

				if(stateobj.submit(clicked,msg,forminputs)) {
					removePrompt(true,clicked,msg,forminputs,true);
				}else{
					if(o.focusIFrame!=null && o.focusIFrame!='') {
						document.frames(o.focusIFrame).document.getElementById(o.parentFocus).focus();
					} else if(o.parentFocus!=null && o.parentFocus!='') {
						if(document.getElementById(o.parentFocus)!= null){
							document.getElementById(o.parentFocus).focus();
						}	
					}
				}
			});
			state.find('.'+ o.prefix +'buttons button:eq('+ stateobj.focus +')').addClass(o.prefix +'defaultbutton');
			
		});
		
		var ie6scroll = function(){ 
			//alert(document.body.clientWidth);
			//jqib.css({ top: w.scrollTop() }); 
		};
		
		var fadeClicked = function(){
			if(o.persistent){
				var i = 0;
				jqib.addClass(o.prefix +'warning');
				var intervalid = setInterval(function(){ 
					jqib.toggleClass(o.prefix +'warning');
					if(i++ > 1){
						clearInterval(intervalid);
						jqib.removeClass(o.prefix +'warning');
					}
				}, 100);
			}
			else removePrompt();
		};
			

		var escapeKeyClosePrompt = function(e){
			var key = (window.event) ? event.keyCode : e.keyCode; // MSIE or Firefox?
			if(key==27) removePrompt();
		};

		var positionPrompt = function(){
			var scrollH = document.body.scrollHeight;
			scrollH = scrollH+o.ieAddHight;
			var noScrollH = w.height();
			var iehight = scrollH > noScrollH ? scrollH : noScrollH;

			jqib.css({ position: (ie6)? "absolute" : "fixed", height: w.height(), width:w.width(),top: (ie6)? w.scrollTop():0, left: 0, right: 0, bottom: 0 });
			jqif.css({ position: "absolute", height: (ie6)?iehight:w.height(), width:w.width(), top: (ie6)? -w.scrollTop():0, left: 0, right: 0, bottom: 0 });
			var lefts=(w.width()-o.width.substring(0,o.width.length-2))/2;
			
			jqi.css({ position: "absolute", top: o.top, left: lefts,width:o.width,height:o.height });//tan chu ceng		
		};
		
		var stylePrompt = function(){
			jqif.css({ zIndex: o.zIndex, display: "none", opacity: o.opacity });
			jqi.css({ zIndex: o.zIndex+1, display: "none" });
			jqib.css({ zIndex: o.zIndex });
		};
		
		var removePrompt = function(callCallback, clicked, msg, formvals, callTrueCallBack){
			if(!clicked || (clicked && o.checkClose())) {
				if(callTrueCallBack && clicked) o.trueCallBack();
				if(callTrueCallBack && !clicked) o.cancelCallBack();
				jqi.remove(); 
				if(ie6)b.unbind('scroll',ie6scroll);//ie6, remove the scroll event
				w.unbind('resize',positionPrompt);
				jqif.fadeOut(o.overlayspeed,function(){
					jqif.unbind('click',fadeClicked);
					jqif.remove();
					if(callCallback) o.callback(clicked,msg,formvals);
					jqib.unbind('keypress',escapeKeyClosePrompt);
					jqib.remove();
					jQuery.ChangeIe6Select('visible');
				});
				w.focus();
				if(callTrueCallBack && clicked) o.lazyCallBack();
			}
			if(o.focusIFrame!=null && o.focusIFrame!='') {
				document.frames(o.focusIFrame).document.getElementById(o.parentFocus).focus();
			} else if(o.parentFocus!=null && o.parentFocus!='') {
				if(document.getElementById(o.parentFocus)!= null){
					document.getElementById(o.parentFocus).focus();
				}	
			}
		};
		var dragDiv = function(){
			if(o.isDrag){
			  $('.'+o.prefix).css("cursor", "move");
			  $('.'+o.prefix).find(':checkbox').css("cursor", "auto");
			  $('.'+o.prefix).find(':radio').css("cursor", "auto");
			  $('.'+o.prefix).draggable();
			}
		};
		positionPrompt();
		stylePrompt();	
		dragDiv();		
		//if(ie6) {
		//	w.scroll(ie6scroll);
		//}//ie6, add a scroll event to fix position:fixed
		jqif.click(fadeClicked);
		w.resize(positionPrompt);
		jqib.keypress(escapeKeyClosePrompt);
		jqi.find('#closeb').click(removePrompt);
		//Show it
		jqif.fadeIn(o.overlayspeed);
		jqi[o.show](o.promptspeed,o.loaded);
		//var bns = jqi.find('.focus_button:first');
		//jqi.find('#focus_button').focus();
		$(".blankskin_button_on").focus();
		return jqib;
	}	
});

function mouseover(Obj){
	        Obj.addClass('blankskin_button_on');
		    Obj.focus();
		}
function mouseout(Obj){
	     Obj.removeClass('blankskin_button_on');
	     Obj.addClass('blankskin_button');
	     Obj.blur();
			
		}
