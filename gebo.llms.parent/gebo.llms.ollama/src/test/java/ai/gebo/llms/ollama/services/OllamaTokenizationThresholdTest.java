package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelChoice;

/**
 * The tokenization threshold of an Ollama embedding model is its input window
 * ({@code <arch>.context_length} of /api/show), not the size of its vectors
 * ({@code <arch>.embedding_length}).
 */
class OllamaTokenizationThresholdTest {

	@Test
	void theInputWindowIsTheThresholdNotTheVectorSize() {
		GOllamaEmbeddingModelChoice listed = new GOllamaEmbeddingModelChoice();
		listed.setContextLength(8192);
		assertEquals(8192, OllamaEmbeddingModelConfigurationSupportService.tokenizationThreshold(listed));

		// a choice saved with only its /api/show details, keys stored with dashes
		GOllamaEmbeddingModelChoice saved = new GOllamaEmbeddingModelChoice();
		saved.setModelDetails(Map.of("capabilities", java.util.List.of("embedding"), "details", Map.of("format", "gguf"),
				"model_info", Map.of("bert-embedding_length", 1024, "bert-context_length", 8192)));
		assertEquals(8192, OllamaEmbeddingModelConfigurationSupportService.tokenizationThreshold(saved));

		GOllamaEmbeddingModelChoice tuned = new GOllamaEmbeddingModelChoice();
		tuned.setOptimalTokenizationParam(512);
		tuned.setContextLength(8192);
		assertEquals(512, OllamaEmbeddingModelConfigurationSupportService.tokenizationThreshold(tuned));

		assertNull(OllamaEmbeddingModelConfigurationSupportService.tokenizationThreshold(new GOllamaEmbeddingModelChoice()));
	}
}
