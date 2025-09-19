package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.model.JobSummary;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;

public class WorkflowCompletionTest extends AbstractBaseTestLLmsIntegrationTests {
	@Autowired
	ContentsBatchProcessedRepository repo;

	@Test
	public void testWorkflowCompletion() throws GeboPersistenceException, GeboJobServiceException {
		GJobStatus status = new GJobStatus();
		status.setDescription("Workdlow calculation test");
		status.setWorkflowType(GWorkflowType.STANDARD.name());
		status.setWorkflowId(GStandardWorkflow.INGESTION.name());
		status = persistentObjectManager.insert(status);
		JobSummary summary = this.ingestionJobService.getJobSummary(status.getCode());
		printSummary(summary);
		assertFalse(summary.getWorkflowStatus().isFinished(), "The status with no logged entries must be not finished");
		ContentsBatchProcessed processed = new ContentsBatchProcessed();
		processed.setJobId(status.getCode());
		processed.setWorkflowType(GWorkflowType.STANDARD.name());
		processed.setWorkflowId(GStandardWorkflow.INGESTION.name());
		processed.setWorkflowStepId(GStandardWorkflowStep.DOCUMENT_DISCOVERY.name());
		processed.setBatchDocumentsInput(1);
		processed.setBatchDocumentsProcessed(1);
		processed.setBatchSentToNextStep(1);
		processed.setTimestamp(new java.util.Date());
		processed.setId(UUID.randomUUID().toString());
		processed.setLastMessage(true);
		repo.insert(processed);
		summary = this.ingestionJobService.getJobSummary(status.getCode());
		printSummary(summary);
		assertFalse(summary.getWorkflowStatus().isFinished(), "The status with no logged entries must be not finished");
		processed = new ContentsBatchProcessed();
		processed.setJobId(status.getCode());
		processed.setWorkflowType(GWorkflowType.STANDARD.name());
		processed.setWorkflowId(GStandardWorkflow.INGESTION.name());
		processed.setWorkflowStepId(GStandardWorkflowStep.TOKENIZATION.name());
		processed.setBatchDocumentsInput(1);
		processed.setBatchDocumentsProcessed(1);
		processed.setBatchSentToNextStep(1);
		processed.setTimestamp(new java.util.Date());
		processed.setId(UUID.randomUUID().toString());
		repo.insert(processed);
		summary = this.ingestionJobService.getJobSummary(status.getCode());
		printSummary(summary);
		assertFalse(summary.getWorkflowStatus().isFinished(), "The status with no logged entries must be not finished");
		processed = new ContentsBatchProcessed();
		processed.setJobId(status.getCode());
		processed.setWorkflowType(GWorkflowType.STANDARD.name());
		processed.setWorkflowId(GStandardWorkflow.INGESTION.name());
		processed.setWorkflowStepId(GStandardWorkflowStep.EMBEDDING.name());
		processed.setBatchDocumentsInput(1);
		processed.setBatchDocumentsProcessed(1);
		processed.setBatchSentToNextStep(1);
		processed.setTimestamp(new java.util.Date());
		processed.setId(UUID.randomUUID().toString());
		repo.insert(processed);
		summary = this.ingestionJobService.getJobSummary(status.getCode());
		printSummary(summary);
		assertFalse(summary.getWorkflowStatus().isFinished(), "The status with no logged entries must be not finished");
		processed = new ContentsBatchProcessed();
		processed.setJobId(status.getCode());
		processed.setWorkflowType(GWorkflowType.STANDARD.name());
		processed.setWorkflowId(GStandardWorkflow.INGESTION.name());
		processed.setWorkflowStepId(GStandardWorkflowStep.GRAPHEXTRACTION.name());
		processed.setBatchDocumentsInput(1);
		processed.setBatchDocumentsProcessed(1);
		processed.setBatchSentToNextStep(1);
		processed.setTimestamp(new java.util.Date());
		processed.setId(UUID.randomUUID().toString());
		repo.insert(processed);
		summary = this.ingestionJobService.getJobSummary(status.getCode());
		printSummary(summary);
		assertTrue(summary.getWorkflowStatus().isFinished(), "The status with correct structure must be signed as finished");
	}

}
