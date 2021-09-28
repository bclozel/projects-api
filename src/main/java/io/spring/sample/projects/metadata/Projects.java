package io.spring.sample.projects.metadata;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Projects extends JpaRepository<Project, String> {

	Optional<Project> findBySlug(String slug);
}
