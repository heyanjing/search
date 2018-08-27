package com.search.cap.main;

import com.search.common.base.core.utils.Configuration2;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by heyanjing on 2018/3/1 10:16.
 */
public class Capm {
    public static final boolean DEBUG = Configuration2.getBoolean("app.debug", true);

    public static final String VCODE = "_code";

    /**
     * 按年月日区分文件
     */
    public static final String FILE_DATE = "yyyy/MM/dd";
    public static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern(FILE_DATE);
    public static final String FILE_DATE_TIME = "yyyyMMddHHmmssSSS";
    public static final DateTimeFormatter FILE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FILE_DATE_TIME);
    /**
     * 系统错误提示
     */
    public static final String SYSTEM_ERROR = "系统繁忙,请稍后再试";
    /**
     * 手机验证码缓存
     */
    public static final String PHONE_VCODE_CACHE = "phoneVcodeCache";
    /**
     * 二维码验证缓存
     */
    public static final String QR_VCODE_CACHE = "qrVcodeCache";
    /**
     * 用户登陆错误次数缓存
     */
    public static final String USERNAME_CACHE = "userNameCache";
    /**
     * 用户输入邮箱缓存
     */
    public static final String EMAI_CACHE = "emailVcodeCache";
    /**
     * 文件分块检查缓存
     */
    public static final String FILE_CHECK_CACHE = "fileCheckCache";
    /**
     * 登陆重试次数
     */
    public static final int LIMIT_TIMES = 3;
    /**
     * 提交信息末尾未签名，请添加中文的企业签名【 】
     */
    public static final String SIGNATURE = "【协成科技】";


    /**
     * 下面是系统设置里面的相关变量
     */
    public static String ICON = null;
    public static String LOGO = null;
    public static String ORG_NAME = null;
    public static String SYSTEM_NAME = null;
    public static Integer SUPPORT_USER_NUMBER = null;
    public static Integer MAX_NUMBER = null;
    public static List<String> ORG_TYPE = null;


    public interface Server {
        public static final String BASE_URL = Configuration2.getString("server.base.url", "http://192.168.70.110/main");
    }

    public interface User {
        /**
         * 生成用户名允许的循环次数
         */
        public static final Integer USERNAME_TIME = Configuration2.getInteger("user.name.time", 5);

    }

    /**
     * 文件上传相关配置
     */
    public interface Upload {
        public static final String NETWORK_ROOT = Configuration2.getString("upload.file.network.root", "/upload");
        public static final String ROOT = Configuration2.getString("upload.file.root", "D:\\upload");
        public static final String TEMP = Configuration2.getString("upload.file.temp", "D:\\upload\\temp");
        public static final Long MAX_SIZE = Configuration2.getLong("upload.file.max.size", 10737418240L);
        public static final Long MAX_REQUEST_SIZE = Configuration2.getLong("upload.file.max.request.size", 21474836480L);
        public static final String SERVER_NAME = Configuration2.getString("upload.file.server.name", "nodedev");
    }

    /**
     * 多服务器时，nginx的配置
     */
    public interface Nginx {
        public static final Boolean SINGLE = Configuration2.getBoolean("nginx.service.mode.single", true);
        public static final Boolean WIRTE = Configuration2.getBoolean("nginx.intranet.write", false);
        public static final String IP = Configuration2.getString("nginx.intranet.ip", "192.168.70.110");
        public static final String DOMAIN = Configuration2.getString("nginx.intranet.domain", "nginx.search.com");
        public static final String HOST_PATH = Configuration2.getString("nginx.host.path", "C:\\Windows\\System32\\drivers\\etc");
        public static final String HOST_NAME = Configuration2.getString("nginx.host.name", "HOSTS");
    }

    /**
     * ftp配置
     */
    public interface Ftp {
        public static final String IP = Configuration2.getString("ftp.ip", "192.168.70.110");
        public static final Integer PORT = Configuration2.getInteger("ftp.port", 21);
        public static final String USERNAME = Configuration2.getString("ftp.username", "heyanjing");
        public static final String PASSWORD = Configuration2.getString("ftp.password", "heyanjing");
    }
    /**
     * ueditor的配置文件
     */
    public interface Ueditor {
        public static final String CONFIG_PATH= Configuration2.getString("ueditor.config.path", "/config/config.json");
    }

    /**
     * 邮件相关。
     *
     * @author Chrise
     */
    public static class Mail {
        public static JavaMailSenderImpl SENDER_OBJ = null;
        public static String SENDER_ADDR = null;
        public static String SENDER_NICK = null;
    }


}
