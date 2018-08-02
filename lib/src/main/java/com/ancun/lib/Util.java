package com.ancun.lib;

/**
 * desc:
 * date: 2018/3/22
 * author: ancun
 */

public class Util {
    private static byte[] a = new byte[]{65, 66, 69, 78, 50, 48, 49, 50, 48, 57, 51, 48, 49, 56, 51, 48};
    private static final byte[] b;

    public static String a(String var0) {
        try {
            byte[] var10000 = var0.getBytes("iso-8859-1");
            int var2 = var10000.length;
            byte[] var10 = var10000;
            byte[] var1 = new byte[var2 * 3 / 4];
            int var3 = 0;
            byte[] var4 = new byte[4];
            int var5 = 0;
            int var6 = 0;

            while(true) {
                label38: {
                    if(var6 < var2) {
                        byte var7 = (byte)(var10[var6] & 127);
                        byte var8;
                        if((var8 = b[var7]) < -5) {
                            var10000 = null;
                            break;
                        }

                        if(var8 < -1) {
                            break label38;
                        }

                        var4[var5++] = var7;
                        if(var5 <= 3) {
                            break label38;
                        }

                        var3 += a(var4, var1, var3);
                        var5 = 0;
                        if(var7 != 61) {
                            break label38;
                        }
                    }

                    var10 = new byte[var3];
                    System.arraycopy(var1, 0, var10, 0, var3);
                    var10000 = var10;
                    break;
                }

                ++var6;
            }

            var1 = a;
            var10 = var10000;

            for(var2 = 0; var2 < var1.length; ++var2) {
                var10 = a(var10, var1[var2]);
            }

            return new String(var10, "utf-8");
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    private static byte[] a(byte[] var0, byte var1) {
        try {
            byte[] var2 = new byte[var0.length];

            for(int var3 = 0; var3 < var0.length; ++var3) {
                var2[var3] = (byte)(var0[var3] ^ var1);
            }

            return var2;
        } catch (Exception var4) {
            return null;
        }
    }

    private static int a(byte[] var0, byte[] var1, int var2) {
        int var4;
        if(var0[2] == 61) {
            var4 = (b[var0[0]] & 255) << 18 | (b[var0[1]] & 255) << 12;
            var1[var2] = (byte)(var4 >>> 16);
            return 1;
        } else if(var0[3] == 61) {
            var4 = (b[var0[0]] & 255) << 18 | (b[var0[1]] & 255) << 12 | (b[var0[2]] & 255) << 6;
            var1[var2] = (byte)(var4 >>> 16);
            var1[var2 + 1] = (byte)(var4 >>> 8);
            return 2;
        } else {
            try {
                var4 = (b[var0[0]] & 255) << 18 | (b[var0[1]] & 255) << 12 | (b[var0[2]] & 255) << 6 | b[var0[3]] & 255;
                var1[var2] = (byte)(var4 >> 16);
                var1[var2 + 1] = (byte)(var4 >> 8);
                var1[var2 + 2] = (byte)var4;
                return 3;
            } catch (Exception var3) {
                return -1;
            }
        }
    }

    static {
        byte[] var10000 = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        b = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
    }
}
