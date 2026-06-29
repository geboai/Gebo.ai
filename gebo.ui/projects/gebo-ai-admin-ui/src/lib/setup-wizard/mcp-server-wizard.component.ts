/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

import { Component, Injectable } from "@angular/core";
import { McpClientConfigControllerService, MCPClientConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRequest, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

/**
 * Service that checks whether the MCP Server setup has been configured.
 * Extends AbstractStatusService to check if there is at least one MCP server registered.
 */
@Injectable()
export class McpServerWizardStatusService extends AbstractStatusService {
    constructor(private mcpClientConfigService: McpClientConfigControllerService) {
        super();
    }

    /**
     * Checks if any MCP Client Config is already saved in the system.
     * @returns An Observable that emits true if at least one MCP Client configuration is found.
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.mcpClientConfigService.listMCPClientConfig({ page: 0, pageSize: 1 }).pipe(
            map(r => !!(r && r.content && r.content.length))
        );
    }
}

/**
 * Component providing the wizard page for setting up and managing MCP Server clients.
 * Displays a list of configured MCP clients and opens the entity editor to add/edit them.
 */
@Component({
    selector: "gebo-mcp-server-wizard-component",
    templateUrl: "mcp-server-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("McpServerWizardComponent") }]
})
export class McpServerWizardComponent extends BaseWizardSectionComponent {
    /**
     * List of retrieved MCP Client configurations.
     */
    public mcpConfigs: MCPClientConfig[] = [];

    constructor(
        setupWizardComunicationService: SetupWizardComunicationService,
        private mcpClientConfigService: McpClientConfigControllerService,
        private geboUIRoutingService: GeboUIActionRoutingService
    ) {
        super(setupWizardComunicationService);
    }

    /**
     * Loads the list of MCP Client configurations from the backend.
     */
    public override reloadData(): void {
        this.loading = true;
        this.mcpClientConfigService.listMCPClientConfig({ page: 0, pageSize: 100 }).subscribe({
            next: (value) => {
                this.mcpConfigs = value.content || [];
                this.isSetupCompleted = this.mcpConfigs.length > 0;
            },
            error: (err) => {
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /**
     * Opens the editor in 'NEW' mode with an initialized MCPClientConfig.
     */
    protected addMcpConfig(): void {
        const config: MCPClientConfig = {
            transportType: 'SSE_LEGACY',
            authMode: 'NONE',
            exportingPrefix: 'mcp-',
            accessibleToAll: true,
            tools: [],
            resources: [],
            prompts: []
        };
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "McpServerWizardComponent",
            targetType: "MCPClientConfig",
            target: config,
            onActionPerformed: (event) => {
                this.reloadData();
            }
        });
    }

    /**
     * Opens the editor in 'OPEN' (EDIT) mode for the selected configuration.
     * @param config The MCPClientConfig to be edited.
     */
    protected editMcpConfig(config: MCPClientConfig): void {
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "McpServerWizardComponent",
            targetType: "MCPClientConfig",
            target: config,
            onActionPerformed: (event) => {
                this.reloadData();
            }
        });
    }
}
