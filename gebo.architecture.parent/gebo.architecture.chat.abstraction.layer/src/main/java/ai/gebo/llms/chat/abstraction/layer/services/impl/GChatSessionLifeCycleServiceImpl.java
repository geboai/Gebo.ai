package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.repository.ChatFullSessionStateRepository;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GChatSessionLifeCycleServiceImpl implements IGChatSessionLifeCycleService {

	private final IGChatFullSessionStateService fullSessionStateService;
	private final IGShrinkedChatSessionStateService shrinkedSessionStateService;

	@Override
	public void createChatSession(GUserChatContext context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException {
		if (context.getInteractions() != null && !context.getInteractions().isEmpty()) {
			throw new GeboChatSessionLifecycleException("Cannot create a session for user context=>" + context.getCode()
					+ " that already has interactions");
		} else {
			ChatFullSessionState data = fullSessionStateService.retrieveState(context.getCode());
			if (data != null)
				throw new GeboChatSessionLifecycleException(
						"Cannot create a session for user context=>" + context.getCode() + " that already has one");
		}
		ChatFullSessionState data = new ChatFullSessionState();
		data.setUserChatContextCode(context.getCode());
		this.fullSessionStateService.save(data);
	}

	@Override
	public void removeChatSession(GUserChatContext context) {
		this.fullSessionStateService.deleteState(context.getCode());
		this.shrinkedSessionStateService.deleteState(context.getCode());
	}

	@Override
	public LLMChatRequestResources addRequestToState(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources addUploadedDocumentToState(UserUploadedContent content, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources removeUploadedDocumentToState(UserUploadedContent content, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources addChatWithDocumentToState(GDocumentReference reference, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources removeChatWithDocumentToState(GDocumentReference reference, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources addRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources removeRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LLMChatRequestResources addLLMGeneratedDocumntsToState(LLMGeneratedResource resource,
			GUserChatContext context, IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInteractionToState(GeboChatRequest request, GeboChatResponse response, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void chatRequestCompleted(GUserChatContext context, IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ensureChatSessionExists(GUserChatContext context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException {

		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context.getCode());
		if (state == null)
			createChatSession(context, targetChatModel);
	}

}
