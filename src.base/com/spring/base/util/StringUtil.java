package com.spring.base.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oro.text.perl.Perl5Util;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;



/**
 * $Id: StringUtil.java,v 1.1 2010/07/26 10:30:23 weijiguang Exp $
 */

public class StringUtil {

	public static final String REGEX_DIGIT = "[0-9]+";

	/**
	 * 判断字符串为null或者为""
	 */
	public static boolean isNull(String value) {
		return null == value || "".equals(value.trim());
	}

	/**
	 * 数值字符串转化为整数，非法数字返回-1
	 * 
	 * @param value
	 */
	public static int toInt(String value) {
		if (isNull(value)) {
			return -1;
		}

		if (!value.matches(REGEX_DIGIT)) {
			return -1;
		}

		return Integer.parseInt(value);
	}
	
	/**
	 * 以指定的字符编码解析字符串的长度
	 * 
	 * @param txt
	 *            要解析的字符串
	 * @param charset
	 *            编码
	 * @return 字符串的长度
	 */
	public static int getStrLength(String txt, String charset) {
		try {
			return txt.getBytes(charset).length;
		} catch (UnsupportedEncodingException ex) {
			return txt.length();
		}
	}
	
	/**
	 * 截取一定长度的字符串,根据指定的编码来判断长度. 例如指定编码为GBK,则一个汉字为2个字符长度
	 * 
	 * @param astr
	 *            String
	 * @param nlength
	 *            int
	 * @param destEncode
	 *            String
	 * @return String
	 */
	public static String msubstr(String astr, int nlength, String destEncode) {
		byte[] mybytes;
		try {
			mybytes = astr.getBytes(destEncode);
			if (mybytes.length <= nlength) {
				return astr;
			}
			if (nlength < 1) {
				return "";
			}
			for (int i = 0; i < astr.length(); i++) {
				String aTestStr = astr.substring(0, i + 1);
				int nDestLength = aTestStr.getBytes(destEncode).length;
				if (nDestLength > nlength) {
					if (i == 0) {
						return "";
					} else {
						return astr.substring(0, i);
					}
				}
			}
			return astr;
		} catch (java.io.UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/*
	 * // GENERAL_PUNCTUATION 判断中文的“号 // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号 //
	 * HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}
	
	/**
	 * 判断是否中文
	 * 
	 * @param strName
	 */
	public static boolean isChinese(String strName) {

		char[] ch = strName.toCharArray();

		for (int i = 0; i < ch.length; i++) {

			char c = ch[i];

			return isChinese(c);
		}
		return false;
	}
	
	/**
	 * 判断输入子串为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断输入子串为字母
	 * @return
	 */
	public static boolean isLetter(String str){
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 获取中文字符串每个汉字的首字母
	 * 
	 * @param getstring
	 * @return
	 */
	public static String convertZH(String getstring) {

		String backgetchar = "";// 用户输入汉字的拼音简码
		if (StringUtil.isChinese(getstring)) {
			String str[] = getstring.split("");// 将字符串转换成数组,数组第0位为""
			int length = str.length;// 获得数组长度
			int start = 1;// 设置循环初始值
			byte[] a = null;
			String getchar = "";// 存储汉字拼音首字母
			while (start < length) {
				a = str[start].getBytes();
				if (a.length > 1) {
					int asc = 256 * (a[0] + 256) + (a[1] + 256);
					if (asc >= 45217 && asc <= 45252) {
						getchar = "A";
					} else if (asc >= 45253 && asc <= 45760) {
						getchar = "B";
					} else if (asc >= 45761 && asc <= 46317) {
						getchar = "C";
					} else if (asc >= 46318 && asc <= 46825) {
						getchar = "D";
					} else if (asc >= 46826 && asc <= 47009) {
						getchar = "E";
					} else if (asc >= 47010 && asc <= 47296) {
						getchar = "F";
					} else if (asc >= 47297 && asc <= 47613) {
						getchar = "G";
					} else if (asc >= 47614 && asc <= 48118) {
						getchar = "H";
					} else if (asc >= 48119 && asc <= 49061) {
						getchar = "J";
					} else if (asc >= 49062 && asc <= 49323) {
						getchar = "K";
					} else if (asc >= 49324 && asc <= 49895) {
						getchar = "L";
					} else if (asc >= 49896 && asc <= 50370) {
						getchar = "M";
					} else if (asc >= 50371 && asc <= 50613) {
						getchar = "N";
					} else if (asc >= 50614 && asc <= 50621) {
						getchar = "O";
					} else if (asc >= 50622 && asc <= 50905) {
						getchar = "P";
					} else if (asc >= 50906 && asc <= 51386) {
						getchar = "Q";
					} else if (asc >= 51387 && asc <= 51445) {
						getchar = "R";
					} else if (asc >= 51446 && asc <= 52217) {
						getchar = "S";
					} else if (asc >= 52218 && asc <= 52967) {
						getchar = "T";
					} else if (asc >= 52698 && asc <= 52979) {
						getchar = "W";
					} else if (asc >= 52980 && asc <= 53640) {
						getchar = "X";
					} else if (asc >= 53689 && asc <= 54480) {
						getchar = "Y";
					} else if (asc >= 54481 && asc <= 62289) {
						getchar = "Z";
					}
				} else {
					getchar = str[start];
				}
				backgetchar += getchar;
				start++;
			}
		} else {
			String[] initc = getstring.split(" ");
			for (int i = 0; i < initc.length; i++) {
				backgetchar += initc[i].substring(0, 1);
			}
		}

		return backgetchar;
	}
	
	public static boolean isNullorBlank(String value) {
		return null == value || "".equals(value);
	}
	
	/**
	 * 拆分字符串到数组列表中.
	 * 
	 * @param pattern
	 *            拆分规则
	 * @param aStr
	 *            要拆分的字符串
	 * @return 拆分后的数组列表
	 */
	public static List splitStrToList(String pattern, String aStr) {
		List alist = new ArrayList();
		Perl5Util util = new Perl5Util();
		util.split(alist, pattern, aStr);
		return alist;
	}
   
	/**
	 * 按照规则拆分字符串到字符串数组中.
	 * 
	 * @param pattern
	 *            拆分规则,格式为: /,/
	 * @param aStr
	 *            要拆分的字符串
	 * @return 拆分后的字符串数组
	 */
	public static String[] splitString(String pattern, String aStr) {
		List<Object> alist = new ArrayList<Object>();
		Perl5Util util = new Perl5Util();
		util.split(alist, pattern, aStr);
		String[] astrs = new String[alist.size()];
		alist.toArray(astrs);
		return astrs;
	}
	/**
	 * 判断一个字符串是否全为数字
	 * 
	 */
	public static boolean isNum(String str) {
		for (int j = 0; j < str.length(); j++) {
			if (!(str.charAt(j) >= 48 && str.charAt(j) <= 57))
				return false;
		}
		return true;
	}
	
	/**
	 * splitListToSub 将比较长的字段内容截短，主要解决sql语句中in () 内容太长，造成数据库服务器堵塞问题
	 * @param source ，内容部分如：deptids列表
	 * @param subLength ，要截取的子内容长度如：deptids:1,2,3;长度为3
	 * @return String[] ，返回子内容数组，
	 */
	public static String[] splitListToSub(List source,int subLength){
		int arrLength = 1 ;
		if(source.size() > subLength){
			arrLength = source.size() / subLength ;
			arrLength = source.size() % subLength == 0 ? arrLength : arrLength + 1;
		}
		
		String []subSQL = new String[arrLength];
		for(int i = 0; i < arrLength; i++){
			List<Integer> tempSub = null;
			if(i==arrLength-1){
				tempSub = source.subList((i*subLength), source.size());
			}else{
				tempSub = source.subList(0 + (i*subLength) , subLength + (i*subLength));
			}
			
			subSQL[i] = tempSub.toString();
			//System.out.println(tempSub.toString());
		}
		return subSQL;
	}

	
	public static Document getDocument(String message) {
		Document doc = null;
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(new ByteArrayInputStream(message
					.getBytes("utf-8")));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 全角转半角
	 * @author zhangzhanliang
	 * @version 2010-6-28
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/**
	 *  半角转全角：  
	 * @author zhangzhanliang
	 * @version 2010-6-28
	 * @param input
	 * @return
	 */
	public static String ToSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	public static void main(String[] args) {
		System.out.println(checkFetionpwd("111111"));
	}
	

	public static boolean isInteger(String intstr) {
		if (isNullorBlank(intstr)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[-]?\\d+");
		Matcher isNum = pattern.matcher(intstr);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}
	
	public static String format(String source, String []args){
		if(args == null || args.length < 1){
			return source;
		}
		
		for(int i = 0; i < args.length; i++){
			String oldChar = "{*}".replace("*", String.valueOf(i));
			source = source.replace(oldChar, args[i]);
		}
		return source;
	}
	
	public static String precent(int numerator, int denomintor){
		if(numerator == 0){
			return "0%";
		}
		float fnum = Float.parseFloat(String.valueOf(numerator));
		float fdenom = Float.parseFloat(String.valueOf(denomintor));
		float result = fnum / fdenom * 100;
		DecimalFormat df = new DecimalFormat("0.00");
		String retV = df.format(result);
		if("00".equals(retV.substring(retV.length() - 2, retV.length()))){
			Float fRes = Float.valueOf(result);
			retV = String.valueOf(fRes.intValue());
		}
		return retV + "%";
	}
	
	public static boolean isRepeatChar(String source){
		if(source == null || source.trim().length() < 2){
			return false;
		}
		
		for(int i = 0; i < source.length(); i++){
			char chrSource = source.charAt(i);
			String tempSource = source.replaceAll(String.valueOf(chrSource), "");
			if(tempSource.length() < source.length() - 1){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkFetionpwd(String pwd) {
		if (isNullorBlank(pwd)) {
			return false;
		}
		String match = "^(\\w){1,32}$";
		return pwd.matches(match);
	}
	/**
	 * pattern 字符串模型替换
	 * @param source
	 * @param value
	 * @author zhuzhigang
	 * @return
	 */
	public static String pattern(String source, final String... value){
		if(value == null || value.length < 1 || source == null){
			return source;
		}
		
		String []oldChar = new String[value.length];
		for(int i = 0; i < value.length; i++){
			oldChar[i] = "{*}".replace("*", String.valueOf(i));
		}
		
		for(int i = 0; i < value.length; i++){
			source = source.replace(oldChar[i], value[i]);
		}
		
		return source;
	}
	/**
	 * 
	* @Title: htmlToString 
	* @Description: html内容转换为String 
	* @param @param content
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String htmlToString(String content){
		if (content!=null && !"".equals(content)) {
			content = content.replaceAll("&", "&amp;");
			content = content.replaceAll("<", "&lt;");
			content = content.replaceAll(">", "&gt;");
			content = content.replaceAll("\"", "&quot;");
			content = content.replaceAll("'", "&acute;");
			content = content.replaceAll("\\\\", "/");
//			content = content.replaceAll("\\r\\n", "");
			return content;
		}else {
			return "";
		}
	}
	
}