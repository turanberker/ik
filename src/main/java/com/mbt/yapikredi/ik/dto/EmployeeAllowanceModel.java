package com.mbt.yapikredi.ik.dto;

import com.querydsl.core.annotations.QueryProjection;

public class EmployeeAllowanceModel
{

    private Long id;

    private String firstName;

    private String lastName;

    private Integer numbersOfAllowanceDay;

    @QueryProjection
    public EmployeeAllowanceModel(Long id, String firstName, String lastName, Integer numbersOfAllowanceDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numbersOfAllowanceDay = numbersOfAllowanceDay==null?0:numbersOfAllowanceDay;
    }

    public EmployeeAllowanceModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNumbersOfAllowanceDay() {
        return numbersOfAllowanceDay;
    }

    public void setNumbersOfAllowanceDay(Integer numbersOfAllowanceDay) {
        this.numbersOfAllowanceDay = numbersOfAllowanceDay;
    }
}
