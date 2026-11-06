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
 * A tool of a library an agent works with having the name of another tool the same
 * agent mounts. The library's tool wins, the other one never reaches the model: an
 * error of the configuration, shown where the agent is edited and written in the log.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryToolCollision {
	/** The name both tools have. */
	private String toolName;
	/** The library type whose tool is the one used. */
	private String libraryTypeId;
	/** What that library is, for the administrator. */
	private String libraryDescription;
	/** The tool source of the tool left out; null when another library's tool is. */
	private String shadowedSourceId;
	/** The library type of the tool left out; null when a tool source's tool is. */
	private String shadowedLibraryTypeId;
	/** What the tool left out belongs to, for the administrator. */
	private String shadowedDescription;

	/** The collision, as it is written in the log. */
	public String written() {
		return "the tool:" + toolName + " of library type:" + libraryTypeId + " takes the place of the one of "
				+ (shadowedLibraryTypeId != null ? "library type:" + shadowedLibraryTypeId
						: "tool source:" + shadowedSourceId);
	}
}
