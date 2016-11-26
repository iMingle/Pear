/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence;

import java.io.Serializable;

/**
 * 可持久化类主键接口
 *
 * @author Mingle
 * @since 1.8
 */
public interface Identifiable<ID extends Serializable> extends Serializable {
    /**
     * 获取实体类的主键
     *
     * @return 主键
     */
    ID getId();

    /**
     * 判断主键是否已设置
     *
     * @return 主键是否已设置
     */
    boolean hasId();

    /**
     * 设置主键
     *
     * @param id 主键
     */
    void setId(ID id);
}
