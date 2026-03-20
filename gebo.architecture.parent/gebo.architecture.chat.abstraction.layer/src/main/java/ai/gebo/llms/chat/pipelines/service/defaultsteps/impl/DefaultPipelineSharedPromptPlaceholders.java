package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.HashMap;
import java.util.Map;

public class DefaultPipelineSharedPromptPlaceholders {

	public static final String INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM = "internalKnowledgeBaseCatalog";
	public static final String DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM = "deepSearchDataSources";
	public static final String LATEST_INTERACTIONS_TEMPLATE_PARAM = "latestInteractions";
	public static final String DOCUMENTS_TEMPLATE_PARAM = "documents";
	

	static final String TOOLS_LIST_TEMPLATE_PARAM = "toolsList";

	public static Map<String, Object> extractSharedPromptParameters(Map<String, Object> params,
			String... templatePlaceholders) {
		Map<String, Object> out = new HashMap<String, Object>();
		if (templatePlaceholders != null && templatePlaceholders.length > 0) {
			for (String param : templatePlaceholders) {
				if (params != null && params.containsKey(param)) {
					out.put(param, params.get(param));
				}
			}
		}
		return out;
	}

	public static final String DELIVERABLE_TYPES_LIST_TEMPLATE_PARAM = "deliverableTypesList";

}
