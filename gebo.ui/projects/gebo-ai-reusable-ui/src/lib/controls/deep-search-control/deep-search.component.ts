import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { DeepSearchDocumentAnalisysResultStep, DeepSearchRequest, DeepSearchResponse, GeboChatRequest, GeboChatResponse, GResponseDocumentRef } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIStreamDeepSearchService } from "./stream-deep-search.service";
import { IGeboChatMessage } from "../../services/base-streaming.service";
import { MessageService, ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
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
    protected deepSearchProcessType?: "standalone" | "chat-context";
    @Output() deepSearchResponseReceived: EventEmitter<DeepSearchResponse> = new EventEmitter();
    @Output() deepSearchChatResponseReceived: EventEmitter<GeboChatResponse> = new EventEmitter();
    @Output() errorOccurredEvent:EventEmitter<any>=new EventEmitter();
    protected streamingResponse: boolean = false;

    protected get loading(): boolean {
        return this.streamingResponse === true;
    }
    protected formGroup: FormGroup = new FormGroup({
        code: new FormControl(),
        description: new FormControl(),
        query: new FormControl(),
        knowledgeBases: new FormControl()

    });
    protected deepSearchResponse?: DeepSearchResponse;
    protected chatResponse?: GeboChatResponse;
    protected analisysStep?: {
        documentRef?: GResponseDocumentRef,
        analisysPortion?: string,
        completionPercent?: number
    };
    protected completionPercent: number = 0;
    constructor(private deepSearchStreamService: GeboAIStreamDeepSearchService, private messageService: MessageService) {

    }
    ngOnInit(): void {

    }
    private onMessage(msg: IGeboChatMessage | string) {
        if (typeof msg === "string") {

        } else {
            if (msg.contentObjectType) {
                switch (msg.contentObjectType) {
                    case "DeepSearchStep": {
                        this.deepSearchResponse = undefined;
                        this.analisysStep = msg.content;
                        if (msg.content?.completionPercent) {

                            this.completionPercent = Math.round(msg.content?.completionPercent);
                        }
                    } break;
                    case "DeepSearchResponse": {
                        this.analisysStep = undefined;
                        this.deepSearchResponse = msg.content;
                        this.completionPercent = 100;
                        this.deepSearchResponseReceived.emit(this.deepSearchResponse);
                    } break;
                    case "GUserMessage": {
                        this.analisysStep = undefined;
                        const message: ToastMessageOptions = {
                            summary: msg.content?.summary,
                            detail: msg.content?.detail,
                            severity: msg.content?.severity
                        };
                        this.messageService.add(message);
                        this.errorOccurredEvent.emit(msg.content);
                    } break;
                    case "GeboChatResponse": {
                        this.analisysStep = undefined;
                        this.chatResponse = msg.content;
                        this.deepSearchChatResponseReceived.emit(msg.content);
                    } break;
                }
            }

            if (msg.lastMessage === true) {
                this.streamingResponse = false;
            }
        }
    }
    onError(err: any) {
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
            this.doStreamDeepSearchInChat();
        }
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