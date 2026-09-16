package ai.gebo.architecture.rag_threasholds_autotune.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai.gebo.rag-threashold-autotune.config")
public class RagThreasholdAutotuneConfig {
	OptimizedThreashold defaultOptimizedThreashold = new OptimizedThreashold();
	private double documentsCardinalityAddedPercentTrigger = 5.0;
	private int dayElapsedWithoutTuning = 3;
	private int sampleFragmentsMinTokenLength = 100;
	private int autotuneMaxGeneratedQuestions = 12;
	/**
	 * Share of the answerable questions that a threshold must still answer to be
	 * eligible. Coverage is a floor rather than the objective: requiring every
	 * question to be answered lets a single hard question drag the threshold to the
	 * loose end of the bracket, where the remaining questions retrieve mostly noise.
	 */
	private double minimumAnsweredQuestionsShare = 0.66;
	/**
	 * How many candidates to draw for every fragment finally kept. The extra ones are
	 * the margin the representativeness selection needs: with a factor of one there is
	 * nothing to choose between.
	 */
	private int sampleCandidatesPerFragment = 3;
	/**
	 * Fragments retrieved when probing how well one candidate represents the corpus.
	 */
	private int sampleRepresentativenessProbeTopK = 20;
	private InitialAutotunePhrases autotuneSamples = null;
	private boolean enabled = true;

	@Data
	public static class InitialAutotunePhrases {
		private double defaultThreashold = 0.5;
		private List<String> phrases = null;
	}

	public RagThreasholdAutotuneConfig() {
		defaultOptimizedThreashold.setFirstHopOptimizedThreashold(0.6);
		defaultOptimizedThreashold.setSecondHopOptimizedThreashold(0.6);
		defaultOptimizedThreashold.setOptimizedThreashold(0.6);
	}

}
