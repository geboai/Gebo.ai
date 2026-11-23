package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * The token counts of a document reference keep the JSON names the generated clients read
 * (ntokensRelevant, ntotalContentTokens, nbytesRelevant) with Jackson 3, the mapper Spring Boot 4
 * answers with, and JSON written with the capitalized names is still read.
 */
class GResponseDocumentRefJsonTest {

	private final JsonMapper mapper = JsonMapper.builder().build();

	@Test
	void writesTheNamesTheClientsRead() {
		GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setNTokensRelevant(120);
		ref.setNTotalContentTokens(4000);
		ref.setNBytesRelevant(512);
		JsonNode json = mapper.valueToTree(ref);
		assertEquals(120, json.get("ntokensRelevant").asLong());
		assertEquals(4000, json.get("ntotalContentTokens").asLong());
		assertEquals(512, json.get("nbytesRelevant").asLong());
		assertFalse(json.has("NTokensRelevant"));
		assertFalse(json.has("NTotalContentTokens"));
		assertFalse(json.has("NBytesRelevant"));
	}

	@Test
	void readsBothSpellings() {
		GResponseDocumentRef lower = mapper.readValue(
				"{\"ntokensRelevant\":7,\"ntotalContentTokens\":70,\"nbytesRelevant\":700}", GResponseDocumentRef.class);
		GResponseDocumentRef upper = mapper.readValue(
				"{\"NTokensRelevant\":7,\"NTotalContentTokens\":70,\"NBytesRelevant\":700}", GResponseDocumentRef.class);
		for (GResponseDocumentRef r : new GResponseDocumentRef[] { lower, upper }) {
			assertEquals(7, r.getNTokensRelevant());
			assertEquals(70, r.getNTotalContentTokens());
			assertEquals(700, r.getNBytesRelevant());
		}
	}

	@Test
	void keepsTheOtherPropertiesAsTheyWere() {
		GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setName("a.docx");
		ref.setLoadPercentage(0.5);
		JsonNode json = mapper.valueToTree(ref);
		assertTrue(json.has("name"));
		assertTrue(json.has("loadPercentage"));
		assertTrue(json.has("references"));
	}
}
