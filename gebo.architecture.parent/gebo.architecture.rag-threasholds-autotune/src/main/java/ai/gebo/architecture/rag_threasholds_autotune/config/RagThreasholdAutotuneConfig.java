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
