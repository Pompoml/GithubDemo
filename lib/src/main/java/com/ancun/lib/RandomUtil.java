package com.ancun.lib;

import java.util.Random;

/**
 * desc:
 * date: 2017/12/26
 * author: ancun
 */

public class RandomUtil {

    public static final Random rand = new Random(System.currentTimeMillis());

    public static Random getRandom() {
        return rand;
    }

}
