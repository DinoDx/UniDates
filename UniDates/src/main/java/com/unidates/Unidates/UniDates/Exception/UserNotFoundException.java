package com.unidates.Unidates.UniDates.Exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Non è stato trovato nessuno studente con l'email inserita");
    }
}
