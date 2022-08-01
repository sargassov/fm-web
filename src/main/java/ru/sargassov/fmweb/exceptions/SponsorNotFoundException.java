package ru.sargassov.fmweb.exceptions;

public class SponsorNotFoundException extends RuntimeException{
    public SponsorNotFoundException(String message) {
        super(message);
    }
}
