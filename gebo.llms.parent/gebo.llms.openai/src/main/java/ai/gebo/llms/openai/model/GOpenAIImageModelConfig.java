package ai.gebo.llms.openai.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;

@Document
public class GOpenAIImageModelConfig extends GBaseImageModelConfig<GOpenAIImageModelChoice> {

}
