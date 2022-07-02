package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.entity.EmployeeAllowanceDayCountEntity;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.repository.EmployeeAllowanceDayCountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeAllowanceDayCountServiceImpl implements EmployeeAllowanceDayCountService{

    private final EmployeeAllowanceDayCountRepository repository;

    public EmployeeAllowanceDayCountServiceImpl(EmployeeAllowanceDayCountRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeAllowanceDayCountEntity getByEmployeeId(Long employeeId){
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public void addDaysToEmployee(EmployeeEntity employee, Integer dayCount) {
        EmployeeAllowanceDayCountEntity entity = getByEmployeeId(employee.getId());
        if(entity==null){
            entity=new EmployeeAllowanceDayCountEntity();
            entity.setEmployee(employee);
            entity.setNumbersOfDays(dayCount);
        }else{
            entity.setNumbersOfDays(entity.getNumbersOfDays()+dayCount);
        }
        repository.saveAndFlush(entity);
    }

    @Override
    public void removeDaysFromEmployee(EmployeeEntity employee, Integer dayCount) {
        EmployeeAllowanceDayCountEntity entity = getByEmployeeId(employee.getId());

        if(entity==null){
            entity=new EmployeeAllowanceDayCountEntity();
            entity.setEmployee(employee);
            entity.setNumbersOfDays(-1*dayCount);
        }else{
            entity.setNumbersOfDays(entity.getNumbersOfDays()-dayCount);
        }
        repository.saveAndFlush(entity);
    }
}
