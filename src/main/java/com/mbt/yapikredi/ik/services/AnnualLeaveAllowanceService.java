package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AnnualLeaveAllowanceService {

    void addEmployeeAllowance(EmployeeEntity employee);

    void addEmployeeAllowancesForNewEmployee(EmployeeEntity employee);
}
