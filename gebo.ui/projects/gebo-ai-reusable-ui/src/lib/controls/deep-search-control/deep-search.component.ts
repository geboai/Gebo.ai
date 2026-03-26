import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, ValidatorFn, Validators } from "@angular/forms";
import { DeepSearchDataSourceDocumentResult, DeepSearchDataSourceResponse, DeepSearchDocumentAnalisysResultStep, DeepSearchRequest, DeepSearchResponse, DeepSearchUISettings, GBaseObject, GeboChatControllerService, GeboChatRequest, GeboChatResponse, GeboDeepSearchControllerService, GeboRagChatControllerService, GKnowledgeBase, GResponseDocumentRef, GUserChatInfo, UserKnowledgeBaseBrowsingControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIStreamDeepSearchService } from "./stream-deep-search.service";
import { IGeboChatMessage } from "../../services/gebo-chat-message";


import { forkJoin, Observable, of } from "rxjs";
import { GeboAIRootNotificationService } from "../../notifications/root-notification.service";
import { ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
// Validators and interfaces moved to GeboAIDeepSearchSoucesChoiceComponent
@Component({
    selector: "gebo-ai-deep-search-component",
    templateUrl: "deep-search.component.html",
    styleUrls: ["./deep-search.component.scss"],
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIDeepSearchModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIDeepSearchComponent"), multi: false }
    ]
})
export class GeboAIDeepSearchComponent implements OnInit, OnChanges {
    @Input() currentDeepSearchRequest?: DeepSearchRequest;
    @Input() currentChatRequest?: GeboChatRequest;
    @Input() mode: "full-ui" | "progress-only" = "full-ui";
    @Input() nextRequestMode: "standard-chat" | "deep-search" = "standard-chat";
    @Input() chatProfileCode?: string;
    protected deepSearchProcessType?: "standalone" | "chat-context";
    @Output() deepSearchResponseReceived: EventEmitter<DeepSearchResponse> = new EventEmitter();
    @Output() deepSearchChatResponseReceived: EventEmitter<GeboChatResponse> = new EventEmitter();
    @Output() skipDeepSearchEvent: EventEmitter<boolean> = new EventEmitter();
    @Output() errorOccurredEvent: EventEmitter<any> = new EventEmitter();
    protected streamingResponse: boolean = false;
    protected selectedDeepSearchDataSources?: string[];
    protected selectedKnowledgeBases?: string[];


    protected get loading(): boolean {
        return this.streamingResponse === true;
    }
    protected formGroup: FormGroup = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        query: new FormControl(),
        knowledgeBases: new FormControl(),
        deepSearchDataSources: new FormControl()
    });


    protected deepSearchResponse?: DeepSearchResponse;
    protected chatResponse?: GeboChatResponse;
    protected analisysStep?:DeepSearchDocumentAnalisysResultStep;
    protected deepSearchDataSourceDocumentResult?: DeepSearchDataSourceDocumentResult;
    protected deepSearchDataSourceResponse?: DeepSearchDataSourceResponse;
    protected deepSearchNotification?:{content?:string};
    protected completionPercent: number = 0;

    protected programmaticStreaming:boolean=false;
    protected deepSearchStarting:boolean=false;
    constructor(private deepSearchStreamService: GeboAIStreamDeepSearchService,
        private messageService: GeboAIRootNotificationService) {
    }
    ngOnInit(): void {

    }
    private clearEventsDisplay(): void {
        this.deepSearchResponse = undefined;
        this.deepSearchDataSourceDocumentResult = undefined;
        this.deepSearchDataSourceResponse = undefined;
        this.analisysStep = undefined;
        this.deepSearchResponse = undefined;
        this.deepSearchNotification=undefined;
        this.chatResponse=undefined;
    }
    protected get inEventsLoop(): boolean {
        return !this.deepSearchResponse && !this.chatResponse && this.streamingResponse;
    }
    protected get isDisplayingDeepSearchProcess(): boolean {
        return !this.analisysStep || !this.deepSearchDataSourceDocumentResult || !this.deepSearchDataSourceResponse || !this.deepSearchNotification || !this.deepSearchResponse;
    }
    public switchToStreamingEventsLoop(streaming:boolean):void {
        this.clearEventsDisplay();
        this.completionPercent=0;
        this.programmaticStreaming=streaming;
        this.streamingResponse=streaming;
        this.deepSearchStarting=true;
    }
    public onMessage(msg: IGeboChatMessage | string) {
        this.deepSearchStarting=false;
        if (typeof msg === "string") {

        } else {
            if (msg.contentObjectType) {
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
                        this.deepSearchResponse = msg.content;
                        this.completionPercent = 100;
                        this.deepSearchResponseReceived.emit(this.deepSearchResponse);
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
                    }break;
                    case "DeepSearchDataSourceResponse": {
                        this.clearEventsDisplay();
                        this.deepSearchDataSourceResponse = msg.content;
                        if (msg.content?.processPercentage) {

                            this.completionPercent = Math.round(msg.content?.processPercentage);
                        }
                    } break;
                    case "DeepSearchNotification": {
                        this.clearEventsDisplay();
                        this.deepSearchNotification=msg.content;
                    }break;
                    case "GUserMessage": {
                        this.clearEventsDisplay();
                        const message: ToastMessageOptions = {
                            summary: msg.content?.summary,
                            detail: msg.content?.detail,
                            severity: msg.content?.severity
                        };
                        this.messageService.addMessage("GeboAIDeepSearchModule","GeboAIDeepSearchComponent",message);
                        //this.errorOccurredEvent.emit(msg.content);
                    } break;
                    case "GeboChatResponse": {
                        this.clearEventsDisplay();
                        this.chatResponse = msg.content;
                        this.deepSearchChatResponseReceived.emit(msg.content);
                    } break;
                }
            }

            if (msg.lastMessage === true) {
                this.streamingResponse = false;
                this.programmaticStreaming=false;
                this.skipDeepSearchEvent.emit(true);
            }
        }
    }
    public onError(err: any) {
        this.streamingResponse = false;
        this.errorOccurredEvent.emit(err);
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (changes["currentDeepSearchRequest"] && this.currentDeepSearchRequest) {
            this.formGroup.patchValue(this.currentDeepSearchRequest);

            this.deepSearchProcessType = "standalone";
            if (this.mode === "progress-only") {
                this.doStreamDeepSearch();
            }
        }
        if (changes["currentChatRequest"] && this.currentChatRequest) {
            this.currentChatRequest.deepSearchDataSources = this.selectedDeepSearchDataSources;
            this.currentChatRequest.choosedKnowledgeBases = this.selectedKnowledgeBases;
            this.doStreamDeepSearchInChat();
        }
    }
    protected onSourcesChoosed(): void {
        if (!this.currentChatRequest) {
            this.formGroup.controls["deepSearchDataSources"].setValue(this.selectedDeepSearchDataSources);
            this.formGroup.controls["knowledgeBases"].setValue(this.selectedKnowledgeBases);
        }
    }

    protected skipDeepSearchOnDataSourceChoice(): void {
        this.skipDeepSearchEvent.emit(true);
    }
    protected doStreamDeepSearch(): void {
        const data: DeepSearchRequest = this.formGroup.value;
        this.completionPercent = 0;
        this.streamingResponse = true;
        this.deepSearchStreamService.streamDeepSearch(data, (msg: IGeboChatMessage | string) => {
            this.onMessage(msg);
        }, (err) => {
            this.onError(err);
        });
    }
    protected doStreamDeepSearchInChat(): void {
        if (this.currentChatRequest) {
            this.streamingResponse = true;
            this.deepSearchStreamService.streamDeepSearchInChat(this.currentChatRequest, (msg: IGeboChatMessage | string) => {
                this.onMessage(msg);
            }, (err) => {
                this.onError(err);
            });
        }
    }

}