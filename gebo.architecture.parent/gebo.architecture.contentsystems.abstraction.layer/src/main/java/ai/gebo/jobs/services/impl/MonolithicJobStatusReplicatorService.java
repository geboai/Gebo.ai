/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.jobs.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.GeboApplicationArchitecture;
import ai.gebo.architecture.environment.conditional.ConditionalOnMonolithic;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.replicator.model.IGReplicationsMap;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;

/**
 * The single, monolith-wide {@link AbstractJobStatusReplicatorService}
 * instance, registered under the shared
 * {@link GStandardModulesConstraints#ASYNC_PUBLISHING_JOB_MODULE} constant -
 * unchanged from the identity this bean always used before it was split by
 * deployment architecture.
 */
@Component
@Scope("singleton")
@ConditionalOnMonolithic
public class MonolithicJobStatusReplicatorService extends AbstractJobStatusReplicatorService {

	public MonolithicJobStatusReplicatorService(GeboApplicationArchitecture architecture,
			IGReplicationsMap replicationsMap, IMessageEnvelopeFactory envelopeFactory, IGRuntimeBinder runtimeBinder) {
		super(GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE, architecture, replicationsMap, envelopeFactory,
				runtimeBinder);
	}
}
