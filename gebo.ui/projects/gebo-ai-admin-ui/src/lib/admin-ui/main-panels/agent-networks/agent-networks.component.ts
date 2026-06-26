import { Component, OnInit } from "@angular/core";
import { AncestorPanelComponent } from "../ancestor-panel/ancestor-admin-panel.component";
import { GBaseObject, GeboAgentsNetworkAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboActionType, GeboUIActionRoutingService } from "@Gebo.ai/reusable-ui";

@Component({
    selector: "agent-networks-component",
    templateUrl: "agent-networks.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "AgentNetworksModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("AgentNetworksComponent") }]
})
export class AgentNetworksComponent extends AncestorPanelComponent implements OnInit {
    protected agentsNetworks: GBaseObject[] = [];
    protected loading:boolean=false;
    constructor(
        private agentNetworkService: GeboAgentsNetworkAdminControllerService,
        private geboUIActionEventService: GeboUIActionRoutingService
    ) {
        super();
    }
    ngOnInit(): void {
        this.reloadViewedData();
    }
    public override reloadViewedData(): void {
        this.loading=true;
        this.agentNetworkService.getAgentsNetwork().subscribe({
            next: (value) => {
                this.agentsNetworks = value;
            },
            complete: () => {
                this.loading=false;
            }
        })
    }
    protected editNetwork(data:GBaseObject) {
        this.geboUIActionEventService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "networkList",
            target: data,
            targetType: "GAgentsNetwork",
            onActionPerformed: (evt) => {
                this.reloadViewedData();
            }
        });
    }
}
