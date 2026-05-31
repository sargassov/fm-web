package ru.sargassov.fmweb.exceptions;

public class CupNotFoundException extends RuntimeException {
    public CupNotFoundException(String message) {
        super(message);
    }
}
