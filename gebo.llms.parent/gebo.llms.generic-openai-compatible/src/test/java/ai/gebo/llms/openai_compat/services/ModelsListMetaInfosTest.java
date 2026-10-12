package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelChoice;
import ai.gebo.llms.openai_compat.services.RegoloAIModelsListProviderService.RegoloAIModel;
import ai.gebo.openrouter.client.model.ModelArchitecture;
import ai.gebo.openrouter.client.model.ModelPricing;
import ai.gebo.openrouter.client.model.OpenRouterModel;
import ai.gebo.openrouter.client.model.TopProvider;

/**
 * The model entries as the live OpenRouter /api/v1/models and regolo.ai
 * /v1/model/info answered them (2026-10).
 */
class ModelsListMetaInfosTest {

	static OpenRouterModel openRouter(String id, long context, List<String> parameters, List<String> input) {
		OpenRouterModel model = new OpenRouterModel();
		model.setId(id);
		model.setName(id);
		model.setContextLength(context);
		model.setSupportedParameters(parameters);
		ModelArchitecture architecture = new ModelArchitecture();
		architecture.setInputModalities(input);
		model.setArchitecture(architecture);
		TopProvider top = new TopProvider();
		top.setMaxCompletionTokens(131072L);
		model.setTopProvider(top);
		ModelPricing pricing = new ModelPricing();
		pricing.setPrompt("0.00003055555555555");
		pricing.setCompletion("0");
		model.setPricing(pricing);
		return model;
	}

	@Test
	void anOpenRouterChatModelCarriesLimitsParametersAndExpiry() {
		OpenRouterModel model = openRouter("z-ai/glm-5.3-flash", 1048576,
				List.of("tools", "structured_outputs", "reasoning", "temperature"), List.of("text", "image"));
		model.setExpirationDate("2026-12-31");
		GBaseChatModelChoice choice = new GBaseChatModelChoice();

		OpenRouterModelsListProviderService.fill(choice, model, "openrouter.ai");

		assertEquals("z-ai/glm-5.3-flash (leaves OpenRouter on 2026-12-31)", choice.getDescription());
		assertEquals(1048576, choice.getContextLength());
		assertEquals(131072, choice.getMetaInfos().getMaxOutputToken());
		assertTrue(choice.getMetaInfos().getSupportsFunctionCalls());
		assertTrue(choice.getMetaInfos().getSupportsStructuredOutput());
		assertTrue(choice.getMetaInfos().getSupportsReasoning());
		assertTrue(choice.getMetaInfos().getSupportsVision());
		assertEquals("2026-12-31", choice.getMetaInfos().getRetirementDate());
		assertEquals("https://openrouter.ai/z-ai/glm-5.3-flash", choice.getInformativeUrl());
	}

	@Test
	void anOpenRouterTranscriptionModelHasNoTokenContextNorTokenPrices() {
		GBaseTranscriptModelChoice choice = new GBaseTranscriptModelChoice();

		OpenRouterModelsListProviderService.fill(choice,
				openRouter("openai/whisper-1", 0, List.of(), List.of("audio")), "openrouter.ai");

		assertNull(choice.getContextLength());
		assertNull(choice.getPricingConditions());
	}

	@Test
	void aRegoloModelCarriesTheCapabilitiesLiteLlmFlags() {
		RegoloAIModel model = new RegoloAIModel();
		model.setModel_name("gpt-oss-120b");
		model.getModel_info().put("mode", "chat");
		model.getModel_info().put("max_tokens", 120000);
		model.getModel_info().put("max_input_tokens", 30000);
		model.getModel_info().put("max_output_tokens", 90000);
		model.getModel_info().put("supports_function_calling", true);
		model.getModel_info().put("supports_response_schema", true);
		model.getModel_info().put("supports_reasoning", true);
		GBaseChatModelChoice choice = new GBaseChatModelChoice();
		choice.setContextLength(30000);

		var meta = RegoloAIModelsListProviderService.metaInfos(model, choice);

		assertEquals(90000, meta.getMaxOutputToken());
		assertTrue(meta.getSupportsFunctionCalls());
		assertTrue(meta.getSupportsStructuredOutput());
		assertTrue(meta.getSupportsReasoning());
		assertFalse(meta.getSupportsVision());
	}
}
