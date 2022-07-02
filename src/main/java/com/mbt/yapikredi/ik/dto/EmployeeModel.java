package com.mbt.yapikredi.ik.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class EmployeeModel extends BaseEmployeeModel {

    @NotNull
    @PastOrPresent
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
