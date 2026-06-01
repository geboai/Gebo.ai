import { Component, Injectable, OnInit } from "@angular/core";
import { GAgentConfig, GBaseObject, GeboAgentAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { AbstractStatusService, BaseWizardSectionComponent, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
import { map, Observable } from "rxjs";
@Injectable()
export class AgentStatusService extends AbstractStatusService {
    constructor(private  geboAgentsService: GeboAgentAdminControllerService) {
        super()
    }

    /**
     * Overrides the base method to fetch the chat profile setup status
     * from the backend service and convert it to a boolean value.
     * 
     * @returns An Observable<boolean> indicating whether chat profiles are set up
     */
    public override getBooleanStatus(): Observable<boolean> {
        return this.geboAgentsService.getAgents().pipe(map(r => (r && r.length)?true:false));
    }
}
@Component({
    selector: "gebo-ai-agent-setup-wizard-component",
    templateUrl: "agent-setup-wizard.component.html",
    standalone: false
})
export class GeboAIAgentSetupWizardComponent extends BaseWizardSectionComponent {
    protected agentsConfig: GBaseObject[] = [];
    constructor(setupWizardComunicationService: SetupWizardComunicationService,
        private geboAgentsService: GeboAgentAdminControllerService,
        private geboUIRoutingService: GeboUIActionRoutingService) {
        super(setupWizardComunicationService);
    }

    public override reloadData(): void {
        this.loading=true;
        this.geboAgentsService.getAgents().subscribe({
            next:(values)=>{
                this.agentsConfig=values;
            },
            complete:()=>{
                this.loading=false;
            }
        });
    }
    protected addAgent(): void {
        const agentConfig: any = {
            accessibleToAll: true,
            enabledFunctions: [],
            maxLoopIterations: 5,
            subscribeAllTools: true
        };
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.NEW,
            context: {},
            contextType: "GeboAIAgentSetupWizardComponent",
            targetType: "GAgentConfig",
            target: agentConfig,
            onActionPerformed:(event)=>{
                this.reloadData();
            }
        });
    }
    protected openAgent(agentConfig: GBaseObject) {
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "GeboAIAgentSetupWizardComponent",
            targetType: "GAgentConfig",
            target: agentConfig,
            onActionPerformed:(event)=>{
                this.reloadData();
            }
        });
    }

}