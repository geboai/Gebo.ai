/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
import { Component, Injectable } from "@angular/core";
import { GBaseObject, GeboAgentsNetworkAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";

/** The section is completed when there is at least one network of agents. */
@Injectable()
export class AgentsNetworksStatusService extends AbstractStatusService {
    constructor(private networksService: GeboAgentsNetworkAdminControllerService) {
        super();
    }

    public override getBooleanStatus(): Observable<boolean> {
        return this.networksService.getAgentsNetwork().pipe(map(x => (x && x.length) ? true : false));
    }
}

/**
 * Setup section listing the networks of agents, like the agents networks panel of the
 * administration. An agent works only within the network of agents it takes part
 * in, so the agents are configured from their network: a network is opened to
 * configure it and its agents, and copied from its editor, also a read-only system
 * network, to have one of one's own.
 */
@Component({
    selector: "gebo-ai-agents-networks-setup-wizard-component",
    templateUrl: "agents-networks-setup-wizard.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "AgentsNetworksSetupWizardModule", multi: false }, {
        provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("AgentsNetworksSetupWizardComponent")
    }]
})
export class AgentsNetworksSetupWizardComponent extends BaseWizardSectionComponent {
    protected agentsNetworks: GBaseObject[] = [];

    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private networksService: GeboAgentsNetworkAdminControllerService,
        private geboUIActionRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }

    public override reloadData(): void {
        this.loading = true;
        this.networksService.getAgentsNetwork().subscribe({
            next: (values) => {
                this.agentsNetworks = values ?? [];
            },
            error: () => {
                this.loading = false;
            },
            complete: () => {
                this.loading = false;
            }
        });
    }

    /** Opens the network editor; a network copied there shows up in the list. */
    protected openNetwork(network: GBaseObject): void {
        this.geboUIActionRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "AgentsNetworksSetupWizardComponent",
            targetType: "GAgentsNetwork",
            target: network,
            onActionPerformed: () => {
                this.reloadData();
            }
        });
    }
}
