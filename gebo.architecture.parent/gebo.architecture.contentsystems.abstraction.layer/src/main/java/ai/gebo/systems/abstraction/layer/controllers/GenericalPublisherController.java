package ai.gebo.systems.abstraction.layer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.ai.service.IGPromptUseInfoDao;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.OperationStatus;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GenericalPublisherController")
@AllArgsConstructor
public class GenericalPublisherController {
	private final static Logger LOGGER = LoggerFactory.getLogger(GenericalPublisherController.class);
	private final IGGeboIngestionJobQueueService jobQueueService;
	private final IGPersistentObjectManager persistenceObjectManager;

	@PostMapping(value = "publishCentralizedEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJobStatus> publishCentralizedEndpoint(
			@RequestBody @Valid GCentralizedProjectEndpoint projectEndpointReference) {
		try {
			GProjectEndpoint original = persistenceObjectManager
					.findByReference(projectEndpointReference.getRemoteProjectReference(), GProjectEndpoint.class);
			if (original == null) {
				return OperationStatus.ofError("Data source unknown",
						"The data source you want to publish cannot be reach");
			}
			GJobStatus job = jobQueueService.createNewAsyncJob(original, new NoContentConsumingSessionParam(),
					GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name());
			return OperationStatus.of(job);
		} catch (Throwable exc) {
			LOGGER.error("Error publishing", exc);
			return OperationStatus.of(exc);
		}
	}

}
