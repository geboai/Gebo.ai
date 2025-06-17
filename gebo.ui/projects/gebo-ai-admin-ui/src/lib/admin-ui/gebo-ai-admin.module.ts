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
import { BrowseContentModule, EditableListboxModule, GeboAIContentReindexModule, GeboAIFileTypesModule, GeboAIModulesModule, GeboAIPluggableProjectEndpointModule, GeboAiRelationListModule, GeboAIReusableChatModel, GeboUIArchitectureModule, ProjectAddContextMenuModule } from "@Gebo.ai/reusable-ui";
import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AccordionModule } from 'primeng/accordion';
import { BlockUIModule } from 'primeng/blockui';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { ChipModule } from "primeng/chip";
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';
import { MessagesModule } from 'primeng/messages';
import { MultiSelectModule } from 'primeng/multiselect';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { PanelModule } from 'primeng/panel';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
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

import { CalendarModule } from 'primeng/calendar';
import { FieldsetModule } from 'primeng/fieldset';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { ListboxModule } from "primeng/listbox";
import { PaginatorModule } from 'primeng/paginator';
import { ToastModule } from 'primeng/toast';
import { TreeSelectModule } from 'primeng/treeselect';

import { GeboAiKnowledgeBaseTreeComponent } from "./entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-tree.component";
import { GeboAiProjectAdminComponent } from "./entity-editors/gebo-ai-knowledgebase-admin/gebo-ai-project-admin.component";

import { GeboAIPluggableKnowledgeAdminBaseTreeSearchService } from "@Gebo.ai/reusable-ui";
import { GeboAiEntityEditingLauncherComponent } from "./entity-editors/gebo-ai-entity-editing-launcher/gebo-ai-entity-editing-launcher.component";

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
import { MessageService } from "primeng/api";
import { GeboAIAccessControlComponent } from "./entity-editors/controls/access-control-group/access-control-group.component";
import { GeboAIAdvancedChatModelGroupComponent } from "./entity-editors/controls/advanced-settings-chatmodel-group/advanced-settings-chatmodel-group.component";
import { GeboAIChooseLLMFunctionsModule } from "./entity-editors/controls/choose-llm-functions/choose-llm-functions.module";
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
import { GeboAiEmbeddedDocumentsPiechartComponent } from "./main-panels/gebo-dashboard/gebo-embedded-piechart.component";
import { GeboAIEmbeddingStatsPanelComponent } from "./main-panels/gebo-dashboard/gebo-embedding-stats-panel.component";
import { SelectModule } from 'primeng/select';


import { GeboAIJiraEndpointComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-endpoint.component";
import { GeboAIJiraAdminComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-admin.component";
import { GeboAIJiraSystemFastComponent } from "./entity-editors/gebo-ai-atlassian-admin/gebo-ai-jira-system-fast.component";
import { GeboAICommonModulesInjectionsModule } from "./gebo-ai-standard-modules-injections.module";
import { GeboAIDeepseekChatModelAdminComponent } from "./entity-editors/gebo-ai-models-admin/gebo-ai-deepseek-chatmodel-admin.component";
import { GeboAIOauth2RegistrationComponent } from "./entity-editors/gebo-ai-oauth2-admin/gebo-ai-oauth2-registration.component";

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
  imports: [CommonModule, FormsModule,GeboAICommonModulesInjectionsModule, OverlayPanelModule, ListboxModule, TextareaModule, ChipModule, MessagesModule, FieldsetModule, MultiSelectModule, PaginatorModule, CalendarModule, ToastModule, FileUploadModule, InputNumberModule, PasswordModule, GeboAiRelationListModule, ReactiveFormsModule, MenuModule, TabViewModule, AccordionModule, TableModule, BlockUIModule, ButtonModule, TreeModule, DialogModule, PanelModule, GeboUIArchitectureModule, InputTextModule, CheckboxModule, EditableListboxModule, GeboAIReusableChatModel, GeboAIContentViewerModule, GeboAIChooseLLMFunctionsModule, BrowseContentModule, TreeSelectModule, VFilesystemSelectorModule, TranslableModule, GeboAIContentReindexModule, ProjectAddContextMenuModule, ChartModule, RadioButtonModule, GeboAIFileTypesModule, SelectModule,  GeboAIModulesModule],
  declarations: [GeboAiAdminComponent, BuildPackagingSystemsComponent, ChatProfilesComponent, SystemsComponent, KnowledgeBasesComponent, LlmsSystemsComponent, GeboAiGitSystemAdminComponent, GeboAiGitEndpointAdminComponent, GeboAiKnowledgeBaseAdminComponent, GeboAiKnowledgeBaseTreeComponent, GeboAiProjectAdminComponent, GeboAiEntityEditingLauncherComponent, GeboAiSecretsAdminListComponent, GeboAiSecretsAdminEditComponent, BuildSystemsChooserComponent, GeboAIJobStatusViewerComponent, GeboAiLogsViewComponent, LogTableComponent, GeboAIFileSystemEndpointComponent, GeboAIOpenAIChatModelAdminComponent, GeboAIOpenAIEmbedModelAdminComponent, GeboAITestChatComponent, GeboAIOllamaChatModelAdminComponent, GeboAIOllamaEmbedModelAdminComponent, GeboAIChatProfileAdminComponent, GeboAIPromptAdminComponent, GeboAIUsersManagementComponent, GeboAIUserComponent, GeboAIGroupComponent, GeboAIAccessControlComponent, GeboAIPromptWizardComponent, GeboAIAdvancedChatModelGroupComponent, GeboAIUploadsEndpointComponent, AncestorPanelComponent, GeboAISharedFilesystemsComponent, GeboAIGFileSystemShareReferenceAdminComponent, GeboAIGoogleSearchAccountComponent, GeboAIAnthropicChatModelAdminComponent, GeboAIDashboardComponent, GeboAiEmbeddedDocumentsPiechartComponent, GeboAIEmbeddingStatsPanelComponent, GeboAIGoogleWorkspaceAccessComponent, GeboAiGoogleDriveSystemAdminComponent, GeboAiGoogleDriveProjectEndpointAdminComponent, GeboAIConfluenceAdminComponent, GeboAIConfluenceEndpointComponent, GeboAIConfluenceSystemFastComponent, GeboAIGenericOpenAIAPIChatModelAdminComponent, GeboAIGenericOpenAIAPIEmbedModelAdminComponent, GeboAIGoogleVertexChatModelAdminComponent, GeboAIGoogleVertexEmbedModelAdminComponent, GeboAIMistralAIChatModelAdminComponent, GeboAIMistralAIEmbedModelAdminComponent, GeboAIStandardChatModelSettings, GeboAISharepointEndpointComponent, GeboAISharepointAdminComponent, GeboAISharepointSystemFastComponent, GeboAIGoogleDriveFastComponent, GeboAIJiraEndpointComponent, GeboAIJiraAdminComponent, GeboAIJiraSystemFastComponent,GeboAIDeepseekChatModelAdminComponent,GeboAIOauth2RegistrationComponent],
  exports: [GeboAiAdminComponent, GeboAiEntityEditingLauncherComponent, GeboAIUsersManagementComponent, GeboAIConfluenceSystemFastComponent, GeboAISharepointSystemFastComponent, GeboAIGoogleDriveFastComponent,GeboAIJiraSystemFastComponent],
  providers: [GeboAIPluggableKnowledgeAdminBaseTreeSearchService, MessageService]
})
export class GeboAiAdminModule {

}