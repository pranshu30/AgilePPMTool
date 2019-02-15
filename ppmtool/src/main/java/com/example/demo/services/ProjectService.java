package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.User;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class ProjectService {
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project, String Username){
    	
    	 if(project.getId() != null){
             Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
             if(existingProject !=null &&(!existingProject.getProjectLeader().equals(Username))){
                 throw new ProjectIdException("Project not found in your account");
             }else if(existingProject == null){
                 throw new ProjectIdException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
             }
         }
    	
    	
    	try {
    	User user = userRepository.findByUsername(Username);
    	project.setUser(user);
    	
    	project.setProjectLeader(user.getUsername());
    	
    	String projectid = project.getProjectIdentifier().toUpperCase();
    	
    		project.setProjectIdentifier(projectid);
    		
    		if(project.getId()==null) {
    			Backlog backlog= new Backlog();
    			project.setBacklog(backlog);
    			backlog.setProject(project);
    			backlog.setProjectIdentifier(projectid);
    			
    		}
    		
    		if(project.getId()!=null) {
    			project.setBacklog(backlogRepository.findByProjectIdentifier(projectid));
    		}
    		 return projectRepository.save(project);
    	}
    	catch (Exception ex){
    		throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exist");
    		
    	}
    	
    	}
    
    public Project findProjectByIdentifier(String projectId,String username) {
    	
    	Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    	
    	if(project == null) {
    		throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' does not exist");
    	}
    	
    	if(!project.getProjectLeader().equals(username)) {
    		throw new ProjectIdException("Project not found in your account");
    	}
    	
    	return project;
    }

    public Iterable<Project> findAllProject(String Username) {
    	User user = userRepository.findByUsername(Username);
    	return projectRepository.findAllByProjectLeader(Username);
    	
    }
    
    public void DeleteProjectByIdentifier(String projectId,String username) {
    	
    	
    	//Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    	
    	//if(project == null) {
    		//throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' does not exist");
    	//}
    	
    	projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
    	
  
}