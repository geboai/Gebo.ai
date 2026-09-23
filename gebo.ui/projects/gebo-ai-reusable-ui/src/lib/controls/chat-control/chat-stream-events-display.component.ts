import { Component, Input, OnChanges, SimpleChanges } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { IGeboChatMessage } from "../../services/gebo-chat-message";
import { GResponseDocumentRef } from '@Gebo.ai/brain';
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
    // A gather cycle or a fast agent hand-off can emit several notifications within a
    // few milliseconds, and the ticker shows one at a time. Without a floor on how long
    // each is shown, the latest simply overwrites the previous before it can be read -
    // measured at zero milliseconds on screen for the search agent's "found N document(s)"
    // beat, which the controller's next-cycle notification replaced instantly. These
    // hold each notification for a minimum time and queue the rest, so every beat is
    // legible; when notifications arrive slower than the dwell the queue stays empty and
    // there is no added latency.
    private static readonly MIN_DWELL_MS = 850;
    private static readonly MAX_QUEUE = 15;
    private notificationQueue: ChatNotificationContent[] = [];
    private dwelling = false;
    constructor(private messageService: GeboAIRootNotificationService) {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["streaming"] && !this.streaming) {
            // The run ended: drop anything still queued so it does not bleed into the next.
            this.clearEventsDisplay();
        }
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
        this.notificationQueue=[];
        this.dwelling=false;
        this.clearNotifiationTimer();
    }
    private enqueueNotification(notification?: ChatNotificationContent): void {
        if (!notification) {
            return;
        }
        // A notification takes over the ticker from the input/routing status.
        this.inputProcessingEvent=undefined;
        this.notifiedPipelineRouting=undefined;
        // Shed the oldest still-pending item if a burst backs the queue up, so the ticker
        // can never fall arbitrarily far behind the live stream.
        if (this.notificationQueue.length>=GeboAIChatStreamEventsDisplayComponent.MAX_QUEUE) {
            this.notificationQueue.shift();
        }
        this.notificationQueue.push(notification);
        if (!this.dwelling) {
            this.showNextNotification();
        }
    }
    private showNextNotification(): void {
        const next=this.notificationQueue.shift();
        if (!next) {
            // Nothing queued: stop dwelling and leave the last notification on screen, so a
            // later arrival appears immediately rather than after a needless delay.
            this.dwelling=false;
            return;
        }
        this.dwelling=true;
        this.currentNotification=next;
        this.clearNotifiationTimer();
        this.timer=timer(GeboAIChatStreamEventsDisplayComponent.MIN_DWELL_MS).subscribe({
            next:()=>{
                if (this.notificationQueue.length>0) {
                    this.showNextNotification();
                } else {
                    this.dwelling=false;
                }
            }
        });
    }
    public clearUI():void {
        this.clearEventsDisplay();
    }
    public onMessage(msg?: IGeboChatMessage) {
        this.currentChatMessage=msg;
        if (msg?.contentObjectType) {
            switch (msg.contentObjectType) {
                case "ChatNotificationContent": {
                    this.enqueueNotification(msg.content);
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