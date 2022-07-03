package com.mbt.yapikredi.ik.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BaseEmployeeModel {



    @Size(max = 75)
    @NotBlank
    private String firstName;

    @Size(max = 75)
    private String lastName;

    public BaseEmployeeModel() {
    }

    public BaseEmployeeModel( String firstName, String lastName) {
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
