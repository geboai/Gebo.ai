package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.List;

import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.SearchRewritings;

public class DefaultPipelineSharedEnvironmentUtil {

	public static final String AI_SELECTED_DEEP_SEARCH_DATA_SOURCES = "AI_SELECTED_DEEP_SEARCH_DATA_SOURCES";
	public static final String AI_SELECTED_DOCUMENTS = "AI_SELECTED_DOCUMENTS";
	public static final String AI_SELECTED_QUERY_REWRITE_SUGGESTIONS = "AI_SELECTED_QUERY_REWRITE_SUGGESTIONS";
	public static final String AI_SELECTED_TOOLS_LIST = "AI_SELECTED_TOOLS_LIST";

	public static SearchRewritings getAISuggestedSearchRewritings(ChatPipelineExecutionRuntimeData data) {
		if (data.getSharedEnvironment() != null && data.getSharedEnvironment()
				.get(AI_SELECTED_QUERY_REWRITE_SUGGESTIONS) instanceof SearchRewritings r) {
			return r;
		}
		return null;
	}

	public static List<String> getAISuggestedSelectedDocuments(ChatPipelineExecutionRuntimeData data) {
		if (data.getSharedEnvironment() != null
				&& data.getSharedEnvironment().get(AI_SELECTED_DOCUMENTS) instanceof List r) {
			return r;
		}
		return null;
	}

	public static List<String> getAISuggestedDeepSearchDataSources(ChatPipelineExecutionRuntimeData data) {
		if (data.getSharedEnvironment() != null
				&& data.getSharedEnvironment().get(AI_SELECTED_DEEP_SEARCH_DATA_SOURCES) instanceof List r) {
			return r;
		}
		return null;
	}

	public static List<String> getAISuggestedToolsCallList(ChatPipelineExecutionRuntimeData data) {
		if (data.getSharedEnvironment() != null
				&& data.getSharedEnvironment().get(AI_SELECTED_TOOLS_LIST) instanceof List r) {
			return r;
		}
		return null;
	}
}
