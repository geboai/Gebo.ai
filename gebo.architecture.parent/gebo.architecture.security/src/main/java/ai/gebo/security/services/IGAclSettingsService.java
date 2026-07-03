/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import java.util.List;

import ai.gebo.security.model.AclGrantAssignment;
import ai.gebo.security.model.AclSelectableOwners;
import ai.gebo.security.model.AclSystemMode;

/**
 * Backend support for the admin ACL settings control. Translates between the
 * opaque {@code List<Integer>} acl aliases stored on
 * {@link ai.gebo.acl.IAclGrantedResource} objects and a structured,
 * everyone/group/user oriented representation that the UI can render and edit.
 */
public interface IGAclSettingsService {

	/**
	 * Reports the content-access policy currently active on the installation.
	 *
	 * @return the active policy and whether ACL enforcement is enabled.
	 */
	AclSystemMode getSystemAclMode();

	/**
	 * Lists every owner that an ACL can be granted to: the everyone pseudo-owner,
	 * all groups (with their membership) and all enabled users.
	 *
	 * @return the selectable owners.
	 */
	AclSelectableOwners getSelectableOwners();

	/**
	 * Resolves a resource's acl aliases into a structured, collapsed list of
	 * owner grants. Collapsing removes redundant entries per grant type: everyone
	 * hides groups/users, and a granted group hides its member users.
	 *
	 * @param aliases the resource {@code aclAliases}; may be {@code null}/empty.
	 * @return the collapsed owner grants (never {@code null}).
	 */
	List<AclGrantAssignment> resolveAliases(List<Integer> aliases);

	/**
	 * Encodes structured owner grants back into acl aliases, creating any missing
	 * alias on the fly. The returned list is what should be stored as the
	 * resource's {@code aclAliases}.
	 *
	 * @param assignments the owner grants; may be {@code null}/empty.
	 * @return the distinct acl aliases (never {@code null}).
	 */
	List<Integer> encodeAssignments(List<AclGrantAssignment> assignments);
}
