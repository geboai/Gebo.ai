package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatSessionLifeCycleConfig;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GChatSessionLifeCycleServiceImpl implements IGChatSessionLifeCycleService {

	private final IGChatFullSessionStateService fullSessionStateService;
	private final IGShrinkedChatSessionStateService shrinkedSessionStateService;
	private final GeboChatSessionLifeCycleConfig lifeCycleConfig;
	private final IGChatSessionStateShrinkerService sessionStateShrinkerService;
	private final DocumentReferenceRepository documentsRepository;
	private final IGChatStorageAreaService chatAreaStorageSession;
	private final IGAIDocumentsCacheService documentsCacheService;

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
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException, IOException {
		List<String> documentsList = request.getForcedRequestDocuments();
		List<UserUploadedContent> uploadedContents = request.getUserUploadedContents();
		this.fullSessionStateService.addRequestToState(request, context);
		this.shrinkedSessionStateService.addRequestToState(request, context);
		if (documentsList != null && !documentsList.isEmpty()) {
			List<GDocumentReference> docs = documentsRepository.findAllById(documentsList);
			for (GDocumentReference doc : docs) {
				addChatWithDocumentToState(doc, context, targetChatModel);
			}
		}
		if (uploadedContents != null) {
			for (UserUploadedContent userUploadedContent : uploadedContents) {
				addUploadedDocumentToState(userUploadedContent, context, targetChatModel);
			}
		}
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
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
		double contextWindow = targetChatModel.getContextLength();
		double shrinkResize = lifeCycleConfig.getSessionShrinkResizeContextWindowCoeff() * contextWindow;
		if (lifeCycleConfig.getMinimumShrinkResizeTargetTokens() != null
				&& shrinkResize < lifeCycleConfig.getMinimumShrinkResizeTargetTokens().doubleValue()) {
			shrinkResize = lifeCycleConfig.getMinimumShrinkResizeTargetTokens().doubleValue();
		}
		return (int) shrinkResize;
	}

	private LLMChatRequestResources shrinkReturnToBudget(ShrinkedChatSessionState shrinked, int budget) {
		LLMChatRequestResources data = shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getRelevantRetrievedDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getRelevantUploadedDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getRelevantLlmGeneratedDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getLatestRequestsRetrievedDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getLatestRequestsChatWithDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getLatestRequestsUploadedDocuments(), budget))
			return shrinked.createChatRequestResources();
		if (removeProgressively(shrinked, shrinked.getLatestRequestsLlmGeneratedDocuments(), budget))
			return shrinked.createChatRequestResources();

		return shrinked.createChatRequestResources();
	}

	private boolean removeProgressively(ShrinkedChatSessionState shrinked,
			CSSReferredContentList<?> cssReferredContentList, int budget) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean removeProgressively(ShrinkedChatSessionState shrinked,
			CSSfRelevantShrinkedDocumentList relevantRetrievedDocuments, int budget) {
		// TODO Auto-generated method stub
		return false;
	}

	private int getTokensBudget(IGConfigurableChatModel targetChatModel) {
		double contextWindow = targetChatModel.getContextLength();
		double maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowFullFillCoeff() * contextWindow;
		if (lifeCycleConfig.getMaximumContextWindowTokenUsed() != null
				&& maximumTokenBudget > lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue()) {
			maximumTokenBudget = lifeCycleConfig.getMaximumContextWindowTokenUsed().doubleValue();
		}
		return (int) maximumTokenBudget;
	}

	@Override
	public LLMChatRequestResources addUploadedDocumentToState(UserUploadedContent content, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException, IOException {
		List<Document> ingested = this.chatAreaStorageSession.getIngestedContentsOf(content);
		AIDocumentsSet docset = AIDocumentsSet.from(ingested);
		ChatFullSessionState state = null;
		ShrinkedChatSessionState shrinked = null;
		if (!docset.getDocumentItems().isEmpty()) {
			AIDocumentReferenceItem data = docset.getDocumentItems().get(0);
			state = this.fullSessionStateService.addUploadedDocumentToState(content, data, context);

			shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(content, data, context);
		} else {
			state = this.fullSessionStateService.retrieveState(context);
			shrinked = this.shrinkedSessionStateService.retrieveState(context);
		}
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

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeUploadedDocumentToState(content,
				context);
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
		AIDocumentReferenceItem data = null;
		try {
			data = this.documentsCacheService.retrieve(reference);
		} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
				| GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
		}
		ChatFullSessionState state = this.fullSessionStateService.addChatWithDocumentToState(reference, data, context);

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(reference, data,
				context);
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

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeChatWithDocumentToState(reference,
				context);
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
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addRetrievedDocumentsToState(retrieved,
				context);
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
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.removeRetrievedDocumentsToState(retrieved,
				context);
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
			GUserChatContext context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException {
		try {
			List<Document> docs = this.chatAreaStorageSession.getIngestedContentsOf(resource);
			AIDocumentsSet docset = AIDocumentsSet.from(docs);
			ChatFullSessionState state = null;
			ShrinkedChatSessionState shrinked = null;
			if (docset != null && !docset.getDocumentItems().isEmpty()) {
				AIDocumentReferenceItem ingested = docset.getDocumentItems().get(0);
				state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(resource, ingested, context);
				shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(resource, ingested, context);
			} else {
				state = this.fullSessionStateService.retrieveState(context);
				shrinked = this.shrinkedSessionStateService.retrieveState(context);
			}
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
		} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception ingesting a generated resource", e);
		}

	}

	@Override
	public void addInteractionToState(GeboChatRequest request, GeboChatResponse response, GUserChatContext context,
			IGConfigurableChatModel targetChatModel) throws GeboChatSessionLifecycleException {
		List<LLMGeneratedResource> generated = response.getGeneratedResources();
		if (generated != null) {
			for (LLMGeneratedResource llmGeneratedResource : generated) {

				addLLMGeneratedDocumntsToState(llmGeneratedResource, context, targetChatModel);

			}
		}
		ChatFullSessionState state = this.fullSessionStateService.addInteractionToState(request, response, context);

		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.addInteractionToState(request, response,
				context);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);

	}

	@Override
	public void chatRequestCompleted(GUserChatContext context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException {
		this.sessionStateShrinkerService.shrink(retrieveAndCheck(context), getTargetShrinkResize(targetChatModel));

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
