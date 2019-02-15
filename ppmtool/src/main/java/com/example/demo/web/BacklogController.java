package com.example.demo.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.services.MapValidationServiceError;
import com.example.demo.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
    private ProjectTaskService projectTaskService;
	@Autowired
    private MapValidationServiceError mapValidationServiceError;
	
	
	 @PostMapping("/{backlog_id}")
	 public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
			 BindingResult result,@PathVariable String backlog_id,Principal principal){
	    	ResponseEntity<?> errorMap = mapValidationServiceError.MapValidationError(result);
	    	if(errorMap!=null) return errorMap;
	        
	    	
	    	ProjectTask projectTask1 = projectTaskService.addprojectTask(backlog_id,projectTask,principal.getName());
	        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	    }
	 
	 @GetMapping("/{backlog_id}")
	 public Iterable<ProjectTask> getPTByBacklog(@PathVariable String backlog_id,Principal principal){
		 Iterable<ProjectTask> projectTask2 = projectTaskService.findPTById(backlog_id,principal.getName());
	    	return projectTask2;
	    }
	 
	 @GetMapping("/{backlog_id}/{pt_id}")
	 public ResponseEntity<?> getPTBySequence(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
	    		    	
	    	ProjectTask projectTask3 = projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
	        return new ResponseEntity<ProjectTask>(projectTask3, HttpStatus.OK);
	    }
	 
	 
	 @PatchMapping("/{backlog_id}/{pt_id}")
	 public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
			 BindingResult result,@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
		 
		 ResponseEntity<?> errorMap = mapValidationServiceError.MapValidationError(result);
	    	if(errorMap!=null) return errorMap;
	    	
	    	ProjectTask updateTask = projectTaskService.updadteprojectTask(projectTask, backlog_id, pt_id,principal.getName());
	        return new ResponseEntity<ProjectTask>(updateTask, HttpStatus.OK);
	    	
	    	
	 }
	 
	 
	 @DeleteMapping("/{backlog_id}/{pt_id}")
	  public ResponseEntity<?> deletePTBySequence(@PathVariable String backlog_id,@PathVariable String pt_id,
			  Principal principal){
	    	projectTaskService.deleteProjectTask(backlog_id, pt_id,principal.getName());
	    	return new ResponseEntity<String>("ProjectTask with ID: '"+pt_id+"' was deleted",HttpStatus.OK);
	    }
}
