/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * One node's data-flow configuration at a point in time: every locally hosted
 * component that reports a {@link GDataFlowMetaInfos}, grouped by messaging
 * module.
 *
 * <p>
 * {@link #collectedAt} is on the report rather than on the per-component model
 * because staleness is a property of the collection, not of any one component,
 * and because it must be answerable on the screen. It matters:
 * {@code GGlobalInternalTopologyServiceImpl} deliberately keeps the previous
 * cached snapshot when a declared microservice is unreachable, so an aggregated
 * view can legitimately contain a node's older report. An audit screen must be
 * able to say "as of this time" instead of presenting it as current.
 * </p>
 */
@Data
public class GDataFlowReport {

	/**
	 * The node this report came from - the {@code GeboCurrentApplication} id, which
	 * is the {@code spring.application.name} under microservices and the single
	 * application id on the monolith.
	 */
	private String nodeId = null;

	/** When this node built the report. */
	private Date collectedAt = null;

	/**
	 * The locally hosted modules, each pruned to the components that actually
	 * report a data flow. Components with no data-flow configuration of their own
	 * are omitted rather than sent as empty entries.
	 */
	private List<GModuleMetaInfo> modules = new ArrayList<GModuleMetaInfo>();

	public GDataFlowReport() {
	}

	public GDataFlowReport(String nodeId, Date collectedAt, List<GModuleMetaInfo> modules) {
		this.nodeId = nodeId;
		this.collectedAt = collectedAt;
		this.modules = modules != null ? modules : new ArrayList<GModuleMetaInfo>();
	}
}
