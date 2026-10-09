/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.core.contents.security.services;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What folders or documents to look for (query by example): always inside the given
 * knowledge bases, each other criterion only when set. Deleted objects are never
 * found, and the user's access rights always apply.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualFilesystemQuery {
	/** The knowledge bases searched: required, nothing is found outside them. */
	private List<String> knowledgeBaseCodes;
	/** The project the objects are in. */
	private String projectCode;
	/** The project endpoint (content source) the objects come from. */
	private String projectEndpointCode;
	/** The folder the objects are directly in. */
	private String parentVirtualFolderCode;
	/** Only the objects at the root of their endpoint (in no folder). */
	private boolean rootsOnly;
	/** A part of the object's name, ignoring case. */
	private String nameContains;
	/** The uniqueIds of the objects. */
	private List<Long> uniqueIds;
}
