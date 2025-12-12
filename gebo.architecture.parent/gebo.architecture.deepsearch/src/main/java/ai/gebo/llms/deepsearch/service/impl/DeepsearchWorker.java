package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.model.base.GObjectRef;

@Service
public class DeepsearchWorker {
	@Autowired(required = false)
	private IKnowledgeGraphSearchService graphRagSearchService;
	@Autowired
	private IGRagDocumentsCachedDao ragDocumentsCachedDao;
	@Autowired
	private IGChatModelRuntimeConfigurationDao chatModelsConfigDao;

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
			}
			if (state.getRagDocumentsPointer() < state.getSemanticDaoResults().getDocumentItems().size()) {
				RagDocumentReferenceItem foundDocument = state.getSemanticDaoResults().getDocumentItems()
						.get(state.getRagDocumentsPointer());
				List<Document> currentProcessedFragments = new ArrayList<Document>();
				int initialFragmentPointer = state.getRagDocumentFragmentPointer();
				if (initialFragmentPointer < foundDocument.getFragments().size()) {
					boolean stopCycling = false;
					for (int i = initialFragmentPointer; !stopCycling && i < foundDocument.getFragments().size(); i++) {
						RagDocumentFragment fragment = foundDocument.getFragments().get(i);
						stopCycling = tokensBudget < fragment.getNTokens();
						if (!stopCycling) {
							tokensBudget -= fragment.getNTokens();
							currentProcessedFragments.add(fragment.toAIDocument());
							state.setRagDocumentFragmentPointer(i);
						}
					}
				}
				PromptTemplate promptTemplate = new PromptTemplate(configuration.getAnalisysPrompt());
				promptTemplate.add("documents", currentProcessedFragments);
				promptTemplate.add("question", request.getQuery());
				ChatResponse response = chatModel.getChatModel().call(promptTemplate.create());

			}
		}
		return null;
	}

}
