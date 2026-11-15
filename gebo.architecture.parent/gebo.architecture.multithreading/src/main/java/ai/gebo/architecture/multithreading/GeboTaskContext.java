/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.multithreading;

import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.context.integration.Slf4jThreadLocalAccessor;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;

/**
 * The context a task takes with it from the thread that submits it to the
 * thread that runs it: the current observation - hence the trace, whose ids the
 * tracing then puts back in the MDC - and the MDC itself, which holds the
 * request's audit context (correlation id, client address, method, path) the
 * security events are built from.
 * <p>
 * Deliberately nothing else: the security context in particular is not carried,
 * so a task keeps running with the identity it always ran with. That is why a
 * private registry is used instead of the global one, where Spring Security
 * registers its own accessors.
 * </p>
 */
public final class GeboTaskContext {

	private static final ContextSnapshotFactory FACTORY = ContextSnapshotFactory.builder()
			.contextRegistry(new ContextRegistry()
					.registerThreadLocalAccessor(ObservationThreadLocalAccessor.getInstance())
					.registerThreadLocalAccessor(new Slf4jThreadLocalAccessor()))
			.build();

	private GeboTaskContext() {
	}

	/**
	 * Captures the context of the calling thread, to be restored with
	 * {@link ContextSnapshot#setThreadLocals()} on the thread that runs the task.
	 */
	public static ContextSnapshot capture() {
		return FACTORY.captureAll();
	}
}
