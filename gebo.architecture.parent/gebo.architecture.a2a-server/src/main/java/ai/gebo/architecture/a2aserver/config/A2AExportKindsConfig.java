/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.a2aserver.model.A2AExportedAgent;
import lombok.Data;

/**
 * What may be offered for export over A2A.
 * <p>
 * A network of agents is the thing worth exporting: it is a capability, and the
 * caller on the other side wants an agent that does the work, not the parts it
 * is made of. A single agent out of its network is rarely that - it has no
 * coordinator, no searchers, nothing but itself - so it is not offered unless
 * an installation says it wants it.
 * <p>
 * These are the platform's own defaults, in code. An installation overrides
 * them as it overrides any other property:
 *
 * <pre>
 * ai.gebo.a2a.netwokOfAgentsExported: true
 * ai.gebo.a2a.agentsExported: false
 * </pre>
 */
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.a2a")
@Data
public class A2AExportKindsConfig {

	/**
	 * Whether networks of agents are offered for export, and may be exported. True
	 * by default: exporting a network is what A2A is for here.
	 */
	private boolean netwokOfAgentsExported = true;

	/**
	 * Whether single agents are offered for export, and may be exported. False by
	 * default: an agent alone is seldom a capability worth publishing.
	 */
	private boolean agentsExported = false;

	/** Whether exports of this kind may be configured at all. */
	public boolean isAllowed(A2AExportedAgent.Kind kind) {
		if (kind == A2AExportedAgent.Kind.AGENT) {
			return agentsExported;
		}
		return netwokOfAgentsExported;
	}
}
