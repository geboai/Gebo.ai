package ai.gebo.architecture.ai.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;

public interface IGStaticPromptsProvider {
	public List<GPromptTemplateConfig> promptsList() throws IOException;

	public default String getId() {
		return this.getClass().getName();
	}
}
