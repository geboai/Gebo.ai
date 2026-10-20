/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.services.IGAgentsNetworkToNetworkAgentAdapterService;
import ai.gebo.architecture.agents.services.IGGenericAgentService;
import ai.gebo.architecture.agents.services.IGNetworkAgentService;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern;
import ai.gebo.architecture.agents.services.IGRoutingNetworkAgentService;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lightweight, serialization-friendly view of an agent service used by the
 * visual network composer. It exposes the information a UI needs to lay out and
 * catenate agent services: the service id/description, its input/output types
 * (as fully qualified class names) and the flags that drive composition rules
 * (whether the service is a network agent, a routing/coordinator agent, a
 * {@link IGAgentsNetworkToNetworkAgentAdapterService network-of-agents adapter}
 * that can be dropped in place of a plain network agent, or a reactive service
 * answering the user directly, which takes part in a network only through an
 * adapter and only as an output node).
 * <p>
 * The {@link #kind} sums the flags up for the composer: what the service does
 * with the messages it receives, as the network validation
 * ({@code GAgentsNetworkCrudServiceImpl}) and the runtime treat it.
 */
@Data
@NoArgsConstructor
public class AgentServiceDescriptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentServiceDescriptor.class);

	/**
	 * What a service does with the messages of the network.
	 */
	public static enum AgentServiceKind {
		/**
		 * Sends each agent of its communication list a command typed on that agent's
		 * input: needs at least one agent to talk to, no type to match.
		 */
		ROUTER,
		/**
		 * Delivers its own output as the input of the agents of its communication list:
		 * needs at least one, and its output type must be their input type.
		 */
		FORWARDER,
		/**
		 * Answers the agent that sent the message and shares the answer in the session
		 * context: its communication list only tells the model whom it may address.
		 */
		RESPONDER,
		/**
		 * A responder that is a whole network of agents run as one agent.
		 */
		NETWORK_ADAPTER,
		/**
		 * Answers the user directly: takes part in a network only as the single output
		 * node of a reactive output network, through an adapter.
		 */
		WRITER
	}

	private String serviceId;
	private String description;
	private String inputType;
	private String outputType;
	private boolean networkAgent;
	private boolean routingAgent;
	private boolean networkAdapter;
	/** True for a service answering the user directly ({@link IGReactiveAgentService}). */
	private boolean reactive;
	/**
	 * For a reactive service, whether an adapter factory makes it a network agent;
	 * true for every network agent service.
	 */
	private boolean adaptable;
	/** The semantic of the messages the service produces, null when unknown. */
	private MessageSemantic messageSemantic;
	private AgentServiceKind kind;

	/**
	 * Describes a network agent service; a reactive service is described without
	 * its adapted types (use {@link #of(IGGenericAgentService, IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern)}).
	 */
	public static AgentServiceDescriptor of(IGGenericAgentService service) {
		return of(service, null);
	}

	/**
	 * Describes any agent service. A reactive service is adapted through the given
	 * repository of adapter factories, so that its input and output types in a
	 * network (the adapter's) are known to the composer.
	 *
	 * @param service          the service
	 * @param adapterFactories the adapter factories, null when the reactive services
	 *                         need no types
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static AgentServiceDescriptor of(IGGenericAgentService service,
			IGReactiveToNetworkAgentAdapterFactoryRepositoryPattern adapterFactories) {
		AgentServiceDescriptor descriptor = new AgentServiceDescriptor();
		descriptor.setServiceId(service.getId());
		descriptor.setDescription(service.getDescription());
		if (service instanceof IGNetworkAgentService<?, ?> networkAgent) {
			descriptor.setNetworkAgent(true);
			descriptor.setAdaptable(true);
			descriptor.setInputType(typeName(networkAgent.getInputType()));
			descriptor.setOutputType(typeName(networkAgent.getOutputType()));
			descriptor.setMessageSemantic(networkAgent.getProducedMessageSemantic());
		} else if (service instanceof IGReactiveAgentService reactiveService) {
			descriptor.setReactive(true);
			final IGReactiveToNetworkAgentAdapterFactory adapterFactory = adapterFactories != null
					? adapterFactories.getFactory(reactiveService)
					: null;
			descriptor.setAdaptable(adapterFactory != null);
			if (adapterFactory != null) {
				try {
					// the adapter is what takes part in the network: its types are the ones the
					// composer matches (the same object the validation builds)
					IGNetworkAgentService<?, ?> adapter = adapterFactory.create(reactiveService).getAdapter();
					descriptor.setInputType(typeName(adapter.getInputType()));
					descriptor.setOutputType(typeName(adapter.getOutputType()));
					descriptor.setMessageSemantic(adapter.getProducedMessageSemantic());
				} catch (Throwable e) {
					LOGGER.warn("Cannot adapt the reactive agent service:" + service.getId()
							+ " to describe its network types", e);
					descriptor.setAdaptable(false);
				}
			}
		}
		descriptor.setRoutingAgent(service instanceof IGRoutingNetworkAgentService);
		descriptor.setNetworkAdapter(service instanceof IGAgentsNetworkToNetworkAgentAdapterService);
		descriptor.setKind(kindOf(descriptor));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Described agent service id:" + descriptor.getServiceId() + " networkAgent:"
					+ descriptor.isNetworkAgent() + " routingAgent:" + descriptor.isRoutingAgent() + " networkAdapter:"
					+ descriptor.isNetworkAdapter() + " reactive:" + descriptor.isReactive() + " adaptable:"
					+ descriptor.isAdaptable() + " semantic:" + descriptor.getMessageSemantic() + " kind:"
					+ descriptor.getKind() + " inputType:" + descriptor.getInputType() + " outputType:"
					+ descriptor.getOutputType());
		}
		return descriptor;
	}

	/**
	 * The kind the flags sum up to, in the order the validation tells them apart: a
	 * reactive service is a writer, a routing service a router, a network adapter
	 * is one, a service delivering its messages is a forwarder, the rest respond.
	 */
	private static AgentServiceKind kindOf(AgentServiceDescriptor descriptor) {
		if (descriptor.isReactive()) {
			return AgentServiceKind.WRITER;
		}
		if (descriptor.isRoutingAgent()) {
			return AgentServiceKind.ROUTER;
		}
		if (descriptor.isNetworkAdapter()) {
			return AgentServiceKind.NETWORK_ADAPTER;
		}
		if (descriptor.getMessageSemantic() == MessageSemantic.EXECUTE_AND_SHARE_RESULT) {
			return AgentServiceKind.FORWARDER;
		}
		return descriptor.isNetworkAgent() ? AgentServiceKind.RESPONDER : null;
	}

	private static String typeName(Class<?> type) {
		return type != null ? type.getName() : null;
	}
}
