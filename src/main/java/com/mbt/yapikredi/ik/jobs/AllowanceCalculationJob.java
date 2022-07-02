package com.mbt.yapikredi.ik.jobs;

import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import com.mbt.yapikredi.ik.entity.QAnnualLeaveAllowanceEntity;
import com.mbt.yapikredi.ik.entity.QEmployeeEntity;
import com.mbt.yapikredi.ik.services.AnnualLeaveAllowanceService;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Service
public class AllowanceCalculationJob {

    @Autowired
    private EntityManager em;

    @Autowired
    private AnnualLeaveAllowanceService allowanceService;

    @Scheduled(cron = "${jobs.allowanceCalculation}")
    public void calculate() {
        QAnnualLeaveAllowanceEntity annualLeaveAllowanceEntity = QAnnualLeaveAllowanceEntity.annualLeaveAllowanceEntity;

        LocalDate aYearBefore = LocalDate.now().minusYears(1);

        JPAQuery<Long> subQuery = new JPAQuery<>();
        subQuery.select(annualLeaveAllowanceEntity.employee.id)
                .from(annualLeaveAllowanceEntity)
                .where(annualLeaveAllowanceEntity.allowanceDate.gt(aYearBefore));

        QEmployeeEntity employeeEntity = QEmployeeEntity.employeeEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<EmployeeEntity> fetch;
        do {
            fetch = queryFactory.select(employeeEntity).from(employeeEntity)
                    .where(employeeEntity.startDate.lt(aYearBefore).and(
                            JPAExpressions.selectOne()
                                    .from(annualLeaveAllowanceEntity)
                                    .where(annualLeaveAllowanceEntity.allowanceDate.gt(aYearBefore))
                                    .notExists()))
                    .limit(100).offset(0)
                    .fetch();

            fetch.forEach(e -> allowanceService.addEmployeeAllowance(e));
        } while (!fetch.isEmpty());

    }
}
