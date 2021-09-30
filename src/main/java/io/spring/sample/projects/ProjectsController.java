package io.spring.sample.projects;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.spring.sample.projects.contributors.Contributor;
import io.spring.sample.projects.contributors.GitHubClient;
import io.spring.sample.projects.contributors.Profile;
import io.spring.sample.projects.metadata.Project;
import io.spring.sample.projects.metadata.Projects;
import io.spring.sample.projects.metadata.Release;
import io.spring.sample.projects.metadata.Version;
import io.spring.sample.projects.repository.ArtifactRepositories;
import io.spring.sample.projects.repository.ArtifactRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectsController {

	private final Projects projects;

	private final ArtifactRepositories artifactRepositories;

	private final GitHubClient gitHubClient;

	public ProjectsController(Projects projects, ArtifactRepositories artifactRepositories, GitHubClient gitHubClient) {
		this.projects = projects;
		this.artifactRepositories = artifactRepositories;
		this.gitHubClient = gitHubClient;
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

	@SchemaMapping
	public Optional<ArtifactRepository> repository(Release release) {
		return switch (release.getStatus()) {
			case SNAPSHOT -> this.artifactRepositories.findById("spring-snapshots");
			case MILESTONE -> this.artifactRepositories.findById("spring-milestones");
			case GENERAL_AVAILABILITY -> this.artifactRepositories.findById("spring-releases");
		};
	}

	@SchemaMapping
	public Flux<Contributor> contributors(Project project) {
		return this.gitHubClient.getContributors(project.getSlug());
	}

	@BatchMapping
	public Mono<Map<Contributor, Profile>> profile(List<Contributor> contributors) {
		return this.gitHubClient.fetchProfiles(Flux.fromIterable(contributors));
	}
}
