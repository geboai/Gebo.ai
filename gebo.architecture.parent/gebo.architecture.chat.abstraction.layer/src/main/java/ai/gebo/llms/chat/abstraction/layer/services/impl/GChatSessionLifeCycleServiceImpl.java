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
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
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
		ShrinkedChatSessionState shrinked = new ShrinkedChatSessionState();
		shrinked.setUserChatContextCode(context.getCode());
		this.shrinkedSessionStateService.save(shrinked);

	}

	@Override
	public void removeChatSession(GUserChatContext context) {
		this.fullSessionStateService.deleteState(context.getCode());
		this.shrinkedSessionStateService.deleteState(context.getCode());
	}

	@Override
	public LLMChatRequestResources addRequestToState(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addRequestToState(request, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addRequestToState(request, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	private int getTargetShrinkResize(IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return 0;
	}

	private LLMChatRequestResources shrinkReturnToBudget(ShrinkedChatSessionState shrinked, int budget) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getTokensBudget(IGConfigurableChatModel targetChatModel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LLMChatRequestResources addUploadedDocumentToState(UserUploadedContent content, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addUploadedDocumentToState(content, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(content, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources removeUploadedDocumentToState(UserUploadedContent content, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.removeUploadedDocumentToState(content, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeUploadedDocumentToState(content, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources addChatWithDocumentToState(GDocumentReference reference, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addChatWithDocumentToState(reference, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(reference, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources removeChatWithDocumentToState(GDocumentReference reference, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.removeChatWithDocumentToState(reference, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeChatWithDocumentToState(reference, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources addRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addRetrievedDocumentsToState(retrieved, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addRetrievedDocumentsToState(retrieved, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources removeRetrievedDocumentsToState(AIDocumentsSet retrieved, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.removeRetrievedDocumentsToState(retrieved, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeRetrievedDocumentsToState(retrieved, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public LLMChatRequestResources addLLMGeneratedDocumntsToState(LLMGeneratedResource resource,
			GUserChatContext context, IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(resource, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(resource, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources();
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources();
		}
		return shrinkReturnToBudget(shrinked, budget);
	}

	@Override
	public void addInteractionToState(GeboChatRequest request, GeboChatResponse response, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.addInteractionToState(request, response, context);
		this.fullSessionStateService.save(state);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addInteractionToState(request, response, context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		

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

	private ChatFullSessionState retrieveAndCheck(String id) throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(id);
		if (state == null)
			throw new GeboChatSessionLifecycleException("session " + id + " does not exist");
		return state;
	}

	private ChatFullSessionState retrieveAndCheck(GUserChatContext ctx) throws GeboChatSessionLifecycleException {
		return this.retrieveAndCheck(ctx.getCode());
	}
}
