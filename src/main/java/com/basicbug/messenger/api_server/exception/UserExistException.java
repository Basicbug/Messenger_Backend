package com.basicbug.messenger.api_server.exception;

/**
 * @author JaewonChoi
 */
public class UserExistException extends RuntimeException {

    public UserExistException() {
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
