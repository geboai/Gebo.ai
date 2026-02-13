package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
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
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatFullSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGShrinkedChatSessionStateService;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSReferredContentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSfRelevantShrinkedDocumentList;
import ai.gebo.llms.chat.abstraction.layer.session.model.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.abstraction.layer.session.model.ShrinkedChatSessionState;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class GChatSessionLifeCycleServiceImpl implements IGChatSessionLifeCycleService, IGMessageEmitter {

	private final SessionShrinkMessagesReceiver sessionShrinkMessagesReceiver;

	static final String SESSION_LIFE_CYCLE_SERVICE = "sessionLifeCycleService";
	private final IGChatFullSessionStateService fullSessionStateService;
	private final IGShrinkedChatSessionStateService shrinkedSessionStateService;
	private final GeboChatSessionLifeCycleConfig lifeCycleConfig;
	private final DocumentReferenceRepository documentsRepository;
	private final IGChatStorageAreaService chatAreaStorageSession;
	private final IGAIDocumentsCacheService documentsCacheService;
	private final IGMessageBroker broker;

	@Override
	public void createChatSession(GUserChatSession context, IGConfigurableChatModel targetChatModel)
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
	public void removeChatSession(GUserChatSession context) {
		this.fullSessionStateService.deleteState(context.getCode());
		this.shrinkedSessionStateService.deleteState(context.getCode());
	}

	@Override
	public LLMChatRequestResources addRequestToState(GUserChatSession context, GeboChatRequest request,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, IOException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		List<String> documentsList = request.getForcedRequestDocuments();
		List<UserUploadedContent> uploadedContents = request.getUserUploadedContents();
		this.fullSessionStateService.addRequestToState(state, request, index);
		this.shrinkedSessionStateService.addRequestToState(shrinked, request, index);
		if (documentsList != null && !documentsList.isEmpty()) {
			List<GDocumentReference> docs = documentsRepository.findAllById(documentsList);
			for (GDocumentReference doc : docs) {
				AIDocumentReferenceItem ingested = null;
				try {
					ingested = this.documentsCacheService.retrieve(doc);
				} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
						| GeboIngestionException e) {
					throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
				}
				state = this.fullSessionStateService.addChatWithDocumentToState(state, doc, ingested, index);
				shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, doc, ingested, index);
			}
		}
		if (uploadedContents != null) {
			for (UserUploadedContent userUploadedContent : uploadedContents) {
				List<Document> ingested = this.chatAreaStorageSession.getIngestedContentsOf(userUploadedContent);
				AIDocumentsSet docset = AIDocumentsSet.from(ingested);
				if (!docset.getDocumentItems().isEmpty()) {
					AIDocumentReferenceItem data = docset.getDocumentItems().get(0);
					state = this.fullSessionStateService.addUploadedDocumentToState(state, userUploadedContent, data,
							index);
					shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(shrinked,
							userUploadedContent, data, index);
				}
			}
		}

		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.fullSessionStateService.save(state);
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
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

	private LLMChatRequestResources applyGenerationPolicy(ShrinkedChatSessionState shrinked, int budget,
			LLMRequestGenerationPolicy policy) {
		switch (policy) {
		case ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET: {
			return shrinked.createChatRequestResources(policy);
		}

		default: {

			LLMChatRequestResources data = shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantChatWithDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantRetrievedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantUploadedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeRelevantPastContentsProgressively(shrinked, shrinked.getRelevantLlmGeneratedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsRetrievedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsChatWithDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsUploadedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			if (removeLatestDocumentsProgressively(shrinked, shrinked.getLatestRequestsLlmGeneratedDocuments(), budget))
				return shrinked.createChatRequestResources(policy);
			return shrinked.createChatRequestResources(policy);
		}

		}

	}

	private boolean removeLatestDocumentsProgressively(ShrinkedChatSessionState shrinked,
			CSSReferredContentList<?> cssReferredContentList, int budget) {
		do {
			if (!cssReferredContentList.getData().isEmpty())
				cssReferredContentList.getData().remove(0);
		} while (!cssReferredContentList.getData().isEmpty() && budget > shrinked.getTokensSize());
		return budget > shrinked.getTokensSize();
	}

	private boolean removeRelevantPastContentsProgressively(ShrinkedChatSessionState shrinked,
			CSSfRelevantShrinkedDocumentList relevantRetrievedDocuments, int budget) {
		do {
			if (!relevantRetrievedDocuments.isEmpty())
				relevantRetrievedDocuments.remove(0);
		} while (!relevantRetrievedDocuments.isEmpty() && budget > shrinked.getTokensSize());
		return budget > shrinked.getTokensSize();
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
	public LLMChatRequestResources addUploadedDocumentToState(GUserChatSession context, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, IOException {
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		List<Document> ingested = this.chatAreaStorageSession.getIngestedContentsOf(content);
		AIDocumentsSet docset = AIDocumentsSet.from(ingested);
		if (!docset.getDocumentItems().isEmpty()) {
			AIDocumentReferenceItem data = docset.getDocumentItems().get(0);
			state = this.fullSessionStateService.addUploadedDocumentToState(state, content, data, index);
			shrinked = this.shrinkedSessionStateService.addUploadedDocumentToState(shrinked, content, data, index);
		}
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.fullSessionStateService.save(state);
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);

	}

	@Override
	public LLMChatRequestResources removeUploadedDocumentToState(GUserChatSession context, UserUploadedContent content,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		state = this.fullSessionStateService.removeUploadedDocumentToState(state, content);
		shrinked = this.shrinkedSessionStateService.removeUploadedDocumentToState(shrinked, content);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		this.fullSessionStateService.save(state);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addChatWithDocumentToState(GUserChatSession context, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		AIDocumentReferenceItem data = null;
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		try {
			data = this.documentsCacheService.retrieve(reference);
		} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
				| GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception in ingesting docs", e);
		}
		state = this.fullSessionStateService.addChatWithDocumentToState(state, reference, data, index);
		shrinked = this.shrinkedSessionStateService.addChatWithDocumentToState(shrinked, reference, data, index);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.fullSessionStateService.save(state);
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources removeChatWithDocumentToState(GUserChatSession context, GDocumentReference reference,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		state = this.fullSessionStateService.removeChatWithDocumentToState(state, reference);
		shrinked = this.shrinkedSessionStateService.removeChatWithDocumentToState(shrinked, reference);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		this.fullSessionStateService.save(state);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addRetrievedDocumentsToState(GUserChatSession context, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);

		state = this.fullSessionStateService.addRetrievedDocumentsToState(state, retrieved, index);
		this.fullSessionStateService.save(state);
		shrinked = this.shrinkedSessionStateService.addRetrievedDocumentsToState(shrinked, retrieved, index);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources removeRetrievedDocumentsToState(GUserChatSession context, AIDocumentsSet retrieved,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);

		state = this.fullSessionStateService.removeRetrievedDocumentsToState(state, retrieved);
		this.fullSessionStateService.save(state);
		shrinked = this.shrinkedSessionStateService.removeRetrievedDocumentsToState(shrinked, retrieved);
		int budget = getTokensBudget(targetChatModel);
		if (shrinked.getTokensSize() >= budget) {
			int targetTokenBudget = getTargetShrinkResize(targetChatModel);
			shrinked.setToBeShrinked(true);
			shrinked.setTargetTokenBudget(targetTokenBudget);
		}
		this.shrinkedSessionStateService.save(shrinked);
		if (state.getTokensSize() < budget) {
			return state.createChatRequestResources(policy);
		}
		if (shrinked.getTokensSize() < budget) {
			return shrinked.createChatRequestResources(policy);
		}
		return applyGenerationPolicy(shrinked, budget, policy);
	}

	@Override
	public LLMChatRequestResources addLLMGeneratedDocumntsToState(GUserChatSession context,
			LLMGeneratedResource resource, IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException {
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);

		try {
			List<Document> docs = this.chatAreaStorageSession.getIngestedContentsOf(resource);
			AIDocumentsSet docset = AIDocumentsSet.from(docs);
			if (docset != null && !docset.getDocumentItems().isEmpty()) {
				AIDocumentReferenceItem ingested = docset.getDocumentItems().get(0);
				state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(state, resource, ingested, index);
				shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(shrinked, resource, ingested,
						index);
			}
			int budget = getTokensBudget(targetChatModel);
			if (shrinked.getTokensSize() >= budget) {
				int targetTokenBudget = getTargetShrinkResize(targetChatModel);
				shrinked.setToBeShrinked(true);
				shrinked.setTargetTokenBudget(targetTokenBudget);
			}
			this.shrinkedSessionStateService.save(shrinked);
			this.fullSessionStateService.save(state);
			if (state.getTokensSize() < budget) {
				return state.createChatRequestResources(policy);
			}
			if (shrinked.getTokensSize() < budget) {
				return shrinked.createChatRequestResources(policy);
			}
			return applyGenerationPolicy(shrinked, budget, policy);
		} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
			throw new GeboChatSessionLifecycleException("Exception ingesting a generated resource", e);
		}

	}

	@Override
	public void addInteractionToState(GUserChatSession context, GeboChatRequest request, GeboChatResponse response)
			throws GeboChatSessionLifecycleException {
		int index = context.getInteractions() != null ? context.getInteractions().size() : 0;
		ChatFullSessionState state = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		List<LLMGeneratedResource> generated = response.getGeneratedResources();
		if (generated != null) {
			for (LLMGeneratedResource llmGeneratedResource : generated) {
				List<Document> docs = null;
				try {
					docs = this.chatAreaStorageSession.getIngestedContentsOf(llmGeneratedResource);
				} catch (IOException | GeboContentHandlerSystemException | GeboIngestionException e) {
					throw new GeboChatSessionLifecycleException("Exception ingesting a generated resource", e);
				}
				AIDocumentsSet docset = AIDocumentsSet.from(docs);

				if (docset != null && !docset.getDocumentItems().isEmpty()) {
					AIDocumentReferenceItem ingested = docset.getDocumentItems().get(0);
					state = this.fullSessionStateService.addLLMGeneratedDocumntsToState(state, llmGeneratedResource,
							ingested, index);
					shrinked = this.shrinkedSessionStateService.addLLMGeneratedDocumntsToState(shrinked,
							llmGeneratedResource, ingested, index);
				}

			}
		}
		state = this.fullSessionStateService.addInteractionToState(state, request, response, index);
		shrinked = this.shrinkedSessionStateService.addInteractionToState(shrinked, request, response, index);
		this.fullSessionStateService.save(state);

		this.shrinkedSessionStateService.save(shrinked);

	}

	@Override
	public void chatRequestCompleted(GUserChatSession context, IGConfigurableChatModel targetChatModel)
			throws GeboChatSessionLifecycleException, LLMConfigException, IOException {
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		if (shrinked != null && shrinked.isToBeShrinked()) {
			String code = context.getCode();
			int budgetSize = getTargetShrinkResize(targetChatModel);
			SessionShrinkRequestPayload checkPayload = new SessionShrinkRequestPayload();
			checkPayload.setTokensBudget(budgetSize);
			checkPayload.setUserChatSessionCode(code);
			GMessageEnvelope<SessionShrinkRequestPayload> envelope = GMessageEnvelope.newMessageFrom(this,
					checkPayload);
			envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			envelope.setTargetComponent(SessionShrinkMessagesReceiver.SESSION_SHRINKER);
			this.broker.accept(envelope);
		}
	}

	@Override
	public void ensureChatSessionExists(GUserChatSession context, IGConfigurableChatModel targetChatModel)
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

	private ChatFullSessionState retrieveAndCheck(GUserChatSession ctx) throws GeboChatSessionLifecycleException {
		return this.retrieveAndCheck(ctx.getCode());
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.CORE_MODULE;
	}

	@Override
	public String getMessagingSystemId() {

		return SESSION_LIFE_CYCLE_SERVICE;
	}

	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(SessionShrinkRequestPayload.class.getName());
	}

	@Override
	public void updateRequest(GUserChatSession context, GeboChatRequest request)
			throws GeboChatSessionLifecycleException {
		ChatFullSessionState full = this.fullSessionStateService.retrieveState(context);
		ShrinkedChatSessionState shrinked = this.shrinkedSessionStateService.retrieveState(context);
		if (shrinked.getCurrentRequest() != null && request.getId() != null
				&& shrinked.getCurrentRequest().getId().equals(request.getId())) {
			shrinked.setCurrentRequest(request);
			this.shrinkedSessionStateService.save(shrinked);
		} else
			throw new GeboChatSessionLifecycleException(
					"Logical problem, the request id to update does not match the actual one");
		if (full.getCurrentRequest().getValue() != null && full.getCurrentRequest().getValue().getId() != null
				&& request.getId() != null && full.getCurrentRequest().getValue().getId().equals(request.getId())) {
			full.getCurrentRequest().setValue(request);
			this.fullSessionStateService.save(full);

		} else
			throw new GeboChatSessionLifecycleException(
					"Logical problem, the request id to update does not match the actual one");

	}
}
