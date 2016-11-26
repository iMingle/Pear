/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 读取邮件的配置文件信息
 * 
 * @since 1.8
 * @author Mingle
 */
@Repository
public class PropertiesMail {
	@Value("${mail.protocol}")
	@Getter private String protocol;
	@Value("${mail.host}")
	@Getter private String host;
	@Value("${mail.port}")
	@Getter private int port;
	@Value("${mail.username}")
	@Getter private String username;
	@Value("${mail.password}")
	@Getter private String password;
}
