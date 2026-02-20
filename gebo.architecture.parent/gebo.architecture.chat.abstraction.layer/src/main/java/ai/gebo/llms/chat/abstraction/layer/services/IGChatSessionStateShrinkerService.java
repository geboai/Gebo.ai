package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;

/*****************************************************************************************
 * Service to reduce from the full session state to a shrinked copy to stay in a
 * budget
 */
public interface IGChatSessionStateShrinkerService {
	
	public void shrink(String sessionCode, int tokensBudget) throws LLMConfigException, IOException;

	public MinimalChatContext shrinkedMinimalContext(String sessionCode, MinimalChatContext mc, int tokensBudget)throws LLMConfigException, IOException;
}
