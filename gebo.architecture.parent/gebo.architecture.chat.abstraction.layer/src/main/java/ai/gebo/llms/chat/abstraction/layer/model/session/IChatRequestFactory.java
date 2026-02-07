package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;

public interface IChatRequestFactory {
	public LLMChatRequestResources createChatRequestResources(LLMRequestGenerationPolicy pol);
	public int getTargetTokenBudget(); 
}
