/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * 数据库工具类
 * 
 * @since 1.8
 * @author Mingle
 */
public abstract class DatabaseUtils {
	public static String decryptPassword(String password) {
		BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
		basicTextEncryptor.setPassword("password");
		
		return basicTextEncryptor.decrypt(password);
	}
}
