package io.spring.sample.projects.metadata;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProjectsInitializer implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(ProjectsInitializer.class);

	private final Projects projects;

	public ProjectsInitializer(Projects projects) {
		this.projects = projects;
	}

	@Override
	public void run(ApplicationArguments args) {
		Project framework = new Project("spring-framework", "Spring Framework");
		framework.createRelease(Version.of("5.3.10")).setCurrent(true);
		framework.createRelease(Version.of("5.3.11-SNAPSHOT"));
		framework.createRelease(Version.of("5.2.17"));
		framework.createRelease(Version.of("5.2.18-BUILD-SNAPSHOT"));

		Project boot = new Project("spring-boot", "Spring Boot");
		boot.createRelease(Version.of("2.6.0-SNAPSHOT"));
		boot.createRelease(Version.of("2.6.0-M2"));
		boot.createRelease(Version.of("2.5.6-SNAPSHOT"));
		boot.createRelease(Version.of("2.5.5")).setCurrent(true);
		boot.createRelease(Version.of("2.4.12-SNAPSHOT"));
		boot.createRelease(Version.of("2.4.11"));
		boot.createRelease(Version.of("2.3.12.RELEASE"));

		Project graphql = new Project("spring-graphql", "Spring GraphQL");
		graphql.createRelease(Version.of("1.0.0-M2"));
		graphql.createRelease(Version.of("1.0.0-SNAPSHOT"));

		List<Project> projects = Arrays.asList(framework, boot, graphql);
		this.projects.saveAll(projects);
		logger.info("Created " + projects.size() + " Project(s).");
	}
}
