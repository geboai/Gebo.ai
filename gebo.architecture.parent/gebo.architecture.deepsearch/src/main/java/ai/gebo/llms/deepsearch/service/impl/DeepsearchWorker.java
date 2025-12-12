package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.model.base.GObjectRef;

@Service
public class DeepsearchWorker {
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	@Autowired(required = false)
	private IKnowledgeGraphSearchService graphRagSearchService;
	@Autowired
	private IGRagDocumentsCachedDao ragDocumentsCachedDao;
	@Autowired
	private IGChatModelRuntimeConfigurationDao chatModelsConfigDao;
	@Autowired
	private DocumentReferenceRepository documentRepo;
	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	public AbstractDeepSearchEvent nextStep(DeepSearchRequest request, List<AbstractDeepSearchEvent> history,
			DeepSearchState state, DeepSearchConfig configuration) {

		IGConfigurableChatModel chatModel = null;
		GObjectRef<GBaseChatModelConfig> chatModelReference = configuration.getChatModelConfiguration();
		if (chatModelReference != null) {
			chatModel = chatModelsConfigDao.findByModelReference(chatModelReference);
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel != null) {
			int tokensBudget = chatModel.getContextLength();

			if (state.getSemanticDaoResults() == null) {
				RagDocumentsCachedDaoResult semanticDaoResult = ragDocumentsCachedDao.multiHopSemanticSearch(null, null,
						null, null, null, null, null);
				state.setSemanticDaoResults(semanticDaoResult);
				if (graphRagSearchService != null) {
					try {
						List<KnowledgeGraphSearchResult> graphRagResult = graphRagSearchService.knowledgeGraphSearch(
								request.getQuery(), request.getKnowledgeBases(),
								configuration.getGraphRagTopN().intValue());
						mergeGraphRagResults(semanticDaoResult, graphRagResult);
					} catch (LLMConfigException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (state.getSemanticDaoResults() != null
					&& state.getRagDocumentsPointer() < state.getSemanticDaoResults().getDocumentItems().size()) {
				RagDocumentReferenceItem foundDocument = state.getSemanticDaoResults().getDocumentItems()
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
						PromptTemplate promptTemplate = new PromptTemplate(configuration.getAnalisysPrompt());
						promptTemplate.add("documents", currentProcessedFragments);
						promptTemplate.add("question", request.getQuery());
						ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
						String result = response.getResult().getOutput().getText();
						DeepSearchDocumentAnalisysResultStep resultStep = new DeepSearchDocumentAnalisysResultStep();
						resultStep.setDeepsearchId(request.getRequestId());
						resultStep.setFragment(result);
						DeepSearchDocumentEvent event = new DeepSearchDocumentEvent();
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
					PromptTemplate promptTemplate = new PromptTemplate(configuration.getConsolidationPrompt());
					promptTemplate.add("consolidated", consolidated);
					promptTemplate.add("documents", fragments.toString());
					ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
					consolidated = response.getResult().getOutput().getText();
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
			PromptTemplate promptTemplate = new PromptTemplate(configuration.getConsolidationPrompt());
			promptTemplate.add("consolidated", consolidated);
			promptTemplate.add("documents", fragments.toString());
			ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());
			consolidated = response.getResult().getOutput().getText();
		}
		DeepSearchProcessedEvent outValue = new DeepSearchProcessedEvent();
		outValue.setInputData(request);
		DeepSearchResponse response = new DeepSearchResponse();
		response.setResponse(consolidated);
		outValue.setOutputData(response);

		response.setSteps(steps);
		return outValue;
	}

	private void mergeGraphRagResults(RagDocumentsCachedDaoResult documents,
			List<KnowledgeGraphSearchResult> graphRagResults) {
		final Map<String, List<RagDocumentFragment>> fragments = new HashMap<String, List<RagDocumentFragment>>();
		final Map<String, RagDocumentReferenceItem> alreadyExisting = new HashMap<String, RagDocumentReferenceItem>();
		documents.getDocumentItems().forEach(x -> {
			alreadyExisting.put(x.getCode(), x);
		});
		graphRagResults.stream().forEach(x -> {
			String documentCode = x.getExtractedDocumentMetaData().getCode();
			if (documentCode == null)
				return;
			if (!fragments.containsKey(documentCode)) {
				fragments.put(documentCode, new ArrayList<RagDocumentFragment>());
			}
			RagDocumentFragment fragment = new RagDocumentFragment(x.getDocument(), x.getExtractedDocumentMetaData());

			RagDocumentReferenceItem existingDoc = alreadyExisting.get(documentCode);
			boolean fragmentAlreadyFound = existingDoc != null && existingDoc.getFragments().stream()
					.anyMatch(f -> f.getCode() != null && f.getCode().equals(fragment.getCode()));

			if (existingDoc == null) {
				existingDoc = new RagDocumentReferenceItem(x.getExtractedDocumentMetaData());
				alreadyExisting.put(documentCode, existingDoc);
				documents.getDocumentItems().add(existingDoc);
			}
			existingDoc.getFragments().add(fragment);
			documents.recalculateSize();

		});
	}

}
