package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPipelineSharedPromptPlaceholders {

	public static final String INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM = "internalKnowledgeBaseCatalog";
	public static final String DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM = "deepSearchDataSources";
	public static final String LATEST_INTERACTIONS_TEMPLATE_PARAM = "latestInteractions";
	public static final String DOCUMENTS_TEMPLATE_PARAM = "documents";
	public static final List<String> ALL_TEMPLATE_PARAMETERS = List.of(INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM,
			DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM, LATEST_INTERACTIONS_TEMPLATE_PARAM, DOCUMENTS_TEMPLATE_PARAM);

	public static Map<String, Object> extractSharedPromptParameters(Map<String, Object> params) {
		Map<String, Object> out = new HashMap<String, Object>();
		for (String param : ALL_TEMPLATE_PARAMETERS) {
			if (params != null && params.containsKey(param)) {
				out.put(param, params.get(param));
			}
		}
		return out;
	}
}
