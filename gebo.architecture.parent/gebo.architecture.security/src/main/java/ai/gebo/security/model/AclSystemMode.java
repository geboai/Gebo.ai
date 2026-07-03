/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.model;

import ai.gebo.acl.ContentAccessPolicy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes the content-access policy currently active on the installation, so
 * the UI can tell whether ACL settings are actually enforced.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AclSystemMode {
	/** The active content-access policy (GROUP_BASED or ACL_BASED). */
	private ContentAccessPolicy policy;
	/** {@code true} when ACL enforcement is switched on ({@code ai.gebo.security.use-acl}). */
	private boolean aclEnabled;
}
