package com.unidates.Unidates.UniDates.Exception;

public class InvalidBanFormatException extends RuntimeException {

    public InvalidBanFormatException(){
        super("Dettagli e/o durata non validi");
    }
}
