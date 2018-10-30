/**
 * 系统名称	: 海盟供应链系统
 * 模块名称	: com.stock.common.util
 * 类/接口名: DateConvert
 * 版本信息	: 1.00
 * 新建日期	: 2017-5-16下午6:35:40
 * 作者		: taofeng
 * 修改历史	: 2017-5-16下午6:35:40
 */
package com.stock.common.util.convert;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName	: DateConvert
 * Function		: 日期转换器.
 * date 		: 2017-5-16下午6:35:40
 * @author 		: taofeng
 * @version		: 1.0
 * @since   JDK 1.7
 */
@SuppressWarnings("rawtypes")
public class DateConvert implements Converter {
	
	/**
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		} else if (type == Timestamp.class) {
			return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
		} else if (type == Date.class) {
			return convertToDate(type, value, "yyyy-MM-dd");
		} else if (type == String.class) {
			return convertToString(type, value);
		}
		throw new ConversionException("不能转换 " + value.getClass().getName()
				+ " 为 " + type.getName());
	}

	/**
	 * convertToDate:转化日期.
	 * @author taofeng
	 * @param type
	 * @param value
	 * @param pattern
	 * @return 
	 * @since JDK 1.7
	 */
	protected Object convertToDate(Class type, Object value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (value instanceof String) {
			try {
				if (StringUtils.isEmpty(value.toString())) {
					return null;
				}
				Date date = sdf.parse((String) value);
				if (type.equals(Timestamp.class)) {
					return new Timestamp(date.getTime());
				}
				return date;
			} catch (Exception pe) {
				return null;
			}
		} else if (value instanceof Date) {
			return value;
		}
		throw new ConversionException("不能转换 " + value.getClass().getName()
				+ " 为 " + type.getName());
	}

	/**
	 * convertToString:转化字符串.
	 * @author taofeng
	 * @param type
	 * @param value
	 * @return 
	 * @since JDK 1.7
	 */
	protected Object convertToString(Class type, Object value) {
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (value instanceof Timestamp) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}

			try {
				return sdf.format(value);
			} catch (Exception e) {
				throw new ConversionException("日期转换为字符串时出错！");
			}
		} else {
			return value.toString();
		}
	}
	
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
	 * 获取指定往后，几个月 的第一天 或者最后一天
	 * @author czx
	 *
	 * @param months eg: 2 往后两个月
	 * @param day eg: 1 第一天 0最后一天
	 * @return
	 */
	public static Date getMonthDay(int  months, int day){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)+months);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}
	/**
	 * 判断是否是周末 1周日 7周六 2 3 4 5 6 周一至周五
	 * @param months
	 * @param day
	 * @return  
	 */
	public static boolean isWeekday(Date d){
		Calendar c = Calendar.getInstance();
		Format f = new SimpleDateFormat("E");
		c.setTime(d);
		int i = c.get(Calendar.DAY_OF_WEEK);
		if(2<=  i && 6>= i){
			return false;
		}else
			return true;
	}
	//获取当前月份之前或者之后的天数数量
	public static int getMaxDayByYearMonth(int months) {
		 Calendar calendar = Calendar.getInstance();
		  int year = calendar.get(Calendar.YEAR);
		  int month = calendar.get(Calendar.MONTH) +months;
		  if(month>11){
			  month=month-12;
			  year++;
		  }
		  int maxDay = 0;
		  int day=1;
		  System.out.println(year+"-"+(month+1)+"-"+maxDay);
        /**
         * 实例化日历各个字段,这里的day为实例化使用
         */
		  calendar.set(year,month,day);
        /**
         * Calendar.Date:表示一个月中的某天
         * calendar.getActualMaximum(int field):返回指定日历字段可能拥有的最大值
         */
          maxDay = calendar.getActualMaximum(Calendar.DATE);
          System.out.println(maxDay);
		  return maxDay;
	}
	//获取上个月日期
	public static String getPreYearMonth(int i) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		/*calendar.set(2012, Calendar.JANUARY, 13);
		System.out.println(format.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, -1);
		System.out.println(format.format(calendar.getTime()));
		 Calendar calendar = Calendar.getInstance();*/
		  int year = calendar.get(Calendar.YEAR);
		  int month = calendar.get(Calendar.MONTH);
		  int day=1;
		  calendar.set(year,month,day);
		  calendar.add(Calendar.MONTH, i);
       /**
        * Calendar.Date:表示一个月中的某天
        * calendar.getActualMaximum(int field):返回指定日历字段可能拥有的最大值
        */
       
		  return format.format(calendar.getTime());
	}
	//获取当月最大的天数
	public static int getDayOfMonth(){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int day=aCalendar.getActualMaximum(Calendar.DATE);
		return day;
	}
	//返回当前月份之前或者之后的周末数量
	public static int getWeekdayByPremonth(int months){

		  int i = 1 ;
		  int count = 0;
		  //System.out.println(getMaxDayByYearMonth(months));
		while(i<=getMaxDayByYearMonth(months)){
		  if(isWeekday(DateConvert.getMonthDay(months,i))){
			// System.out.println(dateToStr(DateHandler.getMonthDay(months,i),"yyyy-MM-dd"));
			  count++;
          }
		  i++;
          }
		return count;
	}
	
	

	public static String getPreDayDate() {
		 Date dNow = new Date();   //当前时间
		 Date dBefore = new Date();

		 Calendar calendar = Calendar.getInstance(); //得到日历
		 calendar.setTime(dNow);//把当前时间赋给日历
		 calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		 dBefore = calendar.getTime();   //得到前一天的时间


		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		 String defaultStartDate = sdf.format(dBefore);    //格式化前一天

		 return defaultStartDate;
	}
	
}
