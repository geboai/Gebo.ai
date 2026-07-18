/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * AI generated comments
 * 
 * GeboAiAdminModule is the main Angular module for the Gebo.ai administration interface.
 * This module integrates various components for managing AI-related configurations including:
 * - Knowledge bases management
 * - LLM systems configuration
 * - Chat profiles management
 * - User and group administration
 * - Various endpoint integrations (Git, Filesystems, Google, Confluence, SharePoint, etc.)
 * - Model administration for different AI providers (OpenAI, Ollama, Anthropic, MistralAI, etc.)
 * 
 * The module imports numerous Angular and PrimeNG modules for UI components and
 * declares all the admin components needed for the Gebo.ai administrative interface.
 */
import { BrowseContentModule, EditableListboxModule, GeboAIContentReindexModule, GeboAIFileTypesModule, GeboAiRelationListModule, GeboAIReusableChatModule, GeboOauth2SecretModule, GeboUIArchitectureModule, ProjectAddContextMenuModule, GeboAIContentSelectionFilterModule, GeboAIFieldTranslationContainerModule, GEBO_AI_MODULE, GeboAINotificationsModule, GeboAIChatModelUseModule, GEBO_UI_ENTITY_FORM_TOKEN, GeboUIEntityFormConfig, GeboAILLMSUsageDashboardModule, GeboAIWorkflowStatsDashboardModule, GeboBlockableContainerDirective } from "@Gebo.ai/reusable-ui";
import { CommonModule } from "@angular/common";
import { NgModule, ModuleWithProviders } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AccordionModule } from 'primeng/accordion';
import { BlockUIModule } from 'primeng/blockui';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { ChipModule } from "primeng/chip";
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';
import { MultiSelectModule } from 'primeng/multiselect';
import { PopoverModule } from 'primeng/popover';
import { PanelModule } from 'primeng/panel';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { TabsModule } from 'primeng/tabs';
import { TextareaModule } from "primeng/textarea";
import { TreeModule } from 'primeng/tree';
import { GeboAiGitEndpointAdminComponent } from "./entity-editors/gebo-ai-git-admin/gebo-ai-git-endpoint-admin.component";
import { GeboAiGitSystemAdminComponent } from "./entity-editors/gebo-ai-git-admin/gebo-ai-git-system-admin.component";
import { GeboAiKnowledgeBaseAdminComponent } from "./entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-admin.component";
import { GeboAiAdminComponent } from "./gebo-ai-admin.component";
import { BuildPackagingSystemsComponent } from "./main-panels/build-packaging-systems/build-packaging-systems.component";
import { ChatProfilesComponent } from "./main-panels/chat-profiles/chat-profiles.component";
import { SystemsComponent } from "./main-panels/company-systems/systems.component";
import { KnowledgeBasesComponent } from "./main-panels/knowledge-bases/knowledge-bases.component";
import { LlmsSystemsComponent } from "./main-panels/llms-systems/llms-systems.component";

import { DatePickerModule } from 'primeng/datepicker';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { ListboxModule } from "primeng/listbox";
import { PaginatorModule } from 'primeng/paginator';

import { TreeSelectModule } from 'primeng/treeselect';
import { OrganizationChartModule } from 'primeng/organizationchart';

import { GeboAiKnowledgeBaseTreeComponent } from "./entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-tree.component";
import { GeboAiProjectAdminComponent } from "./entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-project-admin.component";

import { GeboAIPluggableKnowledgeAdminBaseTreeSearchService } from "@Gebo.ai/reusable-ui";


import { BuildSystemsChooserComponent } from "./entity-editors/controls/build-systems-chooser/build-systems-chooser.component";
import { GeboAIChatProfileAdminComponent } from "./entity-editors/gebo-ai-chat-profile-admin/gebo-ai-chat-profile-admin.component";
import { GeboAIFileSystemEndpointComponent } from "./entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-endpoint.component";
import { GeboAIJobStatusViewerComponent } from "./entity-editors/gebo-ai-job-status-viewer/gebo-ai-job-status-viewer.component";
import { LogTableComponent } from "./entity-editors/gebo-ai-job-status-viewer/log-table.component";
import { GeboAIOllamaChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-ollama-chatmodel-admin.component";
import { GeboAIOllamaEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-ollama-embedmodel-admin.component";
import { GeboAIOpenAIChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-openai-chatmodel-admin.component";
import { GeboAIOpenAIEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-openai-embedmodel-admin.component";
import { GeboAITestChatComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-test-chat.component";
import { GeboAIPromptAdminComponent } from "./entity-editors/gebo-ai-prompt-admin/gebo-ai-prompt-admin.component";
import { GeboAiSecretsAdminEditComponent } from "./entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-edit.component";
import { GeboAiSecretsAdminListComponent } from "./entity-editors/gebo-ai-secrets-admin/gebo-ai-secrets-admin-list.component";

import { GeboAiLogsViewComponent } from "./main-panels/logs-view/logs-view.component";

import { GeboAIContentViewerModule } from "@Gebo.ai/reusable-ui";
import { GeboAIAccessControlComponent } from "./entity-editors/controls/access-control-group/access-control-group.component";
import { GeboAIAdvancedChatModelGroupComponent } from "./entity-editors/controls/advanced-settings-chatmodel-group/advanced-settings-chatmodel-group.component";
import { GeboAIChooseLLMFunctionsModule } from "@Gebo.ai/reusable-ui";
import { GeboAIPromptWizardComponent } from "./entity-editors/controls/prompt-wizard/prompt-wizard.component";
import { GeboAIGFileSystemShareReferenceAdminComponent } from "./entity-editors/gebo-ai-filesystems-admin/gebo-ai-filesystem-share-reference-admin.component";
import { GeboAISharedFilesystemsComponent } from "./entity-editors/gebo-ai-filesystems-admin/gebo-ai-shared-filesystems.component";
import { GeboAIGoogleSearchAccountComponent } from "./entity-editors/gebo-ai-google-search-admin/gebo-ai-google-search-account.component";
import { GeboAIAnthropicChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-anthropic-chatmodel-admin.component";
import { GeboAIUploadsEndpointComponent } from "./entity-editors/gebo-ai-uploads-admin/gebo-ai-uploads-endpoint.component";
import { GeboAIGroupComponent } from "./entity-editors/gebo-ai-users-admin/gebo-ai-group.component";
import { GeboAIUserComponent } from "./entity-editors/gebo-ai-users-admin/gebo-ai-user.component";
import { AncestorPanelComponent } from "./main-panels/ancestor-panel/ancestor-admin-panel.component";
import { GeboAIDashboardComponent } from "./main-panels/gebo-dashboard/gebo-dashboard.component";
import { GeboAIUsersManagementComponent } from "./main-panels/users-management/users-management.component";

import { TranslableModule, VFilesystemSelectorModule } from "@Gebo.ai/reusable-ui";
import { ChartModule } from 'primeng/chart';
import { RadioButtonModule } from 'primeng/radiobutton';
import { GeboAIStandardChatModelSettings } from "./entity-editors/controls/standard-chat-model-settings/standard-chat-model-settings.component";
import { GeboAIConfluenceEndpointComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-endpoint.component";
import { GeboAIConfluenceAdminComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-admin.component";
import { GeboAIConfluenceSystemFastComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-confluence-system-fast.component";
import { GeboAiGoogleDriveSystemAdminComponent } from "./entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-admin.component";
import { GeboAiGoogleDriveProjectEndpointAdminComponent } from "./entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-endpoint-admin.component";
import { GeboAIGoogleDriveFastComponent } from "./entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-drive-fast.component";
import { GeboAIGoogleWorkspaceAccessComponent } from "./entity-editors/gebo-ai-google-workspaces-admin/gebo-ai-google-workspace-access.component";
import { GeboAIGenericOpenAIAPIChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-chatmodel-admin.component";
import { GeboAIGenericOpenAIAPIEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-embedmodel-admin.component";
import { GeboAIGoogleVertexChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-chatmodel-admin.component";
import { GeboAIGoogleVertexEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-google-vertex-embedmodel-admin.component";
import { GeboAIMistralAIChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-chatmodel-admin.component";
import { GeboAIMistralAIEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-mistralai-embedmodel-admin.component";
import { GeboAISharepointEndpointComponent } from "./entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-endpoint.component";
import { GeboAISharepointAdminComponent } from "./entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-admin.component";
import { GeboAISharepointSystemFastComponent } from "./entity-editors/gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-fast.component";
import { GeboAIAwsS3EndpointComponent } from "./entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-endpoint.component";
import { GeboAIAwsS3AdminComponent } from "./entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-admin.component";
import { GeboAIAwsS3SystemFastComponent } from "./entity-editors/gebo-ai-aws-s3-admin/gebo-ai-aws-s3-system-fast.component";
import { GeboAiEmbeddedDocumentsPiechartComponent } from "./main-panels/gebo-dashboard/gebo-embedded-piechart.component";
import { GeboAIEmbeddingStatsPanelComponent } from "./main-panels/gebo-dashboard/gebo-embedding-stats-panel.component";
import { SelectModule } from 'primeng/select';


import { GeboAIJiraEndpointComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-endpoint.component";
import { GeboAIJiraAdminComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-admin.component";
import { GeboAIJiraSystemFastComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-fast.component";
import { GeboAIDeepseekChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-deepseek-chatmodel-admin.component";
import { GeboAIBedrockChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-chatmodel-admin.component";
import { GeboAIBedrockEmbedModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-embedmodel-admin.component";
import { GeboAIBedrockImageModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-image-model-admin.component";
import { GeboAIBedrockTextToSpeechModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-text-to-speech-model-admin.component";
import { GeboAIBedrockTranscriptModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-transcript-model-admin.component";
import { GeboAIBedrockRankerAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-bedrock-ranker-admin.component";
import { GeboAIOauth2RegistrationComponent } from "./entity-editors/gebo-ai-oauth2-admin/gebo-ai-oauth2-registration.component";
import { GeboAIGraphRagExtractionConfigComponent } from "./entity-editors/gebo-graph-rag-extraction-config-admin/graph-rag-extraction-config.component";
import { GeboAIJobGraphicVisualizerComponent } from "./entity-editors/gebo-ai-job-status-viewer/graphic-visualizer.component";
import { GeboAIGraphragConfigComponent } from "./entity-editors/controls/graphrag-config/graphrag-config.component";
import { GeboAIDeepSearchConfigAdminComponent } from "./entity-editors/gebo-deep-search-admin/gebo-deep-search-admin.component";
import { SelectButtonModule } from 'primeng/selectbutton';
import { GeboAIGenericOpenaAIAPiRankerAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-ranker-admin.component";
import { GeboAIOpenAITextToSpeechModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-openai-text-to-speech-model-admin.component";
import { GeboAIOpenAITranscriptModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-openai-transcript-model-admin.component";
import { GeboAIGenericOpenAIAPITextToSpeechModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-text-to-speech-model-admin.component";
import { GeboAIGenericOpenAIAPITranscriptModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-transcript-model-admin.component";
import { GeboAIOpenAIImageModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-openai-image-model-admin.component";
import { GeboAIGenericOpenAIAPIImageModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-generic-openai-api-image-model-admin.component";
import { GeboAIChangeUserPasswordComponent } from "./entity-editors/gebo-ai-users-admin/gebo-ai-change-user-password.component";
import { GeboAIAgentsAdminComponent } from "./entity-editors/gebo-ai-agents-admin/gebo-ai-agents-admin.component";
import { AgentNetworksComponent } from "./main-panels/agent-networks/agent-networks.component";
import { GeboAIAgentsNetworkAdminComponent } from "./entity-editors/gebo-ai-agents-network-admin/gebo-ai-agents-network-admin.component";
import { NgDiagramComponent, NgDiagramBackgroundComponent } from "ng-diagram";
import { GeboAIMcpClientAdminComponent } from "./entity-editors/gebo-ai-mcp-client-admin/gebo-ai-mcp-client-admin.component";
import { AgentNodeComponent } from "./entity-editors/gebo-ai-agents-network-admin/agent-node.component";
import { GeboAIMCPServerAdminComponent } from "./entity-editors/gebo-ai-mcp-server-admin/gebo-ai-mcp-server-admin.component";
import { GeboAIMCPClientEndpointComponent } from "./entity-editors/gebo-ai-mcpclient-admin/gebo-ai-mcpclient-endpoint.component";
//import { TabPanels, TabsModule } from "primeng/tabs";

/**
 * GeboAiAdminModule is the main Angular NgModule for the Gebo.ai administration interface.
 * 
 * This module brings together all components, services, and dependencies needed for the admin UI:
 * - Imports Angular and third-party UI modules (PrimeNG, Gebo.ai reusable components)
 * - Declares all administrative components for various systems (Git, Knowledgebase, Models, etc.)
 * - Provides necessary services for tree searching and UI notifications
 * - Exports components that need to be available to any consuming modules
 * 
 * The module structure reflects a comprehensive admin interface for managing AI resources,
 * including knowledge bases, model configurations, integration endpoints, and user management.
 */
@NgModule({
  imports: [CommonModule, FormsModule, PopoverModule, ListboxModule, TextareaModule, ChipModule, FieldsetModule, MultiSelectModule, PaginatorModule, DatePickerModule, FileUploadModule, InputNumberModule, PasswordModule, GeboAiRelationListModule, ReactiveFormsModule, MenuModule, TabsModule, AccordionModule, TableModule, BlockUIModule, ButtonModule, TreeModule, DialogModule, PanelModule, GeboUIArchitectureModule, InputTextModule, CheckboxModule, EditableListboxModule, GeboAIReusableChatModule, GeboAIContentViewerModule, GeboAIChooseLLMFunctionsModule, BrowseContentModule, TreeSelectModule, VFilesystemSelectorModule, TranslableModule, GeboAIContentReindexModule, ProjectAddContextMenuModule, ChartModule, RadioButtonModule, GeboAIFileTypesModule, SelectModule, GeboOauth2SecretModule, GeboAIContentSelectionFilterModule, GeboAIFieldTranslationContainerModule, SelectButtonModule, GeboAINotificationsModule, GeboAIChatModelUseModule, OrganizationChartModule, NgDiagramComponent, NgDiagramBackgroundComponent, AgentNodeComponent, GeboAILLMSUsageDashboardModule, GeboAIWorkflowStatsDashboardModule, GeboBlockableContainerDirective],
  declarations: [GeboAiAdminComponent, BuildPackagingSystemsComponent, ChatProfilesComponent, AgentNetworksComponent, SystemsComponent, KnowledgeBasesComponent, LlmsSystemsComponent, GeboAiGitSystemAdminComponent, GeboAiGitEndpointAdminComponent, GeboAiKnowledgeBaseAdminComponent, GeboAiKnowledgeBaseTreeComponent, GeboAiProjectAdminComponent, GeboAiSecretsAdminListComponent, GeboAiSecretsAdminEditComponent, BuildSystemsChooserComponent, GeboAIJobStatusViewerComponent, GeboAiLogsViewComponent, LogTableComponent, GeboAIFileSystemEndpointComponent, GeboAIOpenAIChatModelAdminComponent, GeboAIOpenAIEmbedModelAdminComponent, GeboAITestChatComponent, GeboAIOllamaChatModelAdminComponent, GeboAIOllamaEmbedModelAdminComponent, GeboAIChatProfileAdminComponent, GeboAIPromptAdminComponent, GeboAIUsersManagementComponent, GeboAIUserComponent, GeboAIGroupComponent, GeboAIAccessControlComponent, GeboAIPromptWizardComponent, GeboAIAdvancedChatModelGroupComponent, GeboAIUploadsEndpointComponent, AncestorPanelComponent, GeboAISharedFilesystemsComponent, GeboAIGFileSystemShareReferenceAdminComponent, GeboAIGoogleSearchAccountComponent, GeboAIAnthropicChatModelAdminComponent, GeboAIDashboardComponent, GeboAiEmbeddedDocumentsPiechartComponent, GeboAIEmbeddingStatsPanelComponent, GeboAIGoogleWorkspaceAccessComponent, GeboAiGoogleDriveSystemAdminComponent, GeboAiGoogleDriveProjectEndpointAdminComponent, GeboAIConfluenceAdminComponent, GeboAIConfluenceEndpointComponent, GeboAIConfluenceSystemFastComponent, GeboAIGenericOpenAIAPIChatModelAdminComponent, GeboAIGenericOpenAIAPIEmbedModelAdminComponent, GeboAIGoogleVertexChatModelAdminComponent, GeboAIGoogleVertexEmbedModelAdminComponent, GeboAIMistralAIChatModelAdminComponent, GeboAIMistralAIEmbedModelAdminComponent, GeboAIStandardChatModelSettings, GeboAISharepointEndpointComponent, GeboAISharepointAdminComponent, GeboAISharepointSystemFastComponent, GeboAIAwsS3AdminComponent, GeboAIAwsS3EndpointComponent, GeboAIAwsS3SystemFastComponent, GeboAIGoogleDriveFastComponent, GeboAIJiraEndpointComponent, GeboAIJiraAdminComponent, GeboAIJiraSystemFastComponent, GeboAIDeepseekChatModelAdminComponent, GeboAIBedrockChatModelAdminComponent, GeboAIBedrockEmbedModelAdminComponent, GeboAIBedrockImageModelAdminComponent, GeboAIBedrockTextToSpeechModelAdminComponent, GeboAIBedrockTranscriptModelAdminComponent, GeboAIBedrockRankerAdminComponent, GeboAIOauth2RegistrationComponent, GeboAIGraphRagExtractionConfigComponent, GeboAIJobGraphicVisualizerComponent, GeboAIGraphragConfigComponent, GeboAIDeepSearchConfigAdminComponent, GeboAIGenericOpenaAIAPiRankerAdminComponent, GeboAIOpenAITextToSpeechModelAdminComponent, GeboAIOpenAITranscriptModelAdminComponent, GeboAIGenericOpenAIAPITextToSpeechModelAdminComponent, GeboAIGenericOpenAIAPITranscriptModelAdminComponent, GeboAIOpenAIImageModelAdminComponent, GeboAIGenericOpenAIAPIImageModelAdminComponent, GeboAIChangeUserPasswordComponent, GeboAIAgentsAdminComponent, GeboAIAgentsNetworkAdminComponent, GeboAIMcpClientAdminComponent, GeboAIMCPServerAdminComponent, GeboAIMCPClientEndpointComponent],
  exports: [GeboAiAdminComponent, BuildPackagingSystemsComponent, ChatProfilesComponent, AgentNetworksComponent, SystemsComponent, KnowledgeBasesComponent, LlmsSystemsComponent, GeboAiGitSystemAdminComponent, GeboAiGitEndpointAdminComponent, GeboAiKnowledgeBaseAdminComponent, GeboAiKnowledgeBaseTreeComponent, GeboAiProjectAdminComponent, GeboAiSecretsAdminListComponent, GeboAiSecretsAdminEditComponent, BuildSystemsChooserComponent, GeboAIJobStatusViewerComponent, GeboAiLogsViewComponent, LogTableComponent, GeboAIFileSystemEndpointComponent, GeboAIOpenAIChatModelAdminComponent, GeboAIOpenAIEmbedModelAdminComponent, GeboAITestChatComponent, GeboAIOllamaChatModelAdminComponent, GeboAIOllamaEmbedModelAdminComponent, GeboAIChatProfileAdminComponent, GeboAIPromptAdminComponent, GeboAIUsersManagementComponent, GeboAIUserComponent, GeboAIGroupComponent, GeboAIAccessControlComponent, GeboAIPromptWizardComponent, GeboAIAdvancedChatModelGroupComponent, GeboAIUploadsEndpointComponent, AncestorPanelComponent, GeboAISharedFilesystemsComponent, GeboAIGFileSystemShareReferenceAdminComponent, GeboAIGoogleSearchAccountComponent, GeboAIAnthropicChatModelAdminComponent, GeboAIDashboardComponent, GeboAiEmbeddedDocumentsPiechartComponent, GeboAIEmbeddingStatsPanelComponent, GeboAIGoogleWorkspaceAccessComponent, GeboAiGoogleDriveSystemAdminComponent, GeboAiGoogleDriveProjectEndpointAdminComponent, GeboAIConfluenceAdminComponent, GeboAIConfluenceEndpointComponent, GeboAIConfluenceSystemFastComponent, GeboAIGenericOpenAIAPIChatModelAdminComponent, GeboAIGenericOpenAIAPIEmbedModelAdminComponent, GeboAIGoogleVertexChatModelAdminComponent, GeboAIGoogleVertexEmbedModelAdminComponent, GeboAIMistralAIChatModelAdminComponent, GeboAIMistralAIEmbedModelAdminComponent, GeboAIStandardChatModelSettings, GeboAISharepointEndpointComponent, GeboAISharepointAdminComponent, GeboAISharepointSystemFastComponent, GeboAIAwsS3AdminComponent, GeboAIAwsS3EndpointComponent, GeboAIAwsS3SystemFastComponent, GeboAIGoogleDriveFastComponent, GeboAIJiraEndpointComponent, GeboAIJiraAdminComponent, GeboAIJiraSystemFastComponent, GeboAIDeepseekChatModelAdminComponent, GeboAIBedrockChatModelAdminComponent, GeboAIBedrockEmbedModelAdminComponent, GeboAIBedrockImageModelAdminComponent, GeboAIBedrockTextToSpeechModelAdminComponent, GeboAIBedrockTranscriptModelAdminComponent, GeboAIBedrockRankerAdminComponent, GeboAIOauth2RegistrationComponent, GeboAIGraphRagExtractionConfigComponent, GeboAIJobGraphicVisualizerComponent, GeboAIGraphragConfigComponent, GeboAIDeepSearchConfigAdminComponent, GeboAIGenericOpenaAIAPiRankerAdminComponent, GeboAIOpenAITextToSpeechModelAdminComponent, GeboAIOpenAITranscriptModelAdminComponent, GeboAIGenericOpenAIAPITextToSpeechModelAdminComponent, GeboAIGenericOpenAIAPITranscriptModelAdminComponent, GeboAIOpenAIImageModelAdminComponent, GeboAIGenericOpenAIAPIImageModelAdminComponent, GeboAIChangeUserPasswordComponent, GeboAIAgentsAdminComponent, GeboAIAgentsNetworkAdminComponent, GeboAIMcpClientAdminComponent, GeboAIMCPServerAdminComponent, GeboAIMCPClientEndpointComponent],
  providers: [GeboAIPluggableKnowledgeAdminBaseTreeSearchService,
    { provide: GEBO_AI_MODULE, useValue: "GeboAIAdminModule", multi: false }
  ]
})
export class GeboAiAdminModule {
  static forRoot(): ModuleWithProviders<GeboAiAdminModule> {
    return {
      ngModule: GeboAiAdminModule,
      providers: [

        [
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GConfluenceSystem', entityAliases: ['ai.gebo.atlassian.confluence.handler.GConfluenceSystem'], entityUI: GeboAIConfluenceAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGitSystem', entityAliases: ['GGitContentManagementSystem', 'ai.gebo.git.content.handler.GGitContentManagementSystem'], entityUI: GeboAiGitSystemAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GraphRagExtractionConfig', entityAliases: ['ai.gebo.architecture.graphrag.extraction.model.GraphRagExtractionConfig'], entityUI: GeboAIGraphRagExtractionConfigComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGitProjectEndpoint', entityAliases: ['ai.gebo.git.content.handler.GGitProjectEndpoint'], entityUI: GeboAiGitEndpointAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GJiraProjectEndpoint', entityAliases: ['ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint'], entityUI: GeboAIJiraEndpointComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GConfluenceProjectEndpoint', entityAliases: ['ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint'], entityUI: GeboAIConfluenceEndpointComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GJiraSystem', entityAliases: ['ai.gebo.atlassian.jira.handler.GJiraSystem'], entityUI: GeboAIJiraAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'DeepSearchConfig', entityAliases: ['ai.gebo.llms.deepsearch.model.DeepSearchConfig'], entityUI: GeboAIDeepSearchConfigAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'SecretWrapper', entityAliases: ['ai.gebo.secrets.model.SecretWrapper'], entityUI: GeboAiSecretsAdminEditComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GSharepointProjectEndpoint', entityAliases: ['ai.gebo.sharepoint.handler.GSharepointProjectEndpoint'], entityUI: GeboAISharepointEndpointComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GUploadsProjectEndpoint', entityAliases: ['ai.gebo.uploads.content.handler.GUploadsProjectEndpoint'], entityUI: GeboAIUploadsEndpointComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'UsersGroup', entityAliases: ['ai.gebo.security.model.UsersGroup'], entityUI: GeboAIGroupComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GPromptConfig', entityAliases: ['ai.gebo.architecture.ai.model.GPromptConfig'], entityUI: GeboAIPromptAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GAnthropicChatModelConfig', entityAliases: ['ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig'], entityUI: GeboAIAnthropicChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GSharepointContentManagementSystem', entityAliases: ['ai.gebo.sharepoint.handler.GSharepointContentManagementSystem'], entityUI: GeboAISharepointAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GAwsS3ProjectEndpoint', entityAliases: ['ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint'], entityUI: GeboAIAwsS3EndpointComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GAwsS3System', entityAliases: ['ai.gebo.awss3.content.handler.GAwsS3System'], entityUI: GeboAIAwsS3AdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GDeepseekChatModelConfig', entityAliases: ['ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig'], entityUI: GeboAIDeepseekChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockChatModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockChatModelConfig'], entityUI: GeboAIBedrockChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelConfig'], entityUI: GeboAIBedrockEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockImageModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockImageModelConfig'], entityUI: GeboAIBedrockImageModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockTextToSpeechModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockTextToSpeechModelConfig'], entityUI: GeboAIBedrockTextToSpeechModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockTranscriptModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockTranscriptModelConfig'], entityUI: GeboAIBedrockTranscriptModelAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GBedrockRankerModelConfig', entityAliases: ['ai.gebo.llms.aws_bedrock.model.GBedrockRankerModelConfig'], entityUI: GeboAIBedrockRankerAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPIChatModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelConfig'], entityUI: GeboAIGenericOpenAIAPIChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPIEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelConfig'], entityUI: GeboAIGenericOpenAIAPIEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPIRankerModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPIRankerModelConfig'], entityUI: GeboAIGenericOpenaAIAPiRankerAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOpenAITextToSpeechModelConfig', entityAliases: ['ai.gebo.llms.openai.model.GOpenAITextToSpeechModelConfig'], entityUI: GeboAIOpenAITextToSpeechModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOpenAITranscriptModelConfig', entityAliases: ['ai.gebo.llms.openai.model.GOpenAITranscriptModelConfig'], entityUI: GeboAIOpenAITranscriptModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPITextToSpeechModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelConfig'], entityUI: GeboAIGenericOpenAIAPITextToSpeechModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPITranscriptModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelConfig'], entityUI: GeboAIGenericOpenAIAPITranscriptModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOpenAIImageModelConfig', entityAliases: ['ai.gebo.llms.openai.model.GOpenAIImageModelConfig'], entityUI: GeboAIOpenAIImageModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GenericOpenAIAPIImageModelConfig', entityAliases: ['ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig'], entityUI: GeboAIGenericOpenAIAPIImageModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGoogleVertexChatModelConfig', entityAliases: ['ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelConfig'], entityUI: GeboAIGoogleVertexChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGoogleVertexEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.google_vertex.model.GGoogleVertexEmbeddingModelConfig'], entityUI: GeboAIGoogleVertexEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'Oauth2ProviderRegistration', entityAliases: ['Oauth2ProviderModifiableData'], entityUI: GeboAIOauth2RegistrationComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GKnowledgeBase', entityAliases: ['ai.gebo.knlowledgebase.model.contents.GKnowledgeBase'], entityUI: GeboAiKnowledgeBaseAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOllamaChatModelConfig', entityAliases: ['ai.gebo.llms.ollama.model.GOllamaChatModelConfig'], entityUI: GeboAIOllamaChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOllamaEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig'], entityUI: GeboAIOllamaEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GProject', entityAliases: ['ai.gebo.knlowledgebase.model.projects.GProject'], entityUI: GeboAiProjectAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOpenAIChatModelConfig', entityAliases: ['ai.gebo.llms.openai.model.GOpenAIChatModelConfig'], entityUI: GeboAIOpenAIChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GOpenAIEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.openai.model.GOpenAIEmbeddingModelConfig'], entityUI: GeboAIOpenAIEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GJobStatus', entityAliases: ['ai.gebo.knlowledgebase.model.jobs.GJobStatus'], entityUI: GeboAIJobStatusViewerComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GMistralEmbeddingModelConfig', entityAliases: ['ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig'], entityUI: GeboAIMistralAIEmbedModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GMistralChatModelConfig', entityAliases: ['ai.gebo.llms.mistralai.model.GMistralChatModelConfig'], entityUI: GeboAIMistralAIChatModelAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGoogleDriveProjectEndpoint', entityAliases: ['ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint'], entityUI: GeboAiGoogleDriveProjectEndpointAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GFilesystemProjectEndpoint', entityAliases: ['ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint'], entityUI: GeboAIFileSystemEndpointComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GFileSystemShareReference', entityAliases: ['ai.gebo.filesystem.content.handler.GFileSystemShareReference'], entityUI: GeboAIGFileSystemShareReferenceAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGoogleSearchApiCredentials', entityAliases: ['ai.gebo.googlesearch.handler.model.GGoogleSearchApiCredentials'], entityUI: GeboAIGoogleSearchAccountComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GChatProfile', entityAliases: ['GChatProfileConfiguration', 'ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration'], entityUI: GeboAIChatProfileAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GGoogleDriveSystem', entityAliases: ['ai.gebo.googledrive.handlers.GGoogleDriveSystem'], entityUI: GeboAiGoogleDriveSystemAdminComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'Secret', entityAliases: ['SecretWrapper'], entityUI: GeboAiSecretsAdminEditComponent } as GeboUIEntityFormConfig, multi: true },

          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: 'GAgentConfig', entityUI: GeboAIAgentsAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: "GAgentsNetwork", entityUI: GeboAIAgentsNetworkAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: "MCPClientConfig", entityUI: GeboAIMcpClientAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: "GeboMCPServerConfig", entityUI: GeboAIMCPServerAdminComponent } as GeboUIEntityFormConfig, multi: true },
          { provide: GEBO_UI_ENTITY_FORM_TOKEN, useValue: { entityName: "MCPClientProjectEndpoint", entityAliases: ["ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint"], entityUI: GeboAIMCPClientEndpointComponent } as GeboUIEntityFormConfig, multi: true }
        ]
      ]
    };
  }
}