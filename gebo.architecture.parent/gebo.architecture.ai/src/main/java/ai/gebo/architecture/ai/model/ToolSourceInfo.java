/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.ai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A registered source of tools as the administrator sees it: its id (what an
 * agent configuration or the system settings name to leave its tools out), what
 * it is in plain words, the category its tools are listed under and how many
 * tools it exports now.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolSourceInfo {
	/** The unique id of the source ({@code IGToolCallbackSource.getId()}). */
	private String id = null;
	/** What the source is, for the administrator. */
	private String description = null;
	/** The code of the category its tools are listed under. */
	private String categoryCode = null;
	/** The description of the category its tools are listed under. */
	private String categoryDescription = null;
	/** How many tools the source exports now; 0 when it cannot be read. */
	private int toolsCount = 0;
}
