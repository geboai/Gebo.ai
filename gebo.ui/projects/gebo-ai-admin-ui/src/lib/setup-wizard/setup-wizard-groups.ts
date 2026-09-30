/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

/**
 * Setup wizard section groups, registered with the WIZARD_SECTION_GROUP token.
 * Sections join a group by setting groupId to one of the exported ids.
 */
import { SetupWizardsSectionGroup } from "@Gebo.ai/reusable-ui";

export const PLATFORM_SETUP_GROUP_ID: string = "platformSetupGroup";
export const AI_MODELS_SETUP_GROUP_ID: string = "aiModelsSetupGroup";
export const DATA_SOURCES_SETUP_GROUP_ID: string = "dataSourcesSetupGroup";
export const KNOWLEDGE_CHAT_SETUP_GROUP_ID: string = "knowledgeChatSetupGroup";
export const SEARCH_SETUP_GROUP_ID: string = "searchSetupGroup";
export const AGENTS_INTEGRATIONS_SETUP_GROUP_ID: string = "agentsIntegrationsSetupGroup";

export const platformSetupGroup: SetupWizardsSectionGroup = {
    groupId: PLATFORM_SETUP_GROUP_ID,
    orderEntry: 1,
    label: "Platform",
    description: "Accounts, work directory and authentication of this Gebo.ai installation"
};
export const aiModelsSetupGroup: SetupWizardsSectionGroup = {
    groupId: AI_MODELS_SETUP_GROUP_ID,
    orderEntry: 2,
    label: "AI models",
    description: "Large language models and provider deals"
};
export const dataSourcesSetupGroup: SetupWizardsSectionGroup = {
    groupId: DATA_SOURCES_SETUP_GROUP_ID,
    orderEntry: 3,
    label: "Data sources",
    description: "Filesystems and external systems whose contents can be indexed"
};
export const knowledgeChatSetupGroup: SetupWizardsSectionGroup = {
    groupId: KNOWLEDGE_CHAT_SETUP_GROUP_ID,
    orderEntry: 4,
    label: "Knowledge & chat",
    description: "Knowledge bases, graph R.A.G. knowledge extraction, R.A.G. chat profiles and their tuning"
};
export const searchSetupGroup: SetupWizardsSectionGroup = {
    groupId: SEARCH_SETUP_GROUP_ID,
    orderEntry: 5,
    label: "Search",
    description: "Web search and deep search services"
};
export const agentsIntegrationsSetupGroup: SetupWizardsSectionGroup = {
    groupId: AGENTS_INTEGRATIONS_SETUP_GROUP_ID,
    orderEntry: 6,
    label: "Agents & integrations",
    description: "Agents, MCP servers, A2A and administrative API keys"
};
