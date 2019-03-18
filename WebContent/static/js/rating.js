function InitStar(count,cur,s0,s1,ctn,txt)
{
	for(var i=1;i<=count;i++){
		var p=(i<=cur)?s1:s0;
		$("<img/>").attr({"src":p,"flag":i}).css("cursor","pointer").mouseover(function(){ 
			var cur=parseInt($(this).attr("flag"));
			$("#"+ctn).children().each(function(){
				var i=parseInt($(this).attr("flag"));
				var p=(i<=cur)?s1:s0;
				$(this).attr("src",p); 
			});
			showStar(txt,cur); 
		}).click(function(){ 
			$("#"+ctn).attr("star",$(this).attr("flag")); 
		}).appendTo($("#"+ctn)); 
	}
	$("#"+ctn).attr("star",cur).mouseout(function(){ 
		var cur=parseInt($(this).attr("star")); 
		$(this).children().each(function(){ 
			var p=($(this).attr("flag")<=cur)?s1:s0; 
			$(this).attr("src",p); 
		}); 
		showStar(txt,cur); 
	}); 
}
function showStar(txt,cur){ 
	$("#"+txt).val(cur); 
	$("#"+txt).change(); 
}
