package io.spring.sample.projects.metadata;

import java.net.URI;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Release {

	private static final String VERSION_PLACEHOLDER = "{version}";

	private static final String PROJECT_PLACEHOLDER = "{project}";

	private static final String API_URL_TEMPLATE = "https://docs.spring.io/{project}/docs/{version}/api/";

	private static final String REFDOCS_URL_TEMPLATE = "https://docs.spring.io/{project}/docs/{version}/reference/html/";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Version version;

	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

	@Enumerated(EnumType.STRING)
	private ReleaseStatus status;

	private URI referenceDocUrl;

	private URI apiDocUrl;

	private boolean current;


	public Release() {
	}

	public Release(Project project, Version version) {
		this.project = project;
		this.version = version;
		this.apiDocUrl = expandDocUri(API_URL_TEMPLATE, project, version);
		this.referenceDocUrl = expandDocUri(REFDOCS_URL_TEMPLATE, project, version);
		this.status = ReleaseStatus.fromVersion(version);
	}

	private URI expandDocUri(String uriTemplate, Project project, Version version) {
		return URI.create(uriTemplate.replaceAll(Pattern.quote(PROJECT_PLACEHOLDER), project.getSlug())
				.replaceAll(Pattern.quote(VERSION_PLACEHOLDER), version.toString()));
	}

	public Version getVersion() {
		return this.version;
	}

	void setVersion(Version version) {
		this.version = version;
	}

	public Project getProject() {
		return this.project;
	}

	void setProject(Project project) {
		this.project = project;
	}

	public ReleaseStatus getStatus() {
		return this.status;
	}

	public URI getReferenceDocUrl() {
		return referenceDocUrl;
	}

	public void setReferenceDocUrl(URI referenceDocUrl) {
		this.referenceDocUrl = referenceDocUrl;
	}

	public URI getApiDocUrl() {
		return apiDocUrl;
	}

	public void setApiDocUrl(URI apiDocUrl) {
		this.apiDocUrl = apiDocUrl;
	}

	public boolean isCurrent() {
		return this.current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public boolean isGeneralAvailability() {
		return this.status == ReleaseStatus.GENERAL_AVAILABILITY;
	}

	public boolean isMilestone() {
		return this.status == ReleaseStatus.MILESTONE;
	}

	public boolean isSnapshot() {
		return this.status == ReleaseStatus.SNAPSHOT;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Release release = (Release) o;
		return version.equals(release.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(version);
	}
}
