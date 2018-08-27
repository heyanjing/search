package com.search.common.base.core.utils;

import com.search.common.base.core.encrypt.AESs;
import com.search.common.base.core.encrypt.RSAs;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created by heyanjing on 2018/3/1 10:32.
 */
public class AllTest {
    private static final Logger log = LoggerFactory.getLogger(AllTest.class);
    private static final String rsa_public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCFLIG1iL84WyxyqVf9VsR3tlxbzjUJONH7GZEkIME0aHto87CZ/4wMna1Py2LL2VO99PtDyRIuu91vDBKEbMXuPrnM861UaYJwSAru9CYXfj/xYdy89mryFkQ5co52BOUJQrSLmzSgqa2Ow5tohW5FC/A7u0pUdcA/IA7x5Nh+tQIDAQAB";
    private static final String aes_key = "UThGCh2P989si+gCfRKN8A==";
    /**
     * 公钥加密aes key的base64字符串
     */
    private static final String aes_key_encode = "Dd4cKgCZLNH8hf+E6P6/oxCA4Ib36UCNcfs8XBHBzj6nw/88KAX0NE25t8uKIAJE40gx34I+HMA6b/hQfBJ6T4QanVMr+sEXlFlvMIJn88nvrD1fir0nixdxhS5X4FgbaYGTe3o1rXf8Fn8y3WQq7Wk//vxTkxsMaWHmK2JrBho=";

    private static final String rsa_private = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIUsgbWIvzhbLHKpV/1WxHe2XFvONQk40fsZkSQgwTRoe2jzsJn/jAydrU/LYsvZU730+0PJEi673W8MEoRsxe4+uczzrVRpgnBICu70Jhd+P/Fh3Lz2avIWRDlyjnYE5QlCtIubNKCprY7Dm2iFbkUL8Du7SlR1wD8gDvHk2H61AgMBAAECgYBjih21a23KGNPDLHJZKHaXKBYmhJ29s9u3fKewwk7G9mVvz92rbYfnkh+Nqe7BgUKHlEoOo4bUqZTcQMDCfG8ADYkrku8t/4+csIR1zep+ecyGQuhfTX+X8MPe8wOZ/WfmaGOXzlumc207+5rxStwSAj7ak585SXG+kPUOT1cNYQJBALzTmX8w9lnPn8HYJX5Es1IkRgmuBExC3X3sDPaRdeGirVuWzVeBzu2TvN3yAECpL6jNqGkRIktMPOJqni9aQ80CQQC0jKAQJWvcC72Jgs1LYVxKMdaL2XuT3NPjld6HAIMv5tXg0u+ard2uY6WQpjr5o0Aij+1WjToLFK36FLvKKQ6JAkAkST1sFcqhg6adp5284BLdrB7RaWFmDktmEWCEWXufupU5zobzU/yh5wwaUpD93AVOmQbN+f7vHV1t3TM8wknZAkAK0CVB8kvMaPOYCbpr2k/hraxwwbyx9VbHWZFzOmeg9c03Ysqw09rj99nRmPMNQmaz6zTGqzyfr0RgJt+8UsspAkB7KI0TxDVs8Q47XGzo7oG0I8s/WiQryDwfQQONpn++5YrhvNOh1TOuqK2ttY+0EyQ84nf8oWDFR71bA+x2F4F4";

    @Test
    public void rsa4() throws Exception {
        String dencode = AESs.dencode("DPe0+0BwP1Wh9NZpBV1nmA==", RSAs.dencodeByPrivateKeyStr());
        log.info("{}", dencode);
    }

    @Test
    public void rsa3() throws Exception {
        String content = "要加密的内容";
        String encode = AESs.encode(content, aes_key);
        log.warn("{}", encode);
        String s = RSAs.dencodeByPrivateKeyStr(aes_key_encode, rsa_private);
        log.info("{}--{}", aes_key.equals(s), s);
        String dencode = AESs.dencode(encode, s);
        log.info("{}", dencode);
    }

    @Test
    public void rsa2() throws Exception {
        String content = "要加密的内容";
        KeyPair keyPair = RSAs.getKeyPair();
        String publicKeyStr = RSAs.getPublicKeyStr((RSAPublicKey) keyPair.getPublic());
        log.error("公钥--{}", publicKeyStr);
        String privateKeyStr = RSAs.getPrivateKeyStr((RSAPrivateKey) keyPair.getPrivate());
        log.error("私钥--{}", privateKeyStr);

        log.warn("{}", "aes加密原始内容，rsa加密aes的key");
        byte[] keyByteArr = AESs.getKeyByteArr();
        String aesKeyStr = Base64.encodeBase64String(keyByteArr);
        log.error("aes key 的字符串--{}", aesKeyStr);
        String encode = AESs.encode(content, aesKeyStr);
        log.info("aes加密content后的结果为---{}", encode);
        String keyBase64Str = RSAs.encodeByPublicKeyStr(aesKeyStr, publicKeyStr);
        log.info("rsa加密aes key 的字符串 的结果为---{}", keyBase64Str);

        log.info("{}", "解密");
        byte[] keyByteArr2 = RSAs.dencode2ByteArrByPrivateKeyStr(keyBase64Str, privateKeyStr);
        String xx = RSAs.dencodeByPrivateKeyStr(keyBase64Str, privateKeyStr);
        String dencode = AESs.dencode(encode, xx);
        log.info("解密后的结果---{}", dencode);

    }

    @Test
    public void rsa() throws Exception {
        KeyPair keyPair = RSAs.getKeyPair();
        String publicKeyStr = RSAs.getPublicKeyStr((RSAPublicKey) keyPair.getPublic());
        log.info("公钥--{}", publicKeyStr);
        String privateKeyStr = RSAs.getPrivateKeyStr((RSAPrivateKey) keyPair.getPrivate());
        log.info("私钥--{}", privateKeyStr);


        log.debug("{}", "私钥加密，公钥解密");
        String base64Content = RSAs.encodeByPrivateKeyStr("rsa非对称加密测试", privateKeyStr);
        log.info("{}", base64Content);
        String str = RSAs.dencodeByPublicKeyStr(base64Content, publicKeyStr);
        log.info("{}", str);
        log.debug("{}", "公钥加密，私钥解密");
        base64Content = RSAs.encodeByPublicKeyStr(str, publicKeyStr);
        log.info("{}", base64Content);
        str = RSAs.dencodeByPrivateKeyStr(base64Content, privateKeyStr);
        log.info("{}", str);


        log.debug("{}", "AES加密");
        String keyBase64String = AESs.getKeyBase64String();

        base64Content = AESs.encode(str, keyBase64String);
        log.info("{}", base64Content);
        str = AESs.dencode(base64Content, keyBase64String);
        log.info("{}", str);
    }

    @Test
    public void c2() throws Exception {

//        String baseurl="http://192.168.99.102:8000/main/api/login";
//        Map<String,String> params=new HashMap<>();
//        params.put("userName","市局管理员");
//        params.put("password","admin");
//        String s = HttpClientUtil.invokeHttp(HttpClientUtil.POST, baseurl, params);
//        String s1 = HttpClientUtil.invokeHttp(HttpClientUtil.POST, new URI(baseurl), params);
//        String s2 = HttpClientUtil.invokeHttp(HttpClientUtil.POST, "http", "192.168.99.102", 8000, "/main/api/login", params);
//        log.info("{}",s);
//        log.info("{}",s1);
//        log.info("{}",s2);
//        HttpClientUtil.invokeHttp(HttpClientUtil.GET, baseurl, params);


//        URI uri = new URI("http://192.168.99.102:8000/main/api/login");
//        log.info("{}",uri.toString());
//        log.info("{}",uri.getQuery());

//        String s = Configuration2.getString("sms.corpid");
//        log.info("{}", s);
    }

    @Test
    public void instance() throws Exception {
        log.info("{}", File.separator);
        String url = "http://192.168.70.110:8080/capm/alltypes/pageBySql";
        Map<String, Object> params = Guava.newHashMap();
        params.put("pageNumber", 1);
        params.put("pageSize", 1);

        String s = Https.get(url, params);
        log.info("{}", s);
        String post = Https.post(url, params);
        log.info("{}", post);

    }


    @Test
    public void test() throws Exception {
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL("http://192.168.99.102:8000/main/api/login");
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // 默认是 GET方式
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        //设置本次连接是否自动重定向
        connection.setInstanceFollowRedirects(true);
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
        String content1 = "userName=" + URLEncoder.encode("市局管理员", "utf-8");
        String content2 = "&password=" + URLEncoder.encode("admin", "utf-8");
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
        String content = content1 + content2;
        out.writeBytes(content);
        //流用完记得关
        out.flush();
        out.close();
        //获取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            sb.append(line);
        }
        log.info("{}", sb.toString());
        reader.close();
        //该干的都干完了,记得把连接断了
        connection.disconnect();
    }

}