package com.mbt.yapikredi.ik.dto;

import com.mbt.yapikredi.ik.data.EnumRequestStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@ApiModel(value = "İzin taleplerinin listelendiği veri modeli", description = "Model")
public class RequestListModel {

    @NotNull
    @Positive
    @ApiModelProperty(value = "İzin Talebinin id'si")
    private Long id;

    @NotNull
    @Positive
    @ApiModelProperty(value = "Çalışanın id'si")
    private Long employeeId;

    @NotBlank
    @ApiModelProperty(value = "Çalışanın tam adı")
    private String employee;

    @NotNull
    @ApiModelProperty(value = "Talep edilen izin başlangıç tarihi")
    private LocalDate startDate;

    @NotNull
    @ApiModelProperty(value = "Talep edilen izin bitiş tarihi")
    private LocalDate endDate;

    @NotNull
    @ApiModelProperty(value = "izin talebinin durumu")
    private EnumRequestStatus status;

    @NotNull
    @Positive
    @ApiModelProperty(value = "talep edilen izin miktarı")
    private Integer requestedCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
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
