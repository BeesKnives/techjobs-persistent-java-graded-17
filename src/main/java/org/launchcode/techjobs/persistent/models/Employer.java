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

    @NotNull(message = "Location required.")
    @Size(max = 60, message = "Location name is too long.")
    private String location;

    @OneToMany  //________________________________________One to Many, cascade?
    @JoinColumn(name="employer_id")
    public final List<Job> jobs = new ArrayList<>();//try public instead of private


    public Employer(){

    }

    public Employer(String location){
        this.location = location;
    }


    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
