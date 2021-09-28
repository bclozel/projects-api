package io.spring.sample.projects.contributors;

import java.net.URI;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {

	private final String gitHubId;

	private final String name;

	private final String company;

	private final String location;

	private final String twitter;

	private final URI profileUrl;

	@JsonCreator
	public Profile(@JsonProperty("login") String gitHubId, @JsonProperty("name") String name,
			@JsonProperty("company") String company, @JsonProperty("location") String location,
			@JsonProperty("twitter_username") String twitter, @JsonProperty("html_url") String profileUrl) {
		this.gitHubId = gitHubId;
		this.name = name;
		this.company = company;
		this.location = location;
		this.twitter = twitter;
		this.profileUrl = URI.create(profileUrl);
	}

	public String getGitHubId() {
		return this.gitHubId;
	}

	public String getName() {
		return this.name;
	}

	public String getCompany() {
		return this.company;
	}

	public String getLocation() {
		return this.location;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public URI getProfileUrl() {
		return this.profileUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profile profile = (Profile) o;
		return gitHubId.equals(profile.gitHubId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gitHubId);
	}

	@Override
	public String toString() {
		return "Profile{" +
				"gitHubId='" + gitHubId + '\'' +
				", name='" + name + '\'' +
				", company='" + company + '\'' +
				'}';
	}
}
