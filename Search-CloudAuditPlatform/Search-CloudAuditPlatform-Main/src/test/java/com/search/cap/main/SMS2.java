package com.search.cap.main;

import com.search.common.base.core.bean.Result;
import com.search.common.base.core.utils.Guava;
import com.search.common.base.core.utils.SMS;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class SMS2 {

    @Test
    public void sms() {
        StringBuilder sb = Guava.newStringBuilder();
        sb.append("就得不间断的去学习，去汲取知识 其实Java并没有想象中的那么难，首先想要入这个行，要做好一个心理准备，那就是你想走远点，，前期不能怕辛苦，不要闲下来就打LOL、吃鸡、王者农药，有空就得多看看各种开源项目的代码，API的设计方式，各大网站的设计架构，理解各个环节的作用。，不要再私信我初中生、高中生、中专生能不能学习Java了。 或许他们会觉得可以的。 补齐自己的知识视野。 当然这个行业也并不是什么门槛都没有下图是我更新过的自学表，分别分为4个阶段。按照这四个阶段平稳的去学习并在每一个阶段做完相应的项目和练习,反正我个人是认为不可行的，或许你可以去问问其他大神？提是你能学的下去，且通过查看网上的资料或视频能起码看得懂第一阶段的内容。如果第一阶段全职学习耗时2个月以上的话，我个人建议你就需要仔细考虑考虑是找一份工作是完全没有问题的 。当然，这里有个前否真的要人这个行业吧。因为这个时间节点还没能够搞明白第一阶段的内容的话，。IDE是集成开发环境，一般集成开发环境都会带有JDK，可以使用自带的JDK也可以使用我们下载的JDK，从我个人的经验来讲可能后续走起来会更加吃力不同的IDE配置不同。Java常用的IDE有");
        //sb.append("【何彦静】");
        try {
            Result result = SMS.sendSMS("18423452585", "就得不间断的去学习，去汲取知识 其实Java并没有想象中的那么难【何彦静】");
            //Result result = SMS.sendSubSMS("18423452585", sb.toString(), "【何彦静】");
            log.info("{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int sendSMS(String mobile, String content, String send_time) throws Exception {
        int result = -2;
        URL url = null;
        String CorpID = "CQJS000724";//账户名
        String Pwd = "123456@";//密码
        String send_content = URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");//发送内容
        log.info("queryUrl---2--{}", "https://sdk2.028lk.com/sdk2/BatchSend2.aspx?CorpID=" + CorpID + "&Pwd=" + Pwd + "&Mobile=" + mobile + "&Content=" + send_content + "&Cell=&SendTime=" + send_time);
        url = new URL("https://sdk2.028lk.com/sdk2/BatchSend2.aspx?CorpID=" + CorpID + "&Pwd=" + Pwd + "&Mobile=" + mobile + "&Content=" + send_content + "&Cell=&SendTime=" + send_time);
        BufferedReader in = null;
        try {
            log.info("开始发送短信手机号码为 ：{}", mobile);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            result = new Integer(in.readLine()).intValue();
        } catch (Exception e) {
            log.info("网络异常,发送短信失败！");
            result = -2;
        }
        log.info("开始发送短信手机号码为 ：{}", result);
        return result;
    }

    public static int sendSMS(String mobile, String content) throws Exception {
        return sendSMS(mobile, content, "");
    }
}
