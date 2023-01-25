package me.tatiana.springweb.exception;

public class Response500Exception extends RuntimeException {
    private String message;

    public Response500Exception() {
    }

    public Response500Exception(String message) {
        super(message);
    }

    public Response500Exception(String message, Throwable cause) {
        super(message, cause);
    }
}