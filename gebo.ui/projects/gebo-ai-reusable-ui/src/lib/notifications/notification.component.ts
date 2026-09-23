import { Component, Inject, Injectable, Input, OnChanges, SimpleChanges } from "@angular/core";
import { GeboAIRootNotificationService } from "./root-notification.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../controls/field-host-component-iface/field-host-component-iface";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { NotificationLayerEnum } from "./notification-layer";
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
    @Input() layer:NotificationLayerEnum="GLOBAL";
    private oldMessages: (GUserMessage | ToastMessageOptions)[]=[];
    constructor(
        private service: GeboAIRootNotificationService,
        @Inject(GEBO_AI_MODULE) private moduleId: string,
        @Inject(GEBO_AI_FIELD_HOST) private host: GeboAIFieldHost
        ) {
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["messages"] && this.messages && this.messages.length) {
            let filteredMsgs:(GUserMessage | ToastMessageOptions)[]=this.messages;
            if (this.messages.length===this.oldMessages.length) {
                filteredMsgs=[];
                this.messages.forEach(x=>{
                    const hasCorresponding=this.oldMessages.filter(y=>x.id===y.id || (x.detail===y.detail && x.summary===y.summary));
                    if (!hasCorresponding || hasCorresponding.length===0) {
                        filteredMsgs.push(x);
                    }
                });
            }
            this.service.addMessages(this.moduleId, this.host.getEntityName(),filteredMsgs as GUserMessage[],this.layer);
            this.oldMessages=this.messages;
        }
    }
}