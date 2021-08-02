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

}
