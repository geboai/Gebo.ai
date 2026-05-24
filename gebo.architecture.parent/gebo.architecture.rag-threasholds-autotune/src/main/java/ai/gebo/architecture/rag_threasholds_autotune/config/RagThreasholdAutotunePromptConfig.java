package ai.gebo.architecture.rag_threasholds_autotune.config;

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
@ConfigurationProperties(value = "ai.gebo.rag-threashold-autotune.prompts")
@PropertySource(value = "classpath:/rag-autotune/rag-autotune.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class RagThreasholdAutotunePromptConfig {
	public final static String RAG_AUTOTUNE_RATING_PROMPT = "rag-autotune-rating";
	public final  static  String RAG_IN_TOPIC_QUERY_GENERATOR_PROMPT = "in-topic-query-generator";
	private List<GPromptTemplateLibraryReference> library = null;

	@Bean
	public IGStaticPromptsProvider ragThreasholdAlgorithmPromptsProvider() {

		return new PromptTemplateProvidersImplementation(this, library);
	}

	@Bean
	public IGStaticPromptUseInfoProvider ragThreasholdAlgorithmPromptsUseInfoProvider() {

		return new PromptTemplateProvidersImplementation(this, library);
	}

}
