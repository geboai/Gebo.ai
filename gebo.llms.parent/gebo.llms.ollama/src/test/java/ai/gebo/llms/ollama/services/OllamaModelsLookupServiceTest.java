package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.ollama.services.OllamaModelsLookupService.Model;

/**
 * Fed with the /api/tags and /api/show shapes documented at https://docs.ollama.com/api
 */
class OllamaModelsLookupServiceTest {

	static Model model(String name) {
		Model m = new Model();
		m.name = name;
		m.model = name;
		m.details.put("parameter_size", "567M");
		m.details.put("quantization_level", "F16");
		return m;
	}

	static final Map<String, Object> BGE_M3 = Map.of("capabilities", List.of("embedding"), "model_info",
			Map.of("general.architecture", "bert", "bert.context_length", 8192, "bert.embedding_length", 1024));

	static final Map<String, Object> QWEN = Map.of("capabilities", List.of("completion", "tools", "thinking"),
			"model_info", Map.of("qwen3.context_length", 262144));

	@Test
	void theReportedCapabilitiesSortTheModelsWhateverTheirName() {
		assertTrue(OllamaModelsLookupService.offers(BGE_M3, OllamaModelsLookupService.EMBEDDING_CAPABILITY));
		assertFalse(OllamaModelsLookupService.offers(BGE_M3, OllamaModelsLookupService.CHAT_CAPABILITY));
		assertTrue(OllamaModelsLookupService.offers(QWEN, OllamaModelsLookupService.CHAT_CAPABILITY));
		// An older server reporting no capabilities leaves the decision to the model code
		assertTrue(OllamaModelsLookupService.offers(Map.of(), OllamaModelsLookupService.CHAT_CAPABILITY));
		assertTrue(OllamaModelsLookupService.offers(null, OllamaModelsLookupService.EMBEDDING_CAPABILITY));
	}

	@Test
	void theMetaInfosCarryContextLengthAndCapabilities() {
		ModelMetaInfo qwen = OllamaModelsLookupService.metaInfos(model("qwen3:8b"), QWEN);
		assertEquals(262144, qwen.getContextLength());
		assertTrue(qwen.getSupportsFunctionCalls());
		assertTrue(qwen.getSupportsReasoning());
		assertFalse(qwen.getSupportsVision());
		assertTrue(qwen.getChatModel());

		ModelMetaInfo bge = OllamaModelsLookupService.metaInfos(model("bge-m3:latest"), BGE_M3);
		assertEquals(8192, bge.getContextLength());
		assertTrue(bge.getEmbeddingModel());
		assertEquals("bge-m3:latest (567M, F16)", bge.getDescription());

		assertNull(OllamaModelsLookupService.metaInfos(model("x"), null).getChatModel());
	}
}
