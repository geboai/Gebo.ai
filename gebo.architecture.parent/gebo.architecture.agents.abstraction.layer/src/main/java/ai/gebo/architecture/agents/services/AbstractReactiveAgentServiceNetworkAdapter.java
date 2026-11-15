package ai.gebo.architecture.agents.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
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
		// What a tool of the agent's calls declares the run must wait for: read once its
		// stream ended. A respawned agent is told first what it waited for.
		final AgentSuspensionTurn turn = AgentSuspensionTurn.of("Reactive agent " + getId(), toolCalling,
				suspensionHandler, contextAgentPersona);
		final GeboToolCalling callToolCalling = turn.toolCalling();
		final RequestType request = turn.toldInput(msg.getPayload());
		Flux<IGPartialOperation<ResponseType>> flux = service.execute(turn.toldContext(chatRequestContext, request),
				config, request, network,
				contextAgentPersona, notificationSink, session, mySessionContext, runAs, callToolCalling);
		// Once a tool of the call declared what the run waits for, the agent says nothing:
		// what the call answers from then on is the waiting tool's own result (the
		// declaration ends the call with it), written for the model, not for the user,
		// who is asked the question by the suspension.
		final int[] keptFromTheUser = { 0 };
		Flux<IGPartialOperation<ResponseType>> duplicatedFlux = flux.map(x -> {
			if (turn.isWaiting()) {
				keptFromTheUser[0]++;
			} else {
				sink.emitNext(x, null);
			}
			return x;
		});
		List<IGPartialOperation<ResponseType>> buffered = duplicatedFlux.collectList().block();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Reactive agent adapter id:" + getId() + " buffered "
					+ (buffered != null ? buffered.size() : 0) + " partial operation(s)");
		}
		// what the agent streamed before its tool declared the wait reached the user
		if (turn.suspendIfWaiting(network, config, mySessionContext)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Reactive agent adapter id:" + getId() + " waits: " + keptFromTheUser[0]
						+ " partial operation(s) streamed after the wait was declared kept from the user");
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
