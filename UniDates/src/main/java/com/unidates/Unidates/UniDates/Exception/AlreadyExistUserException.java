package com.unidates.Unidates.UniDates.Exception;

public class AlreadyExistUserException extends RuntimeException {

    public AlreadyExistUserException(){
        super("L'email inserita è già in uso");
    }
}
