package com.mbt.yapikredi.ik.services;

import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import com.mbt.yapikredi.ik.dto.CreateRequestModel;
import com.mbt.yapikredi.ik.dto.RequestDetailModel;
import com.mbt.yapikredi.ik.dto.RequestListModel;
import com.mbt.yapikredi.ik.dto.base.PageModel;
import com.mbt.yapikredi.ik.exceptions.CheckedException;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface RequestService {

    RequestDetailModel create(@Valid CreateRequestModel createRequestModel) throws CheckedException;

    RequestDetailModel approve(@NotNull @Positive Long requestId) throws CheckedException;

    RequestDetailModel reject(@NotNull @Positive Long requestId) throws CheckedException;

    PageModel<RequestListModel> findRequestsByStatus(EnumRequestStatus requestStatus, @Positive  @NotNull  Integer pageSize,@PositiveOrZero @NotNull Integer startPage);
}
