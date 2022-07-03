package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.converter.EmployeeConverter;
import com.mbt.yapikredi.ik.dto.CreateEmployeeModel;
import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
import com.mbt.yapikredi.ik.dto.EmployeeModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.entity.QEmployeeAllowanceDayCountEntity;
import com.mbt.yapikredi.ik.entity.QEmployeeEntity;
import com.mbt.yapikredi.ik.repository.EmployeeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    private final EmployeeConverter converter;

    private final AnnualLeaveAllowanceService anualLeaveAllowanceService;

    public EmployeeServiceImpl(EntityManager em, EmployeeRepository repository, EmployeeConverter converter, AnnualLeaveAllowanceService anualLeaveAllowanceService) {
        this.repository = repository;
        this.converter = converter;
        this.anualLeaveAllowanceService = anualLeaveAllowanceService;
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<EmployeeAllowanceModel> findEmployeeAllowanceList(String name, Integer pageSize, Integer startPage) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (name != null && !name.isEmpty()) {
            booleanBuilder.and(QEmployeeEntity.employeeEntity.firstName.concat(" ").concat(QEmployeeEntity.employeeEntity.lastName).containsIgnoreCase(name));
        }

        long totalElementCount = repository.count(booleanBuilder);
        List<EmployeeAllowanceModel> data = repository.getQueryFactory().select(Projections.constructor(EmployeeAllowanceModel.class,
                                QEmployeeEntity.employeeEntity.id,
                                QEmployeeEntity.employeeEntity.firstName,
                                QEmployeeEntity.employeeEntity.lastName,
                                QEmployeeAllowanceDayCountEntity.employeeAllowanceDayCountEntity.numbersOfDays
                        )
                )
                .from(QEmployeeEntity.employeeEntity)
                .leftJoin(QEmployeeAllowanceDayCountEntity.employeeAllowanceDayCountEntity)
                .on(QEmployeeAllowanceDayCountEntity.employeeAllowanceDayCountEntity.employee.eq(QEmployeeEntity.employeeEntity))
                .where(booleanBuilder)
                .limit(pageSize)
                .offset(startPage)
                .fetch();
        return new PageModel(data, startPage, pageSize, totalElementCount);

    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeEntity getEmployee(Long employeeId) {
        return repository.getReferenceById(employeeId);
    }

    @Override
    public EmployeeModel create(CreateEmployeeModel model) {
        EmployeeEntity employeeEntity = converter.convertToEntity(model);
        Period intervalPeriod = Period.between(employeeEntity.getStartDate(), LocalDate.now());
        employeeEntity = repository.save(employeeEntity);
        if (intervalPeriod.getYears() > 0) {
            //işe gireli bir yıldan fazla olmuştur
            anualLeaveAllowanceService.addEmployeeAllowancesForNewEmployee(employeeEntity);
        }
        return converter.convertToModel(employeeEntity);

    }
}
