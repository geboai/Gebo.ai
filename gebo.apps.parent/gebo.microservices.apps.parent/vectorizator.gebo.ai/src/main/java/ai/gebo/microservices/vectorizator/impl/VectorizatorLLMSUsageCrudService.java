/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.vectorizator.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.llms.abstraction.layer.services.impl.AbstractLLMSUsageCrudService;

/**
 * Vectorizator's own instance of the LLM-usage emitter, registered under
 * vectorizator's own already-owned
 * {@link GStandardModulesConstraints#VECTORIZATOR_MODULE}, so it is uniquely
 * addressable under the microservices topology instead of colliding with
 * brain's/graphicator's copies or with tyr's
 * {@code LLMUsageConcentratorReceiverFactory} receiver, which owns
 * {@link GStandardModulesConstraints#LLMS_USAGE_MONITOR} itself.
 */
@Service
@ConditionalOnMicroservices
public class VectorizatorLLMSUsageCrudService extends AbstractLLMSUsageCrudService {

	public VectorizatorLLMSUsageCrudService(IGMessageBroker broker, IMessageEnvelopeFactory envelopeFactory) {
		super(broker, envelopeFactory, GStandardModulesConstraints.VECTORIZATOR_MODULE);
	}
}
