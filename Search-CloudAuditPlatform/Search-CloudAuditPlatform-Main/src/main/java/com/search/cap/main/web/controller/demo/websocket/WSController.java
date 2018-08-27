package com.search.cap.main.web.controller.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by heyanjing on 2018/5/7 16:04.
 */
@RestController
@Slf4j
public class WSController {

    @Autowired(required = false)
    private SimpMessagingTemplate template;

    //**********************************************************************************************************************************
    //************************************直接通过后台发送***************************************************************************************
    //**********************************************************************************************************************************
    // HETODO: 2018/7/4 16:22 websocket推送给多用户和单用户的方法
    @RequestMapping(path = "/ws/wssend", method = RequestMethod.GET)
    public Greeting send() {
        Greeting greeting = new Greeting("多用户消息");
        template.convertAndSend("/wstopic/all", greeting);
        return greeting;
    }

    @RequestMapping(path = "/ws/wssend2", method = RequestMethod.GET)
    public Greeting send2() {
        Greeting greeting = new Greeting("单用户消息");
        template.convertAndSendToUser(0 + "", "/message", greeting);
        return greeting;
    }
//**********************************************************************************************************************************
    //************************************前台，后台互动***************************************************************************************
    //**********************************************************************************************************************************

    @MessageMapping("/all")
    @SendTo("/wstopic/all")
    public Greeting greeting(@Header("atytopic") String topic, String name, @Headers Map<String, Object> headers) {
        System.out.println("connected successfully....");
        System.out.println(topic);
        System.out.println(name);
        System.out.println(headers);
        return new Greeting("Hello, " + name + "!");
    }

    @MessageMapping("/message")
    @SendToUser("/message")
    public Greeting handleSubscribe(String msg) {
        log.info("{}", msg);
        return new Greeting(msg);
    }


}
