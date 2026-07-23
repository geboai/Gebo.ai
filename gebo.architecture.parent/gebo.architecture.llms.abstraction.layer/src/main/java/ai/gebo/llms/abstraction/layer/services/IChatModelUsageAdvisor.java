package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

public interface IChatModelUsageAdvisor
		extends ILLMModelUsageHandler<GBaseChatModelConfig>, CallAdvisor, StreamAdvisor {

}
