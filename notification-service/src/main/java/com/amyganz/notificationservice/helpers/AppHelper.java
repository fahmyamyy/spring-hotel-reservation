package com.amyganz.notificationservice.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppHelper {
    public static String convertSDF(Date date) {
//        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
