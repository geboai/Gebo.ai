package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatFullSessionState;
import ai.gebo.llms.chat.abstraction.layer.model.session.ShrinkedChatSessionState;

/*****************************************************************************************
 * Service to reduce from the full session state to a shrinked copy to stay in a
 * budget
 */
public interface IGChatSessionStateShrinkerService {
	public ShrinkedChatSessionState shrink(ChatFullSessionState fullSessionState, int tokensBudget) throws LLMConfigException, IOException;
}
