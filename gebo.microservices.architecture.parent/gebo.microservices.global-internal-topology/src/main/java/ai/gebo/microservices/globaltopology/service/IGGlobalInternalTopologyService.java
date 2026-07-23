/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.microservices.globaltopology.service;

import java.util.List;

import ai.gebo.application.messaging.model.MicroserviceMetaInfo;

/**
 * Maintains the network-wide internal messaging topology by polling every declared
 * microservice's {@code InternalMessagingTopologyController} on a schedule (and on
 * demand). The cached hierarchy is only replaced when every declared microservice
 * answered; an incomplete poll (a service down) is ERROR-logged and leaves the
 * previous snapshot in place.
 */
public interface IGGlobalInternalTopologyService {

	/**
	 * The last fully-collected global topology (empty until the first successful
	 * full poll). Never {@code null}.
	 */
	List<MicroserviceMetaInfo> getGlobalTopology();

	/**
	 * Re-poll every declared microservice now (the on-demand reset). Returns
	 * {@code true} when the poll was complete and the cache was refreshed,
	 * {@code false} when at least one declared microservice was down.
	 */
	boolean refresh();
}
