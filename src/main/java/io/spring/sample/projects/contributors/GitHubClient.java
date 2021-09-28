package io.spring.sample.projects.contributors;

import java.util.Collections;
import java.util.Map;

import io.spring.sample.projects.ProjectsProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClient {

	private static final MediaType APPLICATION_VND_GITHUB = MediaType.parseMediaType("application/vnd.github.v3+json");

	private static final String REPOS_URL = "/repos/spring-projects/{project}";

	private static final String CONTRIBUTORS_URL = REPOS_URL + "/contributors";

	private static final String USER_URL = "/users/{username}";

	private final WebClient webClient;

	public GitHubClient(WebClient.Builder builder, ProjectsProperties properties)  {
		this.webClient = builder.baseUrl("https://api.github.com")
				.defaultHeaders(headers -> {
					headers.setAccept(Collections.singletonList(APPLICATION_VND_GITHUB));
					if (StringUtils.hasText(properties.getGithub().getUsername())) {
						headers.setBasicAuth(properties.getGithub().getUsername(), properties.getGithub().getAccessToken());
					}
				})
				.build();
	}

	public Flux<Contributor> getContributors(String projectId) {
		return this.webClient.get().uri(CONTRIBUTORS_URL, projectId).retrieve().bodyToFlux(Contributor.class).take(10);
	}

	public Mono<Profile> fetchProfile(Contributor contributor) {
		return this.webClient.get().uri(USER_URL, contributor.getGitHubId()).retrieve().bodyToMono(Profile.class);
	}

	public Mono<Map<Contributor, Profile>> fetchProfiles(Flux<Contributor> contributors) {
		return contributors
				.flatMap(contributor -> this.fetchProfile(contributor).map(profile -> Tuples.of(contributor, profile)))
				.collectMap(Tuple2::getT1, Tuple2::getT2);
	}
	
}
