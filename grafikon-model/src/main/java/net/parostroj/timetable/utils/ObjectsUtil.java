package net.parostroj.timetable.utils;

public class ObjectsUtil {

    public static String checkAndTrim(String str) {
        if (str != null) {
            str = str.trim();
            if ("".equals(str)) {
                str = null;
            }
        }
        return str;
    }

    public static boolean compareWithNull(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        } else if (o1 != null && o1.equals(o2)) {
            return true;
        } else {
            return false;
        }
    }

}