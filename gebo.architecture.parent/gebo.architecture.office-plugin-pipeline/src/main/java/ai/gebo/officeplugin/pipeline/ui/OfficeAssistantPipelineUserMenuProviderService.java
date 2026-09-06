/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenuItem;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderService;
import ai.gebo.officeplugin.pipeline.OfficeAssistantConstants;

/**
 * User menu of the office-assistant pipeline. The office assistant always answers
 * through its network of agents, so it exposes a single "Agentic chat" option with
 * no explicit route override (the pipeline router shortcuts to the network).
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Component
@Scope("singleton")
public class OfficeAssistantPipelineUserMenuProviderService implements IPipelineUserMenuProviderService {

	private static final String AGENTIC_CHAT_MENU_ID = "agenticChat";
	private static final String AGENTIC_CHAT_DESCRIPTION = "Agentic chat";
	private static final String AGENTIC_CHAT_ICON = "pi pi-microchip-ai";

	@Override
	public String getPipelineId() {
		return OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE;
	}

	@Override
	public List<PipelineChatMenu> getUIMenu(String chatProfileCode) {
		PipelineChatMenuItem item = new PipelineChatMenuItem();
		item.setDefaultOption(true);
		item.setOptionId(AGENTIC_CHAT_MENU_ID);
		item.setIcon(AGENTIC_CHAT_ICON);
		item.setDescription(AGENTIC_CHAT_DESCRIPTION);
		item.setRouteOption(null);
		item.setPipelineId(OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE);

		PipelineChatMenu menu = new PipelineChatMenu();
		menu.setMenuId(AGENTIC_CHAT_MENU_ID);
		menu.setPipelineId(OfficeAssistantConstants.OFFICE_ASSISTANT_PIPELINE);
		menu.setOrder(1);
		menu.setDescription(AGENTIC_CHAT_DESCRIPTION);
		menu.setIcon(AGENTIC_CHAT_ICON);
		List<PipelineChatMenuItem> items = new ArrayList<>();
		items.add(item);
		menu.setItems(items);

		List<PipelineChatMenu> outMenu = new ArrayList<>();
		outMenu.add(menu);
		return outMenu;
	}
}
