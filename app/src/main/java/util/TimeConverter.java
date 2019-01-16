package util;

import android.util.Log;

import com.capybarasoft.weatherapp.mvp.model.entity.DailyForecast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    public static String getDayOfWeekFromTimeString(String timeString){
        try{
            Date from = new SimpleDateFormat(Constants.DatePatterns.BASE_TIME_PATTERN).parse(timeString);
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
            return simpleDateformat.format(from);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getDayAndMonthFromString(String timeString){
        try {
            Date from = new SimpleDateFormat(Constants.DatePatterns.BASE_TIME_PATTERN).parse(timeString);
            SimpleDateFormat simpleDateformat = new SimpleDateFormat(Constants.DatePatterns.YYYY_MM_DD_PATTERN);
            return simpleDateformat.format(from);
        }
        catch (Exception e){

        }
        return "";
    }

    public static boolean equalsCurrentDate(DailyForecast dailyForecast){
        try{
            Date fromDate = new SimpleDateFormat(Constants.DatePatterns.BASE_TIME_PATTERN).parse(dailyForecast.getDate());
            SimpleDateFormat fromDateFormat = new SimpleDateFormat(Constants.DatePatterns.YYYY_MM_DD_PATTERN);

            Date now = new Date();
            SimpleDateFormat nowDateFormat = new SimpleDateFormat(Constants.DatePatterns.YYYY_MM_DD_PATTERN);
            return fromDateFormat.format(fromDate).equals(nowDateFormat.format(now));
        }
        catch (Exception e){

        }
        return false;
    }

    public static String getDateWithoutTime(String timeString){
        try {
            Date from = new SimpleDateFormat(Constants.DatePatterns.BASE_TIME_PATTERN).parse(timeString);
            SimpleDateFormat simpleDateformat = new SimpleDateFormat(Constants.DatePatterns.YYYY_MM_DD_PATTERN);
            return simpleDateformat.format(from);
        }
        catch (Exception e){

        }
        return "";
    }


    public static String getHours(String timeString){
        try {
            Date from = new SimpleDateFormat(Constants.DatePatterns.BASE_TIME_PATTERN).parse(timeString);
            SimpleDateFormat simpleDateformat = new SimpleDateFormat(Constants.DatePatterns.HH_PATTERN);
            return simpleDateformat.format(from);
        }
        catch (Exception e){

        }
        return "";
    }

}
