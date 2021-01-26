package com.unidates.Unidates.UniDates.Exception;

public class BannedUserException extends RuntimeException{

    public BannedUserException() {
        super("Il tuo account é stato attualmente sospeso.");
    }
}
