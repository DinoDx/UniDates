package com.unidates.Unidates.UniDates.Exception;

public class InvalidModifyFormatException extends RuntimeException {

    public InvalidModifyFormatException(){
        super("Uno o più campi inseriti non hanno un formato valido");
    }
}
