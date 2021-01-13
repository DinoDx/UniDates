package com.unidates.Unidates.UniDates.Exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(){
        super("Non esiste un match tra i due studenti");
    }
}
