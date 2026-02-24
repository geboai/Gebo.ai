package ai.gebo.atlassian.confluence.handler.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.ai.service.AbstractStaticPromptsLibraryProvider;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.confluence")
@Data
@PropertySource(value = "classpath:/confluence-prompts-library/index.yml", factory = GeboYamlPropertySourceFactory.class)
public class ConfluenceHandlerConfig extends AbstractStaticPromptsLibraryProvider {

	List<GPromptLibraryReference> library = new ArrayList<GPromptLibraryReference>();

	public ConfluenceHandlerConfig() {

	}

	@Override
	protected List<GPromptLibraryReference> getReferences() {
		return getLibrary();
	}

}
