/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.uploads.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.jobs.services.impl.AbstractJobStatusEmitter;

/**
 * The Uploads microservice's own instance of the job-status broadcaster,
 * registered under `Uploads`'s own already-owned
 * {@link GStandardModulesConstraints#UPLOADS_MODULE}, so it is uniquely
 * addressable under the microservices topology instead of colliding with
 * every other content handler's copy under the shared monolithic constant.
 */
@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class UploadsJobStatusEmitter extends AbstractJobStatusEmitter {

	public UploadsJobStatusEmitter(IGRuntimeBinder runtimeBinder, IMessageEnvelopeFactory envelopeFactory) {
		super(runtimeBinder, envelopeFactory, GStandardModulesConstraints.UPLOADS_MODULE);
	}
}
