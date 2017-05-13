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

package org.mingle.pear.exception;

/**
 * HTTP请求超时异常
 *
 * @author mingle
 * @since 1.8
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
