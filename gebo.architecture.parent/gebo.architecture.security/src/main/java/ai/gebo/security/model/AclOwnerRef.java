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
 * A selectable ACL owner presented to the UI: {@code everyone}, a group or a
 * user. For groups, {@link #memberUserIds} carries the group membership so the
 * control can hide users already covered by a selected group.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AclOwnerRef {
	/** Whether this reference is the everyone pseudo-owner, a group or a user. */
	private AclOwnerType type;
	/** Owner code: group code for GROUP, username for USER, unused for EVERYONE. */
	private String code;
	/** Human readable label to show in the picker. */
	private String label;
	/** Optional description (group description). */
	private String description;
	/** For GROUP only: usernames of the group members; {@code null} otherwise. */
	private List<String> memberUserIds;
}
