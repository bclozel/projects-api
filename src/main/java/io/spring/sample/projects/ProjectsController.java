package io.spring.sample.projects;

import java.util.Optional;

import io.spring.sample.projects.metadata.Project;
import io.spring.sample.projects.metadata.Projects;
import io.spring.sample.projects.metadata.Release;
import io.spring.sample.projects.metadata.Version;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectsController {

	private final Projects projects;

	public ProjectsController(Projects projects) {
		this.projects = projects;
	}

	@QueryMapping
	public Optional<Project> project(@Argument String slug) {
		return this.projects.findBySlug(slug);
	}

	@MutationMapping
	public Optional<Release> createRelease(@Argument String projectSlug, @Argument Version version) {
		return this.projects.findBySlug(projectSlug).map(p -> {
			Release release = p.createRelease(version);
			this.projects.save(p);
			return release;
		});
	}

}
