/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/*
 * Public API Surface of gebo-ai-admin-ui
 * AI generated comments
 * This is the main barrel file for the gebo-ai-admin-ui library.
 * It exports all the public-facing components, modules, and services
 * that consumers of this library will need to access.
 */

// Export the admin UI routing module for handling navigation within the admin interface
export * from './lib/admin-ui/gebo-ai-admin-routing.module';

// Export the main admin module which contains all components and services for the admin UI
export * from './lib/admin-ui/gebo-ai-admin.module';

// Export the setup wizard service which provides functionality for guiding users through initial configuration
export * from "./lib/setup-wizard/gebo-setup-wizards.service";

// Export the setup wizards module containing all components related to the setup process
export * from "./lib/setup-wizard/setup-wizards.module";

// Export the setup wizards routing module for navigation within the setup wizard flow
export * from "./lib/setup-wizard/setup-wizards-routing.module";

// Missing components exported to allow isolated partial compilation without NG3001/NG8001 errors
export * from "./lib/admin-ui/gebo-ai-admin.component";

export * from "./lib/admin-ui/main-panels/users-management/users-management.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-fast.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-fast.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-fast.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-fast.component";
export * from "./lib/setup-wizard/llms-setup-wizard.component";
export * from "./lib/setup-wizard/setup-wizards.component";
export * from "./lib/setup-wizard/vectorstore-wizard.component";
export * from "./lib/setup-wizard/work-folder-wizard.component";
export * from "./lib/setup-wizard/agent-setup-wizard.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-ranker-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-text-to-speech-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-transcript-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-text-to-speech-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-transcript-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-image-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-image-model-admin.component";
export * from "./lib/admin-ui/gebo-ai-standard-modules-injections.module";
export * from "./lib/admin-ui/main-panels/build-packaging-systems/build-packaging-systems.component";
export * from "./lib/admin-ui/main-panels/chat-profiles/chat-profiles.component";
export * from "./lib/admin-ui/main-panels/company-systems/systems.component";
export * from "./lib/admin-ui/main-panels/knowledge-bases/knowledge-bases.component";
export * from "./lib/admin-ui/main-panels/llms-systems/llms-systems.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-git-admin/gebo-ai-git-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-git-admin/gebo-ai-git-endpoint-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-tree.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-project-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-list.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-edit.component";
export * from "./lib/admin-ui/entity-editors/controls/build-systems-chooser/build-systems-chooser.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-job-status-viewer/gebo-ai-job-status-viewer.component";
export * from "./lib/admin-ui/main-panels/logs-view/logs-view.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-job-status-viewer/log-table.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-openai-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-image-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-text-to-speech-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-transcript-model-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-ranker-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-test-chat.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-ollama-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-ollama-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-chat-profile-admin/gebo-ai-chat-profile-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-prompt-admin/gebo-ai-prompt-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-user.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-group.component";
export * from "./lib/admin-ui/entity-editors/controls/access-control-group/access-control-group.component";
export * from "./lib/admin-ui/entity-editors/controls/prompt-wizard/prompt-wizard.component";
export * from "./lib/admin-ui/entity-editors/controls/advanced-settings-chatmodel-group/advanced-settings-chatmodel-group.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-uploads-admin/gebo-ai-uploads-endpoint.component";
export * from "./lib/admin-ui/main-panels/ancestor-panel/ancestor-admin-panel.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-shared-filesystems.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-share-reference-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-google-search-admin/gebo-ai-google-search-account.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-anthropic-chatmodel-admin.component";
export * from "./lib/admin-ui/main-panels/gebo-dashboard/gebo-dashboard.component";
export * from "./lib/admin-ui/main-panels/gebo-dashboard/gebo-embedded-piechart.component";
export * from "./lib/admin-ui/main-panels/gebo-dashboard/gebo-embedding-stats-panel.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-workspace-access.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-endpoint-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-embedmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/controls/standard-chat-model-settings/standard-chat-model-settings.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-fast.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-webdav-client-admin/gebo-ai-webdav-system-fast.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-endpoint.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-models-admin/gebo-ai-deepseek-chatmodel-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-oauth2-admin/gebo-ai-oauth2-registration.component";

export * from "./lib/admin-ui/entity-editors/gebo-graph-rag-extraction-config-admin/graph-rag-extraction-config.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-job-status-viewer/graphic-visualizer.component";
export * from "./lib/admin-ui/entity-editors/controls/graphrag-config/graphrag-config.component";
export * from "./lib/admin-ui/entity-editors/gebo-deep-search-admin/gebo-deep-search-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-agents-admin/gebo-ai-agents-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-agents-network-admin/gebo-ai-agents-network-admin.component";
export * from "./lib/admin-ui/main-panels/agent-networks/agent-networks.component";

export * from "./lib/setup-wizard/mcp-server-wizard.component";
export * from "./lib/setup-wizard/gebo-ai-mcp-server-wizard.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-mcp-client-admin/gebo-ai-mcp-client-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-users-admin/gebo-ai-change-user-password.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-mcp-server-admin/gebo-ai-mcp-server-admin.component";
export * from "./lib/admin-ui/entity-editors/gebo-ai-mcpclient-admin/gebo-ai-mcpclient-endpoint.component";


