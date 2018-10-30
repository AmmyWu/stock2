package com.stock.service.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/******** 日期处理 ********/
public class DateHandler {

	/* *
	 * 改变日期函数， date变量为日期， amount变量为要更改的日期时间
	 * 例子：addDay("1990-1-2",2)，输出为"1990-1-4"
	 */
	public static String addDay(String date, int amount) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String changeDate = "";
		String[] arrary = date.split("-");
		int year, month, day;
		try {
			year = Integer.valueOf(arrary[0]);
			month = Integer.valueOf(arrary[1]);
			day = Integer.valueOf(arrary[2]);
			calendar.set(year, month - 1, day);
			calendar.add(calendar.DATE, amount);
			changeDate = sdf.format(calendar.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return changeDate;
	}

	/* *
	 * 改变日期格式函数， date变量为日期， 例子：changeFormat("2013-8-4")，输出为"8月4日 周二"
	 */
	@SuppressWarnings("deprecation")
	public static String changeFormat(String date) {
		String[] arrary = date.split("-");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		int year, month, day;
		String[] week = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		try {
			year = Integer.valueOf(arrary[0]);
			month = Integer.valueOf(arrary[1]);
			day = Integer.valueOf(arrary[2]);
			calendar.set(year, month - 1, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime()) + " "
				+ week[calendar.getTime().getDay()];
	}

	/* *
	 * 计算日期差函数， begin变量为开始日期，end变量为结束日期 例子：changeFormat("2013-8-4")，输出为"8月4日 周二"
	 */
	public static long getQuot(String begin, String end) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(begin);
			Date date2 = ft.parse(end);
			quot = date2.getTime() - date1.getTime();
			quot = quot / 86400000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quot;

	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @author chengxl@cst.zju.edu.cn at 2014-4-22
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date d, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(d);
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @author chengxl@cst.zju.edu.cn at 2014-4-22
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String timestampToStr(Timestamp ts, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(ts);
	}

	/**
	 * formatStr:yyyy-MM-dd HH:mm:ss
	 * 
	 * @author chengxl@cst.zju.edu.cn at 2014-4-22
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */

	public static Date strToDate(String dateStr, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;

		try {
			date = format.parse(dateStr);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;

	}
	
	/**
	 * 给指定日期加上指定月份
	 * @author chengxl@cst.zju.edu.cn at 2014年10月14日 
	 *
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date ,int month){
		
		 Calendar calender = Calendar.getInstance();
         calender.setTime( date);
         calender.add(Calendar.MONTH, month);
		
		return calender.getTime();
	}
	
	
	/**
	 * 给指定日期加上指定天数
	 * @author chengxl@cst.zju.edu.cn at 2014年10月14日 
	 *
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addDay(Date date ,int day){
		
		 Calendar calender = Calendar.getInstance();
         calender.setTime( date);
         calender.add(Calendar.DATE, day);
         
        
		
		return calender.getTime();
	}
	
	
	
	
	/**
	 * 给指定日期加上指定小时
	 * @author chengxl@cst.zju.edu.cn at 2014年10月14日 
	 *
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addHour(Date date ,int hour){
		
		 Calendar calender = Calendar.getInstance();
         calender.setTime( date);
         calender.add(Calendar.HOUR, hour);
         
        
		
		return calender.getTime();
	}
	
	
	/**
	 * 给指定日期加上指定小时
	 * @author chengxl@cst.zju.edu.cn at 2014年10月14日 
	 *
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMinute(Date date ,int minute){
		
		 Calendar calender = Calendar.getInstance();
         calender.setTime( date);
         calender.add(Calendar.MINUTE, minute);
         
        
		
		return calender.getTime();
	}
	
	//与系统当前时间相差 分钟数
	public static long diffMinutes(Date date){
		
		Date now = new Date();
		
		
		 long diff =  (now.getTime() - date.getTime());////这样得到的差值是微秒级别
		
//		  long days = diff / (1000 * 60 * 60 * 24);
//		  
//		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//		  
//		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		
		return diff/(1000 * 60);
	}


	private static void diff(){
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		 
		{
		 
		  Date d1 = df.parse("2004-03-26 13:41:40");
		 
		  Date d2 = df.parse("2004-01-02 11:30:24");
		  long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
		  long days = diff / (1000 * 60 * 60 * 24);
		 
		  long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		  long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		  
		  System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
		 
		}
		catch (Exception e)
		{
		}
		
	}
	
	  public static void main(String[] args){ 
		 // DayHandler d = new DayHandler();
		  
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.SIMPLIFIED_CHINESE);
		//  Calendar calender = Calendar.getInstance();
		 Date d = new Date();
		 
		 
		System.out.println(addDay(d,-1));;
		 
		 
		 System.out.println(dateToStr(DateHandler.addMinute(new Date(), 30),"yyyy-MM-dd hh:mm:ss")); 
	  }
	 
}
