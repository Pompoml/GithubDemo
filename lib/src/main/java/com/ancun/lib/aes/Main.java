package com.ancun.lib.aes;

import java.io.UnsupportedEncodingException;

/**
 * desc:
 * date: 2018/4/2
 * author: ancun
 */

public class Main {

    public static void main(String args[]) {

        String value = "default";

        try {
            byte[] bytesValue = AesUtil.hexString2Bytes("b42392874720b86a80c9695c3691793f");
            if (bytesValue != null && bytesValue.length > 0) {
                value = new String(AesUtil.decodeAes(bytesValue), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("value = " + value);
        //com.viber.voip

    }

}
