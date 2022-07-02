package com.mbt.yapikredi.ik.entity;


import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class RequestEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)
    private EmployeeEntity employee;

    @NotNull
    @Column(name = "REQUEST_STATUS")
    private EnumRequestStatus requestStatus = EnumRequestStatus.ONAY_BEKLIYOR;

    @NotNull
    @Column(name = "ANNUAL_LEAVE_START")
    private LocalDate annualLeaveStart;

    @NotNull
    @Column(name = "ANNUAL_LEAVE_END")
    private LocalDate annualLeaveEnd;

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public EnumRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(EnumRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDate getAnnualLeaveStart() {
        return annualLeaveStart;
    }

    public void setAnnualLeaveStart(LocalDate annualLeaveStart) {
        this.annualLeaveStart = annualLeaveStart;
    }

    public LocalDate getAnnualLeaveEnd() {
        return annualLeaveEnd;
    }

    public void setAnnualLeaveEnd(LocalDate annualLeaveEnd) {
        this.annualLeaveEnd = annualLeaveEnd;
    }
}
