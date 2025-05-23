/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Rest controller for admin log viewing operations.
 * Manages job status and user messages retrieval and deletion.
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/LogViewController")
public class LogViewController {

	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	@Autowired
	JobStatusRepository jobsRepository;

	@Autowired
	UserMessageRepository messagesRepository;

	/**
	 * Default constructor for LogViewController.
	 */
	public LogViewController() {

	}

	/**
	 * Filter for retrieving job entries based on class name and job type.
	 */
	public static class JobsEntriesForClassNameFilter {
		public String className = null; // The class name to filter job entries
		public JobType jobType = null; // The job type to filter job entries
		public DataPage page = new DataPage(); // Pagination details
	}

	/**
	 * Retrieves job entries based on the class name and job type.
	 *
	 * @param filter the filter containing class name, job type, and pagination details
	 * @return a page of job status items
	 */
	@PostMapping(value = "getJobsEntriesForClassName", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GJobStatusItem> getJobsEntriesForClassName(@RequestBody JobsEntriesForClassNameFilter filter) {
		Pageable pageable = filter.page != null ? filter.page.toPageable() : Pageable.ofSize(20);

		return jobsRepository.findByProjectEndpointReferenceClassNameAndJobType(filter.className, filter.jobType,
				pageable);
	}

	/**
	 * Filter for retrieving job entries based on project endpoint and job type.
	 */
	public static class JobsEntriesForProjectEndpointFilter {
		public GObjectRef<GProjectEndpoint> endpointRef = null; // Project endpoint reference
		public JobType jobType = null; // The job type to filter job entries
		public DataPage page = new DataPage(); // Pagination details
	}

	/**
	 * Retrieves job entries based on the project endpoint and job type.
	 *
	 * @param filter the filter containing project endpoint, job type, and pagination details
	 * @return a page of job status items
	 */
	@PostMapping(value = "getJobsEntriesForProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GJobStatusItem> getJobsEntriesForProjectEndpoint(
			@RequestBody JobsEntriesForProjectEndpointFilter filter) {
		Pageable pageable = filter.page != null ? filter.page.toPageable() : Pageable.ofSize(20);

		return jobsRepository.findByProjectEndpointReferenceAndJobType(filter.endpointRef, filter.jobType, pageable);
	}

	/**
	 * Filter for retrieving job entries based solely on job type.
	 */
	public static class JobsEntriesForJobType {
		public JobType jobType = null; // The job type to filter job entries
		public DataPage page = new DataPage(); // Pagination details
	}

	/**
	 * Retrieves job entries based on the job type.
	 *
	 * @param filter the filter containing job type and pagination details
	 * @return a page of job status items
	 */
	@PostMapping(value = "getJobsEntriesForJobType", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GJobStatusItem> getJobsEntriesForJobType(@RequestBody JobsEntriesForJobType filter) {
		Pageable pageable = filter.page != null ? filter.page.toPageable() : Pageable.ofSize(20);
		return jobsRepository.findByJobType(filter.jobType, pageable);
	}

	/**
	 * Deletes job statuses and corresponding messages by their IDs.
	 *
	 * @param id the list of job IDs to be deleted
	 */
	@PostMapping(value = "deleteJobStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteJobStatus(@RequestBody List<String> id) {
		jobsRepository.deleteAllById(id);
		messagesRepository.deleteByJobIdIn(id);
	}

	/**
	 * Parameters required to get job messages paged by job ID.
	 */
	public static class GetJobMessagesParam {
		@NotNull
		public String jobId = null; // The ID of the job
		@NotNull
		public DataPage dataPage = null; // Pagination details
	}

	/**
	 * Retrieves paginated user messages associated with a job ID.
	 *
	 * @param param the parameters containing job ID and pagination details
	 * @return a page of user messages
	 */
	@PostMapping(value = "getJobMessagesPaged", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Page<GUserMessage> getJobMessagesPaged(@Valid @RequestBody GetJobMessagesParam param){
		return messagesRepository.findByJobId(param.jobId, param.dataPage.toPageable());
	}
}