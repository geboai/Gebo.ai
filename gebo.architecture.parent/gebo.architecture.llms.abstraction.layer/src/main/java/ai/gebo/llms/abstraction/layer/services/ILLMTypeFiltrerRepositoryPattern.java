package ai.gebo.llms.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.model.OperationStatus;

public interface ILLMTypeFiltrerRepositoryPattern extends IGImplementationsRepositoryPattern<ILLMTypeFiltrer> {
	public ILLMTypeFiltrer getLLMTypeFilter(GModelType modelType);

	public <T extends GBaseChatModelChoice> OperationStatus<List<T>> filterChatModels(GModelType modelType,
			OperationStatus<List<T>> models);

	public <T extends GBaseEmbeddingModelChoice> OperationStatus<List<T>> filterEmbeddingModels(GModelType modelType,
			OperationStatus<List<T>> models);
}
