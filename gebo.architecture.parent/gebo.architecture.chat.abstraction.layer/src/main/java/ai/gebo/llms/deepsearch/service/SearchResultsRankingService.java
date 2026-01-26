package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.ratings.DocumentRateRequest;
import ai.gebo.llms.deepsearch.model.ratings.DocumentRefToRate;
import ai.gebo.llms.deepsearch.model.ratings.RatedDocumentsList;
import ai.gebo.llms.deepsearch.model.ratings.SharedRatingsStructure;

@Service
public class SearchResultsRankingService extends BaseLlmsInvokingService {
	static final JTokkitTokenCountEstimator estimator = new JTokkitTokenCountEstimator();

	public SearchResultsRankingService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);

	}

	public void rateReferences(IGConfigurableChatModel chatModel, DeepSearchConfig config,
			List<SearchResult> searchResults, DeepSearchRequest request, SharedRatingsStructure ratingStructure)
			throws IOException, LLMConfigException {
		final String prompt = config.getRatingPrompt();
		final double jsonBudget = chatModel.getContextLength()
				- (estimator.estimate(prompt) + estimator.estimate(prompt));
		List<String> splitted = splittedByBudget(searchResults, Math.round(jsonBudget * 0.8));
		RatedDocumentsList total = new RatedDocumentsList();
		for (String documents : splitted) {
			RatedDocumentsList result = callLLMWithDocumentsStructuredReturn(chatModel, prompt, documents,
					request.getQuery(), RatedDocumentsList.class);
			if (result != null && result.getRatedDocumentRefs() != null) {
				total.getRatedDocumentRefs().addAll(result.getRatedDocumentRefs());
			}
		}
		ratingStructure.addSearchResultsWithRatings(searchResults, total);
	}

	private static DocumentRefToRate to(SearchResult result) {
		DocumentRefToRate dr = new DocumentRefToRate();
		dr.setId(result.getId());
		dr.setTitle(result.getDescriptiveText());
		if (dr.getTitle() == null && result.getResultReference() != null) {
			if (result.getResultReference().getTitle() != null) {
				dr.setTitle(result.getResultReference().getTitle());
			}
			if (dr.getTitle() == null) {
				dr.setTitle(result.getResultReference().getName());
			}
		}
		if (dr.getTitle() == null && result.getNavigationReference() != null
				&& result.getNavigationReference().path != null) {
			dr.setTitle(result.getNavigationReference().path.name);
		}
		return dr;
	}

	static ObjectMapper mapper = new ObjectMapper();

	private List<String> splittedByBudget(List<SearchResult> searchResults, double jsonBudget) throws IOException {
		List<String> out = new ArrayList<String>();
		DocumentRateRequest docs = new DocumentRateRequest();
		final int totalBudget = (int) jsonBudget;
		final int baseStructureTokens = estimator.estimate(mapper.writeValueAsString(docs)) + 20;
		int currentTokensCount = baseStructureTokens;
		List<DocumentRefToRate> refs = searchResults.stream().map(SearchResultsRankingService::to).toList();
		for (DocumentRefToRate documentRefToRate : refs) {
			final int thisEntrySize = estimator.estimate(mapper.writeValueAsString(documentRefToRate));
			if ((currentTokensCount + thisEntrySize) <= totalBudget) {
				docs.getDocumentRefs().add(documentRefToRate);
				currentTokensCount += thisEntrySize;
			} else {
				out.add(mapper.writeValueAsString(docs));
				currentTokensCount = baseStructureTokens + thisEntrySize;
				docs = new DocumentRateRequest();
				docs.getDocumentRefs().add(documentRefToRate);
			}
		}
		if (docs.getDocumentRefs().size() > 0) {
			out.add(mapper.writeValueAsString(docs));
		}
		return out;
	}

}
