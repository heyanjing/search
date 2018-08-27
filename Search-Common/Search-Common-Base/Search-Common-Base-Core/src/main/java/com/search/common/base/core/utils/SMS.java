package com.search.common.base.core.utils;

import com.search.common.base.core.bean.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class SMS {
    private static final Logger log = LoggerFactory.getLogger(SMS.class);

    public static Result sendSMS(String mobile, String content, String send_time) {
        Result result;
        BufferedReader in = null;
        try {
            Map<String, Object> params = Guava.newHashMap();
            params.put("CorpID", Info.SMS_USER);
            params.put("Pwd", Info.SMS_PWD);
            params.put("Mobile", mobile);
            params.put("Content", URLEncoder.encode(content.replaceAll("<br/>", " "), Info.SMS_ENCODE));
            params.put("Cell", "");
            params.put("SendTime", send_time);


            String queryString = Https.toQueryString(Info.SMS_BASEURL, params, true);
            URL url = new URL(queryString);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            int responseCode = new Integer(in.readLine()).intValue();
            log.info("queryUrl:{}-----返回码:{}", queryString, responseCode);
            if (responseCode > SMSState.MINUS_ZERO.getCode()) {
                result = Result.success();
            } else {
                result = Result.failure();
            }
            result.setCode(responseCode);
        } catch (Exception e) {
            result = Result.failure(SMSState.MINUS_TWO.getCode());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    public static Result sendSMS(String mobile, String content) throws Exception {
        return sendSMS(mobile, content, "");
    }

    public static Result sendSubSMS(String mobile, String content, String sign) throws Exception {
        Result r = Result.failure("发送内容不能为空");
        if (StringUtils.isNotBlank(content)) {
            int length = content.length();
            int count = length / 250 + 1;
            for (int i = 0; i < count; i++) {
                String sendContent = content.substring(250 * i, 250 * (i + 1) > length ? length : 250 * (i + 1));
                Result result = sendSMS(mobile, sendContent + sign);
                if (!result.isStatus()) {
                    return Result.failure(result.getCode(), "第" + (i + 1) + "段短信发送失败");
                } else {
                    if (i == count - 1) {
                        r = result;
                    }
                }

            }
            return r;
        }
        return r;
    }

    interface Info {
        public static final String SMS_USER = Configuration2.getString("sms.corpid", "CQJS000724");
        public static final String SMS_PWD = Configuration2.getString("sms.pwd", "123456@");
        public static final String SMS_ENCODE = Configuration2.getString("sms.encode", "GBK");
        public static final String SMS_BASEURL = Configuration2.getString("sms.baseurl", "https://sdk2.028lk.com/sdk2/BatchSend2.aspx");
    }

    /**
     * 大于0的数字	提交成功
     * -1	账号未注册
     * -2	其他错误
     * -3	帐号或密码错误
     * -5	余额不足，请充值
     * -6	定时发送时间不是有效的时间格式
     * -7	提交信息末尾未签名，请添加中文的企业签名【 】
     * -8	发送内容需在1到300字之间
     * -9	发送号码为空
     * -10	定时时间不能小于系统当前时间
     */
    enum SMSState {
        MINUS_ZERO(0, "成功与否的阈值"),
        MINUS_ONE(-1, "账号未注册"),
        MINUS_TWO(-2, "其他错误"),
        MINUS_THREE(-3, "帐号或密码错误"),
        MINUS_FIVE(-5, "余额不足，请充值"),
        MINUS_SIX(-6, "定时发送时间不是有效的时间格式"),
        MINUS_SEVEN(-7, "提交信息末尾未签名，请添加中文的企业签名【 】"),
        MINUS_EIGHT(-8, "发送内容需在1到300字之间"),
        MINUS_NINE(-9, "发送号码为空"),
        MINUS_TEN(-10, "定时时间不能小于系统当前时间"),
        OTHER(999, "成功");

        private int code;
        private String text;

        SMSState(int code, String text) {
            this.code = code;
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
