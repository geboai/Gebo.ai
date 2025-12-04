package ai.gebo.userspace.handler.model;

import ai.gebo.userspace.handler.dto.UserspaceFolderDto;
import jakarta.validation.constraints.NotNull;

/**
 * Class representing the publishing status of a folder
 */
public class PublishingStatus {
	public boolean underPubishingAlgorithm = false;
	public boolean hasBeenPublished = false;
	public String jobId = null;
	@NotNull
	public UserspaceFolderDto folder = null;
}