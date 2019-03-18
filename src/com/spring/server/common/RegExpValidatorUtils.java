package com.spring.server.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spring.server.exception.ChineseException;
import com.spring.server.exception.ContainSpecialCharactersException;
import com.spring.server.exception.DateException;
import com.spring.server.exception.DateOrTimeException;
import com.spring.server.exception.DateTimeException;
import com.spring.server.exception.MobilePhoneNumberFormatException;
import com.spring.server.exception.NotNumbersException;
import com.spring.server.exception.ParameterLengthException;
import com.spring.server.exception.ParameterRequisiteException;

public class RegExpValidatorUtils {
	
	
	/**
	 * 验证是否不包含特殊字符，
	 * @param str
	 * @param length 最大长度，超出该长度返回false和message
	 * @param paramName 字段名称
	 * @param allowIsEmpty 是否允许为空
	 * @return
	 * @throws ContainSpecialCharactersException 
	 * @throws ParameterRequisiteException 
	 * @throws ParameterLengthException 
	 */
	public static BoolAndMessage isNotContainSpecialCharacters(String str,int length,String paramName,boolean allowIsEmpty) throws ContainSpecialCharactersException, ParameterRequisiteException, ParameterLengthException {
		String regex = "^[\u4E00-\u9FA5A-Za-z0-9_\\-()（）]+$";
		
		if(str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if(!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException(paramName);
		}else if(!match(regex, str) ){
			throw new ContainSpecialCharactersException(paramName);
		}else if(!(str.length()<=length)){
			throw new ParameterLengthException(paramName,length);
		}else{
			return new BoolAndMessage();
		}
		
	}

	
	public static BoolAndMessage isNumber(String str,int length,String paramName,boolean allowIsEmpty) throws ParameterRequisiteException, NotNumbersException, ParameterLengthException {
		String regex = "^[0-9]*$";
		str = str == null ? "" :str;
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if(!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException(paramName);
		}else if(!match(regex, str) ){
			throw new NotNumbersException(paramName);
		}else if(!(str.length()<=length)){
			throw new ParameterLengthException(paramName,length);
		}else{
			return new BoolAndMessage();
			
		}
	}
	
	public static BoolAndMessage isTel(String str,int length,String paramName,boolean allowIsEmpty) throws MobilePhoneNumberFormatException, ParameterRequisiteException, ParameterLengthException {
		String regex = "^1[3|4|5|8|9]\\d{9}$";  
		if (str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if (!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException("电话号码");
		}else if(!match(regex, str)  ){
			throw new MobilePhoneNumberFormatException();
		}else if(!(str.length()<=length)){
			throw new ParameterLengthException(paramName,length);
		} else {
			return new BoolAndMessage();
		}
	}
	public static BoolAndMessage isDateOrTime(String str,boolean allowIsEmpty) throws  ParameterRequisiteException, DateOrTimeException {
		String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";  
		if (str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if (!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException("日期或时间不能为空");
		}else if(!match(regex, str)  ){
			throw new DateOrTimeException();
		}else{
			return new BoolAndMessage();
		}
	}
	public static BoolAndMessage isDate(String str,boolean allowIsEmpty) throws  ParameterRequisiteException, DateOrTimeException, DateException {
		String regex = "^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$";  
		if (str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if (!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException("日期不能为空");
		}else if(!match(regex, str)  ){
			throw new DateException();
		}else{
			return new BoolAndMessage();
		}
	}
	public static BoolAndMessage isDateTime(String str,boolean allowIsEmpty) throws  ParameterRequisiteException, DateOrTimeException, DateException, DateTimeException {
		String regex = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";  
		if (str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if (!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException("日期时间不能为空");
		}else if(!match(regex, str)  ){
			throw new DateTimeException();
		}else{
			return new BoolAndMessage();
		}
	}
	public static BoolAndMessage isChinese(String str,int length,String paramName,boolean allowIsEmpty) throws ParameterRequisiteException, ChineseException, ParameterLengthException {
		String regex = "^[\\Α-\\￥]+$";  
		if (str == null){
			str="";
		}
		if (allowIsEmpty && str.trim().length()==0) {
			return new BoolAndMessage();
		}else if (!allowIsEmpty && str.trim().length()==0){
			throw new ParameterRequisiteException(paramName+"不能为空");
		}else if(!match(regex, str)  ){
			throw new ChineseException(paramName);
		}else if(!(str.length()<=length)){
			throw new ParameterLengthException(paramName,length);
		} else{
			return new BoolAndMessage();
		}
		
	}
	
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		Pattern pattern1 = Pattern.compile("^1[3|4|5|8|9]\\d{9}$");
		Pattern pattern2 = Pattern.compile("^0\\d{2,3}-\\d{7,8}$");
		Matcher matcher1 = pattern1.matcher("13279508893");
		Matcher matcher2 = pattern2.matcher("13279508893");
		
		System.out.println(!matcher1.matches() || !matcher2.matches());
	}
	
}
