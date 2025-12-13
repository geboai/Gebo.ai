package ai.gebo.llms.deepsearch.model;

import lombok.Data;

@Data
public abstract class AbstractDeepSearchEvent<InputType, OutputType> {
	public static enum DeepResearchEventStatus {
		PROCESSING, DONE, ERROR
	}

	InputType inputData = null;
	OutputType outputData = null;
	DeepResearchEventStatus status = null;
}
