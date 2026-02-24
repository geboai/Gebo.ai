package ai.gebo.architecture.ai.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.ai.repository.PromptConfigRepository;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.impl.GPromptConfigDaoImpl;

@Configuration
public class PromptsLibrariesDaoConfig {
	;

	@Bean
	@Scope("singleton")
	public IGPromptConfigDao promptConfigDao(PromptConfigRepository repository,
			@Autowired(required = false) List<IGStaticPromptsProvider> promptsProviders) throws IOException {
		return new GPromptConfigDaoImpl(promptsProviders, repository);
	}

}
