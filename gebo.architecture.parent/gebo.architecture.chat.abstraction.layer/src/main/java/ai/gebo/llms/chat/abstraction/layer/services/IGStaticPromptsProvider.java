package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;

public interface IGStaticPromptsProvider {
	public List<GPromptConfig> promptsList();
}
