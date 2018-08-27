package com.search.cap.main.web.service.common;

import com.search.common.base.core.bean.Result;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.OnError;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by heyanjing on 2018/7/9 17:30.
 */
@ServerEndpoint(value = "/api/ws/{userId}", configurator = SpringConfigurator.class)
@Slf4j
//@Component
public class Websocket {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static long onlineCount = 0;

    /**
     * 记录每个用户下多个终端的连接
     */
    private static Map<String, Set<Websocket>> userSocket = new HashMap<>();

    /**
     * 需要session来对用户发送数据, 获取连接特征userId
     */
    private Session session;
    /**
     * 用户id
     */
    private String userId;

    /**
     * @param @param  userId 用户id
     * @param @param  session websocket连接的session属性
     * @param @throws IOException
     * @Title: onOpen
     * @Description: websocekt连接建立时的操作
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.warn("{}--sessionId={}", "连接", session.getId());
        this.session = session;
        this.userId = userId;
        //根据该用户当前是否已经在别的终端登录进行添加操作
        if (userSocket.containsKey(this.userId)) {
            log.info("当前用户id:{}已有其他终端登录", this.userId);
            //增加该用户set中的连接实例
            Set<Websocket> websockets = userSocket.get(this.userId);
            log.info("集合：{}---this:{}", websockets, this);
            if (!websockets.contains(this)) {
                log.info("{}", "不包含，添加当前websocket");
                websockets.add(this);
                onlineCount++;
            }
        } else {
            log.info("当前用户id:{}第一个终端登录", this.userId);
            Set<Websocket> addUserSet = new HashSet<>();
            addUserSet.add(this);
            userSocket.put(this.userId, addUserSet);
            onlineCount++;
        }
        log.info("用户{}登录的终端个数是为{}", userId, userSocket.get(this.userId).size());
        log.info("当前在线用户数为：{},所有终端个数为：{}", userSocket.size(), onlineCount);
    }

    /**
     * @Title: onClose
     * @Description: 连接关闭的操作
     */
    @OnClose
    public void onClose() {
        log.warn("{}", "关闭");
        //移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
        Set<Websocket> websockets = userSocket.get(this.userId);
        if (websockets.size() == 1) {
            //最后一个终端下线
            userSocket.remove(this.userId);
        } else {
            websockets.remove(this);
        }
        onlineCount--;
        log.info("用户{}登录的终端个数是为{}", this.userId, userSocket.get(this.userId) == null ? 0 : userSocket.get(this.userId).size());
        log.info("当前在线用户数为：{},所有终端个数为：{}", userSocket.size(), onlineCount);
    }

    /**
     * @param @param message 收到的消息
     * @param @param session 该连接的session属性
     * @Title: onMessage
     * @Description: 收到消息后的操作
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自用户id为：{}的消息：{}", this.userId, message);
        if (session == null) {
            log.info("session null");
        }
    }

    /**
     * @param @param session 该连接的session
     * @param @param error 发生的错误
     * @Title: onError
     * @Description: 连接发生错误时候的操作
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用户id为：{}的连接发送错误", this.userId);
        error.printStackTrace();
    }

    /**
     * @param @param  userId 用户id
     * @param @param  message 发送的消息
     * @param @return 发送成功返回true，反则返回false
     * @Title: sendMessageToUser
     * @Description: 发送消息给用户下的所有终端
     */
    public boolean sendMessageToUser(String userId, String message) {
        if (userSocket.containsKey(userId)) {
            log.info(" 给用户id为：{}的所有终端发送消息：{}", userId, message);
            for (Websocket ws : userSocket.get(userId)) {
                log.info("sessionId为:{}", ws.session.getId());
                try {
                    ws.session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info(" 给用户id为：{}发送消息失败", userId);
                    return false;
                }
            }
            return true;
        }
        log.info("发送错误：当前连接不包含id为：{}的用户", userId);
        return false;
    }

    public void clear() {
        userSocket.clear();
        onlineCount = 0;
    }

    public Result each() {
        Map<String, Object> map = new HashMap<>(16);
        userSocket.forEach((s, websockets) -> {
            log.info("用户id:{}的终端数为{}", s, websockets.size());
            map.put(s, websockets.size());
        });
        return Result.successWithData(map,"终端总数"+onlineCount);
    }

}
