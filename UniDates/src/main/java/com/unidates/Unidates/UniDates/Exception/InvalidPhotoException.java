package com.unidates.Unidates.UniDates.Exception;

public class InvalidPhotoException extends RuntimeException {

    public InvalidPhotoException(){
        super("La foto non rispetta le dimensioni consentite");
    }
}
