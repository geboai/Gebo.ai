package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.ranker.model.RankingInput;
import ai.gebo.ranker.model.RankingOutput;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GRankerServiceImpl implements IGRankerService {
	private final IGRankerModelRuntimeConfigurationDao rankerModelDao;

	@Override
	public AIDocumentsSet call(AIDocumentsSet input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.countFragments();
		if (nFragments <= 0)
			return input;

		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		if (rankerModel == null)
			throw new LLMConfigException(
					"No ranker model configured, call first isRankerConfigured() to check if there is one");
		RankingInput _input = new RankingInput(query, input.aiDocumentsList(), topK);
		RankingOutput out = rankerModel.getRankerModel().call(_input);
		List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
		return AIDocumentsSet.from(documents);
	}

	@Override
	public boolean isRankerConfigured() {

		return rankerModelDao.defaultHandler() != null;
	}

	@Override
	public List<Document> call(List<Document> input, String query, int topK) throws LLMConfigException {
		final int nFragments = input.size();
		if (nFragments <= 0)
			return input;

		IGConfigurableRankerModel rankerModel = rankerModelDao.defaultHandler();
		if (rankerModel == null)
			throw new LLMConfigException(
					"No ranker model configured, call first isRankerConfigured() to check if there is one");
		RankingInput _input = new RankingInput(query, input, topK);
		RankingOutput out = rankerModel.getRankerModel().call(_input);
		List<Document> documents = out.getRanked().stream().map(x -> x.getDocument()).toList();
		return documents;
	}

}
