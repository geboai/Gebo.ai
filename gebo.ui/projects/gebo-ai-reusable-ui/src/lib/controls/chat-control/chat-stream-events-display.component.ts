import { Component, Input } from "@angular/core";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
import { IGeboChatMessage } from "../../services/gebo-chat-message";
import { DeepSearchDataSourceDocumentResult, DeepSearchDataSourceResponse, DeepSearchDocumentAnalisysResultStep } from "@Gebo.ai/gebo-ai-rest-api";
import { ToastMessageOptions } from "primeng/api";
import { GeboAIRootNotificationService } from "../../notifications/root-notification.service";

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
export class GeboAIChatStreamEventsDisplayComponent {
    @Input() streaming: boolean = false;
    protected currentChatMessage?: IGeboChatMessage;
    @Input() routingChoiceSelector?: string;
    protected analisysStep?: DeepSearchDocumentAnalisysResultStep;
    protected deepSearchDataSourceDocumentResult?: DeepSearchDataSourceDocumentResult;
    protected deepSearchDataSourceResponse?: DeepSearchDataSourceResponse;
    protected deepSearchNotification?: { content?: string };
    protected completionPercent: number = 0;
    constructor(private messageService: GeboAIRootNotificationService) {

    }
    protected get inEventsLoop(): boolean {
        return this.streaming;
    }
    protected get isDisplayingDeepSearchProcess(): boolean {
        return !this.analisysStep || !this.deepSearchDataSourceDocumentResult || !this.deepSearchDataSourceResponse || !this.deepSearchNotification;
    }
    private clearEventsDisplay(): void {

        this.deepSearchDataSourceDocumentResult = undefined;
        this.deepSearchDataSourceResponse = undefined;
        this.analisysStep = undefined;
        this.deepSearchNotification = undefined;

    }
    public onMessage(msg?: IGeboChatMessage) {
        if (msg?.contentObjectType) {
            switch (msg.contentObjectType) {
                case "DeepSearchStep": {
                    this.clearEventsDisplay();
                    this.analisysStep = msg.content;
                    if (msg.content?.processPercentage) {

                        this.completionPercent = Math.round(msg.content?.processPercentage);
                    }
                } break;
                case "DeepSearchResponse": {
                    this.clearEventsDisplay();
                    this.completionPercent = 100;
                } break;
                case "DeepSearchDataSourceDocumentResult": {
                    this.clearEventsDisplay();
                    this.deepSearchDataSourceDocumentResult = msg.content;
                    if (msg.content?.processPercentage) {

                        this.completionPercent = Math.round(msg.content?.processPercentage);
                    }
                } break;
                case "DeepSearchDocumentAnalisysResultStep": {
                    this.analisysStep = msg.content;
                    if (msg.content?.processPercentage) {

                        this.completionPercent = Math.round(msg.content?.processPercentage);
                    }
                } break;
                case "DeepSearchDataSourceResponse": {
                    this.clearEventsDisplay();
                    this.deepSearchDataSourceResponse = msg.content;
                    if (msg.content?.processPercentage) {

                        this.completionPercent = Math.round(msg.content?.processPercentage);
                    }
                } break;
                case "DeepSearchNotification": {
                    this.clearEventsDisplay();
                    this.deepSearchNotification = msg.content;
                } break;
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