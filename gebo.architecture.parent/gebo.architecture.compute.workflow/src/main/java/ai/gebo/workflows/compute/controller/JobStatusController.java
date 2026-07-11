package ai.gebo.workflows.compute.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.workflows.compute.model.JobSummary;
import ai.gebo.workflows.compute.service.IGeboWorkflowsStatsService;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/JobStatusController")
@AllArgsConstructor
public class JobStatusController {
	private final IGeboWorkflowsStatsService statusService;

	/**
	 * Retrieves the status of a job by its job code.
	 * 
	 * @param jobCode The unique identifier of the job
	 * @return The current status of the requested job
	 * @throws GeboPersistenceException If there's an error retrieving the job status
	 */
	@GetMapping(value = "getJobStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public GJobStatus getJobStatus(@RequestParam("jobCode") String jobCode) throws GeboPersistenceException {
		GJobStatus retValue = statusService.getStatus(jobCode);
		return retValue;
	}

	/**
	 * Retrieves a summary of a job including optional detailed statistics.
	 * 
	 * @param jobId        The unique identifier of the job
	 * @param statsDetails Flag indicating whether to include detailed statistics
	 * @return A summary of the job
	 * @throws GeboPersistenceException If there's an error accessing persistent
	 *                                  data
	 */
	@GetMapping(value = "getJobSummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public JobSummary getJobSummary(@RequestParam("jobCode") String jobId)
			throws GeboPersistenceException {
		return statusService.getJobSummary(jobId);
	}

}
