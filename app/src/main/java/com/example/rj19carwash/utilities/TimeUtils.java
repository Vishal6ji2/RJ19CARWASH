package com.example.rj19carwash.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TimeUtils {

    public static String getDayMonth(String actualDate){

        SimpleDateFormat month_date = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        String day_month = " ";

        if (actualDate!=null) {
            Date date;
            try {
                date = sdf.parse(actualDate);

                day_month = month_date.format(Objects.requireNonNull(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return day_month;
    }

    public static String getTime(String time) {

        String ampmTime = " ";
        if (time!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            Date dt;
            try {
                dt = sdf.parse(time);
                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                if (dt != null) {
                    ampmTime = sdfs.format(dt);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ampmTime;
    }

    public static String getDateTime(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        if (datetime != null) {
            try {
                date = formatter.parse(datetime);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            formatter = new SimpleDateFormat("hh:mm a, dd MMM yyyy", Locale.getDefault());
        }
            return formatter.format((date));
    }

}
