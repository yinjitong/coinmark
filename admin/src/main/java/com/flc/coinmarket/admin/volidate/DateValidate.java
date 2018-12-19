package com.flc.coinmarket.admin.volidate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateValidate {
    public static HashMap<String,Date>  dateValidate(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {//七天前到当天
            Calendar c = Calendar.getInstance();
            endDate = c.getTime();
            c.add(Calendar.DATE, -7);
            startDate = c.getTime();
        } else if (startDate != null && endDate == null) {//startDate到当天
            Calendar c = Calendar.getInstance();
            endDate = c.getTime();

        } else if (startDate == null && endDate != null) {//前七天到endDate
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, -7);
            startDate = c.getTime();
        }
        HashMap<String,Date> map=new HashMap<>();

        Calendar before=Calendar.getInstance();
        before.setTime(startDate);
        before.set(Calendar.HOUR_OF_DAY,0);
        before.set(Calendar.MINUTE, 0);
        before.set(Calendar.SECOND, 0);
        Date startTime=  before.getTime();

        Calendar today=Calendar.getInstance();
        today.setTime(endDate);
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Date endTime=  today.getTime();

        map.put("startDate",startTime);
        map.put("endDate",endTime);
        return map;
    }


    public static void main(String[] args) {
        HashMap<String, Date> stringDateHashMap = DateValidate.dateValidate(null,null);

        System.out.println("startDate = [" + stringDateHashMap.get("startDate") + "]"+
                "endDate = [" + stringDateHashMap.get("endDate") + "]");
    }
}
