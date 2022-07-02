package com.mbt.yapikredi.ik.converter;

import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class EntityReferansConverter {

    @Autowired
    private EntityManager em;


    public EmployeeEntity toEmployeeEntity(Long employeeId) {
        return em.getReference(EmployeeEntity.class, employeeId);
    }
}
