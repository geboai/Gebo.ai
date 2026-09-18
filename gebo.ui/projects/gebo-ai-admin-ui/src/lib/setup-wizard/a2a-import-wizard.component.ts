/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Injectable } from "@angular/core";
import { A2AClientConfigControllerService, A2ARemoteAgentConfig } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

/** True once at least one remote A2A agent has been registered. */
@Injectable()
export class A2AImportWizardStatusService extends AbstractStatusService {
    constructor(private service: A2AClientConfigControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return this.service.list(0, 1).pipe(map(r => !!(r && r.content && r.content.length)));
    }
}

/**
 * Setup-area screen for the A2A "import" side: lists the registered external A2A
 * agents and opens the entity editor to add/edit them. Mirrors the MCP client
 * setup wizard.
 */
@Component({
    selector: "gebo-a2a-import-wizard-component",
    templateUrl: "a2a-import-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("A2AImportWizardComponent") }]
})
export class A2AImportWizardComponent extends BaseWizardSectionComponent {
    public configs: A2ARemoteAgentConfig[] = [];

    constructor(
        setupWizardComunicationService: SetupWizardComunicationService,
        private service: A2AClientConfigControllerService,
        private geboUIRoutingService: GeboUIActionRoutingService
    ) {
        super(setupWizardComunicationService);
    }

    public override reloadData(): void {
        this.loading = true;
        this.service.list(0, 100).subscribe({
            next: (value) => {
                this.configs = value.content || [];
                this.isSetupCompleted = this.configs.length > 0;
            },
            error: () => { this.loading = false; },
            complete: () => { this.loading = false; }
        });
    }

    protected addConfig(): void {
        const config: A2ARemoteAgentConfig = {
            agentCardUrl: '',
            transportType: 'JSONRPC',
            authMode: 'NONE',
            exportingPrefix: 'a2a-',
            enabled: false,
            accessibleToAll: true,
            skills: []
        };
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "A2AImportWizardComponent",
            targetType: "A2ARemoteAgentConfig",
            target: config,
            onActionPerformed: () => this.reloadData()
        });
    }

    protected editConfig(config: A2ARemoteAgentConfig): void {
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "A2AImportWizardComponent",
            targetType: "A2ARemoteAgentConfig",
            target: config,
            onActionPerformed: () => this.reloadData()
        });
    }
}
