package net.timxekhach.utility;

import net.timxekhach.operation.data.entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class XeReflectionUtils extends ReflectionUtils {


    @SuppressWarnings("unchecked")
    public static <E> E getField(Object o, String fieldName) {
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

    @SuppressWarnings("unchecked")
    public static <E> E invokeMethodByName(Object obj, String methodName, Object... params) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if(method.getName().equals(methodName)) {
                try {
                    return (E) method.invoke(obj, params);
                } catch (Exception e) {
                    LoggerFactory.getLogger(XeReflectionUtils.class).debug("cannot find method: " + methodName);
                }
            }
        }
        return null;
    }

}
