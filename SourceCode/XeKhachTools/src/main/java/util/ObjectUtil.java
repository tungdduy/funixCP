package util;

import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class ObjectUtil extends ObjectUtils {
    public static boolean anyTrue(Boolean... objs) {
        if(objs == null) return false;
        for (Boolean obj : objs) {
            if(obj) return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static Function newInstanceFromClass() {
        return object -> {
            Class clazz = (Class) object;
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
}
