package com.unidates.Unidates.UniDates.Exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String location){
        super("Trying to navigate " + location + " without Auth");
    }

    public NotAuthorizedException() {
    }
}
