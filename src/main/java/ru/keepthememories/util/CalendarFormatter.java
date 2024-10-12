package ru.keepthememories.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarFormatter {

    static private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Calendar parse(String date) {
        if (date == null)
            return null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(date));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Calendar calendar) {
        return format.format(calendar.getTime());
    }

}
