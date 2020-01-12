package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 日期时间相关的工具类
 * @Classname DatetimeUtil
 * @Description TODO
 * @Date 2020-01-12
 * @Created by WANG
 */
public class DatetimeUtil {
    private static final Log log = LogFactory.getLog(DatetimeUtil.class);

    private static Map<String, DateFormat> formatMap = new HashMap<>();

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /***
     * 获取当前时间的毫秒数,是自1970-1-01 00:00:00.000 到当前时刻的时间距离
     * 如:1445090702909
     * 多线程,有很多重复值
     * @return 返回类型为String
     */
    public static String getCurrentTimeMillis(){
        return String.valueOf(System.currentTimeMillis());
    }

    /***
     * 获取当前时间的纳秒,1ms=1000000ns
     * 精准1000000倍的取纳秒的方法
     * 多线程,基本无重复值
     * @return
     */
    public static String getNanoTime(){
        return String.valueOf(System.nanoTime());
    }

    /***
     * 返回当前年，如：2015
     * @return
     */
    public static String getCurrentYear(){
        Calendar calendar=Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /***
     * 返回当前月,如：07
     * @return
     */
    public static String getCurrentMonth(){
        Calendar calendar=Calendar.getInstance();
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(month.length()==1){
            return "0"+month;
        }
        return month;
    }

    /***
     * 返回当前日,如：26
     * @return
     */
    public static String getCurrentDayOfMonth(){
        Calendar calendar=Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    private static DateFormat getDateFormat(String pattern){
        DateFormat format = formatMap.get(pattern);
        if(format == null){
            format = new SimpleDateFormat(pattern);
//            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            formatMap.put(pattern, format);
        }
        return format;
    }

    /**
     * 把时间字符串转换成指定格式的时间
     * @param pattern
     * @param dateString
     * @return Date
     */
    public static Date parse(String pattern, String dateString){
        try{
            DateFormat format = getDateFormat(pattern);
            return format.parse(dateString);
        }catch (ParseException e){
            log.error("时间解析错误 ", e);
            return null;
        }
    }

    /**
     * 把时间转换成指定格式的时间字符串
     * @param pattern
     * @param date
     * @return String
     */
    public static String format(String pattern, Date date){
        DateFormat format = getDateFormat(pattern);
        return format.format(date);
    }

    public static Date stringConvertDate(String dateString){
        return parse("yyyy-MM-dd HH:mm:ss", dateString);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            return sdf.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    /**
     * @Description TODO 距离零点还有多少秒
     * @param
     * @return int
     * @Date 2020-01-12
     * @auther Samuel
     */
    public static int secondsFromZero(){
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        final int seconds = (int)((cal.getTimeInMillis() - System.currentTimeMillis())/1000);
        return seconds;
    }

    public static Date stringConvertDate(String dateString,String pattern){
        return parse(pattern, dateString);

//        SimpleDateFormat sdf = new SimpleDateFormat(p);
//        try {
//            return sdf.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public static Date convertDate(Date date,String p){
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf2.parse(sdf.format(date));
        } catch (ParseException e) {
            log.error("DatetimeUtil.convertDate:"+e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args) {
//        Date d = DatetimeUtil.stringConvertDate("1991-07-30 00:00:00", "yyyy-MM-dd HH:mm:ss");
//        System.out.println(d);
//    }

    /***
     * 返回当前时间，如：2015-01
     * @return
     */
    public static String getDateTime(){
        Calendar calendar=Calendar.getInstance();
        String year= String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(month.length()==1){
            month="0"+month;
        }
        String datetime=year+"-"+month;
        return datetime;
    }
    /***
     * 返回当前时间的前一月份，如：2015-01前一月份是2014-12
     * @return
     */
    public static String getMonthAgo(){
        Calendar calendar=Calendar.getInstance();
        String year= String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        if(month.equals("1")){
            year= String.valueOf(calendar.get(Calendar.YEAR)-1);
            month="12";
        }else{
            month=String.valueOf(calendar.get(Calendar.MONTH));
        }
        if(month.length()==1){
            month="0"+month;
        }
        String datetime=year+"-"+month;
        return datetime;
    }
    /***
     * 返回当前时间的前年，如：2015前一年是2014
     * @return
     */
    public static String getYearAgo(){
        Calendar calendar=Calendar.getInstance();
        String year= String.valueOf(calendar.get(Calendar.YEAR)-1);
        return year;
    }

    /***
     * 返回查询时间
     * @return
     */
    static Calendar c = Calendar.getInstance();
    // 获取当前月
    final int month = c.get(Calendar.MONTH) + 1;
    public int getmonth() {
        return month;
    }

    // 获取到当前年
    final int year = c.get(Calendar.YEAR);
    public int getYear() {
        return year;
    }

    // 获取上一个季度
    public int getCurrentQuarger() {
        if (month >= 1 && month <= 3)
            return 4;
        if (month >= 4 && month <= 6)
            return 1;
        if (month >= 7 && month <= 9)
            return 2;
        return 3;
    }

    // 获取上一个月
    public int getCurrentMonthAgo() {
        if (month == 1) {
            return 12;
        }
        int currentMonth = month - 1;
        return currentMonth;
    }

    // 获取上一年
    public int getCurrentYearAgo() {
        return year - 1;
    }

    // 判断当前月分是否用生成季度报表
    public boolean isCreateQuarter() {
        if (month == 1 || month == 4 || month == 7 || month == 10) {
            return true;
        }
        return false;
    }

    // 获取季报表查询开始时间
    public String getQuarterBegin() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "10";
            yearTemp = year - 1;
        } else {
            monthTemps = "0" + (month - 3);
        }
        if (this.isCreateQuarter()) {
            date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        }
        return date;
    }

    // 获取到即报表上上季度开始时间
    public String getQuarerLastBengin() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "07";
            yearTemp = year - 1;
        } else if (month == 4) {
            monthTemps = "10";
            yearTemp = year - 1;
        } else {
            monthTemps = "0" + (month - 6);
        }

        if (this.isCreateQuarter()) {
            date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        }
        return date;
    }

    // 获取季报表查询结束时间
    public String getQuarterEnd() {
        String date = null;
        String monthTemps = String.valueOf(month);
        if (month < 10)
            monthTemps = "0" + monthTemps;
        if (this.isCreateQuarter()) {
            date = year + "-" + monthTemps + "-" + "01" + " 00:00:00";
        }
        return date;
    }

    // 获取到即报表上上季度结束时间
    public String getQuarerLastEnd() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "10";
            yearTemp = year - 1;
        } else if (month == 4) {
            monthTemps = "01";
        } else {
            monthTemps = "0" + (month - 3);
        }
        if (this.isCreateQuarter()) {
            date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        }
        return date;
    }

    // 获取月报表查询开始时间
    public String getMonthBegin() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "12";
            yearTemp = year - 1;
        } else if (month < 11) {
            monthTemps = "0" + (month - 1);
        }
        date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        return date;
    }

    // 获取上上月报表查询开始时间
    public String getMonthLastBegin() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "11";
            yearTemp = year - 1;
        } else if (month == 2) {
            monthTemps = "12";
            yearTemp = year - 1;
        } else if (month < 12) {
            monthTemps = "0" + (month - 2);
        } else {
            monthTemps = String.valueOf(month - 1);
        }
        date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        return date;
    }

    // 获取月报表查询结束时间
    public String getMonthEnd() {
        String date = null;
        String monthTemps = String.valueOf(month);
        if (month < 10) {
            monthTemps = "0" + month;
        }
        date = year + "-" + monthTemps + "-" + "01" + " 00:00:00";
        return date;
    }
    // 获取上上月报表查询结束时间
    public String getMonthLastEnd() {
        String date = null;
        String monthTemps = String.valueOf(month);
        int yearTemp = year;
        if (month == 1) {
            monthTemps = "12";
            yearTemp = year - 1;
        }
        if (month < 11) {
            monthTemps = "0" + (month - 1);
        } else {
            monthTemps = String.valueOf(month - 1);
        }
        date = yearTemp + "-" + monthTemps + "-" + "01" + " 00:00:00";
        return date;
    }
    //获取上个月的最后一天
    public static String getMonthLastDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE, -1);
        String data =df.format(cal.getTime());
        return data;

    }
    //获取本月的最后一天
    public String getThisMonthLastDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }
    //获取某月有几天
    public static Integer getThisMonthDay(String pTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            log.error("DatetimeUtil.getThisMonthDay:"+e.getMessage());
        }
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //获取上个月的第一天
    public static String getMonthFirstDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String data = df.format(cal.getTime());
        return data;
    }

    //获取本月的第一天
    public String getThisMonthFirstDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String data = df.format(cal.getTime());
        return data;
    }

    // 获取月报表名字前缀
    public String getMonthName() {
        int yearTemp = year;
        if (month == 1) {
            yearTemp = this.getCurrentYearAgo();
        }
        return yearTemp + "-" + this.getCurrentMonthAgo();
    }

    // 获取季报表名字前缀
    public String getQuarterName() {
        int yearTemp = year;
        if (month == 1) {
            yearTemp = this.getCurrentYearAgo();
        }
        return yearTemp + "第" + this.getCurrentQuarger() + "季度";
    }

    //获取当前日期是周几
    public static int getDay(){
        int getWeek = c.get(Calendar.DAY_OF_WEEK);
        if (getWeek == 1) {
            return 7;
        }else {
            return getWeek - 1;
        }
    }

    // 获取到本周周一//此数据不稳定请勿用
    public String getMonday() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        cal.add(Calendar.WEEK_OF_YEAR, 0);
        String data =df.format(cal.getTime());
        return data;
    }
    // 获取到本周周一//获取本周周一请用此方法
    public String getMondaytoday() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int day_of_week = c.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        c.add(Calendar.DATE , -day_of_week + 1 );
        c.add(Calendar.WEEK_OF_YEAR, 0);
        String data =df.format(c.getTime());
        return data;
    }
    //获取本周周日
    public String getMondaySumDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int day_of_week = c.get(Calendar. DAY_OF_WEEK) - 1;
        if (day_of_week == 0 ) {
            day_of_week = 7 ;
        }
        c.add(Calendar.DATE , -day_of_week + 1 );
        c.add(Calendar.WEEK_OF_YEAR, 0);
        c.add(Calendar.DAY_OF_YEAR,6);
        String data =df.format(c.getTime());
        System.out.print(data);
        return data;
    }
    // 获取到上个周周一
    public String getMondayLast() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String data =df.format(cal.getTime());
        return data;
    }



    //获取三个周前的周一
    public String getMondayThree() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        cal.add(Calendar.WEEK_OF_YEAR, -3);
        String data =df.format(cal.getTime());
        return data;
    }
    // 获取到上个周周天
    public String getLastSum() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.add(Calendar.DATE, +6);
        String data =df.format(cal.getTime());
        return data;
    }

    //获取周报名字
    public String getMondayName(){
        return getMondayLast()+"到"+getMonday();
    }
    //获取两分钟前
    public String getLastMinute(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -15);//15分钟前
        String data=time.format(cal.getTime());
        return data;
    }
    //获取当前时间
    public String getMinute(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String data=time.format(cal.getTime());
        return data;
    }

    public String getMinutes(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String data=time.format(cal.getTime());
        return data;
    }
    //获取上一天
    public static String getLastDay(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);//获取一天前
        String data=time.format(cal.getTime());
        return data;
    }
    //获取七天前日期
    public static String getLastweek(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);//7前
        String data=time.format(cal.getTime());
        return data;
    }

    // 获取到七个周前周一
    public String getMondayLastSeven() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //
        c.add(Calendar.WEEK_OF_YEAR, -7);
        String data =df.format(c.getTime());
        return data;
    }

    // 获取到半年前时间
    public String getSexMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Calendar.MONDAY-6,Calendar.DATE);
        String data =df.format(cal.getTime());
        return data;
    }

    /**
     *@Title getViewTime
     * @author lize16713
     * @Description 获取年数
     */
    public static String getViewTime(Long ms){
        double yesr = ms/(1000.0*60.0*60.0*24.0*30.0*12.0);
        String time = String.format("%.1f", yesr);

        if(time.contains("-")){
            return time = "0 年";
        }

        String[] str = time.split("\\.");

        int i= Integer.parseInt(str[0]);
        int d = Integer.parseInt(str[1]);
        if(i>0&&d>=5){
            time = str[0]+".5 年";
        }else if(i>0&&d<5){
            time = str[0]+" 年";
        }else if(i<=0&&d>=5){
            time = "1  年";
        }else{
            time = "0.5 年";
        }
        return time;
    }

    /**
     * yangkai17025
     * 给定时间判断此时间所在周的周一*（周日）
     * **/
    public String  convertWeekByDate(Date time) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if(1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime()); // 周一
//        cal.add(Calendar.DATE, 6);
//        String imptimeEnd = sdf.format(cal.getTime());//周天
        return imptimeBegin;
    }

    /**
     * yangkai17025
     *
     * **/
    public static class MyDate{
        int day;
        int month;
        int year;
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf2= new SimpleDateFormat("dd");

        public MyDate(Date d){
            this.day = Integer.valueOf(sdf2.format(d));
            this.month =  Integer.valueOf(sdf1.format(d));
            this.year = Integer.valueOf(sdf0.format(d));;
        }

        public long funtion(MyDate d){
            int newDay = d.day;
            int newMonth = d.month;
            int newYear = d.year;
            Calendar c1 = Calendar.getInstance();
            c1.set(newYear,newMonth,newDay);
            long n1 = c1.getTimeInMillis();
            Calendar c2 = Calendar.getInstance();
            c2.set(year,month,day);
            long n2 = c2.getTimeInMillis();
            return Math.abs((n1 - n2)/24/3600000);
        }
    }
    /**
     * 两个时间之间的查（天）
     * yangkai17025
     *
     * **/
    public static long getDateDiffe(Date a,Date b){
        MyDate d1 = new MyDate(a);
        MyDate d2 = new MyDate(b);
        return d1.funtion(d2);
    }
    /**
     * 获取本月有多少天）
     * yangkai17025
     *
     * **/
    public static int getDateOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    /**
     *返回给定时间是周几
     * @throws ParseException
     **/
    public static int getDayForWeek(String pTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            log.error("DatetimeUtil.getDayForWeek:"+e.getMessage());
        }
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 更新work_day数据
     */
    public static void updateWorkDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int year= 2017;
        int m=1;//月份计数
        while (m<13){
            int month=m;
            Calendar cal=Calendar.getInstance();//获得当前日期对象
            cal.clear();//清除信息
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month-1);//1月从0开始
            cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天


            int count=cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            System.out.println("当月天数" + count);

            System.out.println("INSERT INTO sys_workday (create_time,update_time,work_day) VALUES (NOW(),NOW(),'"+sdf.format(cal.getTime())+"');");//当月第一天
            for (int j=0;j<=(count - 2);){
                cal.add(Calendar.DAY_OF_MONTH,+1);
                j++;
                System.out.println("INSERT INTO sys_workday (create_time,update_time,work_day) VALUES (NOW(),NOW(),'"+sdf.format(cal.getTime())+"');");
            }
            m++;
        }
    }

    /**
     * 获取给定时间的星期
     * @param time
     * @return
     */
    public static String getWeek(Date time){
        if(time == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 1) {
            return "星期一";
        }
        if (week == 2) {
            return "星期二";
        }
        if (week == 3) {
            return "星期三";
        }
        if (week == 4) {
            return "星期四";
        }
        if (week == 5) {
            return "星期五";
        }
        if (week == 6) {
            return "星期六";
        }
        if (week == 0) {
            return "星期日";
        }
        return null;
    }

    /**
     * 获取当前时间前num天的日期
     *
     * @param num
     * @return
     */
    public static List<String> getDateList(int num) {
        List<String> dateList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateList.add(sdf.format(c.getTime()));
        if (num <= 0) {
            return dateList;
        }

        for (int i = 1; i < num; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            dateList.add(sdf.format(calendar.getTime()));
            calendar.clear();
        }
        return dateList;
    }

    public static void main(String[] args) {
        updateWorkDay();
    }

    /**
     *给定String返回data
     * @throws ParseException
     **/
    public static Date getDayOfStirng(String pTime) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(pTime);
    }

}