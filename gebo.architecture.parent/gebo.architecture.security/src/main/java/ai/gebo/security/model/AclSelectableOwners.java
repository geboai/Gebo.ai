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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The full set of owners the ACL settings control can offer: the everyone
 * pseudo-owner plus every group (with membership) and every enabled user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AclSelectableOwners {
	/** The everyone pseudo-owner reference. */
	private AclOwnerRef everyone;
	/** All selectable groups, each carrying its member usernames. */
	private List<AclOwnerRef> groups;
	/** All selectable (enabled) users. */
	private List<AclOwnerRef> users;
}
