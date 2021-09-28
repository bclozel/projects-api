package io.spring.sample.projects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProjectsProperties.class)
public class ProjectsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectsApiApplication.class, args);
	}

}
