package com.unidates.Unidates.UniDates.Exception;

public class InvalidMessageFormatException extends RuntimeException {

    public InvalidMessageFormatException(){
        super("Il testo deve essere compreso tra 1 e 249 caratteri");
    }
}
