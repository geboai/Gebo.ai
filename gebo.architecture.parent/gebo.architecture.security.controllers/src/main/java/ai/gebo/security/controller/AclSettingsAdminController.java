/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.security.model.AclGrantAssignment;
import ai.gebo.security.model.AclSelectableOwners;
import ai.gebo.security.model.AclSystemMode;
import ai.gebo.security.services.IGAclSettingsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Admin-only REST controller backing the reusable ACL settings control. It
 * reports whether the installation is in ACL mode, lists the owners an ACL can
 * be granted to (everyone/groups/users) and translates between a resource's
 * opaque {@code aclAliases} and a structured, everyone/group/user representation
 * the UI can edit. All operations require the {@code ADMIN} role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/AclSettingsAdminController")
@AllArgsConstructor
public class AclSettingsAdminController {

	private final IGAclSettingsService aclSettingsService;

	/**
	 * Reports the content-access policy currently active on the installation, so
	 * the control can tell whether ACL settings are actually enforced.
	 *
	 * @return the active policy and whether ACL enforcement is enabled.
	 */
	@GetMapping(value = "getSystemAclMode", produces = MediaType.APPLICATION_JSON_VALUE)
	public AclSystemMode getSystemAclMode() {
		return aclSettingsService.getSystemAclMode();
	}

	/**
	 * Lists every owner an ACL can be granted to: the everyone pseudo-owner, all
	 * groups (with their membership) and all enabled users.
	 *
	 * @return the selectable owners.
	 */
	@GetMapping(value = "getSelectableOwners", produces = MediaType.APPLICATION_JSON_VALUE)
	public AclSelectableOwners getSelectableOwners() {
		return aclSettingsService.getSelectableOwners();
	}

	/**
	 * Resolves a resource's acl aliases into a structured, collapsed list of owner
	 * grants for display and editing.
	 *
	 * @param aliases the resource {@code aclAliases}.
	 * @return the collapsed owner grants.
	 */
	@PostMapping(value = "resolveAliases", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<AclGrantAssignment> resolveAliases(@RequestBody List<Integer> aliases) {
		return aclSettingsService.resolveAliases(aliases);
	}

	/**
	 * Encodes structured owner grants back into acl aliases, to be stored as the
	 * resource's {@code aclAliases}.
	 *
	 * @param assignments the owner grants.
	 * @return the distinct acl aliases.
	 */
	@PostMapping(value = "encodeAssignments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> encodeAssignments(@Valid @RequestBody List<AclGrantAssignment> assignments) {
		return aclSettingsService.encodeAssignments(assignments);
	}
}
