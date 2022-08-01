package ru.sargassov.fmweb.exceptions;

public class Notice {
    private int statusCode;
    private String message;

    public Notice() {
    }

    public Notice(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
