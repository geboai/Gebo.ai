package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.anthropic.models.messages.OutputConfig;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.llms.anthropic.model.AnthropicThinkingSupport;
import ai.gebo.llms.anthropic.services.AnthropicChatModelConfigurationSupportService.ThinkingRequest;

class AnthropicThinkingRequestTest {

	static final AnthropicThinkingSupport CURRENT = new AnthropicThinkingSupport(true, false, true,
			Set.of("low", "medium", "high", "xhigh", "max"));
	static final AnthropicThinkingSupport NO_MAX = new AnthropicThinkingSupport(true, false, true,
			Set.of("low", "medium", "high", "xhigh"));
	static final AnthropicThinkingSupport ALWAYS_THINKING = new AnthropicThinkingSupport(true, false, false,
			Set.of("low", "medium", "high"));
	static final AnthropicThinkingSupport BUDGET_ONLY = new AnthropicThinkingSupport(false, true, true, Set.of());

	static ThinkingRequest request(ChatModelThinkingOption option, AnthropicThinkingSupport support, String code) {
		return AnthropicChatModelConfigurationSupportService.thinkingRequest(option, support, code);
	}

	@Test
	void theCapabilitiesChooseTheEffortLevel() {
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.LOW),
				request(ChatModelThinkingOption.LOW_THINKING, CURRENT, "claude-opus-5-5"));
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.MEDIUM),
				request(ChatModelThinkingOption.MEDIUM_THINKING, CURRENT, "claude-opus-5-5"));
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.MAX),
				request(ChatModelThinkingOption.HIGH_THINKING, CURRENT, "claude-opus-5-5"));
		// Maximum thinking is the highest level the model has
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.XHIGH),
				request(ChatModelThinkingOption.HIGH_THINKING, NO_MAX, "claude-x"));
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.HIGH),
				request(ChatModelThinkingOption.HIGH_THINKING, ALWAYS_THINKING, "claude-x"));
	}

	@Test
	void thinkingIsTurnedOffOnlyWhereTheModelAcceptsIt() {
		assertEquals(new ThinkingRequest(true, null),
				request(ChatModelThinkingOption.NO_THINKING, CURRENT, "claude-sonnet-5-5"));
		assertNull(request(ChatModelThinkingOption.NO_THINKING, ALWAYS_THINKING, "claude-fable-5-1"));
	}

	@Test
	void aBudgetOnlyModelKeepsTheProviderDefault() {
		assertNull(request(ChatModelThinkingOption.HIGH_THINKING, BUDGET_ONLY, "claude-haiku-4-5"));
		assertEquals(new ThinkingRequest(true, null),
				request(ChatModelThinkingOption.NO_THINKING, BUDGET_ONLY, "claude-haiku-4-5"));
	}

	@Test
	void withoutCapabilitiesTheModelCodeDecidesAsBefore() {
		assertNull(request(ChatModelThinkingOption.HIGH_THINKING, null, "claude-haiku-4-5"));
		assertEquals(new ThinkingRequest(false, OutputConfig.Effort.MAX),
				request(ChatModelThinkingOption.HIGH_THINKING, null, "claude-opus-5-5"));
		assertEquals(new ThinkingRequest(true, null),
				request(ChatModelThinkingOption.NO_THINKING, null, "claude-opus-5-5"));
		assertNull(request(ChatModelThinkingOption.AUTO, null, "claude-opus-5-5"));
	}
}
