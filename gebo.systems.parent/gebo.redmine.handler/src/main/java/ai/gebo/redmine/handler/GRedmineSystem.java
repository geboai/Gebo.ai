/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * A Redmine server: its base URI (inherited) and the stored secret used to call
 * its REST API, either an API key (TOKEN secret, sent as the X-Redmine-API-Key
 * header) or a username and password (USERNAME_PASSWORD secret, sent as basic
 * authentication). The REST web service must be enabled in Redmine.
 */
public class GRedmineSystem extends GContentManagementSystem {
	/** Code of the secret holding the credentials, never the credentials. */
	private String secretCode = null;

	public GRedmineSystem() {
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
}
