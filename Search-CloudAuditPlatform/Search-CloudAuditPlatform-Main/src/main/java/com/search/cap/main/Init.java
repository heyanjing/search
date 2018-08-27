package com.search.cap.main;

import com.search.cap.main.common.enums.Nums;
import com.search.cap.main.entity.Mailsettings;
import com.search.cap.main.entity.Settings;
import com.search.cap.main.web.service.settings.MailSettingsService;
import com.search.cap.main.web.service.settings.SettingsService;
import com.search.common.base.core.Constants;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by heyanjing on 2017/12/18 10:45.
 * <p>
 * 注入的context、this.servletContext=servletContext;和获取的webApplicationContext.getServletContext()是同一个对象
 * ContextLoader.getCurrentWebApplicationContext().getServletContext()
 * 该方法需要spring容器初始化完成才能使用
 */
@Component
@Slf4j
@SuppressWarnings("unused")
public class Init implements ApplicationContextAware/*, ServletContextAware */ {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final int CHAR_START = 51;
    public static final int CHAR_END = 90;
    public static final int NUM_START = 48;
    public static final int NUM_END = 57;
    /**
     * 生成用户名的30位字符
     */
    public static List<String> charList = Guava.newArrayList();
    /**
     * 短信验证码的10位数字
     */
    public static List<String> numList = Guava.newArrayList();

    @Autowired(required = false)
    private ServletContext context;
    private ServletContext servletContext;
    private ApplicationContext applicationContext;

    @Autowired
    private SettingsService settingsService;
    @Autowired
    private MailSettingsService mailSettingsService;

    @PostConstruct
    public void init() {
        File uploadRoot = new File(Capm.Upload.ROOT);
        if (!uploadRoot.exists()) {
            uploadRoot.mkdirs();
        }
        File uploadServerName = new File(uploadRoot, Capm.Upload.SERVER_NAME);
        if (!uploadServerName.exists()) {
            uploadServerName.mkdirs();
        }

        File uploadTemp = new File(Capm.Upload.TEMP);
        if (!uploadTemp.exists()) {
            uploadTemp.mkdirs();
        }
        if (!Capm.Nginx.SINGLE && Capm.Nginx.WIRTE) {
            File hostFile = new File(Capm.Nginx.HOST_PATH, Capm.Nginx.HOST_NAME);
            try {
                FileWriter writer = new FileWriter(hostFile, true);
                writer.write("\r\n" + Capm.Nginx.IP + " " + Capm.Nginx.DOMAIN);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //加入全局常量
        if (context != null) {
            String ctx = context.getContextPath();
            String staticc = "/static";
            String lib = staticc + "/lib";
            context.setAttribute("CTX", ctx);
            context.setAttribute("STATIC", staticc);
            context.setAttribute("IMG", staticc + "/img");
            context.setAttribute("JS", staticc + "/js");
            context.setAttribute("CSS", staticc + "/css");
            context.setAttribute("LIB", lib);
            context.setAttribute("V", LocalDateTime.now().format(formatter));
            log.debug("{}", context);
        }
        //生成30位的基础字符
        for (int i = CHAR_START; i < CHAR_END; i++) {
            boolean b = (i <= 57 || i >= 65) && i != 73 && i != 79;
            if (b) {
                charList.add(String.valueOf((char) i).toLowerCase());
            }
        }
        //生成10数字验证码
        for (int i = NUM_START; i <= NUM_END; i++) {
            numList.add(String.valueOf((char) i));
        }
        //系统设置
        Settings settings = this.settingsService.getSettings();
        log.warn("系统设置：{}", settings);
        changeSettingStaticValue(settings);

        //邮件设置
        Mailsettings mailSettings = this.mailSettingsService.getMailSettings();
        log.warn("邮件设置：{}", mailSettings);
        changeMailSettingsStaticValue(mailSettings);
    }

    public static void changeSettingStaticValue(Settings settings) {
        if (settings != null) {
            Capm.ICON = settings.getSicon();
            Capm.LOGO = settings.getSlogo();
            Capm.ORG_NAME = settings.getSorgname();
            Capm.SYSTEM_NAME = settings.getSsystemname();
            Capm.SUPPORT_USER_NUMBER = settings.getIsupportusernumber();
            Capm.MAX_NUMBER = settings.getImaxnumber();
            if (StringUtils.isNotBlank(settings.getSorgtype())) {
                Capm.ORG_TYPE = Arrays.asList(settings.getSorgtype().split(Constants.COMMA));
            }
        }
    }

    /**
     * 改变邮件设置静态值。
     *
     * @param settings 邮件设置对象。
     * @author Chrise 2018年3月28日
     */
    public static void changeMailSettingsStaticValue(Mailsettings settings) {
        if (settings != null) {
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.timeout", settings.getItimeout().toString());
            prop.setProperty("mail.smtp.auth", (settings.getIneedauth().intValue() == Nums.YES.getValue().intValue()) ? "false" : "false");

            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(settings.getSmailserver());
            sender.setUsername(settings.getSusername());
            sender.setPassword(settings.getSpassword());
            sender.setDefaultEncoding("UTF-8");
            sender.setJavaMailProperties(prop);

            Capm.Mail.SENDER_OBJ = sender;
            Capm.Mail.SENDER_ADDR = settings.getSsenderaddr();
            Capm.Mail.SENDER_NICK = settings.getSsendernick();
        }
    }

    @PreDestroy
    public void close() {
        if (context != null) {
            context.removeAttribute("CTX");
            context.removeAttribute("STATIC");
            context.removeAttribute("IMG");
            context.removeAttribute("JS");
            context.removeAttribute("CSS");
            context.removeAttribute("LIB");
            context.removeAttribute("V");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
        //ServletContext servletContext = webApplicationContext.getServletContext();
        this.applicationContext = applicationContext;
    }

   /* @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }*/


    //*********************************************************heyanjing--start*******************************************************************************************************************************
    //*********************************************************heyanjing--end*********************************************************************************************************************************
    //*********************************************************wangjinbing--start*****************************************************************************************************************************
    //*********************************************************wangjinbing--end*******************************************************************************************************************************
    //*********************************************************lirui--start***********************************************************************************************************************************
    //*********************************************************lirui--end*************************************************************************************************************************************
    //*********************************************************chenjunhua--start******************************************************************************************************************************
    //*********************************************************chenjunhua--end********************************************************************************************************************************
    //*********************************************************huanghao--start********************************************************************************************************************************
    //*********************************************************huanghao--end**********************************************************************************************************************************
    //*********************************************************liangjing--start*******************************************************************************************************************************
    //*********************************************************liangjing--end*********************************************************************************************************************************
    //*********************************************************yuanxiaojun--start*****************************************************************************************************************************
    //*********************************************************yuanxiaojun--end*******************************************************************************************************************************

}
