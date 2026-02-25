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
import ai.gebo.architecture.ai.service.IStaticPromptsProviderRepositoryPattern;
import ai.gebo.architecture.ai.service.impl.GPromptConfigDaoImpl;
import ai.gebo.architecture.ai.service.impl.GPromptConfigDynamicSource;

@Configuration
public class PromptsLibrariesDaoConfig {
	@Bean
	@Scope("singleton")
	public IGPromptConfigDao promptConfigDao(PromptConfigRepository repository,
			IStaticPromptsProviderRepositoryPattern promptsProviders, GPromptConfigDynamicSource dynamicSources)
			throws IOException {
		return new GPromptConfigDaoImpl(repository, promptsProviders, dynamicSources);
	}

}
