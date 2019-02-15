package com.example.demo.domain;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(updatable=false)
	private String projectSeqeuence;
	@NotBlank(message="Summary cannot be empty")
	private String summary;
	private String acceptanceCriteria;
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectSeqeuence=" + projectSeqeuence + ", Summary=" + summary
				+ ", acceptanceCriteria=" + acceptanceCriteria + ", status=" + status + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", created_At=" + created_At + ", updated_At=" + updated_At
				+ ", projectIdentifier=" + projectIdentifier + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSeqeuence() {
		return projectSeqeuence;
	}

	public void setProjectSeqeuence(String projectSeqeuence) {
		this.projectSeqeuence = projectSeqeuence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(Date updated_At) {
		this.updated_At = updated_At;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	private String status;
	private Integer priority;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date dueDate;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date created_At;
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;
	
	//Many to one Backlog
	@ManyToOne(fetch = FetchType.EAGER)// remove refresh
	@JoinColumn(columnDefinition="backlog_id",updatable=false, nullable=false)
	@JsonIgnore
	private Backlog backlog;
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	public ProjectTask() {
		
	}

	@Column(updatable=false)
	private String projectIdentifier;
	
	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}
	
	@PostUpdate
	protected void onUpdate() {
		this.updated_At = new Date();
	}
	
	
	
	

}
