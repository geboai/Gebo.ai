package ai.gebo.llms.openai_compat.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import lombok.Data;

@Document
public class GenericOpenAIAPIRankerModelConfig extends GBaseRankerModelConfig<GenericOpenAIAPIRankerModelChoice> {

	

}
