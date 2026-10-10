/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.model.IGObjectWithSecurity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Who may reach what a register endpoint stands for: one access rule configured
 * on an object of the platform (a knowledge base, a chat profile, a model, the
 * deep search configuration...), with what it decides.
 *
 * <p>
 * The platform has two access models, chosen system-wide
 * ({@code ai.gebo.security.useAcl}): the users/groups lists of
 * {@link IGObjectWithSecurity}, or ACL entries ({@code IAclGrantedResource}
 * aliases). A rule records both what its object holds and which of them the
 * check that applies it reads ({@link #mechanism}, {@link #grant}), so the
 * register can show the list actually in force; the ACL aliases are decoded, and
 * the system-wide model set, by the register when it is collected.
 * </p>
 */
@Data
public class DataEndpointAccess {

	/** Which lists the check applying a rule reads. */
	public static enum Mechanism {
		/**
		 * The users/groups lists in either access model (chat profiles, models, the
		 * deep search access): their check is {@code isCanAccess}.
		 */
		USERS_GROUPS,
		/**
		 * The users/groups lists in the users/groups model; in the ACL model the ACL
		 * entries for {@link DataEndpointAccess#getGrant()}, the lists counting too for
		 * a READ ({@code filterCanDoAction}): knowledge bases, projects, networks of
		 * agents.
		 */
		CONTENT,
		/**
		 * The ACL entries only, applied in the ACL model only: the permissions the
		 * documents of a data source inherit from it.
		 */
		ACL_ONLY
	}

	/** One decoded ACL entry: who ({@code user:..}, {@code group:..}, everyone) and the grant. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AclEntry {
		private String principal = null;
		private String grant = null;
	}

	/** The object the rule is configured on, for a reader: "Knowledge base 'library'". */
	private String grantedBy = null;
	/** What the rule decides, for a reader: "Retrieving its documents in chat and search". */
	private String scope = null;
	private Mechanism mechanism = Mechanism.USERS_GROUPS;
	/** The grant the check asks for (READ, EXECUTE), read against the ACL entries. */
	private String grant = "READ";
	private boolean accessibleToAll = false;
	private List<String> users = new ArrayList<>();
	private List<String> groups = new ArrayList<>();
	/** The object's ACL aliases as stored; {@link #aclEntries} once decoded. */
	private List<Integer> aclAliases = new ArrayList<>();
	private List<AclEntry> aclEntries = new ArrayList<>();
	/** Administrators pass the check whatever is listed. */
	private boolean administrators = true;
	/** What a reader needs to read the rule right, when the lists alone do not say it. */
	private String note = null;

	/**
	 * A rule read from the users/groups lists of an object.
	 *
	 * @param object    the object the rule is configured on
	 * @param grantedBy that object, for a reader
	 * @param scope     what the rule decides, for a reader
	 * @param mechanism which lists the check applying it reads
	 * @return the rule
	 */
	public static DataEndpointAccess of(IGObjectWithSecurity object, String grantedBy, String scope,
			Mechanism mechanism) {
		DataEndpointAccess access = new DataEndpointAccess();
		access.setGrantedBy(grantedBy);
		access.setScope(scope);
		access.setMechanism(mechanism);
		if (object != null) {
			access.setAccessibleToAll(Boolean.TRUE.equals(object.getAccessibleToAll()));
			if (object.getAccessibleUsers() != null) {
				access.getUsers().addAll(object.getAccessibleUsers());
			}
			if (object.getAccessibleGroups() != null) {
				access.getGroups().addAll(object.getAccessibleGroups());
			}
		}
		return access;
	}

	/** Adds the object's ACL aliases, read when the ACL model is in force. */
	public DataEndpointAccess withAclAliases(List<Integer> aliases) {
		if (aliases != null) {
			aclAliases.addAll(aliases);
		}
		return this;
	}
}
