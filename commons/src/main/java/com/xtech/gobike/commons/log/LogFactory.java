package com.xtech.gobike.commons.log;

public class LogFactory {
    private static Log log = new Log();
    public static Log getLog(Class tagClass){
        return log;
    }
}
