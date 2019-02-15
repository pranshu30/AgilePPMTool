package com.example.demo.web;

import java.security.Principal;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Project;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.services.MapValidationServiceError;
import com.example.demo.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private MapValidationServiceError mapValidationServiceError;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,BindingResult result,Principal principal){
    	ResponseEntity<?> error = mapValidationServiceError.MapValidationError(result);
    	if(error!=null) return error;
        
    	
    	Project project1 = projectService.saveOrUpdateProject(project,principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId,Principal principal){
    	Project project = projectService.findProjectByIdentifier(projectId,principal.getName());
    	return new ResponseEntity<Project>(project,HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProject(Principal principal){
    	
    	return projectService.findAllProject(principal.getName());
    }
    
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId,Principal principal){
    	projectService.DeleteProjectByIdentifier(projectId,principal.getName());
    	return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted",HttpStatus.OK);
    }
    
   
    
}