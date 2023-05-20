package com.xiaomi_mall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommUtils {
    /**
     * 获取当前日期时间的指定格式字符串表示
     * @param format 格式化字符串，如 "yyyy-MM/dd"
     * @return 当前日期时间的字符串表示
     */
    public static String getNowDateLongStr(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date now = new Date();
        return dateFormat.format(now);
    }
}
