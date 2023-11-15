package com.ispl.ekalarogya.training.utils;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String SERVER_FORMAT2 = "dd MMM yyyy";

    public static final String DATE_FORMAT_WITH_2_DIGIT_YEAR = "dd MMM yy";
    public static final long ONE_DAY_TIME = 86400000;
    public static final String SQL_DATE_TYPE_FORMAT = "yyyy-MM-dd";
    private static final String TAG = DateUtils.class.getSimpleName();
    private static final String TODAY = "Today";
    private static final String TOMORROW = "Tomorrow";
    private static final String YESTERDAY = "Yesterday";
    private static final long oneDateTime = 86400000;
    private static final long sixDaysTime = 518400000;
    public static final String BOOKING_SERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BOOKING_DEVICE_DATE_FORMAT = "EEE, MMM dd, hh:mm aa";
    public static final String EARNING_DATE_FORMAT = "dd/MM/yy";
    public static final String NEW_REQUEST_DATE_FORMAT = "MMMM dd, yy";
    public static final String BOOKING_TIME_FORMAT = "HH:mm:ss";
    public static final String BOOKING_TIME_DISPLAY_FORMAT = "hh:mm aa";
    public static final String MONTH_FORMAT = "MMMM";
    public static final String SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CHAT_DISPLAY_DATE_FORMAT = "hh:mm a";
    public static final String ORDER_DATE_FORMAT = "MMM dd yyyy";
    public static final String AUDIO_RECORD_DATE_TIME_FORMAT = "yyyy-MM-dd_HH:mm:ss";
    public static final String CHAT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String CONVERSATION_TIME_FORMAT = "hh:mm aa";
    public static final String CONVERSATION_DATE_FORMAT = "dd-MM-yyyy";
    public static final String PROJECT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CONVERSATION_HEADER_DATE_FORMAT = "dd MMMM yyyy";
    public static final String CONVERSATION_IMAGE_PREVIEW_DATE_FORMAT = "dd MMMM, HH:mm";
    public static final String DATE_FORMATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }


    public static String getDateAndTime(Long timestamp, String dateFormat) {
        Date date = new Date(timestamp);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return fullDateFormat.format(date);
    }


    public static String getDateForServer(long timestamp) {
        return new SimpleDateFormat(SQL_DATE_TYPE_FORMAT, Locale.getDefault()).format(new Date(timestamp));
    }

    public static ArrayList<String> getCurrentSixDays() {
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dateList.add(getFullTime(Long.valueOf(getCurrentTimeStamp()), ((long) i) * 86400000));
        }
        return dateList;
    }
    public static String parseStringDate(String dateToConvert, String inputDateFormat, String outputDateFormat) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat, Locale.getDefault());
        try {
            Date date = inputFormat.parse(dateToConvert);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateToConvert;
        }
    }

    public static  ArrayList<String> getFirstWeekDays(int currentMonth,int currentYear)
    {

        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 1; i < 8; i++)
        {
            dateList.add(DateUtils.parseDate((i+"-"+currentMonth+"-"+currentYear),"dd-MM-yyyy","yyyy-MM-dd"));

        }
        return dateList;
    }
    public static  ArrayList<String> getSecondWeekDays(int currentMonth,int currentYear)
    {

        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 1; i < 8; i++)
        {
            dateList.add(DateUtils.parseDate(((i+7)+"-"+currentMonth+"-"+currentYear),"dd-MM-yyyy","yyyy-MM-dd"));

        }
        return dateList;
    } public static  ArrayList<String> getThirdWeekDays(int currentMonth,int currentYear)
    {

        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 1; i < 8; i++)
        {
            dateList.add(DateUtils.parseDate(((i+14)+"-"+currentMonth+"-"+currentYear),"dd-MM-yyyy","yyyy-MM-dd"));

        }
        return dateList;
    }public static  ArrayList<String> getFourthWeekDays(int currentMonth,int currentYear)
    {

        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 1; i < 8; i++)
        {
            dateList.add(DateUtils.parseDate(((i+21)+"-"+currentMonth+"-"+currentYear),"dd-MM-yyyy","yyyy-MM-dd"));

        }
        return dateList;
    }
    public static String getOnlyDate(String actualDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("dd", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String month_name = month_date.format(date);
        System.out.println("Month :" + month_name);

        return month_name;
    }
    public static String getOnlyMonth(String actualDate) {
        SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String month_name = month_date.format(date);
        System.out.println("Month :" + month_name);

        return month_name;
    }
    public static String getFullTime(Long timestamp, long increment) {
        return new SimpleDateFormat(SQL_DATE_TYPE_FORMAT, Locale.getDefault()).format(new Date(new Date(timestamp.longValue()).getTime() + increment));
    }

    public static long getWeekTimeStamp(long currentTimeStamp) {
        Date newDate = new Date(currentTimeStamp - (6 * 86400000)); //6 * 24 * 60 * 60 * 1000 - week
        return newDate.getTime();
    }

    public static String convertDateFormat(long dateToConvert, String dateFormat) {
        Date date = new Date(dateToConvert);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return fullDateFormat.format(date);
    }

    public static String getTodayDate() {
        Date date = new Date(getCurrentTimeStamp());
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Logger.ErrorLog("getTodayDate", fullDateFormat.format(date));
        return fullDateFormat.format(date);
    }

    public static String getTodayDateFromMonth(int monthNumber) {

        if(monthNumber< 10){

            Date date = new Date(getCurrentTimeStamp());

            SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-"+"0"+monthNumber+"-01", Locale.getDefault());
            Logger.ErrorLog("getTodayDate", fullDateFormat.format(date));
            return fullDateFormat.format(date);

        }else {
            Date date = new Date(getCurrentTimeStamp());

            SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-"+monthNumber+"-01", Locale.getDefault());
            Logger.ErrorLog("getTodayDate", fullDateFormat.format(date));
            return fullDateFormat.format(date);
        }
    }

    public static String parseDate(String dateToConvert, String dateFormat, String convertDateFormat) {
        if (TextUtils.isEmpty(dateToConvert))
            return dateToConvert;
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat defaultFormatter = new SimpleDateFormat(convertDateFormat, Locale.getDefault());
        SimpleDateFormat serverFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            Date date = serverFormatter.parse(dateToConvert);
            return defaultFormatter.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToConvert;

    }


    public static String getDateWithIncrement(long increment, String dateFormat) {
        Date newDate = new Date(new Date(getCurrentTimeStamp()).getTime() + increment);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Logger.ErrorLog("getDateWithIncrement", fullDateFormat.format(newDate));
        return fullDateFormat.format(newDate);
    }

    public static String getMonth(int monthId) {
        String month = "";
        switch (monthId) {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return month;
        }
    }


    private static String getDayType(String date) {
        String dateType = "";
        if (date.equals(getTodayDate())) {
            dateType = TODAY;
        }
        Logger.ErrorLog("getDayType", dateType);
        return dateType;
    }


    public static Date getParsedDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static boolean isTodayDate(long timestamp) {
        Logger.ErrorLog("Is today date : ", "  - " + (timestamp == getCurrentTimeStamp()));
        return getFullTime(Long.valueOf(timestamp), 0).equals(getFullTime(Long.valueOf(getCurrentTimeStamp()), 0));
    }

    public static long getLongDateTimeMills(int dateJump) {
        return ((long) dateJump) * 86400000;
    }

    public static long convertStringDateToMilliSecond(String stringDate, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(stringDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getTimeDifference(long fromDate, long toDate) {
        long days = (toDate - fromDate) / 86400000;
        Logger.ErrorLog(TAG, "Time Difference in days : " + days);
        return days;
    }

    public static String convertBookingDate(String dateToConvert, String dateFormat, String convertDateFormat) {
        if (TextUtils.isEmpty(dateToConvert))
            return dateToConvert;
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat defaultFormatter = new SimpleDateFormat(convertDateFormat, Locale.getDefault());
        SimpleDateFormat serverFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

        try {
            Date date = serverFormatter.parse(dateToConvert);
            return defaultFormatter.format(date.getTime());


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToConvert;

    }

    public static int[] getCurrentDateTimeAfterSplitting(long dateToConvert, String delimiter, int arraySize) {
        int[] dateTime = new int[arraySize];
        if (dateToConvert == 0)
            dateToConvert = getCurrentTimeStamp();
        Date date = new Date(dateToConvert);
        SimpleDateFormat fullDateFormat = new SimpleDateFormat(SQL_DATE_TYPE_FORMAT, Locale.getDefault());
        Logger.DebugLog(TAG, "Date : " + fullDateFormat.format(date));
        String[] splittedDate = fullDateFormat.format(date).split(delimiter);
        if (splittedDate.length > 0) {
            for (int i = 0; i < splittedDate.length; i++) {
                Logger.DebugLog(TAG, "Split Date : " + splittedDate[i]);
                dateTime[i] = Integer.parseInt(splittedDate[i]);
            }
        }

        return dateTime;
    }

    public static long getPreviousOneMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        return date.getTime();
    }

    public static long convertDateIntoLong(String dateToConvert, String dateFormat) {
        if (TextUtils.isEmpty(dateToConvert))
            return 0;
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat defaultFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

        try {
            Date date = defaultFormatter.parse(dateToConvert);
            return date.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }


    public static boolean isYesterday(String date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            Date d = simpleDateFormat.parse(date);
            return android.text.format.DateUtils.isToday(d.getTime() + android.text.format.DateUtils.DAY_IN_MILLIS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isToday(String date_time, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date date = null;
        try {
            date = (Date) formatter.parse(date_time);
            return android.text.format.DateUtils.isToday(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getDateForConversation(String dateToConvert, String dateFormat, String convertDateFormat) {
        if (TextUtils.isEmpty(dateToConvert))
            return dateToConvert;

        SimpleDateFormat defaultFormatter = new SimpleDateFormat(convertDateFormat,Locale.getDefault());
        SimpleDateFormat serverFormatter = new SimpleDateFormat(dateFormat,Locale.getDefault());

        try {
            Date date = serverFormatter.parse(dateToConvert);
            String convertedDate =  defaultFormatter.format(date.getTime());
            if (isToday(convertedDate, convertDateFormat)) {
                return "Today";
            } else if (isYesterday(convertedDate, convertDateFormat)) {
                return "Yesterday";
            } else {
                return convertedDate;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateToConvert;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getAllTotalTime(int additionalHour, int additionalMinute, int additionalSeconds){

        LocalTime time = LocalTime.of(0, 0, 0);

        // Add hours, minutes, or seconds
        LocalTime newTime = time.plusHours(additionalHour).plusMinutes(additionalMinute).plusSeconds(additionalSeconds);

        System.out.println(newTime);


        return newTime.toString();
    }
    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        return month+1;
    }


}
