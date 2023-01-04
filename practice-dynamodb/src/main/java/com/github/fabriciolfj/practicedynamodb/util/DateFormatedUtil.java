package com.github.fabriciolfj.practicedynamodb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatedUtil {

    public static String toDateIso() {
        var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        var date = new Date();
        return sdf.format(date);
    }
}
