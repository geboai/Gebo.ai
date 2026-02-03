package ai.gebo.llms.deepsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.deepsearch")
@Data
public class DeepSearchDefaultConfig extends DeepSearchConfig {

	private int maxExternalSourcesSearchResults = 8;
	private boolean externalSourcesEnabled = false;
	private boolean deepSearchUIAllowChooseSources = false;
	private int perDataSourceMaxVisited = 25;
	private int perDataSourceMaxInputTokens = 5000000;
	private int perDataSourceMaxOutputTokens = 1000000;
	private DeepSearchVariant usedVariant = DeepSearchVariant.FULL_REACTIVE;

	public DeepSearchDefaultConfig() {
		this.setDescription("Default deep search configuration");
		this.setDefaultConfig(true);
		this.firstHopSimilarityThreashold = 0.5;
		this.secondHopSimilarityThreashold = 0.5;
		this.searchType = SearchType.MULTI_HOP;
		this.chatModelConfiguration = null;

		this.ragQueryOptions = new RagQueryOptions(1000000, CompletenessLevel.STRICT_QUERY_RELATED);
		this.ragQueryOptions.setTopK(100);
		this.ragQueryOptions.setSimilarityThreashold(0.5);
		this.graphRagTopN = 50;

	}

}
