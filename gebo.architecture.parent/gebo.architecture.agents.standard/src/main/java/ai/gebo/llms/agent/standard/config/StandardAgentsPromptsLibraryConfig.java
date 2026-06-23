package ai.gebo.llms.agent.standard.config;

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
import ai.gebo.llms.chat.abstraction.layer.config.GeboOverriddenPromptsLibrary;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.agents.prompts")
@Data
@PropertySource(value = "classpath:/agents-prompt-library/agents-prompt-library.yml", factory = GeboYamlPropertySourceFactory.class)
public class StandardAgentsPromptsLibraryConfig {
	public static final String COORDINATOR_AGENT_PROMPT = "controller-coordinator-agent-prompt";
	public static final String REPORT_AND_ANSWER_WRITER_AGENT_PROMPT = "report-answer-writer-agent-prompt";
	private List<GPromptTemplateLibraryReference> library = null;

	@Bean
	public IGStaticPromptsProvider standardAgentsPromptsProvider(GeboOverriddenPromptsLibrary overridenLibrary) {

		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary());
	}

	@Bean
	public IGStaticPromptUseInfoProvider standardAgentsPromptsUseInfoProvider(
			GeboOverriddenPromptsLibrary overridenLibrary) {

		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary());
	}

}
