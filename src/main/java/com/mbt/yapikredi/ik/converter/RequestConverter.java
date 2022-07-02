package com.mbt.yapikredi.ik.converter;

import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses =EntityReferansConverter.class )
public interface RequestConverter {

    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id" )
    })
    RequestDetailModel convertToDetailModel(RequestEntity entity);

    @Mappings({
            @Mapping(target = "employee", source = "employeeId" ),
            @Mapping(target = "annualLeaveStart", source = "startDate" ),
            @Mapping(target = "annualLeaveEnd", source = "endDate" ),
    })
    RequestEntity convertToEntity(CreateRequestModel createRequestModel);
}
