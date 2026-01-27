package ai.gebo.llms.chat.pipelines.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineConfiguration;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.DefaultInputChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.DefaultRoutingChatPipelineStepServiceImpl;
import lombok.Data;

@Configuration
@ConfigurationProperties(value = "ai.gebo.chatpipes")
@Data
public class ChatPipelinesConfiguration {
	private static final String DEFAULT_PIPELINE = "default-pipeline";

	public ChatPipelinesConfiguration() {
		ChatPipelineConfiguration defaultPipeline = new ChatPipelineConfiguration();
		defaultPipeline.setCode(DEFAULT_PIPELINE);
		defaultPipeline.setDefaultPipeline(true);
		defaultPipeline.setStepInputId(DefaultInputChatPipelineStepServiceImpl.DEFAULT_INPUT_STEP);
		defaultPipeline.setStepRouterId(DefaultRoutingChatPipelineStepServiceImpl.DEFAULT_ROUTING_STEP);
		this.pipelines.add(defaultPipeline);
		this.defaultPipelineRoutingDecisionPrompt = new GPromptConfig();
		this.defaultPipelineRoutingDecisionPrompt.setPrompt(
				"You are a chat pipeline routing manager. Your task is to output ONLY a single JSON object (no markdown, no extra text).\r\n"
						+ "\r\n" + "You must set \"responseRoutingDecision\" to exactly one of:\r\n"
						+ "- \"PURE_LLM_RESPONSE\"\r\n" + "- \"RAG_LLM_RESPONSE\"\r\n"
						+ "- \"DEEP_SEARCH_RESPONSE\"\r\n" + "- \"TOOLS_USE_RESPONSE\"\r\n" + "\r\n"
						+ "Decision rules (apply in this exact priority order):\r\n" + "1) TOOLS_USE_RESPONSE:\r\n"
						+ "   Choose this if the user explicitly asks to use tools (search/browse/check/call/invoke) OR the question cannot be answered without using one of the provided tools.\r\n"
						+ "2) DEEP_SEARCH_RESPONSE:\r\n"
						+ "   Choose this ONLY if the user explicitly requests a report/research/in-depth analysis, OR asks for citations/sources across multiple documents/data sources, OR asks to analyze/compare a broad corpus.\r\n"
						+ "   Important: \"explain in detail\" or \"long explanation\" alone is NOT sufficient to choose deep search.\r\n"
						+ "3) RAG_LLM_RESPONSE:\r\n"
						+ "   Choose this if the answer is not fully supported by training knowledge OR the currently provided chat/doc fragments, and retrieval from a knowledge base is likely sufficient (no deep multi-source analysis required).\r\n"
						+ "4) PURE_LLM_RESPONSE:\r\n"
						+ "   Choose this if the answer is fully achievable using training knowledge and/or already provided chat/doc fragments.\r\n"
						+ "\r\n" + "Inputs:\r\n"
						+ "- documents/doc fragments already present in the chat history (may be empty):\r\n"
						+ "{documents}\r\n" + "\r\n" + "- latest chat interactions, latest first (may be empty):\r\n"
						+ "{latestInteractions}\r\n" + "\r\n" + "- current user question:\r\n" + "{question}\r\n"
						+ "\r\n" + "- deep search data sources (may be empty):\r\n" + "{deepSearchDataSources}\r\n"
						+ "\r\n" + "- tools list (may be empty):\r\n" + "{toolsList}\r\n" + "\r\n" + "{format}\r\n"
						+ "\r\n" + "Field rules:\r\n"
						+ "- expandDocuments: include ONLY document codes that exist in the chat history.\r\n"
						+ "- queryRewritings: fill ONLY when responseRoutingDecision is RAG_LLM_RESPONSE .\r\n"
						+ "- deepSearchDataSourceCodesToAnalyze: fill ONLY when responseRoutingDecision is DEEP_SEARCH_RESPONSE (otherwise []).\r\n"
						+ "- toolsToUse: fill ONLY when responseRoutingDecision is TOOLS_USE_RESPONSE (otherwise []).\r\n"
						+ "- If deep search data sources is empty and you would otherwise choose DEEP_SEARCH_RESPONSE, choose RAG_LLM_RESPONSE instead unless tools can satisfy it.\r\n"
						+ "- If tools list is empty you cannot choose TOOLS_USE_RESPONSE.");
	}

	private List<ChatPipelineConfiguration> pipelines = new ArrayList<ChatPipelineConfiguration>();
	private GPromptConfig defaultPipelineRoutingDecisionPrompt = null;
	private GPromptConfig defaultPipelineRagOutputPrompt = null;
	private GPromptConfig defaultPipelineOutputPrompt = null;
	private GPromptConfig defaultPipelineToolCallOutputPrompt = null;
	private int maxRoutingDecisionDocumentsTokenBudget = 12000;

}
