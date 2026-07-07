/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * An abstract factory class for creating instances that are capable of disposing resources associated with a specific endpoint.
 * This class serves as a base for implementing specific resource disposal mechanisms.
 *
 * @param <EndpointType> The type of endpoint that this factory can create resource disposers for.
 */
public abstract class GAbstractResourcesDisposerFactory<EndpointType extends GProjectEndpoint>
		implements IGMessageReceiverFactory {
	
	// Logger instance for logging messages and errors.
	final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// Discovery service for locating persistent folders.
	IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer = null;
	
	// Handler for content management systems.
	IGContentManagementSystemHandler moduleHandler = null;
	
	// Repository for managing endpoint data within MongoDB.
	IGBaseMongoDBProjectEndpointRepository<EndpointType> endpointRepository = null;
	
	// Repository for tracking the status of jobs.
	JobStatusRepository jobStatusRepo = null;

	/**
	 * Constructor to initialize the GAbstractResourcesDisposerFactory with necessary services and repositories.
	 *
	 * @param persistenceFolderDiscoverer The service to discover local persistent folders.
	 * @param moduleHandler               The handler for content management systems.
	 * @param endpointRepository          The repository for accessing endpoint data.
	 * @param jobStatusRepo               The repository for job statuses.
	 */
	public GAbstractResourcesDisposerFactory(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			IGContentManagementSystemHandler moduleHandler,
			IGBaseMongoDBProjectEndpointRepository<EndpointType> endpointRepository, JobStatusRepository jobStatusRepo) {
		this.persistenceFolderDiscoverer = persistenceFolderDiscoverer;
		this.moduleHandler = moduleHandler;
		this.endpointRepository = endpointRepository;
		this.jobStatusRepo = jobStatusRepo;
	}

	/**
	 * Gets the type of system component managed by this factory.
	 *
	 * @return The system component type as an APPLICATION_COMPONENT.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Returns a list of payload types that are accepted by this factory.
	 *
	 * @return A list of accepted payload type class names.
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(GDeletedProjectEndpointPayload.class.getName(), GDeletedProjectPayload.class.getName());
	}

	/**
	 * Determines whether every payload type is accepted.
	 *
	 * @return false, as not all payload types are accepted.
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	/**
	 * Checks if resources associated with the given endpoint can be disposed.
	 * <p>
	 * The endpoint is received as a base {@link GProjectEndpoint}: for a deleted endpoint it is the
	 * shareable {@link GCentralizedProjectEndpoint} carried in the message (the concrete object no
	 * longer exists), while for a deleted project it is the concrete endpoint loaded from the
	 * repository.
	 *
	 * @param endpoint The endpoint to check.
	 * @return True if resources can be disposed, false otherwise.
	 */
	protected abstract boolean isCanBeDisposedResources(GProjectEndpoint endpoint);

	/**
	 * Disposes the resources associated with the given endpoint.
	 *
	 * @param endpoint                    The endpoint whose resources are to be disposed (centralized
	 *                                    view for a deleted endpoint, concrete instance otherwise).
	 * @param contentManagementSystemCode The originating content management system code, used to
	 *                                    resolve the system when the concrete endpoint is unavailable;
	 *                                    may be {@code null}.
	 */
	protected abstract void disposeResources(GProjectEndpoint endpoint, String contentManagementSystemCode);

	/**
	 * Deletes the file system associated with the given endpoint. The on-disk folder is resolved from
	 * the endpoint identity (real class name and code) so it matches the folder created for the
	 * concrete endpoint during ingestion, even when only the centralized view is available.
	 *
	 * @param endpoint                    The endpoint whose file system is to be deleted.
	 * @param contentManagementSystemCode The originating content management system code (may be null).
	 */
	protected final void disposeFileSystem(GProjectEndpoint endpoint, String contentManagementSystemCode) {
		try {
			String endpointClassName;
			String endpointCode;
			if (endpoint instanceof GCentralizedProjectEndpoint) {
				GObjectRef<GProjectEndpoint> ref = ((GCentralizedProjectEndpoint) endpoint).getRemoteProjectReference();
				endpointClassName = ref != null ? ref.getClassName() : endpoint.getClass().getName();
				endpointCode = ref != null ? ref.getCode() : endpoint.getCode();
			} else {
				endpointClassName = endpoint.getClass().getName();
				endpointCode = endpoint.getCode();
			}
			GContentManagementSystem system = resolveSystem(endpoint, contentManagementSystemCode);
			String filesystem2Delete = this.persistenceFolderDiscoverer.getLocalPersistentFolder(system,
					endpointClassName, endpointCode);
			LOGGER.info("Deleting filesystem folder:" + filesystem2Delete);
			File file = new File(filesystem2Delete);
			if (file.exists() && file.isDirectory()) {
				FileSystemUtils.deleteRecursively(file);
			}
		} catch (Throwable e) {
			LOGGER.error("Problem in disposing filesystem", e);
		}
	}

	/**
	 * Resolves the content management system for the endpoint being disposed. For a concrete endpoint
	 * the module handler resolves it directly; for a centralized (deleted) endpoint the system is
	 * looked up amongst the module configurations by the carried content management system code,
	 * falling back to the single configured system when no code is provided.
	 *
	 * @param endpoint                    The endpoint being disposed.
	 * @param contentManagementSystemCode The originating content management system code (may be null).
	 * @return The resolved content management system, or {@code null} if it cannot be determined.
	 */
	@SuppressWarnings("unchecked")
	private GContentManagementSystem resolveSystem(GProjectEndpoint endpoint, String contentManagementSystemCode) {
		try {
			if (!(endpoint instanceof GCentralizedProjectEndpoint)) {
				return moduleHandler.getSystem(endpoint);
			}
			List<GContentManagementSystem> configurations = moduleHandler.getConfigurations();
			if (configurations == null || configurations.isEmpty()) {
				return null;
			}
			if (contentManagementSystemCode != null) {
				for (GContentManagementSystem system : configurations) {
					if (contentManagementSystemCode.equals(system.getCode())) {
						return system;
					}
				}
			}
			return configurations.get(0);
		} catch (Throwable t) {
			LOGGER.warn("Unable to resolve content management system for disposal", t);
			return null;
		}
	}

	/**
	 * Creates an instance of IGMessageReceiver to handle incoming messages.
	 *
	 * @return A new instance of IGMessageReceiver.
	 */
	@Override
	public IGMessageReceiver create() {
		LOGGER.info("create() releasing a IGMessageReceiver instance");
		return new IGMessageReceiver() {

			/**
			 * Processes incoming messages containing endpoint payloads to dispose resources.
			 *
			 * @param msg The message to process.
			 */
			@Override
			public void accept(GMessageEnvelope msg) {
				LOGGER.info("Begin accept(...) message:" + msg.toString());
				if (msg.getPayload() instanceof GDeletedProjectEndpointPayload) {
					GDeletedProjectEndpointPayload payload = (GDeletedProjectEndpointPayload) msg.getPayload();
					// The concrete endpoint was already removed from persistence before this message was
					// sent, so we work from the shareable centralized view: its remote reference carries the
					// original (concrete) class name and code needed to purge jobs and on-disk resources.
					GCentralizedProjectEndpoint endpoint = payload.getEndpoint();
					GObjectRef<GProjectEndpoint> ref = endpoint.getRemoteProjectReference();
					jobStatusRepo.deleteByProjectEndpointReference(ref);
					if (isCanBeDisposedResources(endpoint)) {
						LOGGER.info("Disposing resources for:" + msg.toString());
						disposeResources(endpoint, payload.getContentManagementSystemCode());
					}
					msg.setDelivered(true);
					msg.setProcessed(true);

				} else if (msg.getPayload() instanceof GDeletedProjectPayload) {
					GDeletedProjectPayload payload = (GDeletedProjectPayload) msg.getPayload();
					List<EndpointType> endpoints = endpointRepository
							.findByParentProjectCode(payload.getProject().getCode());
					for (EndpointType endpoint : endpoints) {
						GObjectRef<GProjectEndpoint> ref = GObjectRef.of(endpoint);
						jobStatusRepo.deleteByProjectEndpointReference(ref);
						if (isCanBeDisposedResources(endpoint)) {
							LOGGER.info("Disposing resources for:" + msg.toString());
							disposeResources(endpoint, null);
						}
					}
					endpointRepository.deleteByParentProjectCode(payload.getProject().getCode());
					msg.setDelivered(true);
					msg.setProcessed(true);
				}
				LOGGER.info("End accept(...) message:" + msg.toString());
			}

			/**
			 * Returns the messaging system ID for the receiver.
			 *
			 * @return The messaging system ID.
			 */
			@Override
			public String getMessagingSystemId() {
				return GAbstractResourcesDisposerFactory.this.getMessagingSystemId();
			}

			/**
			 * Returns the type of system component associated with the receiver.
			 *
			 * @return The system component type.
			 */
			@Override
			public SystemComponentType getComponentType() {
				return GAbstractResourcesDisposerFactory.this.getComponentType();
			}

			/**
			 * Determines if every payload type is accepted by the receiver.
			 *
			 * @return false, as not all payload types are accepted.
			 */
			@Override
			public boolean isAcceptEveryPayloadType() {
				return false;
			}

			/**
			 * Returns a list of payload types accepted by the receiver.
			 *
			 * @return A list of accepted payload type class names.
			 */
			@Override
			public List<String> getAcceptedPayloadTypes() {
				return GAbstractResourcesDisposerFactory.this.getAcceptedPayloadTypes();
			}

			/**
			 * Returns the messaging module ID for the receiver.
			 *
			 * @return The messaging module ID.
			 */
			@Override
			public String getMessagingModuleId() {
				return GAbstractResourcesDisposerFactory.this.getMessagingModuleId();
			}
		};
	}

	/**
	 * Returns the pool cardinality, representing the maximum number of instances that can be created.
	 *
	 * @return The pool cardinality as an integer.
	 */
	@Override
	public int getPoolCardinality() {
		return 1;
	}

	/**
	 * Returns the messaging system ID associated with resources disposal.
	 *
	 * @return The messaging system ID for the disposal component.
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.RESOURCES_DISPOSE_COMPONENT;
	}

	/**
	 * Determines if the sender thread should be used for message handling.
	 *
	 * @return false, indicating that the sender thread is not used.
	 */
	@Override
	public boolean useSenderThread() {
		return false;
	}
}