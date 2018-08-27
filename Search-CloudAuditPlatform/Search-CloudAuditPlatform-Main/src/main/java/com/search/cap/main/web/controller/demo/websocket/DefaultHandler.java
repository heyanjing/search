package com.search.cap.main.web.controller.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * Created by heyanjing on 2018/5/8 14:27.
 */
@Slf4j
public class DefaultHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("{}", request);
        log.info("{}", wsHandler);
        log.info("{}", attributes);
        Principal principal = super.determineUser(request, wsHandler, attributes);
        log.info("{}", principal);
        //principal = (Principal) Shiros.getCurrentUser();
        //log.info("{}", principal);
        return principal;
    }

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map attributes) throws Exception {
        log.error("{}", "DefaultHandler");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest
                    = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest
                    .getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
        }
        return true;
    }
}
