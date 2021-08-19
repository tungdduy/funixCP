package net.timxekhach.utility;

import net.timxekhach.operation.response.ErrorCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XeDateUtils {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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
}
