package ai.gebo.llms.openai_compat.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;

@Document
public class GenericOpenAIAPIImageModelConfig extends GBaseImageModelConfig<GenericOpenAIAPIImageModelChoice> {

}
