package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.entity.AnnualLeaveAllowanceEntity;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.repository.AnnualLeaveAllowanceRepository;
import com.mbt.yapikredi.ik.util.AllowanceCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class AnnualLeaveAllowanceServiceImpl implements AnnualLeaveAllowanceService{

    private final AnnualLeaveAllowanceRepository repository;

    public AnnualLeaveAllowanceServiceImpl(AnnualLeaveAllowanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addEmployeeAllowance(EmployeeEntity employee) {
        AnnualLeaveAllowanceEntity entity=new AnnualLeaveAllowanceEntity();
        entity.setEmployee(employee);
        entity.setAllowanceDate(LocalDate.now());
        entity.setNumbersOfDays(AllowanceCalculator.getNumber(employee.getStartDate()));
        repository.save(entity);
    }
}
