/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Implementation of the {@link IGEmbeddingMessageReceiver} interface that routes documents for embedding processing.
 * This service handles document ingestion and distributes document fragments to appropriate embedding models
 * based on knowledge base configurations.
 */
package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.ragsystem.content.vectorizator.DocumentAccessResult;
import ai.gebo.ragsystem.content.vectorizator.IGDocumentChunkServiceAccessor;
import ai.gebo.ragsystem.content.vectorizator.IGEmbeddingMessageReceiver;
import ai.gebo.ragsystem.content.vectorizator.IGEmbedder;

@Service
public class GEmbeddingMessageReceiverImpl implements IGEmbeddingMessageReceiver {
	/** Logger for this class */
	private static Logger LOGGER = LoggerFactory.getLogger(GEmbeddingMessageReceiverImpl.class);
	
	/** Repository for accessing knowledge base information */
	@Autowired
	protected KnowledgeBaseRepository kbaseRepo;
	
	/** Data access object for embedding model configurations */
	@Autowired
	protected IGEmbeddingModelRuntimeConfigurationDao embeddingConfigsDao;
	
	/** Service for accessing document content */
	@Autowired
	protected IGDocumentChunkServiceAccessor chunkedContentAccessor;
	
	/** Runtime binder for obtaining service implementations */
	@Autowired
	protected IGRuntimeBinder binder;

	/**
	 * Default constructor
	 */
	public GEmbeddingMessageReceiverImpl() {

	}

	/**
	 * Processes incoming messages for embedding generation.
	 * The method handles document references by ingesting them first, then routes
	 * document fragments to appropriate embedding models based on their knowledge base.
	 * 
	 * @param messages Envelope containing batch of messages to process
	 */
	@Override
	public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
		GMessagesBatchPayload allmsgpayload = messages.getPayload();
		boolean directIngestion = false;
		List<GMessageEnvelope<GDocumentReferencePayload>> documentReferencePayloads = new ArrayList<GMessageEnvelope<GDocumentReferencePayload>>();
		for (int i = 0; i < allmsgpayload.size(); i++) {
			GMessageEnvelope envelope = (GMessageEnvelope) allmsgpayload.get(i);
			if (envelope.getPayload() instanceof GDocumentReferencePayload) {
				documentReferencePayloads.add(envelope);
			}
		}
		List<DocumentAccessResult> impossibleToIngest = new ArrayList<DocumentAccessResult>();
		if (!documentReferencePayloads.isEmpty()) {
			allmsgpayload.removeAll(documentReferencePayloads);
			List<DocumentAccessResult> ingestedContents = readChunks(documentReferencePayloads);
			for (DocumentAccessResult ingested : ingestedContents) {
				if (ingested.isSuccessfullyHandled())
					allmsgpayload.addAll(ingested.getIngested());
				else {
					impossibleToIngest.add(ingested);
				}
			}
		}
		if (!impossibleToIngest.isEmpty()) {
			IGEmbedder tokenizatorAndEmbedder = null;
			tokenizatorAndEmbedder = binder.getImplementationOf(IGEmbedder.class);
			tokenizatorAndEmbedder.notifyFailingIngestion(impossibleToIngest);

		}
		// Group document fragments by knowledge base code
		HashMap<String, List<GMessageEnvelope<GDocumentMessageFragmentPayload>>> byknowledgebaseCode = new HashMap<String, List<GMessageEnvelope<GDocumentMessageFragmentPayload>>>();
		HashMap<String, List<IGConfigurableEmbeddingModel>> embeddingsByKnowledgebase = new HashMap<String, List<IGConfigurableEmbeddingModel>>();
		for (int i = 0; i < allmsgpayload.size(); i++) {
			GMessageEnvelope<GDocumentMessageFragmentPayload> msg = (GMessageEnvelope<GDocumentMessageFragmentPayload>) allmsgpayload
					.get(i);
			GDocumentMessageFragmentPayload payload = msg.getPayload();
			String knowledgeBase = payload.getDocumentReference().getRootKnowledgebaseCode();
			if (knowledgeBase == null) {
				LOGGER.error(
						"No knowledge base code for document:" + msg.getPayload().getDocumentReference().getCode());
				continue;
			}
			if (!byknowledgebaseCode.containsKey(knowledgeBase)) {
				byknowledgebaseCode.put(knowledgeBase,
						new ArrayList<GMessageEnvelope<GDocumentMessageFragmentPayload>>());
			}
			byknowledgebaseCode.get(knowledgeBase).add(msg);
		}
		// Retrieve knowledge bases and their embedding models
		List<GKnowledgeBase> kbases = kbaseRepo.findAllById(byknowledgebaseCode.keySet());
		for (GKnowledgeBase kbase : kbases) {
			List<GObjectRef> embeddings = kbase.getEmbeddingModelReferences();
			List<IGConfigurableEmbeddingModel> embeddingModels = new ArrayList<IGConfigurableEmbeddingModel>();
			if (embeddings != null && !embeddings.isEmpty()) {
				List<IGConfigurableEmbeddingModel> filteredList = embeddingConfigsDao.findListByPredicate(x -> {
					GObjectRef<GBaseEmbeddingModelConfig> ref = GObjectRef
							.of((GBaseEmbeddingModelConfig) x.getConfig());
					return embeddings.stream().anyMatch(y -> {
						return ref.getClassName().equals(y.getClassName()) && ref.getCode().equals(y.getCode());
					});
				});
				embeddingModels.addAll(filteredList);
			} else {
				// Use default embedding model if none specified
				IGConfigurableEmbeddingModel defaultEmbedding = embeddingConfigsDao.defaultHandler();
				embeddingModels.add(defaultEmbedding);
			}
			embeddingsByKnowledgebase.put(kbase.getCode(), embeddingModels);
		}
		// Process document fragments with appropriate embedding models
		IGEmbedder tokenizatorAndEmbedder = null;
		for (GKnowledgeBase kbase : kbases) {
			List<IGConfigurableEmbeddingModel> embeddingModels = embeddingsByKnowledgebase.get(kbase.getCode());
			List<GMessageEnvelope<GDocumentMessageFragmentPayload>> messagesList = byknowledgebaseCode
					.get(kbase.getCode());
			if (embeddingModels != null && messagesList != null && !embeddingModels.isEmpty()
					&& !messagesList.isEmpty()) {
				if (tokenizatorAndEmbedder == null) {
					tokenizatorAndEmbedder = binder.getImplementationOf(IGEmbedder.class);
				}
				tokenizatorAndEmbedder.embed(embeddingModels, messagesList);
			}
		}
	}

	/**
	 * Processes document reference payloads by accessing their content.
	 * 
	 * @param documentReferencePayloads List of document reference envelopes to be ingested
	 * @return List of document access results
	 */
	private List<DocumentAccessResult> readChunks(
			List<GMessageEnvelope<GDocumentReferencePayload>> documentReferencePayloads) {

		List<DocumentAccessResult> ingested = new ArrayList<DocumentAccessResult>();
		for (GMessageEnvelope<GDocumentReferencePayload> envelope : documentReferencePayloads) {
			DocumentAccessResult ingest = chunkedContentAccessor.access(envelope);
			ingested.add(ingest);
		}
		return ingested;
	}

}