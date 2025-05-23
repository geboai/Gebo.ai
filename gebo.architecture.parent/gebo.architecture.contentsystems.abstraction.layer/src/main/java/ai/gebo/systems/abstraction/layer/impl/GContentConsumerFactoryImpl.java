/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DependencyTreeRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.knowledgebase.repositories.ProjectRepository;
import ai.gebo.knowledgebase.repositories.SoftwareArtifactsRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.model.base.GBaseVersionableObject;

/**
 * AI generated comments
 * Implementation of the IGContentConsumerFactory interface, responsible for creating content consumer instances.
 */
@Service
public class GContentConsumerFactoryImpl implements IGContentConsumerFactory {
	
	// Automatically injects the required dependencies using Spring's @Autowired annotation
	@Autowired
	IGPersistentObjectManager persistenceManager;
	@Autowired
	DocumentReferenceRepository documentRepository;
	@Autowired
	VirtualFolderRepository folderRepository;
	@Autowired
	SoftwareArtifactsRepository deliverableRepository;
	@Autowired
	DependencyTreeRepository dependencyTreeRepository;
	@Autowired
	ProjectRepository projectsRepo;
	@Autowired
	KnowledgeBaseRepository kRepo;

	/**
	 * Inner class implementing IGContentConsumer, which consumes different types of contents.
	 */
	public class ContextawareContentConsumer implements IGContentConsumer {
		// Variables to hold information about the current project and knowledge base
		GProjectEndpoint endpoint = null;
		GProject project = null;
		GKnowledgeBase knowledgeBase = null;

		/**
		 * Processes the provided GBaseVersionableObject according to its type and persists it using the appropriate repository.
		 * 
		 * @param t the content object to be consumed
		 */
		@Override
		public void accept(GBaseVersionableObject t) {
			if (t instanceof GDocumentReference) {
				GDocumentReference reference = (GDocumentReference) t;
				reference.setRootKnowledgebaseCode(knowledgeBase != null ? knowledgeBase.getCode() : null);
				documentRepository.save(reference);
			}
			if (t instanceof GVirtualFolder) {
				folderRepository.save((GVirtualFolder) t);
			}
			if (t instanceof GDependencyTree) {
				dependencyTreeRepository.save((GDependencyTree) t);
			} else if (t instanceof GSoftwareArtifact) {
				deliverableRepository.save((GSoftwareArtifact) t);
			}
		}

		/**
		 * Method to signal the end of the consuming process.
		 */
		@Override
		public void endConsuming() {
			// Implementation can be added here if needed for cleanup or finalization
		}

	}

	/**
	 * Constructs a GContentConsumerFactoryImpl instance.
	 */
	public GContentConsumerFactoryImpl() {

	}

	/**
	 * Creates a ContextawareContentConsumer instance based on the provided GProjectEndpoint.
	 * 
	 * @param endpoint the project endpoint used to determine associated project and knowledge base
	 * @return an instance of ContextawareContentConsumer
	 */
	@Override
	public IGContentConsumer create(GProjectEndpoint endpoint) {
		GProject project = null;
		GKnowledgeBase knowledgeBase = null;
		if (endpoint.getParentProjectCode() != null) {
			Optional<GProject> entry = projectsRepo.findById(endpoint.getParentProjectCode());
			if (entry.isPresent()) {
				project = entry.get();
			}
		}
		if (project != null && project.getRootKnowledgeBaseCode() != null) {
			Optional<GKnowledgeBase> entry = kRepo.findById(project.getRootKnowledgeBaseCode());
			if (entry.isPresent()) {
				knowledgeBase = entry.get();
			}
		}
		ContextawareContentConsumer consumer = new ContextawareContentConsumer();
		consumer.endpoint = endpoint;
		consumer.knowledgeBase = knowledgeBase;
		consumer.project = project;
		return consumer;
	}

}