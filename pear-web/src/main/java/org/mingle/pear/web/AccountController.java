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

import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;
import org.mingle.pear.service.AccountService;
import org.mingle.pear.util.DeleteStatus;
import org.mingle.pear.util.Sex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author mingle
 */
@RequestMapping("/accounts")
@Controller
public class AccountController {
    @Inject private AccountService accountService;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("sexTypes", Sex.values());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "text/html")
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
        return "redirect:/accounts/show/" + encodeUrlPathSegment(account.getId().toString(), request);
    }

    @RequestMapping(value = "/create", produces = "text/html")
    public String createForm(Model uiModel) {
        uiModel.addAttribute("account", new Account());
        return "accounts/create";
    }

    @RequestMapping(value = "/show/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model model) {
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setId(id);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        model.addAttribute("account", accounts.isEmpty() ? null : accounts.get(0));
        return "accounts/show";
    }

    @RequestMapping(value = "/list", produces = "text/html")
    public String list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            Model model) {
        AccountQueryParam queryParam = new AccountQueryParam();
        model.addAttribute("accounts", accountService.queryAccounts(queryParam));
        return "accounts/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "text/html")
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

    @RequestMapping(value = "/update/{id}", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        AccountQueryParam queryParam = new AccountQueryParam();
        queryParam.setId(id);
        List<Account> accounts = accountService.queryAccounts(queryParam);
        model.addAttribute("account", accounts.isEmpty() ? null : accounts.get(0));
        return "accounts/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
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

        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
}
