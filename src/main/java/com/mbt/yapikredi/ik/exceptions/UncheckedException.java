package com.mbt.yapikredi.ik.exceptions;

public class UncheckedException extends RuntimeException {

    private ExceptionData exceptionData;

    public UncheckedException(ExceptionData data) {
        this.exceptionData = data;
    }

    public ExceptionData getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(ExceptionData exceptionData) {
        this.exceptionData = exceptionData;
    }
}
