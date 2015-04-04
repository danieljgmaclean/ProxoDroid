package com.danieljgmaclean.proxodroid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by danieljgmaclean on 22/03/15.
 */
public class Utils {

    private static String couchDBIP = "http://192.168.2.4:5984";
    private static String couchDBName = "notifications";

    public static String getDateTime(){
        //ISO 8601 format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    public static String getCouchDBIP() {
        return couchDBIP;
    }

    public static void setCouchDBIP(String couchDBIP) {
        Utils.couchDBIP = couchDBIP;
    }

    public static String getCouchDBName() {
        return couchDBName;
    }

    public static void setCouchDBName(String couchDBName) {
        Utils.couchDBName = couchDBName;
    }
}
