package io.spring.sample.projects.metadata;

public enum ReleaseStatus {

	GENERAL_AVAILABILITY(".RELEASE"),
	MILESTONE("-M", "-RC"),
	SNAPSHOT("-SNAPSHOT", "BUILD-SNAPSHOT");

	private final String[] markers;

	ReleaseStatus(String... markers) {
		this.markers = markers;
	}

	public static ReleaseStatus fromVersion(Version version) {
		for (ReleaseStatus status : ReleaseStatus.values()) {
		    for (String marker : status.markers) {
				if (version.toString().contains(marker)) {
					return status;
				}
			}
		}
		return ReleaseStatus.GENERAL_AVAILABILITY;
	}

}
