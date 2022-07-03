package com.mbt.yapikredi.ik.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@ApiModel(value = "Çalışan bilgisinin veri modeli", description = "Model")
public class EmployeeModel extends BaseEmployeeModel {

    @NotNull
    @Positive
    @ApiModelProperty(value = "Çalışanın id'si")
    private Long id;

    @NotNull
    @PastOrPresent
    @ApiModelProperty(value = "Çalışanın işe başladığı tarih")
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
