package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.anthropic.core.ObjectMappers;
import com.anthropic.models.beta.models.BetaModelInfo;

import ai.gebo.llms.anthropic.model.AnthropicThinkingSupport;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;

/**
 * The mapping of the Models API answer, fed with the json shape the api documents
 * (https://platform.claude.com/docs/en/api/http/beta/models/retrieve), including the
 * fields newer than the sdk on the classpath.
 */
class AnthropicModelInfoMapperTest {

	static BetaModelInfo parse(String json) throws Exception {
		return ObjectMappers.jsonMapper().readValue(json, BetaModelInfo.class);
	}

	static String capability(boolean supported) {
		return "{\"supported\":" + supported + "}";
	}

	static String capabilities(boolean adaptive, boolean budget, boolean disabled, boolean effort, boolean xhigh,
			boolean max) {
		return "{\"batch\":" + capability(true) + ",\"citations\":" + capability(true) + ",\"code_execution\":"
				+ capability(true) + ",\"compaction\":{\"supported\":true,\"summarize\":" + capability(true) + "}"
				+ ",\"context_management\":{\"supported\":true}" + ",\"effort\":{\"supported\":" + effort
				+ ",\"low\":" + capability(effort) + ",\"medium\":" + capability(effort) + ",\"high\":"
				+ capability(effort) + ",\"xhigh\":" + capability(xhigh) + ",\"max\":" + capability(max) + "}"
				+ ",\"image_input\":" + capability(true) + ",\"pdf_input\":" + capability(true)
				+ ",\"server_tools\":{\"supported\":true,\"web_search\":" + capability(true) + ",\"code_execution\":"
				+ capability(true) + "}" + ",\"structured_outputs\":" + capability(true)
				+ ",\"thinking\":{\"supported\":true,\"types\":{\"adaptive\":" + capability(adaptive) + ",\"enabled\":"
				+ capability(budget) + ",\"disabled\":" + capability(disabled) + "}}}";
	}

	static String model(String id, String displayName, String lifecycle, String retiresAt, String capabilities) {
		return "{\"type\":\"model\",\"id\":\"" + id + "\",\"display_name\":\"" + displayName + "\""
				+ ",\"created_at\":\"2026-07-24T00:00:00Z\",\"lifecycle\":\"" + lifecycle + "\",\"line\":\"opus\""
				+ ",\"deprecated_at\":" + (retiresAt != null ? "\"2026-08-01T00:00:00Z\"" : "null")
				+ ",\"retires_at\":" + (retiresAt != null ? "\"" + retiresAt + "\"" : "null")
				+ ",\"max_input_tokens\":1000000,\"max_tokens\":128000,\"allowed_fallback_models\":[]"
				+ (capabilities != null ? ",\"capabilities\":" + capabilities : "") + "}";
	}

	@Test
	void aCurrentModelCarriesItsLimitsCapabilitiesAndThinking() throws Exception {
		GAnthropicChatModelChoice choice = AnthropicModelInfoMapper.toChoice(parse(model("claude-opus-5-5",
				"Claude Opus 5.5", "active", null, capabilities(true, false, false, true, true, true))));

		assertEquals("claude-opus-5-5", choice.getCode());
		assertEquals("Claude Opus 5.5", choice.getDescription());
		assertEquals(1000000, choice.getContextLength());
		assertEquals(128000, choice.getMetaInfos().getMaxOutputToken());
		assertTrue(choice.getSupportsFunctionCalls());
		assertTrue(choice.getSupportsStructuredOutput());
		assertTrue(choice.getMetaInfos().getSupportsVision());
		assertTrue(choice.getMetaInfos().getSupportsReasoning());
		assertFalse(choice.getMetaInfos().getDeprecated());
		assertNull(choice.getMetaInfos().getRetirementDate());
		assertEquals("opus", choice.getModelDetails().get(AnthropicModelInfoMapper.LINE));
		assertEquals("active", choice.getModelDetails().get(AnthropicModelInfoMapper.LIFECYCLE));

		AnthropicThinkingSupport thinking = AnthropicThinkingSupport.readFrom(choice.getModelDetails());
		assertTrue(thinking.adaptive());
		assertFalse(thinking.budget());
		// The api says this model refuses thinking.type=disabled
		assertFalse(thinking.disabled());
		assertEquals(Set.of("low", "medium", "high", "xhigh", "max"), thinking.effortLevels());
	}

	@Test
	void aDeprecatedModelSaysWhenItRetires() throws Exception {
		GAnthropicChatModelChoice choice = AnthropicModelInfoMapper.toChoice(parse(model("claude-sonnet-4-5-20250929",
				"Claude Sonnet 4.5", "deprecated", "2026-11-30T00:00:00Z",
				capabilities(false, true, true, false, false, false))));

		assertEquals("Claude Sonnet 4.5 (deprecated, retires 2026-11-30)", choice.getDescription());
		assertTrue(choice.getMetaInfos().getDeprecated());
		assertEquals("2026-11-30T00:00:00Z", choice.getMetaInfos().getRetirementDate());
		assertEquals("2026-08-01T00:00:00Z", choice.getMetaInfos().getDeprecationDate());

		AnthropicThinkingSupport thinking = AnthropicThinkingSupport.readFrom(choice.getModelDetails());
		assertFalse(thinking.adaptive());
		assertTrue(thinking.budget());
		assertTrue(thinking.disabled());
		assertTrue(thinking.effortLevels().isEmpty());
	}

	@Test
	void aModelWithoutCapabilitiesIsStillListed() throws Exception {
		GAnthropicChatModelChoice choice = AnthropicModelInfoMapper
				.toChoice(parse(model("claude-new", "Claude New", "active", null, null)));

		assertEquals("claude-new", choice.getCode());
		assertEquals(1000000, choice.getContextLength());
		assertNull(choice.getSupportsStructuredOutput());
		assertNull(AnthropicThinkingSupport.readFrom(choice.getModelDetails()));
	}
}
