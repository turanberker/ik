package com.mbt.yapikredi.ik.entity;

import com.mbt.yapikredi.ik.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class EmployeeEntity extends BaseEntity {

    @Size(max = 75)
    @NotBlank
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(max = 75)
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @PastOrPresent
    @Column(name = "START_DATE")
    private LocalDate startDate;
    
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
