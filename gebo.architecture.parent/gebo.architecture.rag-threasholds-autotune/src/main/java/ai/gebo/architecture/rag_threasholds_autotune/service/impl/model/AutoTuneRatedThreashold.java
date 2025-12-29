package ai.gebo.architecture.rag_threasholds_autotune.service.impl.model;

import lombok.ToString;

@ToString
public class AutoTuneRatedThreashold {
	public 	double threashold = 0.0;
	public double rating = 0.0;
	public double totalDistance = 0.0;
	public double averageDistance = 0.0;
	public double resultsPoints = 0.0;
}