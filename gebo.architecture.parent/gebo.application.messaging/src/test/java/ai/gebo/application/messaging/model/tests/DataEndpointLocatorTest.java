/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataEndpointLocator;

/**
 * The data-flow report is an ADMIN-visible map of every store this installation
 * talks to, and the configuration objects it is built from carry credentials
 * inline. These tests pin the guarantee that none of them can reach it.
 *
 * <p>
 * The inputs below are the real shapes taken from the deployment config, not
 * invented ones: the MongoDB connection string is the one in
 * {@code dockers/gebo.microservices/config/application.yml}, and the Qdrant,
 * OpenSearch and Neo4j cases mirror how those stores are configured there.
 * </p>
 */
public class DataEndpointLocatorTest {

	/** The exact value shipped in the microservices deployment config. */
	private static final String MONGO_CONNECTION_STRING = "mongodb://mongoroot:mongopwd@mongo:27017/?authSource=admin";

	@Test
	public void stripsUserInfoAndQueryFromAMongoConnectionString() {
		String locator = DataEndpointLocator.sanitize(MONGO_CONNECTION_STRING);

		assertEquals("mongodb://mongo:27017", locator);
		assertFalse(locator.contains("mongoroot"), "username must not survive");
		assertFalse(locator.contains("mongopwd"), "password must not survive");
	}

	@Test
	public void keepsTheDatabaseNameBecauseItIsAuditRelevant() {
		assertEquals("mongodb://mongo:27017/brain-gebo",
				DataEndpointLocator.sanitize("mongodb://mongoroot:mongopwd@mongo:27017/brain-gebo"));
	}

	@Test
	public void splitsUserInfoOnTheLastAtSoAPasswordContainingOneCannotLeak() {
		// A '@' inside the password would fool a first-'@' split and leave the
		// tail of the password in the host position.
		assertEquals("mongodb://mongo:27017",
				DataEndpointLocator.sanitize("mongodb://user:p@ssw0rd@mongo:27017"));
	}

	@Test
	public void dropsAQueryStringThatCouldItselfCarryAKey() {
		assertEquals("https://qdrant:6334", DataEndpointLocator.sanitize("https://qdrant:6334?apiKey=ce7c85bc-f037"));
	}

	@Test
	public void dropsAFragment() {
		assertEquals("https://opensearch:9200", DataEndpointLocator.sanitize("https://opensearch:9200#anything"));
	}

	@Test
	public void handlesAMultiHostConnectionStringWithoutFailing() {
		// A replica-set string is not an RFC-conformant URI; the report must still
		// render, and must still be credential-free.
		String locator = DataEndpointLocator.sanitize("mongodb://user:pw@h1:27017,h2:27017/db");

		assertEquals("mongodb://h1:27017,h2:27017/db", locator);
		assertFalse(locator.contains("pw@"), "credentials must not survive");
	}

	@Test
	public void leavesAnAlreadyCleanUriAlone() {
		assertEquals("bolt://neo4j:7687", DataEndpointLocator.sanitize("bolt://neo4j:7687"));
		assertEquals("https://api.openai.com/v1", DataEndpointLocator.sanitize("https://api.openai.com/v1"));
	}

	@Test
	public void isIdempotentSoReapplyingItAfterTheTopologyHopIsHarmless() {
		String once = DataEndpointLocator.sanitize(MONGO_CONNECTION_STRING);

		assertEquals(once, DataEndpointLocator.sanitize(once));
	}

	@Test
	public void normalizesATrailingSlashSoIdenticalEndpointsCompareEqual() {
		assertEquals(DataEndpointLocator.sanitize("mongodb://mongo:27017"),
				DataEndpointLocator.sanitize("mongodb://mongo:27017/"));
	}

	@Test
	public void handlesAHostPortPairWithNoScheme() {
		assertEquals("mongo:27017", DataEndpointLocator.sanitize("mongo:27017"));
	}

	@Test
	public void returnsNullForNothingRatherThanAnEmptyLocator() {
		assertNull(DataEndpointLocator.sanitize(null));
		assertNull(DataEndpointLocator.sanitize("   "));
	}

	@Test
	public void redactsRatherThanEchoingWhenNothingRecognizableSurvives() {
		// Never fall back to the raw input on a value that could not be parsed -
		// that is exactly the case where it might still hold a credential.
		assertEquals(DataEndpointLocator.REDACTED, DataEndpointLocator.sanitize("mongodb://user:pw@/db"));
	}

	@Test
	public void buildsALocatorFromSeparateFieldsAsTheStoreConfigsHoldThem() {
		// OpenSearchConfig keeps protocol, host and port apart.
		assertEquals("https://opensearch:9200", DataEndpointLocator.of("https", "opensearch", 9200, null));
		// QdrantConfig likewise, and the collection name is worth keeping.
		assertEquals("https://qdrant:6334/gebo", DataEndpointLocator.of("https", "qdrant", 6334, "gebo"));
		assertEquals("neo4j:7687", DataEndpointLocator.of(null, "neo4j", 7687, null));
		assertNull(DataEndpointLocator.of("https", null, 9200, null));
	}

	@Test
	public void theEndpointSetterSanitizesSoARawStringCannotBeStoredByMistake() {
		DataEndpoint endpoint = new DataEndpoint();

		endpoint.setEndpoint(MONGO_CONNECTION_STRING);

		assertEquals("mongodb://mongo:27017", endpoint.getEndpoint());
		assertFalse(endpoint.getEndpoint().contains("mongopwd"), "password must not reach the model");
	}

	@Test
	public void theLocalityHintClaimsLocalOnlyWhenTheHostCannotBeAnythingElse() {
		// Docker-compose service names - the stores of every shipped deployment.
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				DataEndpointLocality.hintFromLocator("mongodb://mongo:27017/db"));
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				DataEndpointLocality.hintFromLocator("https://opensearch:9200"));
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				DataEndpointLocality.hintFromLocator("bolt://neo4j:7687"));
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				DataEndpointLocality.hintFromLocator("http://localhost:11434"));
		assertEquals(DataEndpointLocality.LOCAL_DEPLOYMENT,
				DataEndpointLocality.hintFromLocator("http://127.0.0.1:6333"));
	}

	@Test
	public void theLocalityHintStaysSilentOnAnythingItCannotEstablish() {
		// A managed cloud store is indistinguishable from an on-prem DNS name at
		// this level, and claiming "local" for the former is the damaging error.
		assertNull(DataEndpointLocality.hintFromLocator("mongodb://cluster0.abcd.mongodb.net:27017"));
		assertNull(DataEndpointLocality.hintFromLocator("https://api.openai.com/v1"));
		assertNull(DataEndpointLocality.hintFromLocator("https://qdrant.internal.corp:6334"));
		assertNull(DataEndpointLocality.hintFromLocator(null));
		assertNull(DataEndpointLocality.hintFromLocator("   "));
	}

	@Test
	public void theEndpointSetterAlsoTakesSeparateConfigurationFields() {
		DataEndpoint endpoint = new DataEndpoint();

		endpoint.setEndpoint("https", "opensearch", 9200, "gebo-kb-chunks");

		assertEquals("https://opensearch:9200/gebo-kb-chunks", endpoint.getEndpoint());
	}
}
