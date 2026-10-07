package ai.gebo.llms.deepsearch.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.LoggerFactory;
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

	// Number of documents processed in parallel during chunking.
	private int documentsParallelism = 2;
	// Number of in-flight LLM sub-analysis calls per data source (rails of the token-budget coordinator).
	// Set via ai.gebo.deepsearch.analysis-parallelism in application.yml.
	private int analysisParallelism = 2;
	// Max number of data sources analyzed concurrently. Caps total concurrent LLM calls at
	// maxConcurrentSources * analysisParallelism. These parallelism knobs are intentionally NOT on the
	// UI-editable DeepSearchConfig; they are sysadmin-only, set statically via application.yml
	// (ai.gebo.deepsearch.*).
	private int maxConcurrentSources = 2;

	private int offTopicChunksSkipDocumentThreashold = 3;
	private int perDataSourceMaxVisited = 25;
	private int internalKnowledgeDeepSearchTopK = 40;
	private int perDataSourceMaxInputTokens = 5000000;
	private int perDataSourceMaxOutputTokens = 1000000;
	private List<DeepSearchUserIntentThreashold> deepSearchUserIntentThreasholds = new ArrayList<DeepSearchUserIntentThreashold>();
	// The partial analyses are folded into a running report as they come, and the analysis stops once
	// the consolidation model judges the report enough (the verdict line of its prompt). false brings back
	// the stop on the count of batches the analysis model declared satisfactory
	// (deepSearchUserIntentThreasholds). Set via ai.gebo.deepsearch.sufficiency-check-enabled.
	private boolean sufficiencyCheckEnabled = true;
	// The batches analysed, by deliverable, before a report judged enough may stop the analysis.
	private List<DeepSearchSufficiencyMinimum> sufficiencyMinimumAnalysedBatches = new ArrayList<DeepSearchSufficiencyMinimum>();
	// Share of an analysis batch one piece of a document read whole (chat with documents) may fill: about
	// 1/factor pieces per batch, a batch exceeding its budget by one piece at most; it is also the least
	// budget an analysis lane goes on with (below it the lane hands its consolidation over). The batch
	// budget itself is the one budget formula (ai.gebo.llms.tokens-budget.factor). 0 < factor < 1.
	// Set via ai.gebo.deepsearch.chunk-filling-factor.
	private double chunkFillingFactor = DEFAULT_CHUNK_FILLING_FACTOR;

	public static final double DEFAULT_CHUNK_FILLING_FACTOR = 0.25d;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeepSearchUserIntentThreashold {
		List<DeliverableIntent> intents = new ArrayList<DeliverableIntent>();
		int maxInTopicSatisfactoryDocuments;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DeepSearchSufficiencyMinimum {
		List<DeliverableIntent> intents = new ArrayList<DeliverableIntent>();
		int minimumAnalysedBatches;
	}

	public DeepSearchDefaultConfig() {
		this.setDescription("Default deep search configuration");
		this.setDefaultConfig(true);
		this.firstHopSimilarityThreashold = 0.5;
		this.secondHopSimilarityThreashold = 0.5;
		this.searchType = SearchType.MULTI_HOP;
		this.documentsParallelism = 2;
		this.analysisParallelism = 2;
		this.maxConcurrentSources = 2;
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
		this.sufficiencyMinimumAnalysedBatches.add(new DeepSearchSufficiencyMinimum(
				new ArrayList<>(List.of(DeliverableIntent.QA, DeliverableIntent.UNKNOWN)), 1));
		this.sufficiencyMinimumAnalysedBatches.add(new DeepSearchSufficiencyMinimum(
				new ArrayList<>(List.of(DeliverableIntent.HOWTO, DeliverableIntent.SUMMARY)), 2));
		this.sufficiencyMinimumAnalysedBatches.add(new DeepSearchSufficiencyMinimum(
				new ArrayList<>(List.of(DeliverableIntent.DECISION, DeliverableIntent.ANALISYS)), 3));
		this.setAccessibleToAll(true);
		this.setPerDataSourceConfigured(false);
		this.setExternalSourceSearchEnabledByDefault(true);
	}

	/**
	 * The batches to analyse, for a deliverable, before a report judged enough may stop
	 * the analysis: the first configured entry when the deliverable is in none, 1 when
	 * nothing is configured.
	 */
	public int minimumAnalysedBatchesBeforeStop(DeliverableIntent intent) {
		final DeliverableIntent finalIntent = intent != null ? intent : DeliverableIntent.QA;
		if (this.sufficiencyMinimumAnalysedBatches == null || this.sufficiencyMinimumAnalysedBatches.isEmpty()) {
			return 1;
		}
		return Math.max(1,
				this.sufficiencyMinimumAnalysedBatches.stream()
						.filter(x -> x.intents != null && x.intents.contains(finalIntent)).findFirst()
						.orElse(this.sufficiencyMinimumAnalysedBatches.get(0)).getMinimumAnalysedBatches());
	}

	/**
	 * The tokens of a piece of a document read whole, out of the budget of a batch with
	 * no consolidation: the budget times the {@link #getChunkFillingFactor() filling
	 * factor}, at least 1.
	 */
	public int chunkTokens(long batchBudget) {
		return (int) Math.max(1, Math.min(Integer.MAX_VALUE, (long) (Math.max(0, batchBudget)
				* factorOrDefault(chunkFillingFactor, DEFAULT_CHUNK_FILLING_FACTOR, "chunk-filling-factor"))));
	}

	/** The factor when 0 < factor < 1, else its default (told once). */
	private static double factorOrDefault(double factor, double defaultValue, String name) {
		if (factor > 0d && factor < 1d) {
			return factor;
		}
		if (INVALID_FACTORS_WARNED.add(name)) {
			LoggerFactory.getLogger(DeepSearchDefaultConfig.class).warn("ai.gebo.deepsearch." + name + "="
					+ factor + " is not between 0 and 1 (excluded): " + defaultValue + " is used");
		}
		return defaultValue;
	}

	private static final Set<String> INVALID_FACTORS_WARNED = ConcurrentHashMap
			.newKeySet();

	public int getSatisfactorySubAnalisysThreashold(DeliverableIntent intent) {
		if (intent == null)
			intent = DeliverableIntent.QA;
		final DeliverableIntent finalIntent = intent;
		return this.deepSearchUserIntentThreasholds.stream().filter(x -> x.intents.contains(finalIntent)).findFirst()
				.orElse(this.deepSearchUserIntentThreasholds.get(0)).getMaxInTopicSatisfactoryDocuments();
	}

}
