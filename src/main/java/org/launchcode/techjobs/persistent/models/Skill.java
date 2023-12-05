package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    public Skill(){}

    @ManyToMany(mappedBy="skills") //______________many-to-many
    private List<Job> jobs= new ArrayList<>();



    @Size(max = 600, message = "Description is too long.")
    private String description;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
