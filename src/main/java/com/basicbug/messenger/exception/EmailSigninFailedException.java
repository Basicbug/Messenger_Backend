package com.basicbug.messenger.exception;

/**
 * @author JaewonChoi
 */
public class EmailSigninFailedException extends RuntimeException {

    public EmailSigninFailedException() {
        super();
    }

    public EmailSigninFailedException(String message) {
        super(message);
    }

    public EmailSigninFailedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
