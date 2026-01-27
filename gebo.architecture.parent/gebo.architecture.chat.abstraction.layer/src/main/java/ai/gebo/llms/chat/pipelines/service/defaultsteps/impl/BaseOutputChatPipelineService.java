package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;


public class BaseOutputChatPipelineService {
	//TODO: PUT here The retrieval of docs not in list
	public LLMChatRequestResources integrateWithAISuggestedDocuments(ChatPipelineExecutionRuntimeData runtimeData) {
		
		return runtimeData.getRequestResources();
	}

	

}
