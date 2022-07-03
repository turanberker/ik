package com.mbt.yapikredi.ik.dto;

import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel(value = "İzin talebi veri modeli", description = "Model")
public class RequestDetailModel extends CreateRequestModel {

    @NotNull
    @Positive
    @ApiModelProperty(value = "İzin Talebinin id'si")
    private Long Id;

    @NotNull
    @ApiModelProperty(value = "İzin Talebinin durumu")
    private EnumRequestStatus status;

    @NotNull
    @Positive
    @ApiModelProperty(value = "Talep edilen izin miktarı")
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
