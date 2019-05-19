package top.aleaf.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 郭新晔
 */
public class DateUtils {
    private static final long ONE_SECOND = 1;
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;
    private static final long ONE_MONTH = 30 * ONE_DAY;
    private static final long ONE_YEAR = 12 * ONE_MONTH;

    private static final long TEN = 10;

    /**
     * 时
     */
    public static final String HH = "HH时";
    /**
     * 年
     */
    public static final String YYYY = "yyyy";
    /**
     * 月日
     */
    public static final String MM_DD = "MM-dd";
    /**
     * 年月日
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 年月日
     */
    public static final String YYYY_MM_DD_Z = "yyyy年MM月dd日";
    /**
     * 年月日 时分秒
     */
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param format 格式
     * @return yyyy年MM月dd HH:mm
     * MM-dd HH:mm 2018-12-25
     */
    public static String formatDate(String format, Date date) {
        SimpleDateFormat simple = new SimpleDateFormat(format);
        return simple.format(date);
    }

    /**
     * 时间描述
     *
     * @param date 需要描述的date
     * @return 字符串描述
     */
    public static String dateDesc(Date date) {
        long time = date.getTime() / 1000;
        long now = System.currentTimeMillis() / 1000;
        long ago = now - time;
        if (ago <= ONE_MINUTE) {
            return ago / ONE_SECOND + "秒前";
        } else if (ago <= ONE_HOUR) {
            return ago / ONE_MINUTE + "分钟前";
        } else if (ago <= ONE_DAY) {
            return ago / ONE_HOUR + "小时前";
        } else if (isYesterday(date)) {
            return String.format("昨天 %s", formatDate(HH, date));
        } else if (ago <= ONE_WEEK) {
            return String.format("%d天前 %s", ago / ONE_DAY, formatDate(HH, date));
        } else if (isSameYear(date)) {
            return formatDate(MM_DD, date);
        } else {
            return formatDate(YYYY_MM_DD_Z, date);
        }
    }

    private static boolean isYesterday(Date date) {
        //昨天
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(date);
        yesterday.add(Calendar.DATE, -1);

        return formatDate(YYYY_MM_DD, date).equals(formatDate(YYYY_MM_DD, yesterday.getTime()));
    }

    private static boolean isSameYear(Date date) {
        return formatDate(YYYY, date).equals(formatDate(YYYY, new Date()));
    }

    /**
     * 小于十布零
     *
     * @param num 数字
     * @return 结果
     */
    private static String fillZero(int num) {
        if (num < TEN) {
            return String.format("0%d", num);
        }
        return String.valueOf(num);
    }

}