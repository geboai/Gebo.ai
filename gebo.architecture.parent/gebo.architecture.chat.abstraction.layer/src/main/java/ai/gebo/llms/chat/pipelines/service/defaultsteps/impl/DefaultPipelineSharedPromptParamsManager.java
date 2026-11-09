package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class DefaultPipelineSharedPromptParamsManager {

	public static final String INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM = "internalKnowledgeBaseCatalog";
	public static final String DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM = "deepSearchDataSources";
	public static final String LATEST_INTERACTIONS_TEMPLATE_PARAM = "latestInteractions";
	public static final String DOCUMENTS_TEMPLATE_PARAM = "documents";
	private final IGToolCallbackSourceRepositoryPattern toolCallbackSourceRepo;

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
	

	/** The tools named, as a prompt lists them; empty when there are none. */
	public String toolsListPromptPart(List<String> functions) {
		if (functions == null || functions.isEmpty()) {
			return "";
		}
		return RoutingPromptUtil.toolsListPromptPart(toolCallbackSourceRepo.getEnabledToolsTree(functions));
	}

	public String toolsListPromptPart(IGConfigurableChatModel chatModel) {
		return chatModel != null ? toolsListPromptPart(chatModel.getToolNames()) : "";
	}

}
