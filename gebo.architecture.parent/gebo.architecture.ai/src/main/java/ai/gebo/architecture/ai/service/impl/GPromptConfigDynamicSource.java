package ai.gebo.architecture.ai.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.repository.PromptConfigRepository;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import lombok.AllArgsConstructor;

/**
 * AI generated comments A dynamic configuration source for GPromptConfig
 * entities integrated with PromptConfigRepository.
 */
@Service
@AllArgsConstructor
public class GPromptConfigDynamicSource

		implements IGDynamicConfigurationSource<GPromptConfig> {
	final PromptConfigRepository directRepo;

	@Override
	public List<GPromptConfig> getConfigurations() {
		return directRepo.findAll();
	}

	@Override
	public GPromptConfig findByCode(String code) {
		Optional<GPromptConfig> opt = directRepo.findById(code);
		return opt.isPresent() ? opt.get() : null;
	}
	// Inherits all functionality from GDynamicConfigurationSourceAdapter
}