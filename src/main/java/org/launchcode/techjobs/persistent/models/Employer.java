package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Employer extends AbstractEntity {

    public Employer(){

    }

    @NotNull(message = "Location required.")
    @Size(max = 60, message = "Location name is too long.")
    private String location;


    @OneToMany (cascade = CascadeType.ALL) //________________________________________One to Many, cascade?
    @JoinColumn(name="employer_id")
    private final List<Job> jobs = new ArrayList<>();



    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
