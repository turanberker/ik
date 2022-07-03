package com.mbt.yapikredi.ik.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@ApiModel(value = "Yeni İzin Talebini yapılırken kullanılacak veri modeli", description = "Model")
public class CreateRequestModel {

    @NotNull
    @Positive
    @ApiModelProperty(value = "Çalışanın id'si")
    private Long employeeId;

    @NotNull
    @ApiModelProperty(value = "İzin başlangıç tarihi")
    private LocalDate startDate;

    @NotNull
    @ApiModelProperty(value = "İzin bitiş tarihi")
    private LocalDate endDate;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
