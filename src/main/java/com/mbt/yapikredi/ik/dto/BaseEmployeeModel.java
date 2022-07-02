package com.mbt.yapikredi.ik.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class BaseEmployeeModel {

    @NotNull
    @Positive
    private Long id;

    @Size(max = 75)
    @NotBlank
    private String firstName;

    @Size(max = 75)
    private String lastName;

    public BaseEmployeeModel() {
    }

    public BaseEmployeeModel(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
