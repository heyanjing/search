package com.search.common.base.core.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by heyanjing on 2018/3/5 14:33.
 */
public class RSAs {

    /**
     * 加密算法
     */
    public static final String KEY_ALGORITHM = "RSA";
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFLIG1iL84WyxyqVf9VsR3tlxbzjUJONH7GZEkIME0aHto87CZ/4wMna1Py2LL2VO99PtDyRIuu91vDBKEbMXuPrnM861UaYJwSAru9CYXfj/xYdy89mryFkQ5co52BOUJQrSLmzSgqa2Ow5tohW5FC/A7u0pUdcA/IA7x5Nh+tQIDAQAB";
    public static final String RSA_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUsgbWIvzhbLHKpV/1WxHe2XFvONQk40fsZkSQgwTRoe2jzsJn/jAydrU/LYsvZU730+0PJEi673W8MEoRsxe4+uczzrVRpgnBICu70Jhd+P/Fh3Lz2avIWRDlyjnYE5QlCtIubNKCprY7Dm2iFbkUL8Du7SlR1wD8gDvHk2H61AgMBAAECgYBjih21a23KGNPDLHJZKHaXKBYmhJ29s9u3fKewwk7G9mVvz92rbYfnkh+Nqe7BgUKHlEoOo4bUqZTcQMDCfG8ADYkrku8t/4+csIR1zep+ecyGQuhfTX+X8MPe8wOZ/WfmaGOXzlumc207+5rxStwSAj7ak585SXG+kPUOT1cNYQJBALzTmX8w9lnPn8HYJX5Es1IkRgmuBExC3X3sDPaRdeGirVuWzVeBzu2TvN3yAECpL6jNqGkRIktMPOJqni9aQ80CQQC0jKAQJWvcC72Jgs1LYVxKMdaL2XuT3NPjld6HAIMv5tXg0u+ard2uY6WQpjr5o0Aij+1WjToLFK36FLvKKQ6JAkAkST1sFcqhg6adp5284BLdrB7RaWFmDktmEWCEWXufupU5zobzU/yh5wwaUpD93AVOmQbN+f7vHV1t3TM8wknZAkAK0CVB8kvMaPOYCbpr2k/hraxwwbyx9VbHWZFzOmeg9c03Ysqw09rj99nRmPMNQmaz6zTGqzyfr0RgJt+8UsspAkB7KI0TxDVs8Q47XGzo7oG0I8s/WiQryDwfQQONpn++5YrhvNOh1TOuqK2ttY+0EyQ84nf8oWDFR71bA+x2F4F4";

    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        return keyPairGen.generateKeyPair();
    }

    public static String getPrivateKeyStr(RSAPrivateKey privateKey) {
        return Base64.encodeBase64String(privateKey.getEncoded());
    }

    public static String getPublicKeyStr(RSAPublicKey publicKey) {
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

    /**
     * 公钥加密
     *
     * @param content      原始内容
     * @param publicKeyStr 公钥base64字符串
     * @return
     * @throws Exception
     */
    public static String encodeByPublicKeyStr(String content, String publicKeyStr) throws Exception {
        return encodeByPublicKeyStr(content.getBytes(), publicKeyStr);
    }

    /**
     * 公钥加密
     *
     * @param byteArr      原始内容
     * @param publicKeyStr 公钥base64字符串
     * @return
     * @throws Exception
     */
    public static String encodeByPublicKeyStr(byte[] byteArr, String publicKeyStr) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(byteArr));
    }

    /**
     * 私钥解密
     *
     * @param base64Content 公钥加密后的base64
     * @param privateKeyStr 私钥base64字符串
     * @return 解密后的内容
     * @throws Exception Exception
     */
    public static String dencodeByPrivateKeyStr(String base64Content, String privateKeyStr) throws Exception {
        byte[] bytes = dencode2ByteArrByPrivateKeyStr(base64Content, privateKeyStr);
        return new String(bytes);
    }

    public static String dencodeByPrivateKeyStr() throws Exception {
        return dencodeByPrivateKeyStr(AESs.AES_KEY_ENCODE, RSA_PRIVATE_KEY);
    }

    /**
     * 私钥解密
     *
     * @param base64Content 公钥加密后的base64
     * @param privateKeyStr 私钥base64字符串
     * @return 解密后的内容
     * @throws Exception Exception
     */
    public static byte[] dencode2ByteArrByPrivateKeyStr(String base64Content, String privateKeyStr) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(Base64.decodeBase64(base64Content));
    }

    /**
     * 私钥加密
     *
     * @param content       内容
     * @param privateKeyStr 私钥base64字符串
     * @return 加密后的base64
     * @throws Exception Exception
     */
    public static String encodeByPrivateKeyStr(String content, String privateKeyStr) throws Exception {
        return encodeByPrivateKeyStr(content.getBytes(), privateKeyStr);
    }

    /**
     * 私钥加密
     *
     * @param byteArr       内容
     * @param privateKeyStr 私钥base64字符串
     * @return 加密后的base64
     * @throws Exception Exception
     */
    public static String encodeByPrivateKeyStr(byte[] byteArr, String privateKeyStr) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(byteArr));
    }

    /**
     * 公钥解密
     *
     * @param base64Content 私钥加密后的base64
     * @param publicKeyStr  公钥base64字符串
     * @return 解密后的内容
     * @throws Exception Exception
     */
    public static String dencodeByPublicKeyStr(String base64Content, String publicKeyStr) throws Exception {
        byte[] bytes = dencode2ByteArrByPublicKeyStr(base64Content, publicKeyStr);
        return new String(bytes);
    }

    /**
     * 公钥解密
     *
     * @param base64Content 私钥加密后的base64
     * @param publicKeyStr  公钥base64字符串
     * @return 解密后的内容
     * @throws Exception Exception
     */
    public static byte[] dencode2ByteArrByPublicKeyStr(String base64Content, String publicKeyStr) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(Base64.decodeBase64(base64Content));
    }


}
