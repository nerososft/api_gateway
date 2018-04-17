package com.iot.nero.api_gateway.core.exceptions;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/1
 * Time   下午1:04
 */
public class MockApiNotFoundException extends Exception {

    public MockApiNotFoundException() {
    }

    public MockApiNotFoundException(String message) {
        super(message);
    }

    public MockApiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MockApiNotFoundException(Throwable cause) {
        super(cause);
    }

}
