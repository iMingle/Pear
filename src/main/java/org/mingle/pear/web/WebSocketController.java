package org.mingle.pear.web;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	@Inject
	private SimpMessagingTemplate template;
	
	@MessageMapping("/greeting")
	@SendTo("/topic/welcome")
	public String welcome(String name) {
//		this.template.convertAndSend("/topic/welcome", "Welcome " + name + "!");
		return "Welcome " + name + "!";
	}
	
}
