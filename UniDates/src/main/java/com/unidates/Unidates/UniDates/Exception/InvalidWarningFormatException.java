package com.unidates.Unidates.UniDates.Exception;

public class InvalidWarningFormatException extends RuntimeException {

    public InvalidWarningFormatException(){
        super("Motivazione e/o dettagli non validi");
    }
}
