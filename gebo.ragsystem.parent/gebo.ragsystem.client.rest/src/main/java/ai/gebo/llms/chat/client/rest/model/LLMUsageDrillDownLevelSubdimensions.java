package ai.gebo.llms.chat.client.rest.model;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.ModelType;
import lombok.Data;

@Data
public class LLMUsageDrillDownLevelSubdimensions {
	private List<String> providerId;
	private List<String> username;
	private List<String> model;
	private List<String> callerStack;
	private List<ModelType> modelType;
	private List<Integer> year;
	private List<Integer> month;
}
