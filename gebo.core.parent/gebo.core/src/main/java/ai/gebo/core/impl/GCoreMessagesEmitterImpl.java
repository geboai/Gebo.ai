/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.impl;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.ComponentMetaInfo;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.security.services.IGSecurityService;

/**
 * AI generated comments
 * Implementation of the IGMessageEmitter interface for emitting messages related to 
 * project and knowledge base deletion operations in the core module.
 */
@Component
@Scope("singleton")
public class GCoreMessagesEmitterImpl implements IGMessageEmitter {
	@Autowired
	IGMessageBroker broker; // Broker for handling message acceptance and dispatch.
	@Autowired
	IGSecurityService securityService; // Security service for retrieving user information.
	@Autowired
	ProjectRepository projectsRepo; // Repository for accessing project data.

	/**
	 * Constructor for GCoreMessagesEmitterImpl. Initializes the component.
	 */
	public GCoreMessagesEmitterImpl() {

	}

	/**
	 * Retrieves the messaging module ID.
	 * 
	 * @return the module ID for the core module.
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.CORE_MODULE;
	}

	/**
	 * Retrieves the messaging system ID.
	 * 
	 * @return the system ID for the settings controller component.
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.SYSTEM_SETTINGS_CONTROLLER_COMPONENT;
	}

	/**
	 * Retrieves the component type.
	 * 
	 * @return the system component type as an application component.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Retrieves the list of payload types that this emitter can emit.
	 * 
	 * @return a list of class names of payloads.
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(GDeletedKnowledgeBasePayload.class.getName(), GDeletedProjectPayload.class.getName(),
				GDeletedProjectEndpointPayload.class.getName());
	}

	/**
	 * Sends payloads indicating the deletion of a project to various components.
	 * 
	 * @param payload the deleted project payload.
	 */
	public void sendDeletingPayload(GDeletedProjectPayload payload) {
		this.sendDeletingPayloadToResourceDisposeComponents(payload);
		this.sendDeletingPayloadToCoreMongoDocuments(payload);
		this.sendDeletingPayloadToVectorizator(payload);
	}

	/**
	 * Sends payloads indicating the deletion of a knowledge base to various components.
	 * Deletes associated projects as well.
	 * 
	 * @param payload the deleted knowledge base payload.
	 */
	public void sendDeletingPayload(GDeletedKnowledgeBasePayload payload) {
		Stream<GProject> stream = this.projectsRepo.findByRootKnowledgeBaseCode(payload.getKnowledgeBase().getCode());
		stream.forEach(x -> {
			GDeletedProjectPayload projectPayload = new GDeletedProjectPayload();
			projectPayload.setProject(x);
			this.sendDeletingPayloadToResourceDisposeComponents(projectPayload);
		});
		this.sendDeletingPayloadToCoreMongoDocuments(payload);
		this.sendDeletingPayloadToVectorizator(payload);
		projectsRepo.deleteByRootKnowledgeBaseCode(payload.getKnowledgeBase().getCode());
	}

	/**
	 * Sends a deletion payload to resource dispose components.
	 * 
	 * @param payload generic message payload to emit.
	 */
	protected void sendDeletingPayloadToResourceDisposeComponents(IGMessagePayloadType payload) {
		String user = securityService.getCurrentUser().getUsername(); // Get the current user's username.
		List<ComponentMetaInfo> systems = broker
				.getComponentsByMessagingSystemId(GStandardModulesConstraints.RESOURCES_DISPOSE_COMPONENT);
		for (ComponentMetaInfo componentMetaInfo : systems) {
			GMessageEnvelope msg = GMessageEnvelope.newMessageFrom(this, payload, user);
			msg.setTargetModule(componentMetaInfo.getMessagingModuleId());
			msg.setTargetComponent(componentMetaInfo.getMessagingSystemId());
			broker.accept(msg);
		}
	}

	/**
	 * Sends a deletion payload to core MongoDB documents components.
	 * 
	 * @param payload generic message payload to emit.
	 */
	protected void sendDeletingPayloadToCoreMongoDocuments(IGMessagePayloadType payload) {
		String user = securityService.getCurrentUser().getUsername(); // Retrieve current user's username.
		GMessageEnvelope msg = GMessageEnvelope.newMessageFrom(this, payload, user);
		msg.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
		msg.setTargetComponent(GStandardModulesConstraints.MONGO_DISPOSE_DOCUMENTS_COMPONENT);
		broker.accept(msg);
	}

	/**
	 * Sends a deletion payload to vectorizator components for further processing.
	 * 
	 * @param payload generic message payload to emit.
	 */
	protected void sendDeletingPayloadToVectorizator(IGMessagePayloadType payload) {
		String user = securityService.getCurrentUser().getUsername(); // Retrieve current user's username.
		GMessageEnvelope msg = GMessageEnvelope.newMessageFrom(this, payload, user);
		msg.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
		msg.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT);
		broker.accept(msg);
	}
}