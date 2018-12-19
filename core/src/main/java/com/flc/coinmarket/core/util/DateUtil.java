package com.flc.coinmarket.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 获取昨日00:00:00 今日00:00:00)
     */
    public  static  Date[] getDawnTime(){
        Date[] date=new Date[2];
        //获取昨日00:00:00
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.DATE, yesterday.get(Calendar.DATE) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY,0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar today=Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        System.out.println("yesterday=========:"+yesterday.getTime());
        System.out.println("today=========:"+today.getTime());
        date[0]=yesterday.getTime();
        date[1]=today.getTime();
        return date;
    }

    public static String  getTodayYMD(){
        Calendar c=Calendar.getInstance();
        Date time = c.getTime();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(time);
        return format;
    }

    public static String  getYesterdayYMD(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);//设置为前一天
        Date time = c.getTime();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(time);
        return format;
    }

    public static Date getYestrtdayDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);//设置为前一天
        Date dBefore = c.getTime();//得到前一天的时间
        return dBefore;
    }


    public static void main(String[] args) {
        Date[] dawnTime = getDawnTime();

        System.out.println("yestoday = [" + dawnTime[0] + "]........"+"today = [" + dawnTime[1] + "]");

    }
}
