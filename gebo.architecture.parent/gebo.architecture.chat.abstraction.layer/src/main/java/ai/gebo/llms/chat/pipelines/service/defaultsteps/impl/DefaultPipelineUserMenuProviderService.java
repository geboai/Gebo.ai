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
	private static final String KNOWLEDGE_BASE_DEEP_SEARCH = "Knowledge base deep search";
	private static final String KNOWLEDGE_BASE_SEARCH = "Knowledge base search";
	private static final String INTERNAL_KNOWLEDGE_OPTION = "internalKnowledgeOption";
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDeepSearchDataSourceLookupService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	static final PipelineChatMenu agenticChatMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem agenticChatItem = new PipelineChatMenuItem();
	static {
		agenticChatMenu.setMenuId("agenticChat");
		agenticChatMenu.setOrder(1);
		agenticChatMenu.setDescription("Agentic chat");
		agenticChatItem.setDefaultOption(true);
		agenticChatItem.setOptionId("agenticChat");
		agenticChatItem.setIcon("pi-microchip-ai");
		agenticChatItem.setDescription("Agentic chat");
		agenticChatItem.setRouteOption(null);
		agenticChatMenu.setItems(List.of(agenticChatItem));
	}
	static final PipelineChatMenu deepSearchMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem deepSearchMenuItem = new PipelineChatMenuItem();
	static final PipelineChatMenu ragMenu = new PipelineChatMenu();
	static final PipelineChatMenuItem ragMenuItem = new PipelineChatMenuItem();
	static {
		deepSearchMenu.setMenuId("deepSearch");
		deepSearchMenu.setPipelineId(null);
		deepSearchMenu.setDescription("Deep search");
		deepSearchMenuItem.setOptionId("deepSearch");
		deepSearchMenuItem.setDescription("Multiple sources");
		deepSearchMenuItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
		ragMenu.setMenuId("rag");
		ragMenu.setDescription("R.a.g. chat");
		ragMenuItem.setOptionId("rag");
		ragMenuItem.setDescription("R.a.g. chat");
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
		outMenu.add(agenticChatMenu);
		outMenu.add(ragMenu);
		if (!enabledDataSources.isEmpty()) {
			PipelineChatMenu thisDeepSearchMenu = (PipelineChatMenu) deepSearchMenu.clone();
			thisDeepSearchMenu.getItems().add(deepSearchMenuItem);
			for (IGReactiveDeepSearchDataSourceService ds : enabledDataSources) {
				PipelineChatMenuItem dsMenuItem = new PipelineChatMenuItem();
				dsMenuItem.setRouteOption(RespondingWith.SHALLOW_SEARCH_RESPONSE.name());
				dsMenuItem.setOptionId(RespondingWith.SHALLOW_SEARCH_RESPONSE.name() + "." + ds.getHandlerId());
				dsMenuItem.setDescription(ds.getDescription(deepSearchConfig));
				dsMenuItem.setProductId(ds.getProductId());
				PipelineChatMenuItemParameter dsPipelineParameter = new PipelineChatMenuItemParameter();
				dsPipelineParameter.setParameterName(DefaultRoutingChatPipelineStepServiceImpl.SEARCHED_SYSTEM);
				dsPipelineParameter.setParameterValue(ds.getHandlerId());
				dsMenuItem.getParameters().add(dsPipelineParameter);
				thisDeepSearchMenu.getItems().add(dsMenuItem);
			}
			PipelineChatMenuItem ikMenuItem = new PipelineChatMenuItem();
			ikMenuItem.setDescription(KNOWLEDGE_BASE_SEARCH);
			ikMenuItem.setOptionId(INTERNAL_KNOWLEDGE_OPTION);
			ikMenuItem.setRouteOption(RespondingWith.DEEP_RAG_RESPONSE.name());
			thisDeepSearchMenu.getItems().add(ikMenuItem);
			outMenu.add(thisDeepSearchMenu);
		} else {
			PipelineChatMenu ikMenu = new PipelineChatMenu();
			ikMenu.setDescription(KNOWLEDGE_BASE_DEEP_SEARCH);
			ikMenu.setMenuId(INTERNAL_KNOWLEDGE_OPTION);
			PipelineChatMenuItem ikMenuItem = new PipelineChatMenuItem();
			ikMenuItem.setDescription(KNOWLEDGE_BASE_DEEP_SEARCH);
			ikMenuItem.setOptionId(INTERNAL_KNOWLEDGE_OPTION);
			ikMenuItem.setRouteOption(RespondingWith.DEEP_RAG_RESPONSE.name());
			ikMenu.setItems(List.of(ikMenuItem));
			outMenu.add(ikMenu);
		}
		return outMenu;
	}

}
