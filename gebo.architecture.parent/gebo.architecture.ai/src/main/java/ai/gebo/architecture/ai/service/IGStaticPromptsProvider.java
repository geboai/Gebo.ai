package ai.gebo.architecture.ai.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.ai.model.GPromptConfig;

public interface IGStaticPromptsProvider {
	public List<GPromptConfig> promptsList() throws IOException;

	public default String getId() {
		return this.getClass().getName();
	}
}
