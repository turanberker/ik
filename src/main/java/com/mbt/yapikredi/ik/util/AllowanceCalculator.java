package com.mbt.yapikredi.ik.util;

import com.mbt.yapikredi.ik.exceptions.ExceptionData;
import com.mbt.yapikredi.ik.exceptions.UncheckedException;

import java.time.LocalDate;

public class AllowanceCalculator {

    public static int getNumber(LocalDate startDate){
        LocalDate today = LocalDate.now();
        if(startDate.isAfter(today)){
            throw new UncheckedException(ExceptionData.fromMessage("startdate can not be after today"));
        }
        int yearDiff = today.getYear() - startDate.getYear();
        if(yearDiff==0){
            return 0;
        }        else if(yearDiff>=1  &&yearDiff<5){
            return 15;
        }else if(yearDiff>=5  &&yearDiff<10){
            return 18;
        }
        else {
            return 24;
        }
    }
}
