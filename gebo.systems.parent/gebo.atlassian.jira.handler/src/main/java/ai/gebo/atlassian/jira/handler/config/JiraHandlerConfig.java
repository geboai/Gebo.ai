package ai.gebo.atlassian.jira.handler.config;

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
@ConfigurationProperties(value = "ai.gebo.jira")
@PropertySource(value = "classpath:/jira-prompts-library/jira-library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class JiraHandlerConfig {
	private List<GPromptLibraryReference> library = null;

	@Bean
	protected IGStaticPromptsProvider jiraPromptsProvider() {

		return new PromptProvidersImplementation(this, library);
	}

	@Bean
	protected IGStaticPromptUseInfoProvider jiraPromptsUseInfoProvider() {

		return new PromptProvidersImplementation(this, library);
	}

}
