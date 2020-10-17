package com.github.marcoral.simplenettyserver.api.exception;

public class InvalidPacketException extends Exception {
    public InvalidPacketException() {}

    public InvalidPacketException(String cause) {
        super(cause);
    }
}
