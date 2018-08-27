package com.search.cap.main.web.controller.demo.websocket;

import com.search.cap.main.web.BaseControllers;
import com.search.cap.main.web.service.common.WebsocketService;
import com.search.cap.main.web.service.demo.WSMessageService;
import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by heyanjing on 2018/7/9 17:34.
 */
@Controller
@RequestMapping("/demo/ws")
@Slf4j
public class WebsocketController extends BaseControllers {
    @Autowired
    private WSMessageService wsMessageService;
    @Autowired
    private WebsocketService websocketService;

    /**
     * /demo/ws/test
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test(@RequestParam(value = "userId") Long userId, @RequestParam(value = "message") String message) {
        log.debug("收到发送请求，向用户{}的消息：{}", userId, message);
        if (wsMessageService.sendToAllTerminal(userId, message)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    /**
     * /demo/ws/index
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/demo/websocket/ws";
    }

    /**
     * /demo/ws/test2
     */
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseBody
    public String test2(String userId, String msg) {
        log.info("收到发送请求，向用户{}的消息：{}", userId, msg);
        if (this.websocketService.apiSend2User(userId, msg)) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    /**
     * /demo/ws/clear
     */
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    @ResponseBody
    public String clear() {

        if (this.websocketService.clear()) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }

    /**
     * /demo/ws/each
     */
    @RequestMapping(value = "/each", method = RequestMethod.GET)
    @ResponseBody
    public Result each() {
        return this.websocketService.each();
    }

    /**
     * /demo/ws/index2
     */
    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2() {
        return "/demo/websocket/ws2";
    }
}
