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
 * This file defines the main module for the Gebo.ai setup wizards system that guides users through
 * configuring their Gebo.ai installation. It includes multiple setup sections that follow a defined
 * sequence to set up various components of the system, such as user accounts, work directories,
 * vector databases, LLMs, and integrations with external systems.
 */

import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

import { LLMSetupWizardComponent } from "./llms-setup-wizard.component";
import { AlwaysTrueStatusService, EditableListboxModule, GEBO_AI_MODULE, GeboAIFieldTranslationContainerModule, ProjectAddContextMenuModule, SetupWizardPanelModule, SetupWizardsSection, VFilesystemSelectorModule, WIZARD_SECTION, TranslableModule, GeboAIApiKeyModule, GeboAINotificationsModule } from "@Gebo.ai/reusable-ui";
import { LLMSetupWizardService } from "./llms-setup-wizard.service";
import { SetupWizardsComponent } from "./setup-wizards.component";
import { DialogModule } from "primeng/dialog";
import { RadioButtonModule } from "primeng/radiobutton";
import { FieldsetModule } from "primeng/fieldset";
import { PanelModule } from "primeng/panel";
import { BlockUIModule } from "primeng/blockui";
import { ToggleButtonModule } from "primeng/togglebutton";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { TextareaModule } from "primeng/textarea";

import { TableModule } from 'primeng/table';
import { VectorStoreWizardComponent, VectorStoreWizardService } from "./vectorstore-wizard.component";
import { CheckboxModule } from "primeng/checkbox";
import { WorkFolderWizardComponent, WorkFolderWizardEnabledService, WorkFolderWizardStatusService } from "./work-folder-wizard.component";
import { SharedFilesystemAlreadySetupService, SharedFilesystemEnabledService, SharedFilesystemWizardComponent } from "./shared-filesystem-wizard.component";
import { KnowledgeBasePresentService, KnowledgeBaseWizardComponent } from "./knowledge-base-wizard.component";
import { GeboAiAdminModule } from "../admin-ui/gebo-ai-admin.module";
import { GeboSetupWizardService } from "./gebo-setup-wizards.service";
import { ChatProfileStatusService, ChatProfileWizardComponent } from "./chat-profile-wizard.component";
import { PaginatorModule } from "primeng/paginator";
import { UsersWizardComponent } from "./users-wizard.component";
import { ConfluenceInstalledModuleService, ConfluenceWizardComponent, ConfuenceStatusService } from "./confluence-wizard.component";
import { SharepointInstalledModuleService, SharepointStatusService, SharepointWizardComponent } from "./sharepoint-wizard.component";
import { WebdavInstalledModuleService, WebdavStatusService, WebdavWizardComponent } from "./webdav-wizard.component";
import { AwsS3InstalledModuleService, AwsS3StatusService, AwsS3WizardComponent } from "./aws-s3-wizard.component";
import { GoogleWorkspacesInstalledModuleService, GoogleWorkspacesStatusService, GoogleWorkspacesWizardComponent } from "./google-workspace-wizard.component";
import { JiraInstalledModuleService, JiraStatusService, JiraWizardComponent } from "./jira-wizard.component";
import { Oauth2SetupEnabledService, Oauth2SetupWizardService, Oauth2WizardComponent } from "./oauth2-wizard.component";
import { GraphRagStatusService, GraphRagWizardComponent, Neo4jModuleEnabledService } from "./graphrag-wizard.component";
import { AccordionModule } from 'primeng/accordion';
import { GeboAILLMSVendorConfiguration } from "./llms-setup-components/llms-vendor-configuration.component";
import { GeboAILlmsVendorModelTypeConfig } from "./llms-setup-components/llms-vendor-modeltype.component";
import { SelectButtonModule } from 'primeng/selectbutton';
import { TabsModule } from 'primeng/tabs';

import { GeboAIGoogleSearchWizardComponent, GoogleSearcStatusService } from "./google-search-wizard.component";
import { GeboAIDeepSearchWizardComponent } from "./deep-search-wizard.component";
import { GeboAIRagAutotuneWizardComponent, RagAutotuneStatusService } from "./rag-autotune-wizard.component";
import { GeboAIEasyVendorConfigurationComponent } from "./llms-setup-components/easy-vendor-configuration.component";
import { AgentStatusService, GeboAIAgentSetupWizardComponent } from "./agent-setup-wizard.component";
import { McpServerWizardComponent, McpServerWizardStatusService } from "./mcp-server-wizard.component";
import { GeboAIMCPServerWizardComponent, GeboAIMcpServerWizardStatusService } from "./gebo-ai-mcp-server-wizard.component";
import { GeneratedAdminApiKeyWizardComponent, GeneratedAdminApiKeyEnabledService } from "./generated-admin-api-key-wizard.component";
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
/**
 * Setup section for administrator user account configuration.
 * This is a mandatory section that appears first in the setup sequence.
 * It ensures at least one administrator account is created to manage the Gebo.ai installation.
 */
const adminUserSetupSection: SetupWizardsSection = {
    orderEntry: 1,
    label: "Administrator & others account setup",
    description: "At least an administration account is required to grant that this gebo.ai installation is properly managed, add other users if necessary",
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: AlwaysTrueStatusService,
    wizardComponent: UsersWizardComponent,
    wizardSectionId: "adminUserSetupSection",
    mandatory: true
};



/**
 * Setup section for configuring the Gebo.ai work directory.
 * This mandatory section sets up the folder where configurations and working files
 * will be stored, which should be backed up periodically.
 */
const geboWorkDirectorySetupSection: SetupWizardsSection = {
    orderEntry: 3,
    label: "Gebo.ai work directory",
    description: "Configure a work directory, it will be the folder where configurations and all working files of this installation will be stored. It's also a great idea to backup it periodically.",
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: WorkFolderWizardStatusService,
    wizardComponent: WorkFolderWizardComponent,
    wizardSectionId: "geboWorkDirectorySetupSection",
    mandatory: true
};

/**
 * Setup section for vector database configuration.
 * This mandatory section requires the work directory to be configured first.
 * It allows users to choose between different vector database options like local vector database,
 * Qdrant or MongoDB Atlas for RAG services.
 */
const VectorStoreSetupSection: SetupWizardsSection = {
    orderEntry: 4,
    requredStepsIds: ["geboWorkDirectorySetupSection"],
    label: "Vector database configuration",
    description: "A vector database have to be configured for Gebo.ai to provide retrieve augmented generation services, you can choose between a local vector database, Qdrant or Mongo Atlas",
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: VectorStoreWizardService,
    wizardComponent: VectorStoreWizardComponent,
    wizardSectionId: "VectorStoreSetupSection",
    mandatory: true
};


const oauth2SetupSection: SetupWizardsSection = {
    orderEntry: 5,
    label: "Single signon, oauth2 authentication",
    description: "Oauth2 single signon authentication and oauth2 clients configuration",
    enabledService: Oauth2SetupEnabledService,
    setupCompletedService: Oauth2SetupWizardService,
    wizardComponent: Oauth2WizardComponent,
    wizardSectionId: "oauth2SetupSection"
}
/**
 * Setup section for configuring Large Language Models.
 * This mandatory section requires the vector store setup to be completed first.
 * It enables configuration of different LLMs with cloud or local/LAN backend services.
 * At least one chat model and one embedding model must be configured.
 */
const adminLLMSSetupSection: SetupWizardsSection = {
    orderEntry: 6,
    requredStepsIds: ["VectorStoreSetupSection"],
    label: "Large language models setup",
    description: "Configure various llms with cloud or local/lan infrastructure backend services, at least one chat model and one embedding models are to be configured to let the software work properly.",
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: LLMSetupWizardService,
    wizardComponent: LLMSetupWizardComponent,
    wizardSectionId: "adminLLMSSetupSection",
    mandatory: true
};
const graphRagBaseSetupSection: SetupWizardsSection = {
    orderEntry: 7,
    requredStepsIds: ["VectorStoreSetupSection", "adminLLMSSetupSection"],
    label: "Graph rag/Knowledge extraction stup",
    description: "Configure graph rag and knowledge extraction subsystem",
    installedModule: Neo4jModuleEnabledService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: GraphRagStatusService,
    wizardComponent: GraphRagWizardComponent,
    wizardSectionId: "graphRagBaseSetupSection",
    mandatory: false,
    experimental: true
};
/**
 * Setup section for shared filesystem paths.
 * This optional section allows configuring company shared filesystems paths
 * that can be used as sources for RAG document retrieval in knowledge bases.
 */
const sharedFileSystemSetupSection: SetupWizardsSection = {
    orderEntry: 8,
    label: "Shared filesystems paths setup",
    description: "Configure the company shared filesystems paths to be used as retrieve augmented generation documents area in various knowledge bases",
    enabledService: SharedFilesystemEnabledService,
    setupCompletedService: SharedFilesystemAlreadySetupService,
    wizardComponent: SharedFilesystemWizardComponent,
    wizardSectionId: "sharedFileSystemSetupSection",
    mandatory: false
};

/**
 * Setup section for Atlassian Confluence integration.
 * This optional section configures Gebo.ai's connection to Atlassian Confluence cloud
 * or on-premise installations to access their content for RAG.
 */
const atlassianConfluenceSystemSetupSection: SetupWizardsSection = {
    orderEntry: 9,
    label: "Atlassian confluence integration",
    description: "Configure Gebo.ai to access your Atlassian Confluence cloud or on premise installations",
    installedModule: ConfluenceInstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: ConfuenceStatusService,
    wizardComponent: ConfluenceWizardComponent,
    wizardSectionId: "atlassianConfluenceSystemSetupSection",
    mandatory: false
};

/**
 * Setup section for Atlassian Jira integration.
 * This optional section configures Gebo.ai's connection to Atlassian Jira cloud installations.
 */
const jiraConfluenceSystemSetupSection: SetupWizardsSection = {
    orderEntry: 10,
    label: "Atlassian jira integration",
    description: "Configure Gebo.ai to access your Atlassian jira cloud installations",
    installedModule: JiraInstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: JiraStatusService,
    wizardComponent: JiraWizardComponent,
    wizardSectionId: "atlassianJiraSystemSetupSection",
    mandatory: false
};

/**
 * Setup section for Microsoft Sharepoint integration.
 * This optional section configures Gebo.ai's connection to Microsoft Sharepoint
 * cloud or on-premise installations.
 */
const microsoftSharepointSystemSetupSection: SetupWizardsSection = {
    orderEntry: 11,
    label: "Microsoft Sharepoint integration",
    description: "Configure Gebo.ai to access your Microsoft Sharepoint cloud or on premise installations",
    installedModule: SharepointInstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: SharepointStatusService,
    wizardComponent: SharepointWizardComponent,
    wizardSectionId: "microsoftSharepointSystemSetupSection",
    mandatory: false
};

/**
 * Setup section for AWS S3 integration.
 * This optional section configures Gebo.ai's connection to AWS S3 (or S3-compatible)
 * buckets to access their contents.
 */
const awsS3SystemSetupSection: SetupWizardsSection = {
    orderEntry: 11.5,
    label: "AWS S3 integration",
    description: "Configure Gebo.ai to access your AWS S3 (or S3-compatible) buckets contents",
    installedModule: AwsS3InstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: AwsS3StatusService,
    wizardComponent: AwsS3WizardComponent,
    wizardSectionId: "awsS3SystemSetupSection",
    mandatory: false
};

/**
 * Setup section for Google Drive/Workspaces integration.
 * This optional section configures Gebo.ai's connection to Google cloud Drive/Workspaces
 * to access team data.
 */
const googleDriveWorkspacesSystemSetupSection: SetupWizardsSection = {
    orderEntry: 12,
    label: "Google Drive/Workspaces integration",
    description: "Configure Gebo.ai to access your Google cloud Drive/Workspaces team data",
    installedModule: GoogleWorkspacesInstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: GoogleWorkspacesStatusService,
    wizardComponent: GoogleWorkspacesWizardComponent,
    wizardSectionId: "googleDriveWorkspacesSystemSetupSection",
    mandatory: false
};

const webdavSystemSetupSection: SetupWizardsSection = {
    orderEntry: 12.5,
    label: "WebDAV integration",
    description: "WebDAV compatible servers: Nextcloud, ownCloud, OpenCloud, Pydio Cells, Seafile/SeafDAV, ONLYOFFICE Workspace, Synology DSM WebDAV Server ...",
    installedModule: WebdavInstalledModuleService,
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: WebdavStatusService,
    wizardComponent: WebdavWizardComponent,
    wizardSectionId: "webdavSystemSetupSection",
    mandatory: false
};

/**
 * Setup section for knowledge base configuration.
 * This optional section requires work directory, vector store, and LLMs to be configured first.
 * It guides users to create at least one knowledge base for RAG services.
 */
const firstKnowledgeBaseSetupSection: SetupWizardsSection = {
    orderEntry: 13,
    requredStepsIds: ["geboWorkDirectorySetupSection", "VectorStoreSetupSection", "adminLLMSSetupSection"],
    label: "Configure at least a knowledge base",
    description: "Configure at least a knowledge base to let your users enjoy the retrieve augmented generations services of Gebo.ai",

    enabledService: AlwaysTrueStatusService,
    setupCompletedService: KnowledgeBasePresentService,
    wizardComponent: KnowledgeBaseWizardComponent,
    wizardSectionId: "firstKnowledgeBaseSetupSection",
    mandatory: false
};

/**
 * Setup section for RAG chat profile configuration.
 * This optional section requires knowledge base, work directory, vector store, and LLMs to be set up first.
 * It guides users to create at least one retrieve-augmented-generation (RAG) chat profile.
 */
const firstChatProfileBaseSetupSection: SetupWizardsSection = {
    orderEntry: 14,
    requredStepsIds: ["firstKnowledgeBaseSetupSection", "geboWorkDirectorySetupSection", "VectorStoreSetupSection", "adminLLMSSetupSection"],
    label: "Configure at least a \"R.A.G.\" chat profile",
    description: "Configure at least a retrieve augmented chat profile for your users based on one of your configured knowledge bases",
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: ChatProfileStatusService,
    wizardComponent: ChatProfileWizardComponent,
    wizardSectionId: "firstChatProfileBaseSetupSection",
    mandatory: false
};
const ragAutotuneSetupSection: SetupWizardsSection = {
    orderEntry: 15,
    requredStepsIds: [],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: RagAutotuneStatusService,
    label: "R.a.g. autotune",
    description: "Automatic retrieve augmented generation parameters tuning",
    wizardComponent: GeboAIRagAutotuneWizardComponent,
    wizardSectionId: "ragAutotuneSetupSection",
    mandatory: false
};

const googleSearchApiSetupSection: SetupWizardsSection = {
    orderEntry: 16,
    requredStepsIds: [],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: GoogleSearcStatusService,
    label: "Configure Google search api",
    description: "Configure Google search api credentials for AI web searches",
    wizardComponent: GeboAIGoogleSearchWizardComponent,
    wizardSectionId: "googleSearchApiSetupSection",
    mandatory: false
};
const deepSearchApiSetupSection: SetupWizardsSection = {
    orderEntry: 17,
    requredStepsIds: [],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: AlwaysTrueStatusService,
    label: "Configure Deep search",
    description: "Configure Deep search parameters",
    wizardComponent: GeboAIDeepSearchWizardComponent,
    wizardSectionId: "deepSearchApiSetupSection",
    mandatory: false
};

const agentSetupSection: SetupWizardsSection = {
    orderEntry: 18,
    requredStepsIds: [adminLLMSSetupSection.wizardSectionId],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: AgentStatusService,
    label: "Configure Agent Services",
    description: "Configure Agent Services and their available tools",
    wizardComponent: GeboAIAgentSetupWizardComponent,
    wizardSectionId: "agentSetupSection",
    mandatory: false

};

const mcpServerSetupSection: SetupWizardsSection = {
    orderEntry: 19,
    requredStepsIds: [],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: McpServerWizardStatusService,
    label: "Configure MCP Servers",
    description: "Configure Model Context Protocol (MCP) servers to extend the AI's tools, resources, and prompts.",
    wizardComponent: McpServerWizardComponent,
    wizardSectionId: "mcpServerSetupSection",
    mandatory: false
};

const geboMcpServerSetupSection: SetupWizardsSection = {
    orderEntry: 19.5,
    requredStepsIds: [],
    enabledService: AlwaysTrueStatusService,
    setupCompletedService: GeboAIMcpServerWizardStatusService,
    label: "Configure Exposed MCP Servers",
    description: "Configure local MCP servers to expose tools, resources, and prompts from your Gebo installation.",
    wizardComponent: GeboAIMCPServerWizardComponent,
    wizardSectionId: "geboMcpServerSetupSection",
    mandatory: false
};

const generatedAdminApiKeySetupSection: SetupWizardsSection = {
    orderEntry: 20,
    requredStepsIds: [],
    enabledService: GeneratedAdminApiKeyEnabledService,
    setupCompletedService: AlwaysTrueStatusService,
    label: "Configure Administrative API Keys",
    description: "Generate and manage API keys for system automation or MCP integrations.",
    wizardComponent: GeneratedAdminApiKeyWizardComponent,
    wizardSectionId: "generatedAdminApiKeySetupSection",
    mandatory: false
};

/**
 * @NgModule for Gebo.ai setup wizards
 * This module bundles all the setup wizard components, services, and dependencies.
 * It provides a structured approach to setting up the Gebo.ai application through
 * multiple sequential wizard sections that guide users through the configuration process.
 * Each wizard section is registered with the WIZARD_SECTION injection token.
 */
@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, SetupWizardPanelModule, DialogModule, EditableListboxModule, RadioButtonModule, FieldsetModule, PanelModule, BlockUIModule, ToggleButtonModule, ButtonModule, InputTextModule, GeboAINotificationsModule, TableModule, CheckboxModule, VFilesystemSelectorModule, ProjectAddContextMenuModule, GeboAiAdminModule, PaginatorModule, TextareaModule, GeboAIFieldTranslationContainerModule, AccordionModule, TranslableModule, SelectButtonModule, TabsModule, GeboAIApiKeyModule, GeboAINotificationsModule, SelectModule, DatePickerModule],
    declarations: [LLMSetupWizardComponent, SetupWizardsComponent, VectorStoreWizardComponent, WorkFolderWizardComponent, SharedFilesystemWizardComponent, KnowledgeBaseWizardComponent, ChatProfileWizardComponent, UsersWizardComponent, ConfluenceWizardComponent, SharepointWizardComponent, WebdavWizardComponent, AwsS3WizardComponent, GoogleWorkspacesWizardComponent, JiraWizardComponent, Oauth2WizardComponent, GraphRagWizardComponent, GeboAILLMSVendorConfiguration, GeboAILlmsVendorModelTypeConfig, GeboAIGoogleSearchWizardComponent, GeboAIDeepSearchWizardComponent, GeboAIRagAutotuneWizardComponent, GeboAIEasyVendorConfigurationComponent,GeboAIAgentSetupWizardComponent, McpServerWizardComponent, GeboAIMCPServerWizardComponent, GeneratedAdminApiKeyWizardComponent],
    exports: [SetupWizardsComponent],
    providers: [
        Oauth2SetupWizardService,
        Oauth2SetupEnabledService,
        LLMSetupWizardService,
        VectorStoreWizardService,
        WorkFolderWizardEnabledService,
        WorkFolderWizardStatusService,
        SharedFilesystemEnabledService,
        SharedFilesystemAlreadySetupService,
        KnowledgeBasePresentService,
        ConfuenceStatusService,
        GeboSetupWizardService,
        ChatProfileStatusService,
        SharepointStatusService,
        WebdavStatusService,
        AwsS3StatusService,
        GoogleWorkspacesStatusService,
        ConfluenceInstalledModuleService,
        SharepointInstalledModuleService,
        WebdavInstalledModuleService,
        AwsS3InstalledModuleService,
        GoogleWorkspacesInstalledModuleService,
        JiraInstalledModuleService,
        JiraStatusService,
        GraphRagStatusService,
        Neo4jModuleEnabledService,
        GoogleSearcStatusService,
        RagAutotuneStatusService,
        AgentStatusService,
        McpServerWizardStatusService,
        GeboAIMcpServerWizardStatusService,
        GeneratedAdminApiKeyEnabledService,
        { provide: WIZARD_SECTION, useValue: adminUserSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: geboWorkDirectorySetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: oauth2SetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: adminLLMSSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: VectorStoreSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: graphRagBaseSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: sharedFileSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: atlassianConfluenceSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: jiraConfluenceSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: microsoftSharepointSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: awsS3SystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: googleDriveWorkspacesSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: webdavSystemSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: firstKnowledgeBaseSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: firstChatProfileBaseSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: ragAutotuneSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: googleSearchApiSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: deepSearchApiSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: agentSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: mcpServerSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: geboMcpServerSetupSection, multi: true },
        { provide: WIZARD_SECTION, useValue: generatedAdminApiKeySetupSection, multi: true },
        { provide: GEBO_AI_MODULE, useValue: "GeboSetupWizardsModule", multi: false }]


})
export class GeboSetupWizardsModule {

}