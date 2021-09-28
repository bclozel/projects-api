package io.spring.sample.projects.contributors;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Contributor {

	private String gitHubId;

	private Long contributionsCount;

	@JsonCreator
	public Contributor(@JsonProperty("login") String gitHubId, @JsonProperty("contributions") Long contributionsCount) {
		this.gitHubId = gitHubId;
		this.contributionsCount = contributionsCount;
	}

	public String getGitHubId() {
		return this.gitHubId;
	}

	public Long getContributionsCount() {
		return this.contributionsCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contributor that = (Contributor) o;
		return gitHubId.equals(that.gitHubId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gitHubId);
	}

	@Override
	public String toString() {
		return "Contributor{" +
				"gitHubId='" + gitHubId + '\'' +
				", contributionsCount=" + contributionsCount +
				'}';
	}
}
