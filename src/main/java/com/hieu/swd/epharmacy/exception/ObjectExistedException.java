package com.hieu.swd.epharmacy.exception;

public class ObjectExistedException extends RuntimeException {
    public ObjectExistedException() {
        super();
    }

    public ObjectExistedException(String message) {
        super(message);
    }

    public ObjectExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
