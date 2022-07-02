package com.mbt.yapikredi.ik.entity;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

/**
 * Hakedilen izinlerin tarihlerini tutuyor
 */
@Entity
public class AnnualLeaveAllowanceEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID", nullable=false, updatable = false)
    private EmployeeEntity employee;

    @NotNull
    @PastOrPresent
    @Column(name = "ALLOWANCE_DATE")
    private LocalDate allowanceDate;

    @NotNull
    @Positive
    @Column(name = "NUMBERS_OF_DAYS")
    private Integer numbersOfDays;

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public LocalDate getAllowanceDate() {
        return allowanceDate;
    }

    public void setAllowanceDate(LocalDate allowanceDate) {
        this.allowanceDate = allowanceDate;
    }

    public Integer getNumbersOfDays() {
        return numbersOfDays;
    }

    public void setNumbersOfDays(Integer numbersOfDays) {
        this.numbersOfDays = numbersOfDays;
    }
}
