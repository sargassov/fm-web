package ru.sargassov.fmweb.exceptions;

public class PlacementNotFoundException extends RuntimeException{
    public PlacementNotFoundException(String message) {
        super(message);
    }
}
