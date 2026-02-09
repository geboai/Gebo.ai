package ai.gebo.llms.deepsearch.model;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import lombok.Data;

@Data
public class DeepSearchState {
	private AIDocumentsSet documentSearchResults = null;
	private String currentDataSourceHandlerRunning = null;
	private int ragDocumentsPointer = 0;
	private int ragDocumentFragmentPointer = 0;
	private int fragmentsCount = 0;
	private int elaboratedFragmentsCount = 0;
	private String consolidatedResult=null;
    private HashMap<String, Object> dataSourcesStatus = new HashMap<String, Object>();
	private HashMap<String, AtomicInteger> dataSourcesStatusTotalSteps = new HashMap<String, AtomicInteger>();
	private HashMap<String, AtomicInteger> dataSourcesStatusDoneSteps = new HashMap<String, AtomicInteger>();

	private int dataSourceAlreadyConsolidatedIndex = 0;

	public double calculateProcessedPercent() {
		double total = fragmentsCount;
		double processed = elaboratedFragmentsCount;
		for (AtomicInteger nr : dataSourcesStatusTotalSteps.values()) {
			total += nr.doubleValue();
		}
		for (AtomicInteger nr : dataSourcesStatusDoneSteps.values()) {
			processed += nr.doubleValue();
		}
		return total == 0.0 ? 0.0 : processed / total * 100.0;
	}

}
