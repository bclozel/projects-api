package io.spring.sample.projects.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ArtifactRepositories extends
		CrudRepository<ArtifactRepository, String>, QuerydslPredicateExecutor<ArtifactRepository> {

}
