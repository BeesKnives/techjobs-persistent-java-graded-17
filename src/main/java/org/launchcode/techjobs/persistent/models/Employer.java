package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Employer extends AbstractEntity {

    public Employer(){

    }

    @NotNull(message = "Location required.")
    @Size(max = 60, message = "Location name is too long.")
    private String location;


    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
