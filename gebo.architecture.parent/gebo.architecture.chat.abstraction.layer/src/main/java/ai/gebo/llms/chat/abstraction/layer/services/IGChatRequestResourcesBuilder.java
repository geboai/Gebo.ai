package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IGChatRequestResourcesBuilder {
	public LLMChatRequestResources buildRequestResources(GeboChatRequest lastRequest, GUserChatContext actualContext,
			int tokensBudget) throws IOException, GeboPersistenceException, GeboContentHandlerSystemException, GeboIngestionException;
}
