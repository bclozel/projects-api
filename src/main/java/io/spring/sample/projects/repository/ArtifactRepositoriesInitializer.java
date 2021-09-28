package io.spring.sample.projects.repository;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ArtifactRepositoriesInitializer implements ApplicationRunner {

	Logger logger = LoggerFactory.getLogger(ArtifactRepositoriesInitializer.class);

	private final ArtifactRepositories repositories;

	public ArtifactRepositoriesInitializer(ArtifactRepositories repositories) {
		this.repositories = repositories;
	}

	@Override
	public void run(ApplicationArguments args) {
		List<ArtifactRepository> repositoryList = Arrays.asList(
				new ArtifactRepository("spring-releases", "Spring Releases", "https://repo.spring.io/release"),
				new ArtifactRepository("spring-milestones", "Spring Milestones", "https://repo.spring.io/milestone"),
				new ArtifactRepository("spring-snapshots", "Spring Snapshots", "https://repo.spring.io/snapshot"));
		repositories.saveAll(repositoryList);
		logger.info("Created " + repositoryList.size() + " ArtifactRepository instance(s).");
	}

}
