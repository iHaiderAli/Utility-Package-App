package com.creative.utilitypackage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtility {

    public static long convertTimeStringToMiliSeconds(String strTime) {

        long retVal = 0;
        String hour = strTime.substring(0, 2);
        String min = strTime.substring(3, 5);
        String sec = strTime.substring(6, 8);
        String milli = strTime.substring(9, 12);
        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(min);
        int s = Integer.parseInt(sec);
        int ms = Integer.parseInt(milli);

        long lH = h * 60 * 60 * 1000;
        long lM = m * 60 * 1000;
        long lS = s * 1000;

        retVal = lH + lM + lS + ms;
        return retVal;
    }

    public static String convertMiliSecondsToTimeString(String pattern, long millis) {
        //      "h:mm a"
        SimpleDateFormat timeFormatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return timeFormatter.format(millis);
    }

    public static String convertDateToMilis(String pattern, String givenDateString) {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            Date mDate = sdf.parse(givenDateString);
            long timeInMilliseconds = mDate.getTime();
            SimpleDateFormat format_before = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);

            time = format_before.format(timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    public static long getMiliSecOfTime(String pattern, String time) {
        //      "HH:mm:ss.SSS"
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Calendar datetime = Calendar.getInstance();

        String[] strings = time.split(":");
        datetime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strings[0]));
        String[] mins = strings[1].split(" ");
        datetime.set(Calendar.MINUTE, Integer.parseInt(mins[0]));

        return Long.parseLong(sdf.format(datetime.getTime()));
    }

    public static String formatCompleteMonthToShortMonth(String month) {
        SimpleDateFormat monthParse = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM", Locale.ENGLISH);
        try {
            return monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getCurrentTimeInMilis() {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int am_pm = calendar.get(Calendar.AM_PM);
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.AM_PM, am_pm);

        return calendar.getTimeInMillis();
    }

    public static String getDesiredDateFormat(String currentDatePattern, String inputtedDate, String desirePattern) {

        //current date pattern should be same as inputted date
        SimpleDateFormat format1 = new SimpleDateFormat(currentDatePattern, Locale.ENGLISH);
        Date date = null;
        try {
            date = format1.parse(inputtedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat(desirePattern, Locale.ENGLISH);
        return format2.format(date);
    }

    public static String getTimeDifference(long fromDate) {

        long diff = 0;
        long ms2 = System.currentTimeMillis();
        diff = ms2 - fromDate;

        int diffInSec = Math.abs((int) (diff / (Constants.SECONDS)));
        String difference = "";
        if (diffInSec < Constants.MINUTES) {
            difference = diffInSec +" "+ Constants.SECONDS_AGO;
        } else if ((diffInSec / Constants.HOUR) < 1) {
            difference = (diffInSec / Constants.MINUTES) +" "+ Constants.MINUTES_AGO;
        } else if ((diffInSec / Constants.DAY) < 1) {
            difference = (diffInSec / Constants.HOUR) +" "+ Constants.HOURS_AGO;
        } else if ((diffInSec / Constants.WEEK) < 1) {
            difference = (diffInSec / Constants.DAY) +" "+ Constants.DAYS_AGO;
        } else if ((diffInSec / Constants.MONTH) < 1) {
            difference = (diffInSec / Constants.WEEK) +" "+ Constants.WEEK_AGO;
        } else if ((diffInSec / Constants.YEAR) < 1) {
            difference = (diffInSec / Constants.MONTH) +" "+ Constants.MONTHS_AGO;
        } else {

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            difference = format_before.format(c.getTime());
        }

        return difference;
    }

}
