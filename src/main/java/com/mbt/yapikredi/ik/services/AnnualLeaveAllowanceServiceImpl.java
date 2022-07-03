package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.entity.AnnualLeaveAllowanceEntity;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.repository.AnnualLeaveAllowanceRepository;
import com.mbt.yapikredi.ik.util.AllowanceCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnnualLeaveAllowanceServiceImpl implements AnnualLeaveAllowanceService {

    private final AnnualLeaveAllowanceRepository repository;

    private final EmployeeAllowanceDayCountService employeeAllowanceDayCountService;

    public AnnualLeaveAllowanceServiceImpl(AnnualLeaveAllowanceRepository repository, EmployeeAllowanceDayCountService employeeAllowanceDayCountService) {
        this.repository = repository;
        this.employeeAllowanceDayCountService = employeeAllowanceDayCountService;
    }

    @Override
    public void addEmployeeAllowance(EmployeeEntity employee) {
        AnnualLeaveAllowanceEntity entity = new AnnualLeaveAllowanceEntity();
        entity.setEmployee(employee);
        entity.setAllowanceDate(LocalDate.now());
        entity.setNumbersOfDays(AllowanceCalculator.getNumberAccordingToToday(employee.getStartDate()));
        repository.save(entity);
        employeeAllowanceDayCountService.addDaysToEmployee(employee, entity.getNumbersOfDays());
    }

    @Override
    public void addEmployeeAllowancesForNewEmployee(EmployeeEntity employee) {
        LocalDate date = employee.getStartDate().plusYears(1);
        List<AnnualLeaveAllowanceEntity> listToAdd = new ArrayList<>();

        int totaldaysToAdd = 0;
        while (date.isBefore(LocalDate.now())) {
            int daysToAdd = AllowanceCalculator.getNumber(employee.getStartDate(), date);
            AnnualLeaveAllowanceEntity entity = new AnnualLeaveAllowanceEntity();
            entity.setEmployee(employee);
            entity.setAllowanceDate(date);
            entity.setNumbersOfDays(daysToAdd);
            totaldaysToAdd += daysToAdd;
            date=date.plusYears(1);
        }
        employeeAllowanceDayCountService.addDaysToEmployee(employee, totaldaysToAdd);
        repository.saveAll(listToAdd);
    }
}
