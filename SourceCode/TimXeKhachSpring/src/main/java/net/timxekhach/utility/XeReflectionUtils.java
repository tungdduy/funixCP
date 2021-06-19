package net.timxekhach.utility;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class XeReflectionUtils extends ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <E> E getField(Object o, String fieldName, Class<E> clazz) {
        E result = null;
        try {
            Field field = ReflectionUtils.findField(o.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                result = (E) field.get(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
