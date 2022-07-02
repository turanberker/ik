package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.dto.EmployeeAllowanceModel;
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
import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EntityManager em;

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EntityManager em, EmployeeRepository repository) {
        this.em = em;
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<EmployeeAllowanceModel> findEmployeeAllowanceList(String name, Integer pageSize, Integer startPage) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (name != null && !name.isEmpty()) {
            booleanBuilder.and(QEmployeeEntity.employeeEntity.firstName.concat(" ").concat(QEmployeeEntity.employeeEntity.lastName).containsIgnoreCase(name));
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        long totalElementCount = repository.count(booleanBuilder);
        List<EmployeeAllowanceModel> data = queryFactory.select(Projections.constructor(EmployeeAllowanceModel.class,
                                QEmployeeEntity.employeeEntity.id,
                                QEmployeeEntity.employeeEntity.firstName,
                                QEmployeeEntity.employeeEntity.lastName,
                                QEmployeeAllowanceDayCountEntity.employeeAllowanceDayCountEntity.numbersOfDays
                        )
                )
                .from(QEmployeeEntity.employeeEntity)
                .leftJoin(QEmployeeAllowanceDayCountEntity.employeeAllowanceDayCountEntity)
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
}
