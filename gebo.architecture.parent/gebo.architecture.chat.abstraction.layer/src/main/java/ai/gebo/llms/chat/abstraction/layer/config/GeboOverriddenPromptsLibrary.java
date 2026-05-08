package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptLibraryReference;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.overridden.prompts")
@Data
public class GeboOverriddenPromptsLibrary {
	private List<GPromptLibraryReference> library = null;

}
