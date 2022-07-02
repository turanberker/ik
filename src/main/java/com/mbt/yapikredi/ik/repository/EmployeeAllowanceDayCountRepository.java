package com.mbt.yapikredi.ik.repository;

import com.mbt.yapikredi.ik.entity.EmployeeAllowanceDayCountEntity;
import com.mbt.yapikredi.ik.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAllowanceDayCountRepository extends BaseRepository<EmployeeAllowanceDayCountEntity> {

    EmployeeAllowanceDayCountEntity findByEmployeeId(Long employeeId);
}
