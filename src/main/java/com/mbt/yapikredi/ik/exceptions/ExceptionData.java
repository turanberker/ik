package com.mbt.yapikredi.ik.exceptions;

import java.util.ResourceBundle;

public class ExceptionData {

    private String message;

    private String bundle;

    public static ExceptionData fromMessage(String message){
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setMessage(message);
        return exceptionData;
    }

    public static ExceptionData fromBundle(String bundle){
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setBundle(bundle);
        return exceptionData;
    }

    protected ExceptionData() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }
}
