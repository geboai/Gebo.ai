package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.ITokensCountable;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.repository.ShrinkedChatSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.GDocumentReferenceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.LLMGeneratedResourceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.UserUploadedContentSTO;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GShrinkedChatSessionStateServiceImpl implements IGShrinkedChatSessionStateService {
	private final ShrinkedChatSessionStateRepository repo;
	private final DocumentReferenceRepository docRepo;

	@Override
	public ShrinkedChatSessionState retrieveState(String id) {
		Optional<ShrinkedChatSessionState> op = repo.findById(id);
		return op.isPresent() ? op.get() : null;
	}

	@Override
	public void deleteState(String id) {
		repo.deleteById(id);
	}

	@Override
	public ShrinkedChatSessionState addRequestToState(ShrinkedChatSessionState session, GeboChatRequest request,
			int index) {
		session.setCurrentRequest(request);
		return session;
	}

	@Override
	public ShrinkedChatSessionState addInteractionToState(ShrinkedChatSessionState session, GeboChatRequest request,
			GeboChatResponse response, int index) {
		session.setCurrentRequest(null);
		List<CSSSimplefiedInteraction> interactions = session.getChatHistory().getInteractions();
		CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction();
		interaction.setUser(request.getQuery());
		int length = ITokensCountable.tokensEstimator.estimate(request.getQuery());
		interaction.setUserTokenSize(length);
		interaction.setAssistant(response.getQueryResponse());
		length = ITokensCountable.tokensEstimator.estimate(response.getQueryResponse());
		interaction.setAssistantTokenSize(length);
		interactions.add(interaction);
		return session;
	}

	@Override
	public ShrinkedChatSessionState save(ShrinkedChatSessionState data) {

		return repo.save(data);
	}

	@Override
	public ShrinkedChatSessionState addUploadedDocumentToState(ShrinkedChatSessionState session,
			UserUploadedContent content, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<UserUploadedContentSTO> contentBag = new CSSInteractionReferredContent<UserUploadedContentSTO>();
		contentBag.setAppReference(UserUploadedContentSTO.of(content));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getLatestRequestsUploadedDocuments().getData().add(contentBag);
		return session;
	}

	@Override
	public ShrinkedChatSessionState removeUploadedDocumentToState(ShrinkedChatSessionState session,
			UserUploadedContent content) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addChatWithDocumentToState(ShrinkedChatSessionState session,
			GDocumentReference reference, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<GDocumentReferenceSTO> contentBag = new CSSInteractionReferredContent<GDocumentReferenceSTO>();
		contentBag.setAppReference(GDocumentReferenceSTO.of(reference));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getLatestRequestsChatWithDocuments().getData().add(contentBag);
		return session;
	}

	@Override
	public ShrinkedChatSessionState removeChatWithDocumentToState(ShrinkedChatSessionState session,
			GDocumentReference reference) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addRetrievedDocumentsToState(ShrinkedChatSessionState session,
			AIDocumentsSet retrieved, int index) throws GeboChatSessionLifecycleException {
		for (AIDocumentReferenceItem doc : retrieved.getDocumentItems()) {
			Optional<GDocumentReference> dr = this.docRepo.findById(doc.getCode());
			if (dr.isPresent()) {
				CSSInteractionReferredContent<GDocumentReferenceSTO> contentBag = new CSSInteractionReferredContent<GDocumentReferenceSTO>();
				contentBag.setAppReference(GDocumentReferenceSTO.of(dr.get()));
				contentBag.setAiDocument(doc);
				contentBag.setInteractionIndex(index);
				session.getLatestRequestsRetrievedDocuments().getData().add(contentBag);
			}
		}
		return session;
	}

	@Override
	public ShrinkedChatSessionState removeRetrievedDocumentsToState(ShrinkedChatSessionState session,
			AIDocumentsSet retrieved) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShrinkedChatSessionState addLLMGeneratedDocumntsToState(ShrinkedChatSessionState session,
			LLMGeneratedResource resource, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<LLMGeneratedResourceSTO> contentBag = new CSSInteractionReferredContent<LLMGeneratedResourceSTO>();
		contentBag.setAppReference(LLMGeneratedResourceSTO.of(resource));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getLatestRequestsLlmGeneratedDocuments().getData().add(contentBag);
		return session;
	}

}
