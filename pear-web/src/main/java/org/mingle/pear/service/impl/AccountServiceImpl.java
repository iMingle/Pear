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

package org.mingle.pear.service.impl;

import org.mingle.pear.dao.AccountDao;
import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;
import org.mingle.pear.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author mingle
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject private AccountDao accountDao;

    @Override public int createAccount(Account account) {
        return accountDao.insert(account);
    }

    @Override public int updateAccount(Account account) {
        return accountDao.update(account);
    }

    @Override public int deleteAccount(Long id) {
        return accountDao.delete(id);
    }

    @Override public boolean isNameExist(String name) {
        return false;
    }

    @Override public List<Account> queryAccounts(AccountQueryParam queryParam) {
        return accountDao.query(queryParam);
    }
}
