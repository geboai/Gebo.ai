package ai.gebo.llms.deepsearch.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

@Data
public class DeepSearchConfig extends GBaseObject {
	protected String consolidationPrompt = null;
	protected String analisysPrompt = null;
	protected RagQueryOptions ragQueryOptions;
	protected Double firstHopSimilarityThreashold = null;
	protected Double secondHopSimilarityThreashold = null;
	protected Integer graphRagTopN = null;
	protected Integer tokensLimit = null;
	@GObjectReference(referencedType = GBaseChatModelConfig.class,referencesExtensions = true)
	protected GObjectRef<GBaseChatModelConfig> chatModelConfiguration = null;
	protected Boolean defaultConfig = null;
	@GObjectReference(referencedType = GChatProfileConfiguration.class)
	protected String chatProfileCode = null;

}
