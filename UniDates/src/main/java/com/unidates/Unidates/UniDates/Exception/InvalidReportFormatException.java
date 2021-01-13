package com.unidates.Unidates.UniDates.Exception;

public class InvalidReportFormatException extends RuntimeException {

    public InvalidReportFormatException(){
        super("Motivazione e/o dettagli non validi");
    }
}
