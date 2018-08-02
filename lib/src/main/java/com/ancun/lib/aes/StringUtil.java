package com.ancun.lib.aes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kangbixing_dian91 on 2016/4/19.
 */
public class StringUtil {

    /**
     *
     * @Title: stringToInt
     * @Description: String转换成int
     *
     * @param str
     * @return
     * @throws
     */
    public static int stringToInt(String str) {
        int n = 0;
        try {
            String s = nullToString(str);
            if (s.equals("")) {
                return n;
            } else {
                n = Integer.parseInt(str);
                return n;
            }
        } catch (Exception e) {
            return n;
        }
    }

    /**
     *
     * @Title: nullToString
     * @Description: 把null 转换成 ""字符串
     * @param str
     * @return
     * @throws
     */
    public static String nullToString(String str) {
        if (str == null || str.trim().equals("")) {
            return "";
        } else {
            return str;
        }
    }


    /**
     * 是否是空字符串
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return (str == null || str.trim().length() == 0);
    }


    public static boolean isEmptyList(Collection data) {

        if (null == data || data.size() == 0)
            return true;

        return false;
    }

    /**
     * 在data中index的位置是否是无效的
     * @param data 数据
     * @param index 位置
     * @return boolean
     */
    public static boolean isInvalidIndex(List data, int index) {

        if (isEmptyList(data) || data.size() <= index)
            return true;
        return false;
    }


    /**
     * 判断字符串内容是否为数字
     *
     * @param content
     * @return
     */
    public static boolean isDigital(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (!Character.isDigit(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 手机号码是否有效
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileValid(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(177)|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);

        return m.matches();
    }

    /**
     * 编码url
     */
    public static String encodeContentForUrl(String content) {
        try {
            return (content == null ? "" : URLEncoder.encode(content, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 编码url
     */
    public static String decodeContentForUrl(String content) {
        try {
            return (content == null ? "" : URLDecoder.decode(content, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 是否仅包含字母和汉字
     *
     * @param text
     * @return
     */
    public static boolean isLetterOrChinese(String text) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]+$";
        return text.matches(regex);
    }

    public static int toInt(String str) {

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float toFloat(String str){
       try{

           return Float.parseFloat(str);
       } catch (Exception e){
           return 0.0f;
       }
    }

    public static long toLong(String str){

        try{
            return Long.parseLong(str);
        }catch (Exception  e){
            return 0l;
        }
    }


    public static String toLowerCase(String str) {

        if (isEmptyString(str))
            return str;
        return str.toLowerCase();
    }

    public static String toUpperCase(String str) {

        if (isEmptyString(str))
            return str;
        return str.toUpperCase();
    }
    public static String format(String format, Object value){

        return String.format(Locale.ENGLISH, format, value);
    }


/**
 *  将所有的数字、字母及标点全部转为全角字符
 *  at 2016/10/13 11:52
 *  by ancun
 */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 从字节数组到十六进制字符串转换
    public static String bytes2HexString(byte[] b) {
        byte[] hex = "0123456789abcdef".getBytes();
        byte[] buff = new byte[2 * b.length];
        for (int i = 0; i < b.length; i++) {
            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
            buff[2 * i + 1] = hex[b[i] & 0x0f];
        }
        String str = null;
        try {
            str = new String(buff, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
