package ai.gebo.llms.deepsearch.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.repository.DeepSearchConfigRepository;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class DeepSearchConfigProviderImpl implements IGDeepSearchConfigProvider {
	private final DeepSearchDefaultConfig defaultConfig;
	private final DeepSearchConfigRepository configRepository;

	@Override
	public DeepSearchConfig get() {
		DeepSearchConfig cfg = configRepository.findByDefaultConfig(Boolean.TRUE);
		return cfg != null ? cfg : defaultConfig;
	}

	@Override
	public DeepSearchDefaultConfig getDefaultConfig() {
		return defaultConfig;
	}
}
