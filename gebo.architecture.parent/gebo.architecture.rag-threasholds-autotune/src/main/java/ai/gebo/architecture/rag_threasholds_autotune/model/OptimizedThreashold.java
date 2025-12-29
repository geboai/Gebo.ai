package ai.gebo.architecture.rag_threasholds_autotune.model;

import lombok.Data;

@Data
public class OptimizedThreashold {

	private double optimizedThreashold = 0.0;
	private double firstHopOptimizedThreashold = 0.0;
	private double secondHopOptimizedThreashold = 0.0;

}
