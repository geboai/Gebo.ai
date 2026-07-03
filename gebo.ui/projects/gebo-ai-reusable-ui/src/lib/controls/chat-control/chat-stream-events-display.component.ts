import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { IGeboChatMessage } from "../../services/gebo-chat-message";
import { GResponseDocumentRef } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { GeboAIRootNotificationService } from "../../notifications/root-notification.service";
import { PipelineRoutingOption } from "./pipeline-routing-option";
import { Subscription, timer } from "rxjs";
interface ChatNotificationContent {
	code:string;
	message:string;
	icon?:string;
	duration?:number;
	notificationType:"INFO"|"DEBUG"|"ERROR";
}
@Component({
    selector: "gebo-ai-chat-stream-events-display",
    templateUrl: "chat-stream-events-display.component.html",
    styleUrl:"chat-stream-events-display.component.scss",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIChatStreamEventsDisplayComponent"),
            multi: false
        }
    ]
})
export class GeboAIChatStreamEventsDisplayComponent implements OnChanges{
    @Input() streaming: boolean = false;
    protected currentChatMessage?: IGeboChatMessage;
    @Input() routingChoiceSelector?: string;
    @Input() actualPipelineRoutingOption?:PipelineRoutingOption;
    protected currentNotification?:ChatNotificationContent;
    protected inputProcessingEvent?:{document:GResponseDocumentRef};
    protected notifiedPipelineRouting?:PipelineRoutingOption;
    private timer?: Subscription;
    constructor(private messageService: GeboAIRootNotificationService) {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (this.actualPipelineRoutingOption && changes["actualPipelineRoutingOption"]) {
            this.clearEventsDisplay();
            this.notifiedPipelineRouting=this.actualPipelineRoutingOption;
        }
    }
    protected get inEventsLoop(): boolean {
        return this.streaming;
    }

    private clearNotifiationTimer():void {
        if (this.timer) {
            try {
                this.timer.unsubscribe();
            }catch(e) {}
        }
    }
    private clearEventsDisplay(): void {
        this.currentNotification=undefined;
        this.inputProcessingEvent=undefined;
        this.notifiedPipelineRouting=undefined;
        this.clearNotifiationTimer();
    }
    private startNotificationTimer(duration: number) {
        this.timer=timer(duration).subscribe({
            next:()=>{
                this.currentNotification=undefined;    
            }
        })
    }
    public clearUI():void {
        this.clearEventsDisplay();
    }
    public onMessage(msg?: IGeboChatMessage) {
        this.currentChatMessage=msg;
        if (msg?.contentObjectType) {
            switch (msg.contentObjectType) {
                case "ChatNotificationContent": {
                    this.clearNotifiationTimer();
                    this.clearEventsDisplay();
                    this.currentNotification= msg.content;
                    if (this.currentNotification?.duration) {
                        //this.startNotificationTimer(this.currentNotification?.duration);
                    }
                } break;
                case "GInputProcessingEvent": {
                    this.clearEventsDisplay();
                    this.inputProcessingEvent=msg.content;
                    
                };break;
                case "GUserMessage": {
                    this.clearEventsDisplay();
                    const message: ToastMessageOptions = {
                        summary: msg.content?.summary,
                        detail: msg.content?.detail,
                        severity: msg.content?.severity
                    };
                    this.messageService.addMessage("GeboAIDeepSearchModule", "GeboAIDeepSearchComponent", message);
                    //this.errorOccurredEvent.emit(msg.content);
                } break;
                case "GeboChatResponse": {
                    this.clearEventsDisplay();

                } break;
            }
        }
    }
    
}