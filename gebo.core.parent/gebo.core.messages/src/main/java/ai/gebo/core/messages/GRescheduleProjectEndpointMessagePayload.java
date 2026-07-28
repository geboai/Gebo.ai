/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import lombok.Data;

/**
 * Sent by a content-handler's {@code GAbstractSystemsArchitectureController}
 * (targeting {@code scheduler-module.scheduler-component}) whenever a project
 * endpoint is inserted or updated, so the central scheduler can (re)compute
 * its next publish run.
 *
 * <p>
 * Carries the full flattened {@link GCentralizedProjectEndpoint} rather than a
 * reference: under the microservices architecture the central scheduler runs
 * on tyr, which has no access to the originating content-handler's own local
 * Mongo, but every field the scheduler's next-run computation needs
 * ({@code programmedTables}, {@code synchroStrategy}, {@code published}, ...)
 * is already copied onto the centralized view.
 * </p>
 *
 * <p>
 * The sending handler's identity is not carried on this payload: it is
 * already present as {@code sourceModule} on the {@code GMessageEnvelope}
 * this payload travels in, which the central scheduler records against the
 * scheduled task so it knows which handler's
 * {@code async-publishing-job-component} to target when the run is due.
 * </p>
 */
@Data
public class GRescheduleProjectEndpointMessagePayload extends GBaseMessagePayload {

	/**
	 * The flattened, shareable view of the project endpoint to (re)schedule.
	 */
	private GCentralizedProjectEndpoint centralizedProjectEndpoint = null;

}
