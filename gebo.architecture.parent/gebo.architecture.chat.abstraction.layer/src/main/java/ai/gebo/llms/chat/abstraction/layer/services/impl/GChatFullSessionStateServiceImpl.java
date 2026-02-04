package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.impl.AIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GChatFullSessionStateServiceImpl implements IGChatFullSessionStateService {
	final IGChatStorageAreaService storageAreaService;
	final DocumentReferenceRepository documentsRepository;
	final AIDocumentsCacheService documentsCacheService;
	final ChatFullSessionStateRepository sessionRepo;
	final GeboChatConfigs chatConfig;
	final static Logger LOGGER = LoggerFactory.getLogger(GChatFullSessionStateServiceImpl.class);

	@Override
	public ChatFullSessionState addRequestToState(GeboChatRequest request, GUserChatContext context) {
		ChatFullSessionState outState = retrieveState(context.getCode());
		if (outState.getCurrentRequest() == null || outState.getCurrentRequest().getValue() == null) {
			outState.getCurrentRequest().setValue(request);
			sessionRepo.save(outState);
		}
		return outState;
	}

	@Override
	public ChatFullSessionState retrieveState(String id) {
		Optional<ChatFullSessionState> opt = sessionRepo.findById(id);
		if (opt.isPresent())
			return opt.get();
		else {

			return null;
		}
	}

	@Override
	public void deleteState(String id) {
		sessionRepo.deleteById(id);

	}

	@Override
	public ChatFullSessionState addInteractionToState(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context) {
		ChatFullSessionState state = retrieveState(context);
		
		
		return null;
	}

	@Override
	public ChatFullSessionState save(ChatFullSessionState data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addUploadedDocumentToState(UserUploadedContent content, AIDocumentReferenceItem ingested, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState removeUploadedDocumentToState(UserUploadedContent content, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addChatWithDocumentToState(GDocumentReference reference, AIDocumentReferenceItem ingestedDocument, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState removeChatWithDocumentToState(GDocumentReference reference, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState removeRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addLLMGeneratedDocumntsToState(LLMGeneratedResource resource, AIDocumentReferenceItem ingested, GUserChatContext context)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

}
