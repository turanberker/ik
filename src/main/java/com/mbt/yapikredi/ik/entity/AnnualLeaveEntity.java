package com.mbt.yapikredi.ik.entity;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Kullanılan izinleri tutuyor
 */
@Entity
public class AnnualLeaveEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="REQUEST_ID", nullable=false, updatable = false)
    private RequestEntity request;

    @NotNull
    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID", nullable=false, updatable = false)
    private EmployeeEntity employee;

    @NotNull
    @Column(name = "DAYS")
    private int days;

    public RequestEntity getRequest() {
        return request;
    }

    public void setRequest(RequestEntity request) {
        this.request = request;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
