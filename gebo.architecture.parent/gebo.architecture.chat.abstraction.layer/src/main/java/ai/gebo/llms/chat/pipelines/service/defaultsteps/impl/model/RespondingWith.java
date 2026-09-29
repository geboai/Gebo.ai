package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model;

public enum RespondingWith {
	PURE_LLM_RESPONSE, RAG_LLM_RESPONSE, DEEP_SEARCH_RESPONSE, TOOLS_USE_RESPONSE, CHAT_WITH_FILES, PURE_SEARCH, DELEGATED_AGENT, IMAGE_GENERATION_RESPONSE,
	/**
	 * A single agent with tools, working in a loop it ends by itself, over the network
	 * of agents; only chosen from the chat menu, never by the router.
	 */
	AGENTIC_LOOP_RESPONSE
}