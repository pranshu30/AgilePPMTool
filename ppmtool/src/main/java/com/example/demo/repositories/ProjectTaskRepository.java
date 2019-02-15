package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{
	
	Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);
	
	ProjectTask findByProjectSeqeuence(String pt_id);
}
