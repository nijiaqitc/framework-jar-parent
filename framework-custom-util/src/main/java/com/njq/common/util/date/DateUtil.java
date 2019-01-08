package com.njq.common.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends BaseDate {

    private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat dateFormat6 = new SimpleDateFormat("HHmmMMdd");
    private static SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy/MM");
    private static SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyyMM");

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Timestamp getNowTimeForTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 返回 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String toDateString1(Timestamp date) {
        if (date != null) {
            return dateFormat1.format(date);
        } else {
            return null;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转换成yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String toStringString1(String date) {
        try {
            if (date != null && date.length() > 0) {
                Date dd = dateFormat4.parse(date);
                return toDateString1(dd);
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 返回 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String toDateString1(Date date) {
        if (date != null) {
            return dateFormat1.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toDateString2(Timestamp date) {
        if (date != null) {
            return dateFormat2.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toDateString2(Date date) {
        if (date != null) {
            return dateFormat2.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回HH:mm
     *
     * @param date
     * @return
     */
    public static String toDateString3(Timestamp date) {
        if (date != null) {
            return dateFormat3.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回HH:mm
     *
     * @param date
     * @return
     */
    public static String toDateString3(Date date) {
        if (date != null) {
            return dateFormat3.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toDateString4(Timestamp date) {
        if (date != null) {
            return dateFormat4.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toDateString4(Date date) {
        if (date != null) {
            return dateFormat4.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date toDate1(Date date) throws ParseException {
        if (date != null) {
            return dateFormat2.parse(dateFormat2.format(date));
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd
     *
     * @param date 必须传入yyyy-MM-dd格式的字符串
     * @return
     * @throws ParseException
     */
    public static Date toDate1(String date) throws ParseException {
        if (date != null && date.length() > 0) {
            return dateFormat2.parse(date);
        } else {
            return null;
        }
    }

    /**
     * 返回格式yyyy/MM/dd
     *
     * @param date
     * @return
     * @throws ParseException 2016-4-11 author njq
     */
    public static String toDateString5(Timestamp date) {
        if (date != null) {
            return dateFormat5.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回格式yyyy/MM/dd
     *
     * @param date
     * @return
     * @throws ParseException 2016-4-11 author njq
     */
    public static String toDateString5(Date date) {
        if (date != null) {
            return dateFormat5.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回格式HHmmMMdd
     *
     * @param date
     * @return
     */
    public static String toDateString6(Date date) {
        if (date != null) {
            return dateFormat6.format(date);
        } else {
            return null;
        }
    }

    /**
     * 将时间格式化为yyyy/mm
     *
     * @param date
     * @return
     */
    public static String toDateString7(Date date) {
        if (date != null) {
            return dateFormat7.format(date);
        } else {
            return null;
        }
    }

    /**
     * 格式化未yyyyMM
     *
     * @param date
     * @return
     */
    public static String toDateString8(Date date) {
        if (date != null) {
            return dateFormat8.format(date);
        } else {
            return null;
        }
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss
     *
     * @param date 必须传入yyyy-MM-dd格式的字符串
     * @return
     * @throws ParseException
     */
    public static Date toDate11(String date) throws ParseException {
        if (date != null && date.length() > 0) {
            return dateFormat4.parse(date);
        } else {
            return null;
        }
    }

    /**
     * 判断日期是否为空，如果为空返回当月第一天
     *
     * @param date传入日期
     * @return
     */
    public static Date isNullToFirstDay(Date start, Date end) {
        if (start == null) {
            if (end == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return calendar.getTime();
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                return calendar.getTime();
            }
        } else {
            return start;
        }

    }

    /**
     * 判断日期是否为空，如果为空返回当月最后一天
     *
     * @param date传入日期
     * @return
     */
    public static Date isNullToLastDay(Date start, Date end) {
        if (end == null) {
            if (start == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DATE, -1);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                return calendar.getTime();
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(start);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.DATE, -1);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                return calendar.getTime();
            }
        } else {
            return end;
        }
    }

    /**
     * 获取明天的时间
     *
     * @return 2016-1-6 author njq
     */
    public static Date getTromowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, +1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取加上n月后的时间开始点,返回yyyy-mm-dd
     *
     * @param date
     * @param number
     * @return
     */
    public static Date getNMonthDateSt(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return calendar.getTime();
    }

    /**
     * 获取加上n月后的时间结束点,返回yyyy-mm-dd
     *
     * @param date
     * @param number
     * @return
     */
    public static Date getNMonthDateEn(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
}
