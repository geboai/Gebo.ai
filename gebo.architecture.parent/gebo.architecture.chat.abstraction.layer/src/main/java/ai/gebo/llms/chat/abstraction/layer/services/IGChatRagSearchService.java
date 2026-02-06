package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.session.GUserChatSession;

public interface IGChatRagSearchService {
	public AIDocumentsSet searchRelatedDocuments(GeboChatRequest chatRequest, GUserChatSession context, int tokensBudget);
}
