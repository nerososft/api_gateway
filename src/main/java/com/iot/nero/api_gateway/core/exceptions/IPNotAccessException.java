package com.iot.nero.api_gateway.core.exceptions;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/31
 * Time   上午11:37
 */
public class IPNotAccessException extends Exception {
    public IPNotAccessException() {
    }

    public IPNotAccessException(String message) {
        super(message);
    }

    public IPNotAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public IPNotAccessException(Throwable cause) {
        super(cause);
    }

}
