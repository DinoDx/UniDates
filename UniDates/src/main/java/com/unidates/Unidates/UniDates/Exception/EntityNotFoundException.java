package com.unidates.Unidates.UniDates.Exception;

import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message){
        super(message);
    }
}
