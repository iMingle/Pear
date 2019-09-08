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

package org.mingle.pear.dao;

import org.apache.ibatis.jdbc.SQL;
import org.mingle.pear.domain.Account;
import org.mingle.pear.dto.AccountQueryParam;

import java.util.Objects;

/**
 * @author mingle
 */
public class AccountSqlProvider {
    private static final String TABLE_NAME = "t_account";

    public String query(final AccountQueryParam queryParam) {
        return new SQL() {
            {
                SELECT("id, name, age, email, sex");
                FROM(TABLE_NAME);
                WHERE("1 = 1");
                if (Objects.nonNull(queryParam.getId())) {
                    AND();
                    WHERE("id = #{id}");
                }
                if (Objects.nonNull(queryParam.getAge())) {
                    AND();
                    WHERE("age = #{age}");
                }
                if (Objects.nonNull(queryParam.getPageNumber()) && Objects.nonNull(queryParam.getPageSize())) {
                    LIMIT(queryParam.getPageSize());
                    OFFSET((queryParam.getPageNumber() - 1) * queryParam.getPageSize());
                }
            }
        }.toString();
    }

    public String count(final AccountQueryParam queryParam) {
        return new SQL() {
            {
                SELECT("COUNT(*)");
                FROM(TABLE_NAME);
                WHERE("1 = 1");
                if (Objects.nonNull(queryParam.getId())) {
                    AND();
                    WHERE("id = #{id}");
                }
                if (Objects.nonNull(queryParam.getAge())) {
                    AND();
                    WHERE("age = #{age}");
                }
            }
        }.toString();
    }

    public String insert(final Account account) {
        return new SQL() {
            {
                INSERT_INTO(TABLE_NAME);
                VALUES("age", "#{age}");
                VALUES("email", "#{email}");
                VALUES("name", "#{name}");
                VALUES("sex", "#{sex}");
                VALUES("version", "#{version}");
            }
        }.toString();
    }

    public String update(final Account account) {
        return new SQL() {
            {
                UPDATE(TABLE_NAME);
                if (Objects.nonNull(account.getName()))
                    SET("name = #{name}");
                if (Objects.nonNull(account.getAge()))
                    SET("age = #{age}");
                if (Objects.nonNull(account.getEmail()))
                    SET("email = #{email}");
                if (Objects.nonNull(account.getSex()))
                    SET("sex = #{sex}");
                if (Objects.nonNull(account.getVersion()))
                    SET("version = #{version}");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
