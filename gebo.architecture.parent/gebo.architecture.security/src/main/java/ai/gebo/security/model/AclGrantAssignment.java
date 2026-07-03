/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.model;

import java.util.List;

import ai.gebo.acl.AclGrantType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The grants held by a single ACL owner (everyone/group/user). This is the
 * structured, alias-free representation exchanged with the UI: the backend
 * resolves aliases into these and encodes these back into aliases.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AclGrantAssignment {
	/** The owner kind this assignment refers to. */
	private AclOwnerType ownerType;
	/** Owner code: group code for GROUP, username for USER, unused for EVERYONE. */
	private String ownerCode;
	/** The grants held by this owner. */
	private List<AclGrantType> grants;
}
