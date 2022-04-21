package ru.sargassov.fmweb.exceptions;

public class PresentDayNotFoundException extends RuntimeException{
    public PresentDayNotFoundException(String message) {
        super(message);
    }
}
