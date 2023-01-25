package com.unidates.Unidates.UniDates.Exception;

public class PasswordMissmatchException extends RuntimeException {

    public PasswordMissmatchException(){
        super("La password inserita non corrisponde a quella utente");
    }
}
