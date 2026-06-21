package ai.gebo.llms.deepsearch.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions.CompletenessLevel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(value = "ai.gebo.deepsearch")
@Data
public class DeepSearchDefaultConfig extends DeepSearchConfig {

	private int maxExternalSourcesSearchResults = 20;
	
	private int offTopicChunksSkipDocumentThreashold = 3;
	private int perDataSourceMaxVisited = 25;
	private int internalKnowledgeDeepSearchTopK = 40;
	private int perDataSourceMaxInputTokens = 5000000;
	private int perDataSourceMaxOutputTokens = 1000000;
	private List<DeepSearchUserIntentThreashold> deepSearchUserIntentThreasholds = new ArrayList<DeepSearchUserIntentThreashold>();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeepSearchUserIntentThreashold {
		List<DeliverableIntent> intents = new ArrayList<DeliverableIntent>();
		int maxInTopicSatisfactoryDocuments;
	}

	public DeepSearchDefaultConfig() {
		this.setDescription("Default deep search configuration");
		this.setDefaultConfig(true);
		this.firstHopSimilarityThreashold = 0.5;
		this.secondHopSimilarityThreashold = 0.5;
		this.searchType = SearchType.MULTI_HOP;
		this.documentsParallelism = 2;
		this.ragQueryOptions = new RagQueryOptions(1000000, CompletenessLevel.STRICT_QUERY_RELATED);
		this.ragQueryOptions.setTopK(100);
		this.ragQueryOptions.setSimilarityThreashold(0.5);
		this.graphRagTopN = 50;
		DeepSearchUserIntentThreashold lowerLevelsThreashold = new DeepSearchUserIntentThreashold(
				List.of(DeliverableIntent.QA, DeliverableIntent.UNKNOWN), 3);
		DeepSearchUserIntentThreashold midLevelsThreashold = new DeepSearchUserIntentThreashold(
				List.of(DeliverableIntent.HOWTO, DeliverableIntent.SUMMARY), 8);
		DeepSearchUserIntentThreashold highLevelsThreashold = new DeepSearchUserIntentThreashold(
				List.of(DeliverableIntent.DECISION, DeliverableIntent.ANALISYS), 20);
		this.deepSearchUserIntentThreasholds.add(lowerLevelsThreashold);
		this.deepSearchUserIntentThreasholds.add(midLevelsThreashold);
		this.deepSearchUserIntentThreasholds.add(highLevelsThreashold);
		this.setAccessibleToAll(true);
		this.setPerDataSourceConfigured(false);
	}

	public int getSatisfactorySubAnalisysThreashold(DeliverableIntent intent) {
		if (intent == null)
			intent = DeliverableIntent.QA;
		final DeliverableIntent finalIntent = intent;
		return this.deepSearchUserIntentThreasholds.stream().filter(x -> x.intents.contains(finalIntent)).findFirst()
				.orElse(this.deepSearchUserIntentThreasholds.get(0)).getMaxInTopicSatisfactoryDocuments();
	}

}
