/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gebo.ai comment agent
 *
 * Represents an authentication response that includes an access token, token
 * type, and user information.
 */
@AllArgsConstructor
@Getter
public class AuthResponse {
	// The access token issued for the authenticated session
	private SecurityHeaderData securityHeaderData;

	// User information associated with the authenticated session
	private UserInfo userInfo = null;

}