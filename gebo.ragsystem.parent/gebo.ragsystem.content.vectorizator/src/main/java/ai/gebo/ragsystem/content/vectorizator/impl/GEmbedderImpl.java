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
 * 
 * Implementation of the IGTokenizatorAndEmbedder interface that handles document tokenization
 * and embedding into vector stores. This service processes documents, tokenizes them, 
 * and embeds them using configured embedding models.
 */
package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.core.messages.GContentEmbeddingHandshakePayload;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GUserMessagePayload;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent.GVectorizedContentId;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.ragsystem.content.vectorizator.DocumentAccessResult;
import ai.gebo.ragsystem.content.vectorizator.IGEmbedder;

@Service
public class GEmbedderImpl implements IGEmbedder {
	private static final Logger LOGGER = LoggerFactory.getLogger(GEmbedderImpl.class);
	@Autowired
	IGRuntimeBinder runtimeBinder;

	@Autowired
	VectorizedContentRepository vectorizedContentRepository;

	/**
	 * Default constructor for GTokenizatorAndEmbedderImpl.
	 */
	public GEmbedderImpl() {

	}

	/**
	 * Tokenizes and embeds document fragments into vector stores using the provided
	 * embedding models. The method processes each document, breaks it into tokens,
	 * embeds them, and saves the results to the vector stores. It also tracks
	 * vectorization progress and sends status updates.
	 *
	 * @param embeddingModels The list of embedding models to use for vectorization
	 * @param messagesList    The list of document messages to process
	 */
	@Override
	public void embed(List<IGConfigurableEmbeddingModel> embeddingModels,
			List<GMessageEnvelope<GDocumentMessageFragmentPayload>> messagesList) {
		long startTimestamp = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin tokenizeAndEmbed(....) ndocs:" + messagesList.size());
		}
		boolean sendHandshake = true;
		final List<String> ids = new ArrayList<String>();
		List<Integer> toBeRemoved = new ArrayList<Integer>();
		int idx = 0;
		final Map<String, List<GVectorizedContent>> vectorizedMap = new HashMap<String, List<GVectorizedContent>>();
		final Map<String, List<String>> vectorsToDeleteForVectorStoreId = new HashMap<String, List<String>>();
		final Map<String, Map<String, Boolean>> distinctJobDocumentVectorized = new HashMap<String, Map<String, Boolean>>();
		final HashMap<String, GContentsProcessingStatusUpdatePayload> vectorizationStatistics = new HashMap<String, GContentsProcessingStatusUpdatePayload>();

		// Collect document IDs and prepare lists for deleted documents
		for (GMessageEnvelope<GDocumentMessageFragmentPayload> x : messagesList) {
			ids.add(x.getPayload().getDocumentReference().getCode());

			Boolean deleted = x.getPayload().getDocumentReference().getDeleted();
			if (deleted != null && deleted) {
				toBeRemoved.add(idx);
			} else {
				// Count vectorization batch nr. of documents and completed
				String key = getProcessingKey(x);

				if (!vectorizationStatistics.containsKey(key)) {
					GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
					payload.setJobId(x.getPayload().getJobId());
					payload.setWorkflowType(x.getWorkflowType() != null ? x.getWorkflowType().name() : null);
					payload.setWorkflowId(x.getWorkflowId());
					payload.setWorkflowStepId(x.getWorkflowStepId());
					vectorizationStatistics.put(key, payload);
				}
				GContentsProcessingStatusUpdatePayload ctr = vectorizationStatistics.get(key);
				ctr.setBatchDocumentsInput(ctr.getBatchDocumentsInput() + 1);
			}
			idx++;
		}

		// Retrieve existing vectorized content for the documents
		Stream<GVectorizedContent> vectorized = vectorizedContentRepository.findByIdDocReferenceCodeIn(ids);

		// Organize existing vectorized content by document code
		vectorized.forEach(x -> {
			if (!vectorizedMap.containsKey(x.getId().getDocReferenceCode())) {
				vectorizedMap.put(x.getId().getDocReferenceCode(), new ArrayList<GVectorizedContent>());
			}
			vectorizedMap.get(x.getId().getDocReferenceCode()).add(x);
			if (!vectorsToDeleteForVectorStoreId.containsKey(x.getId().getVectorStoreId())) {
				vectorsToDeleteForVectorStoreId.put(x.getId().getVectorStoreId(), new ArrayList<String>());
			}
			vectorsToDeleteForVectorStoreId.get(x.getId().getVectorStoreId()).addAll(x.getVectorsId());
			x.setVectorsId(new ArrayList<String>());

		});

		// Delete existing vectors from vector stores
		for (IGConfigurableEmbeddingModel embeddingModel : embeddingModels) {
			List<String> ids2delete = vectorsToDeleteForVectorStoreId.get(embeddingModel.getCode());
			if (ids2delete != null && !ids2delete.isEmpty()) {
				embeddingModel.getVectorStore().delete(ids2delete);
			}
		}

		List<GVectorizedContent> vectorizedToDelete = new ArrayList<GVectorizedContent>();
		// Handle deletions of vectorized content
		for (Integer index : toBeRemoved) {
			GMessageEnvelope<GDocumentMessageFragmentPayload> x = messagesList.get(index);
			String code = x.getPayload().getDocumentReference().getCode();
			List<GVectorizedContent> vectorizedList = vectorizedMap.remove(code);
			if (vectorizedList != null && !vectorizedList.isEmpty()) {
				vectorizedToDelete.addAll(vectorizedList);
			}
		}

		// Remove deleted documents from processing list
		if (!toBeRemoved.isEmpty()) {
			List<GMessageEnvelope<GDocumentMessageFragmentPayload>> onlyValidEntries = new ArrayList<GMessageEnvelope<GDocumentMessageFragmentPayload>>();
			int index = 0;
			for (GMessageEnvelope<GDocumentMessageFragmentPayload> ml : messagesList) {
				if (!toBeRemoved.contains(Integer.valueOf(index))) {
					onlyValidEntries.add(ml);
				}
				index++;
			}
			messagesList = onlyValidEntries;
		}

		// Group embedding models by token threshold
		TreeMap<Integer, List<IGConfigurableEmbeddingModel>> groupByEmbedSize = new TreeMap<Integer, List<IGConfigurableEmbeddingModel>>();
		List<String> validEmbedders = new ArrayList<String>();
		for (IGConfigurableEmbeddingModel embedder : embeddingModels) {
			if (!groupByEmbedSize.containsKey(embedder.getTokenizationThreshold())) {
				groupByEmbedSize.put(embedder.getTokenizationThreshold(),
						new ArrayList<IGConfigurableEmbeddingModel>());
			}
			groupByEmbedSize.get(embedder.getTokenizationThreshold()).add(embedder);
			validEmbedders.add(embedder.getCode());
		}

		Date now = new Date();
		List<GUserMessage> allUserMessages = new ArrayList<GUserMessage>();

		// Process documents for each token threshold group
		for (Map.Entry<Integer, List<IGConfigurableEmbeddingModel>> entry : groupByEmbedSize.entrySet()) {
			List<GUserMessage> userMessages = new ArrayList<GUserMessage>();
			List<Document> tokenizeddocuments = new ArrayList<Document>();
			List<String> theseEmbeddingModels = entry.getValue().stream().map(x -> {
				return x.getCode();
			}).toList();
			HashMap<String, List<String>> newIdsPerDocCode = new HashMap<String, List<String>>();

			// Tokenize documents and prepare for embedding
			for (GMessageEnvelope<GDocumentMessageFragmentPayload> x : messagesList) {
				GDocumentMessageFragmentPayload payload = x.getPayload();

				List<Document> enriched = payload.getDocuments();
				newIdsPerDocCode.put(x.getPayload().getDocumentReference().getCode(), enriched.stream().map(y -> {
					return y.getId();
				}).toList());
				tokenizeddocuments.addAll(enriched);

				// Create success message for this document
				String messageSummary = "Document: " + payload.getDocumentReference().getName()
						+ " embedded successfully";
				String messageDetail = "Embedding document " + payload.getDocumentReference().getCode()
						+ " with nr of tokenized fragments: " + enriched.size() + " in embedding models vector stores "
						+ theseEmbeddingModels;
				String key = getProcessingKey(x);
				// Update vectorization job statistics

				GContentsProcessingStatusUpdatePayload accountingEntry = vectorizationStatistics.get(key);
				if (accountingEntry != null) {
					accountingEntry.setProcessedChunks(accountingEntry.getProcessedChunks() + enriched.size());
					long tokensCount = 0l;
					for (Document doc : enriched) {
						if (doc.getMetadata() != null
								&& doc.getMetadata().containsKey(DocumentMetaInfos.GEBO_TOKEN_LENGTH)) {
							Object tokens = doc.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH);
							if (tokens instanceof Number) {
								tokensCount += ((Number) tokens).longValue();
							}
						}
					}
					accountingEntry.setProcessedTokens(accountingEntry.getProcessedTokens() + tokensCount);

				}

				GUserMessage message = GUserMessage.successMessage(messageSummary, messageDetail);
				message.setJobId(x.getPayload().getJobId());
				userMessages.add(message);
			}

			try {
				// Process each embedding model for the current token threshold group
				List<IGConfigurableEmbeddingModel> embedders = entry.getValue();
				for (IGConfigurableEmbeddingModel embedder : embedders) {
					String vectorStoreId = embedder.getCode();
					long reqTime = 0l;
					if (LOGGER.isDebugEnabled()) {
						reqTime = System.currentTimeMillis();
						LOGGER.debug("Begin embed & save to vector store:" + vectorStoreId);
					}

					// Add tokenized documents to vector store
					if (!tokenizeddocuments.isEmpty()) {
						embedder.getVectorStore().add(tokenizeddocuments);
					}

					if (LOGGER.isDebugEnabled()) {
						long difference = System.currentTimeMillis() - reqTime;
						LOGGER.debug(
								"End  embed & save to vector store:" + vectorStoreId + " time-msec=>" + difference);
					}

					// Update vectorized content records
					List<String> vectorsToBeDeleted = new ArrayList<String>();
					for (GMessageEnvelope<GDocumentMessageFragmentPayload> x : messagesList) {
						String code = x.getPayload().getDocumentReference().getCode();
						if (!distinctJobDocumentVectorized.containsKey(x.getPayload().getJobId())) {
							distinctJobDocumentVectorized.put(x.getPayload().getJobId(),
									new HashMap<String, Boolean>());
						}
						distinctJobDocumentVectorized.get(x.getPayload().getJobId()).put(code, true);
						GDocumentMessageFragmentPayload payload = x.getPayload();
						List<String> newIds = newIdsPerDocCode.get(code);
						if (newIds == null)
							newIds = new ArrayList<String>();
						List<GVectorizedContent> vectorizedList = vectorizedMap.get(code);
						if (vectorizedList == null) {
							vectorizedMap.put(code, vectorizedList = new ArrayList<GVectorizedContent>());
						}

						// Find or create vectorized content entry
						Optional<GVectorizedContent> foundEntry = vectorizedList.stream().filter(y -> {
							return y.getId().getDocReferenceCode().equals(code)
									&& y.getId().getVectorStoreId().equals(vectorStoreId);
						}).findFirst();

						if (foundEntry.isPresent()) {
							// Update existing vectorized content
							GVectorizedContent vect = foundEntry.get();
							vect.setDeleted(false);
							vect.setHash(payload.getHash());
							vect.setFileSize(payload.getDocumentReference().getFileSize());
							vect.setModificationDate(payload.getDocumentReference().getModificationDate());
							vect.setParentProjectCode(payload.getDocumentReference().getParentProjectCode());
							vect.setRootKnowledgebaseCode(payload.getDocumentReference().getRootKnowledgebaseCode());
							if (vect.getVectorsId() != null) {
								vectorsToBeDeleted.addAll(vect.getVectorsId());
							}
							vect.setVectorsId(newIds);
							vect.setLastVectorizedDate(now);
						} else {
							// Create new vectorized content entry
							GVectorizedContent vect = new GVectorizedContent();
							vect.setId(new GVectorizedContentId());
							vect.getId().setDocReferenceCode(payload.getDocumentReference().getCode());
							vect.getId().setVectorStoreId(vectorStoreId);
							vect.setDeleted(false);
							vect.setHash(payload.getHash());
							vect.setFileSize(payload.getDocumentReference().getFileSize());
							vect.setModificationDate(payload.getDocumentReference().getModificationDate());
							vect.setParentProjectCode(payload.getDocumentReference().getParentProjectCode());
							vect.setRootKnowledgebaseCode(payload.getDocumentReference().getRootKnowledgebaseCode());
							vect.setProjectEndpointReference(
									payload.getDocumentReference().getProjectEndpointReference());
							vect.setVectorsId(newIds);
							vect.setLastVectorizedDate(now);
							vectorizedList.add(vect);
						}
					}

					// Delete old vectors if needed
					if (!vectorsToBeDeleted.isEmpty()) {
						try {
							embedder.getVectorStore().delete(vectorsToBeDeleted);
						} catch (Throwable th) {
							LOGGER.error("Error deleting vectors from VectorStore", th);
						}
					}
				}
				allUserMessages.addAll(userMessages);
			} catch (Throwable th) {
				// Handle embedding errors
				LOGGER.error("Error in saving to VectorStore", th);
				sendHandshake = false;

			}
		}

		// Prepare vectorized content records for saving
		List<GVectorizedContent> vectorizedList = new ArrayList<GVectorizedContent>();
		for (List<GVectorizedContent> v : vectorizedMap.values()) {
			for (GVectorizedContent vc : v) {
				if (!validEmbedders.contains(vc.getId().getVectorStoreId())) {
					vectorizedToDelete.add(vc);
				} else {
					vectorizedList.add(vc);
				}
			}
		}

		// Delete and save vectorized content records
		if (!vectorizedToDelete.isEmpty()) {
			vectorizedContentRepository.deleteAll(vectorizedToDelete);
		}
		if (!vectorizedList.isEmpty()) {
			vectorizedContentRepository.saveAll(vectorizedList);
		}

		// Send vectorization status updates
		GContentVectorizationEmitterComponent emitter = runtimeBinder
				.getImplementationOf(GContentVectorizationEmitterComponent.class);
		for (GContentsProcessingStatusUpdatePayload vectPayload : vectorizationStatistics.values()) {
			Map<String, Boolean> map = distinctJobDocumentVectorized.get(vectPayload.getJobId());
			vectPayload.setBatchDocumentsProcessed((long) (map != null ? map.size() : 0));
			GMessageEnvelope<GContentsProcessingStatusUpdatePayload> msg = GMessageEnvelope.newMessageFrom(emitter,
					vectPayload);
			msg.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			msg.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			msg.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
			emitter.send(msg);
		}

		// Send embedding handshakes if required
		if (sendHandshake) {
			List<GMessageEnvelope<GContentEmbeddingHandshakePayload>> handshakes = new ArrayList<GMessageEnvelope<GContentEmbeddingHandshakePayload>>();
			for (GMessageEnvelope<GDocumentMessageFragmentPayload> x : messagesList) {
				GDocumentMessageFragmentPayload payload = x.getPayload();
				if (payload.getRequiresEmbeddingHandshake() != null && payload.getRequiresEmbeddingHandshake()) {

					GContentEmbeddingHandshakePayload handshake = GContentEmbeddingHandshakePayload.ack(payload);
					GMessageEnvelope<GContentEmbeddingHandshakePayload> handshakeMsg = GMessageEnvelope
							.newMessageFrom(emitter, handshake);
					handshakeMsg.setTargetModule(x.getSourceModule());
					handshakeMsg.setTargetComponent(x.getSourceComponent());
					handshakeMsg.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					handshakes.add(handshakeMsg);
				}
			}
			for (GMessageEnvelope<GContentEmbeddingHandshakePayload> gmsg : handshakes) {
				emitter.send(gmsg);
			}
		}

		// Send user messages
		for (GUserMessage userMessage : allUserMessages) {
			GUserMessagePayload payload = new GUserMessagePayload();
			payload.setUserMessage(userMessage);
			GMessageEnvelope<GUserMessagePayload> userMessageMsg = GMessageEnvelope.newMessageFrom(emitter, payload);
			userMessageMsg.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			userMessageMsg.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			userMessageMsg.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
			emitter.send(userMessageMsg);
		}

		long endTimestamp = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End tokenizeAndEmbed(....) embedded in ms:" + (endTimestamp - startTimestamp));
		}
	}

	/**
	 * Notifies the system about documents that failed during the ingestion process.
	 * This method creates error messages and updates vectorization status for
	 * failed documents.
	 *
	 * @param impossibleToIngest List of document access results that could not be
	 *                           processed
	 */
	@Override
	public void notifyFailingIngestion(List<DocumentAccessResult> impossibleToIngest) {
		GContentVectorizationEmitterComponent emitter = runtimeBinder
				.getImplementationOf(GContentVectorizationEmitterComponent.class);

		Map<String, GContentsProcessingStatusUpdatePayload> payloads = new HashMap<String, GContentsProcessingStatusUpdatePayload>();

		// Process each failed document
		for (DocumentAccessResult documentAccessResult : impossibleToIngest) {
			if (!documentAccessResult.isSuccessfullyHandled()) {
				final String key = getProcessingKey(documentAccessResult.getDocReferenceMessage());
				if (!payloads.containsKey(key)) {
					GContentsProcessingStatusUpdatePayload errorInstance = new GContentsProcessingStatusUpdatePayload();
					errorInstance.setJobId(documentAccessResult.getDocReferenceMessage().getPayload().getJobId());
					errorInstance
							.setWorkflowType(documentAccessResult.getDocReferenceMessage().getWorkflowType() != null
									? documentAccessResult.getDocReferenceMessage().getWorkflowType().name()
									: null);
					errorInstance.setWorkflowId(documentAccessResult.getDocReferenceMessage().getWorkflowId());
					errorInstance.setWorkflowStepId(documentAccessResult.getDocReferenceMessage().getWorkflowStepId());
					payloads.put(key, errorInstance);

				}
				payloads.get(key)
						.setBatchDocumentsProcessingErrors((payloads.get(key).getBatchDocumentsProcessingErrors() + 1));

				// Send user message about the error if available
				if (documentAccessResult.getNegativeMessage() != null) {
					GUserMessagePayload payload = new GUserMessagePayload();
					payload.setUserMessage(documentAccessResult.getNegativeMessage());
					GMessageEnvelope<GUserMessagePayload> userMessageMsg = GMessageEnvelope.newMessageFrom(emitter,
							payload);
					payload.getUserMessage()
							.setJobId(documentAccessResult.getDocReferenceMessage().getPayload().getJobId());
					userMessageMsg.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
					userMessageMsg.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
					userMessageMsg.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					emitter.send(userMessageMsg);
				}
			}
		}

		// Send vectorization status updates for each job with errors
		for (GContentsProcessingStatusUpdatePayload vectPayload : payloads.values()) {
			GMessageEnvelope<GContentsProcessingStatusUpdatePayload> msg = GMessageEnvelope.newMessageFrom(emitter,
					vectPayload);
			msg.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			msg.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			msg.setTargetType(SystemComponentType.APPLICATION_COMPONENT);			
			emitter.send(msg);
		}

	}

	private String getProcessingKey(GMessageEnvelope<? extends GAbstractContentMessageFragmentPayload> data) {

		return data.getPayload().getJobId() + "-" + data.getWorkflowType() + "-" + data.getWorkflowId() + "-"
				+ data.getWorkflowStepId();

	}

}