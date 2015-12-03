package org.mingle.pear.web;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@MessageMapping("/greet")
	@SendTo("/topic/welcome")
	public String welcome(String name) {
		return "Welcome " + name + "!";
	}
	
}
