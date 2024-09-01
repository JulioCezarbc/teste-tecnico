package com.julio.tgid.exception;

public class AlreadyInUse extends RuntimeException{
    public AlreadyInUse(String msg){
        super(msg + "In use");
    }
}
