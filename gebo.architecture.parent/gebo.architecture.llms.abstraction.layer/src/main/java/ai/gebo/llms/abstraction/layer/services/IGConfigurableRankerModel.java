package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.ranker.model.RankerModel;

public interface IGConfigurableRankerModel<ModelConfig extends GBaseRankerModelConfig>
		extends IGConfigurableModel<ModelConfig, GRankerModelType> {
	public RankerModel getRankerModel();
}
