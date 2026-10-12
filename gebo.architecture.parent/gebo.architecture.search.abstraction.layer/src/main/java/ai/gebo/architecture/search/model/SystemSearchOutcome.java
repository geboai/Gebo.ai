/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.search.model;

import java.util.List;

/**
 * What a best effort search on one system gave: its results, or why the system could
 * not be searched, said so that a model (or a user) can tell it.
 *
 * @param systemCode     the code of the system searched
 * @param systemName     its name as shown, its description when it has one
 * @param results        the results found, empty when the system could not be searched
 * @param unavailability why the system could not be searched, null when it was
 * @param detail         the technical detail of the failure, for the logs
 */
public record SystemSearchOutcome(String systemCode, String systemName, List<SearchResult> results,
		Unavailability unavailability, String detail) {

	/** Why a system could not be searched. */
	public enum Unavailability {
		/** It did not answer within the timeout. */
		NOT_RESPONDING("not responding"),
		/** It could not be reached, or answered with a server error. */
		OUT_OF_SERVICE("out of service"),
		/** It refused the access (credentials, permissions). */
		ACCESS_REFUSED("refused the access"),
		/** It failed for another reason (quota or rate limit exceeded, a bad request...). */
		FAILED("failed");

		private final String said;

		Unavailability(String said) {
			this.said = said;
		}

		public String said() {
			return said;
		}
	}

	public static SystemSearchOutcome found(String systemCode, String systemName, List<SearchResult> results) {
		return new SystemSearchOutcome(systemCode, systemName, results != null ? results : List.of(), null, null);
	}

	public static SystemSearchOutcome unavailable(String systemCode, String systemName, Unavailability unavailability,
			String detail) {
		return new SystemSearchOutcome(systemCode, systemName, List.of(), unavailability, detail);
	}

	public boolean available() {
		return unavailability == null;
	}

	/** The system that could not be searched, and why, as the model is told: null when it was searched. */
	public String unavailableNotice() {
		if (available()) {
			return null;
		}
		return (systemName != null && !systemName.isBlank() ? systemName : systemCode) + ": " + unavailability.said()
				+ (detail != null && unavailability == Unavailability.NOT_RESPONDING ? " (" + detail + ")" : "");
	}
}
