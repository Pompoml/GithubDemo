package com.ancun.lib;

/**
 * desc:
 * date: 2017/12/26
 * author: ancun
 */

public class TestClass {

    public TestClass() {
    }

    public int getIndexByChance(int first, int second, int third) {
        int total = first + second + third;

        int index = 0;
        int random = RandomUtil.getRandom().nextInt(99999) % total;
        // int random = new Random().nextInt(total);

        if (random >= 0 && random < first) {
            index = 0;
        } else if (random >= first && random < first + second) {
            index = 1;
        } else if (random >= first + second && random < total) {
            index = 2;
        }
        return index;
    }

}
