package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.EmployeeModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface EmployeeService {

    PageModel<EmployeeAllowanceModel> findEmployeeAllowanceList(String name, @Positive  @NotNull  Integer pageSize,@PositiveOrZero @NotNull Integer startPage);

    EmployeeEntity getEmployee(Long employeeId);

    EmployeeModel create(EmployeeModel model);
}
