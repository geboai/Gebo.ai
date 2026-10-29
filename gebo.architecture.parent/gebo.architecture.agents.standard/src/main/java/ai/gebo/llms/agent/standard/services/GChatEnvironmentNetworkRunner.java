/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standard.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkServiceFactory;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionStateShrinkerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.abstraction.layer.session.model.GChatSessionOpeningOptions;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IGRequestUnderstandingService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;

/**
 * Runs a network of agents in the environment a chat gives it, for callers that
 * are not a chat.
 * <p>
 * A network that reaches knowledge bases is only as good as what it is pointed
 * at, and in a chat that is the chat profile's doing: which knowledge bases,
 * which models, which retrieval thresholds, which rules. Run from outside
 * without one, the same network sees every knowledge base its caller can see
 * and the platform's defaults - a different thing altogether.
 * <p>
 * More than that, a chat already keeps what a long-lived caller needs: a
 * conversation that survives between runs, a history consolidated and shrunk to
 * the room a model leaves ({@link IGChatSessionStateShrinkerService}), an area
 * where attachments live, and the rules set for that conversation and profile.
 * A caller that wants those things does not need a second notion of them - it
 * needs a chat, named by the conversation it chooses, and this runs the network
 * in it.
 * <p>
 * The answer is read from the network's own frames. Both readings are the same
 * run: the frames as they come for a caller that streams, and the frame marked
 * as the last for a caller that waits.
 */
@Service
public class GChatEnvironmentNetworkRunner implements IGChatEnvironment {
	private static final Logger LOGGER = LoggerFactory.getLogger(GChatEnvironmentNetworkRunner.class);

	private final IGRuntimeBinder runtimeBinder;
	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	private final IGChatModelRuntimeConfigurationDao chatModelsDao;
	/** Stores what a caller attaches, when this installation takes uploads at all. */
	private final ObjectProvider<IGUserUploadContentHandler> uploadContentHandler;
	/** Builds the environment of the network, the same the chat pipelines build. */
	private final GNetworkEnvironmentBuilder environmentBuilder;
	/** Understands the request as the chat pipelines do, when the run asks for it. */
	private final IGRequestUnderstandingService requestUnderstanding;

	public GChatEnvironmentNetworkRunner(IGRuntimeBinder runtimeBinder,
			IGChatSessionLifeCycleService chatSessionLifecycleService, IGChatModelRuntimeConfigurationDao chatModelsDao,
			ObjectProvider<IGUserUploadContentHandler> uploadContentHandler,
			GNetworkEnvironmentBuilder environmentBuilder, IGRequestUnderstandingService requestUnderstanding) {
		this.runtimeBinder = runtimeBinder;
		this.chatSessionLifecycleService = chatSessionLifecycleService;
		this.chatModelsDao = chatModelsDao;
		this.uploadContentHandler = uploadContentHandler;
		this.environmentBuilder = environmentBuilder != null ? environmentBuilder : GNetworkEnvironmentBuilder.plain();
		this.requestUnderstanding = requestUnderstanding;
	}

	/** What a caller sends along with its question. */
	@Getter
	@AllArgsConstructor
	public static class ChatRunAttachments {
		/** Files the caller sent, to be stored in the conversation's own area. */
		private final List<MultipartFile> files;
		/** The knowledge bases it asked the run to work on, when it said. */
		private final List<String> knowledgeBases;
		/** The documents it named for the run to take in, when it named any. */
		private final List<String> documents;

		public static ChatRunAttachments none() {
			return new ChatRunAttachments(List.of(), List.of(), List.of());
		}

		public boolean isEmpty() {
			return files.isEmpty() && knowledgeBases.isEmpty() && documents.isEmpty();
		}
	}

	/** What a run produced: its frames, and the response of the last of them. */
	public static class ChatEnvironmentRun {
		private final Flux<GeboChatMessageEnvelope> frames;
		private final GeboChatResponse response;
		private final IGReactiveChatAgentsNetworkService service;
		/** The conversation this run happened in, which the next one can continue. */
		@Getter
		private final String chatContextCode;

		ChatEnvironmentRun(Flux<GeboChatMessageEnvelope> frames, GeboChatResponse response,
				IGReactiveChatAgentsNetworkService service, String chatContextCode) {
			this.frames = frames;
			this.response = response;
			this.service = service;
			this.chatContextCode = chatContextCode;
		}

		/** Every frame the network produced, in order. */
		public Flux<GeboChatMessageEnvelope> getFrames() {
			return frames;
		}

		/** The response the run filled in, which the last frame also carries. */
		public GeboChatResponse getResponse() {
			return response;
		}

		/** Releases the network service of the run. */
		public void dispose() {
			if (service != null) {
				service.dispose();
			}
		}
	}

	/**
	 * The request a chat would have built for this run.
	 *
	 * @param text            what the caller asked
	 * @param chatProfileCode the profile to work with, or null for none
	 * @param chatContextCode the conversation to go on with, or null for a new one
	 */
	public GeboChatRequest requestOf(String text, String chatProfileCode, String chatContextCode) {
		GeboChatRequest request = new GeboChatRequest();
		request.setQuery(text != null ? text : "");
		request.setStreamResponse(true);
		if (chatProfileCode != null && !chatProfileCode.isBlank()) {
			request.setChatProfileCode(chatProfileCode);
		}
		if (chatContextCode != null && !chatContextCode.isBlank()) {
			request.setUserChatContextCode(chatContextCode);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Chat environment request built with chatProfileCode:" + chatProfileCode + " conversation:"
					+ chatContextCode);
		}
		return request;
	}

	/**
	 * Runs the named network on the environment of the given request, as the
	 * identity in the current security context - which a caller with nobody behind
	 * it must have taken on before calling.
	 *
	 * @throws AgentException when the network does not exist or the run cannot be
	 *                        prepared
	 */
	public ChatEnvironmentRun run(String networkCode, GeboChatRequest request, ChatRunAttachments attachments,
			INotificationSink sink) throws AgentException {
		final GAgentsNetwork network = runtimeBinder.getImplementationOf(IAgentsNetworkDao.class)
				.findByCode(networkCode);
		if (network == null) {
			throw new AgentException("The network of agents does not exist: " + networkCode);
		}
		return run(network, request, attachments, sink);
	}

	/**
	 * The same, on a network the caller has already resolved and may have adjusted
	 * for this run - how many times its controller may act, above all.
	 * <p>
	 * A chat is bounded because somebody is waiting for the answer. A caller that
	 * nobody is waiting for can let the network work for as long as the work takes,
	 * and says so by handing in a network whose loop limit is its own. Adjust a
	 * copy ({@link GAgentsNetwork#jsonClone()}), never the stored configuration.
	 */
	public ChatEnvironmentRun run(GAgentsNetwork network, GeboChatRequest request, ChatRunAttachments attachments,
			INotificationSink sink) throws AgentException {
		return run(network, request, attachments, List.of(), sink);
	}

	/**
	 * The same, with rules this run is to follow on top of the conversation's own -
	 * the way the rules of a chat reach every model call, since that is what they
	 * are for.
	 */
	public ChatEnvironmentRun run(GAgentsNetwork network, GeboChatRequest request, ChatRunAttachments attachments,
			List<String> rulesOfThisRun, INotificationSink sink) throws AgentException {
		return run(network, request, attachments, rulesOfThisRun, Map.of(), sink);
	}

	/**
	 * The same, with values the tools of this run can read from their own tool
	 * context - how anything a tool needs to know about the run it is serving
	 * reaches it (see {@code ToolCallbackDeclarationUtil}). A caller whose tools
	 * must know which of its runs they are in puts that here.
	 */
	public ChatEnvironmentRun run(GAgentsNetwork network, GeboChatRequest request, ChatRunAttachments attachments,
			List<String> rulesOfThisRun, Map<String, Object> toolsContextOfThisRun, INotificationSink sink)
			throws AgentException {
		return run(GChatEnvironmentRunRequest.builder().network(network).request(request)
				.attachments(attachments != null ? attachments : ChatRunAttachments.none())
				.rulesOfThisRun(rulesOfThisRun != null ? rulesOfThisRun : List.of())
				.toolsContextOfThisRun(toolsContextOfThisRun != null ? toolsContextOfThisRun : Map.of()).sink(sink)
				.build());
	}

	@Override
	public ChatEnvironmentRun run(GChatEnvironmentRunRequest run) throws AgentException {
		if (run == null || run.getNetwork() == null) {
			throw new AgentException("There is no network of agents to run");
		}
		if (run.getRequest() == null) {
			throw new AgentException("There is no request to run the network of agents on");
		}
		final GAgentsNetwork network = run.getNetwork();
		final GeboChatRequest request = run.getRequest();
		final String networkCode = network.getCode();
		final IGReactiveChatAgentsNetworkServiceFactory factory = runtimeBinder
				.getImplementationOf(IGReactiveChatAgentsNetworkServiceFactory.class);
		if (factory == null) {
			throw new AgentException("The chat network of agents factory is not available");
		}
		final ChatRunAttachments attachments = run.getAttachments() != null ? run.getAttachments()
				: ChatRunAttachments.none();
		IGReactiveChatAgentsNetworkService service = null;
		// Whether the request was started in the conversation and not ended yet: a run
		// that fails after that releases it, or the conversation refuses the next one.
		boolean started = false;
		try {
			// the language of the caller's own message, as the pipelines detect it on
			// every request: no model is called for it
			if (requestUnderstanding != null) {
				requestUnderstanding.detectLanguage(request);
			}
			// The conversation has to exist before its request can be started, and a caller
			// naming one we have never seen is naming a new one. It is opened - or shared -
			// before anything reads it, since whoever runs in it must be granted it first.
			chatSessionLifecycleService.ensureChatSessionExists(request, openingOptionsOf(run));
			final IGConfigurableChatModel chatModel = chatSessionLifecycleService.getSessionChatModel(request);
			final IGConfigurableChatModel serviceModel = chatModelsDao.defaultHandler();
			attach(request, attachments);
			final GeboChatResponse response = chatSessionLifecycleService.createEmptyResponse(request);
			final LLMChatRequestResources resources = chatSessionLifecycleService.startRequest(request, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			started = true;
			if (run.getRulesOfThisRun() != null && !run.getRulesOfThisRun().isEmpty()) {
				// Beside the rules the conversation and its profile already carry.
				resources.getRulesToFollow().addAll(run.getRulesOfThisRun());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("The run follows " + run.getRulesOfThisRun().size()
							+ " rule(s) of its own, beside the conversation's");
				}
			}
			final MinimalChatContext minimalChatContext = chatSessionLifecycleService.getMinimalChatContext(request,
					IGChatSessionStateShrinkerService.serviceModelContextBudget(serviceModel));
			// The knowledge bases the run works on, the same for its tools and its agents.
			final List<String> knowledgeBases = knowledgeBasesOf(request, run.getKnowledgeBases(), attachments);
			resources.setAvailableKnowledgeBaseCodes(knowledgeBases);
			minimalChatContext.setAvailableKnowledgeBaseCodes(knowledgeBases);
			// Every tool the run calls is recorded into the response, so what comes back
			// says what was done to answer, as a chat's response does.
			final ToolCallsListener toolCalls = ToolCallsListener.appendingTo(response.getCalledFunctions());
			resources.setToolCallsListener(toolCalls);
			minimalChatContext.setToolCallsListener(toolCalls);
			final ChatPipelineExecutionRuntimeData runtimeData = new ChatPipelineExecutionRuntimeData(null,
					chatModel.getContextLength(), resources, response, minimalChatContext, true);
			if (run.isUnderstood()) {
				understand(runtimeData, chatModel, serviceModel, run.getSink());
			}
			final Map<String, Object> environment = environmentBuilder.build(runtimeData, knowledgeBases,
					run.getEnvironmentOfThisRun());
			final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			service = factory.create(network, run.getSink(), ChatPipelineExecutionRuntimeData.class,
					GeboChatMessageEnvelope.class, runAs);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Running network:" + network.getCode() + " from " + run.getOrigin()
						+ " in the environment of chat profile:" + request.getChatProfileCode() + " conversation:"
						+ request.getUserChatContextCode() + (run.isTransientConversation() ? " (transient)" : "")
						+ " over " + knowledgeBases.size() + " knowledge base(s) (" + run.getKnowledgeBases()
						+ "), context window:" + chatModel.getContextLength());
			}
			// The sink buffers what it emits, so reading the frames after the run loses
			// nothing and reading them as they come loses nothing either.
			final Flux<GeboChatMessageEnvelope> frames = service.getFlux();
			IChatRequestContext context = resources.createChatRequestContext();
			if (run.getToolsContextOfThisRun() != null) {
				for (Map.Entry<String, Object> entry : run.getToolsContextOfThisRun().entrySet()) {
					context = context.withToolsContextValue(entry.getKey(), entry.getValue());
				}
				if (!run.getToolsContextOfThisRun().isEmpty() && LOGGER.isDebugEnabled()) {
					LOGGER.debug("The tools of the run are told " + run.getToolsContextOfThisRun().size()
							+ " thing(s) about it: " + run.getToolsContextOfThisRun().keySet());
				}
			}
			final GeboChatMessageEnvelope last = service.executeNetwork(context, runtimeData, environment);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Network:" + network.getCode() + " answered in conversation:"
						+ request.getUserChatContextCode() + ", last frame present:" + (last != null));
			}
			final GeboChatResponse answered = responseOf(last, response);
			if (run.isTransientConversation()) {
				// Nothing of a transient conversation is kept: the request is released and
				// the conversation dropped, with whatever it stored.
				chatSessionLifecycleService.releaseRequest(request);
				started = false;
				dropTransient(request);
			} else {
				// The run ends as a chat's request ends (see ChatRequestEnding): the answer is
				// saved as an interaction of the conversation, the session is maintained and
				// the request is released - so the next run goes on in the same conversation,
				// with this one behind it, instead of being refused as a concurrent request.
				ended(request, response, answered, chatModel);
				started = false;
			}
			return new ChatEnvironmentRun(frames, answered, service, request.getUserChatContextCode());
		} catch (AgentException ae) {
			releaseIfStarted(started, request);
			disposeQuietly(service);
			dropTransientQuietly(run, request);
			throw new GChatEnvironmentRunException(ae.getMessage(), ae, request.getUserChatContextCode());
		} catch (GeboChatSessionLifecycleException | GeboPersistenceException | NetworkOfAgentsException
				| LLMConfigException | IOException | RuntimeException e) {
			releaseIfStarted(started, request);
			disposeQuietly(service);
			dropTransientQuietly(run, request);
			LOGGER.error("The run of network '" + networkCode + "' in a chat environment failed", e);
			throw new GChatEnvironmentRunException("The run of network '" + networkCode + "' failed: " + e.getMessage(),
					e, request.getUserChatContextCode());
		}
	}

	/** How the conversation of the run is opened, or shared when it exists already. */
	private static GChatSessionOpeningOptions openingOptionsOf(GChatEnvironmentRunRequest run) {
		return GChatSessionOpeningOptions.builder().transientRun(run.isTransientConversation())
				.runOrigin(run.getOrigin()).sharedWithUsers(run.getSharedWithUsers())
				.sharedWithGroups(run.getSharedWithGroups()).build();
	}

	/**
	 * The request understood as the chat pipelines understand one. What cannot be
	 * understood is run as it came: the understanding helps the answer, it is not a
	 * condition of it.
	 */
	private void understand(ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, INotificationSink sink) {
		if (requestUnderstanding == null) {
			return;
		}
		try {
			requestUnderstanding.understand(runtimeData, chatModel, serviceModel, sink);
		} catch (ChatPipelineException | RuntimeException notUnderstood) {
			LOGGER.warn("The request of this run could not be understood as a chat understands one; it runs as it came",
					notUnderstood);
		}
	}

	/**
	 * The knowledge bases the run works on, as its scope says: the conversation's
	 * profile's, failing that every one the identity of the run can see, or the
	 * ones the caller stated.
	 */
	private List<String> knowledgeBasesOf(GeboChatRequest request, GKnowledgeBaseScope scope,
			ChatRunAttachments attachments) {
		final GKnowledgeBaseScope effective = scope != null ? scope : GKnowledgeBaseScope.PROFILE_OR_ALL_VISIBLE;
		if (effective == GKnowledgeBaseScope.STATED) {
			final List<String> stated = attachments != null && !attachments.getKnowledgeBases().isEmpty()
					? attachments.getKnowledgeBases()
					: request.getChoosedKnowledgeBases();
			return stated != null ? stated.stream().filter(code -> code != null && !code.isBlank()).distinct().toList()
					: List.of();
		}
		List<String> profiles = List.of();
		try {
			final List<GKnowledgeBase> ofTheProfile = chatSessionLifecycleService
					.getSessionAvailableKnowledgeBases(request);
			profiles = ofTheProfile != null ? ofTheProfile.stream().map(GKnowledgeBase::getCode)
					.filter(code -> code != null).distinct().toList() : List.of();
		} catch (GeboChatSessionLifecycleException | RuntimeException e) {
			LOGGER.error("Cannot read the knowledge bases of conversation " + request.getUserChatContextCode(), e);
		}
		if (!profiles.isEmpty() || effective == GKnowledgeBaseScope.PROFILE) {
			return profiles;
		}
		// no profile: what the identity of the run can see, as a call from outside a chat
		final UserKnowledgeBasesExecutionEnvironment visible = runtimeBinder
				.getImplementationOf(UserKnowledgeBasesExecutionEnvironment.class);
		final List<String> all = visible != null ? visible.knowledgeBaseCodes() : List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Conversation:" + request.getUserChatContextCode()
					+ " has no chat profile: the run works on the " + all.size() + " knowledge base(s) its identity sees");
		}
		return all;
	}

	/** Drops the transient conversation of a run that is over. */
	private void dropTransient(GeboChatRequest request) {
		if (request.getUserChatContextCode() == null) {
			return;
		}
		try {
			chatSessionLifecycleService.removeChatSession(request.getUserChatContextCode());
			LOGGER.debug("Transient conversation:{} of request:{} dropped", request.getUserChatContextCode(),
					request.getId());
		} catch (GeboChatSessionLifecycleException | RuntimeException notDropped) {
			// the sweeper takes it later
			LOGGER.warn("The transient conversation " + request.getUserChatContextCode()
					+ " could not be dropped now; it is swept later", notDropped);
		}
	}

	private void dropTransientQuietly(GChatEnvironmentRunRequest run, GeboChatRequest request) {
		if (run.isTransientConversation()) {
			dropTransient(request);
		}
	}

	/**
	 * Ends the request of a run that answered: the answer is saved on the request's
	 * own response - whose called functions the recorder filled - the way the chat
	 * pipeline merges a network's answer, the interaction is saved in the
	 * conversation, the session maintenance of a completed request runs, and the
	 * request is released whatever happened before.
	 */
	private void ended(GeboChatRequest request, GeboChatResponse response, GeboChatResponse answered,
			IGConfigurableChatModel chatModel) {
		try {
			if (answered != null && answered != response) {
				response.setQueryResponse(answered.getQueryResponse());
				response.setDocumentsRef(answered.getDocumentsRef());
				response.setListedDocumentNames(answered.getListedDocumentNames());
				response.setAdditionalContents(answered.getAdditionalContents());
				ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.mergeBackendMessages(answered, response);
			}
			chatSessionLifecycleService.endRequest(request, response);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Request:" + request.getId() + " of conversation:" + request.getUserChatContextCode()
						+ " ended, its interaction saved: answer length:"
						+ (response.getQueryResponse() != null ? response.getQueryResponse().length() : 0)
						+ " called functions:"
						+ (response.getCalledFunctions() != null ? response.getCalledFunctions().size() : 0));
			}
		} catch (GeboChatSessionLifecycleException | RuntimeException notEnded) {
			LOGGER.error("The interaction of request " + request.getId() + " in conversation "
					+ request.getUserChatContextCode() + " could not be saved", notEnded);
		}
		try {
			chatSessionLifecycleService.chatRequestCompleted(request, chatModel);
		} catch (GeboChatSessionLifecycleException | LLMConfigException | IOException
				| RuntimeException notCompleted) {
			LOGGER.error("Completing request " + request.getId() + " of conversation "
					+ request.getUserChatContextCode() + " failed", notCompleted);
		} finally {
			chatSessionLifecycleService.releaseRequest(request);
		}
	}

	/** A run that failed after its request was started leaves the conversation free. */
	private void releaseIfStarted(boolean started, GeboChatRequest request) {
		if (started) {
			LOGGER.debug("Releasing request:" + request.getId() + " of conversation:"
					+ request.getUserChatContextCode() + " after a failed run");
			chatSessionLifecycleService.releaseRequest(request);
		}
	}

	/**
	 * Attaches what the caller sent: the knowledge bases and documents it stated,
	 * and the files it sent, stored in the conversation's own area and carried by
	 * the request, which is how a chat hands over an upload: the request's start
	 * takes them in with everything else.
	 */
	private void attach(GeboChatRequest request, ChatRunAttachments attachments) {
		if (attachments == null || attachments.isEmpty()) {
			return;
		}
		if (!attachments.getKnowledgeBases().isEmpty()) {
			request.setChoosedKnowledgeBases(List.copyOf(attachments.getKnowledgeBases()));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("The caller asked the run to work on " + attachments.getKnowledgeBases().size()
						+ " stated knowledge base(s)");
			}
		}
		if (!attachments.getDocuments().isEmpty()) {
			final List<String> forced = new ArrayList<>(
					request.getForcedRequestDocuments() != null ? request.getForcedRequestDocuments() : List.of());
			forced.addAll(attachments.getDocuments());
			request.setForcedRequestDocuments(forced);
		}
		if (attachments.getFiles().isEmpty()) {
			return;
		}
		final IGUserUploadContentHandler uploads = uploadContentHandler != null ? uploadContentHandler.getIfAvailable()
				: null;
		if (uploads == null) {
			LOGGER.warn("The caller sent " + attachments.getFiles().size()
					+ " file(s) but this installation cannot take uploads: they are left out of the run");
			return;
		}
		final OperationStatus<List<UserUploadedContent>> stored = uploads
				.chatSessionUpload(request.getUserChatContextCode(), attachments.getFiles());
		final List<UserUploadedContent> contents = stored != null ? stored.getResult() : null;
		if (contents == null || contents.isEmpty()) {
			LOGGER.warn("The file(s) the caller sent could not be stored: they are left out of the run");
			return;
		}
		final List<UserUploadedContent> carried = new ArrayList<>(
				request.getUserUploadedContents() != null ? request.getUserUploadedContents() : List.of());
		carried.addAll(contents);
		request.setUserUploadedContents(carried);
		if (LOGGER.isDebugEnabled()) {
			for (UserUploadedContent content : contents) {
				LOGGER.debug("The file " + content.getFileName() + " the caller sent is part of the run");
			}
		}
	}

	private GeboChatResponse responseOf(GeboChatMessageEnvelope last, GeboChatResponse filledIn) {
		if (last != null && last.getContent() instanceof GeboChatResponse answered) {
			return answered;
		}
		return filledIn;
	}

	private void disposeQuietly(IGReactiveChatAgentsNetworkService service) {
		if (service == null) {
			return;
		}
		try {
			service.dispose();
		} catch (RuntimeException disposing) {
			LOGGER.warn("Disposing the network service of a failed run failed", disposing);
		}
	}
}
