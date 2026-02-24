package ai.gebo.atlassian.jira.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.ai.service.AbstractStaticPromptsLibraryProvider;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.jira")
@Data
@PropertySource(value = "classpath:/jira-prompts-library/index.yml", factory = GeboYamlPropertySourceFactory.class)
public class JiraHandlerConfig extends AbstractStaticPromptsLibraryProvider {

	List<GPromptLibraryReference> library = new ArrayList();

	@Override
	protected List<GPromptLibraryReference> getReferences() {

		return getLibrary();
	}
}
