package com.search.cap.main.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.cap.main.Capm;
import com.search.cap.main.Init;
import com.search.common.base.core.Constants;
import com.search.common.base.core.utils.Guava;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by heyanjing on 2018/3/16 10:32.
 */
@Component
@Slf4j
public class Commons {


    public static String getWorkingSql(String sql,String userIdPlaceholder) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select  temp2.processInstancesId,temp2.currentStepName, temp1.* from ( ");
        sb.append(sql);
        sb.append(" ) temp1,");
        sb.append(" ( select pin.SUSERID, pi.SDATAID,pi.SID as ProcessInstancesId,ps.SNAME as currentStepName from ProcessInstances pi,ProcessInstanceNodes pin ,ProcessSteps ps where pi.ISTATE=101 and pin.ISTATE=101 and pi.SID=pin.SPROCESSINSTANCEID and ps.ISTATE=1 and ps.SID=pi.SPROCESSSTEPID ) temp2");
        sb.append(" where temp1.dataId=temp2.SDATAID and  temp2.SUSERID=");
        sb.append(userIdPlaceholder);
        return sb.toString();
    }
    public static String getFinishedSql(String sql,String userIdPlaceholder) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select  temp2.processInstancesId,temp2.currentStepName,temp2.currentUserName,temp1.* from ( ");
        sb.append(sql);
        sb.append(" ) temp1,");
        sb.append(" (select distinct pin.SUSERID, pi.SDATAID,pi.SID as ProcessInstancesId  , ps.SNAME as currentStepName ,");
        sb.append(" (select u.SNAME from ProcessInstanceNodes pin2,USERS u where pin2.ISTATE=101 and u.ISTATE=1 and pin2.SUSERID=u.SID and pin2.SPROCESSINSTANCEID=pi.SID) as currentUserName");
        sb.append(" from ProcessInstances pi,ProcessInstanceNodes pin,ProcessSteps ps where pi.ISTATE!=103 and pin.ISTATE=102 and pi.SID=pin.SPROCESSINSTANCEID and ps.ISTATE=1 and  ps.SID=pi.SPROCESSSTEPID ) temp2");
        sb.append(" where temp1.dataId=temp2.SDATAID  and temp2.SUSERID!=(select u.SID from ProcessInstanceNodes pin2,USERS u where pin2.ISTATE=101 and u.ISTATE=1 and pin2.SUSERID=u.SID and pin2.SPROCESSINSTANCEID=temp2.processInstancesId) and  temp2.SUSERID=");
        sb.append(userIdPlaceholder);
        return sb.toString();
    }
    public static String getExceptionSql(String sql,String userIdPlaceholder) {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("select  temp2.processInstancesId,temp1.* from ( ");
        sb.append(sql);
        sb.append(") temp1,");
        sb.append(" (select distinct pin.SUSERID, pi.SDATAID ,pi.SID as ProcessInstancesId from ProcessInstances pi,ProcessInstanceNodes pin,ProcessSteps ps where pi.ISTATE=103 and ( pin.ISTATE=102 or pin.ISTATE=103) and pi.SID=pin.SPROCESSINSTANCEID and ps.ISTATE=1 and ps.SID=pi.SPROCESSSTEPID  ) temp2");
        sb.append(" where temp1.dataId=temp2.SDATAID and temp2.SUSERID=");
        sb.append(userIdPlaceholder);
        return sb.toString();
    }
    /**
     * 生成默认密码，身份证后6位
     *
     * @param idCard 身份证
     */
    public static String getDefaultPasswordByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            throw new RuntimeException("身份证不能为空");
        }
        return encode(idCardLast6(idCard));
    }

    public static String idCardLast6(String idCard) {
        return idCard.substring(12);
    }

    /**
     * 随机生成8位的默认密码
     */
    public static String getDefaultPassword() {
        //return encode(get8RandomCode());
        return encode("000000");
    }

    public static String getDefaultPasswordByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException("手机号码不能为空");
        }
        return encode(phoneLast6(phone));
    }

    public static String phoneLast6(String phone) {
        return phone.substring(5);
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return 密码加密
     */
    public static String encode(String password) {
        Sha512Hash sha512Hash = new Sha512Hash(password, null, 2);
        return sha512Hash.toString().substring(0, 32);
    }

    /**
     * 获取8位随机字符
     */
    public static String get8RandomCode() {
        return getRandomCode(8);
    }

    /**
     * 获取10位随机字符
     */
    public static String get10RandomCode() {
        return getRandomCode(10);
    }

    /**
     * 获取length 位随机字符
     *
     * @param length 多少位
     */
    public static String getRandomCode(int length) {
        try {
            StringBuilder sb = Guava.newStringBuilder();
            for (int j = 0; j < length; j++) {
                int index = RandomUtils.nextInt(0, 30);
                sb.append(Init.charList.get(index));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取8位随机数
     */
    public static String get6RandomNum() {
        return getRandomNum(6);
    }

    /**
     * 获取4位随机数
     */
    public static String get4RandomNum() {
        return getRandomNum(4);
    }


    /**
     * 获取length 位随机数字
     *
     * @param length 多少位
     */
    public static String getRandomNum(int length) {
        try {
            StringBuilder sb = Guava.newStringBuilder();
            for (int j = 0; j < length; j++) {
                int index = RandomUtils.nextInt(0, 10);
                sb.append(Init.numList.get(index));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return 获取压缩包名称
     */
    public static String getZipName() {
        return getFilePrefic() + get8RandomCode() + ".zip";
    }

    /**
     * @return 文件前缀
     */
    public static String getFilePrefic() {
        return LocalDateTime.now().format(Capm.FILE_DATE_TIME_FORMATTER) + Constants.UNDERLINE;
    }

    /**
     * 发送邮件。
     *
     * @param to      收件人。
     * @param subject 主题。
     * @param text    内容。
     * @throws MessagingException           消息异常。
     * @throws UnsupportedEncodingException 不支持的字符编码异常。
     * @author Chrise 2018年3月28日
     */
    public static void sendMail(String to, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        sendMail(new String[]{to}, subject, text);
    }

    /**
     * 发送邮件。
     *
     * @param to      收件人。
     * @param subject 主题。
     * @param text    内容。
     * @throws MessagingException           消息异常。
     * @throws UnsupportedEncodingException 不支持的字符编码异常。
     * @author Chrise 2018年3月28日
     */
    public static void sendMail(String[] to, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = Capm.Mail.SENDER_OBJ.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(Capm.Mail.SENDER_ADDR, Capm.Mail.SENDER_NICK);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        Capm.Mail.SENDER_OBJ.send(message);
    }

    /**
     * @param classPathFile /config/config.json
     */
    public static String readFileAsString(String classPathFile) {
        URL url =  Commons.class.getResource(classPathFile);
        File file = new File(url.getFile());
        try {
            return FileUtils.readFileToString(file, "utf-8").replaceAll("/\\*[\\s\\S]*?\\*/", "");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    /**
     * Str转List
     * @author Liangjing 2018年6月23日
     * @param json
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public static <T> List<T> strToList(Class<?> cls,String json) throws JsonParseException, JsonMappingException, IOException{
    	ObjectMapper mapper = new ObjectMapper(); 
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, cls);
		return mapper.readValue(json, javaType);
    }
    /**
     * Str转Obj
     * @author Liangjing 2018年6月25日
     * @param cls
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public static <T> T strToObj(Class<?> cls,String json) throws JsonParseException, JsonMappingException, IOException{
    	ObjectMapper mapper = new ObjectMapper();
    	return (T) mapper.readValue(json, cls);
    }
}
