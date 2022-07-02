package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.entity.EmployeeAllowanceDayCountEntity;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EmployeeAllowanceDayCountService {

    EmployeeAllowanceDayCountEntity getByEmployeeId(Long employeeId);

    void addDaysToEmployee(EmployeeEntity employee, Integer dayCount);

    void removeDaysFromEmployee(EmployeeEntity employee, Integer dayCount);
}
