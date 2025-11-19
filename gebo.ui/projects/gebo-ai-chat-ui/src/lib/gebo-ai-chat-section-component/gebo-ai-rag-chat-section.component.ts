/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ChatModelsLookupControllerService, DataPage, GChatProfileConfiguration, GeboChatControllerService, GeboRagChatControllerService, GeboUserChatsControllerService, GUserChatInfo, PageGUserChatInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { FormControl, FormGroup } from "@angular/forms";
import { PaginatorState } from "primeng/paginator";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { ScrollerOptions, TreeNode } from "primeng/api";
import { TreeNodeSelectEvent, TreeScrollIndexChangeEvent } from "primeng/tree";

/* AI generated comments */
/**
 * GeboAiChatSectionComponent is responsible for managing the chat interface section.
 * It handles:
 * - Loading and displaying chat history
 * - Creating new regular or RAG (Retrieval-Augmented Generation) chats
 * - Managing chat profiles and model options
 * - Switching between different chat views
 */
@Component({
    selector: "gebo-ai-chat-section-component",
    templateUrl: "gebo-ai-rag-chat-section.component.html",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiChatModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAiChatSectionComponent") }]
})
export class GeboAiChatSectionComponent implements OnInit, OnChanges {


    /** Flag indicating if the chats page is currently loading */
    public chatsPageLoading: boolean = false;
    /** Flag indicating if the chat models are currently loading */
    public chatsModelLoading: boolean = false;
    /** Flag indicating if the chat profiles are currently loading */
    public chatsProfilesLoading: boolean = false;
    /** Flag controlling the visibility of the chats list and options panel */
    public chatsListAndOptionsVisible: boolean = false;
    /** Flag indicating if a RAG chat is currently open */
    public openRagChat: boolean = false;
    /** Flag indicating if a regular chat is currently open */
    public openChat: boolean = false;
    /** Stores the current page of chat history */
    public chatsPage?: PageGUserChatInfo;
    /** Stores the currently active chat information */
    public currentChat: GUserChatInfo = {};
    /**
     * Computed property to determine if any loading is in progress
     * Returns true if any loading process is active
     */
    public get loading(): boolean {
        return this.chatsPageLoading || this.chatsModelLoading || this.chatsProfilesLoading;
    }
    /** Pagination configuration for chat history */
    public page: DataPage = {
        page: 0,
        pageSize: 100
    };
    protected chatsTree!: TreeNode[];
    protected scrollOptions!: ScrollerOptions;
    /** Form group for RAG chat profile selection */
    public formGroup: FormGroup = new FormGroup({
        chatProfileCode: new FormControl()

    });
    /** Form group for regular chat model selection */
    public chatFormGroup: FormGroup = new FormGroup({
        chatModelCode: new FormControl()

    });
    /** Stores available chat profile configurations */
    public chatProfilesData: GChatProfileConfiguration[] = [];
    /** Stores available chat models data */
    chatModelsData: { code?: string, description?: string }[] = [];

    /**
     * Component constructor that injects necessary services
     * @param activatedRoute Service to access the current route information
     * @param geboRagChatControllerService Service for RAG chat operations
     * @param geboChatControllerService Service for regular chat operations
     * @param geboUserChatsControllerService Service for user chat management
     * @param geboChatModelsControllerService Service for accessing chat models
     */
    constructor(
        private cd: ChangeDetectorRef,
        private geboRagChatControllerService: GeboRagChatControllerService,
        private geboChatControllerService: GeboChatControllerService,
        private geboUserChatsControllerService: GeboUserChatsControllerService,
        private geboChatModelsControllerService: ChatModelsLookupControllerService,
        private geboTranslationService: GeboAITranslationService) {
        this.scrollOptions = {
           
            onLazyLoad: () => {
                console.log("lazyLoad");
            },
            onScroll: () => {
                console.log("lazyScroll");
            },
            onScrollIndexChange: () => {
                console.log("indexChange");
            }

        };
    }

    /**
     * Creates a new RAG chat with the selected profile
     * Sets up the current chat with profile information and transitions to RAG chat view
     */
    createRagChat(): void {
        const value = this.formGroup.value;
        this.currentChat = {
            code: undefined,
            chatProfileCode: value.chatProfileCode,
            description: "Chat with profile " + this.chatProfilesData?.find(x => x.code === value.chatProfileCode)?.description
        };
        this.doOpenRagChat();
    }

    /**
     * Opens the RAG chat interface and hides other chat views
     */
    doOpenRagChat(): void {
        this.openRagChat = true;
        this.openChat = false;
        this.chatsListAndOptionsVisible = false;
    }

    /**
     * Creates a new regular chat with the selected model
     * Sets up the current chat with model information and transitions to chat view
     */
    createChat(): void {
        const value = this.chatFormGroup.value;
        this.currentChat = {
            code: undefined,
            chatProfileCode: undefined,
            chatModelCode: value.chatModelCode,
            description: "Chat with " + this.chatModelsData?.find(x => x.code === value.chatModelCode)?.description
        };
        this.doOpenChat();
    }

    /**
     * Opens the regular chat interface and hides other chat views
     */
    doOpenChat(): void {
        this.openChat = true;
        this.openRagChat = false;
        this.chatsListAndOptionsVisible = false;
    }
    scrollIndexChange(indexChange: TreeScrollIndexChangeEvent) {

    }

    /**
     * Loads the user's chat history with pagination
     * Updates the chatsPage property with the retrieved data
     */
    public loadChatList(page: number = 0): void {
        if (!this.chatsTree) {
            this.chatsTree = [{ label: "Chats", leaf: false, children: [], expanded: true }];

        }

        if (this.page.page !== undefined && this.page.pageSize !== undefined) {
            this.page.page = page;
            this.chatsPageLoading = true;
            this.geboUserChatsControllerService.getMyChatsPaged(this.page.page, this.page.pageSize).subscribe({
                next: (page) => {
                    this.chatsPage = page;
                    const childrens: GUserChatInfo[] = page.content ? page.content : [];
                    const newItems: TreeNode<GUserChatInfo>[] = childrens.map(x => {
                        const item: TreeNode = {
                            label: x.description,
                            leaf: true,
                            data: x,
                            icon: "pi pi-comments"
                        };

                        return item;
                    });
                    if (!this.chatsTree[0].children) {
                        this.chatsTree[0].children = newItems;
                    } else {
                        this.chatsTree[0].children = [...this.chatsTree[0].children, ...newItems];
                    }
                }, error: (err) => {
                    this.chatsPageLoading = false;
                },
                complete: () => {
                    this.chatsPageLoading = false;
                }
            });
        }
    }
    onUpdatedChat(chatInfo: GUserChatInfo) {
        if (this.chatsTree && this.chatsTree.length) {
            const childs = this.chatsTree[0].children as TreeNode<GUserChatInfo>[];
            if (childs) {
                for (let i: number = 0; i < childs.length; i++) {
                    if (childs[i]?.data?.code === chatInfo?.code) {
                        childs[i].data = chatInfo;
                    }
                }
            }
        }
    }

    openChatItem(event: TreeNodeSelectEvent) {
        if (event && event.node.data) {
            this.activateChat(event.node.data);
        }
    }
    /**
     * Closes the current chat, returns to the chat list view, and refreshes the chat history
     */
    reloadChatsAndCloseChat(): void {
        this.chatsListAndOptionsVisible = true;
        this.currentChat = {};
        this.openChat = false;
        this.openRagChat = false;
        this.loadChatList();
    }

    /**
     * Loads available chat profiles and models for selection
     * Updates form controls with default selections when data is loaded
     */
    loadChatOptions(): void {
        this.chatsProfilesLoading = true;
        this.geboChatModelsControllerService.getRuntimeConfiguredChatModelsLookup().subscribe({
            next: (value) => {
                this.chatModelsData = value;
                if (this.chatFormGroup && this.chatModelsData && this.chatModelsData.length) {
                    this.chatFormGroup.controls["chatModelCode"].setValue(this.chatModelsData[0].code);
                }
            },
            complete: () => {
                this.chatsProfilesLoading = false;
            }
        });
        this.chatsModelLoading = true;
        this.geboRagChatControllerService.getChatProfiles().subscribe({
            next: (value) => {
                this.chatProfilesData = value;
                if (this.formGroup && this.chatProfilesData && this.chatProfilesData.length) {
                    this.formGroup.controls["chatProfileCode"].setValue(this.chatProfilesData[0].code);
                }
            },
            complete: () => {
                this.chatsModelLoading = false;
            }
        });
    }

    /**
     * Angular lifecycle hook that initializes the component
     * Shows the chat list view and loads chat options and history
     */
    ngOnInit(): void {

        this.chatsListAndOptionsVisible = true;
        this.loadChatOptions();
        this.loadChatList();

    }

    /**
     * Sets the provided chat as the current chat and opens the appropriate interface
     * Opens RAG chat or regular chat based on the chat type
     * @param chat The chat information to activate
     */
    activateChat(chat: GUserChatInfo) {
        this.currentChat = chat;
        if (chat.ragChat === true) {
            this.doOpenRagChat();
        } else {
            this.doOpenChat();
        }
    }

    /**
     * Angular lifecycle hook that responds to input property changes
     * @param changes Simple changes object containing current and previous values
     */
    ngOnChanges(changes: SimpleChanges): void {

    }

    /**
     * Handles paginator events for the chat history list
     * Updates pagination settings and reloads the chat list
     * @param evt The paginator state containing page and rows information
     */
    onPageChange(evt: PaginatorState) {
        this.page.page = evt.page;
        this.page.pageSize = evt.rows;
        this.loadChatList();
    }

}