package ai.gebo.llms.setup.config;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LLMModelPresetChoice {
	@NotNull
	String code = null;
	String description = null;
	boolean defaultChoice = false;
	Integer contextWindow = null;
	List<ChatModelsUses> uses = null;
	Integer maxGeneratedTokens = null;
	ChatModelThinkingOption thinking = null;
	/**
	 * The temperature the model is created with; null leaves it to the model's parameter
	 * rules of the models library: temperature 0 where the model takes one, none where
	 * it refuses it.
	 */
	Double temperature = null;
	/**
	 * The thinking level of the model when it is created to serve the internal services,
	 * in place of {@link #thinking}: one model can think for chat and not for the
	 * internal services. Null takes {@link #thinking}.
	 */
	ChatModelThinkingOption internalServicesThinking = null;

	/**
	 * The thinking level of the model created for the uses: the internal services one
	 * when it serves them and the preset gives one, else {@link #thinking}.
	 *
	 * @param uses the uses the model is created for, may be null
	 */
	public ChatModelThinkingOption thinkingFor(List<ChatModelsUses> uses) {
		if (internalServicesThinking != null && uses != null && uses.contains(ChatModelsUses.INTERNAL_SERVICES))
			return internalServicesThinking;
		return thinking;
	}
}
