package com.njq.common.util.date;

import com.njq.common.util.string.StringUtil2;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class BaseDate {
    public static final DateTimeFormatter DEFAULT_HOUR_MINUTE_FORMATTER = DateTimeFormat.forPattern("HH:mm");
    private static final long serialVersionUID = -3098985139095632110L;
    private static final String DEFAULT_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_STRING = "yyyy-MM-dd";
    public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public BaseDate() {
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate convertDateToLocalDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String dateFormat(String sdate) {
        return dateFormat(sdate, "yyyy-MM-dd");
    }

    public static String dateFormatT(String sdate) {
        return dateFormat(sdate, "yyyyMMddHHmmss");
    }

    public static String dateFormat(String sdate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static long getIntervalDays(String sd, String ed) {
        return (java.sql.Date.valueOf(ed).getTime() - java.sql.Date.valueOf(sd).getTime()) / 86400000L;
    }

    public static int getInterval(String beginMonth, String endMonth) {
        int intBeginYear = Integer.parseInt(beginMonth.substring(0, 4));
        int intBeginMonth = Integer.parseInt(beginMonth.substring(beginMonth.indexOf("-") + 1));
        int intEndYear = Integer.parseInt(endMonth.substring(0, 4));
        int intEndMonth = Integer.parseInt(endMonth.substring(endMonth.indexOf("-") + 1));
        return (intEndYear - intBeginYear) * 12 + (intEndMonth - intBeginMonth) + 1;
    }

    public static Date getDate(String sDate, String dateFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
        ParsePosition pos = new ParsePosition(0);
        return fmt.parse(sDate, pos);
    }

    public static String getCurrentYear() {
        return getFormatCurrentTime("yyyy");
    }

    public static String getCurrentMonth() {
        return getFormatCurrentTime("MM");
    }

    public static String getCurrentDay() {
        return getFormatCurrentTime("dd");
    }

    public static String getCurrentYM() {
        return getFormatCurrentTime("yyyy-MM");
    }

    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd");
    }

    public static String getFormatDate(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    public static String getCurrentTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatTime(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getFormatCurrentTime(String format) {
        return getFormatDateTime(new Date(), format);
    }

    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date).trim();
    }

    public static Date getDateObj(int year, int month, int day) {
        Calendar c = new GregorianCalendar();
        c.set(year, month - 1, day);
        return c.getTime();
    }

    public static Date getDateObj(String args, String split) {
        String[] temp = args.split(split);
        int year = new Integer(temp[0]);
        int month = new Integer(temp[1]);
        int day = new Integer(temp[2]);
        return getDateObj(year, month, day);
    }

    public static String formatDate(Date theDate) {
        Locale locale = Locale.CHINA;
        String dateString = "";
        if (theDate == null) {
            return "";
        } else {
            try {
                try {
                    Calendar cal = Calendar.getInstance(locale);
                    cal.setTime(theDate);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
                    dateString = dateFormatter.format(cal.getTime());
                } catch (Exception var8) {
                    System.out.println("test result:" + var8.getMessage());
                }

                return dateString;
            } finally {
                ;
            }
        }
    }

    public static Date getDateFromString(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resDate = null;

        try {
            resDate = sdf.parse(dateStr);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resDate;
    }

    public static Date getDateObj() {
        Calendar c = new GregorianCalendar();
        return c.getTime();
    }

    public static String getTimestamp() {
        Calendar c = new GregorianCalendar();
        return String.valueOf(c.getTime().getTime());
    }

    public static int getDaysOfCurMonth() {
        int curyear = new Integer(getCurrentYear());
        int curMonth = new Integer(getCurrentMonth());
        int[] mArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (curyear % 400 == 0 || curyear % 100 != 0 && curyear % 4 == 0) {
            mArray[1] = 29;
        }

        return mArray[curMonth - 1];
    }

    public static int getDaysOfCurMonth(final String time) {
        String[] timeArray = time.split("-");
        int curyear = new Integer(timeArray[0]);
        int curMonth = new Integer(timeArray[1]);
        int[] mArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (curyear % 400 == 0 || curyear % 100 != 0 && curyear % 4 == 0) {
            mArray[1] = 29;
        }

        return curMonth == 12 ? mArray[0] : mArray[curMonth - 1];
    }

    public static int getDayofWeekInMonth(String year, String month, String weekOfMonth, String dayOfWeek) {
        Calendar cal = new GregorianCalendar();
        int y = new Integer(year);
        int m = new Integer(month);
        cal.clear();
        cal.set(y, m - 1, 1);
        cal.set(8, new Integer(weekOfMonth));
        cal.set(7, Integer.parseInt(dayOfWeek));
        return cal.get(5);
    }

    public static int getDayOfWeek(String year, String month, String day) {
        Calendar cal = new GregorianCalendar(new Integer(year), new Integer(month) - 1, new Integer(day));
        return cal.get(7);
    }

    public static int getWeekOfYear(String year, String month, String day) {
        Calendar cal = new GregorianCalendar();
        cal.clear();
        cal.set(new Integer(year), new Integer(month) - 1, new Integer(day));
        return cal.get(3);
    }

    public static Date getDateAdd(Date date, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(5, amount);
        return cal.getTime();
    }

    public static String getFormatDateAdd(Date date, int amount, String format) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(5, amount);
        return getFormatDateTime(cal.getTime(), format);
    }

    public static String getFormatCurrentAdd(int amount, String format) {
        Date d = getDateAdd(new Date(), amount);
        return getFormatDateTime(d, format);
    }

    public static String getFormatYestoday(String format) {
        return getFormatCurrentAdd(-1, format);
    }

    public static String getYestoday(String sourceDate, String format) {
        return getFormatDateAdd(getDateFromString(sourceDate, format), -1, format);
    }

    public static String getFormatTomorrow(String format) {
        return getFormatCurrentAdd(1, format);
    }

    public static String getFormatDateTommorrow(String sourceDate, String format) {
        return getFormatDateAdd(getDateFromString(sourceDate, format), 1, format);
    }

    public static String getCurrentDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(cal.getTime()).trim();
    }

    public static String getCurDate() {
        GregorianCalendar gcDate = new GregorianCalendar();
        int year = gcDate.get(1);
        int month = gcDate.get(2) + 1;
        int day = gcDate.get(5);
        int hour = gcDate.get(11);
        int minute = gcDate.get(12);
        int sen = gcDate.get(13);
        String y = Integer.toString(year);
        String m;
        if (month < 10) {
            m = "0" + Integer.toString(month);
        } else {
            m = Integer.toString(month);
        }

        String d;
        if (day < 10) {
            d = "0" + Integer.toString(day);
        } else {
            d = Integer.toString(day);
        }

        String h;
        if (hour < 10) {
            h = "0" + Integer.toString(hour);
        } else {
            h = Integer.toString(hour);
        }

        String n;
        if (minute < 10) {
            n = "0" + Integer.toString(minute);
        } else {
            n = Integer.toString(minute);
        }

        String s;
        if (sen < 10) {
            s = "0" + Integer.toString(sen);
        } else {
            s = Integer.toString(sen);
        }

        return "" + y + m + d + h + n + s;
    }

    public static String getCurTimeByFormat(String format) {
        Date newdate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(newdate);
    }

    public static long getDiff(String startTime, String endTime) {
        long diff = 0L;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = simpleDateFormat.parse(startTime);
            Date endDate = simpleDateFormat.parse(endTime);
            diff = startDate.getTime() - endDate.getTime();
            diff /= 1000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return diff;
    }

    public static String getHour(long second) {
        long hour = second / 60L / 60L;
        long minute = (second - hour * 60L * 60L) / 60L;
        long sec = second - hour * 60L * 60L - minute * 60L;
        return hour + "小时" + minute + "分钟" + sec + "秒";
    }

    public static String getDateTime(long microsecond) {
        return getFormatDateTime(new Date(microsecond), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByAddFltHour(float flt) {
        int addMinute = (int) (flt * 60.0F);
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(12, addMinute);
        return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByAddFltHour(String dateStr, float flt) {
        int addMinute = (int) (flt * 60.0F);
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
        cal.add(12, addMinute);
        return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByMinusFltHour(String dateStr, float flt) {
        int addMinute = -((int) (flt * 60.0F));
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
        cal.add(12, addMinute);
        return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByMinusFlt(String dateStr, int flt) {
        int addMinute = -flt;
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDateFromString(dateStr, "yyyy-MM-dd HH:mm:ss"));
        cal.add(12, addMinute);
        return getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDateByAddMinite(String datetime, int minute) {
        String returnTime = null;
        GregorianCalendar cal = new GregorianCalendar();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(datetime);
            cal.setTime(date);
            cal.add(12, minute);
            returnTime = getFormatDateTime(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        return returnTime;
    }

    public static Date getDateByAddMinite(Date datetime, int minute) {
        GregorianCalendar cal = new GregorianCalendar();

        try {
            cal.setTime(datetime);
            cal.add(12, minute);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return cal.getTime();
    }

    public static Date getDateByAddHour(Date date, int hour) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(11, hour);
        return cal.getTime();
    }

    public static int getIntHour(String endTime, String startTime) {
        long diff = 0L;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date endDate = simpleDateFormat.parse(endTime);
            Date startDate = simpleDateFormat.parse(startTime);
            diff = endDate.getTime() - startDate.getTime();
            diff /= 3600000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return (new Long(diff)).intValue();
    }

    public static float getDiffHour(String endTime, String startTime) {
        float diff = 0.0F;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date endDate = ft.parse(endTime);
            Date startDate = ft.parse(startTime);
            diff = (float) (endDate.getTime() - startDate.getTime());
            diff /= 3600000.0F;
        } catch (ParseException var6) {
            var6.printStackTrace();
        }

        return diff;
    }

    public static int getDiffHour(Date startTime, Date endTime) {
        long diff = 0L;
        diff = endTime.getTime() - startTime.getTime();
        diff /= 3600000L;
        return (new Long(diff)).intValue();
    }

    public static Date getDateFromString(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date resDate = null;

        try {
            resDate = sdf.parse(dateStr);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resDate;
    }

    public static String getDateByCurrent(int days) {
        Calendar cal = new GregorianCalendar();
        cal.add(5, days);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(cal.getTime());
    }

    public static String getStringToSting(String date, String fromat1, String format2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat1);
        Date resDate = sdf.parse(date);
        sdf = new SimpleDateFormat(format2);
        return sdf.format(resDate).trim();
    }

    public static void main(String[] args) throws Exception {
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        String y = "2013-11-05";
        String z = "2013-12-06";
        Date yy = sdf.parse(y);
        Date zz = sdf.parse(z);
        System.out.println(Calendar.getInstance().get(11));
        int d = (int) ((zz.getTime() - yy.getTime()) / 86400000L);
        System.out.println(d);
    }

    public static String findChineseDayOfWeek(int dayOfWeek) {
        String[] days = new String[]{"", "一", "二", "三", "四", "五", "六", "日"};
        if (dayOfWeek >= 1 && dayOfWeek <= 7) {
            return "周" + days[dayOfWeek];
        } else {
            throw new IllegalArgumentException(StringUtil2.format("一周的第几天不能是:{0}", new Object[]{dayOfWeek}));
        }
    }

    public static DateTime roundDateTime(DateTime dateTime) {
        if (dateTime.minuteOfHour().get() >= 10 && dateTime.minuteOfHour().get() <= 40) {
            dateTime = dateTime.withTime(dateTime.getHourOfDay(), 30, 0, 0);
        } else if (dateTime.minuteOfHour().get() < 10) {
            dateTime = dateTime.withTime(dateTime.getHourOfDay(), 0, 0, 0);
        } else {
            dateTime = dateTime.plusHours(1);
            dateTime = dateTime.withTime(dateTime.getHourOfDay(), 0, 0, 0);
        }

        return dateTime;
    }
}
