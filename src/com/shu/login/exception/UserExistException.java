package com.shu.login.exception;

/**
 * Created by Qjkobe on 2016/2/19.
 */
public class UserExistException extends Exception {
    public UserExistException() {
        super();
    }
    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserExistException(String message) {
        super(message);
    }
    public UserExistException(Throwable cause) {
        super(cause);
    }
}
