package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GModelType;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrer;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.model.OperationStatus;

@Service
public class LLMTypeFiltrerRepositoryPatternImpl extends GAbstractImplementationsRepositoryPattern<ILLMTypeFiltrer>
		implements ILLMTypeFiltrerRepositoryPattern {

	public LLMTypeFiltrerRepositoryPatternImpl(@Autowired(required = false) List<ILLMTypeFiltrer> implementations) {
		super(reorder(implementations));

	}

	private static List<ILLMTypeFiltrer> reorder(List<ILLMTypeFiltrer> implementations) {
		if (implementations == null)
			return List.of();

		ILLMTypeFiltrer defaultHandler = null;
		List<ILLMTypeFiltrer> nonDefaults = new ArrayList<>();
		for (ILLMTypeFiltrer illmTypeFiltrer : implementations) {
			if (illmTypeFiltrer.isDefaultFilter()) {
				if (defaultHandler != null) {
					throw new RuntimeException(
							"Only a single defaultFilter == true ILLMTypeFiltrer is allowed in the system");
				} else {
					defaultHandler = illmTypeFiltrer;
				}
			} else {
				nonDefaults.add(illmTypeFiltrer);
			}
		}
		List<ILLMTypeFiltrer> handlersInOrder = new ArrayList<>(nonDefaults);
		if (defaultHandler != null) {
			handlersInOrder.add(defaultHandler);
		}
		return handlersInOrder;
	}

	@Override
	public String getCodeValue(ILLMTypeFiltrer x) {

		return x.getId();
	}

	@Override
	public ILLMTypeFiltrer getLLMTypeFilter(GModelType modelType) {
		return findImplementation(x -> x.isHandlerSupported(modelType.getCode()));
	}

	@Override
	public <T extends GBaseChatModelChoice> OperationStatus<List<T>> filterChatModels(GModelType modelType,
			OperationStatus<List<T>> models) {
		if ((models.getResult() == null || models.getResult().isEmpty()) && models.isHasErrorMessages()) {
			return OperationStatus.of(null, models.getMessages());
		}
		ILLMTypeFiltrer handler = getLLMTypeFilter(modelType);
		return OperationStatus.of(handler.filterChatModels(models.getResult()), models.getMessages());
	}

	@Override
	public <T extends GBaseEmbeddingModelChoice> OperationStatus<List<T>> filterEmbeddingModels(GModelType modelType,
			OperationStatus<List<T>> models) {
		if ((models.getResult() == null || models.getResult().isEmpty()) && models.isHasErrorMessages()) {
			return OperationStatus.of(null, models.getMessages());
		}
		ILLMTypeFiltrer handler = getLLMTypeFilter(modelType);
		return OperationStatus.of(handler.filterEmbeddingModels(models.getResult()), models.getMessages());
	}

}
