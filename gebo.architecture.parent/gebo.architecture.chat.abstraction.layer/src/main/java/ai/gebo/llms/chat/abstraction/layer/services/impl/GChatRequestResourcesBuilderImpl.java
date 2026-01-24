package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
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
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRequestResourcesBuilder;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatRequestResourcesBuilderImpl implements IGChatRequestResourcesBuilder {
	private final ShrinkedChatSessionStateRepository shrinkedSessionStateRepository;
	private final IGChatSessionStateService chatSessionStateService;
	private final IGChatStorageAreaService chatStorageAreaService;
	private final DocumentReferenceRepository documentsRepository;
	private final AIDocumentsCacheService documentsCacheService;

	@Override
	public LLMChatRequestResources buildRequestResources(GeboChatRequest lastRequest, GUserChatContext actualContext,
			int tokensBudget)
			throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException {
		// user asked resources to be included in the chat
		List<UserUploadedContent> uploads = lastRequest.getUserUploadedContents();
		
		AIDocumentsSet chatWithDocsContents = new AIDocumentsSet();
		if (uploads != null && uploads.size() > 0) {
			for (UserUploadedContent upload : uploads) {
				List<Document> ingested = chatStorageAreaService.getIngestedContentsOf(upload);
				if (ingested != null && !ingested.isEmpty()) {
					AIDocumentsSet entrySet = AIDocumentsSet.from(ingested);
					chatWithDocsContents = AIDocumentsSet.join(chatWithDocsContents, entrySet);
				}
			}
		}
		List<String> chatWithDocumentsList = lastRequest.getForcedRequestDocuments();
		if (chatWithDocumentsList != null && !chatWithDocumentsList.isEmpty()) {
			List<GDocumentReference> documents = documentsRepository.findAllById(chatWithDocumentsList);
			Map<String, AIDocumentReferenceItem> data = new HashMap();
			for (GDocumentReference gDocumentReference : documents) {
				AIDocumentReferenceItem ingested = documentsCacheService.retrieve(gDocumentReference);
				data.put(ingested.getCode(), ingested);
			}
			AIDocumentsSet thisset = AIDocumentsSet.createDocumentsDaoResultFromMap(data);
			chatWithDocsContents = AIDocumentsSet.join(chatWithDocsContents, thisset);
		}
		// if i find the session state already shrinked i use it
		Optional<ShrinkedChatSessionState> shrinkedOptional = shrinkedSessionStateRepository
				.findById(actualContext.getCode());
		if (shrinkedOptional.isPresent()) {
			//here add shrinked + actual request + chatWithDocsContents
		} else {
			ChatFullSessionState fullState = this.chatSessionStateService.extractState(null, actualContext);
			//here add fullstate + actual request + chatWithDocsContents
		}
		return null;
	}

}
