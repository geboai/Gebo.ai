package ai.gebo.llms.deepseek.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.deepseek.api.DeepSeekApi.ChatCompletionRequest.ReasoningEffort;

import ai.gebo.llms.deepseek.model.GDeepseekChatModelChoice;
import ai.gebo.llms.deepseek.services.DeepseekModelsLookupService.DeepseekModelsList;
import tools.jackson.databind.json.JsonMapper;

/**
 * Fed with the response documented at https://api-docs.deepseek.com/api/list-models
 */
class DeepseekModelsLookupServiceTest {

	static final String RESPONSE = """
			{"object":"list","data":[{"id":"deepseek-flash","object":"model","owned_by":"deepseek",
			"name":"DeepSeek-V4.1-Flash","context_window":1048576,"max_output_tokens":393216,
			"input_modalities":["text","image"],"output_modalities":["text"],
			"effort":{"supported_levels":["low","high","max"],"default_level":"high"},
			"api_capabilities":{"anthropic_messages":{"system_prompt_update":"in-history"}}}]}
			""";

	@Test
	void theModelsApiFieldsReachTheChoice() {
		DeepseekModelsList list = JsonMapper.builder().build().readValue(RESPONSE, DeepseekModelsList.class);
		assertEquals("deepseek", list.getData().get(0).getOwned_by());

		GDeepseekChatModelChoice choice = DeepseekModelsLookupService.toChoice(list.getData().get(0));

		assertEquals("deepseek-flash", choice.getCode());
		assertEquals("DeepSeek-V4.1-Flash (deepseek-flash)", choice.getDescription());
		assertEquals(1048576, choice.getContextLength());
		assertEquals(393216, choice.getMetaInfos().getMaxOutputToken());
		assertTrue(choice.getMetaInfos().getSupportsVision());
		assertTrue(choice.getMetaInfos().getSupportsReasoning());
		assertTrue(choice.getSupportsFunctionCalls());
		assertEquals(List.of("low", "high", "max"), choice.getModelDetails().get(DeepseekModelsLookupService.EFFORT_LEVELS));
		assertEquals("high", choice.getModelDetails().get(DeepseekModelsLookupService.DEFAULT_EFFORT));
	}

	@Test
	void theConfiguredBaseUrlIsListedLikeTheChatModelsUseIt() {
		assertEquals("https://api.deepseek.com/models", DeepseekModelsLookupService.modelsUrl(null));
		assertEquals("https://proxy.example/deepseek/models",
				DeepseekModelsLookupService.modelsUrl("https://proxy.example/deepseek/"));
	}

	@Test
	void maximumThinkingIsTheHighestLevelTheModelLists() {
		assertEquals(ReasoningEffort.MAX, DeepseekChatModelConfigurationSupportService
				.maximumEffort(Map.of(DeepseekModelsLookupService.EFFORT_LEVELS, List.of("low", "high", "max"))));
		assertEquals(ReasoningEffort.HIGH, DeepseekChatModelConfigurationSupportService
				.maximumEffort(Map.of(DeepseekModelsLookupService.EFFORT_LEVELS, List.of("low", "high"))));
		assertNull(DeepseekChatModelConfigurationSupportService
				.maximumEffort(Map.of(DeepseekModelsLookupService.EFFORT_LEVELS, List.of("low"))));
		// A configuration saved before the levels were read keeps asking for MAX
		assertEquals(ReasoningEffort.MAX, DeepseekChatModelConfigurationSupportService.maximumEffort(Map.of()));
	}
}
