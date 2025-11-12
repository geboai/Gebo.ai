package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;

public interface ILLMTypeFiltrer {
	public default String getId() {
		return this.getClass().getName();
	}

	public boolean isDefaultFilter();

	public boolean isHandlerSupported(String handlerId);

	public <T extends GBaseChatModelChoice> List<T> filterChatModels(List<T> models);

	public <T extends GBaseEmbeddingModelChoice> List<T> filterEmbeddingModels(List<T> models);
}
