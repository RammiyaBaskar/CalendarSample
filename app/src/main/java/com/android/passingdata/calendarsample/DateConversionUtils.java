package com.android.passingdata.calendarsample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by admin on 13-03-2017.
 */

public class DateConversionUtils {


    public static String getDateFromString(String dateInString, String actualformat, String exceptedFormat) {
        SimpleDateFormat form = new SimpleDateFormat(actualformat, Locale.getDefault());
//        form.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formatedDate = null;
        Date date;
        try {
            date = form.parse(dateInString);
            SimpleDateFormat postFormater = new SimpleDateFormat(exceptedFormat, Locale.getDefault());
            formatedDate = postFormater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatedDate;
    }


    public static int getAgeFromDOB(String dateInString, String actualformat, String exceptedFormat)
    {

        SimpleDateFormat form = new SimpleDateFormat(actualformat, Locale.getDefault());
//        form.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formatedDate = null;
        Date date;
        try {
            date = form.parse(dateInString);
            SimpleDateFormat postFormater = new SimpleDateFormat(exceptedFormat, Locale.getDefault());
            formatedDate = postFormater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int yearOfBirth = Integer.parseInt(formatedDate.toString());
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        return (currentYear-yearOfBirth);
    }

}
