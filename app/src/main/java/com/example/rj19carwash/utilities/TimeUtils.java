package com.example.rj19carwash.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TimeUtils {

    public static String getDayMonth(String date){
        String day, month;
        LocalDate currentDate;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.parse(date);

            // Get day from date
            day = String.valueOf(currentDate.getDayOfMonth());

            // Get month from date
            month = currentDate.getMonth().toString();
        }else {
            String[] dateParts = date.split("-");

            // Getting day, month, and year
            // from date
//            String year = dateParts[0];
            month = dateParts[1];
            day = dateParts[2];

        }

        return day+" "+month.substring(0, 3);
    }

    public static String getTime(String time) {

        String ampmTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date dt;
        try {
            dt = sdf.parse(time);
            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
            if (dt != null) {
                ampmTime = sdfs.format(dt);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ampmTime;
    }

}
