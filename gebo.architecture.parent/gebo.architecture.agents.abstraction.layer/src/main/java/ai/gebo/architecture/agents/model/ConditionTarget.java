/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Who or what a condition is waited from: the users, the groups of users and
 * the systems that may answer it, by their ids. Set by the tool or the system
 * that makes the condition, for an advanced process: the standard tools leave
 * it empty.
 * <p>
 * Set, only who or what it lists may answer, or an administrator. Empty, the
 * conversation's owner may, or anyone with access to the conversation, or an
 * administrator, as well as the accounts that deliver events for others.
 */
@Data
@NoArgsConstructor
public class ConditionTarget implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The usernames of the users who may answer. */
	private List<String> users = new ArrayList<String>();
	/** The codes of the groups whose members may answer. */
	private List<String> groups = new ArrayList<String>();
	/** The ids of the systems that may answer, as a source names its system. */
	private List<String> systems = new ArrayList<String>();

	/**
	 * A target of the ids given, each stripped and kept once, the blank ones
	 * dropped; any of them may be null.
	 */
	public static ConditionTarget of(Collection<String> users, Collection<String> groups,
			Collection<String> systems) {
		final ConditionTarget target = new ConditionTarget();
		target.setUsers(idsOf(users));
		target.setGroups(idsOf(groups));
		target.setSystems(idsOf(systems));
		return target;
	}

	/** Whether it lists anybody or anything: an empty target leaves the condition to the usual answerers. */
	@JsonIgnore
	public boolean isSet() {
		return !idsOf(users).isEmpty() || !idsOf(groups).isEmpty() || !idsOf(systems).isEmpty();
	}

	/** Whether it lists the user. */
	public boolean listsUser(String username) {
		return lists(users, username);
	}

	/** Whether it lists one of the groups. */
	public boolean listsAnyGroup(Collection<String> groupCodes) {
		if (groupCodes == null) {
			return false;
		}
		for (String code : groupCodes) {
			if (lists(groups, code)) {
				return true;
			}
		}
		return false;
	}

	/** Whether it lists the system. */
	public boolean listsSystem(String system) {
		return lists(systems, system);
	}

	/** A copy, its ids cleaned as {@link #of(Collection, Collection, Collection)} cleans them. */
	public ConditionTarget copy() {
		return of(users, groups, systems);
	}

	/** How it is named to people: the users, groups and systems it lists. */
	public String label() {
		final List<String> parts = new ArrayList<String>();
		if (!idsOf(users).isEmpty()) {
			parts.add("users " + String.join(", ", idsOf(users)));
		}
		if (!idsOf(groups).isEmpty()) {
			parts.add("groups " + String.join(", ", idsOf(groups)));
		}
		if (!idsOf(systems).isEmpty()) {
			parts.add("systems " + String.join(", ", idsOf(systems)));
		}
		return String.join("; ", parts);
	}

	private static boolean lists(List<String> ids, String id) {
		return id != null && !id.isBlank() && idsOf(ids).contains(id.strip());
	}

	private static List<String> idsOf(Collection<String> ids) {
		final LinkedHashSet<String> kept = new LinkedHashSet<String>();
		if (ids != null) {
			for (String id : ids) {
				if (id != null && !id.isBlank()) {
					kept.add(id.strip());
				}
			}
		}
		return new ArrayList<String>(kept);
	}
}
