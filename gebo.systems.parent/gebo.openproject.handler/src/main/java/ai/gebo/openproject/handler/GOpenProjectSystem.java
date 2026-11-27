/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * An OpenProject server: its base URI (inherited, without /api/v3) and the stored
 * secret holding the API key of the account used to call its API v3 (a TOKEN
 * secret, sent as the basic authentication of the user "apikey").
 */
public class GOpenProjectSystem extends GContentManagementSystem {
	/** Code of the secret holding the credentials, never the credentials. */
	private String secretCode = null;

	public GOpenProjectSystem() {
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
}
