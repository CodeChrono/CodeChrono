package com.codechrono.idea.plugin.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author CodeChrono
 * 日期格式化
 */
public class CustomDateFormat extends SimpleDateFormat {
    public CustomDateFormat(String pattern) {
        super(pattern);
    }

    private static final Map<String, SimpleDateFormat> TIME_FORMAT_MAP = new HashMap<>();

    static {
        TIME_FORMAT_MAP.put("DATE28", new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
        TIME_FORMAT_MAP.put("DATE14", new SimpleDateFormat("yyyyMMddHHmmss"));
        TIME_FORMAT_MAP.put("DATE12", new SimpleDateFormat("yyyyMMddHHmm"));
        TIME_FORMAT_MAP.put("DATE10", new SimpleDateFormat("yyyyMMddHH"));
        TIME_FORMAT_MAP.put("DATE8", new SimpleDateFormat("yyyyMMdd"));
        TIME_FORMAT_MAP.put("DATE6", new SimpleDateFormat("yyyyMM"));
        TIME_FORMAT_MAP.put("DATE4", new SimpleDateFormat("yyyy"));
    }


    @Override
    public Date parse(String source, ParsePosition pos) {
        if (source == null) {
            return null;
        }
        SimpleDateFormat sd = (SimpleDateFormat) TIME_FORMAT_MAP.get("DATE" + source.length());
        if (sd != null) {
            return sd.parse(source, pos);
        }
        return super.parse(source, pos);
    }

    @Override
    public Date parse(String source) {
        if (source == null) {
            return null;
        }
        try {
            SimpleDateFormat sd = (SimpleDateFormat) TIME_FORMAT_MAP.get("DATE" + source.length());
            if (sd != null) {
                return sd.parse(source);
            }
            return super.parse(source);
        } catch (Exception e) {
        }
        return null;
    }

    public Date parse28(String source) {
        if (source == null) {
            return null;
        }

        try {
            SimpleDateFormat sd = (SimpleDateFormat) TIME_FORMAT_MAP.get("DATE" + source.length());
            if (sd != null) {
                return sd.parse(source);
            }
            return super.parse(source);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CustomDate:Failed to parse date time string:  " + source);
        }

        // 假设CST代表的时区偏移量是固定的，比如中国标准时间（这实际上是不准确的，只是作为示例）
        // 对于准确的时区处理，应该使用TimeZone.getTimeZone("具体的时区ID")
        //TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai"); // 这里仅作为示例，实际上CST可能代表其他时区
/*
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        formatter.setTimeZone(timeZone); // 设置时区
        try {
            Date date = formatter.parse(source);
            System.out.println("Parsed Date: " + date);
            return date;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to parse date time string:  " + source);
        }

 */
        return null;
    }


}
