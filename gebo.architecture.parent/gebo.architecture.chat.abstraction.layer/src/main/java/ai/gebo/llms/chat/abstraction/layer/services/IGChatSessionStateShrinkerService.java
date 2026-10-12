package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;

/*****************************************************************************************
 * Service to reduce from the full session state to a shrinked copy to stay in a
 * budget
 */
public interface IGChatSessionStateShrinkerService {
	
	public void shrink(String sessionCode, int tokensBudget) throws LLMConfigException, IOException;

	public MinimalChatContext shrinkedMinimalContext(String sessionCode, MinimalChatContext mc, int tokensBudget)throws LLMConfigException, IOException;

	/**
	 * Prepares the minimal context of the chat for the given budget, when its history
	 * does not fit it: built once the chat's request is over, the next request finds it
	 * ready.
	 */
	public void prepareMinimalContext(String sessionCode, int tokensBudget) throws LLMConfigException, IOException;

	/**
	 * The budget of the minimal chat context a request's internal services work with: a
	 * third of the service model's context.
	 */
	public static int serviceModelContextBudget(IGConfigurableChatModel serviceModel) {
		return serviceModel.getContextLength() / 3;
	}
}
