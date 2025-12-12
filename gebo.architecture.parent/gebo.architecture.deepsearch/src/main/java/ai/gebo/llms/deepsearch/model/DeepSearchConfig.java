package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

@Data
public class DeepSearchConfig extends GBaseObject {
	protected String consolidationPrompt = null;
	protected String analisysPrompt = null;
	protected RagQueryOptions ragQueryOptions;
	protected Integer tokensLimit = null;
	protected GObjectRef<GBaseChatModelConfig> chatModelConfiguration = null;

}
