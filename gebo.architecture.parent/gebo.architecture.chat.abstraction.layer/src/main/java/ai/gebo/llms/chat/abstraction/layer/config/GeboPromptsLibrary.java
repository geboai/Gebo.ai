package ai.gebo.llms.chat.abstraction.layer.config;

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
@ConfigurationProperties(value = "ai.gebo.prompts")
@PropertySource(value = "classpath:/prompts-library/prompts-library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class GeboPromptsLibrary {
	private List<GPromptLibraryReference> library = null;
	public static final String PROMPT_USE_STANDARD_CHAT_PROMPT = "standard-chat-prompt";
	public static final String PROMPT_USE_STANDARD_RAG_PROMPT = "standard-rag-prompt";
	public static final String DEFAULT_PIPELINE_CHAT_OUTPUT_PROMPT = "default-pipeline-chat-output-prompt";
	public static final String DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT = "default-pipeline-rag-output-prompt";
	public static final String DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT = "default-pipeline-routing-decision-prompt";
	public static final String DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT = "default-pipeline-initial-query-rewriting-prompt";
	public static final String DEFAULT_PIPELINE_TOOLS_CALL_OUTPUT_PROMPT = "default-pipeline-tools-call-output-prompt";
	public static final String DEFAULT_PIPELINE_RAG_SEARCH_PLANNER_PROMPT = "default-pipeline-rag-search-planner";
	public static final String DEFAULT_PIPELINE_CHAT_WITH_DOCUMENTS_PROMPT = "default-pipeline-chat-with-documents-prompt";
	public static final String DEFAULT_PIPELINE_PURE_SEARCH_CHOSE_DATASOURCES_PROMPT = "default-pipeline-pure-search-datasources-selection-prompt";
	public static final String DEFAULT_PIPELINE_PURE_SEARCH_SUMMARY_PROMPT = "default-pipeline-pure-search-summary-prompt";
	public static final String CHAT_HISTORY_DOCUMENTS_CONSOLIDATION = "chat-history-documents-consolidation";
	public static final String HISTORY_CONSOLIDATION_PROMPT = "history-consolidation-prompt";
	public static final String PROMPT_TEMPLATE_WIZARD_DEFAULT = "prompt-template-wizard-default";
	public static final String SUMMARIZE_CHAT_DESCRIPTION = "summarize-chat-description";

	public static final String DEEP_SEARCH_SEARCH_QUERY_EXTRACTION_PROMPT = "deep-search-search-query-extraction-prompt";
	public static final String DEEP_SEARCH_KEYWORD_GENERATION_PROMPT = "deep-search-keyword-generation-prompt";
	public static final String DEEP_SEARCH_CONTENT_RATING_PROMPT = "deep-search-content-rating-prompt";
	public static final String DEEP_SEARCH_CONSOLIDATION_PROMPT = "deep-search-consolidation-prompt";
	public static final String DEEP_SEARCH_FILE_ANALISYS_PROMPT = "deep-search-file-analisys-prompt";
	public static final String DEEP_SEARCH_DATA_SOURCES_FILE_ANALISYS_PROMPT = "deep-search-data-sources-file-analisys-prompt";
	public static final String DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT = "deep-search-empty-results-fallback-prompt";
	public static final List<String> ALL_PROMPT_CODES = List.of(DEFAULT_PIPELINE_CHAT_OUTPUT_PROMPT,
			DEFAULT_PIPELINE_RAG_OUTPUT_PROMPT, DEFAULT_PIPELINE_ROUTING_DECISION_PROMPT,
			DEFAULT_PIPELINE_TOOLS_CALL_OUTPUT_PROMPT, CHAT_HISTORY_DOCUMENTS_CONSOLIDATION,
			HISTORY_CONSOLIDATION_PROMPT, PROMPT_TEMPLATE_WIZARD_DEFAULT, SUMMARIZE_CHAT_DESCRIPTION,
			DEEP_SEARCH_SEARCH_QUERY_EXTRACTION_PROMPT, DEEP_SEARCH_KEYWORD_GENERATION_PROMPT,
			DEEP_SEARCH_CONTENT_RATING_PROMPT, DEEP_SEARCH_CONSOLIDATION_PROMPT, DEEP_SEARCH_FILE_ANALISYS_PROMPT,
			DEFAULT_PIPELINE_QUERY_REWRITING_PROMPT, PROMPT_USE_STANDARD_CHAT_PROMPT, PROMPT_USE_STANDARD_RAG_PROMPT,
			DEEP_SEARCH_DATA_SOURCES_FILE_ANALISYS_PROMPT, DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);

	@Bean
	public IGStaticPromptsProvider standardChatsPromptsProvider() {

		return new PromptProvidersImplementation(this, library);
	}

	@Bean
	public IGStaticPromptUseInfoProvider standardChatsPromptsUseInfoProvider() {

		return new PromptProvidersImplementation(this, library);
	}

}
