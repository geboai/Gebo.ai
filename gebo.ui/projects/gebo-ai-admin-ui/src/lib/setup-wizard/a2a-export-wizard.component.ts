/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

import { Component, Injectable } from "@angular/core";
import { A2AServerConfig, GeboA2AServerAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

/** True once at least one A2A server has been published. */
@Injectable()
export class A2AExportWizardStatusService extends AbstractStatusService {
    constructor(private service: GeboA2AServerAdminControllerService) {
        super();
    }
    public override getBooleanStatus(): Observable<boolean> {
        return this.service.findAll1().pipe(map(list => !!(list && list.length)));
    }
}

/**
 * Setup-area screen for the A2A "export" side: lists the published A2A servers and
 * opens the entity editor to add/edit them. Mirrors the exposed-MCP-server wizard.
 */
@Component({
    selector: "gebo-a2a-export-wizard-component",
    templateUrl: "a2a-export-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("A2AExportWizardComponent") }]
})
export class A2AExportWizardComponent extends BaseWizardSectionComponent {
    public configs: A2AServerConfig[] = [];

    constructor(
        setupWizardComunicationService: SetupWizardComunicationService,
        private service: GeboA2AServerAdminControllerService,
        private geboUIRoutingService: GeboUIActionRoutingService
    ) {
        super(setupWizardComunicationService);
    }

    public override reloadData(): void {
        this.loading = true;
        this.service.findAll1().subscribe({
            next: (list) => {
                this.configs = list || [];
                this.isSetupCompleted = this.configs.length > 0;
            },
            error: () => { this.loading = false; },
            complete: () => { this.loading = false; }
        });
    }

    protected addConfig(): void {
        const config: A2AServerConfig = {
            exportedRelativeUrl: '',
            enabled: false,
            accessibleToAll: true,
            exportedAgents: []
        };
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "A2AExportWizardComponent",
            targetType: "A2AServerConfig",
            target: config,
            onActionPerformed: () => this.reloadData()
        });
    }

    protected editConfig(config: A2AServerConfig): void {
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "A2AExportWizardComponent",
            targetType: "A2AServerConfig",
            target: config,
            onActionPerformed: () => this.reloadData()
        });
    }
}
