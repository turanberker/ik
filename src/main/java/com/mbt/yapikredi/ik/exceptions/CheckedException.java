package com.mbt.yapikredi.ik.exceptions;

public class CheckedException extends Exception {

    private ExceptionData exceptionData;

    public CheckedException(ExceptionData exception) {

        this.exceptionData = exception;
    }

    public ExceptionData getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
