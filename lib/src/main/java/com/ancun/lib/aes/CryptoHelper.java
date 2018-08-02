package com.ancun.lib.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密、解密方法封装
 * Created by fengbing_dian91 on 2015/12/14.
 */
public class CryptoHelper {
    /**
     * 进行AES加密,AES/CBC/PKCS5Padding模式
     *
     * @param source 所要加密的数据
     * @param key    密钥
     * @param iv     iv向量
     * @return AES加密结果
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] aesEncryptBytes(byte[] source, byte[] key, byte[] iv) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException,
            IllegalBlockSizeException {
        return aesEncryptBytes(source, key, iv, null);
    }

    /**
     * 进行AES加密
     *
     * @param source     所要加密的数据
     * @param key        密钥
     * @param iv         iv向量
     * @param cipherType 模式，为空时使用：AES/CBC/PKCS5Padding模式
     * @return AES加密结果
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] aesEncryptBytes(byte[] source, byte[] key, byte[] iv, String cipherType)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (cipherType == null || cipherType.isEmpty()) {
            cipherType = "AES/CBC/PKCS5Padding";
        }
        Cipher cipher = Cipher.getInstance(cipherType);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(source);
    }

    /**
     * AES解密，使用utf-8编码，AES/CBC/PKCS5Padding模式
     *
     * @param source AES加密后的密文，使用Base64Decoder转化后的byte数组
     * @param key    密钥
     * @param iv     iv向量
     * @return 返回解密后的byte数组
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] aesDecryptBytes(byte[] source, byte[] key, byte[] iv) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException,
            IllegalBlockSizeException {
        return aesDecryptBytes(source, key, iv, null);
    }

    /**
     * AES解密
     *
     * @param source     AES加密后的密文，使用Base64Decoder转化后的byte数组
     * @param key        密钥
     * @param iv         iv向量
     * @param cipherType 模式，为空是，默认使用AES/CBC/PKCS5Padding
     * @return 返回解密后的byte数组
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] aesDecryptBytes(byte[] source, byte[] key, byte[] iv, String cipherType)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (cipherType == null || cipherType.isEmpty()) {
            cipherType = "AES/CBC/PKCS5Padding";
        }
        Cipher cipher = Cipher.getInstance(cipherType);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(source);
    }

    //endregion

    //region DES模式的加密、解密方法

    /**
     * DES的加密算法，iv使用key的前8个字符，使用utf-8编码
     *
     * @param source 待加密的字符串
     * @param key    密钥
     * @return 加密结果
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public static String desEncrypt(String source, String key)
            throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
            IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException,
            InvalidKeySpecException {
        return desEncrypt(source, key, key.substring(0, 8));
    }

    /**
     * DES加密算法，使用utf-8编码
     *
     * @param source 待加密的字符串
     * @param key    密钥
     * @param iv     iv向量
     * @return 加密结果
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public static String desEncrypt(String source, String key, String iv) throws NoSuchPaddingException,
            BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return desEncrypt(source, key, iv, "utf-8");
    }


    /**
     * DES加密算法
     *
     * @param source      待加密的字符串
     * @param key         密钥
     * @param iv          iv向量
     * @param charsetName 字符集编码，为空或null时，默认使用utf-8
     * @return 加密结果
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeySpecException
     */
    public static String desEncrypt(String source, String key, String iv, String charsetName)
            throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException {
        if (charsetName == null || charsetName.isEmpty()) {
            charsetName = "utf-8";
        }

        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charsetName));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(charsetName));

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return toHexString(cipher.doFinal(source.getBytes(charsetName)));
    }

    /**
     * DES的解密方法，iv使用key的前8个字符，使用utf-8编码
     *
     * @param source 使用des加密的密文
     * @param key    密钥
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static String desDecrypt(String source, String key) throws NoSuchPaddingException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        return desDecrypt(source, key, key.substring(0, 8));
    }

    /**
     * DES的解密方法，使用utf-8编码
     *
     * @param source 使用des加密的密文
     * @param key    密钥
     * @param iv     iv向量
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public static String desDecrypt(String source, String key, String iv) throws NoSuchPaddingException,
            BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return desDecrypt(source, key, iv, "utf-8");
    }

    /**
     * DES的解密方法
     *
     * @param source      使用des加密的密文
     * @param key         密钥
     * @param iv          iv向量
     * @param charsetName 字符集编码，为空或null时，默认使用utf-8
     * @return 解密后的字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeySpecException
     */
    public static String desDecrypt(String source, String key, String iv, String charsetName)
            throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException {
        if (charsetName == null || charsetName.isEmpty()) {
            charsetName = "utf-8";
        }
        byte[] sourceBytes = convertHexString(source);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charsetName));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(charsetName));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return new String(cipher.doFinal(sourceBytes));
    }

    //endregion

    //region 3DES模式的加密、解密 方法

    /**
     * 3DES模式的加密算法，iv使用key的前8个字符，使用utf-8编码
     *
     * @param source 待加密的字符串
     * @param key    密钥
     * @return 加密结果
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeySpecException
     */
    public static String tripleDesEncrypt(String source, String key) throws NoSuchPaddingException,
            InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return tripleDesEncrypt(source, key, key.substring(0, 8));

    }

    /**
     * 3DES模式的加密算法，使用utf-8编码
     *
     * @param source 待加密的字符串
     * @param key    密钥
     * @param iv     iv
     * @return 加密结果
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static String tripleDesEncrypt(String source, String key, String iv) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return tripleDesEncrypt(source, key, iv, "utf-8");
    }

    /**
     * 3DES模式的加密算法
     *
     * @param source      待加密的字符串
     * @param key         密钥
     * @param iv          iv
     * @param charsetName 字符集编码，为空或null时，默认使用utf-8
     * @return 加密结果
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String tripleDesEncrypt(String source, String key, String iv, String charsetName)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (charsetName == null || charsetName.isEmpty()) {
            charsetName = "utf-8";
        }

        Cipher cipher = Cipher.getInstance("DESEDE/CBC/PKCS5Padding");

        DESedeKeySpec desedeKeySpec = new DESedeKeySpec(key.getBytes(charsetName));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESEDE");
        SecretKey secretKey = keyFactory.generateSecret(desedeKeySpec);

        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(charsetName));

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return toHexString(cipher.doFinal(source.getBytes(charsetName)));
    }

    /**
     * 3DES的解密方法，iv使用key的前8个字符，使用utf-8编码
     *
     * @param source 使用3des加密的密文
     * @param key    密钥
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static String tripleDesDecrypt(String source, String key) throws NoSuchPaddingException,
            InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return tripleDesDecrypt(source, key, key.substring(0, 8));
    }

    /**
     * 3DES的解密方法，使用utf-8编码
     *
     * @param source 使用3des加密的密文
     * @param key    密钥
     * @param iv     iv
     * @return 解密后的字符串
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public static String tripleDesDecrypt(String source, String key, String iv) throws NoSuchPaddingException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return tripleDesDecrypt(source, key, iv, "utf-8");
    }

    /**
     * 3DES的解密方法
     *
     * @param source      使用3des加密的密文
     * @param key         密钥
     * @param iv          iv
     * @param charsetName 字符集编码，为空或null时，默认使用utf-8
     * @return 解密后的字符串
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String tripleDesDecrypt(String source, String key, String iv, String charsetName)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (charsetName == null || charsetName.isEmpty()) {
            charsetName = "utf-8";
        }
        byte[] srcBytes = convertHexString(source);
        Cipher cipher = Cipher.getInstance("DESEDE/CBC/PKCS5Padding");
        DESedeKeySpec desedeKeySpec = new DESedeKeySpec(key.getBytes(charsetName));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESEDE");
        SecretKey secretKey = keyFactory.generateSecret(desedeKeySpec);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(charsetName));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return new String(cipher.doFinal(srcBytes));
    }

    //endregion

    /**
     * 将字符串转化byte[]，用于des和3des的加密、解密
     *
     * @param ss 字符串
     * @return 转化后的byte[]结果
     */
    private static byte[] convertHexString(String ss) {
        byte[] digest = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 将byte[]转化成String，用于des和3des的加密、解密
     *
     * @param b 待转化发byte数组
     * @return 转化后的字符串
     */
    private static String toHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    /**
     * 进行HMAC_Sha1加密
     *
     * @param source 待加密的字符串
     * @param key    密钥
     * @return 返回加密结果
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] hash_HMAC_Sha1(String source, String key) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeyException {
        byte[] byteKeys = key.getBytes("utf-8");

        SecretKey secretKey = new SecretKeySpec(byteKeys, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(secretKey);

        byte[] byteSource = source.getBytes("utf-8");

        return mac.doFinal(byteSource);
    }
}