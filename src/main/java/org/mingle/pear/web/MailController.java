package org.mingle.pear.web;
import javax.inject.Inject;

import org.mingle.pear.properties.PropertiesMail;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mail")
@RestController
public class MailController {
	@Inject
	private MailSender mailSender;
	@Inject
	private PropertiesMail propMail;
	
	@RequestMapping(value = "/send/{address}", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean send(@PathVariable String address) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(propMail.getUsername());
		mailMessage.setTo(address);
		mailMessage.setSubject("test send mail");
		mailMessage.setText("hello");
		try {
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
