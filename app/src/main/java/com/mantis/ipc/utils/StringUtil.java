package com.mantis.ipc.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String forceReturnStr(String str) {
        return str == null ? "" : str;
    }

    public static String concatByComma(List<String> sources) {
        if(sources == null || sources.size() ==0 ){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sources.size(); i++) {
            if (sb.length() > 0) {//该步即不会第一位有逗号，也防止最后一位拼接逗号！
                sb.append(",");
            }
            sb.append(sources.get(i));
        }
        return sb.toString();
    }

    public static boolean isEmptyStr(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        }
        return false;
    }

    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static boolean isStrangerStr(String str) {
        if(isEmptyStr(str)) {
            return false;
        }

        return str.endsWith("@stranger");
    }


    public static boolean isWxId(String str) {
        if(isEmptyStr(str)) {
            return false;
        }
        return str.startsWith("wxid_");
    }

    public static boolean isPhoneNumber(String str) {
        if(isEmptyStr(str)) {
            return  false;
        }
        if(!Character.isDigit(str.charAt(0))) {
            return false;
        }
        String regExp = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[89])\\d{8}$";

        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isAllNumber(String str) {
        if(isEmptyStr(str)) {
            return  false;
        }
        if(!Character.isDigit(str.charAt(0))) {
            return false;
        }
        String regExp = "^[0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
