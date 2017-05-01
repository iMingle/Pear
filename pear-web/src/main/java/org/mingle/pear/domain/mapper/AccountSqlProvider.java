package org.mingle.pear.domain.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.mingle.pear.domain.Account;

import static org.apache.ibatis.jdbc.SelectBuilder.BEGIN;

/**
 * @author mingle
 */
public class AccountSqlProvider {
    private static final String TABLE_NAME = "t_account";

    public String insert(final Account account) {
        return new SQL() {
            {
                BEGIN();
                INSERT_INTO(TABLE_NAME);
                VALUES("age", "#{age}");
                VALUES("email", "#{email}");
                VALUES("name", "#{name}");
                VALUES("sex", "" + account.getSex().getValue());
                VALUES("version", "#{version}");
            }
        }.toString();
    }
}
