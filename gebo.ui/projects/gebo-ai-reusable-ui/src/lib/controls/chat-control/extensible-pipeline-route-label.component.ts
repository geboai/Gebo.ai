import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { Subscription, timer } from "rxjs";

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
export class GeboAIExtensiblePipelineRouteDisplayComponent implements OnChanges {
    protected visible: boolean = false;
    protected timer?: Subscription;
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["pipelineRouterDecisionCode"]) {
            try {
                this.timer?.unsubscribe();
            } catch (e) {

            }
            this.visible = true;
            this.timer = timer(5000).subscribe({
                next: () => {
                    this.hide();
                    try {
                        this.timer?.unsubscribe();
                    } catch (e) {

                    }
                }
            });

        }
    }
    protected hide(): void {
        this.visible=false;
    }
    @Input() pipelineRouterDecisionCode?: string;
}