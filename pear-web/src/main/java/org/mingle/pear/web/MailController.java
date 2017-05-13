/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mingle.pear.web;

import org.mingle.pear.properties.PropertiesMail;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/mail")
@RestController
public class MailController {
    @Inject private MailSender mailSender;
    @Inject private PropertiesMail propMail;

    @RequestMapping(value = "/send/{address}", method = {RequestMethod.GET, RequestMethod.POST})
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
