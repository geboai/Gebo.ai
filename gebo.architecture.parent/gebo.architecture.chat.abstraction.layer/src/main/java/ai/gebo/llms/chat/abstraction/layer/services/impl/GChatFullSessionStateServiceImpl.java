package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.ai.model.ITokensCountable;
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
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSInteractionReferredContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSSimplefiedInteraction;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.GDocumentReferenceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.LLMGeneratedResourceSTO;
import ai.gebo.llms.chat.abstraction.layer.session.model.UserUploadedContentSTO;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GChatFullSessionStateServiceImpl implements IGChatFullSessionStateService {
	private final IGChatStorageAreaService storageAreaService;
	private final DocumentReferenceRepository documentsRepository;
	private final AIDocumentsCacheService documentsCacheService;
	private final ChatFullSessionStateRepository sessionRepo;
	private final DocumentReferenceRepository docRepo;
	private final GeboChatConfigs chatConfig;
	private final static Logger LOGGER = LoggerFactory.getLogger(GChatFullSessionStateServiceImpl.class);

	@Override
	public ChatFullSessionState addRequestToState(ChatFullSessionState session, GeboChatRequest request, int index) {

		session.getCurrentRequest().setValue(request);

		return session;
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
	public ChatFullSessionState addInteractionToState(ChatFullSessionState session, GeboChatRequest request,
			GeboChatResponse response, int index) {
		session.setCurrentRequest(null);
		List<CSSSimplefiedInteraction> interactions = session.getChatHistory().getValue().getInteractions();
		CSSSimplefiedInteraction interaction = new CSSSimplefiedInteraction();
		interaction.setUser(request.getQuery());
		int length = ITokensCountable.tokensEstimator.estimate(request.getQuery());
		interaction.setRequestId(request.getId());
		interaction.setUserTokenSize(length);
		interaction.setUserIntent(request.getUserIntent());
		interaction.setAssistant(response.getQueryResponse());
		length = ITokensCountable.tokensEstimator.estimate(response.getQueryResponse());
		interaction.setAssistantTokenSize(length);
		interactions.add(interaction);
		return session;

	}

	@Override
	public ChatFullSessionState save(ChatFullSessionState data) {
		return this.sessionRepo.save(data);
	}

	@Override
	public ChatFullSessionState addUploadedDocumentToState(ChatFullSessionState session, UserUploadedContent content,
			AIDocumentReferenceItem ingested, int index) throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<UserUploadedContentSTO> contentBag = new CSSInteractionReferredContent<UserUploadedContentSTO>();
		contentBag.setAppReference(UserUploadedContentSTO.of(content));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getUploadedDocuments().getValue().getData().add(contentBag);
		return session;
	}

	@Override
	public ChatFullSessionState removeUploadedDocumentToState(ChatFullSessionState session, UserUploadedContent content)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addChatWithDocumentToState(ChatFullSessionState session, GDocumentReference reference,
			AIDocumentReferenceItem ingested, int index) throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<GDocumentReferenceSTO> contentBag = new CSSInteractionReferredContent<GDocumentReferenceSTO>();
		contentBag.setAppReference(GDocumentReferenceSTO.of(reference));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getChatWithDocuments().getValue().getData().add(contentBag);
		return session;
	}

	@Override
	public ChatFullSessionState removeChatWithDocumentToState(ChatFullSessionState session,
			GDocumentReference reference) throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addRetrievedDocumentsToState(ChatFullSessionState session, AIDocumentsSet retrieved,
			int index) throws GeboChatSessionLifecycleException {
		for (AIDocumentReferenceItem doc : retrieved.getDocumentItems()) {
			Optional<GDocumentReference> dr = this.docRepo.findById(doc.getCode());
			if (dr.isPresent()) {
				CSSInteractionReferredContent<GDocumentReferenceSTO> contentBag = new CSSInteractionReferredContent<GDocumentReferenceSTO>();
				contentBag.setAppReference(GDocumentReferenceSTO.of(dr.get()));
				contentBag.setAiDocument(doc);
				contentBag.setInteractionIndex(index);
				session.getRetrievedDocuments().getValue().getData().add(contentBag);
			}
		}
		return session;
	}

	@Override
	public ChatFullSessionState removeRetrievedDocumentsToState(ChatFullSessionState session, AIDocumentsSet retrieved)
			throws GeboChatSessionLifecycleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatFullSessionState addLLMGeneratedDocumntsToState(ChatFullSessionState session,
			LLMGeneratedResource resource, AIDocumentReferenceItem ingested, int index)
			throws GeboChatSessionLifecycleException {
		CSSInteractionReferredContent<LLMGeneratedResourceSTO> contentBag = new CSSInteractionReferredContent<LLMGeneratedResourceSTO>();
		contentBag.setAppReference(LLMGeneratedResourceSTO.of(resource));
		contentBag.setAiDocument(ingested);
		contentBag.setInteractionIndex(index);
		session.getLlmGeneratedDocuments().getValue().getData().add(contentBag);
		return session;
	}

}
