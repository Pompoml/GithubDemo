package com.ancun.lib;

import java.lang.reflect.Field;

/**
 * desc:
 * date: 2018/1/3
 * author: ancun
 */

public class Main {

    public static void main(String args[]) {

        for (int i = 0; i < 26; i++) {
            char tmp = (char) ('a' + i);
            try {
                Field field = h.class.getField(String.valueOf(tmp));
                System.out.println(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 26; i++) {
            char tmp = (char) ('A' + i);
            try {
                Field field = h.class.getField(String.valueOf(tmp));
                System.out.println(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 58; j++) {
                int a = ('a' + i);
                int b = ('A' + j);

                if (b > 90 && b < 97) {
                    continue;
                }


                String tmp = (char) a + "" + (char) b;

                //System.out.println(tmp);

                try {
                    Field field = h.class.getField(tmp);
                    System.out.println(field.getName() + " : " + field.get(null));
                } catch (Exception e) {
                    //e.printStackTrace();
                }

            }

        }


    }

}
