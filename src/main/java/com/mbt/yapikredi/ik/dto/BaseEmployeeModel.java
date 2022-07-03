package com.mbt.yapikredi.ik.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "Temel Çalışan Bilgilerinin veri modeli", description = "Model")
public class BaseEmployeeModel {

    @ApiModelProperty(value = "Çalışanın Adı")
    @Size(max = 75)
    @NotBlank
    private String firstName;

    @ApiModelProperty(value = "Çalışanın Soyadı")
    @Size(max = 75)
    private String lastName;

    public BaseEmployeeModel() {
    }

    public BaseEmployeeModel(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
