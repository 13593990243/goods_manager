package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guosx
 * @date 2021/2/4
 */
public class DateUtil {
    public static String getOrderNo(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        return format;
    }
    public static String getDateStr(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        return format;
    }
}
