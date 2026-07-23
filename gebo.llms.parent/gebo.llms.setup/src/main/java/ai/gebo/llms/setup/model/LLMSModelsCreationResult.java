package ai.gebo.llms.setup.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.llms.abstraction.layer.model.ChatModelsUses;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.setup.config.ModelType;
import lombok.Data;

/**
 * Outcome of a wizard {@code createLLMS} request. Besides the models actually
 * created, it reports the requests that could not be honoured because the chosen
 * model code (e.g. a preset from the vendor .yml) is no longer offered by the
 * provider. For each of those the currently available provider choices are
 * returned so the UI can let the user pick a replacement and resubmit.
 */
@Data
public class LLMSModelsCreationResult {

	private List<GBaseModelConfig> created = new ArrayList<>();
	private List<LLMUnresolvedModel> unresolved = new ArrayList<>();

	@Data
	public static class LLMUnresolvedModel {
		private ModelType type;
		/** For chat, distinguishes the default (CHAT) slot from the internal-services one. */
		private List<ChatModelsUses> uses;
		private String serviceHandler;
		private String requestedModelCode;
		private List<GBaseModelChoice> availableChoices = new ArrayList<>();
	}
}
