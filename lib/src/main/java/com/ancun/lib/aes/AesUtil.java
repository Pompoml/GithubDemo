package com.ancun.lib.aes;

import java.io.UnsupportedEncodingException;

/**
 * desc:
 * date: 2018/4/2
 * author: ancun
 */

public class AesUtil  {
    /** key. */
    private static byte[] key = {43, 29, 49, 98, 106, 92, 47, 76, 123, 22, 110, 13, 22, 34, 125, 62};
    /** iv. */
    private static byte[] iv = {77, 66, 87, 110, 27, 46, 18, 58, 97, 123, 95, 29, 67, 20, 97, 94};

    /** 编码格式. */
    public static final String CHARSET_NAME = "UTF-8";

    /**
     * 编码
     *
     * @param input 需要编码的数据
     * @return 密码
     */
    public static byte[] encodeAes(byte[] input) {
        try {
            return CryptoHelper.aesEncryptBytes(input, key, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码
     *
     * @param input 需要解码的数据
     * @return 源码
     */
    public static byte[] decodeAes(byte[] input) {
        try {
            return CryptoHelper.aesDecryptBytes(input, key, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从字节数组到十六进制字符串转换
     *
     * @param b 需要转换的值
     * @return 16进制的字符串
     */
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

    //

    /**
     * 从十六进制字符串到字节数组转换
     *
     * @param hexstr 需要转成16进制的值
     * @return byte数组
     */
    public static byte[] hexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }


    /**
     * @param c 需要转换的字符
     * @return int
     */
    private static int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }

    public static String encodeCleanAesToHexString(String input){
        try {
            byte[] result =  AesUtil.encodeAes(input.getBytes("UTF-8"));
            return StringUtil.bytes2HexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
