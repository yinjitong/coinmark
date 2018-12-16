package com.flc.coinmarket.app.volidate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DateValidate {
    public static HashMap<String,String>  dateValidate(Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {//14天前到当天
            Calendar c = Calendar.getInstance();
            endDate = c.getTime();
            c.add(Calendar.DATE, -14);
            startDate = c.getTime();
        } else if (startDate != null && endDate == null) {//startDate到当天
            Calendar c = Calendar.getInstance();
            endDate = c.getTime();
        } else if (startDate == null && endDate != null) {//前14天到endDate
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, -14);
            startDate = c.getTime();
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sStartDate = sdf.format(startDate);
        String sEndDate =sdf.format(endDate);

        HashMap<String,String> map=new HashMap<>();
        map.put("startDate",sStartDate);
        map.put("endDate",sEndDate);
        return map;
    }
}
