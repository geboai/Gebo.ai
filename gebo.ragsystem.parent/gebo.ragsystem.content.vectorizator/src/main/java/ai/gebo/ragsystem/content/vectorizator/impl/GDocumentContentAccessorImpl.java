/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GDocumentReferenceSnapshot;
import ai.gebo.model.GUserMessage;
import ai.gebo.ragsystem.content.vectorizator.DocumentAccessResult;
import ai.gebo.ragsystem.content.vectorizator.IGDocumentContentAccessor;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator.SendEvaluationPolicy;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;

/**
 * AI generated comments
 * 
 * Implementation of the document content accessor service that retrieves document
 * content from various sources and prepares it for vectorization.
 * This service handles document access, content retrieval, and error management.
 */
@Service
public class GDocumentContentAccessorImpl implements IGDocumentContentAccessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GDocumentContentAccessorImpl.class);
	
	/**
	 * Content evaluator used to stream document content
	 */
	@Autowired
	protected IGContentDispatchingEvaluator evaluator;
	
	/**
	 * Policy that determines how document content should be evaluated for sending
	 */
	protected SendEvaluationPolicy evaluationPolicy = SendEvaluationPolicy.TIMESTAMP_HASH_POLICY;
	
	/**
	 * Repository of handlers for different content management systems
	 */
	@Autowired
	protected IGContentManagementSystemHandlerRepositoryPattern handlerRepository;

	/**
	 * Default constructor
	 */
	public GDocumentContentAccessorImpl() {

	}

	/**
	 * Accesses document content based on a document reference envelope.
	 * This method retrieves document content from the appropriate content management system,
	 * processes it into message fragments, and prepares it for vectorization.
	 * 
	 * @param envelope A message envelope containing document reference information
	 * @return A DocumentAccessResult containing processed document fragments and status information
	 */
	@Override
	public DocumentAccessResult access(GMessageEnvelope<GDocumentReferencePayload> envelope) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin access(...)");
		}
		// Cache for content handlers
		Map handlerCache = new HashMap();
		// List to store processed document fragments
		final List<GMessageEnvelope<GDocumentMessageFragmentPayload>> ingested = new ArrayList<GMessageEnvelope<GDocumentMessageFragmentPayload>>();
		GDocumentReference docref = envelope.getPayload().getDocumentReference();
		GUserMessage errorMessage = null;
		
		// Find the appropriate handler for the source module
		IGContentManagementSystemHandler handler = handlerRepository.findImplementation(x -> {
			return x.getMessagingModuleId().equals(envelope.getSourceModule());
		});
		if (handler == null)
			throw new RuntimeException("Handler for module:" + envelope.getSourceModule() + " not found");
		
		boolean successfullStream = false;
		boolean emptyContent = true;
		Stream<GAbstractContentMessageFragmentPayload> stream;
		try {
			// Retrieve content stream from the document
			stream = evaluator.stream(evaluationPolicy, docref, handler, handlerCache);

			if (stream != null) {
				// Create a snapshot of the document reference for tracking
				final GDocumentReferenceSnapshot snapshot = new GDocumentReferenceSnapshot();
				snapshot.setCode(docref.getCode());
				snapshot.setModifiedDate(docref.getModificationDate());
				snapshot.setTokensCount(0);
				snapshot.setFileSize(docref.getFileSize());
				final List<String> hashes = new ArrayList<String>();
				
				// Process each content fragment
				stream.forEach(x -> {
					GDocumentMessageFragmentPayload payload = new GDocumentMessageFragmentPayload();
					payload.setDocumentReference(docref);
					payload.setEndPoint(envelope.getPayload().getEndPoint());
					payload.setKnowledgeBase(envelope.getPayload().getKnowledgeBase());
					payload.setProject(envelope.getPayload().getProject());
					if (x instanceof GDocumentMessageFragmentPayload) {
						GDocumentMessageFragmentPayload p = (GDocumentMessageFragmentPayload) x;
						payload.setDocuments(p.getDocuments());
						payload.setHash(p.getHash());
					}
					
					// Create a message envelope for each fragment
					GMessageEnvelope<GDocumentMessageFragmentPayload> message = new GMessageEnvelope<GDocumentMessageFragmentPayload>();
					message.setPayload(payload);
					message.setSourceModule(envelope.getSourceModule());
					message.setSourceComponent(envelope.getSourceComponent());
					message.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
					message.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_COMPONENT);
					message.getPayload().setJobId(envelope.getPayload().getJobId());
					ingested.add(message);
					
					// Update token count and hash information
					snapshot.setTokensCount(snapshot.getTokensCount() + message.getPayload().getEstimatedTokens());
					if (message.getPayload().getHash() != null) {
						hashes.add(message.getPayload().getHash());
					}

				});

			}
			emptyContent = ingested.isEmpty();
			successfullStream = true;
		} catch (Throwable e) {
			// Handle errors during document access
			String code = docref != null ? docref.getCode() : null;
			errorMessage = GUserMessage.errorMessage("Error accessing document:" + code, e);
			errorMessage.setId(UUID.randomUUID().toString());
			errorMessage.setJobId(envelope.getPayload().getJobId());
			LOGGER.error("Error accessing document " + code, e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End access(...)");
		}
		return new DocumentAccessResult(successfullStream, emptyContent, ingested, envelope, errorMessage);
	}
}