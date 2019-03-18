function trim(obj)  
{  
return obj.replace(/(^\s*)|(\s*$)/g, "");  
}; 
$.extend($.fn.validatebox.defaults.rules, {
	
	// 验证中文,英文,数字
	chAndEnAndNum : {
		validator : function(value) {
			return /^[a-zA-Z0-9\u4E00-\u9FA5]+$/.test(value);
		},
		message : "只能输入中文字、英文字母、数字"
	},

	numAndEn : {
		validator : function(value) {
			return /^[a-zA-Z0-9]+$/.test(value);
		},
		message : "只能输入英文字母、数字"
	},
	
            idcard: {// 验证身份证
                validator: function (value) {
                    return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/i.test(value);
                },
                message: '身份证号码格式不正确'
            },
            minLength: {
                validator: function (value, param) {
                    return value.length >= param[0];
                },
                message: '请输入至少（2）个字符.'
            },
            length: { validator: function (value, param) {
                var len = $.trim(value).length;
                return len >= param[0] && len <= param[1];
            },
                message: "输入内容长度必须介于{0}和{1}之间."
            },
            phone: {// 验证电话号码
                validator: function (value) {
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '格式不正确,请使用下面格式:020-88888888'
            },
            mobile: {// 验证手机号码
                validator: function (value) {
                    return /^(13|14|15|17|18)\d{9}$/i.test(value);
                },
                message: '手机号码格式不正确'
            },
            email: {// 验证邮箱
            	validator: function (value) {
            		return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+(com|cn)+$/i.test(value);
            	},
            	message: '邮箱格式不正确'
            },
            intOrFloat: {// 验证整数或小数
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '请输入数字，并确保格式正确'
            },
            currency: {// 验证货币
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '货币格式不正确'
            },
            qq: {// 验证QQ,从10000开始
                validator: function (value) {
                    return /^[1-9]\d{4,9}$/i.test(value);
                },
                message: 'QQ号码格式不正确'
            },
            integer: {// 验证整数 可正负数
                validator: function (value) {
                    //return /^[+]?[1-9]+\d*$/i.test(value);

                    return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
                },
                message: '请输入整数'
            },
            age: {// 验证年龄
                validator: function (value) {
                    return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
                },
                message: '年龄必须是0到120之间的整数'
            },

            chinese: {// 验证中文
                validator: function (value) {
                    return /^[\Α-\￥]+$/i.test(value);
                },
                message: '请输入中文'
            },
            english: {// 验证英语
                validator: function (value) {
                    return /^[A-Za-z]+$/i.test(value);
                },
                message: '请输入英文'
            },
            unnormal: {// 验证是否包含空格和非法字符
                validator: function (value) {
                    return /.+/i.test(value);
                },
                message: '输入值不能为空或包含其他非法字符'
            },
            username: {// 验证用户名
                validator: function (value) {
                    return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
                },
                message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
            },
            faxno: {// 验证传真
                validator: function (value) {
                    //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '传真号码不正确'
            },
            zip: {// 验证邮政编码
                validator: function (value) {
                    return /^[1-9]\d{5}$/i.test(value);
                },
                message: '邮政编码格式不正确'
            },
            ip: {// 验证IP地址
                validator: function (value) {
                    return /d+.d+.d+.d+/i.test(value);
                },
                message: 'IP地址格式不正确'
            },
            name: {// 验证姓名，可以是中文或英文
                validator: function (value) {
                    return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
                },
                message: '请输入姓名'
            },
            date: {
                validator: function (value) {
                    //格式yyyy-MM-dd或yyyy-M-d
                    return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
                },
                message: '清输入合适的日期格式'
            },
//            bank: {
//            	validator:function (value) {
//            		
//            	},
//            },
            msn: {
                validator: function (value) {
                    return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
                },
                message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
            },
            /*必须和某个字段相等*/  
            equalTo: {  
                validator:function(value,param){  
                    return $(param[0]).val() == value;  
                },  
                message:'字段不匹配'  
            } 
        }); 

/**
* 账号验证
*/
function checkName(obj)  {
	var TelNum =  /^[A-Z_a-z0-9]{6,16}$/;
	if(TelNum.test(obj)){
		return true;
	}else{
		return false;
	}
};

/**
* 电话号码验证
*/
function checkedMobile(obj) {
	var TelNum =  /^[0-9]{7,13}$/;
	if(TelNum.test(obj)){
		return true;
	}else{
		return false;
	}
};

var Validate = {
	checkedEmail : function(email) { // 验证Email格式是否是合法
		// var reg =
		// /^([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i;
		var reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
		if (reg.test(email)) {
			return true;
		}
		return false;
	},
	checkedMobile : function(mobile) { // 验证手机号码格式是否是有效
//		var reg = /^(1(([35][0-9])|(47)|[8][01236789]))\d{8}$/;
//		var reg = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
//		var reg = /^1[0123456789]\d{9}$/;
		var reg = /^1[3456789]\d{9}$/;
		if (reg.test(mobile)) {
			return true;
		}
		return false;
	},
	trim : function(str) { // 去掉字符串两边的空格
		return str.replace(/(^\s*)|(\s*$)/g, "");
	},
	strLen : function(str) {// 获取指定字符串的长度
		return str.replace(/[ ]/g, "").length;
	},
	length : function(str) { //字符串的长度，包括中文(一个中文占两位)
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	checkedTel : function(tel) { // 验证座机号码格式是否正确
		return /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/
				.test(tel);
	},
	checkEnglish : function(name) { // 英文值检测
		if (name.length == 0) {
			return false;
		}
		for (i = 0; i < name.length; i++) {
			if (name.charCodeAt(i) > 128) {
				return false;
			}
		}
		return true;
	},
	checkChina : function (name) {// 中文值检测 (是否包括中文)
		if (name.length == 0)
			return false;
		for (i = 0; i < name.length; i++) {
			if (name.charCodeAt(i) > 128)
				return true;
		}
		return false;
	},
	isChinaAndEnglish : function (name) {// 中文值检测 (是否由中文英文组成)
		if (name.length == 0)
			return false;
		var reg = /[a-zA-Z]|[\u4e00-\u9fa5]/;
		var isChecked = true;
		for(var i =0; i < name.length; i++) {
			if(!reg.test(name.charAt(i))) {
				isChecked = false;
				break;
			}
		}
		return isChecked;
	},
	
	isNumberAndEnglish : function (name) {// 是否由数字和英文组成
		if (name.length == 0)
			return false;
		var reg = /[a-zA-Z]|[\d+]/;
		var isChecked = true;
		for(var i =0; i < name.length; i++) {
			if(!reg.test(name.charAt(i))) {
				isChecked = false;
				break;
			}
		}
		return isChecked;
	},
	checkNumber : function isNumber(num) { // 判断是不是一个数字，是返回true ,不是返回 false;
		return !isNaN(num);
	},
	isNumber : function(str) { // 判断是不是数字 （正则表达式验证正整数）
		if (/^\d+$/.test(str)) {
			return true;
		} else {
			return false;
		}
	},
	isDouble : function(str){//判断是不是double类型的数据
		var re = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
		if(re.test(str)){
			return true;
		}else{
			return false;
		}
	},
	isDecimal : function(str){//判断是不是整数或者小数
		var re = /^\d+(\.\d*)?$/;
		if(re.test(str)){
			return true;
		}else{
			return false;
		}
	},
	filterHTML : function(str) { // 过滤字符串中的HTML标签111
		if (str != null) {
			return str.replace(/<.*?>/g, "");
		}
		return str;
	},
	checkedUserName : function(str) { // 判断字符串是否是数据及下划线组成
		var reg = /[^A-Za-z0-9_]/g;
		if (reg.test(str)) {
			return (false);
		} else {
			return (true);
		}
	},
	checkName : function(str){
		var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;
		if (!patrn.exec(str)) return false;
		return true;
	},
	isNull : function(str) { // 验证字符串是否为空
		if (str == null || str == "" || str == 'null') {
			return true;
		}
		return false;
	},
	onClickSelect : function() { // 点击选中文本框中的内容
		var obj = document.activeElement;
		if (obj.tagName == "TEXTAREA") {
			obj.select();
		}
		if (obj.tagName == "INPUT") {
			if (obj.type == "text") {
				obj.select();
			}
		}
	},
	stripscript : function(s) { // 过滤特殊字符
		var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
		var rs = "";
		for (var i = 0; i < s.length; i++) {
			rs = rs + s.substr(i, 1).replace(pattern, '');
		}
		return rs;
	},
	checkStr : function(str) { // 验证是否包括以下特殊字符
		var a = new Array("$", "@", "#", "!", "~", "`", "^", "&", "*", "<",
				">", "(", ")", "-", "=", "+", "\\", "/", "?", ".", ";", "{",
				"}", "[", "]", ":", "\"", "'") // a为验证字符串的规则
		for (i = 0; i < a.length; i++) {
			if (str.indexOf(a[i]) >= 0) { // 此处进行验证判断是否存在指定的字符
				return true;
			}
		}
		return false;
	},
	checkStr2 : function(str) { // 验证是否包括以下特殊字符
		var a = new Array("\\", "\"", "'") // a为验证字符串的规则
		for (i = 0; i < a.length; i++) {
			if (str.indexOf(a[i]) >= 0) { // 此处进行验证判断是否存在指定的字符
				return true;
			}
		}
		return false;
	},
	checkStrInfo : function(str, min, max, info) { // 验证字符串的长度,参数为最小长度，最大长度
													// ;验证通过返回true.否则返回f
		if (min != 0 && max != 0) { // 如果最小长度和最大长度都有限制时
			if (this.strLen(str) < min || this.strLen(str) > max) {
				// alert(info+"长度必须在"+min+"至"+max+"之间！");
	} }},
	checkArrayCharIndexOfStr : function(str,arr) { //验证是否包括指定数据中的特殊字符,str为要验证的字符串，arr为验证的字符数组
		for (i = 0; i < arr.length; i++) {
			if (str.indexOf(arr[i]) >= 0) { // 此处进行验证判断是否存在指定的字符
				return true;
			}
		}
		return false;
	} ,
	checkStrInfo : function(str,min,max,info) { //验证字符串的长度,参数为最小长度，最大长度 ;验证通过返回true.否则返回f
		if(min != 0 && max != 0) { // 如果最小长度和最大长度都有限制时
			if(this.strLen(str) < min || this.strLen(str) > max){
				//alert(info+"长度必须在"+min+"至"+max+"之间！");
				return false;
			}
		}
		if (min != 0) {
			if (this.strLen(str) < min) {
				// alert(info+ "长度不能少于"+ min+"位");
				return false;
			}
		}
		if (this.strLen(str) > max) {
			// alert(info+ "长度不能大于"+ max+"位");
			return false;
		}
		return true;
	},// 保留几位小数
	formatFloat : function(src, pos) {
		// （四舍五入）
		return src.toFixed(pos);
	},
	checkMoney : function(money) { // 验证金钱
		var reg = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
		return reg.test(money)
	},
	checkDate : function(date) { // 验证是否是有效的日期 YYYY-MM-DD
		var reg = /^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$/i;
		if (reg.test(date)) return true;
		return false;
	},
	checkTime : function(time){ //验证是否为有效时间 yyyy-MM-dd HH:mm:ss
		var reg =  /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
		if (reg.test(time)) return true;
		return false;
	},
	checkDateTime:function(str) {
		var reg=/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/ 
		if (!reg.test(str)){ 
			return false; 
		} 
		return true ;
	},
	dateCompareStr : function (d1,d2) {  //比较两个时间（前者大于后者时返回1，否则返回0）
		var reg = "/";

	    d1 = new Date(Date.parse(d1.replace(/-/g,reg)));
	    d2 = new Date(Date.parse(d2.replace(/-/g,reg)));
	    
		if(d1 > d2) {
			return 1;
		}else{
			return 0;
		}
	},
	checkUrl : function(url){
		var urlreg=/^[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/
		if(!urlreg.test(url)){
			return false; 
		}
		return true;
	},
	checkIsInteger:function(num){
		var r = /^[0-9]*[1-9][0-9]*$/;
		if(r.test(num)){
			return true;
		}else{
			return false;
		}
	},
	checkIdCard:function(idcard){
		var Errors = new Array("0", "身份证号码位数不对!", "身份证号码出生日期超出范围或含有非法字符!",
				"身份证号码校验错误!", "身份证地区非法!");
		var area = {
			11 : "北京",
			12 : "天津",
			13 : "河北",
			14 : "山西",
			15 : "内蒙古",
			21 : "辽宁",
			22 : "吉林",
			23 : "黑龙江",
			31 : "上海",
			32 : "江苏",
			33 : "浙江",
			34 : "安徽",
			35 : "福建",
			36 : "江西",
			37 : "山东",
			41 : "河南",
			42 : "湖北",
			43 : "湖南",
			44 : "广东",
			45 : "广西",
			46 : "海南",
			50 : "重庆",
			51 : "四川",
			52 : "贵州",
			53 : "云南",
			54 : "西藏",
			61 : "陕西",
			62 : "甘肃",
			63 : "青海",
			64 : "宁夏",
			65 : "新疆",
			71 : "台湾",
			81 : "香港",
			82 : "澳门",
			91 : "国外"
		}
		var idcard, Y, JYM;
		var S, M;
		var idcard_array = new Array();
		idcard_array = idcard.split("");
		if (area[parseInt(idcard.substr(0, 2))] == null)
			return Errors[4];
		switch (idcard.length) {
		case 15:
			if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
					|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
							.substr(6, 2)) + 1900) % 4 == 0)) {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			} else {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			}
			if (ereg.test(idcard))
				return Errors[0];
			else
				return Errors[2];
			break;
		case 18:
			if (parseInt(idcard.substr(6, 4)) % 4 == 0
					|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
							.substr(6, 4)) % 4 == 0)) {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
			} else {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
			}
			if (ereg.test(idcard)) {
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
						+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
						* 9
						+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
						* 10
						+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
						* 5
						+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
						* 8
						+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
						* 4
						+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
						* 2 + parseInt(idcard_array[7]) * 1
						+ parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9])
						* 3;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y, 1);
				if (M == idcard_array[17])
					return Errors[0];
				else
					return Errors[3];
			} else
				return Errors[2];
			break;
		default:
			return Errors[1];
			break;
		}
	}
};

function getArgs4Html(){
    //var sc=document.getElementsByTagName('script'); //获取js传回的值
	var args={},argsStr=[],param,t,name,value;
    var sc=decodeURI(window.location.search);
    var paramsArr=sc.split('?');
    if(paramsArr.length<=1){
    	return;
    }else{
    	paramsArr = paramsArr[1].split('&');
    }
    var args={},argsStr=[],param,t,name,value;
    for(var i=0,len=paramsArr.length;i<len;i++){
            param=paramsArr[i].split('=');
            name=param[0],value=param[1];
            if(typeof args[name]=="undefined"){ //参数尚不存在
                args[name]=value;
            }else if(typeof args[name]=="string"){ //参数已经存在则保存为数组
                args[name]=[args[name]]
                args[name].push(value);
            }else{ //已经是数组的
                args[name].push(value);
            }
    }
    //组装成json格式showArg(args[i])
    args.toString=function(){
        for(var i in args) argsStr.push(i+':'+args[i]);
        return '{'+argsStr.join(',')+'}';
    }
    return args; //以json格式返回获取的所有参数
};

Date.prototype.format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	} 

	if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
	if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	} 
	} 
	return format; 
	};