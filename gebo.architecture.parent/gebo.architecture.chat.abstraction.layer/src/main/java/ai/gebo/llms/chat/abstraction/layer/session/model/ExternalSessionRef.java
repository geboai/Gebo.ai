/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.session.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * How another system names a conversation of its own that a Gebo conversation
 * stands for: the system, as it names itself ({@code ERP}, {@code WORKFLOW},
 * {@code A2A}...), and its key of the dialogue. A conversation opened by a key is
 * found again by it: the dialogue of the external side maps to one Gebo
 * conversation, however many times it comes back. Both parts are needed; a
 * reference with either missing names nothing.
 */
@Data
@NoArgsConstructor
public class ExternalSessionRef implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The system of an A2A caller that names its own context id. */
	public static final String A2A_SYSTEM = "A2A";
	/** The system the external side is or belongs to, as it names itself. */
	private String externalSystem = null;
	/** The external side's own key of the dialogue, unique within its system. */
	private String externalSessionKey = null;

	/** The reference, stripped; null when either part is missing. */
	public static ExternalSessionRef of(String externalSystem, String externalSessionKey) {
		if (isBlank(externalSystem) || isBlank(externalSessionKey)) {
			return null;
		}
		final ExternalSessionRef ref = new ExternalSessionRef();
		ref.setExternalSystem(externalSystem.strip());
		ref.setExternalSessionKey(externalSessionKey.strip());
		return ref;
	}

	/** Whether both parts are given. */
	@JsonIgnore
	public boolean isGiven() {
		return !isBlank(externalSystem) && !isBlank(externalSessionKey);
	}

	/** The reference as a label: {@code <system>/<key>}. */
	public String label() {
		return (externalSystem != null ? externalSystem.strip() : "?") + "/"
				+ (externalSessionKey != null ? externalSessionKey.strip() : "?");
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
