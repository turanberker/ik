package com.mbt.yapikredi.ik.configuration;

import com.mbt.yapikredi.ik.exceptions.CheckedException;
import com.mbt.yapikredi.ik.exceptions.ExceptionData;
import com.mbt.yapikredi.ik.exceptions.UncheckedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private Translator translator;

    @ExceptionHandler(value
            = {CheckedException.class})
    protected ResponseEntity<ExceptionData> handleCheckedExceptions(
            CheckedException ex, WebRequest request) {

        ex.getExceptionData().setMessage(translator.toLocale(ex.getExceptionData().getBundle()));
        return new ResponseEntity<>(ex.getExceptionData(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value
            = {UncheckedException.class})
    protected ResponseEntity<ExceptionData> handleUnCheckedExceptions(
            UncheckedException ex, WebRequest request) {

        if (StringUtils.isNotEmpty(ex.getExceptionData().getBundle())) {
            ex.getExceptionData().setMessage(translator.toLocale(ex.getExceptionData().getBundle()));
        }
        return new ResponseEntity<>(ex.getExceptionData(), HttpStatus.BAD_REQUEST);
    }
}
