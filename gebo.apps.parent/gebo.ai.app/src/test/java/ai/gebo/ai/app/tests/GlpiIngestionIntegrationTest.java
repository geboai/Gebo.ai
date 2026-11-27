/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;

/**
 * {@link AbstractGlpiIngestionIntegrationTest} on GLPI 11.0.13, the release
 * the gebo.glpi.client spec comes from.
 */
public class GlpiIngestionIntegrationTest extends AbstractGlpiIngestionIntegrationTest {
	private static final Network NETWORK = Network.newNetwork();

	@Container
	static final GenericContainer<?> GLPI_DB = glpiDatabase(NETWORK);

	@Container
	static final GenericContainer<?> GLPI = glpiServer("glpi/glpi:11.0.13", NETWORK, GLPI_DB);

	@Override
	protected GenericContainer<?> glpi() {
		return GLPI;
	}
}
