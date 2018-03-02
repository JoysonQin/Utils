package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Created by changlu on 1/21/18.
 */
public class LocalDateUtil {

    private static final DateTimeFormatter yearAndMonth = DateTimeFormatter.ofPattern("yyyy-MM");

    private static final DateTimeFormatter yearAndMonthAndDay = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ZoneId zone = ZoneId.systemDefault();

    public static String dateToYearAndMonth(Date date){
        LocalDate localDate = dateToLocalDate(date);
        return dateToStandardYearAndMonth(localDate);
    }

    public static String dateToYearAndMonth(LocalDate localDate){
        return localDate.format(yearAndMonth);
    }

    public static Date localDateToDate(LocalDate localDate){
        Date date = Date.from(localDate.atStartOfDay(zone).toInstant());
        return date;
    }

    public static LocalDate dateToLocalDate(Date date){
        return date.toInstant().atZone(zone).toLocalDate();
    }

    public static String dateToStandardYearAndMonth(Date date){
        LocalDate localDate = dateToLocalDate(date);
        return dateToStandardYearAndMonth(localDate);
    }

    public static String dateToStandardYearAndMonth(LocalDate localDate){
        StringBuffer s = new StringBuffer();
        s.append(localDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase());
        s.append("-");
        s.append(localDate.getYear()%100);
        return s.toString();
    }

    public static Date dateToLastDayOfMonth(Date date){
        LocalDate localDate = dateToLocalDate(date);
        return dateToLastDayOfMonth(localDate);
    }

    public static Date dateToLastDayOfMonth(LocalDate localDate){
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.lengthOfMonth());
        return localDateToDate(date);
    }

    public static Date getDefaultDate(){
        LocalDateTime localDateTime = LocalDateTime.of(1970,1,8,0,0,0);
        return Date.from(localDateTime.atZone(zone).toInstant());
    }

    public static String format(Date start) {
        return dateToLocalDate(start).format(yearAndMonthAndDay);
    }
}
