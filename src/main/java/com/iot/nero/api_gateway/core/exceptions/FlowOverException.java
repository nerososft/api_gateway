package com.iot.nero.api_gateway.core.exceptions;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/4
 * Time   下午1:40
 */
public class FlowOverException extends Exception {

    public FlowOverException() {
    }

    public FlowOverException(String message) {
        super(message);
    }

    public FlowOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowOverException(Throwable cause) {
        super(cause);
    }

}
