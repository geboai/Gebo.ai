/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.model;

import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a scheduled task specifically related to a project endpoint.
 * This class extends the AbstractScheduledTask with a type parameter of GObjectRef of a GProjectEndpoint.
 * It's used in the scheduling system to handle tasks associated with project endpoints.
 */
@Getter
@Setter
public class ProjectEndpointScheduledTask extends AbstractScheduledTask<GObjectRef<GProjectEndpoint>> {

	/**
	 * The messagingModuleId of the content-handler that requested this
	 * reschedule (the {@code sourceModule} of the
	 * {@code GRescheduleProjectEndpointMessagePayload} envelope that created or
	 * last renewed this task) - the address the central scheduler dispatches
	 * {@code PublishProjectEndpointMessagePayload} to, targeting that handler's
	 * own {@code async-publishing-job-component}, when this task comes due.
	 */
	private String sourceModule = null;

	/**
	 * The flattened endpoint carried by the reschedule request that created or
	 * last renewed this task, denormalized here so the central scheduler can
	 * compute the *next* run at dispatch time without reaching back into the
	 * originating content-handler's own (possibly remote, in microservices)
	 * persistence - which, unlike this record, it has no access to.
	 * {@code GCentralizedProjectEndpoint#getRemoteProjectReference()} doubles as
	 * the concrete-type {@code GProjectEndpoint} reference the eventual
	 * {@code PublishProjectEndpointMessagePayload} needs.
	 */
	private GCentralizedProjectEndpoint centralizedProjectEndpoint = null;

}