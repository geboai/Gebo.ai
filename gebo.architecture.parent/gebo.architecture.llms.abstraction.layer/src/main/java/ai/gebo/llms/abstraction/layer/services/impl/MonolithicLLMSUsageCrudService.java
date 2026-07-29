/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMonolithic;

/**
 * The single, monolith-wide {@link AbstractLLMSUsageCrudService} instance,
 * registered under the shared {@link GStandardModulesConstraints#LLMS_USAGE_MONITOR}
 * constant - unchanged from the identity this bean always used before it was
 * split by deployment architecture. Safe here (unlike under microservices)
 * because the monolith hosts both this emitter and the receiver
 * ({@code LLMUsageConcentratorReceiverFactory}, {@code gebo.architecture.compute.workflow})
 * in the same JVM, and {@code GBaseMessageBroker} keeps separate maps for
 * emitters and receivers.
 */
@Service
@ConditionalOnMonolithic
public class MonolithicLLMSUsageCrudService extends AbstractLLMSUsageCrudService {

	public MonolithicLLMSUsageCrudService(IGMessageBroker broker, IMessageEnvelopeFactory envelopeFactory) {
		super(broker, envelopeFactory, GStandardModulesConstraints.LLMS_USAGE_MONITOR);
	}
}
