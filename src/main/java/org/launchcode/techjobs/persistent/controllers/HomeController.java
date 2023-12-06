package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
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
        model.addAttribute("jobs", jobRepository.findAll());
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
    public String processAddJobForm(@ModelAttribute @Valid Job newJob, //where is it getting this new job?? the above method?
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        System.out.println(errors);
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills); //get skills list from skills(sent as ids)


        Optional<Employer> result = employerRepository.findById(employerId); //get Employer from employerId (working)

        if(!result.isPresent()){
            return "add";
        }

        if (errors.hasErrors()) { //__________!!!!is there errors? trouble with @notnull in Jobs' employer field
	        model.addAttribute("title", "Add Job");
            model.addAttribute(new Job());
            model.addAttribute("skills", skillRepository.findAll());
            model.addAttribute("employers", employerRepository.findAll());
            System.out.println(errors);
            return "add";
        }



        Employer employer = result.get(); // get employer from Optional<Employer> result above (working)

//     ____________________________from testProcessAddJobFormHandlesSkillsProperly:
//        new Expectations() {{
//            skillRepository.findAllById((Iterable<Integer>) any);
//            job.setSkills((List<Skill>) any); //_______________________missing this invocation?? I have that below though.
//        }};

//        Job newJob = new Job(name, employer, skillObjs);
        newJob.setEmployer(employer); //set employer to the job (working)
        newJob.setSkills(skillObjs); //set skill list to the job

        System.out.println(newJob.getEmployer());
        System.out.println(newJob.getSkills());

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
