package ai.gebo.architecture.rag_threasholds_autotune.config;

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

	public RagThreasholdAutotuneConfig() {
		defaultOptimizedThreashold.setFirstHopOptimizedThreashold(0.7);
		defaultOptimizedThreashold.setSecondHopOptimizedThreashold(0.7);
		defaultOptimizedThreashold.setOptimizedThreashold(0.7);
	}

}
