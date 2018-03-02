package utils;

import com.lianjia.plats.store.link.utils.entity.DateUtilsException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 本地dateUtil(仅适合本项目使用)
 *
 * @summary 本地dateUtil(仅适合本项目使用)
 * @author: dangdandan
 * @Copyright (c) 2017, Lianjia Group All Rights Reserved.
 * @since: 2017年07月29日 15:29:25
 */
public class DateLocalUtils {

    public final static SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd");
    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public final static SimpleDateFormat Y_M_D_H_M_S_F = new SimpleDateFormat(Y_M_D_H_M_S);


    /**
     * 获取之前的月份
     * <p>
     * 返回格式如：201709
     *
     * @param months
     * @return
     */
    public static String getBeforeMonthDate(int months) {
        if (months > 0) {
            months = -months;
        }

        return LocalDate.now().plusMonths(months).toString("yyyyMM");
    }

    /**
     * 获取前n月第一天
     *
     * @param months
     * @return string 20170101000000（yyyyMMddHHmmss）
     */
    public static String getBeforeMonthTime(int months) {
        return getBeforeMonthDate(months) + "01000000";
    }

    /**
     * 获取某一天的最后一秒
     *
     * @param paramDate
     * @return
     */
    public static Date getEndTimeOfDay(Date paramDate) {
        if (Objects.isNull(paramDate)) {
            return null;
        }

        LocalDate today = LocalDate.fromDateFields(paramDate);

        return new LocalDateTime(today.getYear(), today.getMonthOfYear(), today.getDayOfMonth(),
                23, 59, 59, 999).toDate();
    }

    /**
     * 获取某一天的第一秒
     *
     * @param paramDate
     * @return
     */
    public static Date getBeginTimeOfDay(Date paramDate) {
        if (Objects.isNull(paramDate)) {
            return null;
        }

        LocalDate today = LocalDate.fromDateFields(paramDate);
        return today.toDate();
    }

    /**
     * 获取两个日期相隔的月份
     *
     * @param beginDate
     * @param endDate
     * @return
     * @throws DateUtilsException
     */
    public static Integer monthsBetween(Date beginDate, Date endDate) throws DateUtilsException {

        if (Objects.isNull(beginDate) || Objects.isNull(endDate)) {
            throw new DateUtilsException("入参有误, 有空值");
        }

        return Months.monthsBetween(new DateTime(beginDate), new DateTime(endDate)).getMonths();
    }

    /**
     * 获取两个时间相隔的秒数
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws DateUtilsException
     */
    public static Integer secondsBetween(DateTime beginTime, DateTime endTime) throws DateUtilsException {
        return Seconds.secondsBetween(beginTime, endTime).getSeconds();
    }

    /**
     * 获取日期的年
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static boolean yearEquals(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            throw new DateUtilsException("入参有误, 有空值");
        }

        return getYearByDate(date1) == getYearByDate(date2);
    }

    /**
     * 将制定日期格式化成指定格式
     * @param date 需要进行格式化的日期
     * @param pattern 日期格式
     * @return 格式化后的日期时间字符串
     */
    public static String getDateTime(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = Y_M_D_H_M_S;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    /**
     * 将Object类型转换成String
     * @param obj
     * @return
     */
    public static String defaultEmptyStr(Object obj) {
        if (obj == null){
            return "";}
        return defaultStr(obj.toString(), "");
    }

    /**
     * 去除字符串首尾的空格
     * @param str
     * @param defaultStr
     * @return
     */
    public static String defaultStr(String str, String defaultStr) {
        return StringUtils.isEmpty(str) ? defaultStr : str.trim();
    }

    /**
     * 判断字符串是否为合法时间格式
     *
     * @param dateStr
     * @return
     */
    public static boolean isValidDate(String dateStr){
        try {
            Y_M_D.parse(dateStr);
            return true;
        } catch (ParseException e) {
            try {
                Y_M_D_H_M_S_F.parse(dateStr);
                return true;
            } catch (ParseException e1) {
                return false;
            }
        }
    }

    /**
     * 判断日期大小  返回 true: 前者大于等于后者  false: 后者大于前者
     *
     * @param previousDate
     * @param laterDate
     * @return
     */
    public static boolean compareDate(Date previousDate, Date laterDate){
        if (Objects.isNull(previousDate) || Objects.isNull(laterDate)) {
            throw new DateUtilsException("入参有误, 有空值");
        }
        return previousDate.getTime() >= laterDate.getTime();
    }
}
