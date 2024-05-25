package com.neo.core.controllers;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
// hàm có rheer lấy đươc  User disconnected with session ID: snmwjnhn nhưng không lấy được id đã kết nối nên
    // không thể sủ dụng đưọc.
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
//            String sessionId = headerAccessor.getSessionId();
//            // Handle user offline event
//            System.out.println("User disconnected with session ID: " + sessionId);
//            // Here you can call your API to notify that the user is offline
//        }
    }
}
