/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.exception;

/**
 * HTTP请求超时异常
 * 
 * @since 1.8
 * @author Mingle
 */
public class HttpRequestTimeoutException extends RuntimeException {
	private static final long serialVersionUID = -1626960970904228309L;
	
	final long timeout;

	public HttpRequestTimeoutException(long timeout) {
		super("this http request took " + timeout + " milliseconds");
		this.timeout = timeout;
	}
	
	public HttpRequestTimeoutException(long timeout, String message) {
		super(message + ", this http request took " + timeout + " milliseconds");
		this.timeout = timeout;
	}
	
	public HttpRequestTimeoutException(long timeout, String message, Throwable cause) {
		super(message + ", this http request took " + timeout + " milliseconds", cause);
		this.timeout = timeout;
	}
	
	public HttpRequestTimeoutException(long timeout, Throwable cause) {
		super(cause);
		this.timeout = timeout;
	}
	
	public HttpRequestTimeoutException(long timeout, String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
		super(message + ", this http request took " + timeout + " milliseconds", cause, enableSuppression, writableStackTrace);
		this.timeout = timeout;
	}
	
}
