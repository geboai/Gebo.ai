package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
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
	

	public String toolsListPromptPart(IGConfigurableChatModel chatModel) {
		StringBuffer buffer = new StringBuffer();
		if (chatModel != null && chatModel.getConfig() != null
				&& chatModel.getConfig() instanceof GBaseChatModelConfig chatModelConfig) {
			if (chatModelConfig.getEnabledFunctions() != null && !chatModelConfig.getEnabledFunctions().isEmpty()) {
	
				List<ToolCategoriesTree> tools = toolCallbackSourceRepo
						.getEnabledToolsTree(chatModelConfig.getEnabledFunctions());
				buffer.append(RoutingPromptUtil.toolsListPromptPart(tools));
			}
		}
		return buffer.toString();
	}

}
