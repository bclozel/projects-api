package io.spring.sample.projects;

import java.util.Optional;

import io.spring.sample.projects.contributors.GitHubClient;
import io.spring.sample.projects.metadata.Project;
import io.spring.sample.projects.metadata.Projects;
import io.spring.sample.projects.metadata.Version;
import io.spring.sample.projects.repository.ArtifactRepositories;
import io.spring.sample.projects.repository.ArtifactRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.boot.test.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@GraphQlTest(ProjectsController.class)
class ProjectsControllerTests {

	private static Project springGraphQl;

	private static ArtifactRepository milestoneRepository;

	private static ArtifactRepository snapshotRepository;
	
	@Autowired
	private GraphQlTester graphQlTester;

	@MockBean
	private Projects projects;

	@MockBean
	private ArtifactRepositories artifactRepositories;

	@MockBean
	private GitHubClient gitHubClient;


	@BeforeAll
	static void setup() {
		springGraphQl = new Project("spring-graphql", "Spring GraphQL");
		springGraphQl.createRelease(Version.of("1.0.0-M3"));
		springGraphQl.createRelease(Version.of("1.0.0-SNAPSHOT"));
		milestoneRepository = new ArtifactRepository("spring-milestones", "Spring Milestones", "https://repo.spring.io/milestone");
		snapshotRepository = new ArtifactRepository("spring-snapshots", "Spring Snapshots", "https://repo.spring.io/snapshot");
	}

	@Test
	void shouldListReleaseRepositories() {
		given(this.projects.findBySlug(eq("spring-graphql")))
				.willReturn(Optional.of(springGraphQl));

		given(this.artifactRepositories.findById(eq("spring-milestones")))
				.willReturn(Optional.of(milestoneRepository));
		given(this.artifactRepositories.findById(eq("spring-snapshots")))
				.willReturn(Optional.of(snapshotRepository));

		this.graphQlTester.query("""
				{
				  project(slug: "spring-graphql") {
					name
					releases {
					  version
					  repository {
						id
					  }
					}
				  }
				}
				""")
				.execute().path("project.releases[*].repository.id")
				.entityList(String.class).contains("spring-milestones", "spring-snapshots");
	}
}