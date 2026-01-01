import { Component, Inject, Injectable, Input, OnChanges, SimpleChanges } from "@angular/core";
import { GeboAIRootNotificationService } from "./root-notification.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../controls/field-host-component-iface/field-host-component-iface";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
/*************************
 * This component will forward to the display messages area messages
 */
@Component({
    selector: "gebo-ai-notifications",
    template: "",
    standalone: false
})
export class GeboAINotificationComponent implements OnChanges {
    @Input() messages: (GUserMessage | ToastMessageOptions)[] = [];
    constructor(
        private service: GeboAIRootNotificationService,
        @Inject(GEBO_AI_MODULE) private moduleId: string,
        @Inject(GEBO_AI_FIELD_HOST) private host: GeboAIFieldHost) {
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["messages"] && this.messages) {
            this.service.addMessages(this.moduleId, this.host.getEntityName(), this.messages as GUserMessage[]);
        }
    }
}