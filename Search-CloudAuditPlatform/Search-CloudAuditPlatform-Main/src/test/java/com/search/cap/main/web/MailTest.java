package com.search.cap.main.web;

import com.search.common.base.test.BaseTest;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by heyanjing on 2017/12/8 15:09.
 */
@Slf4j
public class MailTest extends BaseTest {
    @Autowired
    JavaMailSender qqMailSender;
    @Autowired
    //@Qualifier(value = "m163MailSender")
            JavaMailSender m163MailSender;

    /**
     * 原生的邮件发送写法
     */
    @Test
    public void ss() throws Exception {
        // 配置
        Properties prop = new Properties();
        // 设置邮件服务器主机名，这里是163
        prop.put("mail.host", "smtp.163.com");
        // 发送邮件协议名称
        prop.put("mail.transport.protocol", "smtp");
        // 是否认证
        prop.put("mail.smtp.auth", true);

        // SSL加密
        MailSSLSocketFactory sf = null;
        sf = new MailSSLSocketFactory();
        // 设置信任所有的主机
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建会话对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            // 认证信息，需要提供"用户账号","授权码"
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("18423452585@163.com", "heyanjing890919");
            }
        });
        // 是否打印出debug信息
        session.setDebug(true);

        // 创建邮件
        Message message = new MimeMessage(session);
        // 邮件发送者
        message.setFrom(new InternetAddress("18423452585@163.com"));
        // 邮件接受者
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("993912685@qq.com"));
        // 邮件主题
        message.setSubject("激活邮件");
        String content = "<html><head></head><body><h1>请点击连接激活</h1><h3><a href='http://localhost:8080/active?code="
                + 1 + "'>http://localhost:8080/active?code=" + 1 + "</href></h3></body></html>";
        message.setContent(content, "text/html;charset=UTF-8");
        // Transport.send(message);
        // 邮件发送
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    @Test
    public void jdbclog() {
        log.info("helloworld");   //不会被写入数据库
    }

    /**
     * 邮箱注册验证
     */
    @Test
    public void send0() {
        StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！\n");
        sb.append("http://localhost:8080/springmvc/user/register?action=activate&email=");
        sb.append("email");
        sb.append("&validateCode=");
        sb.append("validateCode");
        SimpleMailMessage mail = new SimpleMailMessage();
        try {
            mail.setFrom("1366162208@qq.com");// 发送者,
            mail.setTo("993912685@qq.com");// 接受者
            mail.setSubject("测试主题");// 主题
            mail.setText(sb.toString());// 邮件内容
            qqMailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 存文本
     */
    @Test
    public void send1() {
        SimpleMailMessage mail = new SimpleMailMessage();
        try {
            mail.setFrom("1366162208@qq.com");// 发送者,
            mail.setTo("993912685@qq.com");// 接受者
            mail.setSubject("测试主题");// 主题
            mail.setText("发送邮件内容测试");// 邮件内容
            qqMailSender.send(mail);
            mail.setFrom("18423452585@163.com");
            m163MailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 带附件
     */
    @Test
    public void send2() {
        MimeMessage message = qqMailSender.createMimeMessage();
        try {
            // 你需要使用true作为标记来指出你多条信息所需要发送的内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1366162208@qq.com");
            helper.setTo("993912685@qq.com");
            helper.setText("Check out this image!");
            helper.setSubject("附件");
            // 让我们来把臭名昭著的windows示例文件附件上(这次我们已经复制到c:/)
            FileSystemResource file = new FileSystemResource(new File("D:\\Temp/2.jpg"));
            helper.addAttachment("2.jpg", file);
            qqMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内嵌资源
     */
    @Test
    public void send3() {
        MimeMessage message = qqMailSender.createMimeMessage();
        try {
            // 你需要使用true作为标记来指出你多条信息所需要发送的内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1366162208@qq.com");
            helper.setTo("993912685@qq.com");
            helper.setText("<html><body><img src='cid:id'></body></html>", true);
            helper.setSubject("附件");
            // 让我们来把臭名昭著的windows示例文件附件上(这次我们已经复制到c:/)
            FileSystemResource file = new FileSystemResource(new File("D:\\Temp/2.jpg"));
            //指定资源id
            helper.addInline("id", file);
            qqMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
