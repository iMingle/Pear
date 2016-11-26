/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
//	@Inject
//	private SimpMessagingTemplate template;

    @MessageMapping("/greeting")
    @SendTo("/topic/welcome")
    public String welcome(String name) {
//		this.template.convertAndSend("/topic/welcome", "Welcome " + name + "!");
        return "Welcome " + name + "!";
    }

}
