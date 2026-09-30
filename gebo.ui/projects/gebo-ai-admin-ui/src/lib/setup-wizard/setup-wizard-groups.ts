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
export const AI_MODELS_AGENTS_SETUP_GROUP_ID: string = "aiModelsAgentsSetupGroup";
export const SECURITY_SETUP_GROUP_ID: string = "securitySetupGroup";
export const ENTERPRISE_INTEGRATIONS_SETUP_GROUP_ID: string = "enterpriseIntegrationsSetupGroup";
export const KNOWLEDGE_CHAT_SETUP_GROUP_ID: string = "knowledgeChatSetupGroup";
export const EXTERNAL_SEARCH_SETUP_GROUP_ID: string = "externalSearchSetupGroup";

export const platformSetupGroup: SetupWizardsSectionGroup = {
    groupId: PLATFORM_SETUP_GROUP_ID,
    orderEntry: 1,
    label: "Platform",
    description: "Work directory of this Gebo.ai installation"
};
export const aiModelsAgentsSetupGroup: SetupWizardsSectionGroup = {
    groupId: AI_MODELS_AGENTS_SETUP_GROUP_ID,
    orderEntry: 2,
    label: "AI Models, agents & interoperability",
    description: "Large language models, provider deals, agents, MCP servers and A2A interoperability"
};
export const securitySetupGroup: SetupWizardsSectionGroup = {
    groupId: SECURITY_SETUP_GROUP_ID,
    orderEntry: 3,
    label: "Security",
    description: "Users & groups, single sign-on and administrative API keys"
};
export const enterpriseIntegrationsSetupGroup: SetupWizardsSectionGroup = {
    groupId: ENTERPRISE_INTEGRATIONS_SETUP_GROUP_ID,
    orderEntry: 4,
    label: "Enterprise integrations",
    description: "Filesystems and enterprise systems whose contents can be indexed"
};
export const knowledgeChatSetupGroup: SetupWizardsSectionGroup = {
    groupId: KNOWLEDGE_CHAT_SETUP_GROUP_ID,
    orderEntry: 5,
    label: "Knowledge & chat",
    description: "Knowledge bases, graph R.A.G. knowledge extraction, R.A.G. chat profiles and their tuning"
};
export const externalSearchSetupGroup: SetupWizardsSectionGroup = {
    groupId: EXTERNAL_SEARCH_SETUP_GROUP_ID,
    orderEntry: 6,
    label: "External search",
    description: "Web search and deep search services"
};
