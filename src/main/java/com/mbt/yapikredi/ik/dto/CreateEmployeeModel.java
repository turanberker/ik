package com.mbt.yapikredi.ik.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@ApiModel(value = "Yeni Çalışan Kaydetmek için kullanılacak veri modeli", description = "Model")
public class CreateEmployeeModel extends BaseEmployeeModel{
    @NotNull
    @PastOrPresent
    @ApiModelProperty(value = "İşe başladığı tarih")
    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
