/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.google_vertex.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.security.KeyPairGenerator;
import java.security.PrivateKey;

import org.junit.jupiter.api.Test;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

/**
 * A service account key carries the project the Vertex client needs; other
 * credentials tell none.
 */
class VertexAIConfiguratorProjectTest {

	@Test
	void theServiceAccountKeyTellsItsProject() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		PrivateKey key = generator.generateKeyPair().getPrivate();
		ServiceAccountCredentials serviceAccount = ServiceAccountCredentials.newBuilder()
				.setClientEmail("gebo@my-project.iam.gserviceaccount.com").setPrivateKey(key)
				.setProjectId("my-project").build();
		assertEquals("my-project", VertexAIConfigurator.serviceAccountProject(serviceAccount));
		assertNull(VertexAIConfigurator.serviceAccountProject(GoogleCredentials.create(new AccessToken("t", null))));
		assertEquals("global", VertexAIConfigurator.DEFAULT_LOCATION);
	}
}
