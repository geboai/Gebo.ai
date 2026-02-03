package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.impl.AIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesBuilder;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatRequestResourcesBuilderImpl implements IGChatRequestResourcesBuilder {

	private final GeboChatConfigs geboChatConfigs;
	private final ShrinkedChatSessionStateRepository shrinkedSessionStateRepository;
	private final IGChatSessionStateService chatSessionStateService;
	private final IGChatStorageAreaService chatStorageAreaService;
	private final DocumentReferenceRepository documentsRepository;
	private final AIDocumentsCacheService documentsCacheService;
	private final IGChatSessionStateShrinkerService sessionShrinkerService;

	@Override
	public LLMChatRequestResources buildRequestResources(GeboChatRequest lastRequest, GUserChatContext actualContext,
			int tokensBudget) throws IOException, GeboPersistenceException, GeboContentHandlerSystemException,
			GeboIngestionException, LLMConfigException {
		// user asked resources to be included in the chat

		// if i find the session state already shrinked i use it
		Optional<ShrinkedChatSessionState> shrinkedOptional = shrinkedSessionStateRepository
				.findById(actualContext.getCode());
		if (shrinkedOptional.isPresent()) {
			return buildFromShrinked(shrinkedOptional.get(), lastRequest, actualContext, tokensBudget);
		} else {
			ChatFullSessionState fullState = this.chatSessionStateService.extractState(lastRequest, actualContext);
			// here add fullstate + actual request + chatWithDocsContents
			if (fullState.getTokensSize() <= tokensBudget) {
				return fullState.toChatRequestResources();
			} else {
				ShrinkedChatSessionState shrinked = sessionShrinkerService.shrink(fullState, tokensBudget);
				shrinkedSessionStateRepository.save(shrinked);
				return buildFromShrinked(shrinked, lastRequest, actualContext, tokensBudget);
			}
		}

	}

	private LLMChatRequestResources buildFromShrinked(ShrinkedChatSessionState shrinkedChatSessionState,
			GeboChatRequest lastRequest, GUserChatContext actualContext, int tokensBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		AIDocumentsSet chatWithDocsContents = new AIDocumentsSet();
		AIDocumentsSet uploadedDocuments = new AIDocumentsSet();
		// here add shrinked + actual request + chatWithDocsContents
		List<UserUploadedContent> uploads = new ArrayList<UserUploadedContent>(
				lastRequest != null && lastRequest.getUserUploadedContents() != null
						? lastRequest.getUserUploadedContents()
						: List.of());
		List<String> chatWithDocumentsList = new ArrayList(
				lastRequest != null && lastRequest.getForcedRequestDocuments() != null
						? lastRequest.getForcedRequestDocuments()
						: List.of());
		final int shrinkedInteractionsIndex = shrinkedChatSessionState.getConsolidatedInteractions()
				.getLastInteractionPointer();
		List<ChatInteractions> latestInteractions = actualContext.getInteractions().subList(shrinkedInteractionsIndex,
				actualContext.getInteractions().size());
		List<CSSSimplefiedInteraction> lastInteractions = new ArrayList<CSSSimplefiedInteraction>();
		for (ChatInteractions interaction : latestInteractions) {
			List<UserUploadedContent> _uploads = interaction.getRequest() != null
					? interaction.getRequest().getUserUploadedContents()
					: List.of();
			List<String> _chatWithDocuments = interaction.getRequest() != null
					? interaction.getRequest().getForcedRequestDocuments()
					: List.of();
			String user = interaction.getRequest() != null ? interaction.getRequest().getQuery() : "";
			int userTokenSize = interaction.getRequestNTokens() != null ? interaction.getRequestNTokens() : 0;
			String assistant = interaction.getResponse() != null && interaction.getResponse().getQueryResponse() != null
					? interaction.getResponse().getQueryResponse().toString()
					: "";
			String decisionCode = interaction.getResponse() != null
					? interaction.getResponse().getPipelineRouterDecisionCode()
					: null;
			int assistantTokenSize = interaction.getResponseNTokens() != null ? interaction.getResponseNTokens() : 0;
			CSSSimplefiedInteraction semplified = new CSSSimplefiedInteraction(user, userTokenSize, assistant,
					assistantTokenSize, decisionCode);
			lastInteractions.add(semplified);
			if (_uploads != null) {
				uploads.addAll(uploads);
			}
			if (_chatWithDocuments != null) {
				chatWithDocumentsList.addAll(_chatWithDocuments);
			}
		}
		if (uploads != null && uploads.size() > 0) {
			for (UserUploadedContent upload : uploads) {
				List<Document> ingested = chatStorageAreaService.getIngestedContentsOf(upload);
				if (ingested != null && !ingested.isEmpty()) {
					AIDocumentsSet entrySet = AIDocumentsSet.from(ingested);
					chatWithDocsContents = AIDocumentsSet.join(chatWithDocsContents, entrySet);
				}
			}
		}
		if (chatWithDocumentsList != null && !chatWithDocumentsList.isEmpty()) {
			Map<String, Boolean> distincts = new HashMap<String, Boolean>();
			chatWithDocumentsList.forEach(x -> {
				distincts.put(x, true);
			});
			List<GDocumentReference> documents = documentsRepository.findAllById(distincts.keySet());
			Map<String, AIDocumentReferenceItem> data = new HashMap();
			for (GDocumentReference gDocumentReference : documents) {
				AIDocumentReferenceItem ingested = documentsCacheService.retrieve(gDocumentReference);
				data.put(ingested.getCode(), ingested);
			}
			AIDocumentsSet thisset = AIDocumentsSet.fromMap(data);
			uploadedDocuments = AIDocumentsSet.join(uploadedDocuments, thisset);
		}
		AIDocumentsSet latestRequestsChatWithDocuments = chatWithDocsContents;
		// retrieved documents in the last request
		AIDocumentsSet retrievedDocuments = lastRequest != null ? lastRequest.getDocuments() : null;
		// documents specifically uploaded from the user in the last not consolidated
		// turns
		AIDocumentsSet latestRequestsUploadedDocuments = uploadedDocuments;
		// Rag retrieved contents storically or in the current request
		AIDocumentsSet historicallyRetrievedDocuments = toAIDocumentsSet(
				shrinkedChatSessionState.getRelevantRetrievedDocuments());
		// Uploaded historical contents
		AIDocumentsSet historicallyUploadedDocuments = toAIDocumentsSet(
				shrinkedChatSessionState.getRelevantUploadedDocuments());
		// LLM Generated artifacts/documents
		AIDocumentsSet llmGeneratedDocuments = toAIDocumentsSet(
				shrinkedChatSessionState.getRelevantLlmGeneratedDocuments());
		String chatConsolidation = shrinkedChatSessionState.getConsolidatedInteractions().getConsolidationText();

		return new LLMChatRequestResources(latestRequestsChatWithDocuments, retrievedDocuments,
				latestRequestsUploadedDocuments, historicallyRetrievedDocuments, historicallyUploadedDocuments,
				llmGeneratedDocuments, chatConsolidation, lastInteractions, lastRequest);
	}

	private AIDocumentsSet toAIDocumentsSet(CSSfRelevantShrinkedDocumentList relevantRagRetrievedDocuments) {

		return relevantRagRetrievedDocuments != null
				? relevantRagRetrievedDocuments
						.toAIDocumentsSet(geboChatConfigs.getHistoricDocumentRelevancyThreashold())
				: new AIDocumentsSet();
	}

}
