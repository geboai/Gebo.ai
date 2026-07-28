/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.environment.conditional.ConditionalOnMicroservices;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.jobs.services.impl.AbstractJobStatusEmitter;

/**
 * The AwsS3 microservice's own instance of the job-status broadcaster,
 * registered under `AwsS3`'s own already-owned
 * {@link GStandardModulesConstraints#AWS_S3_MODULE}, so it is uniquely
 * addressable under the microservices topology instead of colliding with
 * every other content handler's copy under the shared monolithic constant.
 */
@Component
@Scope("singleton")
@ConditionalOnMicroservices
public class AwsS3JobStatusEmitter extends AbstractJobStatusEmitter {

	public AwsS3JobStatusEmitter(IGRuntimeBinder runtimeBinder, IMessageEnvelopeFactory envelopeFactory) {
		super(runtimeBinder, envelopeFactory, GStandardModulesConstraints.AWS_S3_MODULE);
	}
}
