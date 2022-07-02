package com.mbt.yapikredi.ik.converter;

import com.mbt.yapikredi.ik.dto.EmployeeModel;
import com.mbt.yapikredi.ik.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EntityReferansConverter.class)
public interface EmployeeConverter {

    EmployeeEntity convertToEntity(EmployeeModel model);

    EmployeeModel convertToModel(EmployeeEntity entity);
}
