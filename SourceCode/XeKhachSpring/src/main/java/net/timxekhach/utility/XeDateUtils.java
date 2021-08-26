package net.timxekhach.utility;

import net.timxekhach.operation.response.ErrorCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class XeDateUtils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static Date timeAppToApi(String time) {
        try {
            return TIME_FORMAT.parse(time);
        } catch (ParseException e) {
            ErrorCode.INVALID_TIME_FORMAT.throwNow();
        }
        return null;
    }

    public static Date dateAppToApi(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            ErrorCode.INVALID_TIME_FORMAT.throwNow();
        }
        return null;
    }

    public static Date dateTimeAppToApi(String date) {
        try {
            return DATE_TIME_FORMAT.parse(date);
        } catch (ParseException e) {
            ErrorCode.INVALID_TIME_FORMAT.throwNow();
        }
        return null;
    }
    public static boolean isMonday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.MONDAY;
    }

    public static boolean isTuesday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.TUESDAY;
    }
    public static boolean isWednesday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.WEDNESDAY;
    }

    public static boolean isThursday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.THURSDAY;
    }
    public static boolean isFriday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.FRIDAY;
    }

    public static boolean isSunday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.SUNDAY;
    }

    public static boolean isSaturday(final Date date) {
        final int day = XeDateUtils.getDayOfWeek(date);
        return day == Calendar.SATURDAY;
    }

    public static int getDayOfWeek(final Date date) {
        return XeDateUtils.getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    public static Calendar getCalendar(final Date date) {
        return XeDateUtils.getCalendar(date, 7);
    }

    public static Calendar getCalendar(final Date date, final int minimalDaysInFirstWeek) {
        final Calendar calendar = new GregorianCalendar();
        if (minimalDaysInFirstWeek != 0) {
            calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar;
    }

    public static String buildSearchString(Date date) {
        if(XeDateUtils.isSunday(date)) return "#SUN#";
        if(XeDateUtils.isMonday(date)) return "#MON#";
        if(XeDateUtils.isTuesday(date)) return "#TUE#";
        if(XeDateUtils.isWednesday(date)) return "#WED#";
        if(XeDateUtils.isThursday(date)) return "#THU#";
        if(XeDateUtils.isFriday(date)) return "#FRI#";
        if(XeDateUtils.isSaturday(date)) return "#SAT#";
        return "#";
    }

    public static Date getHoursAndMinutesOnly(final Date date) {
        if (date == null) {
            return null;
        }

        // Get an instance of the Calendar.
        final Calendar calendar = Calendar.getInstance();

        // Set the time of the calendar to the given date.
        calendar.setTime(date);

        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Return the date again.
        return calendar.getTime();
    }

    public static Date mergeDateAndTime(final Date date, final Date time) {
        if (date == null) {
            return time;
        }
        if (time == null) {
            return date;
        }

        final Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(date);

        final Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(time);

        secondCalendar.set(Calendar.YEAR, firstCalendar.get(Calendar.YEAR));
        secondCalendar.set(Calendar.MONTH, firstCalendar.get(Calendar.MONTH));
        secondCalendar.set(Calendar.DATE, firstCalendar.get(Calendar.DATE));

        return secondCalendar.getTime();
    }
}
