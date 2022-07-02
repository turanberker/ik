package com.mbt.yapikredi.ik.dto;

import com.mbt.yapikredi.ik.data.EnumRequestStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RequestDetailModel extends CreateRequestModel {

    @NotNull
    @Positive
    private Long Id;

    @NotNull
    private EnumRequestStatus status;

    @NotNull
    @Positive
    private Integer requestedCount;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public EnumRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRequestStatus status) {
        this.status = status;
    }

    public Integer getRequestedCount() {
        return requestedCount;
    }

    public void setRequestedCount(Integer requestedCount) {
        this.requestedCount = requestedCount;
    }
}
