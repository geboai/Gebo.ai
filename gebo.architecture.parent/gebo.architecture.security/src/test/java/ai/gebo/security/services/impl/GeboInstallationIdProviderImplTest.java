/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.security.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** The host part of the installation id is the machine's name, never a loopback one. */
class GeboInstallationIdProviderImplTest {

	@Test
	void theHostNameIsNeverLocalhost() {
		String host = GeboInstallationIdProviderImpl.hostName();

		assertThat(host).isNotBlank();
		assertThat(host).isNotEqualTo("localhost").doesNotStartWith("localhost.").isNotEqualTo("127.0.0.1")
				.isNotEqualTo("::1");
	}
}
