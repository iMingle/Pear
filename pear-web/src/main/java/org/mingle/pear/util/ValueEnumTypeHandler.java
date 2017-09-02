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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mingle
 */
public class ValueEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> implements TypeHandler<E> {
    private final Class<?> type;
    private final Method getValueMethod;

    public ValueEnumTypeHandler(Class<?> typeArg) {
        if (typeArg == null) {
            throw new IllegalArgumentException("typeArg can not be null");
        }

        type = typeArg;
        getValueMethod = getValueMethod(type);
    }

    private Method getValueMethod(Class<?> clazz) {
        try {
            return clazz.getDeclaredMethod("getValue");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getValue(Object invoker) {
        try {
            return getValueMethod.invoke(invoker);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getEnum(Object value) {
        Object[] enums = type.getEnumConstants();
        for (Object enumItem : enums) {
            Object enumValue = getValue(enumItem);
            if (enumValue.equals(value)) {
                return enumItem;
            }
        }

        throw new RuntimeException("Enum Value " + String.valueOf(value) + " not defined in enum " + type.getClass().getName());
    }

    private void throwNotSupportValueTypeException() {
        throw new RuntimeException("value type not support");
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        Object value = getValue(e);

        if (value instanceof Integer) {
            preparedStatement.setInt(i, (Integer) value);
        } else if (value instanceof Byte) {
            preparedStatement.setByte(i, (Byte) value);
        } else if (value instanceof Short) {
            preparedStatement.setShort(i, (Short) value);
        } else if (value instanceof Long) {
            preparedStatement.setLong(i, (Long) value);
        } else if (value instanceof Boolean) {
            preparedStatement.setBoolean(i, (Boolean) value);
        } else {
            throwNotSupportValueTypeException();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Class<?> returnType = getValueMethod.getReturnType();
        Object val = null;
        if (Integer.class.equals(returnType) || int.class.equals(returnType)) {
            val = resultSet.getInt(s);
        } else if (Short.class.equals(returnType) || short.class.equals(returnType)) {
            val = resultSet.getShort(s);
        } else if (Long.class.equals(returnType) || long.class.equals(returnType)) {
            val = resultSet.getLong(s);
        } else if (Byte.class.equals(returnType) || byte.class.equals(returnType)) {
            val = resultSet.getByte(s);
        } else if (Boolean.class.equals(returnType) || boolean.class.equals(returnType)) {
            val = resultSet.getBoolean(s);
        } else {
            throwNotSupportValueTypeException();
        }
        return (E) getEnum(val);
    }


    @SuppressWarnings("unchecked")
    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Class<?> returnType = getValueMethod.getReturnType();
        Object val = null;
        if (Integer.class.equals(returnType)) {
            val = resultSet.getInt(i);
        } else if (Short.class.equals(returnType)) {
            val = resultSet.getShort(i);
        } else if (Long.class.equals(returnType)) {
            val = resultSet.getLong(i);
        } else if (Byte.class.equals(returnType)) {
            val = resultSet.getByte(i);
        } else if (Boolean.class.equals(returnType)) {
            val = resultSet.getBoolean(i);
        } else {
            throwNotSupportValueTypeException();
        }
        return (E) getEnum(val);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Class<?> returnType = getValueMethod.getReturnType();
        Object val = null;
        if (Integer.class.equals(returnType)) {
            val = callableStatement.getInt(i);
        } else if (Short.class.equals(returnType)) {
            val = callableStatement.getShort(i);
        } else if (Long.class.equals(returnType)) {
            val = callableStatement.getLong(i);
        } else if (Byte.class.equals(returnType)) {
            val = callableStatement.getByte(i);
        } else if (Boolean.class.equals(returnType)) {
            val = callableStatement.getBoolean(i);
        } else {
            throwNotSupportValueTypeException();
        }
        return (E) getEnum(val);
    }
}
