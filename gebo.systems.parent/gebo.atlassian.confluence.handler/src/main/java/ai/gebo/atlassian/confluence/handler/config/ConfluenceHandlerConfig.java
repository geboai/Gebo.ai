package ai.gebo.atlassian.confluence.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.ai.service.IGStaticPromptUseInfoProvider;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.PromptProvidersImplementation;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.confluence")
@PropertySource(value = "classpath:/confluence-prompts-library/confluence-library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class ConfluenceHandlerConfig  {
	private List<GPromptLibraryReference> library = new ArrayList<GPromptLibraryReference>();

	@Bean
	protected IGStaticPromptsProvider confluencePromptsProvider() {

		return new PromptProvidersImplementation(this,library);
	}

	@Bean
	protected IGStaticPromptUseInfoProvider confluencePromptsUseInfoProvider() {

		return new PromptProvidersImplementation(this,library);
	}

}
