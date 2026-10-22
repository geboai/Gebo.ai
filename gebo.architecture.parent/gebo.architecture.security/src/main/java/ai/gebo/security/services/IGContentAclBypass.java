/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services;

import ai.gebo.security.model.UserInfos;

/**
 * A product's rule letting some users read every content regardless of the ACL
 * aliases, as an administrator does: an allow-list of accounts or domains, for
 * example. Optional: any number of beans may be declared, none means no bypass.
 * Consulted by {@link IGSecurityService#isContentAclEnforced()}, the one rule of
 * every content filter (the searches, the deep searches, the browsing and the
 * reading of the knowledge bases' documents), so a bypass applies to all of them
 * alike.
 */
public interface IGContentAclBypass {
	/** Whether the user reads every content regardless of the ACL aliases. */
	boolean bypassesContentAcl(UserInfos user);
}
