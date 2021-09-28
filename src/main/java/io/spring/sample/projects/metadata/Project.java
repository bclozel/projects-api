package io.spring.sample.projects.metadata;

import java.net.URI;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
	private String slug;

	private String name;

	private URI repositoryUrl;

	@Enumerated(EnumType.STRING)
	private ProjectStatus status = ProjectStatus.ACTIVE;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Release> releases = new HashSet<>();

	public Project() {
	}

	public Project(String slug, String name) {
		this.slug = slug;
		this.name = name;
		this.repositoryUrl = URI.create("https://github.com/spring-projects/" + slug);
	}

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(URI repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public Set<Release> getReleases() {
		return this.releases;
	}

	public void setReleases(Set<Release> releases) {
		this.releases = releases;
	}

	public Release createRelease(Version version) {
		Release release = new Release(this, version);
		this.releases.add(release);
		return release;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Project project = (Project) o;
		return slug.equals(project.slug) && name.equals(project.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(slug, name);
	}
}
