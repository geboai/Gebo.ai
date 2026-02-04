package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;

public interface IChatRequestFactory {
	public LLMChatRequestResources createChatRequestResources();
	public int getTargetTokenBudget(); 
}
