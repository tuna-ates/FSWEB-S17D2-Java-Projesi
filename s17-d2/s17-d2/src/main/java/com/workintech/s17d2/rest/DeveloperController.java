package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech")
public class DeveloperController {
    private Taxable taxable;
    private  Map<Integer, Developer> developerMap;
    @PostConstruct
    public void postConstruct(){
         developerMap =new HashMap<>();
    }

    @Autowired
    public DeveloperController(Taxable taxable) {
      this.taxable=taxable;

    }


    @PostMapping("/")
    public Developer addDeveloper(@RequestBody Developer developer){
        if (developer.getExperience().equals(Experience.JUNIOR)){
            Developer junior=new JuniorDeveloper(developer.getId(),developer.getName(),developer.getSalary());
            double newSalary=junior.getSalary()-(taxable.getSimpleTaxRate()* junior.getSalary());
           junior.setSalary(newSalary);
            developerMap.put(junior.getId(), junior);

            return developerMap.get(developer.getId());
        }
        else if (developer.getExperience().equals(Experience.MID)){
            Developer mid=new MidDeveloper(developer.getId(),developer.getName(),developer.getSalary());
            double newSalary= mid.getSalary()-(mid.getSalary()*taxable.getMiddleTaxRate());
            mid.setSalary(newSalary);
            developerMap.put(mid.getId(), mid);
            return developerMap.get(developer.getId());
        }
        else if (developer.getExperience().equals(Experience.SENIOR)){
            Developer senior=new SeniorDeveloper(developer.getId(),developer.getName(),developer.getSalary());
            double newSalary= senior.getSalary()-(senior.getSalary()*taxable.getUpperTaxRate());
            senior.setSalary(newSalary);
            developerMap.put(senior.getId(), senior);
            return developerMap.get(developer.getId());
        }

        return null;
    }
    @GetMapping("/developers")
    public List<Developer> allDeveloper(){

         return  developerMap.values().stream().toList();

    }
    @GetMapping("/developers/{id}")
    public Developer IdDeveloper(@PathVariable int id){

       return developerMap.get(id);

    }
    @PutMapping("/developers/{id}")
    public Developer updateDeveloper(@PathVariable int id,@RequestBody Developer developer){
        developerMap.replace(id,developer);
        return developer;
    }
    @DeleteMapping("/developers/{id}")
    public Developer deleteDeveloper(@PathVariable int id){
        Developer developer =developerMap.get(id);
        developerMap.remove(id);
        return developer;
    }

}
