/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.config.GeboConfig;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus.JobType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatusItem;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcherRepositoryPattern;

/**
 * AI generated comments
 * 
 * GeboIngestionManager is responsible for managing the ingestion process of
 * content from various project endpoints. It handles the creation, tracking,
 * and execution of content extraction and vectorization jobs.
 */
@Service
class GeboIngestionManager {
	/** Logger for this class */
	static Logger LOGGER = LoggerFactory.getLogger(GeboIngestionManager.class);

	/** Configuration for Gebo system */
	@Autowired
	GeboConfig geboConfig;

	/** Repository for job status records */
	@Autowired
	JobStatusRepository jobStatusRepository;

	/** Repository for user messages */
	@Autowired
	UserMessageRepository messagesRepository;

	/** Dispatcher for IOC module contents using repository pattern */
	@Autowired
	IGIOCModuleContentsDispatcherRepositoryPattern iocRepositoryPattern;

	/** Manager for persistent objects */
	@Autowired
	IGPersistentObjectManager persistentObjectManager;

	/** Maximum number of messages to save in a single batch */
	public static final int BATCH_MESSAGES_SAVING = 1000;

	/** Maximum number of document reference IDs to search in a single batch */
	public static final int BATCH_DOCREFS_IDS_SEARCH = 1000;

	/** Map to track job status by endpoint key */
	static Map<String, GJobStatus> statusMap = new HashMap<String, GJobStatus>();

	/**
	 * Creates a new job status for content extraction and vectorization for a
	 * project endpoint
	 * 
	 * @param item The project endpoint for which to create the job
	 * @return A new GJobStatus instance representing the created job
	 * @throws GeboPersistenceException
	 */
	GJobStatus internalCreateContentsExtractionAndVectorizationStatus(GProjectEndpoint item, String workflowType,
			String workflowId) throws GeboPersistenceException {
		GJobStatus status = new GJobStatus();
		status.setCode(UUID.randomUUID().toString());
		status.setDescription("Job to complete reading and LLM embedding contents of " + item.getDescription() + " ["
				+ item.getCode() + "]");
		status.setProjectEndpointReference(GObjectRef.of(item));
		status.setProjectCode(item.getParentProjectCode());
		GProject project = persistentObjectManager.findById(GProject.class, item.getParentProjectCode());
		status.setKnowledgeBaseCode(project.getRootKnowledgeBaseCode());
		status.setStartDateTime(new Date());
		status.setWorkflowType(workflowType);
		status.setWorkflowId(workflowId);
		status.setError(false);
		status.setFinished(false);
		status.setProcessing(true);
		status.setJobType(JobType.CONTENTS_READING_VECTORIZING);
		jobStatusRepository.save(status);
		GUserMessage message = GUserMessage.infoMessage("Start processing data source:" + item.getDescription(),
				"Contained new or updated documents will be inserted in the vector store to be used in retrieve augmented generation functionalities of Gebo.ai");
		message.setJobId(status.getCode());
		messagesRepository.save(message);
		return status;
	}

	/**
	 * Executes the content reading and vectorization process for a given job status
	 * 
	 * @param status The job status to process
	 * @return The updated job status after processing
	 * @throws GeboJobServiceException If an error occurs during processing
	 */
	GJobStatus internalReadAndVectorizeContents(GJobStatus status) throws GeboJobServiceException {
		long ts = System.currentTimeMillis();
		LOGGER.info("Start read & vectorize contents "
				+ (status.getProjectEndpointReference() != null ? status.getProjectEndpointReference().toString()
						: ""));
		try {
			synchronized (statusMap) {
				statusMap.put(key(status.getProjectEndpointReference()), status);
			}
			GProjectEndpoint endpoint = persistentObjectManager.findByReference(status.getProjectEndpointReference(),
					GProjectEndpoint.class);
			IGIOCModuleContentsDispatcher handler = iocRepositoryPattern.findByProjectEndpoint(endpoint);
			if (handler == null)
				throw new GeboJobServiceException("Required IGIOCModuleContentsDispatcher not found");
			handler.dispatchContents(endpoint, status);
		} catch (GeboContentHandlerSystemException | GeboPersistenceException e) {
			throw new GeboJobServiceException(
					"exception streaming with endpoint=>" + status.getProjectEndpointReference(), e);
		} finally {
			synchronized (statusMap) {
				statusMap.remove(key(status.getProjectEndpointReference()));
			}

		}
		LOGGER.info(
				"End (in " + (System.currentTimeMillis() - ts) + " milliseconds) read & send to vectorization contents "
						+ (status.getProjectEndpointReference() != null
								? status.getProjectEndpointReference().toString()
								: ""));
		return status;
	}

	/**
	 * Checks if a job is currently running for the specified project endpoint
	 * 
	 * @param endpoint The project endpoint reference to check
	 * @return true if a job is running for the endpoint, false otherwise
	 */
	boolean isJobRunning(GObjectRef<GProjectEndpoint> endpoint) {
		if (geboConfig != null && geboConfig.getClustered() != null && geboConfig.getClustered()) {
			// For clustered environments, check the repository for active jobs
			List<GJobStatusItem> stream = jobStatusRepository
					.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndProcessingTrue(
							endpoint.getClassName(), endpoint.getCode());
			return !stream.isEmpty();
		} else
			// For non-clustered environments, check the in-memory status map
			return statusMap.containsKey(key(endpoint));
	}

	/**
	 * Generates a unique key for a project endpoint reference
	 * 
	 * @param endpoint The project endpoint reference
	 * @return A string key composed of className and code
	 */
	static private String key(GObjectRef<GProjectEndpoint> endpoint) {
		return endpoint.getClassName() + "-" + endpoint.getCode();
	}

	/**
	 * Creates a content extraction and vectorization job for a project endpoint
	 * reference
	 * 
	 * @param endpoint     The project endpoint reference
	 * @param workflowType
	 * @param workflowI
	 * @return The created job status
	 * @throws GeboPersistenceException If an error occurs while retrieving the
	 *                                  endpoint
	 */
	public GJobStatus internalCreateContentsExtractionAndVectorizationStatus(GObjectRef<GProjectEndpoint> endpoint,
			String workflowType, String workflowId) throws GeboPersistenceException {
		GProjectEndpoint ep = persistentObjectManager.findByReference(endpoint, GProjectEndpoint.class);
		return internalCreateContentsExtractionAndVectorizationStatus(ep, workflowType, workflowId);
	}

}