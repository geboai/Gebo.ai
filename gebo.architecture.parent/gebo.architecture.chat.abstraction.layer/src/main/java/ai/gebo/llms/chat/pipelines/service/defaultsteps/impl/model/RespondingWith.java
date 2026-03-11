package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model;

public enum RespondingWith {
	PURE_LLM_RESPONSE, RAG_LLM_RESPONSE, DEEP_SEARCH_RESPONSE, SHALLOW_SEARCH_RESPONSE, DEEP_RAG_RESPONSE,
	TOOLS_USE_RESPONSE, CHAT_WITH_FILES
}