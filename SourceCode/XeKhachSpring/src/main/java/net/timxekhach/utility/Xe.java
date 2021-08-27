package net.timxekhach.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Xe {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    public static Logger staticLogger = LoggerFactory.getLogger("XeLogger");
}
