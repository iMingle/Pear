/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.dao.impl;

import org.mingle.pear.dao.AccountDao;
import org.mingle.pear.domain.Account;
import org.mingle.pear.persistence.dao.impl.GenericDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @since 1.8
 * @author Mingle
 */
@Repository
public class AccountDaoImpl extends GenericDaoImpl<Account, Long> implements
		AccountDao {

	public AccountDaoImpl() {
		super(Account.class);
	}

}
