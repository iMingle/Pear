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

import com.alibaba.fastjson.JSON;
import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;
import org.mingle.pear.dto.Page;
import org.mingle.pear.properties.PropertiesMail;
import org.mingle.pear.service.AccountService;
import org.mingle.pear.util.DeleteStatus;
import org.mingle.pear.util.Sex;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author mingle
 */
@RequestMapping("/accounts")
@Controller
public class AccountController {
    @Resource private AccountService accountService;
    @Resource private JavaMailSenderImpl mailSender;
    @Resource private PropertiesMail propMail;
    @Resource private TemplateEngine templateEngine;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("sexTypes", Sex.mapValue());
    }

    @PostMapping(value = "/create", produces = "text/html")
    public String create(Account account, BindingResult bindingResult,
                         Model model, HttpServletRequest request) {
        if (accountService.isNameExist(account.getName()))
            bindingResult.rejectValue("name", "", "This name already exists");
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "accounts/create";
        }
        model.asMap().clear();
        accountService.createAccount(account);
        return "redirect:/accounts/list";
    }

    @GetMapping(value = "/create", produces = "text/html")
    public String createForm(Model uiModel) {
        uiModel.addAttribute("account", new Account());
        return "accounts/create";
    }

    @GetMapping(value = "/show/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model model) {
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setId(id);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        model.addAttribute("account", accounts.isEmpty() ? null : accounts.get(0));
        return "accounts/show";
    }

    @GetMapping(value = "/list", produces = "text/html")
    public String list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model model) {
        if (Objects.isNull(page))
            page = 1;
        if (Objects.isNull(size))
            size = 10;
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setPageNumber(page);
        queryParam.setPageSize(size);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        model.addAttribute("accounts", Page.<Account>builder()
                .pageNumber(page)
                .pageSize(size)
                .totalCount(accountService.countAccount(queryParam))
                .items(accounts).build());
        return "accounts/list";
    }

    @PostMapping(value = "/email/{id}", produces = "text/html")
    public @ResponseBody boolean sendEmail(@PathVariable Long id) {
        if (Objects.isNull(id))
            return false;

        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setId(id);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        if (accounts.isEmpty())
            return false;

        Account account = accounts.get(0);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("测试邮件");
            helper.setFrom(propMail.getUsername());
            helper.setTo(account.getEmail());
            helper.setCc(account.getEmail());
            helper.setBcc(account.getEmail());
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariables(JSON.parseObject(JSON.toJSONString(account)));
            String process = templateEngine.process("mail/table.html", context);
            helper.setText(process,true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping(value = "/update", produces = "text/html")
    public String update(Account account, BindingResult bindingResult,
                         Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "accounts/update";
        }
        model.asMap().clear();
        accountService.updateAccount(account);
        return "redirect:/accounts/show/" + encodeUrlPathSegment(account.getId().toString(), request);
    }

    @GetMapping(value = "/update/{id}", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setId(id);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        model.addAttribute("account", accounts.isEmpty() ? null : accounts.get(0));
        return "accounts/update";
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json; charset=UTF-8")
    public @ResponseBody DeleteStatus delete(@PathVariable("id") Long id,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "size", required = false) Integer size,
                                             Model model) {
        try {
            accountService.deleteAccount(id);
            model.asMap().clear();
            model.addAttribute("page", (page == null) ? "1" : page.toString());
            model.addAttribute("size", (size == null) ? "5" : size.toString());
            return DeleteStatus.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return DeleteStatus.ERROR;
        }
    }

    private String encodeUrlPathSegment(String pathSegment,
                                        HttpServletRequest request) {
        String enc = request.getCharacterEncoding();
        if (enc == null)
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;

        pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        return pathSegment;
    }
}
