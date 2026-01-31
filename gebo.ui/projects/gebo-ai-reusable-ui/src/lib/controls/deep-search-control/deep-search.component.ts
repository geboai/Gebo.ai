import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from "@angular/core";
import { FormControl, FormGroup, ValidatorFn, Validators } from "@angular/forms";
import { DeepSearchDataSourceDocumentResult, DeepSearchDataSourceResponse, DeepSearchRequest, DeepSearchResponse, DeepSearchUISettings, GBaseObject, GeboChatControllerService, GeboChatRequest, GeboChatResponse, GeboDeepSearchControllerService, GeboRagChatControllerService, GKnowledgeBase, GResponseDocumentRef, GUserChatInfo, UserKnowledgeBaseBrowsingControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIStreamDeepSearchService } from "./stream-deep-search.service";
import { IGeboChatMessage } from "../../services/base-streaming.service";


import { forkJoin, Observable, of } from "rxjs";
import { GeboAIRootNotificationService } from "../../notifications/root-notification.service";
import { ToastMessageOptions } from "primeng/api";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "../field-host-component-iface/field-host-component-iface";
interface IChooseSources {
    deepSearchDataSources?: string[];
    knowledgeBases?: string[];
}
const atLeastAKnowledgeBaseOrSystemValidator: ValidatorFn = (ctrl) => {
    const fg: FormGroup = ctrl as FormGroup;
    const value: IChooseSources = fg.value;
    const hasKBS: boolean = (value?.knowledgeBases ? true : false) && ((value?.knowledgeBases?.length && value?.knowledgeBases?.length > 0) ? true : false);
    const hasSRC: boolean = (value?.deepSearchDataSources ? true : false) && ((value?.deepSearchDataSources?.length && value?.deepSearchDataSources?.length > 0) ? true : false);
    let out: any | null = null;
    if (!(hasKBS || hasSRC)) {
        out = {
            noSourceSelected: "noSrc"
        };
    }
    return out;
}
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
    protected loadingRelatedBackend: boolean = false;
    protected choosableDataSources: GBaseObject[] = [];
    protected choosableKnowledgeBases: GBaseObject[] = [];


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
    protected chooseDeepSearchDataSourcesFormGroup: FormGroup = new FormGroup({
        deepSearchDataSources: new FormControl(),
        knowledgeBases: new FormControl()
    });

    protected deepSearchResponse?: DeepSearchResponse;
    protected chatResponse?: GeboChatResponse;
    protected analisysStep?: {
        documentRef?: GResponseDocumentRef,
        analisysPortion?: string,
        completionPercent?: number
    };
    protected deepSearchDataSourceDocumentResult?: DeepSearchDataSourceDocumentResult;
    protected deepSearchDataSourceResponse?: DeepSearchDataSourceResponse;
    protected deepSearchNotification?:{content?:string};
    protected completionPercent: number = 0;
    protected showChooseDataSourceDialog: boolean = false;
    protected deepSearchUISettings:DeepSearchUISettings= {
        deepSearchUIAllowChooseSources:false,
        externalSourcesEnabled:false
    }
    protected programmaticStreaming:boolean=false;
    constructor(private deepSearchStreamService: GeboAIStreamDeepSearchService,
        private deepSearchControllerService: GeboDeepSearchControllerService,
        private knowledgeBaseDataSourcesService: UserKnowledgeBaseBrowsingControllerService,
        private ragChatService: GeboRagChatControllerService,
        private chatService: GeboChatControllerService,
        private messageService: GeboAIRootNotificationService) {
        this.chooseDeepSearchDataSourcesFormGroup.setValidators(atLeastAKnowledgeBaseOrSystemValidator);
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
    }
    protected get inEventsLoop(): boolean {
        return !this.deepSearchResponse && !this.chatResponse && this.streamingResponse;
    }
    protected get isDisplayingDeepSearchProcess(): boolean {
        return !this.analisysStep || !this.deepSearchDataSourceDocumentResult || !this.deepSearchDataSourceResponse;
    }
    public switchToStreamingEventsLoop(streaming:boolean):void {
        this.clearEventsDisplay();
        this.programmaticStreaming=streaming;
        this.streamingResponse=streaming;
    }
    public onMessage(msg: IGeboChatMessage | string) {
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
                        this.errorOccurredEvent.emit(msg.content);
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
        if (changes["nextRequestMode"] && this.nextRequestMode==="deep-search" && !this.programmaticStreaming) {
            this.chooseDataSources();
        }
        if (changes["currentDeepSearchRequest"] && this.currentDeepSearchRequest) {
            this.formGroup.patchValue(this.currentDeepSearchRequest);

            this.deepSearchProcessType = "standalone";
            if (this.mode === "progress-only") {
                this.doStreamDeepSearch();
            }
        }
        if (changes["currentChatRequest"] && this.currentChatRequest) {
            const choosedDataSourcesObject: IChooseSources = this.chooseDeepSearchDataSourcesFormGroup.value;
            const deepSearchDataSources = choosedDataSourcesObject?.deepSearchDataSources;
            const knowledgeBases = choosedDataSourcesObject?.knowledgeBases;
            this.currentChatRequest.deepSearchDataSources = deepSearchDataSources;
            this.currentChatRequest.choosedKnowledgeBases = knowledgeBases;
            this.doStreamDeepSearchInChat();
        }
    }
    private chooseSources(): Observable<[GBaseObject[], GBaseObject[],DeepSearchUISettings]> {
        const kbObservable: Observable<GBaseObject[]> = (this.chatProfileCode) ? this.ragChatService.getVisibleKnowledgeBasesByProfileCode(this.chatProfileCode) : this.chatService.getVisibleKnowledgeBases();
        return forkJoin([this.deepSearchControllerService.getDeepSearchDataSources(), kbObservable,this.deepSearchControllerService.getDeepSearchUISettings()]);
    }
    private chooseDataSources(): void {
        this.chooseDeepSearchDataSourcesFormGroup.reset();
        this.loadingRelatedBackend = true;
        this.chooseSources().subscribe({
            next: (dsList) => {
                this.choosableDataSources = dsList[0];
                this.choosableKnowledgeBases = dsList[1];
                if (dsList[2]) {
                    this.deepSearchUISettings=dsList[2];
                }
                const defaultSelection: IChooseSources = {
                    deepSearchDataSources: this.choosableDataSources ? this.choosableDataSources.map(x => x.code) as string[] : [],
                    knowledgeBases: this.choosableKnowledgeBases ? this.choosableKnowledgeBases.map(x => x.code) as string[] : []
                };
                if (this.deepSearchUISettings.externalSourcesEnabled!==true) {
                    defaultSelection.deepSearchDataSources=[];
                }
                this.chooseDeepSearchDataSourcesFormGroup.patchValue(defaultSelection);
                this.loadingRelatedBackend = false;
                let totalChoices: number = 0;
                if (this.choosableDataSources?.length) {
                    totalChoices += this.choosableDataSources.length;
                }
                if (this.choosableKnowledgeBases?.length) {
                    totalChoices += this.choosableKnowledgeBases.length;
                }
                //Choice UI has to be viewed only where options for data sources are more than one
                if (totalChoices > 1 && this.deepSearchUISettings.deepSearchUIAllowChooseSources===true) {
                    this.showChooseDataSourceDialog = true;
                }
            },
            complete: () => {
                this.loadingRelatedBackend = false;
            }
        });
    }

    protected doChoosedSources(): void {
        this.showChooseDataSourceDialog = false;
        const choosedDataSourcesObject: IChooseSources = this.chooseDeepSearchDataSourcesFormGroup.value;
        const deepSearchDataSources = choosedDataSourcesObject?.deepSearchDataSources;
        const knowledgeBases = choosedDataSourcesObject?.knowledgeBases;
        if (this.currentChatRequest) {

            this.currentChatRequest.deepSearchDataSources = deepSearchDataSources;
            this.currentChatRequest.choosedKnowledgeBases = knowledgeBases;

        } else {
            this.formGroup.controls["deepSearchDataSources"].setValue(deepSearchDataSources);
            this.formGroup.controls["knowledgeBases"].setValue(knowledgeBases);

        }
    }
    protected skipDeepSearchOnDataSourceChoice(): void {
        this.showChooseDataSourceDialog = false;
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