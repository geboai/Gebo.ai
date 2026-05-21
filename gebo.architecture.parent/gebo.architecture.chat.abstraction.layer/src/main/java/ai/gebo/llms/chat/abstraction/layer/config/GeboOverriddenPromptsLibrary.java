package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.overridden.prompts")
@Data
public class GeboOverriddenPromptsLibrary {
	private List<GPromptTemplateLibraryReference> library = null;

}
