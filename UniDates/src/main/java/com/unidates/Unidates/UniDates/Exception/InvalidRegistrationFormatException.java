package com.unidates.Unidates.UniDates.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidRegistrationFormatException extends RuntimeException {
    public InvalidRegistrationFormatException(){
        super("Uno o più dati inseriti non sono validi");
    }
}
