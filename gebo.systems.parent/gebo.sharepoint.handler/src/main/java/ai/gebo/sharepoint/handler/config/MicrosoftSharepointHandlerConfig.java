package ai.gebo.sharepoint.handler.config;

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
@ConfigurationProperties(value = "ai.gebo.sharepoint")
@PropertySource(value = "classpath:/msgraph-templates/msgraph-templates.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class MicrosoftSharepointHandlerConfig {
	private List<GPromptLibraryReference> library = null;
	@Bean
	protected IGStaticPromptsProvider msGraphPromptsProvider() {

		return new PromptProvidersImplementation(this, library);
	}

	@Bean
	protected IGStaticPromptUseInfoProvider msGraphPromptsUseInfoProvider() {

		return new PromptProvidersImplementation(this, library);
	}
	 

}
