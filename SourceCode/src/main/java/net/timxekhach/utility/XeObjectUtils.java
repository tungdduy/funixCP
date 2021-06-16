package net.timxekhach.utility;

import java.lang.reflect.Field;

public class XeObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    public static String getStringValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj).toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * <pre>
     * obj or fieldName = null  -> true
     * fieldName = ""           -> true
     * fieldName = "  "         -> true
     * fieldName = null         -> true
     * fieldName = " f"         -> false
     * </pre>
     */
    public static boolean isFieldEmpty(Object obj, String fieldName) {
        return obj == null || fieldName == null || XeStringUtils.isNotBlank(XeObjectUtils.getStringValue(obj, fieldName));
    }


}
