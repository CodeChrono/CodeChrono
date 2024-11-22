package com.codechrono.idea.plugin.utils;

/**
 * @author CodeChrono
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    private String messageName;
    public CustomException(Throwable e) {
        super(e);
    }
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, String messageName) {
        super(message);
        this.messageName = messageName;
    }

    public String getMessagename() {
        return this.messageName;
    }

}
