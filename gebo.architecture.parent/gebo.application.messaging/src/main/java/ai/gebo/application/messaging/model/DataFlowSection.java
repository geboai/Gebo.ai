/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A part of what one component reports that stands on its own - each network of
 * agents the agents component reports - drawn by the register on its own tab.
 * Endpoints and steps name the section they belong to; an endpoint naming none is
 * shared by the component's sections and drawn on the tab of each one reaching it.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFlowSection {
	/** Unique inside the component: the network code. */
	private String id = null;
	/** How the register names it to a reader: the network description. */
	private String description = null;
}
