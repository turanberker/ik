package com.mbt.yapikredi.ik.dto;

import com.querydsl.core.annotations.QueryProjection;

public class EmployeeAllowanceModel extends BaseEmployeeModel {

    private Integer numbersOfAllowanceDay;

    @QueryProjection
    public EmployeeAllowanceModel(Long id, String firstName, String lastName, Integer numbersOfAllowanceDay) {
        super(id, firstName, lastName);
        this.numbersOfAllowanceDay = numbersOfAllowanceDay == null ? 0 : numbersOfAllowanceDay;
    }

    public EmployeeAllowanceModel() {
    }

    public Integer getNumbersOfAllowanceDay() {
        return numbersOfAllowanceDay;
    }

    public void setNumbersOfAllowanceDay(Integer numbersOfAllowanceDay) {
        this.numbersOfAllowanceDay = numbersOfAllowanceDay;
    }
}
