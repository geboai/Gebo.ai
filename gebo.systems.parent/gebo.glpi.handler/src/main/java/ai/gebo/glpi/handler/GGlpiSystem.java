/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.glpi.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A GLPI installation. The GLPI high-level API v2 only accepts OAuth2 bearer
 * tokens for its data endpoints, obtained with the password grant: an OAuth
 * client registered in GLPI (Setup &gt; OAuth clients) and a GLPI user, whose
 * GLPI rights decide what the ingestion and the searches can read. Both are
 * referenced here by secret code, never stored on the entity.
 * <p>
 * baseUri is the GLPI root url (the one of its web interface, e.g.
 * https://glpi.example.com), the api is reached under baseUri/api.php.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GGlpiSystem extends GContentManagementSystem {
	/**
	 * Secret (OAUTH2_STANDARD) holding the client id and the client secret of the
	 * OAuth client registered in GLPI
	 */
	@NotBlank
	private String oauthClientSecretCode = null;
	/**
	 * Secret (USERNAME_PASSWORD) holding the GLPI user the system reads as
	 */
	@NotBlank
	private String secretCode = null;
}
