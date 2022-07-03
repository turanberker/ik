package com.mbt.yapikredi.ik.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel(value = "Çalışan ve izin bilgisinin beraber döndürüldüğü veri modeli", description = "Model")
public class EmployeeAllowanceModel extends BaseEmployeeModel {

    @NotNull
    @Positive
    @ApiModelProperty(value = "Çalışanın id'si")
    private Long id;

    @ApiModelProperty(value = "Çalışanın hakettiği izin miktarı")
    private Integer numbersOfAllowanceDay;

    @QueryProjection
    public EmployeeAllowanceModel(Long id, String firstName, String lastName, Integer numbersOfAllowanceDay) {
        super(firstName, lastName);
        this.id = id;
        this.numbersOfAllowanceDay = numbersOfAllowanceDay == null ? 0 : numbersOfAllowanceDay;
    }

    public EmployeeAllowanceModel() {
    }

    public Integer getNumbersOfAllowanceDay() {
        return numbersOfAllowanceDay;
    }

    public void setNumbersOfAllowanceDay(Integer numbersOfAllowanceDay) {
        this.numbersOfAllowanceDay = numbersOfAllowanceDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
