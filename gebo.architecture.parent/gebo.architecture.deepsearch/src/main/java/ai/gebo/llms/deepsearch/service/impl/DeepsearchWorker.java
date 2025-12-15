package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.KnowledgeBaseRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.repository.DeepSearchDocumentAnalisysResultStepRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchRequestRepository;
import ai.gebo.llms.deepsearch.repository.DeepSearchResponseRepository;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.security.repository.UserRepository.UserInfos;

@Service
public class DeepsearchWorker {
	private static Logger LOGGER = LoggerFactory.getLogger(DeepsearchWorker.class);
	private static final String QUESTION = "question";
	private static final String CONSOLIDATED = "consolidated";
	private static final String DOCUMENTS = "documents";
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	@Autowired(required = false)
	private IKnowledgeGraphSearchService graphRagSearchService;
	@Autowired
	private IGRagDocumentsCachedDao ragDocumentsCachedDao;
	@Autowired
	private DocumentReferenceRepository documentRepo;

	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	public AbstractDeepSearchEvent nextStep(DeepSearchRequest request, List<AbstractDeepSearchEvent> history,
			DeepSearchState state, DeepSearchConfig configuration, UserInfos userInfos,
			List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel) {

		if (request.getQuery() == null || request.getQuery().trim().length() == 0 || request.getKnowledgeBases() == null
				|| request.getKnowledgeBases().isEmpty()) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or no knowledge bases list");
		}

		if (chatModel != null && request.getKnowledgeBases() != null && !request.getKnowledgeBases().isEmpty()) {
			int tokensBudget = chatModel.getContextLength();

			if (state.getDocumentSearchResults() == null) {

				RagDocumentsCachedDaoResult consolidatedDaoResult = new RagDocumentsCachedDaoResult();
				for (IGConfigurableEmbeddingModel embeddingModel : embeddingModels) {
					RagDocumentsCachedDaoResult semanticDaoResult = ragDocumentsCachedDao.multiHopSemanticSearch(
							request.getQuery(), configuration.getRagQueryOptions(), request.getKnowledgeBases(),
							embeddingModel, configuration.getFirstHopSimilarityThreashold(),
							configuration.getSecondHopSimilarityThreashold(), userInfos);
					consolidatedDaoResult = RagDocumentsCachedDaoResult.join(semanticDaoResult, consolidatedDaoResult);
				}

				if (graphRagSearchService != null) {
					try {
						List<KnowledgeGraphSearchResult> graphRagResult = graphRagSearchService.knowledgeGraphSearch(
								request.getQuery(), request.getKnowledgeBases(),
								configuration.getGraphRagTopN().intValue());
						RagDocumentsCachedDaoResult graphragDocumentsResult = graphRagSearchService
								.toRagDocumentsCachedDaoResult(graphRagResult);
						consolidatedDaoResult = RagDocumentsCachedDaoResult.join(consolidatedDaoResult,
								graphragDocumentsResult);
					} catch (LLMConfigException e) {
						LOGGER.error("Error calling the graphrag logic", e);
					}
				}
				state.setDocumentSearchResults(consolidatedDaoResult);
				state.setFragmentsCount(consolidatedDaoResult.countFragments());
			}

			if (state.getDocumentSearchResults() != null
					&& state.getRagDocumentsPointer() < state.getDocumentSearchResults().getDocumentItems().size()) {

				RagDocumentReferenceItem foundDocument = state.getDocumentSearchResults().getDocumentItems()
						.get(state.getRagDocumentsPointer());
				String documentCode = foundDocument.getCode();
				Optional<GDocumentReference> docdata = documentRepo.findById(documentCode);
				if (docdata.isPresent()) {
					List<Document> currentProcessedFragments = new ArrayList<Document>();

					int initialFragmentPointer = state.getRagDocumentFragmentPointer();
					boolean lastFragmentReached = false;
					if (initialFragmentPointer < foundDocument.getFragments().size()) {
						boolean stopCycling = false;
						for (int i = initialFragmentPointer; !stopCycling
								&& i < foundDocument.getFragments().size(); i++) {
							RagDocumentFragment fragment = foundDocument.getFragments().get(i);
							stopCycling = tokensBudget < fragment.getNTokens();
							if (!stopCycling) {
								state.setRagDocumentsPointer(i);
								tokensBudget -= fragment.getNTokens();
								lastFragmentReached = i == foundDocument.getFragments().size() - 1;
								currentProcessedFragments.add(fragment.toAIDocument());
								state.setRagDocumentFragmentPointer(i);
							}
						}
						if (lastFragmentReached) {
							state.setRagDocumentsPointer(state.getRagDocumentsPointer() + 1);
							state.setRagDocumentFragmentPointer(0);
						}
					}
					if (!currentProcessedFragments.isEmpty()) {
						state.setElaboratedFragmentsCount(
								state.getElaboratedFragmentsCount() + currentProcessedFragments.size());
						String result = callLLMWithDocuments(chatModel, configuration.getAnalisysPrompt(),
								currentProcessedFragments, request.getQuery());
						DeepSearchDocumentAnalisysResultStep resultStep = new DeepSearchDocumentAnalisysResultStep();
						resultStep.setDeepsearchCode(request.getCode());
						resultStep.setFragment(result);
						resultStep.setIndex(history.size());
						resultStep.setDocumentCode(documentCode);
						resultStep.setFragmentsCodes(currentProcessedFragments.stream().map(x -> x.getId()).toList());
						DeepSearchDocumentEvent event = new DeepSearchDocumentEvent();
						event.setProcessPercentage(state.calculateProcessedPercent());
						event.setInputData(docdata.get());
						event.setOutputData(resultStep);
						
						return event;
					}
				}

			}

		}
		DeepSearchProcessedEvent consolidatedResult = this.consolidateResult(chatModel, history, request, state,
				configuration);
		return consolidatedResult;

	}

	private String callLLMWithDocuments(IGConfigurableChatModel chatModel, String prompt, Object documents,
			String question) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(DOCUMENTS, documents);
		promptTemplate.add(QUESTION, question);
		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	private String callLLMWithDocumentsAndConsolidation(IGConfigurableChatModel chatModel, String prompt,
			Object documents, String question, String consolidated) {
		PromptTemplate promptTemplate = new PromptTemplate(prompt);
		promptTemplate.add(CONSOLIDATED, consolidated);
		promptTemplate.add(DOCUMENTS, documents);
		promptTemplate.add(QUESTION, question);
		ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
		String result = response.getResult().getOutput().getText();
		return result;
	}

	private DeepSearchProcessedEvent consolidateResult(IGConfigurableChatModel chatModel,
			List<AbstractDeepSearchEvent> history, DeepSearchRequest request, DeepSearchState state,
			DeepSearchConfig configuration) {
		final int tokensBudget = chatModel.getContextLength();
		int tokens = 0;
		String consolidated = "";
		StringBuffer fragments = new StringBuffer();
		List<DeepSearchDocumentAnalisysResultStep> steps = new ArrayList<DeepSearchDocumentAnalisysResultStep>();
		for (AbstractDeepSearchEvent event : history) {
			if (event instanceof DeepSearchDocumentEvent docEvent) {
				steps.add(docEvent.getOutputData());
				String actualFragment = docEvent.getOutputData().getFragment();
				GDocumentReference document = docEvent.getInputData();
				int length = tokenEstimator.estimate(actualFragment);
				if (tokens + length >= tokensBudget) {

					consolidated = callLLMWithDocumentsAndConsolidation(chatModel,
							configuration.getConsolidationPrompt(), fragments.toString(), request.getQuery(),
							consolidated);
					fragments = new StringBuffer();
					tokens = 0;

				}

				fragments.append(DOCUMENT_EXTRACTION_BEGIN);
				fragments.append(DOCUMENT_NAME + document.getName());
				fragments.append(actualFragment);
				fragments.append(END_DOCUMENT_EXTRACTION);
				tokens += length;
			}
		}
		if (!fragments.isEmpty()) {
			consolidated = callLLMWithDocumentsAndConsolidation(chatModel, configuration.getConsolidationPrompt(),
					fragments.toString(), request.getQuery(), consolidated);
		}
		DeepSearchProcessedEvent outValue = new DeepSearchProcessedEvent();
		outValue.setInputData(request);
		DeepSearchResponse response = new DeepSearchResponse();
		response.setDeepsearchCode(request.getCode());
		response.setResponse(consolidated);
		outValue.setOutputData(response);
		outValue.setProcessPercentage(100.0);
		return outValue;
	}

}
