package com.mbt.yapikredi.ik.util;

import com.mbt.yapikredi.ik.exceptions.ExceptionData;
import com.mbt.yapikredi.ik.exceptions.UncheckedException;

import java.time.LocalDate;

public class AllowanceCalculator {

    public static int getNumberAccordingToToday(LocalDate startDate) {
        LocalDate today = LocalDate.now();

        return getNumber(startDate, today);
    }

    public static int getNumber(LocalDate employeeStartDate, LocalDate dateToCheck) {

        if (employeeStartDate.isAfter(dateToCheck)) {
            throw new UncheckedException(ExceptionData.fromMessage("startdate can not be after dateToCheck"));
        }
        int yearDiff = dateToCheck.getYear() - employeeStartDate.getYear();
        if (yearDiff == 0) {
            return 0;
        } else if (yearDiff >= 1 && yearDiff < 5) {
            return 15;
        } else if (yearDiff >= 5 && yearDiff < 10) {
            return 18;
        } else {
            return 24;
        }
    }
}
