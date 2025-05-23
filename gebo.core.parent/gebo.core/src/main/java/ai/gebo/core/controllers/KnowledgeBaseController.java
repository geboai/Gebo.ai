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
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.impl.GCoreMessagesEmitterImpl;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;

/**
 * Controller to manage knowledge bases in the application.
 * Provides endpoints to perform CRUD operations on knowledge bases.
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/KnowledgeBaseController")
public class KnowledgeBaseController {

    // Persistent Object Manager to handle database operations
	@Autowired
	private IGPersistentObjectManager persistenceManager;
	
	// Emitter to handle message sending operations
	@Autowired
	private GCoreMessagesEmitterImpl coreEmitter;
	
	// Repository for Knowledge Base operations
	@Autowired 
	private KnowledgeBaseRepository kbRepo;

	/**
	 * Default constructor for KnowledgeBaseController.
	 */
	public KnowledgeBaseController() {

	}

	/**
	 * Retrieves a list of root knowledge bases (those without a parent).
	 *
	 * @return a list of root GKnowledgeBase objects.
	 * @throws GeboPersistenceException if an error occurs during retrieval.
	 */
	@GetMapping(value = "getKnowledgeBases", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GKnowledgeBase> getKnowledgeBases() throws GeboPersistenceException {
		return kbRepo.findByParentKnowledgebaseCodeIsNull();
	}

	/**
	 * Retrieves child knowledge bases for a given parent code.
	 *
	 * @param code the code of the parent knowledge base.
	 * @return a list of child GKnowledgeBase objects.
	 */
	@GetMapping(value = "getChildKnowledgeBases", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GKnowledgeBase> getChildKnowledgeBases(@RequestParam("code") String code) {
		return kbRepo.findByParentKnowledgebaseCode(code);
	}
	
	/**
	 * Finds a knowledge base by its code.
	 *
	 * @param code the code of the knowledge base.
	 * @return the GKnowledgeBase object with the specified code.
	 * @throws GeboPersistenceException if an error occurs during retrieval.
	 */
	@GetMapping(value = "findKnowledgeBaseByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GKnowledgeBase findKnowledgeBaseByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistenceManager.findById(GKnowledgeBase.class, code);
	}

	/**
	 * Finds knowledge bases using a Query by Example (QBE) pattern.
	 *
	 * @param example an example GKnowledgeBase object for the query.
	 * @return a list of matching GKnowledgeBase objects.
	 * @throws GeboPersistenceException if an error occurs during retrieval.
	 */
	@PostMapping(value = "findKnowledgeBasesByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GKnowledgeBase> findKnowledgeBasesByQbe(@RequestBody GKnowledgeBase example)
			throws GeboPersistenceException {
		return persistenceManager.findByQbe(example);
	}

	/**
	 * Updates an existing knowledge base.
	 *
	 * @param entity the GKnowledgeBase object to update.
	 * @return the updated GKnowledgeBase object.
	 * @throws GeboPersistenceException if an error occurs during update.
	 */
	@PostMapping(value = "updateKnowledgeBase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GKnowledgeBase updateKnowledgeBase(@RequestBody GKnowledgeBase entity) throws GeboPersistenceException {
		// Set default ObjectSpaceType if not set
		if (entity.getObjectSpaceType() == null) {
			entity.setObjectSpaceType(ObjectSpaceType.COMPANY);
		}
		return persistenceManager.update(entity);
	}

	/**
	 * Inserts a new knowledge base.
	 *
	 * @param entity the GKnowledgeBase object to insert.
	 * @return the inserted GKnowledgeBase object.
	 * @throws GeboPersistenceException if an error occurs during insertion.
	 */
	@PostMapping(value = "insertKnowledgeBase", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GKnowledgeBase insertKnowledgeBase(@RequestBody GKnowledgeBase entity) throws GeboPersistenceException {
		// Set default ObjectSpaceType if not set
		if (entity.getObjectSpaceType() == null) {
			entity.setObjectSpaceType(ObjectSpaceType.COMPANY);
		}
		return persistenceManager.insert(entity);
	}

	/**
	 * Deletes a knowledge base.
	 *
	 * @param entity the GKnowledgeBase object to delete.
	 * @throws GeboPersistenceException if an error occurs during deletion.
	 */
	@PostMapping(value = "deleteKnowledgeBase", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteKnowledgeBase(@RequestBody GKnowledgeBase entity) throws GeboPersistenceException {
		persistenceManager.delete(entity);
		
		// Create payload for deleted knowledge base
		GDeletedKnowledgeBasePayload payload = new GDeletedKnowledgeBasePayload();
		payload.setKnowledgeBase(entity);
		coreEmitter.sendDeletingPayload(payload);
	}

}