package ai.gebo.llms.chat.abstraction.layer.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.llms.chat.abstraction.layer.config.GeboChatConfigs;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatRagSearchService;
@Service
public class GChatRagSearchServiceImpl implements IGChatRagSearchService {
	@Autowired
	protected IGSemanticSearchDocumentsCachedDao ragDocumentsCachedDao;
	@Autowired
	protected GeboChatConfigs ragResourcesConfig;
	@Autowired
	protected IRagThreasholdAutotuneService threasholdAutotuneService;
	@Autowired(required = false)
	protected IKnowledgeGraphSearchService knowledgeGraphSearch;
	@Autowired(required = false)
	protected IGFullTextSearchService fullTextSearchService;

	@Override
	public AIDocumentsSet searchRelatedDocuments(GeboChatRequest chatRequest, GUserChatContext context, int tokensBudget) {
		// TODO Auto-generated method stub
		return null;
	}

}
