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

import ai.gebo.architecture.agents.services.GAbstractReactiveOutputAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The contract of a network service factory, as the composer shows it: the
 * factory a network declares to run on fixes the input the network's input nodes
 * must accept and the output its output nodes must produce, and whether the
 * network streams the answer of a single node answering the user directly.
 */
@Data
@NoArgsConstructor
public class NetworkServiceFactoryDescriptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkServiceFactoryDescriptor.class);
	private String factoryId;
	private String description;
	/** Fully qualified name of the type an input node must accept. */
	private String inputType;
	/** Fully qualified name of the type an output node must produce. */
	private String outputType;
	/**
	 * True when the factory streams the answer of exactly one reactive output node:
	 * the only factories that run a service answering the user directly.
	 */
	private boolean reactiveOutput;

	public static NetworkServiceFactoryDescriptor of(IGAgentsNetworkServiceFactory<?, ?, ?> factory) {
		NetworkServiceFactoryDescriptor descriptor = new NetworkServiceFactoryDescriptor();
		descriptor.setFactoryId(factory.getId());
		descriptor.setDescription(factory.getDescription());
		descriptor.setInputType(factory.getInputType() != null ? factory.getInputType().getName() : null);
		descriptor.setOutputType(factory.getOutputType() != null ? factory.getOutputType().getName() : null);
		descriptor.setReactiveOutput(factory instanceof GAbstractReactiveOutputAgentsNetworkServiceFactory);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Described network service factory id:" + descriptor.getFactoryId() + " inputType:"
					+ descriptor.getInputType() + " outputType:" + descriptor.getOutputType() + " reactiveOutput:"
					+ descriptor.isReactiveOutput());
		}
		return descriptor;
	}
}
