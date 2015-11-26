package org.mingle.pear.web;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mingle.pear.domain.Account;
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
			return "accounts/create";
		}
		uiModel.asMap().clear();
		accountService.persist(account);
		return "redirect:/accounts/"
				+ encodeUrlPathSegment(account.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Account());
		return "accounts/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("account", accountService.find(id));
		uiModel.addAttribute("itemId", id);
		return "accounts/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder,
			Model uiModel) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
//			uiModel.addAttribute("accounts", Account.findAccountEntries(
//					firstResult, sizeNo, sortFieldName, sortOrder));
//			float numberOfPages = (float) Account.countAccounts() / sizeNo;
//			uiModel.addAttribute(
//					"maxPages",
//					(int) ((numberOfPages > (int) numberOfPages || numberOfPages == 0.0) ? numberOfPages + 1
//							: numberOfPages));
		} else {
//			uiModel.addAttribute("accounts",
//					Account.findAllAccounts(sortFieldName, sortOrder));
		}
		return "accounts/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Account account, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, account);
			return "accounts/update";
		}
		uiModel.asMap().clear();
		accountService.merge(account);
		return "redirect:/accounts/"
				+ encodeUrlPathSegment(account.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, accountService.find(id));
		return "accounts/update";
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
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
