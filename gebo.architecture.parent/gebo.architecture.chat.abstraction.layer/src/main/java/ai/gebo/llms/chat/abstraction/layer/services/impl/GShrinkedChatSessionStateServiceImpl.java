package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.system.ingestion.GeboIngestionException;

public class GShrinkedChatSessionStateServiceImpl implements IGShrinkedChatSessionStateService {

	public GShrinkedChatSessionStateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShrinkedChatSessionState retrieveState(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteState(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ShrinkedChatSessionState addRequestToState(GeboChatRequest request, GUserChatContext context)
			 {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addInteractionToState(GeboChatRequest request, GeboChatResponse response,
			GUserChatContext context)
			{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState save(ShrinkedChatSessionState data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addUploadedDocumentToState(UserUploadedContent content, AIDocumentReferenceItem ingested, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState removeUploadedDocumentToState(UserUploadedContent content, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addChatWithDocumentToState(GDocumentReference reference, AIDocumentReferenceItem ingestedDocument, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState removeChatWithDocumentToState(GDocumentReference reference,
			GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState removeRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addLLMGeneratedDocumntsToState(LLMGeneratedResource resource,
			AIDocumentReferenceItem ingested, GUserChatContext context) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

}
