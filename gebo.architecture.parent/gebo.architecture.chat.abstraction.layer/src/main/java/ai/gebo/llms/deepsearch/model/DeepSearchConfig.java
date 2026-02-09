package ai.gebo.llms.deepsearch.model;

import ai.gebo.architecture.rag.support.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepSearchConfig extends GBaseObject {
	public static enum SearchType {
		SINGLE_HOP, MULTI_HOP
	}

	@NotNull
	protected SearchType searchType = null;

	protected RagQueryOptions ragQueryOptions;
	protected Double firstHopSimilarityThreashold = null;
	protected Double secondHopSimilarityThreashold = null;
	protected Integer graphRagTopN = null;
	protected Integer tokensLimit = null;
	protected int documentsParallelism = 2;
	protected Boolean manualThreasholdsConfiguration = null;
	@GObjectReference(referencedType = GBaseChatModelConfig.class, referencesExtensions = true)
	protected GObjectRef<GBaseChatModelConfig> chatModelConfiguration = null;
	protected Boolean defaultConfig = null;
	@GObjectReference(referencedType = GChatProfileConfiguration.class)
	protected String chatProfileCode = null;

	public DeepSearchConfig(DeepSearchConfig c) {
		this(c.searchType, c.ragQueryOptions, c.firstHopSimilarityThreashold, c.secondHopSimilarityThreashold,
				c.graphRagTopN, c.tokensLimit, c.documentsParallelism, c.manualThreasholdsConfiguration,
				c.chatModelConfiguration, c.defaultConfig, c.chatProfileCode);
		this.setCode(c.getCode());
		this.setDescription(c.getDescription());
	}

}
