package ai.gebo.llms.agent.chat.service.impl;

import ai.gebo.architecture.agents.model.PipelineType;
import ai.gebo.architecture.agents.services.IGAgenticChatDefaultNetworkOfAgentsService;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import java.util.concurrent.atomic.AtomicReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.NetworkOfAgentsException;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.model.GUserMessage;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.StepEnvironmentParameter;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.IStreamingOutputChatPipelineService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ReactiveChatAgentsNetworkStreamingOutputChatPipelineService
		implements IStreamingOutputChatPipelineService {
	public static final String SERVICE_ID = "ReactiveChatAgentsNetworkStreamingOutputChatPipelineService";
	private static final String EXCEPTION_CREATING_NETWORK_OF_AGENTS = "Exception creating network of agents";
	private static final String EXCEPTION_RUNNING_NETWORK_OF_AGENTS = "Exception running network of agents";
	protected final IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory;
	protected final IDynamicAgentsNetworkDataSource agentsNetworkDataSource;
	protected final IGChatSessionLifeCycleService lifeCycleService;
	/**
	 * Pipeline step id. The chat pipeline step repository indexes steps globally by
	 * {@link #getStepId()}, so a second network-streaming step (e.g. the office
	 * assistant network) must carry a distinct id; this is constructor-injectable
	 * for exactly that reuse, defaulting to {@link #SERVICE_ID} for the standard
	 * default-network step.
	 */
	private final String stepId;
	/**
	 * The pipeline type the chats of this step belong to: when set, the network is
	 * resolved at each request (chat profile, system default, configuration) and the
	 * data source is only the fallback; when null, the data source's first network
	 * is used.
	 */
	protected final PipelineType pipelineType;
	protected final IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.class);

	public ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService) {
		this(factory, agentsNetworkDataSource, lifeCycleService, SERVICE_ID);
	}

	public ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService,
			String stepId) {
		this(factory, agentsNetworkDataSource, lifeCycleService, stepId, null, null);
	}

	/**
	 * A step handing the chats of the pipeline type to the network resolved at each
	 * request, the data source's first network being the fallback.
	 */
	public ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(
			IGAgentsNetworkServiceFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory,
			IDynamicAgentsNetworkDataSource agentsNetworkDataSource, IGChatSessionLifeCycleService lifeCycleService,
			String stepId, PipelineType pipelineType, IGAgenticChatDefaultNetworkOfAgentsService defaultNetworksService) {
		this.factory = factory;
		this.agentsNetworkDataSource = agentsNetworkDataSource;
		this.lifeCycleService = lifeCycleService;
		this.stepId = stepId;
		this.pipelineType = pipelineType;
		this.defaultNetworksService = defaultNetworksService;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Registered reactive chat agents network pipeline step id:" + stepId + " over network factory:"
					+ (factory != null ? factory.getId() : null));
		}
	}

	@Override
	public StepExecutorType getExecutorType() {

		return StepExecutorType.LLM;
	}

	@Override
	public String getStepId() {

		return stepId;
	}

	/**
	 * Adds the messages of a network answer (warnings and notices for the user) to the
	 * pipeline response, each once: the network answer is merged onto that response,
	 * which is what the user receives.
	 */
	static void mergeBackendMessages(GeboChatResponse networkResponse, GeboChatResponse pipelineResponse) {
		if (networkResponse == null || pipelineResponse == null || networkResponse == pipelineResponse
				|| networkResponse.getBackendMessages() == null || networkResponse.getBackendMessages().isEmpty()) {
			return;
		}
		if (pipelineResponse.getBackendMessages() == null) {
			pipelineResponse.setBackendMessages(new ArrayList<>());
		}
		for (GUserMessage message : networkResponse.getBackendMessages()) {
			if (message != null && !pipelineResponse.getBackendMessages().contains(message)) {
				pipelineResponse.getBackendMessages().add(message);
			}
		}
	}

	/**
	 * Builds the environment map seeded into the agents network session
	 * ({@code session.getEnvironment()}). The default network contributes the
	 * available knowledge-base codes and the user intent. Subclasses (e.g. the
	 * office assistant network) override this to enrich the shared environment with
	 * additional entries - such as the document fragments the user is editing -
	 * while keeping the standard entries by calling {@code super}.
	 */
	protected Map<String, Object> buildNetworkEnvironment(ChatPipelineExecutionRuntimeData runtimeData)
			throws LLMConfigException, GeboChatSessionLifecycleException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin buildNetworkEnvironment(...) pipeline step:" + getStepId());
		}
		final Map<String, Object> environment = new HashMap<String, Object>();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		List<GKnowledgeBase> knowledgeBases = lifeCycleService.getSessionAvailableKnowledgeBases(request);
		List<String> knowledgeBaseCodes = knowledgeBases.stream().map(x -> x.getCode()).toList();
		environment.put(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE, knowledgeBaseCodes);
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_INTENT,
				request.getUserIntent() != null ? request.getUserIntent() : DeliverableIntent.SUMMARY);
		environment.put(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED,
				Boolean.TRUE.equals(request.getSearchRequested()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End buildNetworkEnvironment(...) knowledgeBases:" + knowledgeBaseCodes.size() + " userIntent:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.USER_INTENT) + " searchRequested:"
					+ environment.get(StandardAgentsNetworkEnvironmentEntries.SEARCH_REQUESTED));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Knowledge base codes seeded into the network environment: " + knowledgeBaseCodes);
			LOGGER.trace("<USER_QUERY>");
			LOGGER.trace(String.valueOf(request.getQuery()));
			LOGGER.trace("</USER_QUERY>");
		}
		return environment;
	}

	/**
	 * The network the chat is handed to: the one resolved for the pipeline type and
	 * the chat profile when the step has a pipeline type, otherwise, or when nothing
	 * resolves, the data source's first one.
	 */
	protected GAgentsNetwork chooseNetwork(ChatPipelineExecutionRuntimeData runtimeData)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		if (pipelineType != null && defaultNetworksService != null) {
			GChatProfileConfiguration profile = lifeCycleService
					.getSessionChatProfile(runtimeData.getRequestResources().getCurrentRequest());
			String profileNetwork = profile != null ? profile.getDefaultChatNetworkOfAgents() : null;
			GAgentsNetwork resolved = defaultNetworksService.resolveChatNetwork(pipelineType, profileNetwork);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pipeline step:" + getStepId() + " type:" + pipelineType + " chat profile:"
						+ (profile != null ? profile.getCode() : null) + " profile network:" + profileNetwork
						+ " resolved network:" + (resolved != null ? resolved.getCode() : null));
			}
			if (resolved != null) {
				return resolved;
			}
		}
		List<GAgentsNetwork> ds = this.agentsNetworkDataSource != null ? this.agentsNetworkDataSource.getConfigurations()
				: List.of();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("The agents network data source offers " + ds.size()
					+ " network(s); the first one is used as the agentic chat network");
		}
		if (ds.isEmpty())
			throw new ChatPipelineException("No agentic chat network set");
		return ds.get(0);
	}

	@Override
	public List<StepEnvironmentParameter> getRequiredParameters() {

		return List.of();
	}

	@Override
	public Flux<GeboChatMessageEnvelope> execute(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel)
			throws ChatPipelineException, GeboChatSessionLifecycleException, LLMConfigException, GeboChatException,
			IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin execute(...) reactive chat agents network streaming pipeline step:" + getStepId());
		}
		try {
			ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
			INotificationSink notificationSink = sinkUIEmitter;
			final GeboChatResponse responseReference = runtimeData.getChatResponse();
			final GAgentsNetwork network = chooseNetwork(runtimeData);
			final Map<String, Object> environment = buildNetworkEnvironment(runtimeData);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Creating runtime network of agents from config code:" + network.getCode());
			}
			IGReactiveChatAgentsNetworkService runtimeNetwork = factory.create(network, notificationSink,
					ChatPipelineExecutionRuntimeData.class, GeboChatMessageEnvelope.class, runAs);
			Flux<GeboChatMessageEnvelope> flux = runtimeNetwork.getFlux();
			Flux<GeboChatMessageEnvelope> trailingFlux = Flux.defer(() -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Emitting the trailing last-message envelope for pipeline step:" + getStepId());
				}
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(responseReference);
				envelope.setLastMessage(true);
				return Flux.just(envelope);

			});
			// The network runs on its own worker rather than inside the doOnSubscribe
			// callback. Reactor invokes that callback BEFORE handing the subscription to the
			// downstream subscriber, so running the network there finished the whole thing -
			// every agent, every LLM call - before anyone could request a single element. The
			// sink behind this flux is unicast().onBackpressureBuffer(), so everything the
			// agents produced accumulated there and was delivered in one burst at the end.
			//
			// Measured on a report of 2,264 chunks: the browser saw nothing for 55 seconds and
			// then the entire answer at once. That defeats both halves of the streaming design
			// - the writer emitting its text chunk by chunk, and the network replacing one
			// self contained cycle output with the next as the report is refined.
			final AtomicReference<Disposable> networkExecution = new AtomicReference<Disposable>();
			flux = flux.doOnSubscribe((subscription) -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Subscription received, launching executeNetwork(...) for network code:"
							+ network.getCode());
				}
				networkExecution.set(Schedulers.boundedElastic().schedule(() -> runAs.doAs(() -> {
					try {

						runtimeNetwork.executeNetwork(runtimeData.getRequestResources().createChatRequestContext(),
								runtimeData, environment);
					} catch (AgentException | LLMConfigException e) {
						LOGGER.error(EXCEPTION_RUNNING_NETWORK_OF_AGENTS, e);
					}
				})));
			}).map(x -> {
				if (x != null && x.getContent() instanceof GeboChatResponse response) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Merging a network chat response onto the pipeline response, responseLength:"
								+ (response.getQueryResponse() != null ? response.getQueryResponse().length() : 0)
								+ " calledFunctions:"
								+ (response.getCalledFunctions() != null ? response.getCalledFunctions().size() : 0)
								+ " documentRefs:"
								+ (response.getDocumentsRef() != null ? response.getDocumentsRef().size() : 0));
					}
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("<NETWORK_CHAT_RESPONSE>");
						LOGGER.trace(response.getQueryResponse());
						LOGGER.trace("</NETWORK_CHAT_RESPONSE>");
					}
					responseReference.setQueryResponse(response.getQueryResponse());
					// The called functions are not copied: the request's recorder already fills
					// them on this response with the calls of every agent of the network.
					responseReference.setDocumentsRef(response.getDocumentsRef());
					// Carry any additional content the writer produced (e.g. the office
					// assistant's document part) onto the emitted response. Null for the
					// default network, which never sets it.
					responseReference.setAdditionalContents(response.getAdditionalContents());
					// the messages the agents give the user with their answer (e.g. a warning)
					mergeBackendMessages(response, responseReference);
					return new GeboChatMessageEnvelope(responseReference);
				}
				return x;
			}).concatWith(trailingFlux).doOnCancel(() -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reactive chat agents network flux cancelled, disposing runtime network");
				}
				// The network now outlives the subscribe call, so a cancelled flux has to stop
				// it explicitly: without this the agents would keep calling models and paying
				// for answers nobody is listening to any more.
				disposeNetworkExecution(networkExecution);
				runtimeNetwork.dispose();
			}).doFinally(signalType -> {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reactive chat agents network flux terminated with signal:" + signalType
							+ ", disposing runtime network");
				}
				disposeNetworkExecution(networkExecution);
				runtimeNetwork.dispose();
			});
			;
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End execute(...) reactive chat agents network streaming pipeline step:" + getStepId()
						+ ", the output flux is ready to be subscribed");
			}
			return flux.subscribeOn(runAs.wrap(Schedulers.boundedElastic()));
		} catch (NetworkOfAgentsException e) {
			LOGGER.error(EXCEPTION_CREATING_NETWORK_OF_AGENTS, e);
			throw new ChatPipelineException(EXCEPTION_CREATING_NETWORK_OF_AGENTS, e);
		}

	}

	/**
	 * Stops the worker running the network, if it is still running.
	 * <p>
	 * Disposing a task that has already finished is a no-op, and the reference is null
	 * when the flux terminated before anything subscribed.
	 */
	private void disposeNetworkExecution(AtomicReference<Disposable> networkExecution) {
		Disposable execution = networkExecution.getAndSet(null);
		if (execution != null && !execution.isDisposed()) {
			execution.dispose();
		}
	}
}
