package ai.gebo.webdavcms.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import ai.gebo.architecture.ai.service.IGStaticPromptUseInfoProvider;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.PromptTemplateProvidersImplementation;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.webdav")
@PropertySource(value = "classpath:/webdav-prompts-library/webdav-library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class WebdavHandlerConfig {

	private List<GPromptTemplateLibraryReference> library = new ArrayList<GPromptTemplateLibraryReference>();

	@Bean
	protected IGStaticPromptsProvider webdavPromptsProvider() {
		return new PromptTemplateProvidersImplementation(this, library);
	}

	@Bean
	protected IGStaticPromptUseInfoProvider webdavPromptsUseInfoProvider() {
		return new PromptTemplateProvidersImplementation(this, library);
	}
}