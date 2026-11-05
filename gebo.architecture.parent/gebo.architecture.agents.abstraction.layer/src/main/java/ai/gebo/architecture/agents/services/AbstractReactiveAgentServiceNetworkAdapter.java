package ai.gebo.architecture.agents.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

@AllArgsConstructor
public abstract class AbstractReactiveAgentServiceNetworkAdapter<RequestType, ResponseType>
		implements IGNetworkAgentService<RequestType, ResponseType> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReactiveAgentServiceNetworkAdapter.class);

	/** The adapted agent answers with a RESPONSE, shared in the session context. */
	@Override
	public MessageSemantic getProducedMessageSemantic() {
		return MessageSemantic.RESPONSE;
	}
	private final IGReactiveAgentService<RequestType, ResponseType> service;
	private final Class<RequestType> inputType;
	private final Class<ResponseType> outputType;

	private final Sinks.Many<IGPartialOperation<ResponseType>> sink;

	@Override
	public String getId() {

		return service.getId();
	}

	@Override
	public String getDescription() {

		return service.getDescription();
	}

	@Override
	public List<GAgentConfig> getAccessibleConfigurations() {

		return service.getAccessibleConfigurations();
	}

	@Override
	public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {

		return service.getAgentCapabilities(agentConfig);
	}

	@Override
	public Class<RequestType> getInputType() {

		return inputType;
	}

	@Override
	public Class<ResponseType> getOutputType() {
		return outputType;
	}

	@Override
	public List<AgentsExchangeMessage<ResponseType>> onMessage(IChatRequestContext chatRequestContext,
			GAgentConfig config, AgentsExchangeMessage<RequestType> msg, int actualContributionNr,
			GAgentsNetwork network, AgentNetworkParticipant contextAgentPersona, INotificationSink notificationSink,
			AgentsCollaborationSessionContext session,
			AgentPrivateSessionContext<RequestType, ResponseType> mySessionContext, ReactiveIdentityUtil runAs,
			IGAgentsNetworkRuntimeDao agentsDao, GeboToolCalling toolCalling, IGNetworkOfAgentsSuspensionHandler suspensionHandler) throws LLMConfigException, AgentException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin onMessage(...) reactive agent network adapter id:" + getId() + " fromAgent:"
					+ msg.getFromAgent());
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<ADAPTED_REACTIVE_AGENT_INPUT agent=" + getId() + ">");
			LOGGER.trace(String.valueOf(msg.getPayload()));
			LOGGER.trace("</ADAPTED_REACTIVE_AGENT_INPUT>");
		}
		// What a tool of the agent's calls declares the run must wait for (see
		// IGWaitExternalCondition): loaded for every call of the adapted agent, filled
		// only by a mounted waiting tool, read once its stream ended. The adapter's own
		// container, merged into the run's: the run's caller, limits and tools stay.
		final GWaitExternalConditionSink wait = new GWaitExternalConditionSink();
		final GeboToolCalling callToolCalling = GeboToolCalling.merged(
				GeboToolCalling.builder().provide(IGWaitExternalCondition.class, wait).build(), toolCalling);
		// A respawned agent is re-executed on the message it was given, told first what
		// it waited for and what came of it.
		final List<AgentSuspendedWaitingCondition> resolved = suspensionHandler != null
				? suspensionHandler.resolvedConditionsOf(contextAgentPersona)
				: List.of();
		RequestType request = msg.getPayload();
		if (!resolved.isEmpty()) {
			if (request instanceof String text) {
				@SuppressWarnings("unchecked")
				final RequestType told = (RequestType) ResolvedConditionText.prepend(resolved, text);
				request = told;
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Reactive agent adapter id:" + getId() + " respawned with the answers to " + resolved.size()
							+ " condition(s)");
				}
			} else {
				LOGGER.warn("Reactive agent adapter id:" + getId() + " respawned with the answers to " + resolved.size()
						+ " condition(s) but its input is not text ("
						+ (request != null ? request.getClass().getName() : null) + "): the model is not told the answer");
			}
		}
		Flux<IGPartialOperation<ResponseType>> flux = service.execute(chatRequestContext, config, request, network,
				contextAgentPersona, notificationSink, session, mySessionContext, runAs, callToolCalling);
		Flux<IGPartialOperation<ResponseType>> duplicatedFlux = flux.map(x -> {
			sink.emitNext(x, null);
			return x;
		});
		List<IGPartialOperation<ResponseType>> buffered = duplicatedFlux.collectList().block();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent adapter id:" + getId() + " buffered "
					+ (buffered != null ? buffered.size() : 0) + " partial operation(s)");
		}
		if (wait.isWaiting()) {
			// A tool declared the run must wait for something from outside it: the adapter
			// asks the network to suspend the run for each condition and says nothing; what
			// the agent streamed so far reached the user. Re-executed on this message when
			// the conditions are resolved.
			if (suspensionHandler == null) {
				throw new AgentException("Reactive agent " + getId() + " must wait for " + wait.waited().size()
						+ " condition(s) but the run has no suspension handler");
			}
			for (AgentSuspendedWaitingCondition<?, ?> condition : wait.waited()) {
				suspensionHandler.enterSuspensionState(network, config, contextAgentPersona, mySessionContext, condition);
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Reactive agent adapter id:" + getId() + " asked to suspend the run for "
						+ wait.waited().size() + " condition(s), the call stopped by tool:"
						+ callToolCalling.isStoppedByTool());
			}
			return List.of();
		}
		ResponseType response = extractResponse(buffered);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent adapter id:" + getId() + " collapsed the buffered partials into a response:"
					+ (response != null));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("<ADAPTED_REACTIVE_AGENT_OUTPUT agent=" + getId() + ">");
			LOGGER.trace(String.valueOf(response));
			LOGGER.trace("</ADAPTED_REACTIVE_AGENT_OUTPUT>");
		}
		AgentsExchangeMessage<ResponseType> outMessage = AgentsExchangeMessage.of(session,
				contextAgentPersona.getNetworkAgentName(), msg.getFromAgent(), response, getProducedMessageSemantic());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End onMessage(...) reactive agent network adapter id:" + getId());
		}
		return List.of(outMessage);
	}

	protected abstract ResponseType extractResponse(List<IGPartialOperation<ResponseType>> buffered);

}
