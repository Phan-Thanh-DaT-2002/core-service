package com.neo.core.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.neo.core.models.NotifyTopic;
import com.neo.core.models.NotifyUser;

@Controller
public class WebSocketController {
    
	@MessageMapping("/notify-to-handler")
	@SendTo("/topic/notify-to-handler")
	public NotifyTopic greeting(NotifyUser user) throws Exception {
	    return new NotifyTopic(user.getMessage(), user.getUsername());
	}
}
