package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrer;
@Service
public class LLMTypeFiltrerImpl implements ILLMTypeFiltrer {

	public LLMTypeFiltrerImpl() {

	}

	@Override
	public boolean isDefaultFilter() {

		return true;
	}

	@Override
	public boolean isHandlerSupported(String handlerId) {

		return true;
	}

	@Override
	public <T extends GBaseChatModelChoice> List<T> filterChatModels(List<T> models) {

		return models != null
				? models.stream().filter(x -> !(x.getCode() != null && x.getCode().toLowerCase().indexOf("embed") >= 0))
						.toList()
				: List.of();
	}

	@Override
	public <T extends GBaseEmbeddingModelChoice> List<T> filterEmbeddingModels(List<T> models) {
		return models != null
				? models.stream().filter(x -> (x.getCode() != null && x.getCode().toLowerCase().indexOf("embed") >= 0))
						.toList()
				: List.of();
	}

}
