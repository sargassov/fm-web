package ru.sargassov.fmweb.exceptions;

public class LeagueNotFoundException extends RuntimeException{
    public LeagueNotFoundException(String message) {
        super(message);
    }
}
