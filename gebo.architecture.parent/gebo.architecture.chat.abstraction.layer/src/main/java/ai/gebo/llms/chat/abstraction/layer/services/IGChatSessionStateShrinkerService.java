package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/*****************************************************************************************
 * Service to reduce from the full session state to a shrinked copy to stay in a
 * budget
 */
public interface IGChatSessionStateShrinkerService {
	@Async
	public void shrink(String sessionCode, int tokensBudget) throws LLMConfigException, IOException;
}
