/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.util;

import lombok.Getter;

/**
 * 性别
 *
 * @author Mingle
 * @since 1.8
 */
public enum Sex {
    WOMAN(0), MAN(1), INTERSEX(2);

    @Getter private int value;

    Sex(int value) {
        this.value = value;
    }
}
