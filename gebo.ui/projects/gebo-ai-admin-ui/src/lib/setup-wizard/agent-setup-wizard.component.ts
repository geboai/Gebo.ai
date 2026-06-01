import { Component, OnInit } from "@angular/core";
import { GAgentConfig, GBaseObject, GeboAgentAdminControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { BaseWizardSectionComponent, GeboActionType, GeboUIActionRoutingService, SetupWizardComunicationService } from "@Gebo.ai/reusable-ui";
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
            target: agentConfig
        });
    }
    protected openAgent(agentConfig: GBaseObject) {
        this.geboUIRoutingService.routeEvent({
            actionType: GeboActionType.OPEN,
            context: {},
            contextType: "GeboAIAgentSetupWizardComponent",
            targetType: "GAgentConfig",
            target: agentConfig
        });
    }

}