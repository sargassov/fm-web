package ru.sargassov.fmweb.exceptions;

public class TooExpensiveException extends RuntimeException{
    public TooExpensiveException(String message) {
        super(message);
    }
}
