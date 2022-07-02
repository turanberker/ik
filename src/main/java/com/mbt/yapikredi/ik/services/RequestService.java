package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public interface RequestService {

    RequestDetailModel create(@Valid CreateRequestModel createRequestModel) throws CheckedException;

    RequestDetailModel approve(@NotNull @Positive Long requestId);

    RequestDetailModel reject(@NotNull @Positive Long requestId);
}
