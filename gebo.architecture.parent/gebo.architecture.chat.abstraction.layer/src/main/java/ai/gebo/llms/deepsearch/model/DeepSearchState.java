package ai.gebo.llms.deepsearch.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class DeepSearchState {
	private AtomicInteger totalSteps=new AtomicInteger(0); 
	private AtomicInteger doneSteps=new AtomicInteger(0);
	private AtomicInteger satisfactoryDocuments=new AtomicInteger(0);
	private AtomicBoolean completed=new AtomicBoolean(false); 
	private int satisfactoryDocumentsThreashold=0;
	public double calculateProcessedPercent() {
		double total = totalSteps.doubleValue();
		double processed = doneSteps.doubleValue();
		
		return total == 0.0 ? 0.0 : processed / total * 100.0;
	}

}
