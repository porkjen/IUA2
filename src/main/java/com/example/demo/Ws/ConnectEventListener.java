package com.example.demo.Ws;

import com.sun.security.auth.UserPrincipal;
import com.example.demo.Util.LogAssist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.List;

/**
 * 連線事件監聽
 */
@Component
public class ConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebSocketSessions sessions;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        //String user = accessor.getNativeHeader("user").get(0);
        List<String> userHeaders = accessor.getNativeHeader("user");
        String user = "Empyt";
        if (userHeaders != null && !userHeaders.isEmpty()) {
            user = userHeaders.get(0);
        } else {
            System.out.println("Empty");
        }

        String sessionId = accessor.getSessionId();
        sessions.registerSessionId(user, sessionId);
        LogAssist.logInfo(logger, "user login, user:{}, sessionId:{}", user, sessionId);
        LogAssist.logInfo(logger, sessions.toString());
        System.out.println(sessions.getHowMany());

    }

}

