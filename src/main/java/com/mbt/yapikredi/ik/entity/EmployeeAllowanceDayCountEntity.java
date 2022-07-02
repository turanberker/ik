package com.mbt.yapikredi.ik.entity;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class EmployeeAllowanceDayCountEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)
    private EmployeeEntity employee;

    @NotNull
    @Column(name = "NUMBERS_OF_DAYS")
    private Integer numbersOfDays=0;

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public Integer getNumbersOfDays() {
        return numbersOfDays;
    }

    public void setNumbersOfDays(Integer numbersOfDays) {
        this.numbersOfDays = numbersOfDays;
    }
}
