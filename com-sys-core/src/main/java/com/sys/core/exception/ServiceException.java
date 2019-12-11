package com.sys.core.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(Exception ex){
        super(ex);
    }

    public ServiceException(String msg){
        super(msg);
    }

    public ServiceException(String msg,Exception ex){
        super(msg,ex);
    }

    public ServiceException(){
        super();
    }
}
