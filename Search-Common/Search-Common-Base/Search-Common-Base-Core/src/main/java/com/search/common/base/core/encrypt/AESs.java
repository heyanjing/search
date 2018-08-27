package com.search.common.base.core.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by heyanjing on 2018/3/5 15:47.
 */
public class AESs {
    public static final String AES_KEY = "UThGCh2P989si+gCfRKN8A==";
    public static final String AES_KEY_ENCODE = "Dd4cKgCZLNH8hf+E6P6/oxCA4Ib36UCNcfs8XBHBzj6nw/88KAX0NE25t8uKIAJE40gx34I+HMA6b/hQfBJ6T4QanVMr+sEXlFlvMIJn88nvrD1fir0nixdxhS5X4FgbaYGTe3o1rXf8Fn8y3WQq7Wk//vxTkxsMaWHmK2JrBho=";
    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final String ENCRYPT_WAY = "AES/ECB/PKCS5Padding";

    public static SecretKey getSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        return keyGenerator.generateKey();
    }

    public static String getKeyBase64String() throws Exception {
        return Base64.encodeBase64String(getSecretKey().getEncoded());
    }

    public static byte[] getKeyByteArr() throws Exception {
        return getSecretKey().getEncoded();
    }

    /**
     * 加密
     *
     * @param content         内容
     * @param keyBase64String base64加密后的aeskey
     * @return 加密后的base64字符串
     * @throws Exception Exception
     */

    public static String encode(String content, String keyBase64String) throws Exception {
        Key key = new SecretKeySpec(Base64.decodeBase64(keyBase64String), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(content.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    public static String encode(String content, byte[] keyByteArr) throws Exception {
        Key key = new SecretKeySpec(keyByteArr, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(content.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    public static String encode(String content) throws Exception {
        return encode(content, AES_KEY);
    }

    /**
     * 解密
     *
     * @param base64Content   base64加密后的内容
     * @param keyBase64String base64加密后的aeskey
     * @return 解密后的字符串
     * @throws Exception Exception
     */
    public static String dencode(String base64Content, String keyBase64String) throws Exception {
        Key key = new SecretKeySpec(Base64.decodeBase64(keyBase64String), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(base64Content));
        return new String(bytes);
    }

    public static String dencode(String base64Content, byte[] keyByteArr) throws Exception {
        Key key = new SecretKeySpec(keyByteArr, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(ENCRYPT_WAY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(base64Content));
        return new String(bytes);
    }

}
