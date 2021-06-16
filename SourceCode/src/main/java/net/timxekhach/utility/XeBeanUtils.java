package net.timxekhach.utility;

import net.timxekhach.security.XeExceptionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XeBeanUtils {
    public static XeExceptionListener exceptionListener;

    @Autowired
    public void setXeExceptionHandler(XeExceptionListener exceptionListener) {
        XeBeanUtils.exceptionListener = exceptionListener;
    }
}
