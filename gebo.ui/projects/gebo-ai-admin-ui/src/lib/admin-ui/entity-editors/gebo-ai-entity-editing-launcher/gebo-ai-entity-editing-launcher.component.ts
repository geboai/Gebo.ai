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
 * This file defines a component that serves as a launcher for entity editing in the Gebo.ai application.
 * It acts as a central hub that manages references to various admin components for different entity types
 * in the system (knowledge bases, git systems, models, etc.). This launcher component appears to be part
 * of a larger administration interface.
 */
import { Component } from "@angular/core";
import { GeboAIChooseDataSourceTypeComponent, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";
import { GeboAiKnowledgeBaseAdminComponent } from "../gebo-ai-knowledgebase-admin/gebo-ai-knowledgebase-admin.component";
import { GeboAiProjectAdminComponent } from "../gebo-ai-knowledgebase-admin/gebo-ai-project-admin.component";
import { GeboAiGitSystemAdminComponent } from "../gebo-ai-git-admin/gebo-ai-git-system-admin.component";
import { GeboAiGitEndpointAdminComponent } from "../gebo-ai-git-admin/gebo-ai-git-endpoint-admin.component";
import { GeboAiSecretsAdminEditComponent } from "../gebo-ai-secrets-admin/gebo-ai-secrets-admin-edit.component";
import { GeboAIJobStatusViewerComponent } from "../gebo-ai-job-status-viewer/gebo-ai-job-status-viewer.component";
import { GeboAIFileSystemEndpointComponent } from "../gebo-ai-filesystems-admin/gebo-ai-filesystem-endpoint.component";
import { GeboAIOpenAIChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-openai-chatmodel-admin.component";
import { GeboAIOpenAIEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-openai-embedmodel-admin.component";
import { GeboAIOllamaChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-ollama-chatmodel-admin.component";
import { GeboAIOllamaEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-ollama-embedmodel-admin.component";
import { GeboAIChatProfileAdminComponent } from "../gebo-ai-chat-profile-admin/gebo-ai-chat-profile-admin.component";
import { GeboAIPromptAdminComponent } from "../gebo-ai-prompt-admin/gebo-ai-prompt-admin.component";

import { GeboAIGroupComponent } from "../gebo-ai-users-admin/gebo-ai-group.component";
import { GeboAIUploadsEndpointComponent } from "../gebo-ai-uploads-admin/gebo-ai-uploads-endpoint.component";
import { GeboAIGFileSystemShareReferenceAdminComponent } from '../gebo-ai-filesystems-admin/gebo-ai-filesystem-share-reference-admin.component';
import { GeboAIGoogleSearchAccountComponent } from "../gebo-ai-google-search-admin/gebo-ai-google-search-account.component";
import { GeboAIAnthropicChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-anthropic-chatmodel-admin.component";
import { GeboAIConfluenceEndpointComponent } from "../gebo-ai-atlassian-admin/gebo-ai-confluence-endpoint.component";
import { GeboAIConfluenceAdminComponent } from "../gebo-ai-atlassian-admin/gebo-ai-confluence-system-admin.component";
import { GeboAiGoogleDriveSystemAdminComponent } from "../gebo-ai-google-workspaces-admin/gebo-ai-google-drive-admin.component";
import { GeboAiGoogleDriveProjectEndpointAdminComponent } from "../gebo-ai-google-workspaces-admin/gebo-ai-google-drive-endpoint-admin.component";
import { GeboAIGenericOpenAIAPIChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-generic-openai-api-chatmodel-admin.component";
import { GeboAIGenericOpenAIAPIEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-generic-openai-api-embedmodel-admin.component";
import { GeboAIGoogleVertexChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-google-vertex-chatmodel-admin.component";
import { GeboAIGoogleVertexEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-google-vertex-embedmodel-admin.component";
import { GeboAIMistralAIChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-mistralai-chatmodel-admin.component";
import { GeboAIMistralAIEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-mistralai-embedmodel-admin.component";
import { GeboAISharepointEndpointComponent } from "../gebo-ai-sharepoint-admin/gebo-ai-sharepoint-endpoint.component";
import { GeboAISharepointAdminComponent } from "../gebo-ai-sharepoint-admin/gebo-ai-sharepoint-system-admin.component";
import { GeboAIJiraAdminComponent } from "../gebo-ai-atlassian-admin/gebo-ai-jira-system-admin.component";
import { GeboAIJiraEndpointComponent } from "../gebo-ai-atlassian-admin/gebo-ai-jira-endpoint.component";
import { GeboAIDeepseekChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-deepseek-chatmodel-admin.component";
import { GeboAIOauth2RegistrationComponent } from "../gebo-ai-oauth2-admin/gebo-ai-oauth2-registration.component";
import { GeboAIAzureOpenAIChatModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-azure-openai-chatmodel-admin.component";
import { GeboAIAzureOpenAIEmbedModelAdminComponent } from "../gebo-ai-models-admin/gebo-ai-azure-openai-embedmodel-admin.component";
import { GeboAIGraphRagExtractionConfigComponent } from "../gebo-graph-rag-extraction-config-admin/graph-rag-extraction-config.component";

/**
 * @Component GeboAiEntityEditingLauncherComponent
 * 
 * This component serves as a central hub for launching the appropriate admin interfaces
 * for different entity types in the Gebo.ai system. It maintains references to all the 
 * different administrative component types that can be launched depending on the entity 
 * being edited. This allows for dynamic loading of the appropriate editing interface 
 * based on user selection or system requirements.
 * 
 * The component references various admin components including:
 * - Knowledge base and project management
 * - Git system and endpoint management
 * - Secret management
 * - Job status viewing
 * - Various AI model administration (OpenAI, Anthropic, Ollama, etc.)
 * - Integration components for different platforms (Confluence, Google Drive, SharePoint, etc.)
 */
@Component({
    selector: "gebo-ai-entity-editing-launcher",
    templateUrl: "gebo-ai-entity-editing-launcher.component.html",
    standalone: false
})
export class GeboAiEntityEditingLauncherComponent {
    /**
     * Reference to the Knowledge Base admin component type
     */
    knowledgebaseAdminType = GeboAiKnowledgeBaseAdminComponent;

    /**
     * Reference to the Project admin component type
     */
    projectAdminType = GeboAiProjectAdminComponent;

    /**
     * Reference to the Git System admin component type
     */
    gitSystemAdminType = GeboAiGitSystemAdminComponent

    /**
     * Reference to the Git Endpoint admin component type
     */
    gitEndpointAdminType = GeboAiGitEndpointAdminComponent

    /**
     * Reference to the Secrets admin component type
     */
    secretAdminType = GeboAiSecretsAdminEditComponent;

    /**
     * Reference to the Job Status Viewer component type
     */
    jobViewerType = GeboAIJobStatusViewerComponent;

    /**
     * Reference to the Uploads Endpoint component type
     */
    uploadsType = GeboAIUploadsEndpointComponent

    /**
     * Reference to the File Systems Endpoint component type
     */
    fileSystemsType = GeboAIFileSystemEndpointComponent;

    /**
     * Reference to the OpenAI Chat Model admin component type
     */
    openAIChatModelType = GeboAIOpenAIChatModelAdminComponent;

    /**
     * Reference to the Anthropic Chat Model admin component type
     */
    AnthropicChatModelType = GeboAIAnthropicChatModelAdminComponent;

    /**
     * Reference to the Deepseek Chat Model admin component type
     */
    DeepseekChatModelType = GeboAIDeepseekChatModelAdminComponent;

    /**
     * Reference to the OpenAI Embedding Model admin component type
     */
    openAIEmbedModelType = GeboAIOpenAIEmbedModelAdminComponent;

    /**
     * Reference to the Ollama Chat Model admin component type
     */
    ollamaChatModelType = GeboAIOllamaChatModelAdminComponent;

    /**
     * Reference to the Ollama Embedding Model admin component type
     */
    ollamaEmbedModelType = GeboAIOllamaEmbedModelAdminComponent;

    /**
     * Reference to the Chat Profile admin component type
     */
    chatProfileType = GeboAIChatProfileAdminComponent;

    /**
     * Reference to the Prompt admin component type
     */
    promptType = GeboAIPromptAdminComponent;

    /**
     * Reference to the Users Group component type
     */
    usersGroupType = GeboAIGroupComponent;

    /**
     * Reference to the File System Share Reference admin component type
     */
    GFileSystemShareReferenceType = GeboAIGFileSystemShareReferenceAdminComponent;

    /**
     * Reference to the Google Search Configuration component type
     */
    googleSearchConfigType = GeboAIGoogleSearchAccountComponent;

    /**
     * Reference to the Confluence Endpoint component type
     */
    confluenceEndpointType = GeboAIConfluenceEndpointComponent;

    /**
     * Reference to the Confluence System admin component type
     */
    confluenceSystemType = GeboAIConfluenceAdminComponent;

    /**
     * Reference to the Google Drive System admin component type
     */
    googleDriveSystemType = GeboAiGoogleDriveSystemAdminComponent;

    /**
     * Reference to the Google Drive Endpoint admin component type
     */
    googleDriveEndpointType = GeboAiGoogleDriveProjectEndpointAdminComponent;

    /**
     * Reference to the Generic OpenAI API Chat Model admin component type
     */
    genericOpenAIAPIChatModelType = GeboAIGenericOpenAIAPIChatModelAdminComponent;

    /**
     * Reference to the Generic OpenAI API Embedding Model admin component type
     */
    genericOpenAIAPIEmbeddedModelType = GeboAIGenericOpenAIAPIEmbedModelAdminComponent;

    /**
     * Reference to the Google Vertex Chat Model admin component type
     */
    googleVertexChatModelType = GeboAIGoogleVertexChatModelAdminComponent;

    /**
     * Reference to the Google Vertex Embedding Model admin component type
     */
    googleVertexEmbeddingModelType = GeboAIGoogleVertexEmbedModelAdminComponent;

    /**
     * Reference to the MistralAI Chat Model admin component type
     */
    mistralAIChatModelType = GeboAIMistralAIChatModelAdminComponent;

    /**
     * Reference to the MistralAI Embedding Model admin component type
     */
    mistralAIEmbeddingModelType = GeboAIMistralAIEmbedModelAdminComponent;

    /**
     * Reference to the Choose Data Source Type component
     */
    choosedDataSourceType = GeboAIChooseDataSourceTypeComponent;

    /**
     * Reference to the SharePoint Endpoint admin component type
     */
    sharepointEndpointAdminType = GeboAISharepointEndpointComponent;

    /**
     * Reference to the SharePoint System admin component type
     */
    sharepointSystemType = GeboAISharepointAdminComponent;

    /**
     * Reference to the Jira System admin component type
     */
    jiraSystemType = GeboAIJiraAdminComponent;

    /**
     * Reference to the Jira Endpoint System admin component type
     */
    jiraEndpointSystemType = GeboAIJiraEndpointComponent;
    geboAIOauth2RegistrationComponent = GeboAIOauth2RegistrationComponent;
    azureOpenAIChatModelComponent = GeboAIAzureOpenAIChatModelAdminComponent;
    azureOpenAIEmbeddingModelComponent = GeboAIAzureOpenAIEmbedModelAdminComponent;

    geboGraphRagExtractionConfigComponent=GeboAIGraphRagExtractionConfigComponent;

    /**
     * Constructor for the GeboAiEntityEditingLauncherComponent
     * 
     * @param actionServices Service for routing actions within the UI
     */
    constructor(private actionServices: GeboUIActionRoutingService) {

    }
}