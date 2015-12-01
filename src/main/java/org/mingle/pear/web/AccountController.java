package org.mingle.pear.web;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mingle.pear.domain.Account;
import org.mingle.pear.persistence.query.QueryTemplate;
import org.mingle.pear.persistence.query.QueryType;
import org.mingle.pear.persistence.query.SortOrder;
import org.mingle.pear.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/accounts")
@Controller
public class AccountController {
	@Inject
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Account account, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, account);
			return "/create";
		}
		uiModel.asMap().clear();
		accountService.persist(account);
		return "redirect:/accounts/" + encodeUrlPathSegment(account.getId().toString(), httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Account());
		return "/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("account", accountService.find(id));
		uiModel.addAttribute("itemId", id);
		return "/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) SortOrder sortOrder,
			Model uiModel) {
		QueryTemplate qt = QueryTemplate.create(QueryType.JQL, "SELECT t FROM Account t");
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			qt.setFirstResult(firstResult);
			qt.setMaxResults(sizeNo);
			qt.append(QueryTemplate.buildOrderBy("t", sortFieldName, sortOrder));
			uiModel.addAttribute("accounts", accountService.find(qt));
			qt = QueryTemplate.create(QueryType.JQL, "SELECT t FROM Account t");
			int numberOfPages = accountService.findCount(qt).intValue() / sizeNo;
			uiModel.addAttribute("maxPages", ((numberOfPages > numberOfPages || numberOfPages == 0.0) ? numberOfPages + 1
							: numberOfPages));
		} else {
			qt = QueryTemplate.create(QueryType.JQL, "SELECT t FROM Account t");
			qt.append(QueryTemplate.buildOrderBy("t", sortFieldName, sortOrder));
			uiModel.addAttribute("accounts", accountService.find(qt));
		}
		return "/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Account account, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, account);
			return "/update";
		}
		uiModel.asMap().clear();
		accountService.merge(account);
		return "redirect:/accounts/" + encodeUrlPathSegment(account.getId().toString(), httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, accountService.find(id));
		return "/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		accountService.remove(id);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/accounts";
	}

	void populateEditForm(Model uiModel, Account account) {
		uiModel.addAttribute("account", account);
	}

	String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {}
		return pathSegment;
	}
}
