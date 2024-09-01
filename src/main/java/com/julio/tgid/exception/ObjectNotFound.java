package com.julio.tgid.exception;

public class ObjectNotFound extends RuntimeException{
    public ObjectNotFound(String msg){
        super(msg + "not found");
    }
}
