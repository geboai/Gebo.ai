package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.model.OperationStatus;

public interface IGRankerModelConfigurationSupportService<ModelChoice extends GBaseRankerModelChoice, ModelConfig extends GBaseRankerModelConfig>
		extends
		IGModelConfigurationSupportService<GRankerModelType, ModelChoice, ModelConfig, IGConfigurableRankerModel> {
	public static final String STANDARD_RERANK_RELATIVE_URL = "/v1/rerank";
	public static final String STANDARD_RERANK_MODEL_FILTER = "rerank";

	IGConfigurableRankerModel create(ModelConfig config) throws LLMConfigException;

	@Override
	default OperationStatus<List<ModelChoice>> getModelChoices(ModelConfig config) {
		OperationStatus<List<ModelChoice>> rawResult = this.getRawModelChoices(config);
		if (rawResult.getResult() != null && !rawResult.getResult().isEmpty()) {
			List<ModelChoice> originalList = rawResult.getResult();
			List<ModelChoice> filteredList = originalList.stream().filter(
					x -> x.getCode() != null && x.getCode().toLowerCase().contains(STANDARD_RERANK_MODEL_FILTER))
					.toList();
			rawResult.setResult(filteredList);
		}
		return rawResult;
	}

	OperationStatus<List<ModelChoice>> getRawModelChoices(ModelConfig config);
}
