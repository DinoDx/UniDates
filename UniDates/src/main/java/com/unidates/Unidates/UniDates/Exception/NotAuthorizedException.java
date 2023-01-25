package com.unidates.Unidates.UniDates.Exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message){
        super(message);
    }

    public NotAuthorizedException() {
    }
}
