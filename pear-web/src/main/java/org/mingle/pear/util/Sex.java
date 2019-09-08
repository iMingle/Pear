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

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 *
 * @author mingle
 */
public enum Sex {
    WOMAN(0), MAN(1), INTERSEX(2);

    @Getter private int value;

    @Getter private static final Map<Integer, Sex> MAP = Collections.unmodifiableMap(new HashMap<Integer, Sex>() {{
        put(WOMAN.getValue(), WOMAN);
        put(MAN.getValue(), MAN);
        put(INTERSEX.getValue(), INTERSEX);
    }});

    @Getter private static final Map<Sex, Integer> SEX_VALUE = Collections.unmodifiableMap(
            new HashMap<Sex, Integer>() {{
                put(WOMAN, WOMAN.getValue());
                put(MAN, MAN.getValue());
                put(INTERSEX, INTERSEX.getValue());
            }});

    Sex(int value) {
        this.value = value;
    }

    public static Map<Integer, Sex> mapSex() {
        return MAP;
    }

    public static Map<Sex, Integer> mapValue() {
        return SEX_VALUE;
    }
}
