package com.unidates.Unidates.UniDates.Exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("Non è stato trovato nessuno studente con l'email inserita");
    }
}
