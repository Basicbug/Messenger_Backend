package com.basicbug.messenger.auth_server.exception;

/**
 * @author JaewonChoi
 */
public class UidNotFoundException extends RuntimeException{

    public UidNotFoundException() {
        super();
    }

    public UidNotFoundException(String message) {
        super(message);
    }

    public UidNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
