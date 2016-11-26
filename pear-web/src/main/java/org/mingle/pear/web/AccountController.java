/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.web;

import com.google.common.collect.Maps;
import org.mingle.pear.domain.Account;
import org.mingle.pear.persistence.query.QueryTemplate;
import org.mingle.pear.persistence.query.QueryType;
import org.mingle.pear.persistence.query.SortOrder;
import org.mingle.pear.service.AccountService;
import org.mingle.pear.util.DeleteStatus;
import org.mingle.pear.util.Sex;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RequestMapping("/accounts")
@Controller
@EnableAutoConfiguration
public class AccountController {
    @Inject
    private AccountService accountService;

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("sexTypes", Sex.values());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Account account, BindingResult bindingResult,
                         Model model, HttpServletRequest request) {
        Map<String, Object> fields = Maps.newHashMap();
        fields.put("name", account.getName());
        if (accountService.isExist(Account.class, fields))
            bindingResult.rejectValue("name", "", "This name already exists");
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "accounts/create";
        }
        model.asMap().clear();
        accountService.persist(account);
        return "redirect:/accounts/show/" + encodeUrlPathSegment(account.getId().toString(), request);
    }

    @RequestMapping(value = "/create", produces = "text/html")
    public String createForm(Model uiModel) {
        uiModel.addAttribute("account", new Account());
        return "accounts/create";
    }

    @RequestMapping(value = "/show/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("account", accountService.find(id));
        return "accounts/show";
    }

    @RequestMapping(value = "/list", produces = "text/html")
    public String list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
            @RequestParam(value = "sortOrder", required = false) SortOrder sortOrder,
            Model model) {
        QueryTemplate qt = QueryTemplate.create(QueryType.JQL, "SELECT t FROM Account t");
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1)
                    * sizeNo;
            qt.setFirstResult(firstResult);
            qt.setMaxResults(sizeNo);
            qt.append(QueryTemplate.buildOrderBy("t", sortFieldName, sortOrder));
            model.addAttribute("accounts", accountService.findDomains(qt));
            qt = QueryTemplate.create(QueryType.JQL, "SELECT COUNT(t.id) FROM Account t");
            float numberOfPages = (float) accountService.findCount(qt).intValue() / sizeNo;
            model.addAttribute("maxPages", (int) ((numberOfPages > (int) numberOfPages || numberOfPages == 0.0) ? numberOfPages + 1
                    : numberOfPages));
        } else {
            qt.append(QueryTemplate.buildOrderBy("t", sortFieldName, sortOrder));
            model.addAttribute("accounts", accountService.findDomains(qt));
        }
        return "accounts/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Account account, BindingResult bindingResult,
                         Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "accounts/update";
        }
        model.asMap().clear();
        accountService.merge(account);
        return "redirect:/accounts/show/" + encodeUrlPathSegment(account.getId().toString(), request);
    }

    @RequestMapping(value = "/update/{id}", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("account", accountService.find(id));
        return "accounts/update";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    public
    @ResponseBody
    DeleteStatus delete(@PathVariable("id") Long id,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "size", required = false) Integer size,
                        Model model) {
        try {
            accountService.remove(id);
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
