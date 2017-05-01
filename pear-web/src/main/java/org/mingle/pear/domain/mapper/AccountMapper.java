/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.domain.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.mingle.pear.domain.Account;

/**
 * 账户Mapper
 *
 * @author Mingle
 * @since 1.8
 */
public interface AccountMapper {
    @Select("SELECT * FROM t_account WHERE id = #{accountId}")
    Account getAccount(Long accountId);

    @InsertProvider(type = AccountSqlProvider.class, method = "insert")
    int insertAccount(Account account);

    @Delete("DELETE FROM t_account WHERE id = #{accountId}")
    int deleteAccount(Long accountId);
}
