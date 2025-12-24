package ai.gebo.llms.deepsearch.model.events;

import lombok.Data;

@Data
public abstract class AbstractDeepSearchEvent<InputType, OutputType> {
	
	private double processPercentage=0.0;
	private InputType inputData = null;
	private OutputType outputData = null;
	
}
