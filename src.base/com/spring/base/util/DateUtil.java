package com.spring.base.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DateUtil {

	public static final String Format = "yyyy-MM-dd";

	/**
	 * 获得当前日期，缺省格式yyyy-MM-dd
	 * 
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @return 返回日期
	 */
	public String getDate(String format) {

		if (format == null || format.trim().length() == 0)
			format = Format;

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 将日期格式的字符串转换成Date，缺省格式yyyy-MM-dd
	 * 
	 * @param s
	 *            日期字符串
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @return 返回日期
	 */
	public static Date parse(String s, String format) {

		if (format == null || format.trim().length() == 0)
			format = Format;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(s);
		} catch (Exception e) {
			
			return null;
		}
	}

	/**
	 * 把2003-10-11这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 *            要分析的字符串,格式为2003-11-11
	 * @return Date
	 */
	public static java.util.Date dateStr2Date(String aStr) {
		Long result = dateStr2Time(aStr);
		if (null == result) {
			return null;
		}
		return new java.util.Date(result.longValue());
	}
	
	public static java.util.Date timeStr2Time(String aStr) throws ParseException {
		DateFormat sdf;
		if(aStr.length()>=8){
			 sdf = new SimpleDateFormat("HH:mm:ss");
		}else{
			 sdf = new SimpleDateFormat("HH:mm");
		}
		Date date = sdf.parse(aStr); 
		return date;
	}
	public static java.util.Date timeStr2TimeHHMM(String aStr) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse(aStr); 
		return date;
	}

	/**
	 * 得到当前日期的字符串，格式是YYYY-MM-DD
	 * 
	 * @return 当前日期的字符串，格式是YYYY-MM-DD
	 */
	public static String Date() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer date = new StringBuffer();
		date.append(calendar.get(Calendar.YEAR));
		date.append('-');
		date.append(calendar.get(Calendar.MONTH) + 1);
		date.append('-');
		date.append(calendar.get(Calendar.DAY_OF_MONTH));
		return date.toString();
	}

	/**
	 * 从一个日期字符串得到时间的long值.
	 * 
	 * @param strTemp
	 *            String
	 * @return long
	 */
	public static Long dateStr2Time(String strTemp) {
		if (null == strTemp || strTemp.length() < 8) {
			return null;
		}
		String sSign = strTemp.substring(4, 5);
		String sPattern = new StringBuffer("yyyy").append(sSign).append("MM").append(sSign).append("dd").toString();
		return str2DateTime(strTemp, sPattern);
	}

	/**
	 * 把字符串按照规则转换为日期时间的long值.
	 * 
	 * 如果不合法,则返回今天.
	 * 
	 * @param str
	 *            要分析的字符串
	 * @param pattern
	 *            规则
	 * @throws NullPointerException
	 * @return long
	 */
	public static Long str2DateTime(String str, String pattern) {
		DateFormat dateFmt = new SimpleDateFormat(pattern, Locale.US);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFmt.parse(str));
			// return c.getTimeInMillis();
			return new Long(calendar.getTime().getTime());
		} catch (Exception e) {
			
			// SystemUtil.printLine(e.toString());
			return null;
		}
	}

	/**
	 * 把2003-10-11 23:43:55这种字符串转换为时间格式,如果不合法,返回的是当前时间.
	 * 
	 * @param aStr
	 * @return Date
	 */
	public static java.util.Date timeStr2Date(String aStr) {
		Long result = dateTimeStr2Time(aStr);
		if (null == result) {
			return null;
		}
		return new java.util.Date(result.longValue());
	}

	/**
	 * 从一个日期时间字符串得到时间的long值. 例如 2004-5-6 23:52:22 ,包含秒
	 * 
	 * @param strTemp
	 *            String
	 * @return long
	 */
	public static Long dateTimeStr2Time(String strTemp) {
		if (null == strTemp || strTemp.length() < 8) {
			return null;
		}
		String sSign = strTemp.substring(4, 5);
		String sPattern = new StringBuffer("yyyy").append(sSign).append("MM")
				.append(sSign).append("dd").append(" HH:mm:ss").toString();

		Long aLong = str2DateTime(strTemp, sPattern);
		if (null == aLong) {
			String sShortPattern = new StringBuffer("yyyy").append(sSign)
					.append("MM").append(sSign).append("dd").append(" HH:mm")
					.toString();
			aLong = str2DateTime(strTemp, sShortPattern);
		}
		return aLong;
	}

	/**
	 * 获得当前日期的年份
	 * 
	 * @return 当前日期的年份
	 */
	public static Integer year() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.YEAR));
	}

	/**
	 * 获得当前日期的月份
	 * 
	 * @return 当前日期的月份
	 */
	public static Integer month() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 获得当前日期的前一个月份,如果当前是1月，返回12
	 * 
	 * @return 当前日期的前一个月份
	 */
	public static Integer beforeMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		if (month == 0) {
			return new Integer(12);
		} else {
			return new Integer(month);
		}
	}

	/**
	 * 获得当前日期的后一个月份,如果当前是12月，返回1
	 * 
	 * @return 当前日期的后一个月份
	 */
	public static Integer nextMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		if (month == 11) {
			return new Integer(1);
		} else {
			return new Integer(month + 2);
		}
	}

	/**
	 * 获得当前日期的日
	 * 
	 * @return 当前日期的日
	 */
	public static Integer day() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 获得当前时间的小时
	 * 
	 * @return 当前时间的小时
	 */
	public static Integer hour() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * 获得当前时间的分钟
	 * 
	 * @return 当前时间的分钟
	 */
	public static Integer minute() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.MINUTE));
	}

	/**
	 * 获得当前时间的秒
	 * 
	 * @return 当前时间的秒
	 */
	public static Integer second() {
		Calendar calendar = Calendar.getInstance();
		return new Integer(calendar.get(Calendar.SECOND));
	}

	/**
	 * 获得当前的星期是本月的第几周
	 * 
	 * @return 当前的星期是本月的第几周
	 */
	public static int WeekofMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int DayofWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获得这个月有几周
	 * 
	 * @return 这个月有几周
	 */
	public static int getMaxWeekofMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获得当前的星期是本月的第几周
	 * 
	 * @return 当前的星期是本月的第几周
	 */
	public static int WeekofMonth(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int DayofWeek(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获得当前的日期是本星期的第几天
	 * 
	 * @return 当前的日期是本星期的第几天
	 */
	public static int getTodayofWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获得这个月有几周
	 * 
	 * @return 这个月有几周
	 */
	public static int getMaxWeekofMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 0, 0, 0);
		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 得到当前日期时间的字符串，格式是YYYY-MM-DD HH:MM:SS
	 * 
	 * @return 当前日期时间的字符串，格式是YYYY-MM-DD HH:MM:SS
	 */
	public static String DateTime() {
		Calendar calendar = Calendar.getInstance();
		StringBuffer date = new StringBuffer();
		date.append(calendar.get(Calendar.YEAR));
		date.append('-');
		date.append(calendar.get(Calendar.MONTH) + 1);
		date.append('-');
		date.append(calendar.get(Calendar.DAY_OF_MONTH));
		date.append(' ');
		date.append(calendar.get(Calendar.HOUR_OF_DAY));
		date.append(':');
		date.append(calendar.get(Calendar.MINUTE));
		date.append(':');
		date.append(calendar.get(Calendar.SECOND));
		return date.toString();
	}

	/**
	 * 比较2个日期大小
	 * 
	 * @param startTime
	 *            起始日期
	 * @param endTime
	 *            结束日期
	 * @return 比较2个日期大小。>0：startTime>endTime 0:startTime=endTime
	 *         <0:startTime<endTime
	 * 
	 * @throws ParseException
	 */
	public static int compareTwoDate(String startTime, String endTime)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date b = formatter.parse(startTime);
		Date c = formatter.parse(endTime);
		return b.compareTo(c);
	}

	/**
	 * 获得传入date的string类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String dateToString(Date date, String fmt) {

		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}
	public static String dateToString(Date date, String fmt,Locale locale) {
		SimpleDateFormat sdf = null;
        if("en".equals(locale.getLanguage())){
        	 sdf = new SimpleDateFormat(fmt,Locale.ENGLISH);
        }else{
        	 sdf = new SimpleDateFormat(fmt);
        }
        String temp = sdf.format(date);
        if (temp.indexOf("September")!=-1) {
        	temp = temp.replace("September", "Sep");
		}
        if (temp.indexOf("October")!=-1) {
        	temp = temp.replace("October", "Oct");
		}
        if (temp.indexOf("November")!=-1) {
        	temp = temp.replace("November", "Nov");
		}
        if (temp.indexOf("December")!=-1) {
        	temp = temp.replace("December", "Dec");
		}
        if (temp.indexOf("January")!=-1) {
        	temp = temp.replace("January", "Jan");
		}
        if (temp.indexOf("February")!=-1) {
        	temp = temp.replace("February", "Feb");
		}
        if (temp.indexOf("August")!=-1) {
        	temp = temp.replace("August", "Aug");
		}
        if (temp.indexOf("March")!=-1) {
        	temp = temp.replace("March", "Mar");
		}
        if (temp.indexOf("April")!=-1) {
        	temp = temp.replace("April", "Apr");
		}
        if (temp.indexOf("June")!=-1) {
        	temp = temp.replace("June", "Jun");
		}
        if (temp.indexOf("July")!=-1) {
        	temp = temp.replace("July", "Jul");
		}
		return temp;
	}

	/**
	 * 获得传入date的string类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String DateToString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 获得传入date的string类型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String DateToString(Date date,String format) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 将输入格式为2004-8-13,2004-10-8类型的字符串转换为标准的Date类型,这种Date类型 对应的日期格式为YYYY-MM-DD
	 * 00:00:00,代表一天的开始时刻
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的Date对象
	 */
	public static Date toDayStartDate(String dateStr) {
		String[] list = dateStr.split("-");
		int year = Integer.parseInt(list[0]);
		int month = Integer.parseInt(list[1]);
		int day = Integer.parseInt(list[2]);
		Calendar cale = Calendar.getInstance();
		cale.set(year, month - 1, day, 0, 0, 0);
		return cale.getTime();
	}

	/**
	 * 根据生日计算年龄
	 * 
	 * @param birthDay
	 *            生日
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	
	public static void main(String[] ss) {
//		System.out.println(DateToString(new Date()));
		//Tue May 14 00:00:00 CST 2013 11:19:00 00:00:00
		DateUtil.timeStr2Date("Tue May 14 00:00:00 CST 2013 11:19:00 00:00:00");
		Locale  locale=new Locale("en");
	   System.out.println(DateUtil.dateToString(new Date(),"hh:mm",locale));
	   
	   Date date=new Date(System.currentTimeMillis());
	
		date.setHours(Integer.parseInt("3"));
		date.setMinutes(Integer.parseInt("29"));
		String gg=DateUtil.dateToString(date, "HH:mm");
		System.out.println(gg);
	}

	/**
	 * 计算两个日志相差的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getGapOfDays(Date d1, Date d2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long days = 0;
		long time1 = d1.getTime();
		long time2 = d2.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		days = diff / (1000 * 60 * 60 * 24);
		return days;
	}
	
	
	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	
	// 获得本周一到周日的日期（周一从0开始）
	public static String getdayOFWeek(int weekvalue) {
		
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus+weekvalue);
		Date monday = currentDate.getTime();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public static String getPreviousWeekday() {
		
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus - 7 );
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public static String getNextMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	//获得下周日期
	public static String getNextWeek(Date date) {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.add(GregorianCalendar.DATE,   7);
		DateFormat df = DateFormat.getDateInstance();
		Date day = currentDate.getTime();
		String preday = df.format(day);
		return preday;
	}
	//获得下周日期
	public static Date getNextWeekDate(Date date) {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.add(GregorianCalendar.DATE,   7);
		//DateFormat df = DateFormat.getDateInstance();
		Date day = currentDate.getTime();
		
		return day;
	}
	//指定年月和周几得到相应日期字符串集合
	public static List<String> circulateWeekSGetDate(int years,int months ,int week){
    	//Object name[] = {"日","一", "二", "三", "四", "五", "六"};
    	Calendar cd = new GregorianCalendar(years, months-1 , 1);
    	int dateNumber = cd.getActualMaximum(Calendar.DATE);
    	int firstDay = cd.get(Calendar.DAY_OF_WEEK) - 1;
    	int count = 1;
    	List<String> list = new ArrayList<String>();
    	for (int i = 0; i < 6; i++) {
    		for (int j = 0; j < 7; j++) {
	    		if (count > dateNumber)	{
	    			break;
	    		}else {
		    		if ((i * 7 + j) < firstDay){
		    			continue;
		    		}else {
		                if(j == week){ 
		                	String dateStr = years + "-" + months  + "-" + count;
		                	list.add(dateStr);
		                }
		    		}
		    		count++;
	    		}
    		}
    	}
    	return list;
    }
	
	public static List<String> getMaxMONTH(int year,int month){ 
		
		   List<String> monthofDate = new ArrayList<String>();
		   Calendar cd = Calendar.getInstance(); 
	       cd.set(Calendar.YEAR, year);//把日期设为当年第一天   
		   cd.set(Calendar.MONTH, month - 1);//把日期回滚一天。   
	       int maxMONTH = cd.getActualMaximum(Calendar.DATE);  
	       for (int i = 1; i < maxMONTH+1; i++) {
	    	   String dateStr = year + "-" + (month) + "-" + i;
	    	   monthofDate.add(dateStr);
	    	  
		}
	       return monthofDate;
		    
	}
	//根据年year月month日day得到星期星期几
	public static  String getDayOfWeek(int year, int month, int day,ResourceBundle bundle){
		String[] name= new String[]{
				bundle.getString("efetionmanage.common.date.day_SUN"),
				bundle.getString("efetionmanage.common.date.day_MON"),
				bundle.getString("efetionmanage.common.date.day_TUE"),
				bundle.getString("efetionmanage.common.date.day_WED"),
				bundle.getString("efetionmanage.common.date.day_THU"),
				bundle.getString("efetionmanage.common.date.day_FRI"),
				bundle.getString("efetionmanage.common.date.day_SAT")
		};
	 	Calendar   c   =   Calendar.getInstance()   ; 
	 	c.set(year,month-1,day); 
	 	return name[c.get(Calendar.DAY_OF_WEEK)-1]; 

	}
	public static  String getDayOfWeek(Date pTime, ResourceBundle bundle){
		
		String[] name= new String[]{
				bundle.getString("efetionmanage.common.date.day_SUN"),
				bundle.getString("efetionmanage.common.date.day_MON"),
				bundle.getString("efetionmanage.common.date.day_TUE"),
				bundle.getString("efetionmanage.common.date.day_WED"),
				bundle.getString("efetionmanage.common.date.day_THU"),
				bundle.getString("efetionmanage.common.date.day_FRI"),
				bundle.getString("efetionmanage.common.date.day_SAT")
		};
		Calendar   c   =   Calendar.getInstance()   ; 
		c.setTime(pTime);
		  
		return name[c.get(Calendar.DAY_OF_WEEK)-1]; 

	}
	public static  String getDayOfWeek(int weekofday,ResourceBundle bundle){
		String[] name= new String[]{
				bundle.getString("efetionmanage.common.date.day_MON"),
				bundle.getString("efetionmanage.common.date.day_TUE"),
				bundle.getString("efetionmanage.common.date.day_WED"),
				bundle.getString("efetionmanage.common.date.day_THU"),
				bundle.getString("efetionmanage.common.date.day_FRI"),
				bundle.getString("efetionmanage.common.date.day_SAT"),
				bundle.getString("efetionmanage.common.date.day_SUN")
		};
	 	    
	 	return name[weekofday]; 

	}
	public  static  int  dayForWeek(String pTime) throws  Throwable {   
	    SimpleDateFormat format = new  SimpleDateFormat("yyyy-MM-dd" );   
	    Date tmpDate = format.parse(pTime);   
		Calendar cal = new  GregorianCalendar(); 
		cal.setTime(tmpDate);
	    return  cal.get(Calendar.DAY_OF_WEEK)-1; 
	    
	}  
	public  static  int  dayForWeek(Date pTime)  {   
		Calendar cal = new  GregorianCalendar(); 
		cal.setTime(pTime);
	    return  cal.get(Calendar.DAY_OF_WEEK)-1;   
	}  
	 public static String getNextOrBackYearMonth(Date date , int pre){ 
	   String str = ""; 
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);       
	   Calendar cd = Calendar.getInstance(); 
	   cd.setTime(date);
	   cd.add(Calendar.MONTH,pre);//减一个月   
	   cd.set(Calendar.DATE, 1);
	  
	   str=sdf.format(cd.getTime());
	   System.out.println(str);
	   return str;     

	 }
	/**
	 * 获取当前时间
	 * @return  Timestamp
	 */
	public static Timestamp getTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(sdf.format(new Date()));
			Timestamp timestamp=new Timestamp(date.getTime());
			return timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


}
