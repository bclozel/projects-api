package io.spring.sample.projects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "projects")
public class ProjectsProperties {

	private final Github github = new Github();

	public Github getGithub() {
		return this.github;
	}

	public static class Github {

		private String username;

		private String accessToken;

		public String getUsername() {
			return this.username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getAccessToken() {
			return this.accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
	}
}
