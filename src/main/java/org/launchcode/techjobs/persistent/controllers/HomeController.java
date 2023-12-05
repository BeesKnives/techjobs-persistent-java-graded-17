package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute("employers", employerRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId) {

        System.out.println(employerId);

        Optional<Employer> result = employerRepository.findById(employerId); //get Employer from employerId (working)
        System.out.println(result);

        if (errors.hasErrors()) { //__________!!!!is there errors? trouble with @notnull in Jobs' employer field
	        model.addAttribute("title", "Add Job");
            model.addAttribute(new Job());
            model.addAttribute("skills", skillRepository.findAll());
            model.addAttribute("employers", employerRepository.findAll());
            System.out.println(errors);
            return "add";
        }

        Employer employer = result.get(); // get employer from Optional<Employer> result above (working)
 //       System.out.println(employer);

//        if(employer.isPresent()){
//            System.out.println("good job!");
//        }
        newJob.setEmployer(employer); //set the employer to the job (working)
 //       System.out.println(newJob.getEmployer());

        jobRepository.save(newJob); //save job to repository (working)


        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> result = jobRepository.findById(jobId);
        if (!result.isPresent()){
            return "view";
        }
        Job thisJob = result.get();
        model.addAttribute(thisJob);
        return "view";
    }

}
