import { Component, Input } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";

@Component({
    selector: "gebo-ai-pipline-route-display",
    templateUrl: "extensible-pipeline-route-label.component.html",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIExtensiblePipelineRouteDisplayComponent"),
            multi: false
        }
    ]
})
export class GeboAIExtensiblePipelineRouteDisplayComponent {
    @Input() pipelineRouterDecisionCode?: string;
}