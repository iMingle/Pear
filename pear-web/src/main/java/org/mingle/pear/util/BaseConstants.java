/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.util;

/**
 * 基本常量
 *
 * @author Mingle
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
