package ai.gebo.llms.chat.pipelines.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.model.WaitingConditionDropReason;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSuspendedException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IChatPipelineService;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@AllArgsConstructor
public class ChatPipelineServiceImpl implements IChatPipelineService {
	private static Logger LOGGER = LoggerFactory.getLogger(ChatPipelineServiceImpl.class);
	private static final ConcurrentHashMap<String, Sinks.Empty<Void>> activeStreams = new ConcurrentHashMap<>();
	protected final IChatPipelinesExecutor executor;
	protected final IGChatModelRuntimeConfigurationDao chatModelsDao;
	protected final IGPersistentObjectManager persistentObjectManager;
	protected final IGSecurityService securityService;
	protected final IGRuntimeChatProfileChatModelDao chatProfileDao;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;
	protected final IPipelineUserMenuProviderServiceRepositoryPattern pipelineUserMenuProviderServiceRepoPattern;
	/** Where the suspended runs of the networks of agents are kept: a conversation holds one open block at most. */
	protected final GNetworkOfAgentsSuspensionRepository suspensions;
	protected final NetworkOfAgentsSuspensionTransport suspensionTransport;
	/**
	 * Where present, the answers to a suspended network are kept one condition at a
	 * time, next to those other sources give (see {@link IConditionReactionRecorder}).
	 */
	protected final ObjectProvider<IConditionReactionRecorder> reactionRecorders;

	/** A respawn request whose answers were recorded one condition at a time. */
	protected record OneByOne(IConditionReactionRecorder recorder, GNetworkOfAgentsSuspension block,
			RecordedConditionReaction recorded) {
		/** The respawn the request claimed ended: the recorder takes it further. */
		void concluded(GeboChatRequest request) {
			try {
				recorder.concluded(block, request.getId());
			} catch (RuntimeException e) {
				LOGGER.warn("Cannot conclude the respawn of suspension:" + block.getCode() + " by request:"
						+ request.getId() + " (the sweeper takes it further): " + e.getMessage(), e);
			}
		}
	}

	@Override
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		GeboChatResponse response = null;
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			guardOpenSuspension(request);
			IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
			Flux<GeboChatMessageEnvelope> flux = executor.streamingExecute(request, environment, chatModel,
					serviceModel, pipelineCode);
			Vector<GeboChatMessageEnvelope> responsesList = new Vector<GeboChatMessageEnvelope>();
			Flux<GeboChatMessageEnvelope> lastMessageFlux = flux.filter(x -> {
				responsesList.add(x);
				return x.isLastMessage() && x.getContent() instanceof GeboChatResponse;
			});
			GeboChatMessageEnvelope last = lastMessageFlux.blockLast();
			if (last != null && last.getContent() instanceof GeboChatResponse _response) {
				response = _response;
			} else {
				LOGGER.warn("No GeboChatResponse as output");
			}
			return response;
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying chat pipeline";
			throw new ChatPipelineException(msg, e);
		}
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			guardOpenSuspension(request);
			IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
			IGConfigurableChatModel serviceModel = chatModelsDao
					.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);

			String processId = request.getUserChatContextCode();
			Sinks.Empty<Void> stopSink = Sinks.empty();

			if (processId != null) {
				activeStreams.put(processId, stopSink);
			}

			Flux<GeboChatMessageEnvelope> flux = executor.streamingExecute(request, environment, chatModel,
					serviceModel, pipelineCode);

			if (processId != null) {
				flux = flux.takeUntilOther(stopSink.asMono()).doFinally(signalType -> activeStreams.remove(processId));
			}
			return flux;
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying chat pipeline (streaming)";
			throw new ChatPipelineException(msg, e);
		}
	}

	/**
	 * A normal interaction in a conversation holding an open block of a suspended
	 * network of agents: refused when a condition of the block is mandatory, since
	 * only the respawn with the reaction is possible there; otherwise the block is
	 * dropped, the user having chosen to go on without answering, and the
	 * interaction takes place.
	 */
	protected void guardOpenSuspension(GeboChatRequest request) throws GeboChatSuspendedException {
		if (suspensions == null || request == null || request.getUserChatContextCode() == null) {
			return;
		}
		final Optional<GNetworkOfAgentsSuspension> open = suspensions
				.findOpenByConversationCode(request.getUserChatContextCode());
		if (open.isEmpty()) {
			return;
		}
		final GNetworkOfAgentsSuspension block = open.get();
		if (block.isMandatory()) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Conversation:" + request.getUserChatContextCode() + " waits for suspension:" + block.getCode()
						+ ", a normal interaction is refused");
			}
			throw new GeboChatSuspendedException(request.getUserChatContextCode(),
					suspensionTransport != null ? suspensionTransport.stateOf(block)
							: NetworkOfAgentsSuspensionTransport.plain().stateOf(block));
		}
		block.setDroppedAt(new Date());
		block.setDroppedReason(WaitingConditionDropReason.NEW_INTERACTION);
		block.setDroppedBy(currentUsername());
		block.setDateModified(block.getDroppedAt());
		suspensions.save(block);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Conversation:" + request.getUserChatContextCode() + " goes on without answering suspension:"
					+ block.getCode() + ", dropped");
		}
	}

	@Override
	public Optional<NetworkOfAgentsSuspendedState> pendingNetworkOfAgentsSuspension(String userChatContextCode) {
		if (suspensions == null || userChatContextCode == null) {
			return Optional.empty();
		}
		final NetworkOfAgentsSuspensionTransport transport = suspensionTransport != null ? suspensionTransport
				: NetworkOfAgentsSuspensionTransport.plain();
		return suspensions.findOpenByConversationCode(userChatContextCode).map(transport::stateOf);
	}

	/**
	 * A respawn request answering a block one condition at a time: its answers are
	 * recorded next to those other sources gave when the block already holds some,
	 * or when the reaction leaves a mandatory condition unanswered. Any other
	 * request, and every one where no recorder is present, respawns as it always
	 * did; null then.
	 */
	protected OneByOne recordOneByOne(GeboChatRequest request) {
		final IConditionReactionRecorder recorder = reactionRecorders != null ? reactionRecorders.getIfAvailable()
				: null;
		if (recorder == null || suspensions == null || request == null || request.getUserChatContextCode() == null
				|| IConditionReactionRecorder.RESPAWN_ORIGIN.equals(request.getRunOrigin())) {
			return null;
		}
		final GNetworkOfAgentsSuspension block = suspensions.findOpenByConversationCode(request.getUserChatContextCode())
				.orElse(null);
		final NetworkOfAgentsSuspendedStateReaction reaction = request.getNetworkOfAgentsSuspendedStateReaction();
		if (block == null || block.getNetwork() == null || (reaction != null && reaction.getNetworkOfAgentsBlockId() != null
				&& !reaction.getNetworkOfAgentsBlockId().equals(block.getCode()))) {
			// the respawner refuses it, saying why
			return null;
		}
		if (!recorder.holdsAnswersFor(block) && !leavesMandatoryUnanswered(block, reaction)
				&& !asksToGoOnWithout(reaction)) {
			return null;
		}
		final RecordedConditionReaction recorded = recorder.record(block, reaction, currentUsername(), request.getId());
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Conversation:" + request.getUserChatContextCode() + " answered suspension:" + block.getCode()
					+ " one condition at a time: " + recorded.next()
					+ (recorded.waitingFor().isEmpty() ? "" : ", still waiting for " + recorded.waitingFor().size()));
		}
		if (recorded.resumes()) {
			request.setNetworkOfAgentsSuspendedStateReaction(recorded.reaction());
		}
		return new OneByOne(recorder, block, recorded);
	}

	private static boolean leavesMandatoryUnanswered(GNetworkOfAgentsSuspension block,
			NetworkOfAgentsSuspendedStateReaction reaction) {
		for (WaitingCondition condition : block.conditions()) {
			if (condition.isMandatory() && !answers(reaction, condition.getSuspendedConditionId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Whether an answer asks to go on without its condition: only the recorder reads
	 * it, as the conversation's own user; the respawner would refuse it.
	 */
	private static boolean asksToGoOnWithout(NetworkOfAgentsSuspendedStateReaction reaction) {
		if (reaction == null || reaction.getReactions() == null) {
			return false;
		}
		for (AgentSuspendedWaitingConditionReactionDto answer : reaction.getReactions()) {
			if (answer != null && answer.getAttributes() != null && GAbstractConditionTransportTranslator
					.isTrue(answer.getAttributes().get(GAbstractConditionTransportTranslator.GO_ON_WITHOUT))) {
				return true;
			}
		}
		return false;
	}

	private static boolean answers(NetworkOfAgentsSuspendedStateReaction reaction, String conditionId) {
		if (reaction == null || reaction.getReactions() == null || conditionId == null) {
			return false;
		}
		for (AgentSuspendedWaitingConditionReactionDto answer : reaction.getReactions()) {
			if (answer != null && conditionId.equals(answer.getConditionId())) {
				return true;
			}
		}
		return false;
	}

	private String currentUsername() {
		try {
			return securityService != null && securityService.getCurrentUser() != null
					? securityService.getCurrentUser().getUsername()
					: null;
		} catch (RuntimeException noUser) {
			return null;
		}
	}

	/**
	 * The answer to a request whose answers were recorded and that respawns
	 * nothing: what was taken, what was not and why, and what the block still waits
	 * for, with the block as it stands; kept in the conversation as any answer.
	 */
	protected GeboChatResponse replyRecorded(GeboChatRequest request, OneByOne oneByOne)
			throws GeboChatSessionLifecycleException, GeboPersistenceException, IOException, LLMConfigException {
		final GeboChatResponse response = chatSessionLifecycleService.createEmptyResponse(request);
		final NetworkOfAgentsSuspensionTransport transport = suspensionTransport != null ? suspensionTransport
				: NetworkOfAgentsSuspensionTransport.plain();
		final NetworkOfAgentsSuspendedState state = suspensions
				.findOpenByConversationCode(request.getUserChatContextCode()).map(transport::stateOf).orElse(null);
		response.setNetworkOfAgentsSuspendedState(state);
		response.setQueryResponse(ConditionReactionReplies.replyOf(oneByOne.block(), oneByOne.recorded()));
		final IGConfigurableChatModel chatModel = chatSessionLifecycleService.getSessionChatModel(request);
		try {
			chatSessionLifecycleService.startRequest(request, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET);
			chatSessionLifecycleService.endRequest(request, response);
		} finally {
			chatSessionLifecycleService.releaseRequest(request);
		}
		return response;
	}

	private IGConfigurableChatModel getContextSpecifiedModelOrDefault(GUserChatSession context) {
		String modelCode = context.getChatModelCode();
		IGConfigurableChatModel model = chatModelsDao.findByCode(modelCode);
		if (model == null)
			model = chatModelsDao.defaultHandler();
		return model;
	}

	@Override
	public void stopPipeline(String userChatContextCode) throws ChatPipelineException {
		if (userChatContextCode == null)
			return;
		Sinks.Empty<Void> sink = activeStreams.remove(userChatContextCode);
		if (sink != null) {
			sink.tryEmitEmpty();
			LOGGER.info("Successfully emitted stop signal for pipeline/process {}", userChatContextCode);
		} else {
			LOGGER.warn("No active stream found to stop for context code: {}", userChatContextCode);
		}
	}

	@Override
	public List<PipelineChatMenu> getPersonalPipelinesChatMenu(String pipelineCode, String chatProfileCode)
			throws ChatPipelineException {
		IPipelineUserMenuProviderService handler = pipelineUserMenuProviderServiceRepoPattern.findByCode(pipelineCode);
		if (handler == null)
			throw new ChatPipelineException(
					"Pipeline => " + pipelineCode + " does not have an PipelineUserMenuProviderService");
		return handler.getUIMenu(chatProfileCode);
	}

	@Override
	public GeboChatResponse respawnAgenticChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException {
		GeboChatResponse response = null;
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			mergeTypedAnswers(request);
			final OneByOne oneByOne = recordOneByOne(request);
			if (oneByOne != null && !oneByOne.recorded().resumes()) {
				return replyRecorded(request, oneByOne);
			}
			try {
				IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
				IGConfigurableChatModel serviceModel = chatModelsDao
						.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);
				Flux<GeboChatMessageEnvelope> flux = executor.streamingRespawnAgenticChat(request, environment,
						chatModel, serviceModel, pipelineCode);
				Vector<GeboChatMessageEnvelope> responsesList = new Vector<GeboChatMessageEnvelope>();
				Flux<GeboChatMessageEnvelope> lastMessageFlux = flux.filter(x -> {
					responsesList.add(x);
					return x.isLastMessage() && x.getContent() instanceof GeboChatResponse;
				});
				GeboChatMessageEnvelope last = lastMessageFlux.blockLast();
				if (last != null && last.getContent() instanceof GeboChatResponse _response) {
					response = _response;
				} else {
					LOGGER.warn("No GeboChatResponse as output");
				}
				return response;
			} finally {
				if (oneByOne != null) {
					oneByOne.concluded(request);
				}
			}
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying  respawn  chat pipeline";
			throw new ChatPipelineException(msg, e);
		}
	}

	@Override
	public Flux<GeboChatMessageEnvelope> streamingRespawnAgenticChat(String pipelineCode,
			@NotNull GeboChatRequest request, LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException {
		try {
			this.chatSessionLifecycleService.ensureChatSessionExists(request);
			mergeTypedAnswers(request);
			final OneByOne oneByOne = recordOneByOne(request);
			if (oneByOne != null && !oneByOne.recorded().resumes()) {
				return Flux.just(new GeboChatMessageEnvelope<GeboChatResponse>(replyRecorded(request, oneByOne), true));
			}
			Flux<GeboChatMessageEnvelope> flux;
			try {
				IGConfigurableChatModel chatModel = this.chatSessionLifecycleService.getSessionChatModel(request);
				IGConfigurableChatModel serviceModel = chatModelsDao
						.findByUsesOrGetDefault(ChatModelsUses.INTERNAL_SERVICES);

				String processId = request.getUserChatContextCode();
				Sinks.Empty<Void> stopSink = Sinks.empty();

				if (processId != null) {
					activeStreams.put(processId, stopSink);
				}

				flux = executor.streamingRespawnAgenticChat(request, environment, chatModel, serviceModel,
						pipelineCode);

				if (processId != null) {
					flux = flux.takeUntilOther(stopSink.asMono())
							.doFinally(signalType -> activeStreams.remove(processId));
				}
			} catch (Exception notStarted) {
				// rethrown as it was: only what the body throws
				if (oneByOne != null) {
					oneByOne.concluded(request);
				}
				throw notStarted;
			}
			if (oneByOne != null) {
				// the block says how the respawn went, once its stream is over
				flux = flux.doFinally(signalType -> oneByOne.concluded(request));
			}
			return flux;
		} catch (GeboPersistenceException | IOException | LLMConfigException e) {
			String msg = "Exception applying respawn chat pipeline (streaming)";
			throw new ChatPipelineException(msg, e);
		}
	}

	/**
	 * Puts the answers given typed in their attributes, before anything reads them:
	 * the recorder, the respawner and the texts read the attributes.
	 */
	static void mergeTypedAnswers(GeboChatRequest request) {
		final NetworkOfAgentsSuspendedStateReaction reaction = request != null
				? request.getNetworkOfAgentsSuspendedStateReaction()
				: null;
		if (reaction == null || reaction.getReactions() == null) {
			return;
		}
		for (AgentSuspendedWaitingConditionReactionDto answer : reaction.getReactions()) {
			if (answer != null) {
				answer.mergeTypedAttributes();
			}
		}
	}
}
