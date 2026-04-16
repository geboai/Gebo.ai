package ai.gebo.llms.deepsearch.service;

import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;

public interface IGDeepSearchConfigProvider {

	DeepSearchConfig get();

	DeepSearchDefaultConfig getDefaultConfig();

}