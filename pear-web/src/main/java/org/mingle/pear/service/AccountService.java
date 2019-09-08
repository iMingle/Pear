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

package org.mingle.pear.service;

import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;

import java.util.List;

/**
 * @author mingle
 */
public interface AccountService {
    /**
     * 创建账户
     *
     * @param account
     * @return
     */
    int createAccount(Account account);

    /**
     * 更新账户
     *
     * @param account
     * @return
     */
    int updateAccount(Account account);

    /**
     * 删除账户
     *
     * @param id
     * @return
     */
    int deleteAccount(Long id);

    /**
     * 校验名称是否存在
     *
     * @param name
     * @return
     */
    boolean isNameExist(String name);

    /**
     * 查询账户
     *
     * @param queryParam
     * @return
     */
    List<Account> queryAccounts(AccountQueryParam queryParam);

    /**
     * 查询数量
     *
     * @param queryParam
     * @return
     */
    int countAccount(AccountQueryParam queryParam);
}
