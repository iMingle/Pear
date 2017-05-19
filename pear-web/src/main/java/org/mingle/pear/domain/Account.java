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

package org.mingle.pear.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mingle.pear.persistence.domain.BaseDomain;
import org.mingle.pear.util.Sex;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 账户
 *
 * @author mingle
 */
@Entity
@Table(name = "t_account")
@EqualsAndHashCode(callSuper = false)
public @Data class Account extends BaseDomain<Long> {
    private static final long serialVersionUID = -5113175753421859746L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @NotNull
    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "your age must greater than 0")
    @Max(value = 150, message = "your age must under 150")
    private int age;

    @Column(name = "email", length = 50)
    @Pattern(regexp = "[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Invalid email address.")
    private String email;

    @Column(name = "sex", length = 10)
    @Convert(converter = SexConverter.class)
    private Sex sex = Sex.MAN;
}
