package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Integer PTSequence = 0;
	private String projectIdentifier;

	//One to One with Project
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn(columnDefinition="project_id",nullable =false)
	
	//Infinite recursion:goto child class and add json ignore
	@JsonIgnore
	private Project project;
	
	//One to Many with Project Task
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH,mappedBy="backlog", orphanRemoval= true)
	private List<ProjectTask> projectTasks = new ArrayList<>();
	
	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Backlog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPTSequence() {
		return PTSequence;
	}

	public void setPTSequence(Integer pTSequence) {
		PTSequence = pTSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	

}
