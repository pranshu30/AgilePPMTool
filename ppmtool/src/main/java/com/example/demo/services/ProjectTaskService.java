package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addprojectTask(String projectIdentifier, ProjectTask projectTask,String username) {
		//Exception project not found
		
		   //PTs to be added to a specific project, project != null, BL exists
	//try{
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog(); 
				//backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		
		
		   //set the bl to pt
		projectTask.setBacklog(backlog);
		   //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
		Integer BacklogSequence = backlog.getPTSequence();
		
		   // Update the BL SEQUENCE
		BacklogSequence++;
		
		backlog.setPTSequence(BacklogSequence);
		   //Add Sequence to Project Task
		projectTask.setProjectSeqeuence(projectIdentifier.toUpperCase()+'-'+BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());
		   //INITIAL priority when priority null
		
		if(projectTask.getPriority()==null || projectTask.getPriority()==0) {
		projectTask.setPriority(3);
		}
		   //INITIAL status when status is null

		if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepository.save(projectTask);
		
		//}
		//catch(Exception err) {
		//	throw new ProjectIdException("Project ID '"+projectIdentifier.toUpperCase()+"' does not exist");
	//	}
		
		
		
	}
	
	//Check whether backlogId exist or not
	protected boolean checkId(String backlog_Id) {
		 Project project = projectRepository.findByProjectIdentifier(backlog_Id);
	    	
		 if(project==null) {
	    		return false;
	    	}
		 
			 return true;
		 
	}
	
	 public Iterable<ProjectTask> findPTById(String backlog_Id,String username) {
		   projectService.findProjectByIdentifier(backlog_Id, username);
		 	//if(!checkId(backlog_Id)) {
		 	//	throw new ProjectIdException("Project ID '"+backlog_Id.toUpperCase()+"' does not exist");
		 	//}	    	
		 	return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_Id.toUpperCase());	
	    }
	
	 public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id,String username) {

		 projectService.findProjectByIdentifier(backlog_id, username);

		 //Checking if backlog_id exist 	
		//	if(!checkId(backlog_id)) {
		 //		throw new ProjectIdException("Project ID '"+backlog_id.toUpperCase()+"' does not exist");
		 	//}	   
		 ProjectTask projectTask = projectTaskRepository.findByProjectSeqeuence(pt_id.toUpperCase());
	    	
		 //Checking project task exist
		 
	    	if(projectTask==null) {
	    		throw new ProjectIdException("Project Task '"+pt_id.toUpperCase()+"' does not exist");
	    	}
	    	
	    //Checking if project task belong to backlog_Id	
	    	if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
	    		throw new ProjectIdException("Project Task '"+pt_id.toUpperCase()+
	    				"' does not belong to Project ID '"+backlog_id.toUpperCase()+ "' ");

	    	}
	    	
	    	return projectTask;
	    }
	
	 public ProjectTask updadteprojectTask(ProjectTask updateTask,String backlog_Id, String pt_id,String username) {
		 ProjectTask projecttask = findPTByProjectSequence(backlog_Id,pt_id,username);
		 
		 projecttask = updateTask;
		 
		 return projectTaskRepository.save(projecttask);
		 
		 
	 }
	
	 
	 public void deleteProjectTask(String backlog_Id, String pt_id,String username) {
		 ProjectTask projecttask = findPTByProjectSequence(backlog_Id,pt_id,username);
		 
		projectTaskRepository.delete(projecttask);
		 
		 
	 }
	
}
