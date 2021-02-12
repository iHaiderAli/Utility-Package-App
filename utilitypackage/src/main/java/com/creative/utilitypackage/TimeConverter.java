package com.creative.utilitypackage;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter {

    private static final String WEEK_AGO = "week ago";
    private static final String MONTHS_AGO = "month ago";
    private static final String DAYS_AGO = "days ago";
    private static final String HOURS_AGO = "hours ago";
    private static final String MINUTES_AGO = "minute ago";
    private static final String SECONDS_AGO = "seconds ago";

    private static int SECONDS = 1000;
    private static int MINUTES = 60;
    private static int HOUR = MINUTES * 60;
    private static int DAY = HOUR * 24;
    private static int WEEK = DAY * 7;
    private static int MONTH = DAY * 30;
    private static int YEAR = MONTH * 12;

    public static long getCurrentDateTime() {
        long time= System.currentTimeMillis();
        return time;
    }

    public static String convertMiliSecondsToString(String pattern, long millis) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return timeFormatter.format(millis);
    }

    public static long convertStringToMiliSeconds(String date, String formatter) {

        SimpleDateFormat sdf = new SimpleDateFormat(formatter);

        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    private static long getTimeZoneOffset(long time, TimeZone from, TimeZone to) {
        int fromOffset = from.getOffset(time);
        int toOffset = to.getOffset(time);
        int diff = 0;

        if (fromOffset >= 0){
            if (toOffset > 0){
                toOffset = -1*toOffset;
            } else {
                toOffset = Math.abs(toOffset);
            }
            diff = (fromOffset+toOffset)*-1;
        } else {
            if (toOffset <= 0){
                toOffset = -1*Math.abs(toOffset);
            }
            diff = (Math.abs(fromOffset)+toOffset);
        }
        return diff;
    }

    public static String hmsTimeFormatter(long milliSeconds) {
        long seconds = milliSeconds / 1000;
        long sec = seconds % 60;
        long min = (seconds / 60) % 60;
        long hrs = (seconds / (60 * 60)) % 24;
        String s, h, m;
        if (sec <= 9) {
            s = "0" + sec;

        } else {
            s = "" + sec;
        }
        if (hrs <= 9) {
            h = "0" + hrs;
        } else {
            h = "" + hrs;
        }
        if (min <= 9) {
            m = "0" + min;
        } else {
            m = "" + min;
        }
        String remainingTime1 = "" + h + ":" + m + ":" + s;
        String remainingTime = "" + m + ":" + s;
        Log.d("Timmer", "" + remainingTime1);

        return remainingTime1;
    }

    public static long getStartDateOfWeekInTimestamp(Calendar calendar) {

//        let startingDateTimestamp: Int64 = startingDate.millisecondsSince1970
        long startingDateTimestamp = calendar.getTimeInMillis();
        long currentDateTimestamp = Calendar.getInstance().getTimeInMillis();

        long differenceInCurrentAndStarting = currentDateTimestamp - startingDateTimestamp;

        long millisecondsInAWeek = 604800000;
        long numberOfWeek = differenceInCurrentAndStarting/millisecondsInAWeek;

        return ( startingDateTimestamp + ( millisecondsInAWeek * numberOfWeek ) );
    }

    public static long getEndingDateOfWeekInTimestamp(Calendar calendar) {

        long startingDateTimestamp = calendar.getTimeInMillis();
        long currentDateTimestamp = Calendar.getInstance().getTimeInMillis();

        long differenceInCurrentAndStarting = currentDateTimestamp - startingDateTimestamp;

        long millisecondsInAWeek = 604800000;
        long numberOfWeek = differenceInCurrentAndStarting/millisecondsInAWeek;

        return ( startingDateTimestamp + ( millisecondsInAWeek * ( numberOfWeek + 1 ) ) );
    }

    public static long getStartDateOfMonthInTimestamp(Calendar calendar) {

        long startingDateTimestamp = calendar.getTimeInMillis();
        long currentDateTimestamp = Calendar.getInstance().getTimeInMillis();

        long differenceInCurrentAndStarting = currentDateTimestamp - startingDateTimestamp;

        long millisecondsInAMonth = 2592000000L;
        long numberOfMonth = differenceInCurrentAndStarting/millisecondsInAMonth;

        return ( startingDateTimestamp + ( millisecondsInAMonth * numberOfMonth ) );
    }

    public static long getEndingDateOfMonthInTimestamp(Calendar calendar) {

        long startingDateTimestamp = calendar.getTimeInMillis();
        long currentDateTimestamp = Calendar.getInstance().getTimeInMillis();

        long differenceInCurrentAndStarting = currentDateTimestamp - startingDateTimestamp;

        long millisecondsInAMonth = 2592000000L;
        long numberOfMonth = differenceInCurrentAndStarting/millisecondsInAMonth;


        return ( startingDateTimestamp + ( millisecondsInAMonth * ( numberOfMonth + 1 ) ) );
    }

    public static String convertUTCtoLocal(String dateFormatInPut, String dateFomratOutPut, String datesToConvert) {

        String dateToReturn = datesToConvert;

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatInPut);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date gmt = null;

        SimpleDateFormat sdfOutPutToSend = new SimpleDateFormat(dateFomratOutPut);
        sdfOutPutToSend.setTimeZone(TimeZone.getDefault());

        try {
            gmt = sdf.parse(datesToConvert);
            dateToReturn = sdfOutPutToSend.format(gmt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToReturn; }

    public static String convertLocalToUTC(String dateFormat, long datesToConvert) {

        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return sdf.format(new Date(datesToConvert));
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

        int diffInSec = Math.abs((int) (diff / (SECONDS)));
        String difference = "";
        if (diffInSec < MINUTES) {
            difference = diffInSec +" "+ SECONDS_AGO;
        } else if ((diffInSec / HOUR) < 1) {
            difference = (diffInSec / MINUTES) +" "+ MINUTES_AGO;
        } else if ((diffInSec / DAY) < 1) {
            difference = (diffInSec / HOUR) +" "+ HOURS_AGO;
        } else if ((diffInSec / WEEK) < 1) {
            difference = (diffInSec / DAY) +" "+ DAYS_AGO;
        } else if ((diffInSec / MONTH) < 1) {
            difference = (diffInSec / WEEK) +" "+ WEEK_AGO;
        } else if ((diffInSec / YEAR) < 1) {
            difference = (diffInSec / MONTH) +" "+ MONTHS_AGO;
        } else {

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            difference = format_before.format(c.getTime());
        }

        return difference;
    }

    public static long getTimeDifferenceInMinutes(long timepast, boolean direction) {

        long current = System.currentTimeMillis();
        long different = 0;
        long secondsInMilli = 1000;
        CharSequence charSequence1 = "";

        if (direction) {
            different = current - timepast;
        } else {
            different = timepast - current;
        }

        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;
        long yearsInMilli = monthsInMilli * 12;

//        long elapsedYears = different / yearsInMilli;
//        different = different % yearsInMilli;
//
//        long elapsedMonths = different / monthsInMilli;
//        different = different % monthsInMilli;
//
//        if (elapsedYears > 0) {
//            return elapsedYears + "y";
//        } else if (elapsedMonths > 0) {
//            return elapsedMonths + "mo";
//        } else {
//
//            long elapsedDays = different / daysInMilli;
//            different = different % daysInMilli;
//
//            long elapsedHours = different / hoursInMilli;
//            different = different % hoursInMilli;
//
//            if (elapsedDays > 0) {
//                charSequence1 = elapsedDays + "d";
//                if (elapsedHours > 0) {
//                    charSequence1 = charSequence1 + (" " + elapsedHours + "h");
//                }
//                return charSequence1;
//            } else {

        long elapsedMinutes = different / minutesInMilli;
//                different = different % minutesInMilli;

//                if (elapsedHours > 0) {
//                    charSequence1 = elapsedHours + "h";
//                    if (elapsedMinutes > 0) {
//                        charSequence1 = charSequence1 + (" " + elapsedMinutes + "m");
//                    }
//                    return charSequence1;
//                } else if (elapsedMinutes > 0) {
//                    return elapsedMinutes + "m";
//                }
//                else {
//                    long elapsedSeconds = different / secondsInMilli;
//                    return elapsedSeconds + "s";
//                }
//            }
//        }

        return elapsedMinutes > 0 ? elapsedMinutes : 0;
    }

    public static String getPreviousDate(String format, int numberOfDays) {

        DateFormat dateFormat = new SimpleDateFormat(format);

        //Substract one day to current date.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -numberOfDays);

        return dateFormat.format(cal.getTime());

    }

}