package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenuItem;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenuItemParameter;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class DefaultPipelineUserMenuProviderService implements IPipelineUserMenuProviderService {
	private static final String INTERNAL_KNOWLEDGEBASE_DEEP_SEARCH_ICON = "pi pi-sitemap";
	private static final String RAG_CHAT_ICON = "pi pi-database";
	private static final String MULTIPLE_SOURCES_DEEP_SEARCH_DESCRIPTION = "Multiple sources";
	private static final String RAG_CHAT_DESCRIPTION = "R.a.g. chat";
	private static final String RAG_ID = "rag";
	private static final String DEEP_SEARCH_ID = "deepSearch";
	private static final String DEEP_SEARCH_ICON = "pi pi-deep-search";
	private static final String DEEP_SEARCH_DESCRIPTION = "Deep search";
	private static final String AGENTIC_CHAT_DESCRIPTION = "Agentic chat";
	private static final String AGENTIC_CHAT_MENU_ID = "agenticChat";
	private static final String AGENTIC_CHAT_ICON = "pi pi-microchip-ai";
	private static final String KNOWLEDGE_BASE_DEEP_SEARCH = "Knowledge base deep search";
	private static final String KNOWLEDGE_BASE_SEARCH = "Knowledge base search";
	private static final String INTERNAL_KNOWLEDGE_OPTION = "internalKnowledgeOption";
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDeepSearchDataSourceLookupService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	static final PipelineChatMenu agenticChatMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem agenticChatItem = new PipelineChatMenuItem();
	static {
		agenticChatMenu.setMenuId(AGENTIC_CHAT_MENU_ID);
		agenticChatMenu.setOrder(1);
		agenticChatMenu.setDescription(AGENTIC_CHAT_DESCRIPTION);
		agenticChatItem.setDefaultOption(true);
		agenticChatItem.setOptionId(AGENTIC_CHAT_MENU_ID);
		agenticChatItem.setIcon(AGENTIC_CHAT_ICON);
		agenticChatItem.setDescription(AGENTIC_CHAT_DESCRIPTION);
		agenticChatItem.setRouteOption(null);
		agenticChatMenu.setItems(List.of(agenticChatItem));
	}
	static final PipelineChatMenu deepSearchMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem deepSearchMenuItem = new PipelineChatMenuItem();
	static final PipelineChatMenu ragMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem ragMenuItem = new PipelineChatMenuItem();
	static {
		deepSearchMenu.setMenuId(DEEP_SEARCH_ID);
		deepSearchMenu.setPipelineId(null);
		deepSearchMenu.setDescription(DEEP_SEARCH_DESCRIPTION);
		deepSearchMenu.setIcon(DEEP_SEARCH_ICON);
		deepSearchMenuItem.setOptionId(DEEP_SEARCH_ID);
		deepSearchMenuItem.setIcon(DEEP_SEARCH_ICON);
		deepSearchMenuItem.setDescription(MULTIPLE_SOURCES_DEEP_SEARCH_DESCRIPTION);
		deepSearchMenuItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
		ragMenu.setMenuId(RAG_ID);
		ragMenu.setDescription(RAG_CHAT_DESCRIPTION);
		ragMenuItem.setOptionId(RAG_ID);
		ragMenuItem.setDescription(RAG_CHAT_DESCRIPTION);
		ragMenuItem.setIcon(RAG_CHAT_ICON);
		ragMenuItem.setRouteOption(RespondingWith.RAG_LLM_RESPONSE.name());
		ragMenu.getItems().add(ragMenuItem);
	}

	@Override
	public String getPipelineId() {

		return null;
	}

	@Override
	public List<PipelineChatMenu> getUIMenu(String chatProfileCode) {
		List<PipelineChatMenu> outMenu = new ArrayList<PipelineChatMenu>();
		DeepSearchConfig deepSearchConfig = deepSearchConfigProvider.get();
		List<IGReactiveDeepSearchDataSourceService> enabledDataSources = this.enabledDeepSearchDataSourceLookupService
				.enabledDataSources(deepSearchConfig);
		outMenu.add((PipelineChatMenu) agenticChatMenu.clone());
		outMenu.add((PipelineChatMenu) ragMenu.clone());
		if (!enabledDataSources.isEmpty()) {
			PipelineChatMenu thisDeepSearchMenu = (PipelineChatMenu) deepSearchMenu.clone();
			thisDeepSearchMenu.getItems().add(deepSearchMenuItem);
			for (IGReactiveDeepSearchDataSourceService ds : enabledDataSources) {
				PipelineChatMenuItem dsMenuItem = new PipelineChatMenuItem();
				dsMenuItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
				dsMenuItem.setOptionId(RespondingWith.DEEP_SEARCH_RESPONSE.name() + "." + ds.getHandlerId());
				dsMenuItem.setDescription(ds.getDescription(deepSearchConfig));
				dsMenuItem.setProductId(ds.getProductId());
				PipelineChatMenuItemParameter dsPipelineParameter = new PipelineChatMenuItemParameter();
				dsPipelineParameter.setParameterName(DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS);
				dsPipelineParameter.setParameterValue(List.of(ds.getHandlerId()));
				dsMenuItem.getParameters().add(dsPipelineParameter);
				thisDeepSearchMenu.getItems().add(dsMenuItem);
			}
			PipelineChatMenuItem ikMenuItem = new PipelineChatMenuItem();
			ikMenuItem.setDescription(KNOWLEDGE_BASE_SEARCH);
			ikMenuItem.setOptionId(INTERNAL_KNOWLEDGE_OPTION);
			ikMenuItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
			ikMenuItem.setIcon(INTERNAL_KNOWLEDGEBASE_DEEP_SEARCH_ICON);
			PipelineChatMenuItemParameter dsPipelineParameter = new PipelineChatMenuItemParameter();
			dsPipelineParameter.setParameterName(DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS);
			dsPipelineParameter.setParameterValue(
					List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID));
			ikMenuItem.getParameters().add(dsPipelineParameter);
			thisDeepSearchMenu.getItems().add(ikMenuItem);
			outMenu.add(thisDeepSearchMenu);
		} else {
			PipelineChatMenuItem ikMenuItem = new PipelineChatMenuItem();
			ikMenuItem.setDescription(KNOWLEDGE_BASE_DEEP_SEARCH);
			ikMenuItem.setOptionId(INTERNAL_KNOWLEDGE_OPTION);
			ikMenuItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
			ikMenuItem.setIcon(INTERNAL_KNOWLEDGEBASE_DEEP_SEARCH_ICON);
			PipelineChatMenuItemParameter dsPipelineParameter = new PipelineChatMenuItemParameter();
			dsPipelineParameter.setParameterName(DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS);
			dsPipelineParameter.setParameterValue(
					List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID));
			ikMenuItem.getParameters().add(dsPipelineParameter);
			PipelineChatMenu ikMenu = new PipelineChatMenu();
			ikMenu.setDescription(KNOWLEDGE_BASE_DEEP_SEARCH);
			ikMenu.setMenuId(INTERNAL_KNOWLEDGE_OPTION);
			ikMenu.setItems(List.of(ikMenuItem));
			outMenu.add(ikMenu);
		}
		return outMenu;
	}

}
