/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




/**
 * AI generated comments
 * 
 * This module implements a reusable chat component for the Gebo.ai system. It provides a flexible
 * chat interface that can work with both regular and RAG (Retrieval Augmented Generation) enabled
 * chat models. The component supports various features like streaming responses, speech-to-text,
 * text-to-speech, and chat history management.
 */

import { HttpClient } from "@angular/common/http";
import { Component, EventEmitter, forwardRef, HostListener, Inject, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { BASE_PATH, CalledFunction, GBaseChatModelChoice, GeboChatControllerService, GeboChatPipelinesControllerService, GeboChatRequest, GeboChatResponse, GeboChatUserInfo, GeboRagChatControllerService, GeboUserChatsControllerService, GResponseDocumentRef, GUserChatInfo, GUserMessage, LLMGeneratedResource, ModelProviderCapabilities, PipelineChatMenu, SpeechRequest, TranscriptResponse } from "@Gebo.ai/gebo-ai-rest-api";
import { MermaidAPI } from "ngx-markdown";
import { ConfirmationService, ToastMessageOptions } from "primeng/api";
import { forkJoin, Observable, of } from "rxjs";
import { v4 as uuidv4 } from 'uuid';
import { AgenticChatRequestBody, ReactiveRagChatService } from "./reactive-chat.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";

import { IGeboChatMessage } from "../../services/gebo-chat-message";
import { GeboAIRootNotificationService } from "../../notifications/root-notification.service";
import { ExtendedConfirmation, GeboAITranslationService } from "../field-translation-container/gebo-translation.service";
import { GeboAIDeepSearchComponent } from "../deep-search-control/deep-search.component";
import { GeboAIChatInputShellComponent } from "./chat-input-shell.component";
import { PipelineRoutingOption } from "./pipeline-routing-option";
const loading_vocal_answer: ToastMessageOptions = { id: "LOADING_VOCAL_ANSWER", severity: "info", summary: "Loading vocal answer" };
const loading_vocal_answer_received: ToastMessageOptions = { id: "LOADING_VOCAL_ANSWER_RECEIVED", severity: "info", summary: "Vocal answer received" };
const your_speech_is_uploading: ToastMessageOptions = { id: "YOUR_SPEECH_IS_UPLOADING", severity: "info", summary: "Your speech is uploading" };
const chat_history_loaded: ToastMessageOptions = { id: "CHAT_HISTORY_LOADED", summary: "Chat history loaded", detail: "Chat history loaded successfully", severity: "success" };
const clean_chat_loaded: ToastMessageOptions = { id: "NEW_CHAT_LOADED", summary: "New chat loaded", detail: "New chat loaded successfully", severity: "success" };
/**
 * Interface representing a single chat interaction between the user and the AI,
 * containing both the request and potential response.
 */
interface GeboChatInteraction {
    request: GeboChatRequest;
    response?: GeboChatTemplatedResponse;
    loading?: boolean;
    pipelineRouterDecisionCode?: string;
};

/**
 * Interface representing the structured response from the Gebo chat system,
 * including the model information, response content, and any referenced documents.
 */
interface GeboChatTemplatedResponse {
    userChatContextCode?: string;
    usedChatModelCode?: string;
    usedChatModelProvider?: string;
    queryResponse?: any;
    query?: string;
    backendMessages?: Array<GUserMessage>;
    documentsRef?: Array<GResponseDocumentRef>;
    calledFunctions?: Array<CalledFunction>;
    generatedResources?: LLMGeneratedResource[];
};
const moduleId: string = "GeboAIChatControlModule";
const entityId: string = "GeboAIReusableChatComponent";
const scrollStart: number = 350;
/**
 * Component that provides a reusable chat interface for Gebo.ai models.
 * Supports both standard chat and RAG-enabled conversations, with features like
 * streaming responses, speech recognition, and voice output. Manages chat history
 * and document references.
 */
@Component({
    selector: "gebo-ai-reusable-chat-component",
    templateUrl: "gebo-ai-reusable-chat.component.html",
    styleUrls: ["gebo-ai-reusable-chat.component.scss"],
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIChatControlModule", multi: false },
        {
            provide: GEBO_AI_FIELD_HOST, useExisting: forwardRef(() => GeboAIReusableChatComponent),
            multi: false
        }
    ],
    standalone: false
})
export class GeboAIReusableChatComponent implements OnInit, OnChanges, GeboAIFieldHost {




    /**
     * Stores the capabilities of the current model provider
     */
    capabilities?: ModelProviderCapabilities;

    /**
     * List of knowledge base codes available for this chat
     */
    public knowledgeBaseCodes: string[] = [];
    protected userChatContextCode: string | undefined;
    protected currentPipelineRouterDecisionCode?: string;
    protected chatPipelinesMenu: PipelineChatMenu[] = [];

    /**
     * Determines if any loading operation is currently in progress
     */
    public get loading(): boolean {
        return this.loadingChatHistory === true || this.loadingChatResponse === true || this.loadingModelMetaInfo === true || this.waitingForTranscript === true;
    }

    /**
     * Flag indicating if chat loading
     */
    public loadingSearchResult: boolean = false;

    /**
     * Flag indicating if chat history is being loaded
     */
    private loadingChatHistory: boolean = false;

    /**
     * Flag indicating if a chat response is being loaded
     */
    protected loadingChatResponse: boolean = false;

    /**
     * Flag indicating if model metadata is being loaded
     */
    private loadingModelMetaInfo: boolean = false;

    /**
     * Flag indicating if waiting for speech-to-text transcript
     */
    private waitingForTranscript: boolean = false;

    /**
     * Flag indicating if waiting for audio content to load
     */
    private waitingForAudiocontent: boolean = false;

    /**
     * Flag indicating if a chat response is currently streaming
     */
    public chatStreaming: boolean = false;

    /**
     * Timestamp of the last update received during chat streaming
     */
    private chatStreamingUpdatedTime?: number;

    /**
     * Flag indicating if an error occurred during chat streaming
     */
    private chatStreamingErrorOccurred: boolean = false;
    protected openSelectDocumentsWindow: boolean = false;
    protected openedUploadDocumentsWindow: boolean = false;
    /**
     * Determines if the chat streaming might be failing based on timeout
     */
    public get isChatStreamingProbabilyFailing(): boolean {
        if (this.chatStreamingErrorOccurred === true) return true;
        if (this.chatStreaming !== true || this.chatStreamingUpdatedTime === undefined) return false;
        const actualTime: number = new Date().getTime();
        const chatUpdateTime: number = this.chatStreamingUpdatedTime;
        return actualTime >= chatUpdateTime + this.streamingTimeout;
    }

    /**
     * Array of chat interactions (request-response pairs)
     */
    public interactions: GeboChatInteraction[] = [];

    /**
     * Toast messages from the last interaction
     */
    public lastInteractionMessages: ToastMessageOptions[] = [];

    /**
     * Current audio file being played
     */
    public currentAudioTrack?: Blob;

    /**
     * Time in milliseconds after which streaming is considered failed
     */
    @Input() streamingTimeout: number = 30000;

    /**
     * Flag to use only REST API calls (no WebSockets)
     */
    @Input() useRestOnly: boolean = false;

    /**
     * Information about the current chat
     */
    @Input() chatInfo?: GUserChatInfo;

    /**
     * Title displayed for the chat
     */
    @Input() title?: string = "Chat";

    /**
     * Subtitle displayed for the chat
     */
    @Input() subtitle?: string = "";

    /**
     * Name of the model being used
     */
    @Input() modelName: string = "Chat model";

    /**
     * Flag indicating if RAG system should be used
     */
    @Input() ragsystem: boolean = false;

    /**
     * Event emitted when a cancel action is triggered
     */
    @Output() cancelAction: EventEmitter<boolean> = new EventEmitter();

    /**
     * Event emitted when a chat is deleted
     */
    @Output() deleteChatAction: EventEmitter<GUserChatInfo> = new EventEmitter();

    /**
     * Event emitted when a new chat is added
     */
    @Output() addedChatAction: EventEmitter<GUserChatInfo> = new EventEmitter();
    @Output() updatedChatAction: EventEmitter<GUserChatInfo> = new EventEmitter();
    @ViewChild(GeboAIChatInputShellComponent) chatInputShell!: GeboAIChatInputShellComponent;
    /**
     * Configuration options for Mermaid diagrams
     */
    public options: MermaidAPI.MermaidConfig = {
        fontFamily: '"trebuchet ms", verdana, arial, sans-serif',
        logLevel: "info",
        theme: "dark"
    };

    /**
     * Form group for chat input and configuration
     */
    formGroup: FormGroup = new FormGroup({
        chatProfileCode: new FormControl(),
        userChatContextCode: new FormControl(),
        chatModelCode: new FormControl(),
        forcedRequestDocuments: new FormControl(),
        query: new FormControl(),
        userUploadedContents: new FormControl(),
        deepSearchDataSources: new FormControl()
    });
    protected currentPipelineRoutingOption?: PipelineRoutingOption;
    /**
     * Form group for chat information
     */
    chatInfoFormGroup: FormGroup = new FormGroup(
        {
            code: new FormControl(),
            description: new FormControl(),
            username: new FormControl(),
            chatProfileCode: new FormControl(),
            chatModelCode: new FormControl(),
            ragChat: new FormControl(),
            chatCreationDateTime: new FormControl()
        }
    );
    /**
     * Scroll buttons
     */
    public showTopButton: boolean = false;
    public showBottomButton: boolean = false;
    /**
     * Metadata about the current chat model
     */
    public modelMetaInfos?: GBaseChatModelChoice;

    /**
     * User info relevant to the chat
     */
    public chatUserInfos?: GeboChatUserInfo;

    /**
     * Flag to control visibility of user info overlay
     */
    public userInfoOverlayVisible: boolean = false;

    /**
     * Map tracking which documents are selected
     */
    private docSelectedMap: Map<string, boolean> = new Map();

    /**
     * Flag to control visibility of the description change dialog
     */
    protected changeDescriptionDialogOpened: boolean = false;

    /**
     * Constructor - injects all required services
     */

    public constructor(
        private geboUserChatsControllerService: GeboUserChatsControllerService,
        private confirmService: ConfirmationService,
        private chatService: GeboChatControllerService,
        private messageService: GeboAIRootNotificationService,
        private ragChatService: GeboRagChatControllerService,
        private reactiveChatService: ReactiveRagChatService,
        private geboAiTranslationService: GeboAITranslationService,
        private geboChatPipelineService: GeboChatPipelinesControllerService,
        private userChatControllerService: GeboUserChatsControllerService,
        private httpClient: HttpClient,
        @Inject(BASE_PATH) private basePath: string) {
        this.formGroup.controls["userChatContextCode"].valueChanges.subscribe({
            next: (userChatContextCode: string | undefined) => {
                this.userChatContextCode = userChatContextCode;
            }
        });
    }

    @HostListener('window:scroll')
    onWindowScroll() {
        this.showTopButton = window.scrollY > scrollStart;
        this.showBottomButton = ((window.document.body.scrollHeight - window.innerHeight) - window.scrollY) > scrollStart && this.showTopButton;
    }
    public scrollToTop(): void {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    public scrollToBottom(): void {
        window.scrollTo(0, (window.document.body.scrollHeight - window.innerHeight));
    }

    public getEntityName(): string {
        return entityId;
    }

    /**
     * Checks if a document reference is chosen/selected
     * 
     * @param dr Document reference to check
     * @returns True if the document is selected
     */
    public isChoosed(dr: GResponseDocumentRef): boolean {
        if (!dr.documentCode) return false;
        return (dr.documentCode ? true : false) && this.docSelectedMap.get(dr.documentCode) === true;
    }

    /**
     * Adds a document to the selected documents list
     * 
     * @param dr Document reference to add
     */
    public addToChoosed(dr: GResponseDocumentRef): void {
        let actualChoosed: string[] = this.formGroup.controls["forcedRequestDocuments"].value;
        if (!actualChoosed) {
            actualChoosed = [];
        }
        if (dr.documentCode) {
            actualChoosed.push(dr.documentCode);
            this.formGroup.controls["forcedRequestDocuments"].setValue(actualChoosed);
        }
    }
    isDeepSearchResponse(item?: GeboChatResponse) {
        return ((item?.pipelineRouterDecisionCode === "DEEP_SEARCH_RESPONSE" || item?.pipelineRouterDecisionCode === "SHALLOW_SEARCH_RESPONSE") && item?.deepSearchRequestId) ? true : false;
    }
    getDeepSearchRequestId(item?: GeboChatResponse) {
        return item?.deepSearchRequestId;
    }
    /**
     * Handles changes to input properties
     * Loads model info and chat history when chatInfo changes
     */
    ngOnChanges(changes: SimpleChanges): void {
        if (this.chatInfo && changes["chatInfo"]) {
            this.formGroup.patchValue(this.chatInfo);
            this.chatInfoFormGroup.patchValue(this.chatInfo);
            let observables: [Observable<GBaseChatModelChoice>, Observable<GeboChatUserInfo>, Observable<PipelineChatMenu[]>] | null = null;

            if (this.chatInfo.chatProfileCode) {
                observables = [this.ragChatService.getChatProfileModelMetaInfos(this.chatInfo.chatProfileCode), this.ragChatService.getChatModelUserInfoByChatProfileCode(this.chatInfo.chatProfileCode), this.geboChatPipelineService.getDefaultPersonalPipelinesChatMenu(this.chatInfo.chatProfileCode)];
            } else
                if (this.chatInfo.chatModelCode) {
                    observables = [this.chatService.getChatModelMetaInfos(this.chatInfo.chatModelCode), this.chatService.getChatModelUserInfo(this.chatInfo.chatModelCode), of([])];
                }
            if (observables) {
                this.loadingModelMetaInfo = true;
                forkJoin(observables).subscribe({
                    next: (value) => {
                        this.modelMetaInfos = value[0];
                        this.knowledgeBaseCodes = value[1].knowledgeBases?.map(x => x.code).filter(y => y ? true : false) as string[];
                        this.chatUserInfos = value[1];
                        this.chatPipelinesMenu = value[2];
                    },
                    complete: () => {
                        this.loadingModelMetaInfo = false;
                    }
                });

            }
            if (this.chatInfo.chatModelCode) {
                this.chatService.getProviderCapabilities(this.chatInfo.chatModelCode).subscribe({
                    next: (capabilities) => {
                        this.capabilities = capabilities;
                    }

                });
            } else if (this.chatInfo.chatProfileCode && this.ragsystem === true) {
                this.ragChatService.getProfileProviderModelCapabilities(this.chatInfo.chatProfileCode).subscribe({
                    next: (capabilities) => {
                        this.capabilities = capabilities;
                    }

                });
            }
            this.loadChatHistory();

        }
    }

    /**
     * Loads chat history for the current chat
     */
    public loadChatHistory(): void {
        if (this.chatInfo?.code) {

            this.formGroup.controls["userChatContextCode"].setValue(this.chatInfo?.code);
            this.loadingChatHistory = true;
            this.loadingSearchResult = true;
            this.chatStreaming = false;
            this.chatStreamingErrorOccurred = false;
            this.chatStreamingUpdatedTime = undefined;
            this.geboUserChatsControllerService.getChatHistory(this.chatInfo?.code).subscribe({
                next: (value) => {
                    this.interactions = value?.interactions ? value.interactions as GeboChatInteraction[] : [];
                    this.loadingSearchResult = false;
                    this.chatInfoFormGroup.patchValue({ code: value.code, description: value.description });
                    this.lastInteractionMessages = this.interactions && this.interactions?.length>0? [chat_history_loaded]:[clean_chat_loaded];
                    this.scrollToBottom();

                },
                complete: () => {
                    this.loadingChatHistory = false;
                }
            });
        } else {
            this.interactions = [];
        }

    }

    /**
     * Initializes the component by setting up form value change subscriptions
     */
    ngOnInit(): void {
        this.formGroup.controls["forcedRequestDocuments"].valueChanges.subscribe((selectedDocuments: string[]) => {
            this.docSelectedMap.clear();
            if (selectedDocuments) {
                selectedDocuments.forEach(x => {
                    this.docSelectedMap.set(x, true);
                });
            }
        });
    }

    /**
     * Gets document references from an interaction
     * 
     * @param interaction Chat interaction to extract documents from
     * @returns Array of document references
     */
    getDocs(interaction: GeboChatInteraction): GResponseDocumentRef[] {
        return interaction?.response?.documentsRef ? interaction.response.documentsRef : [];
    }

    /**
     * Opens the dialog to change chat description
     */
    doChangeDescription(): void {
        this.changeDescriptionDialogOpened = true;
    }

    /**
     * Saves the changed chat description
     */
    doSaveChangedDescription() {
        this.loadingChatHistory = true;
        this.geboUserChatsControllerService.changeChatDescription(this.chatInfoFormGroup.value).subscribe({
            next: (value) => {
                if (this.chatInfo) {
                    this.chatInfo.description = value.description;

                } else {
                    this.chatInfo = value;
                }
            },
            complete: () => {
                this.loadingChatHistory = false;
                this.changeDescriptionDialogOpened = false;
            }
        });
    }

    /**
     * Handles chat deletion with confirmation
     */
    doDeleteChat(): void {
        const actualChat = this.chatInfoFormGroup.value;
        this.loadingChatHistory = true;
        const confirmation: ExtendedConfirmation = {
            id: "DELETE_CHAT_ACTION",
            header: "Delete chat action",
            message: "Are you shure you whant to delete this chat?",
            accept: () => {
                this.geboUserChatsControllerService.deleteChat(actualChat.code).subscribe({
                    next: (value) => {
                        this.deleteChatAction.emit(actualChat);
                    },
                    complete: () => {
                        this.loadingChatHistory = false
                    }
                })
            }

        };
        const translateSubscription = this.geboAiTranslationService.translateConfirmation(moduleId, entityId, confirmation).subscribe({
            next: (trconfirmation) => {

                this.confirmService.confirm(trconfirmation ? trconfirmation : confirmation);
                try {
                    translateSubscription.unsubscribe();
                } catch (e) {

                }

            }
        })

    }

    /**
     * Performs a chat request using REST API
     * 
     * @param r Chat request to send
     * @param doSpeach Flag to trigger speech playback of response
     */
    private callRestChat(r: GeboChatRequest, doSpeach: boolean): void {


        const interaction: GeboChatInteraction = {

            request: r
        };
        this.interactions.push(interaction);
        let responseObservable: Observable<GeboChatTemplatedResponse> | null = null;
        if (this.ragsystem === true) {

            responseObservable = this.ragChatService.ragChat(r);

        } else {

            responseObservable = this.chatService.chat(r);


        }

        if (responseObservable) {
            this.loadingChatResponse = true;
            responseObservable.subscribe(
                {
                    next: (response) => {
                        interaction.response = response;

                        if (r.userChatContextCode !== response.userChatContextCode) {
                            const newContext: GUserChatInfo = { ...this.chatInfo };
                            newContext.code = response.userChatContextCode;
                            newContext.chatProfileCode = r.chatProfileCode;
                            newContext.ragChat = this.ragsystem;
                            if (this.chatInfo) {
                                this.chatInfo.code = response.userChatContextCode;
                            }
                            this.addedChatAction.emit(newContext);
                        }
                        this.lastInteractionMessages = response?.backendMessages ? response.backendMessages as ToastMessageOptions[] : [];

                        const dataUpdate: any = {
                            chatProfileCode: r.chatProfileCode,
                            chatModelCode: r.chatModelCode,
                            userChatContextCode: response.userChatContextCode,
                            query: null
                        };
                        this.formGroup.patchValue(dataUpdate);
                        this.scrollToBottom();
                        this.loadingChatResponse = false;
                        if (doSpeach === true) {
                            this.speechPlay(response.queryResponse);
                        }
                    },
                    error: (error) => {
                        this.loadingChatResponse = false;
                    },
                    complete: () => {

                    }
                }
            );
        }
        // this.scrollDown();
    }

    /**
     * Performs a chat request using reactive/streaming API
     * 
     * @param r Chat request to send
     * @param doSpeach Flag to trigger speech playback of response
     */
    private callReactiveChat(r: GeboChatRequest, doSpeach: boolean): void {
        this.loadingChatResponse = true;
        this.currentPipelineRouterDecisionCode = (this.ragsystem === true ? "WAITING" : undefined);
        const suggestChatDescription: boolean = this.interactions ? this.interactions.length == 0 : true;
        const interaction: GeboChatInteraction = {
            loading: true,
            request: r,
            pipelineRouterDecisionCode: this.currentPipelineRouterDecisionCode
        };
        this.interactions.push(interaction);
        const messageCallback = (msg: IGeboChatMessage | string) => {
            if (!msg) return;
            this.loadingChatResponse = false;
            this.chatStreamingUpdatedTime = new Date().getTime();
            try {
                let recvd: IGeboChatMessage;
                if (typeof msg === "string") {
                    recvd = {
                        content: msg,
                        contentObjectType: "String",
                        lastMessage: false
                    }
                } else {
                    recvd = msg as IGeboChatMessage;
                }

                this.chatInputShell.onStreamMessage(recvd);

                if (recvd.contentObjectType === "GUserMessage") {
                    const message = recvd.content as ToastMessageOptions;
                    this.lastInteractionMessages = [message];
                    this.chatStreamingErrorOccurred = true;

                }
                if (recvd.contentObjectType === "PipelineRoutingInfos") {
                    const pipelineRouterDecisionCode: string = recvd.content?.pipelineRouterDecisionCode;
                    interaction.pipelineRouterDecisionCode = pipelineRouterDecisionCode;
                    this.currentPipelineRouterDecisionCode = pipelineRouterDecisionCode;
                    if (recvd.content?.chatModel)
                        this.modelName = recvd.content?.chatModel;
                    console.log("current chat pipeline type: " + pipelineRouterDecisionCode);

                }
                if (recvd && recvd.contentObjectType && recvd.contentObjectType === "GeboChatResponse") {

                    interaction.response = recvd.content;
                    if (interaction.response) {
                        const response: GeboChatResponse = interaction.response;
                        if (r.userChatContextCode !== response.userChatContextCode) {
                            if (this.chatInfo) {
                                this.chatInfo.code = response.userChatContextCode;
                            }
                            const newContext: GUserChatInfo = { ...this.chatInfo };
                            newContext.code = response.userChatContextCode;
                            newContext.chatProfileCode = r.chatProfileCode;
                            newContext.ragChat = this.ragsystem;
                            this.addedChatAction.emit(newContext);
                        }
                        if (recvd.lastMessage === true) {
                            this.currentPipelineRouterDecisionCode = undefined;
                            interaction.pipelineRouterDecisionCode = undefined;
                            if (suggestChatDescription === true) {
                                if (response.userChatContextCode) {
                                    const newChatInfoObservable: Observable<GUserChatInfo> = this.userChatControllerService.suggestChatDescription(response.userChatContextCode);
                                    newChatInfoObservable.subscribe({
                                        next: (value: GUserChatInfo) => {
                                            if (this.chatInfo && value?.description) {
                                                this.chatInfo.description = value?.description;
                                            }
                                            this.updatedChatAction.emit(value);
                                        },
                                        error: () => {

                                        }
                                    });
                                }
                            }

                            this.lastInteractionMessages = response?.backendMessages ? response.backendMessages as ToastMessageOptions[] : [];

                            const dataUpdate: any = {
                                chatProfileCode: r.chatProfileCode,
                                chatModelCode: r.chatModelCode,
                                userChatContextCode: response.userChatContextCode,
                                query: null,
                                chatPipelineProcessId: null
                            };
                            this.formGroup.patchValue(dataUpdate);




                            if (doSpeach === true && recvd.lastMessage === true && response.queryResponse) {
                                this.speechPlay(response.queryResponse);
                            }
                        }
                    }
                    this.scrollToBottom();
                } else if (recvd.contentObjectType === "String") {
                    if (interaction.response) {
                        if (!interaction.response.queryResponse) {
                            interaction.response.queryResponse = "";
                        }
                        interaction.response.queryResponse += recvd.content;
                    }
                }
                if (recvd.lastMessage === true) {
                    interaction.loading = false;
                    this.chatStreaming = false;
                }
            } catch (e) {
                console.error("Exception :", e);
            }
            console.log("Received chat word: " + msg);
        };
        const errorCallBack = (error: any) => {
            this.chatStreaming = false;
            this.chatStreamingErrorOccurred = true;
            console.error("Exception in receiving data", error);

            this.chatInputShell.onStreamError(error);

        }
        this.chatStreaming = true;
        this.chatStreamingErrorOccurred = false;

        if (this.ragsystem === true) {
            const agenticChatRequest: AgenticChatRequestBody = {
                request: r,
                environment: undefined
            }
            if (this.currentPipelineRoutingOption) {
                agenticChatRequest.request.chatPipelineProcessId = this.currentPipelineRoutingOption.chatPipelineProcessId;
                agenticChatRequest.environment = this.currentPipelineRoutingOption.pipelineParams;
            }
            //this.reactiveChatService.streamRagChat(r, messageCallback, errorCallBack);
            this.reactiveChatService.streamAgenticChat(agenticChatRequest, messageCallback, errorCallBack);
        } else {
            this.reactiveChatService.streamChat(r, messageCallback, errorCallBack);
        }



    }
    onSumbit(): void {
        if (this.formGroup.valid && !this.chatStreaming) {
            this.sendMessage();
        }
    }
    /**
     * Sends a message to the chat system
     * 
     * @param speechOutput Flag to indicate if speech output is desired
     */
    sendMessage(speechOutput?: boolean) {
        const doSpeach = speechOutput === true;
        const qry: GeboChatRequest = this.formGroup.value;
        const uuid: string = uuidv4();
        qry.id = uuid;
        qry.streamResponse = true;
        // this.loadingSearchResult = true;
        this.formGroup.controls["query"].setValue(null);
        this.formGroup.controls["userUploadedContents"].setValue([]);
        if (this.useRestOnly === true) {
            this.callRestChat(qry, doSpeach);
        } else {
            this.callReactiveChat(qry, doSpeach);
        }
    }
    /**
     * Sends a text-to-speech request and plays the audio
     * 
     * @param text Text to convert to speech
     */
    speechPlay(text: string) {
        let chatModelCode: string | undefined;
        if (!chatModelCode) {
            chatModelCode = this.capabilities?.configurationCode;
        }
        const sr: SpeechRequest = {
            text: text
        };


        if (chatModelCode) {
            this.messageService.addMessage(moduleId, entityId, loading_vocal_answer);


            this.waitingForAudiocontent = true;
            this.chatService.speechText(sr, chatModelCode).subscribe(
                {
                    next: (value) => {
                        this.currentAudioTrack = value;
                        this.messageService.addMessage(moduleId, entityId, loading_vocal_answer_received);

                    },
                    error: (err) => {
                        this.waitingForAudiocontent = false;
                    },
                    complete: () => {
                        this.waitingForAudiocontent = false;
                    }
                }
            );


        }
    }
    onNewSessionCreatedOnUpload(info: GUserChatInfo) {
        this.chatInfo = info;
        this.formGroup.controls["chatProfileCode"].setValue(info.chatProfileCode);
        this.formGroup.controls["userChatContextCode"].setValue(info.code);
        this.formGroup.controls["chatModelCode"].setValue(info.chatModelCode);
        this.chatInfoFormGroup.patchValue(info);
        this.addedChatAction.emit(info);
    }
    onExternalModuleRequest(request: GeboChatRequest) {
        if (!this.interactions) {
            this.interactions = [];
        }
        this.interactions.push({
            request: request,
            loading: false,
        });
    }
    onExternalModuleResponse(response: GeboChatResponse) {
        const interaction = this.interactions[this.interactions.length - 1];
        if (!interaction.response) {
            interaction.response = response;
        }
    }

    /**
     * Handles speech-to-text conversion events
     * 
     * @param event Event containing the audio data to transcribe
     */
    onSpeechEvent(event: { data: Blob; url: string; }) {
        let chatModelCode: string | undefined;
        if (!chatModelCode) {
            chatModelCode = this.capabilities?.configurationCode;
        }
        if (chatModelCode) {
            this.messageService.addMessage(moduleId, entityId, your_speech_is_uploading);


            const url: string = this.basePath + "/api/users/GeboDirectModelChatController/transcriptText";
            console.log("sending directly to model code:" + chatModelCode);
            if (event.data) {

                const params: any = {
                    "modelCode": chatModelCode
                };
                this.waitingForTranscript = true;
                event.data.arrayBuffer().then((value: ArrayBuffer) => {
                    this.httpClient.post(url, value, {
                        params: params

                    }).subscribe({
                        next: (value: TranscriptResponse) => {
                            this.formGroup.controls["query"].setValue(value?.text);
                            this.sendMessage(true);
                        },
                        complete: () => {

                            this.waitingForTranscript = false;
                        }
                    });
                }).catch((reason) => {
                    console.error(reason);
                    this.waitingForTranscript = false;
                }).finally(() => {
                    this.waitingForTranscript = false;
                });
            }
        }
    }

}