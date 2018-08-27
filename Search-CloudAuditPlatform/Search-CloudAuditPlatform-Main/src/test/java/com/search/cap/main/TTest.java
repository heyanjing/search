package com.search.cap.main;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.search.cap.main.bean.demo.FieldBean;
import com.search.cap.main.bean.demo.TableBean;
import com.search.cap.main.bean.demo.TableInfoBean;
import com.search.cap.main.common.Commons;
import com.search.cap.main.entity.Users;
import com.search.cap.main.test.B;
import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.core.utils.SMS;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by heyanjing on 2018/2/10 9:39.
 */
@Slf4j
@SuppressWarnings({"unused"})
public class TTest {
    public static List<String> charList = Guava.newArrayList();
    private static Map<String, Users> userMap = new HashMap<>();

    @Before
    public void before() throws Exception {
        //userMap.put("jack", new Users("jack", "我是密码1", "我是盐1"));
        //userMap.put("tom", new Users("tom", "我是密码2", "我是盐2"));
        //userMap.put("jean", new Users("jean", "我是密码3", "我是盐3"));
        byte a = 81;
        log.info("{}", (char) a);

        for (int i = 51; i < 90; i++) {
            if ((i <= 57 || i >= 65) && i != 73 && i != 79) {
                charList.add(String.valueOf((char) i).toLowerCase());
            }
        }
        log.info("{}", charList);
        log.info("{}", charList.size());
    }

    @Test
    public void tesxt() {
        Map<String, Object> map = Guava.newHashMap();
        map.put("name", "A");
        map.put("age", 22);
        String json = Guava.toJson(map);

        Bean bean = new Bean();
        bean.setName("A");
        bean.setAge(22);
        String json1 = Guava.toJson(map);

        log.info("{}", json);
        log.info("{}", json1);

    }

    @Getter
    @Setter
    class Bean {
        private String name;
        private Integer age;

    }

    //public static String post(String urlStr, Map<String, Object> params, Charset charset) throws Exception {
    //URL url;
    //HttpURLConnection connection = null;
    //BufferedReader reader = null;
    //DataOutputStream out = null;
    //
    //try {
    //    url = new URL(urlStr);
    //    connection = (HttpURLConnection) url.openConnection();
    //    connection.setDoOutput(true);
    //    connection.setDoInput(true);
    //    connection.setRequestMethod("POST");
    //    connection.setUseCaches(false);
    //    connection.connect();
    //    out = new DataOutputStream(connection.getOutputStream());
    //    if (params != null) {
    //        out.writeBytes(toQueryString(params));
    //    }
    //    out.flush();
    //    out.close();
    //    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
    //    StringBuilder buffer = new StringBuilder();
    //    String line;
    //    while ((line = reader.readLine()) != null) {
    //        buffer.append(line);
    //    }
    //    return buffer.toString();
    //} catch (IOException e) {
    //    throw e;
    //} finally {
    //    if (out != null) {
    //        out.close();
    //    }
    //    if (reader != null) {
    //        reader.close();
    //    }
    //    if (connection != null) {
    //        connection.disconnect();
    //    }
    //}
    //}
    @Test
    public void t1() throws Exception {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        DataOutputStream out = null;

        try {
            URL url = new URL("http://192.168.99.102:8000/main/api/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            //if (params != null) {
            String xx = "password=" + URLEncoder.encode("admin", "utf-8");
            //out.writeBytes(xx);
            String xx1 = xx + "&userName=" + URLEncoder.encode("市局管理员", "utf-8");
            out.writeBytes(xx1);
            //}

            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("utf-8")));
            StringBuilder buffer = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String var9 = buffer.toString();
            log.info("{}", var9);
        } catch (IOException var13) {
            throw var13;
        } finally {
            if (out != null) {
                out.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (connection != null) {
                connection.disconnect();
            }

        }

        //String base="http://192.168.70.110/main/api/login";
        //Map<String, String> paramsx = Guava.newHashMap();
        //paramsx.put("userName", "市局管理员");
        //paramsx.put("password", "admin");
        //String post = HttpPlus.post(base, paramsx);
        //log.info("{}",post);
        //Map<String, Object> params2 = Guava.newHashMap();
        //params2.put("userName", "市局管理员");
        //params2.put("password", "admin");
        //String post2 = Https.post(base, params2);
        //log.info("{}",post2);

        //String base1="http://192.168.99.102:8000/main/api/login";
        //Map<String, String> paramsx1 = Guava.newHashMap();
        //paramsx1.put("userName", "市局管理员");
        //paramsx1.put("password", "admin");
        //String post1 = HttpPlus.post(base1, paramsx1);
        //log.info("{}",post1);
        //String base1="http://192.168.99.102:8000/main/api/login";
        //Map<String, Object> paramsx1 = Guava.newHashMap();
        //paramsx1.put("userName", "市局管理员");
        //paramsx1.put("password", "admin");
        //String post1 = Https.post(base1, paramsx1);
        //log.info("{}",post1);

        //String s = HttpClientTool.invokeHttpPost("http://192.168.99.102:8000/main/api/login", paramsx);
        //log.error("{}", s);
        //
        //String s1 = HttpRequestUtil.sendPost("http://192.168.99.102:8000/main/api/login", "?password=admin&userName=%E5%B8%82%E5%B1%80%E7%AE%A1%E7%90%86%E5%91%98", false);
        //log.warn("{}",s1);
        //String s2 = HttpRequestUtil.sendPost("http://192.168.99.102:8000/main/api/login", "?passwordx=admin&password=admin&userName=%E5%B8%82%E5%B1%80%E7%AE%A1%E7%90%86%E5%91%98", false);
        //log.warn("{}",s1);

        //long begintime = System.currentTimeMillis();
        //
        //URL url = new URL("http://192.168.70.110/main/api/login");
        //HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        //urlcon.setRequestProperty("Accept-Charset", "utf-8");
        //urlcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        //
        ////urlcon.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        ////urlcon.setRequestProperty("Accept-Charset", "utf-8");
        ////urlcon.setRequestProperty("contentType", "utf-8");
        ////connection.setRequestProperty("Content-Type", "application/json");
        ////connection.setRequestProperty("contentType", GpsConstants.ENCODING_UTF8);
        ////connection.setRequestProperty("Accept-Charset", GpsConstants.ENCODING_UTF8);
        //urlcon.setDoOutput(true);
        //urlcon.connect();         //获取连接
        //DataOutputStream out = new DataOutputStream(urlcon.getOutputStream());
        //out.writeBytes("?ps=admin&userName=%E5%B8%82%E5%B1%80%E7%AE%A1%E7%90%86%E5%91%98&password=ps");
        //out.flush();
        //out.close();
        //InputStream is = urlcon.getInputStream();
        //BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        //StringBuffer bs = new StringBuffer();
        //String l = null;
        //while ((l = buffer.readLine()) != null) {
        //    bs.append(l).append("/n");
        //}
        //System.out.println(bs.toString());
        //
        ////System.out.println(" content-encode："+urlcon.getContentEncoding());
        ////System.out.println(" content-length："+urlcon.getContentLength());
        ////System.out.println(" content-type："+urlcon.getContentType());
        ////System.out.println(" date："+urlcon.getDate());
        //
        //System.out.println("总共执行时间为：" + (System.currentTimeMillis() - begintime) + "毫秒");
        //
        //
        //String base = "http://192.168.99.102:8000";
        //base = "http://192.168.70.110";
        //
        //Map<String, Object> params = Guava.newHashMap();
        //params.put("userName", "市局管理员");
        //params.put("password", "admin");
        //
        //String post = Https.post(base + "/main/api/login", params);
        //log.info("{}", post);
        //log.info("{}","search_101_101_000_".length());

        //try {
        //    StringBuilder builder = new StringBuilder();
        //    String classPathFile = "/config/config.json";
        //    InputStreamReader reader = new InputStreamReader(Commons.class.getResourceAsStream(classPathFile), "UTF-8");
        //    BufferedReader bfReader = new BufferedReader(reader);
        //
        //    String tmpContent = null;
        //
        //    while ((tmpContent = bfReader.readLine()) != null) {
        //        builder.append(tmpContent);
        //    }
        //
        //    bfReader.close();
        //    log.info("{}", builder.toString().replaceAll( "/\\*[\\s\\S]*?\\*/", "" ));
        //
        //} catch (UnsupportedEncodingException e) {
        //    // 忽略
        //}

        //log.error("{}", Commons.readFileAsString("/config/config.json"));
        //URL url = this.getClass().getResource("/config/config.json");
        //File file = new File(url.getFile());
        //String s = FileUtils.readFileToString(file, "utf-8").replaceAll("/\\*[\\s\\S]*?\\*/", "");
        //log.warn("{}", s);
        //log.info("{}", Commons.phoneLast6("18423452585"));//320f97d3a78d3f2fee9fa26cd7e224d2
        //log.info("{}", Commons.idCardLast6("500223198909107097"));//320f97d3a78d3f2fee9fa26cd7e224d2
        //log.info("{}", Commons.getDefaultPasswordByPhone("18323015841"));//320f97d3a78d3f2fee9fa26cd7e224d2
        //log.info("{}", OrgTypes.getCjdw());
        //log.info("{}", Base64.decodeToString("bWFnbmV0Oj94dD11cm46YnRpaDphMWE0OWU0YWEzMmJmNzI0NTJkMTNjODc1NTg0ZWFjNWE1MDRhOWZkJmRuPUFQS0gtMDM5LjkwMGsubXA0"));
        //String[] arr = {"1", "2"};
        //System.out.println(arr);
    }

    @Test
    public void t() throws Exception {
        int width = 300;
        int height = 300;
        String format = "png";
        String content = "www.baidu.com";

        //定义二维码的参数
        HashMap hints = new HashMap();
        //编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距
        hints.put(EncodeHintType.MARGIN, 2);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            Path file = new File("D:/img.png").toPath();
            //注意：D:/files 目录必须存在，不会自动创建
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Test
    public void zip() throws Exception {

        List<Map<String, Object>> list = Guava.newArrayList();

        Map<String, Object> mapx1 = Guava.newHashMap();

        Map<String, Object> map1 = Guava.newHashMap();
        map1.put("a1", "a1");
        map1.put("b1", "b1");
        Map<String, Object> map2 = Guava.newHashMap();
        map2.put("a2", "a2");
        map2.put("b2", "b2");
        Map<String, Object> map3 = Guava.newHashMap();
        map3.put("a3", "a3");
        map3.put("b3", "b3");

        mapx1.put("map1", map1);
        mapx1.put("map2", map2);
        mapx1.put("map3", map3);

        Map<String, Object> mapx2 = Guava.newHashMap();

        Map<String, Object> map11 = Guava.newHashMap();
        map11.put("a11", "a1");
        map11.put("b11", "b1");
        Map<String, Object> map12 = Guava.newHashMap();
        map12.put("a12", "a12");
        map12.put("b12", "b12");
        Map<String, Object> map13 = Guava.newHashMap();
        map13.put("a13", "a13");
        map13.put("b13", "b13");

        mapx2.put("map11", map11);
        mapx2.put("map12", map12);
        mapx2.put("map13", map13);

        list.add(mapx1);
        list.add(mapx2);
        String json = Guava.toJson(list);
        log.info("{}", json);
        List<Map> maps = Guava.toList(json, Map.class);

        log.info("{}", maps);
        //FileUtils.copyURLToFile(new URL("http://image5.tuku.cn/pic/wallpaper/meinv/renbihuajiaominv/016.jpg"), new File(Capm.Upload.ROOT, "xx.jpg"));

        //String[] arr = {"/nodedev/he/merger/2018/04/13/f2.jpg", "/nodedev/he/merger/2018/04/13/f1.jpg"};
        //log.info("{}", Arrays.asList(arr));
        //List<String> params = Arrays.asList(arr);
        //try {
        //    String zipName = Commons.getZipName();
        //    File zipFile = new File(Capm.Upload.TEMP, zipName);
        //    ZipOutputStream zipOS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        //    zipOS.setMethod(ZipOutputStream.DEFLATED);
        //    byte[] buffer = new byte[1024];
        //    for (String spath : params) {
        //        File realyFile = new File(Capm.Upload.ROOT + spath);
        //        FileInputStream fis = new FileInputStream(realyFile);
        //        zipOS.putNextEntry(new ZipEntry(realyFile.getName()));
        //        int len;
        //        // 读入需要下载的文件的内容，打包到zip文件
        //        while ((len = fis.read(buffer)) > 0) {
        //            zipOS.write(buffer, 0, len);
        //        }
        //        zipOS.closeEntry();
        //        fis.close();
        //    }
        //    zipOS.close();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    @Test
    public void xxa() throws Exception {
        log.info("{}", LocalDateTime.now().format(Capm.FILE_DATE_TIME_FORMATTER));
        log.info("{}", "/upload/nodedev/he/merger/2018/04/13/f1.jpg".length());
        log.info("{}", Commons.encode("admin"));

        String xx = "1,2,3,4,5";
        List<Integer> collect = Arrays.stream(xx.split(",")).mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
        log.info("{}", collect);

        B b = new B();
        b.setId("id");
        b.setAge(1);
        System.out.println(b);
        Users users = new Users();
        users.setSname("xx");
        log.info("{}", users.toString());

    }

    @Test
    public void userName() throws Exception {
        for (int k = 0; k < 5; k++) {
            Map<String, Integer> map = Guava.newHashMap();
            for (int i = 0; i < 11000000; i++) {
                String key = getRandomCode();
                Integer val = map.get(key);
                if (val == null) {
                    val = 1;
                    map.put(key, val);
                } else {
                    map.put(key, val + 1);
                }
            }

            //for (int i = 0; i < 10000; i++) {
            //    int key = RandomUtils.nextInt(0, 30);
            //    log.info("{}", key);
            //    Integer val = map.get(key);
            //    if (val == null) {
            //        val = 1;
            //        map.put(key, val);
            //    } else {
            //        map.put(key, val + 1);
            //    }
            //}
            long sum3 = 0;
            long sum2 = 0;
            long sum1 = 0;
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                //log.info("key:{}   val:{}", entry.getKey(), entry.getValue());
                if (entry.getValue() > 3) {
                    sum3 += 1;
                    //log.error("key:{}   val:{}", entry.getKey(), entry.getValue());
                } else if (entry.getValue() > 2) {
                    sum2 += 1;
                    //log.error("key:{}   val:{}", entry.getKey(), entry.getValue());
                } else if (entry.getValue() > 1) {
                    sum1 += 1;
                    //log.error("key:{}   val:{}", entry.getKey(), entry.getValue());
                }
            }
            log.info("{}", sum3);
            log.info("{}", sum2);
            log.info("{}", sum1);
        }

    }

    private String getRandomCode() {
        StringBuilder sb = Guava.newStringBuilder();
        for (int j = 0; j < 8; j++) {
            int index = RandomUtils.nextInt(0, 30);
            sb.append(charList.get(index));
        }
        return sb.toString();
    }

    @Test
    public void xxxx() throws Exception {
        String key = "ldtDate";
        if (key.startsWith("ldt")) {
            log.info("{}", "以ldt开头");
        } else if (key.startsWith("ld")) {
            log.info("{}", "以ld开头");
        }
    }

    @Test
    public void xxx() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            sb.append("何");

        }
        String s = sb.toString();
        //long startTime = System.nanoTime(); //获取开始时间
        //AsyncTest.test(s);
        ////log.info("{}", s);
        //long endTime = System.nanoTime(); //获取结束时间
        //System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
        long sum = 0;
        for (int i = 0; i < 100; i++) {
            long startTime = System.currentTimeMillis(); //获取开始时间
            log.error("{}", s);
            long endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
            sum += (endTime - startTime);
        }
        System.out.println(sum / 100.0);
    }

    @Test
    public void xx() throws Exception {

        log.info("{}", "FunctionAndFunctionGroupRefs".length());
        log.info("{}", "log" + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy_MM")));

        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        boolean equal = now.isEqual(lastDay);
        log.info("{}", equal);

        log.info("{}", LocalDate.now().getMonth().getValue());
        log.debug("{}", "debug");
        String s = "90c6cf5d-37e0-4a8a-8cfb-08c0d1b643a7";
        log.info("{}", s.length());
        long time = (new Date()).getTime();
        log.info("{}", time);
        log.info("{}", (time + "").length());
    }

    @Test
    public void md5hash() throws Exception {
        //for (Map.Entry<String, Users> e : userMap.entrySet()) {
        //    String key = e.getKey();
        //    Users u = e.getValue();
        //    String salt = u.getSalt();
        //    String password = u.getPassword();
        //    Md5Hash md5Hash = new Md5Hash(password, salt, 2);
        //    log.info("{}---{}", u.getName(), md5Hash);
        //}

        String encodex = Commons.encode("密码00");
        log.info("{}", encodex);
        log.info("{}", encodex.equals("1a0654fc7cba549efe397ba5def2c6b7"));

        String salt = "我是盐";
        String password = "我是密码";
        SimpleHash hash = new SimpleHash("MD5", password, salt, 10);
        log.info("{}", hash);
        Md5Hash md5Hash = new Md5Hash(password, salt, 10);
        log.info("{}", md5Hash);
        Sha512Hash sha512Hash = new Sha512Hash(password, salt, 2);
        log.info("{}", sha512Hash);
        Sha256Hash sha256Hash = new Sha256Hash(password, salt, 2);
        String encode = Commons.encode(password);
        log.info("{}", sha256Hash.toBase64());
        log.info("{}", sha256Hash.toHex());
        log.info("{}", sha256Hash.toString());
        log.info("{}", encode);
        log.info("{}", encode.length());

        log.info("{}", "49438146267149f2cfd1af488b15792e".length());
        log.info("{}", "0f151d695c53814508d5aa862065686637c7207796f4174f740a3f49c64072c77545bb06743945a51b6d734594295cd50e6030b5376773c6b839e74859ada7b4".length());

    }

    @Test
    public void aeskey() throws Exception {
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        byte[] encoded = skey.getEncoded();
        String s = Base64.encodeToString(encoded);
        log.info(s);
    }

    @Test
    public void del() throws Exception {
        FileUtils.deleteQuietly(new File("D:\\upload\\temp\\8367345b442a389391ab0f8382eea42f"));
    }

    @Test
    public void c2() throws Exception {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties("config/config.properties");
            boolean debug = config.getBoolean("app_debug");
            boolean debugx = config.getBoolean("app_debugx", false);
            boolean debugxx = config.getBoolean("app_debugxx", Boolean.FALSE);
            log.info("{}", debug);
            log.info("{}", debugx);
            log.info("{}", debugxx);
        } catch (ConfigurationException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void sms() throws Exception {
        //int i = SMS2.sendSMS("18423452585", "短信测试1【何彦静】");
        //log.error("{}", i);
        Result result = SMS.sendSMS("18423452585", "短信测试2【何彦静】");
        log.info("{}", result);
    }

    @Test
    public void test() {
        for (int i = 1; i != 1; i++) {
            log.info("{}", 11);
        }
    }

    @Test
    public void x2() {
        // HEWARN: 2018/7/31 16:52 检查开发库和测试库中的表和字段
        //test需要新建的表
        List<TableBean> newTables = Guava.newArrayList();
        //test需要新曾的字段
        List<TableInfoBean> newTableFields = Guava.newArrayList();
        //test需要检查的字段
        List<TableInfoBean> checkTableFields = Guava.newArrayList();

        String devJson = "[{\"tableBean\":{\"tableName\":\"APPLYS\",\"tableComment\":\"search_102_101_000_申请表\"},\"fieldBeanList\":[{\"fieldName\":\"IBUDGET\",\"fieldComment\":\"search_102_101_111_批准投资概算\"},{\"fieldName\":\"ICHANGE\",\"fieldComment\":\"search_102_101_115_批准变更金额\"},{\"fieldName\":\"ICOST\",\"fieldComment\":\"search_102_101_104_送审造价\"},{\"fieldName\":\"IESTIMATE\",\"fieldComment\":\"search_102_101_109_批准投资估算\"},{\"fieldName\":\"IPLAN\",\"fieldComment\":\"search_102_101_113_批准投资预算\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"search_102_101_102_类型;101结算102决算103跟踪审计\"},{\"fieldName\":\"LDFINISHEDDATE\",\"fieldComment\":\"search_102_101_107_竣工时间\"},{\"fieldName\":\"LDSTARTDATE\",\"fieldComment\":\"search_102_101_106_开工时间\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITOPINION\",\"fieldComment\":\"search_102_101_119_审计局审核意见\"},{\"fieldName\":\"SBUDGETFILE\",\"fieldComment\":\"search_102_101_110_批准概算计划文件\"},{\"fieldName\":\"SCHANGEFILE\",\"fieldComment\":\"search_102_101_114_变更概算或预算文件\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\",\"fieldComment\":\"search_102_101_103_描述\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLEADERANDPHONE\",\"fieldComment\":\"search_102_101_116_项目负责人及联系电话\"},{\"fieldName\":\"SLINKMANANDPHONE\",\"fieldComment\":\"search_102_101_117_联系人及联系电话\"},{\"fieldName\":\"SOPINION\",\"fieldComment\":\"search_102_101_118_申请单位意见\"},{\"fieldName\":\"SPLANFILE\",\"fieldComment\":\"search_102_101_112_批准预算评审文件\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SPROJECTFILE\",\"fieldComment\":\"search_102_101_108_批准立项文件\"},{\"fieldName\":\"SPROJECTLIBID\",\"fieldComment\":\"search_102_101_101_项目\"},{\"fieldName\":\"SQUALITYGRADE\",\"fieldComment\":\"search_102_101_105_质量等级\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"APPROVALANDFEASIBILITYHISTORYS\",\"tableComment\":\"立项可研调整历史\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCONTENT\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\",\"fieldComment\":\"101:立项102:可研\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"APPROVALS\",\"tableComment\":\"立项\"},\"fieldBeanList\":[{\"fieldName\":\"DESTIMATEAMOUNT\"},{\"fieldName\":\"ICONSTRUCTIONTYPE\"},{\"fieldName\":\"IDESIGNTYPE\"},{\"fieldName\":\"IINTERMEDIARYTYPE\"},{\"fieldName\":\"IPROSPECTINGTYPE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPERVISIONTYPE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AREAS\",\"tableComment\":\"区域\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AUDITSETTINGS\",\"tableComment\":\"审计设置\"},\"fieldBeanList\":[{\"fieldName\":\"IDIVIDINGLINE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AUDITTPLDETAILCOPYS\",\"tableComment\":\"search_102_102_000_送审标准模板详情复制\"},\"fieldBeanList\":[{\"fieldName\":\"IFILETYPE\",\"fieldComment\":\"与文件模板中的模板类型相同\"},{\"fieldName\":\"IMUST\"},{\"fieldName\":\"IPAPER\",\"fieldComment\":\"search_102_102_101_是否电子文档\"},{\"fieldName\":\"IPASS\",\"fieldComment\":\"search_102_102_102_是否通过\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101资料分类102资料项\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAPPLYID\"},{\"fieldName\":\"SAUDITTPLID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFILETPLID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SOPIOIONGB\",\"fieldComment\":\"search_102_102_103_审批意见\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AUDITTPLDETAILS\",\"tableComment\":\"送审标准模板详情\"},\"fieldBeanList\":[{\"fieldName\":\"IMUST\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101资料分类102资料项\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITTPLID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFILETPLID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AUDITTPLS\",\"tableComment\":\"送审标准模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISHOWORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101结算102决算103跟踪审计\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"BIDS\",\"tableComment\":\"招投标\"},\"fieldBeanList\":[{\"fieldName\":\"DBIDAMOUNT\"},{\"fieldName\":\"IBIDDINGTYPE\"},{\"fieldName\":\"ILIMITDAY\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDBIDDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAGENCYORGID\"},{\"fieldName\":\"SBIDDER\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIDCARD\"},{\"fieldName\":\"SMANAGER\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPHONE\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SSECTIONID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SWHEEL\"}]},{\"tableBean\":{\"tableName\":\"BUDGETS\",\"tableComment\":\"预算\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"BUSINESSTABLES\",\"tableComment\":\"业务表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SENNAME\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SZHNAME\"}]},{\"tableBean\":{\"tableName\":\"CALCULATIONS\",\"tableComment\":\"概算\"},\"fieldBeanList\":[{\"fieldName\":\"DCALCULATIONAMOUNT\"},{\"fieldName\":\"DCONSTRUCTIONCOST\"},{\"fieldName\":\"DCONSTRUCTIONOTHERCOST\"},{\"fieldName\":\"DLOANINTEREST\"},{\"fieldName\":\"DRESERVECOST\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"CHARGEORGS\",\"tableComment\":\"分管机构\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"COMMONATTACHS\",\"tableComment\":\"公共附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:机构营业执照，2:机构资质，3:人员身份证，4:人员证书，\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"DATAANDAUDITATTACHS\",\"tableComment\":\"资料与送审附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\",\"fieldComment\":\"文件模板，送审标准模板详情，xx等\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAMEATTACH\",\"fieldComment\":\"search_101_103_101_附件名称\"},{\"fieldName\":\"SPATHATTACH\",\"fieldComment\":\"search_101_103_102_附件路径\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"DICTIONARIES\",\"tableComment\":\"机构资质,人员资质等\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:机构资质,2:人员资质,3 职务\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FEASIBILITYS\",\"tableComment\":\"可研\"},\"fieldBeanList\":[{\"fieldName\":\"DESTIMATEAMOUNT\"},{\"fieldName\":\"ICONSTRUCTIONTYPE\"},{\"fieldName\":\"IDESIGNTYPE\"},{\"fieldName\":\"IINTERMEDIARYTYPE\"},{\"fieldName\":\"IPROSPECTINGTYPE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPERVISIONTYPE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FILETPLS\",\"tableComment\":\"文件模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101资料送审102审计实施方案103其他\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDFUNCTIONGROUPREFS\",\"tableComment\":\"功能与功能组关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONGROUPID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDTABLES\",\"tableComment\":\"功能对应表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUSINESSTABLEID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDUSERREFS\",\"tableComment\":\"功能与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONGROUPANDUSERREFS\",\"tableComment\":\"功能组与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONGROUPID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\",\"fieldComment\":\"在不同机构间授权时，值为当时操作的人的所属机构，在同机构间授权时，值为授权人的功能对应的授权机构\"},{\"fieldName\":\"SREFID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONGROUPS\",\"tableComment\":\"功能组\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SFROMORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONS\",\"tableComment\":\"功能\"},\"fieldBeanList\":[{\"fieldName\":\"IJOINPROCESS\"},{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPHONE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:模块，2:节点，3:标签，4:按钮\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SANDROIDMETHOD\"},{\"fieldName\":\"SBINDEVENT\"},{\"fieldName\":\"SBTNLOCATION\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SJOINPROCESSTABLE\"},{\"fieldName\":\"SMINICON\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SPCMETHOD\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"INTERMEDIARYS\",\"tableComment\":\"机构与机构关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITORGID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SINTERMEDIARYORGID\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGEXCEPTIONS\",\"tableComment\":\"异常日志\"},\"fieldBeanList\":[{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc,2:android,3:apple\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"SEXCEPTION\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIP\"},{\"fieldName\":\"SMESSAGE\"}]},{\"tableBean\":{\"tableName\":\"LOGOPERATIONS\",\"tableComment\":\"操作日志\"},\"fieldBeanList\":[{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc,2:android,3:apple\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIP\"},{\"fieldName\":\"SREQUESTCONTENT\"},{\"fieldName\":\"SRESPONSECONTENT\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGORGDETAILS\",\"tableComment\":\"更改机构详情\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LUSERNUMBER\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGSETTINGID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGSETTINGS\",\"tableComment\":\"系统设置日志\"},\"fieldBeanList\":[{\"fieldName\":\"IMAXNUMBER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTUSERNUMBER\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGO\"},{\"fieldName\":\"SORGNAME\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SSYSTEMNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"MAILSETTINGS\",\"tableComment\":\"邮件设置\"},\"fieldBeanList\":[{\"fieldName\":\"INEEDAUTH\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITIMEOUT\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SMAILSERVER\"},{\"fieldName\":\"SPASSWORD\"},{\"fieldName\":\"SSENDERADDR\"},{\"fieldName\":\"SSENDERNICK\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERNAME\"}]},{\"tableBean\":{\"tableName\":\"MAILTPLS\",\"tableComment\":\"邮件模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"绑定邮箱101，通过邮箱修改手机号102\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCONTENT\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"STITLE\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGANDUSERREFS\",\"tableComment\":\"机构与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"IISPROJECTLEADER\"},{\"fieldName\":\"IPERMISSIONLEVEL\",\"fieldComment\":\"1本机构及所有子机构所有项目,2分管机构所有项目,4有授权所有项目\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"IUSERTYPE\",\"fieldComment\":\"2管理员4普通用户\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTFIRSTTIME\"},{\"fieldName\":\"LDTLASTTIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LTOTAL\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDICTIONARIESID\"},{\"fieldName\":\"SDUTIES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SMANAGERID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGDISABLEREF\",\"tableComment\":\"机构禁用关系，解决机构禁用，启用时，禁用，启用批量用户\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGORUSERANDDICTIONARIEREFS\",\"tableComment\":\"资质一对多\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SDICTIONARIEID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGIDORUSERID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGS\",\"tableComment\":\"机构\"},\"fieldBeanList\":[{\"fieldName\":\"IISDEPARTMENT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101审计局、102中介机构、103嘉宾、104建设业主、105BT单位、106设计单位、107勘察单位、108监理单位、109施工单位-----------多选\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LUSERNUMBER\"},{\"fieldName\":\"SAREAID\",\"fieldComment\":\"只有机构类型为审计局且为市局用户时才显示。区县局审计局直接通过用户所属区域获取该值\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBLOGS\",\"tableComment\":\"计划库日志\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"IYEAR\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBPROJECTLOGS\",\"tableComment\":\"计划库项目列表日志\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SPLANLIBID\"},{\"fieldName\":\"SPROJECTLIBID\"},{\"fieldName\":\"SREASON\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBPROJECTS\",\"tableComment\":\"search_101_102_000_计划库项目列表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDENDDATE\",\"fieldComment\":\"search_101_102_104_结束时间\"},{\"fieldName\":\"LDSTARTDATE\",\"fieldComment\":\"search_101_102_103_开始时间\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\",\"fieldComment\":\"search_101_102_101_实施单位\"},{\"fieldName\":\"SPLANLIBID\"},{\"fieldName\":\"SPROJECTLIBID\"},{\"fieldName\":\"SREASON\",\"fieldComment\":\"search_101_102_105_驳回理由\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\",\"fieldComment\":\"search_101_102_102_审计组长\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBS\",\"tableComment\":\"search_101_101_000_计划库\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\"},{\"fieldName\":\"IYEAR\",\"fieldComment\":\"search_101_101_102_审计年度\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\",\"fieldComment\":\"search_101_101_101_计划标题\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBSATTACHLOGS\",\"tableComment\":\"计划库附件日志\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBSATTACHS\",\"tableComment\":\"search_101_103_000_计划库附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAMEATTACH\",\"fieldComment\":\"search_101_103_101_附件名称\"},{\"fieldName\":\"SPATHATTACH\",\"fieldComment\":\"search_101_103_102_附件路径\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSDESIGNANDFUNCTIONS\",\"tableComment\":\"流程设计对应功能\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSDESIGNS\",\"tableComment\":\"流程设计\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SFROMORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SJSONDATA\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSINSTANCENODES\",\"tableComment\":\"流程实例节点记录\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLASTPROCESSSTEPID\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SPROCESSSTEPID\"},{\"fieldName\":\"SRESULT\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSINSTANCES\",\"tableComment\":\"流程实例\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SPROCESSSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSSTEPS\",\"tableComment\":\"流程步骤\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTBACK\"},{\"fieldName\":\"ISUPPORTOPINION\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"开始101，普通102，会签103，结束104\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSSTEPSANDFIELDREFS\",\"tableComment\":\"流程步骤与字段关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFIELDNAME\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNANDFUNCTIONID\"},{\"fieldName\":\"SSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROJECTLIBATTACHS\",\"tableComment\":\"项目库附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:立项2:可研3:概算4:预算5:施工招投标\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROJECTLIBS\",\"tableComment\":\"项目库\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITORGID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"QUICKFUNCTIONS\",\"tableComment\":\"快捷功能\"},\"fieldBeanList\":[{\"fieldName\":\"ISHOWORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc2手机\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SREFID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SECTIONS\",\"tableComment\":\"标段\"},\"fieldBeanList\":[{\"fieldName\":\"DBUDGETAMOUNT\"},{\"fieldName\":\"DCOMMISSIONCOST\"},{\"fieldName\":\"DENGINEERINGCOST\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUDGETID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SETTINGS\",\"tableComment\":\"系统设置\"},\"fieldBeanList\":[{\"fieldName\":\"IMAXNUMBER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTUSERNUMBER\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGO\"},{\"fieldName\":\"SORGNAME\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SSYSTEMNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SPECIALVIEWS\",\"tableComment\":\"机构特殊视图\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDIVID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"STEPOPERATORS\",\"tableComment\":\"步骤操作人\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"refId101,功能组id102\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SOPERATORID\"},{\"fieldName\":\"SSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"STEPREFS\",\"tableComment\":\"步骤与步骤关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLASTSTEPID\"},{\"fieldName\":\"SNEXTSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"TABLEANDFIELDS\",\"tableComment\":\"表对应字段\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUSINESSTABLEID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SENNAME\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SZHNAME\"}]},{\"tableBean\":{\"tableName\":\"TABLETEST\",\"tableComment\":\"tabletest\"},\"fieldBeanList\":[{\"fieldName\":\"BNUMBER\",\"fieldComment\":\"smallint\"},{\"fieldName\":\"DNUMBER\",\"fieldComment\":\"numeric\"},{\"fieldName\":\"INUMBER\",\"fieldComment\":\"int\"},{\"fieldName\":\"LDTDATE\",\"fieldComment\":\"date\"},{\"fieldName\":\"LDTDATETIME\",\"fieldComment\":\"datetime\"},{\"fieldName\":\"LNUMBER\",\"fieldComment\":\"number\"},{\"fieldName\":\"SCLOB\",\"fieldComment\":\"clob\"},{\"fieldName\":\"SNCLOB\",\"fieldComment\":\"nclob\"},{\"fieldName\":\"SNVARCHAR2\",\"fieldComment\":\"nvarchar2{@javax.validation.constraints.Null}{@org.hibernate.validator.constraints.Length(min=5, max=10)}\"},{\"fieldName\":\"SVARCHAR2\",\"fieldComment\":\"varchar2\"}]},{\"tableBean\":{\"tableName\":\"TABLETPL\",\"tableComment\":\"表模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"USERS\",\"tableComment\":\"用户\"},\"fieldBeanList\":[{\"fieldName\":\"IGENDER\",\"fieldComment\":\"1男，2女\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1amdin,2管理员4普通用户\"},{\"fieldName\":\"LDBIRTHDAY\"},{\"fieldName\":\"LDGRADUATIONDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SEMAIL\"},{\"fieldName\":\"SGRADUATESCHOOL\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIDCARD\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SNICKNAME\"},{\"fieldName\":\"SPASSWORD\"},{\"fieldName\":\"SPHONE\"},{\"fieldName\":\"SSIGNATURE\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERNAME\"}]},{\"tableBean\":{\"tableName\":\"USERSETTINGS\",\"tableComment\":\"用户设置\"},\"fieldBeanList\":[{\"fieldName\":\"ICLASSIFYNUM\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]}]";
        List<TableInfoBean> devTableInfoBeanList = Guava.toList(devJson, TableInfoBean.class);
        Map<String, TableInfoBean> devTable = Guava.newHashMap();
        for (TableInfoBean tableInfoBean : devTableInfoBeanList) {
            String tableName = tableInfoBean.getTableBean().getTableName();
            devTable.put(tableName, tableInfoBean);
        }
        String testJosn = "[{\"tableBean\":{\"tableName\":\"APPROVALANDFEASIBILITYHISTORYS\",\"tableComment\":\"立项可研调整历史\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCONTENT\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\",\"fieldComment\":\"101:立项102:可研\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"APPROVALS\",\"tableComment\":\"立项\"},\"fieldBeanList\":[{\"fieldName\":\"DESTIMATEAMOUNT\"},{\"fieldName\":\"ICONSTRUCTIONTYPE\"},{\"fieldName\":\"IDESIGNTYPE\"},{\"fieldName\":\"IINTERMEDIARYTYPE\"},{\"fieldName\":\"IPROSPECTINGTYPE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPERVISIONTYPE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AREAS\",\"tableComment\":\"区域\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"AUDITSETTINGS\",\"tableComment\":\"审计设置\"},\"fieldBeanList\":[{\"fieldName\":\"IDIVIDINGLINE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"BIDS\",\"tableComment\":\"招投标\"},\"fieldBeanList\":[{\"fieldName\":\"DBIDAMOUNT\"},{\"fieldName\":\"IBIDDINGTYPE\"},{\"fieldName\":\"ILIMITDAY\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDBIDDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAGENCYORGID\"},{\"fieldName\":\"SBIDDER\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIDCARD\"},{\"fieldName\":\"SMANAGER\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPHONE\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SSECTIONID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SWHEEL\"}]},{\"tableBean\":{\"tableName\":\"BUDGETS\",\"tableComment\":\"预算\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"BUSINESSTABLES\",\"tableComment\":\"业务表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SENNAME\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SZHNAME\"}]},{\"tableBean\":{\"tableName\":\"CALCULATIONS\",\"tableComment\":\"概算\"},\"fieldBeanList\":[{\"fieldName\":\"DCALCULATIONAMOUNT\"},{\"fieldName\":\"DCONSTRUCTIONCOST\"},{\"fieldName\":\"DCONSTRUCTIONOTHERCOST\"},{\"fieldName\":\"DLOANINTEREST\"},{\"fieldName\":\"DRESERVECOST\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"CHARGEORGS\",\"tableComment\":\"分管机构\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"COMMONATTACHS\",\"tableComment\":\"公共附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:机构营业执照，2:机构资质，3:人员身份证，4:人员证书，\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"DICTIONARIES\",\"tableComment\":\"机构资质,人员资质等\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:机构资质,2:人员资质,3 职务\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FEASIBILITYS\",\"tableComment\":\"可研\"},\"fieldBeanList\":[{\"fieldName\":\"DESTIMATEAMOUNT\"},{\"fieldName\":\"ICONSTRUCTIONTYPE\"},{\"fieldName\":\"IDESIGNTYPE\"},{\"fieldName\":\"IINTERMEDIARYTYPE\"},{\"fieldName\":\"IPROSPECTINGTYPE\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPERVISIONTYPE\"},{\"fieldName\":\"LDAPPROVALDATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SAPPROVALNUM\"},{\"fieldName\":\"SAPPROVALORGID\"},{\"fieldName\":\"SCAPITALSOURCE\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDEPUTYORGID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDFUNCTIONGROUPREFS\",\"tableComment\":\"功能与功能组关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONGROUPID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDTABLES\",\"tableComment\":\"功能对应表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUSINESSTABLEID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONANDUSERREFS\",\"tableComment\":\"功能与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONGROUPANDUSERREFS\",\"tableComment\":\"功能组与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONGROUPID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\",\"fieldComment\":\"在不同机构间授权时，值为当时操作的人的所属机构，在同机构间授权时，值为授权人的功能对应的授权机构\"},{\"fieldName\":\"SREFID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONGROUPS\",\"tableComment\":\"功能组\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SFROMORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"FUNCTIONS\",\"tableComment\":\"功能\"},\"fieldBeanList\":[{\"fieldName\":\"IJOINPROCESS\"},{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPHONE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1分类(包含七类标签),2:模块，3:节点，4:标签，5:按钮，\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SANDROIDMETHOD\"},{\"fieldName\":\"SBINDEVENT\",\"fieldComment\":\"columnName:functionName\"},{\"fieldName\":\"SBTNLOCATION\",\"fieldComment\":\"顶部：101，尾部102，右键菜单103\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SJOINPROCESSTABLE\"},{\"fieldName\":\"SMINICON\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SPCMETHOD\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"INTERMEDIARYS\",\"tableComment\":\"机构与机构关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITORGID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SINTERMEDIARYORGID\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGEXCEPTIONS\",\"tableComment\":\"异常日志\"},\"fieldBeanList\":[{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc,2:android,3:apple\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"SEXCEPTION\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIP\"},{\"fieldName\":\"SMESSAGE\"}]},{\"tableBean\":{\"tableName\":\"LOGOPERATIONS\",\"tableComment\":\"操作日志\"},\"fieldBeanList\":[{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc,2:android,3:apple\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIP\"},{\"fieldName\":\"SREQUESTCONTENT\"},{\"fieldName\":\"SRESPONSECONTENT\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGORGDETAILS\",\"tableComment\":\"更改机构详情\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LUSERNUMBER\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGSETTINGID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"LOGSETTINGS\",\"tableComment\":\"系统设置日志\"},\"fieldBeanList\":[{\"fieldName\":\"IMAXNUMBER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTUSERNUMBER\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGO\"},{\"fieldName\":\"SORGNAME\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SSYSTEMNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"MAILSETTINGS\",\"tableComment\":\"邮件设置\"},\"fieldBeanList\":[{\"fieldName\":\"INEEDAUTH\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITIMEOUT\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SMAILSERVER\"},{\"fieldName\":\"SPASSWORD\"},{\"fieldName\":\"SSENDERADDR\"},{\"fieldName\":\"SSENDERNICK\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERNAME\"}]},{\"tableBean\":{\"tableName\":\"MAILTPLS\",\"tableComment\":\"邮件模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"绑定邮箱101，通过邮箱修改手机号102\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCONTENT\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"STITLE\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGANDUSERREFS\",\"tableComment\":\"机构与用户关系\"},\"fieldBeanList\":[{\"fieldName\":\"IISPROJECTLEADER\"},{\"fieldName\":\"IPERMISSIONLEVEL\",\"fieldComment\":\"1本机构及所有子机构所有项目,2分管机构所有项目,4有授权所有项目\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"IUSERTYPE\",\"fieldComment\":\"2管理员4普通用户\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTFIRSTTIME\"},{\"fieldName\":\"LDTLASTTIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LTOTAL\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDICTIONARIESID\"},{\"fieldName\":\"SDUTIES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SMANAGERID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGDISABLEREF\",\"tableComment\":\"机构禁用关系，解决机构禁用，启用时，禁用，启用批量用户\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGORUSERANDDICTIONARIEREFS\",\"tableComment\":\"资质一对多\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SDICTIONARIEID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGIDORUSERID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"ORGS\",\"tableComment\":\"机构\"},\"fieldBeanList\":[{\"fieldName\":\"IISDEPARTMENT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101审计局、102中介机构、103嘉宾、104建设业主、105BT单位、106设计单位、107勘察单位、108监理单位、109施工单位-----------多选\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"LUSERNUMBER\"},{\"fieldName\":\"SAREAID\",\"fieldComment\":\"只有机构类型为审计局且为市局用户时才显示。区县局审计局直接通过用户所属区域获取该值\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDES\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPARENTID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBLOGS\",\"tableComment\":\"计划库日志\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"IYEAR\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBPROJECTLOGS\",\"tableComment\":\"计划库项目列表日志\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDENDDATE\"},{\"fieldName\":\"LDSTARTDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SPLANLIBID\"},{\"fieldName\":\"SPROJECTLIBID\"},{\"fieldName\":\"SREASON\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBPROJECTS\",\"tableComment\":\"search_101_102_000_计划库项目列表\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDENDDATE\",\"fieldComment\":\"search_101_102_104_结束时间\"},{\"fieldName\":\"LDSTARTDATE\",\"fieldComment\":\"search_101_102_103_开始时间\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\",\"fieldComment\":\"search_101_102_101_实施单位\"},{\"fieldName\":\"SPLANLIBID\"},{\"fieldName\":\"SPROJECTLIBID\"},{\"fieldName\":\"SREASON\",\"fieldComment\":\"search_101_102_105_驳回理由\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\",\"fieldComment\":\"search_101_102_102_审计组长\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBS\",\"tableComment\":\"search_101_101_000_计划库\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"101年度，102委托\"},{\"fieldName\":\"IYEAR\",\"fieldComment\":\"search_101_101_102_审计年度\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\",\"fieldComment\":\"search_101_101_101_计划标题\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBSATTACHLOGS\",\"tableComment\":\"计划库附件日志\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PLANLIBSATTACHS\",\"tableComment\":\"search_101_103_000_计划库附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAMEATTACH\",\"fieldComment\":\"search_101_103_101_附件名称\"},{\"fieldName\":\"SPATHATTACH\",\"fieldComment\":\"search_101_103_102_附件路径\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSDESIGNANDFUNCTIONS\",\"tableComment\":\"流程设计对应功能\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSDESIGNS\",\"tableComment\":\"流程设计\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTPROJECT\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SFROMORGID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SJSONDATA\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSINSTANCENODES\",\"tableComment\":\"流程实例节点记录\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\",\"fieldComment\":\"101未办理，102已办理，103异常终止\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLASTPROCESSSTEPID\"},{\"fieldName\":\"SPROCESSINSTANCEID\"},{\"fieldName\":\"SPROCESSSTEPID\"},{\"fieldName\":\"SRESULT\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSINSTANCES\",\"tableComment\":\"流程实例\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\",\"fieldComment\":\"101在办,102结束,103异常终止\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDESC\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SPROCESSSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSSTEPS\",\"tableComment\":\"流程步骤\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTBACK\"},{\"fieldName\":\"ISUPPORTOPINION\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"开始101，普通102，会签103，结束104\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROCESSDESIGNID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROCESSSTEPSANDFIELDREFS\",\"tableComment\":\"流程步骤与字段关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFIELDNAME\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SPROCESSDESIGNANDFUNCTIONID\"},{\"fieldName\":\"SSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROJECTLIBATTACHS\",\"tableComment\":\"项目库附件\"},\"fieldBeanList\":[{\"fieldName\":\"IORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:立项2:可研3:概算4:预算5:施工招投标\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDATAID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPATH\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"PROJECTLIBS\",\"tableComment\":\"项目库\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SAUDITORGID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROPRIETORORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"QUICKFUNCTIONS\",\"tableComment\":\"快捷功能\"},\"fieldBeanList\":[{\"fieldName\":\"ISHOWORDER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1:pc2手机\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SREFID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SECTIONS\",\"tableComment\":\"标段\"},\"fieldBeanList\":[{\"fieldName\":\"DBUDGETAMOUNT\"},{\"fieldName\":\"DCOMMISSIONCOST\"},{\"fieldName\":\"DENGINEERINGCOST\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUDGETID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SPROJECTLIBSID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SETTINGS\",\"tableComment\":\"系统设置\"},\"fieldBeanList\":[{\"fieldName\":\"IMAXNUMBER\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ISUPPORTUSERNUMBER\",\"fieldComment\":\"1是，2否\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SICON\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLOGO\"},{\"fieldName\":\"SORGNAME\"},{\"fieldName\":\"SORGTYPE\"},{\"fieldName\":\"SSYSTEMNAME\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"SPECIALVIEWS\",\"tableComment\":\"机构特殊视图\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SDIVID\"},{\"fieldName\":\"SFUNCTIONID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SORGID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"STEPOPERATORS\",\"tableComment\":\"步骤操作人\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"refId101,功能组id102\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SOPERATORID\"},{\"fieldName\":\"SSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"STEPREFS\",\"tableComment\":\"步骤与步骤关系\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SLASTSTEPID\"},{\"fieldName\":\"SNEXTSTEPID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"TABLEANDFIELDS\",\"tableComment\":\"表对应字段\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SBUSINESSTABLEID\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SENNAME\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SZHNAME\"}]},{\"tableBean\":{\"tableName\":\"TABLETPL\",\"tableComment\":\"表模板\"},\"fieldBeanList\":[{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"}]},{\"tableBean\":{\"tableName\":\"USERS\",\"tableComment\":\"用户\"},\"fieldBeanList\":[{\"fieldName\":\"IGENDER\",\"fieldComment\":\"1男，2女\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"ITYPE\",\"fieldComment\":\"1admin4普通用户\"},{\"fieldName\":\"LDBIRTHDAY\"},{\"fieldName\":\"LDGRADUATIONDATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SADDRESS\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SEMAIL\"},{\"fieldName\":\"SGRADUATESCHOOL\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SIDCARD\"},{\"fieldName\":\"SNAME\"},{\"fieldName\":\"SNICKNAME\"},{\"fieldName\":\"SPASSWORD\"},{\"fieldName\":\"SPHONE\"},{\"fieldName\":\"SSIGNATURE\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERNAME\"}]},{\"tableBean\":{\"tableName\":\"USERSETTINGS\",\"tableComment\":\"用户设置\"},\"fieldBeanList\":[{\"fieldName\":\"ICLASSIFYNUM\"},{\"fieldName\":\"ISTATE\"},{\"fieldName\":\"LDTCREATETIME\"},{\"fieldName\":\"LDTUPDATETIME\"},{\"fieldName\":\"SCREATEUSERID\"},{\"fieldName\":\"SID\"},{\"fieldName\":\"SUPDATEUSERID\"},{\"fieldName\":\"SUSERID\"}]}]";
        List<TableInfoBean> testTableInfoBeanList = Guava.toList(testJosn, TableInfoBean.class);
        Map<String, TableInfoBean> testTable = Guava.newHashMap();
        for (TableInfoBean tableInfoBean : testTableInfoBeanList) {
            String tableName = tableInfoBean.getTableBean().getTableName();
            testTable.put(tableName, tableInfoBean);
        }
        for (String tableName : devTable.keySet()) {
            TableInfoBean devTableInfoBean = devTable.get(tableName);
            TableBean tableBean = devTableInfoBean.getTableBean();
            TableInfoBean testTableInfoBean = testTable.get(tableName);
            if (testTableInfoBean == null) {
                //测试库中没有这张表
                newTables.add(tableBean);
            } else {
                //比较测试库和开发库同一张表的字段
                List<FieldBean> devFieldBeanList = devTableInfoBean.getFieldBeanList();
                List<FieldBean> testFieldBeanList = testTableInfoBean.getFieldBeanList();
                List<FieldBean> newfieldBeanList = Guava.newArrayList();
                List<FieldBean> checkfieldBeanList = Guava.newArrayList();
                for (FieldBean fieldBean : devFieldBeanList) {
                    //测试库中没有开发库中的字段
                    if (!testFieldBeanList.contains(fieldBean)) {
                        newfieldBeanList.add(fieldBean);
                    }
                }
                for (FieldBean fieldBean : testFieldBeanList) {
                    //开发库中没有测试库中的字段
                    if (!devFieldBeanList.contains(fieldBean)) {
                        checkfieldBeanList.add(fieldBean);
                    }
                }
                if (!newfieldBeanList.isEmpty()) {
                    newTableFields.add(new TableInfoBean(tableBean, newfieldBeanList));
                }
                if (!checkfieldBeanList.isEmpty()) {
                    checkTableFields.add(new TableInfoBean(tableBean, checkfieldBeanList));
                }

            }
        }
        List<TableBean> newTableList = newTables.stream().sorted(Comparator.comparing(TableBean::getTableName)).collect(Collectors.toList());
        List<TableInfoBean> newTableFieldList = newTableFields.stream().sorted(Comparator.comparing(o -> o.getTableBean().getTableName())).collect(Collectors.toList());
        List<TableInfoBean> checkTableFieldList = checkTableFields.stream().sorted(Comparator.comparing(o -> o.getTableBean().getTableName())).collect(Collectors.toList());
        log.error("{}", Guava.toJson(newTableList));
        log.warn("{}", Guava.toJson(newTableFieldList));
        log.info("{}", Guava.toJson(checkTableFieldList));

    }

    @Test
    public void x() {
        List<String> tables = Guava.newArrayList();
        Map<String, List<String>> fields = Guava.newHashMap();
        String devJson = "{\"PROJECTLIBATTACHS\":[\"IORDER\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"TABLEANDFIELDS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SBUSINESSTABLEID\",\"SCREATEUSERID\",\"SENNAME\",\"SID\",\"SUPDATEUSERID\",\"SZHNAME\"],\"SECTIONS\":[\"DBUDGETAMOUNT\",\"DCOMMISSIONCOST\",\"DENGINEERINGCOST\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SBUDGETID\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SUPDATEUSERID\"],\"BIDS\":[\"DBIDAMOUNT\",\"IBIDDINGTYPE\",\"ILIMITDAY\",\"ISTATE\",\"LDBIDDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAGENCYORGID\",\"SBIDDER\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SID\",\"SIDCARD\",\"SMANAGER\",\"SNAME\",\"SPHONE\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SSECTIONID\",\"SUPDATEUSERID\",\"SWHEEL\"],\"PROCESSINSTANCENODES\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SLASTPROCESSSTEPID\",\"SPROCESSINSTANCEID\",\"SPROCESSSTEPID\",\"SRESULT\",\"SUPDATEUSERID\",\"SUSERID\"],\"FUNCTIONANDTABLES\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SBUSINESSTABLEID\",\"SCREATEUSERID\",\"SFUNCTIONID\",\"SID\",\"SUPDATEUSERID\"],\"SETTINGS\":[\"IMAXNUMBER\",\"ISTATE\",\"ISUPPORTUSERNUMBER\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SICON\",\"SID\",\"SLOGO\",\"SORGNAME\",\"SORGTYPE\",\"SSYSTEMNAME\",\"SUPDATEUSERID\"],\"FUNCTIONANDFUNCTIONGROUPREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONGROUPID\",\"SFUNCTIONID\",\"SID\",\"SUPDATEUSERID\"],\"LOGEXCEPTIONS\":[\"ITYPE\",\"LDTCREATETIME\",\"SEXCEPTION\",\"SID\",\"SIP\",\"SMESSAGE\"],\"PROCESSSTEPS\":[\"ISTATE\",\"ISUPPORTBACK\",\"ISUPPORTOPINION\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SPROCESSDESIGNID\",\"SUPDATEUSERID\"],\"PLANLIBSATTACHLOGS\":[\"IORDER\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"FUNCTIONGROUPANDUSERREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONGROUPID\",\"SID\",\"SORGID\",\"SREFID\",\"SUPDATEUSERID\"],\"FUNCTIONGROUPS\":[\"ISTATE\",\"ISUPPORTPROJECT\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SFROMORGID\",\"SID\",\"SNAME\",\"SORGID\",\"SUPDATEUSERID\"],\"DICTIONARIES\":[\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SNAME\",\"SUPDATEUSERID\"],\"COMMONATTACHS\":[\"IORDER\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"QUICKFUNCTIONS\":[\"ISHOWORDER\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONID\",\"SID\",\"SORGID\",\"SREFID\",\"SUPDATEUSERID\"],\"TABLETPL\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SUPDATEUSERID\"],\"PLANLIBS\":[\"ISTATE\",\"ITYPE\",\"IYEAR\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SORGID\",\"SPROCESSINSTANCEID\",\"SUPDATEUSERID\"],\"BUSINESSTABLES\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SENNAME\",\"SID\",\"SUPDATEUSERID\",\"SZHNAME\"],\"ORGS\":[\"IISDEPARTMENT\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"LUSERNUMBER\",\"SAREAID\",\"SCREATEUSERID\",\"SDES\",\"SID\",\"SNAME\",\"SPARENTID\",\"SUPDATEUSERID\"],\"PROCESSINSTANCES\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SPROCESSDESIGNID\",\"SPROCESSSTEPID\",\"SUPDATEUSERID\"],\"SPECIALVIEWS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDIVID\",\"SFUNCTIONID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\"],\"PROCESSDESIGNS\":[\"ISTATE\",\"ISUPPORTPROJECT\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SFROMORGID\",\"SID\",\"SJSONDATA\",\"SNAME\",\"SORGID\",\"SUPDATEUSERID\"],\"MAILTPLS\":[\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCONTENT\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"STITLE\",\"SUPDATEUSERID\"],\"MAILSETTINGS\":[\"INEEDAUTH\",\"ISTATE\",\"ITIMEOUT\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SMAILSERVER\",\"SPASSWORD\",\"SSENDERADDR\",\"SSENDERNICK\",\"SUPDATEUSERID\",\"SUSERNAME\"],\"FEASIBILITYS\":[\"DESTIMATEAMOUNT\",\"ICONSTRUCTIONTYPE\",\"IDESIGNTYPE\",\"IINTERMEDIARYTYPE\",\"IPROSPECTINGTYPE\",\"ISTATE\",\"ISUPERVISIONTYPE\",\"LDAPPROVALDATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"APPROVALANDFEASIBILITYHISTORYS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCONTENT\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SUPDATEUSERID\"],\"PLANLIBLOGS\":[\"ISTATE\",\"IYEAR\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SUPDATEUSERID\"],\"USERS\":[\"IGENDER\",\"ISTATE\",\"ITYPE\",\"LDBIRTHDAY\",\"LDGRADUATIONDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SCREATEUSERID\",\"SEMAIL\",\"SGRADUATESCHOOL\",\"SID\",\"SIDCARD\",\"SNAME\",\"SNICKNAME\",\"SPASSWORD\",\"SPHONE\",\"SSIGNATURE\",\"SUPDATEUSERID\",\"SUSERNAME\"],\"CHARGEORGS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"ORGDISABLEREF\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"PLANLIBSATTACHS\":[\"IORDER\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAMEATTACH\",\"SPATHATTACH\",\"SUPDATEUSERID\"],\"FUNCTIONS\":[\"IJOINPROCESS\",\"IORDER\",\"ISTATE\",\"ISUPPORTPHONE\",\"ISUPPORTPROJECT\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SANDROIDMETHOD\",\"SBINDEVENT\",\"SBTNLOCATION\",\"SCREATEUSERID\",\"SDESC\",\"SICON\",\"SID\",\"SJOINPROCESSTABLE\",\"SMINICON\",\"SNAME\",\"SPARENTID\",\"SPCMETHOD\",\"SUPDATEUSERID\"],\"PLANLIBPROJECTLOGS\":[\"ISTATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SPLANLIBID\",\"SPROJECTLIBID\",\"SREASON\",\"SUPDATEUSERID\",\"SUSERID\"],\"AUDITSETTINGS\":[\"IDIVIDINGLINE\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\"],\"INTERMEDIARYS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAUDITORGID\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SINTERMEDIARYORGID\",\"SORGTYPE\",\"SUPDATEUSERID\"],\"LOGORGDETAILS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"LUSERNUMBER\",\"SCREATEUSERID\",\"SID\",\"SLOGSETTINGID\",\"SORGID\",\"SUPDATEUSERID\"],\"STEPREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SLASTSTEPID\",\"SNEXTSTEPID\",\"SUPDATEUSERID\"],\"PROCESSSTEPSANDFIELDREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFIELDNAME\",\"SFUNCTIONID\",\"SID\",\"SPROCESSDESIGNANDFUNCTIONID\",\"SSTEPID\",\"SUPDATEUSERID\"],\"FUNCTIONANDUSERREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"CALCULATIONS\":[\"DCALCULATIONAMOUNT\",\"DCONSTRUCTIONCOST\",\"DCONSTRUCTIONOTHERCOST\",\"DLOANINTEREST\",\"DRESERVECOST\",\"ISTATE\",\"LDAPPROVALDATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"AREAS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDES\",\"SID\",\"SNAME\",\"SPARENTID\",\"SUPDATEUSERID\"],\"PROCESSDESIGNANDFUNCTIONS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONID\",\"SID\",\"SPROCESSDESIGNID\",\"SUPDATEUSERID\"],\"PROJECTLIBS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAUDITORGID\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"LOGOPERATIONS\":[\"ITYPE\",\"LDTCREATETIME\",\"SDESC\",\"SID\",\"SIP\",\"SREQUESTCONTENT\",\"SRESPONSECONTENT\",\"SUSERID\"],\"TABLETEST\":[\"BNUMBER\",\"DNUMBER\",\"INUMBER\",\"LDTDATE\",\"LDTDATETIME\",\"LNUMBER\",\"SCLOB\",\"SNCLOB\",\"SNVARCHAR2\",\"SVARCHAR2\"],\"ORGANDUSERREFS\":[\"IISPROJECTLEADER\",\"IPERMISSIONLEVEL\",\"ISTATE\",\"IUSERTYPE\",\"LDTCREATETIME\",\"LDTFIRSTTIME\",\"LDTLASTTIME\",\"LDTUPDATETIME\",\"LTOTAL\",\"SCREATEUSERID\",\"SDICTIONARIESID\",\"SDUTIES\",\"SID\",\"SMANAGERID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"BUDGETS\":[\"ISTATE\",\"LDAPPROVALDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"STEPOPERATORS\":[\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SOPERATORID\",\"SSTEPID\",\"SUPDATEUSERID\"],\"APPROVALS\":[\"DESTIMATEAMOUNT\",\"ICONSTRUCTIONTYPE\",\"IDESIGNTYPE\",\"IINTERMEDIARYTYPE\",\"IPROSPECTINGTYPE\",\"ISTATE\",\"ISUPERVISIONTYPE\",\"LDAPPROVALDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"USERSETTINGS\":[\"ICLASSIFYNUM\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SUPDATEUSERID\",\"SUSERID\"],\"LOGSETTINGS\":[\"IMAXNUMBER\",\"ISTATE\",\"ISUPPORTUSERNUMBER\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SICON\",\"SID\",\"SLOGO\",\"SORGNAME\",\"SORGTYPE\",\"SSYSTEMNAME\",\"SUPDATEUSERID\"],\"ORGORUSERANDDICTIONARIEREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SDICTIONARIEID\",\"SID\",\"SORGIDORUSERID\",\"SUPDATEUSERID\"],\"PLANLIBPROJECTS\":[\"ISTATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SPLANLIBID\",\"SPROJECTLIBID\",\"SREASON\",\"SUPDATEUSERID\",\"SUSERID\"]}";
        Map<String, Object> devMap = Guava.toMap(devJson);
        //for (String key : devMap.keySet()) {
        //    List<String> list = (List<String>) devMap.get(key);
        //    log.info("表：{}---字段：{}", key, list);
        //}
        //log.info("{}", devMap.size());
        String testJson = "{\"ORGDISABLEREF\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"PROJECTLIBATTACHS\":[\"IORDER\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"PLANLIBSATTACHS\":[\"IORDER\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"SECTIONS\":[\"DBUDGETAMOUNT\",\"DCOMMISSIONCOST\",\"DENGINEERINGCOST\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SBUDGETID\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SUPDATEUSERID\"],\"BIDS\":[\"DBIDAMOUNT\",\"IBIDDINGTYPE\",\"ILIMITDAY\",\"ISTATE\",\"LDBIDDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAGENCYORGID\",\"SBIDDER\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SID\",\"SIDCARD\",\"SMANAGER\",\"SNAME\",\"SPHONE\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SSECTIONID\",\"SUPDATEUSERID\",\"SWHEEL\"],\"SETTINGS\":[\"IMAXNUMBER\",\"ISTATE\",\"ISUPPORTUSERNUMBER\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SICON\",\"SID\",\"SLOGO\",\"SORGNAME\",\"SORGTYPE\",\"SSYSTEMNAME\",\"SUPDATEUSERID\"],\"FUNCTIONS\":[\"IJOINPROCESS\",\"IORDER\",\"ISTATE\",\"ISUPPORTPHONE\",\"ISUPPORTPROJECT\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SANDROIDMETHOD\",\"SBINDEVENT\",\"SBTNLOCATION\",\"SCREATEUSERID\",\"SDESC\",\"SICON\",\"SID\",\"SJOINPROCESSTABLE\",\"SNAME\",\"SPARENTID\",\"SPCMETHOD\",\"SUPDATEUSERID\"],\"PLANLIBPROJECTLOGS\":[\"ISTATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SPLANLIBID\",\"SPROJECTLIBID\",\"SREASON\",\"SUPDATEUSERID\",\"SUSERID\"],\"AUDITSETTINGS\":[\"IDIVIDINGLINE\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\"],\"FUNCTIONANDFUNCTIONGROUPREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONGROUPID\",\"SFUNCTIONID\",\"SID\",\"SUPDATEUSERID\"],\"INTERMEDIARYS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAUDITORGID\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SINTERMEDIARYORGID\",\"SORGTYPE\",\"SUPDATEUSERID\"],\"LOGORGDETAILS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"LUSERNUMBER\",\"SCREATEUSERID\",\"SID\",\"SLOGSETTINGID\",\"SORGID\",\"SUPDATEUSERID\"],\"LOGEXCEPTIONS\":[\"ITYPE\",\"LDTCREATETIME\",\"SEXCEPTION\",\"SID\",\"SIP\",\"SMESSAGE\"],\"FUNCTIONANDUSERREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"CALCULATIONS\":[\"DCALCULATIONAMOUNT\",\"DCONSTRUCTIONCOST\",\"DCONSTRUCTIONOTHERCOST\",\"DLOANINTEREST\",\"DRESERVECOST\",\"ISTATE\",\"LDAPPROVALDATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"PLANLIBSATTACHLOGS\":[\"IORDER\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"AREAS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDES\",\"SID\",\"SNAME\",\"SPARENTID\",\"SUPDATEUSERID\"],\"FUNCTIONGROUPANDUSERREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SFUNCTIONGROUPID\",\"SID\",\"SORGID\",\"SREFID\",\"SUPDATEUSERID\"],\"FUNCTIONGROUPS\":[\"ISTATE\",\"ISUPPORTPROJECT\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SFROMORGID\",\"SID\",\"SNAME\",\"SORGID\",\"SUPDATEUSERID\"],\"DICTIONARIES\":[\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SID\",\"SNAME\",\"SUPDATEUSERID\"],\"PROJECTLIBS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SAUDITORGID\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"LOGOPERATIONS\":[\"ITYPE\",\"LDTCREATETIME\",\"SDESC\",\"SID\",\"SIP\",\"SREQUESTCONTENT\",\"SRESPONSECONTENT\",\"SUSERID\"],\"ORGANDUSERREFS\":[\"IISPROJECTLEADER\",\"IPERMISSIONLEVEL\",\"ISTATE\",\"IUSERTYPE\",\"LDTCREATETIME\",\"LDTFIRSTTIME\",\"LDTLASTTIME\",\"LDTUPDATETIME\",\"LTOTAL\",\"SCREATEUSERID\",\"SDICTIONARIESID\",\"SDUTIES\",\"SID\",\"SMANAGERID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"],\"COMMONATTACHS\":[\"IORDER\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SNAME\",\"SPATH\",\"SUPDATEUSERID\"],\"TABLETPL\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SUPDATEUSERID\"],\"PLANLIBS\":[\"ISTATE\",\"IYEAR\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SUPDATEUSERID\"],\"BUDGETS\":[\"ISTATE\",\"LDAPPROVALDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"ORGS\":[\"IISDEPARTMENT\",\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"LUSERNUMBER\",\"SAREAID\",\"SCREATEUSERID\",\"SDES\",\"SID\",\"SNAME\",\"SPARENTID\",\"SUPDATEUSERID\"],\"SPECIALVIEWS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDIVID\",\"SFUNCTIONID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\"],\"MAILTPLS\":[\"ISTATE\",\"ITYPE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCONTENT\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"STITLE\",\"SUPDATEUSERID\"],\"APPROVALS\":[\"DESTIMATEAMOUNT\",\"ICONSTRUCTIONTYPE\",\"IDESIGNTYPE\",\"IINTERMEDIARYTYPE\",\"IPROSPECTINGTYPE\",\"ISTATE\",\"ISUPERVISIONTYPE\",\"LDAPPROVALDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"USERSETTINGS\":[\"ICLASSIFYNUM\",\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SUPDATEUSERID\",\"SUSERID\"],\"MAILSETTINGS\":[\"INEEDAUTH\",\"ISTATE\",\"ITIMEOUT\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SMAILSERVER\",\"SPASSWORD\",\"SSENDERADDR\",\"SSENDERNICK\",\"SUPDATEUSERID\",\"SUSERNAME\"],\"FEASIBILITYS\":[\"DESTIMATEAMOUNT\",\"ICONSTRUCTIONTYPE\",\"IDESIGNTYPE\",\"IINTERMEDIARYTYPE\",\"IPROSPECTINGTYPE\",\"ISTATE\",\"ISUPERVISIONTYPE\",\"LDAPPROVALDATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SAPPROVALNUM\",\"SAPPROVALORGID\",\"SCAPITALSOURCE\",\"SCREATEUSERID\",\"SDEPUTYORGID\",\"SDESC\",\"SID\",\"SNAME\",\"SPROJECTLIBSID\",\"SPROPRIETORORGID\",\"SUPDATEUSERID\"],\"APPROVALANDFEASIBILITYHISTORYS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCONTENT\",\"SCREATEUSERID\",\"SDATAID\",\"SID\",\"SUPDATEUSERID\"],\"LOGSETTINGS\":[\"IMAXNUMBER\",\"ISTATE\",\"ISUPPORTUSERNUMBER\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SICON\",\"SID\",\"SLOGO\",\"SORGNAME\",\"SORGTYPE\",\"SSYSTEMNAME\",\"SUPDATEUSERID\"],\"PLANLIBLOGS\":[\"ISTATE\",\"IYEAR\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SNAME\",\"SUPDATEUSERID\"],\"ORGORUSERANDDICTIONARIEREFS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SDESC\",\"SDICTIONARIEID\",\"SID\",\"SORGIDORUSERID\",\"SUPDATEUSERID\"],\"USERS\":[\"IGENDER\",\"ISTATE\",\"ITYPE\",\"LDBIRTHDAY\",\"LDGRADUATIONDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SADDRESS\",\"SCREATEUSERID\",\"SEMAIL\",\"SGRADUATESCHOOL\",\"SID\",\"SIDCARD\",\"SNAME\",\"SNICKNAME\",\"SPASSWORD\",\"SPHONE\",\"SSIGNATURE\",\"SUPDATEUSERID\",\"SUSERNAME\"],\"PLANLIBPROJECTS\":[\"ISTATE\",\"LDENDDATE\",\"LDSTARTDATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SPLANLIBID\",\"SPROJECTLIBID\",\"SREASON\",\"SUPDATEUSERID\",\"SUSERID\"],\"CHARGEORGS\":[\"ISTATE\",\"LDTCREATETIME\",\"LDTUPDATETIME\",\"SCREATEUSERID\",\"SID\",\"SORGID\",\"SUPDATEUSERID\",\"SUSERID\"]}";
        Map<String, Object> testMap = Guava.toMap(testJson);
        for (String key : devMap.keySet()) {
            List<String> devField = (List<String>) devMap.get(key);
            List<String> testField = (List<String>) testMap.get(key);
            if (testField != null) {
                List<String> fieldList = Guava.newArrayList();
                for (String field : devField) {
                    if (!testField.contains(field)) {
                        fieldList.add(field);
                    }
                }
                if (!fieldList.isEmpty()) {
                    //表差的字段
                    fields.put(key, fieldList);
                }
            } else {
                //差的表
                tables.add(key);
            }
        }
        tables.stream().sorted().forEach(System.out::println);
        log.warn("{}", tables);
        log.error("{}", fields);
    }
}