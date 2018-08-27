package com.search.cap.main.web.service.common;

import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by heyanjing on 2018/7/9 17:32.
 */
@Service
@Slf4j
public class WebsocketService {

    private Websocket apiWebsocket = new Websocket();

    /**
     * 手机端      消息提醒
     *
     * @param userId 用户id
     * @param msg    要发送的消息
     */
    public boolean apiSend2User(String userId, String msg) {
        log.info("向用户{}的消息：{}", userId, msg);
        if (this.apiWebsocket.sendMessageToUser(userId, msg)) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * 清除
     */
    public boolean clear() {
        this.apiWebsocket.clear();
        return true;
    }

    /**
     * 遍历当前用户和对应的终端
     */
    public Result each() {
        return this.apiWebsocket.each();
    }
    //@Autowired
    //private SimpMessagingTemplate template;
    ///**
    // * pc端    消息提醒
    // *
    // * @param userId 用户id
    // * @param msg    要发送的消息
    // */
    //public boolean pcSend2User(String userId, String msg) {
    //    log.info("向用户{}的消息：{}", userId, msg);
    //    try {
    //        this.template.convertAndSendToUser(userId, "/message", msg);
    //        return true;
    //    } catch (MessagingException e) {
    //        e.printStackTrace();
    //        return false;
    //    }
    //}
    //
    ///**
    // * @param userId 用户id
    // * @param msg    要发送的消息
    // */
    //public Result send2User(String userId, String msg) {
    //    Result result = Result.failure();
    //    if (this.apiSend2User(userId, msg) || this.pcSend2User(userId, msg)) {
    //        result.setStatus(true);
    //    }
    //    return result;
    //
    //}
}