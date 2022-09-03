package com.sekarre.helpcenternotification.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static String getDateTimeFormatted(LocalDateTime toFormatDateTime) {
        return toFormatDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
