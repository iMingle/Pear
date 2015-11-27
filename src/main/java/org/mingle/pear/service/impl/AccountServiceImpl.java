/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.service.impl;

import javax.inject.Inject;

import org.mingle.pear.dao.AccountDao;
import org.mingle.pear.domain.Account;
import org.mingle.pear.persistence.dao.GenericDao;
import org.mingle.pear.persistence.service.impl.GenericServiceImpl;
import org.mingle.pear.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @since 1.8
 * @author Mingle
 */
@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Long>
		implements AccountService {
	@Inject
	private AccountDao accountDao;

	@Override
	protected GenericDao<Account, Long> getDao() {
		return accountDao;
	}

}
