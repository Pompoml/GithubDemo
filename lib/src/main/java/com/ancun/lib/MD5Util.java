package com.ancun.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class MD5Util {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 根据路径获取单个文件的MD5值！
     *
     * @param path 文件路径
     * @return
     */
    public static String getFileMD5(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return null;
        }

        return getFileMD5(file);
    }

    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        String md5 = null;
        try {
            FileInputStream in = new FileInputStream(file);
            md5 = getFileMD5(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 获取流的MD5值！
     *
     * @param
     * @return
     */
    public static String getFileMD5(InputStream in) {
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        String md5 = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            md5 = bytesToHexString(digest.digest());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 不足32位，前面补零
        if (md5.length() < 32) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int index = 0; index < 32 - md5.length(); index++) {
                stringBuffer.append("0");
            }
            stringBuffer.append(md5);
            md5 = stringBuffer.toString();
        }

        return md5;
    }

    /**
     * 获取文件夹中文件的MD5值
     *
     * @param file
     * @param listChild ;true递归子目录中的文件
     * @return
     */
    public static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        // <filepath,md5>
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getDirMD5(f, listChild));
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

    /**
     * 待转换成16进制字符串的bytes
     *
     * @param
     * @return 返回转换成16进制字符串的bytes
     */
    public static String bytesToHexString(byte[] bytes) {
        return bytesToHexString(bytes,false);
    }

    public static String bytesToHexString(byte[] bytes,boolean isUpper) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (isUpper) {
                hex = hex.toUpperCase();
            }
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String encode(String str) {
        return encode(str,false);
    }

    public static String encode(byte[] bytes) {
        return encode(bytes,false);
    }

    public static String encode(String str,boolean isUpper) {
        String md5Key;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            md5Key = bytesToHexString(mDigest.digest(),isUpper);
        } catch (Exception e) {
            return null;
        }
        return md5Key;
    }

    public static String encode(byte[] bytes,boolean isUpper) {
        String md5Key;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(bytes);
            md5Key = bytesToHexString(mDigest.digest(),isUpper);
        } catch (Exception e) {
            return null;
        }
        return md5Key;
    }
}