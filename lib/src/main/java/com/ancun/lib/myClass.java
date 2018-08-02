package com.ancun.lib;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class myClass {


    public static void main(String args[]) {
        HashMap<String, String> urlMap = new HashMap();
        urlMap.put("utm_source", "test_source");
        urlMap.put("cpro", "cooldj");

        HashMap<String, String> headMap = new HashMap();
        headMap.put("lan", "zh-CN");
        headMap.put("rslt", "1440*2560");
        headMap.put("osv", "6.0.1");
        headMap.put("pid", "10000001");
        headMap.put("tz", "28800000");
        headMap.put("devid", "3bf032ad3c60b1ad");
        headMap.put("mt", "4");
        headMap.put("vc", "10202");
        headMap.put("cpu", "arm64-v8a");
        headMap.put("sv", "1.2.2");
        headMap.put("ts", "1514543510413");
        headMap.put("gid", "0");
        headMap.put("imei", "357219070578344");
        headMap.put("nt", "10");
        headMap.put("brand", "samsung");
        headMap.put("dm", "SM-G9350");


        String str = buildSign(urlMap, headMap, "238CFF366674B8887F45FF522442974D");

        System.out.print(str);


    }


    public static String buildSign(Map<String, String> urlParams, Map<String, String> headParams, String appKey) {
        StringBuffer sign = new StringBuffer();
        TreeMap<String, String> treeMap = new TreeMap(new Comparator<String>() {
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        treeMap.putAll(headParams);
        if (urlParams != null && !urlParams.isEmpty()) {
            treeMap.putAll(urlParams);
        }

        treeMap.putAll(headParams);
        Iterator it = treeMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (!isEmptyString(value)) {

                sign.append(key).append("=");
                sign.append(value);
                sign.append("&");
            }
        }

        sign.append("key=");
        sign.append(appKey);
        String value = MD5Util.encode(sign.toString());
        return value;
    }

    public static boolean isEmptyString(String str) {
        return (str == null || str.trim().length() == 0);
    }

}
