/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenuItem;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenuItemParameter;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.model.RespondingWith;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import ai.gebo.openchat.pipeline.OpenChatConstants;
import lombok.AllArgsConstructor;

/**
 * User menu of the open-chat pipeline. It exposes only KB-free options:
 * <ul>
 * <li><b>Agentic chat</b> (default, no route override) - the open-chat network of
 * agents, which answers freely and searches external systems when needed;</li>
 * <li><b>Web / external search</b> ({@code DEEP_SEARCH_RESPONSE}) - multiple sources
 * and one item per enabled external source. There is no internal-knowledge-base
 * item and no R.a.g. item; the open-chat router forces the search onto external
 * sources only.</li>
 * </ul>
 * "Chat with file(s)" is added UI-side by the chat input shell when documents are
 * attached, exactly as for the default pipeline.
 */
@ConditionalOnProperty(prefix = "ai.gebo.openchat", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
@Scope("singleton")
@AllArgsConstructor
public class OpenChatPipelineUserMenuProviderService implements IPipelineUserMenuProviderService {

	private static final String AGENTIC_CHAT_MENU_ID = "agenticChat";
	private static final String AGENTIC_CHAT_DESCRIPTION = "Agentic chat";
	private static final String AGENTIC_CHAT_ICON = "pi pi-microchip-ai";
	private static final String DEEP_SEARCH_ID = "deepSearch";
	private static final String DEEP_SEARCH_ICON = "pi pi-deep-search";
	private static final String DEEP_SEARCH_DESCRIPTION = "Web search";
	private static final String MULTIPLE_SOURCES_DESCRIPTION = "Multiple sources";

	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDeepSearchDataSourceLookupService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;

	@Override
	public String getPipelineId() {
		return OpenChatConstants.OPEN_CHAT_PIPELINE;
	}

	@Override
	public List<PipelineChatMenu> getUIMenu(String chatProfileCode) {
		List<PipelineChatMenu> outMenu = new ArrayList<>();

		// Default agentic entry: no route override, so the open-chat router runs the
		// KB-free network of agents.
		PipelineChatMenuItem agenticItem = new PipelineChatMenuItem();
		agenticItem.setDefaultOption(true);
		agenticItem.setOptionId(AGENTIC_CHAT_MENU_ID);
		agenticItem.setIcon(AGENTIC_CHAT_ICON);
		agenticItem.setDescription(AGENTIC_CHAT_DESCRIPTION);
		agenticItem.setRouteOption(null);
		agenticItem.setPipelineId(OpenChatConstants.OPEN_CHAT_PIPELINE);
		PipelineChatMenu agenticMenu = new PipelineChatMenu();
		agenticMenu.setMenuId(AGENTIC_CHAT_MENU_ID);
		agenticMenu.setPipelineId(OpenChatConstants.OPEN_CHAT_PIPELINE);
		agenticMenu.setOrder(1);
		agenticMenu.setDescription(AGENTIC_CHAT_DESCRIPTION);
		agenticMenu.setIcon(AGENTIC_CHAT_ICON);
		agenticMenu.setItems(new ArrayList<>(List.of(agenticItem)));
		outMenu.add(agenticMenu);

		// External deep-search entries (no internal knowledge base): only shown when at
		// least one external data source is enabled.
		DeepSearchConfig deepSearchConfig = deepSearchConfigProvider.get();
		List<IGReactiveDeepSearchDataSourceService> enabledDataSources = enabledDeepSearchDataSourceLookupService
				.enabledDataSources(deepSearchConfig);
		if (enabledDataSources != null && !enabledDataSources.isEmpty()) {
			PipelineChatMenu deepSearchMenu = new PipelineChatMenu();
			deepSearchMenu.setMenuId(DEEP_SEARCH_ID);
			deepSearchMenu.setDescription(DEEP_SEARCH_DESCRIPTION);
			deepSearchMenu.setIcon(DEEP_SEARCH_ICON);
			deepSearchMenu.setPipelineId(OpenChatConstants.OPEN_CHAT_PIPELINE);

			// "Multiple sources": no explicit source param - the open-chat router fills in
			// all enabled external (non-IKB) sources.
			PipelineChatMenuItem multipleItem = new PipelineChatMenuItem();
			multipleItem.setOptionId(DEEP_SEARCH_ID);
			multipleItem.setIcon(DEEP_SEARCH_ICON);
			multipleItem.setDescription(MULTIPLE_SOURCES_DESCRIPTION);
			multipleItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
			multipleItem.setPipelineId(OpenChatConstants.OPEN_CHAT_PIPELINE);
			deepSearchMenu.getItems().add(multipleItem);

			for (IGReactiveDeepSearchDataSourceService ds : enabledDataSources) {
				// Defensive: never surface the internal knowledge base in the open-chat menu.
				if (DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID.equals(ds.getHandlerId())) {
					continue;
				}
				PipelineChatMenuItem dsItem = new PipelineChatMenuItem();
				dsItem.setRouteOption(RespondingWith.DEEP_SEARCH_RESPONSE.name());
				dsItem.setOptionId(RespondingWith.DEEP_SEARCH_RESPONSE.name() + "." + ds.getHandlerId());
				dsItem.setDescription(ds.getDescription(deepSearchConfig));
				dsItem.setProductId(ds.getProductId());
				dsItem.setPipelineId(OpenChatConstants.OPEN_CHAT_PIPELINE);
				PipelineChatMenuItemParameter param = new PipelineChatMenuItemParameter();
				param.setParameterName(OpenChatConstants.DEEP_SEARCHED_SYSTEMS);
				param.setParameterValue(List.of(ds.getHandlerId()));
				dsItem.getParameters().add(param);
				deepSearchMenu.getItems().add(dsItem);
			}
			outMenu.add(deepSearchMenu);
		}
		return outMenu;
	}
}
