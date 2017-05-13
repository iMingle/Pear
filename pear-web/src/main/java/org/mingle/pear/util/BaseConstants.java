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

package org.mingle.pear.util;

/**
 * 基本常量
 *
 * @author mingle
 * @since 1.8
 */
public final class BaseConstants {
    /**
     * 长日期时间格式，精确到秒
     */
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 短日期时间格式，只精确到天
     */
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 时间格式，精确到分钟
     */
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * 默认的字符分割符,逗号
     */
    public static final String SPLITER_FLAG = ",";

    /**
     * 空白字符串
     */
    public static final String BLANK_STRING = "";

    /**
     * 键值分割符,冒号
     */
    public static final String VALUE_LABEL_SPLITER = ":";

    /**
     * 键值分割符,分号
     */
    public static final String SEMI_SPLITER = ";";

    /**
     *
     */
    final static String ENUM_PREFIX = "enums.";

    /**
     * 默认的根节点ID
     */
    final static Long ROOT_ID = 1L;

    /**
     * 加密key值
     */
    public static final String ENCRYPT = "password";

    /**
     * 初始口令表验证密码
     */
    public static final String DEFAULTSECPASS = "123456";

    public static final String DEFAULTORDER = "0000";

    /**
     * 持久化单元名称
     */
    public static final String PERSIST_UNIT_NAME = "persistenceUnit";
}
