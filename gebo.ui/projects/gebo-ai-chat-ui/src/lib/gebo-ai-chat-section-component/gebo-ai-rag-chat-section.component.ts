/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */




import { ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChanges } from "@angular/core";
import { ChatModelsLookupControllerService, ChatUIOptions, DataPage, GChatProfileConfiguration, GeboRagChatControllerService, GeboUserChatsControllerService, GUserChatInfo, PagedModelGUserChatInfo } from "@Gebo.ai/gebo-ai-rest-api";
import { FormControl, FormGroup } from "@angular/forms";
import { PaginatorState } from "primeng/paginator";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { ScrollerOptions, TreeNode } from "primeng/api";
import { TreeNodeSelectEvent, TreeScrollIndexChangeEvent } from "primeng/tree";
import { ActivatedRoute, Router } from "@angular/router";

interface ExtendedGUserChatInfo extends GUserChatInfo {
    routerLink: string
};
function toExtended(chatInfo: GUserChatInfo): ExtendedGUserChatInfo {
    const extended: ExtendedGUserChatInfo = {
        ...chatInfo,
        routerLink: "/ui/chat/" + chatInfo.code + "/load"
    };
    return extended;
}

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
    styleUrl: "gebo-ai-rag-chat-section.component.scss",
    standalone: false,
    providers: [{ provide: GEBO_AI_MODULE, useValue: "GeboAiChatModule", multi: false }, { provide: GEBO_AI_FIELD_HOST, multi: false, useValue: fieldHostComponentName("GeboAiChatSectionComponent") }]
})
export class GeboAiChatSectionComponent implements OnInit, OnChanges {


    /** Flag indicating if the chats page is currently loading */
    protected chatsPageLoading: boolean = false;
    /** Flag indicating if the chat models are currently loading */
    protected chatsModelLoading: boolean = false;
    /** Flag indicating if the chat profiles are currently loading */
    protected chatsProfilesLoading: boolean = false;
    /** Flag controlling the visibility of the chats list and options panel */
    protected chatsListAndOptionsVisible: boolean = false;
    /** Flag indicating if a RAG chat is currently open */
    protected openRagChat: boolean = false;
    /** Flag indicating if a regular chat is currently open */
    protected openChat: boolean = false;
    /** Stores the current page of chat history */
    protected chatsPage?: PagedModelGUserChatInfo;
    /** Stores the currently active chat information */
    protected currentChat: GUserChatInfo = {};

    protected chatDataLoading: boolean = false;
    /**
     * Computed property to determine if any loading is in progress
     * Returns true if any loading process is active
     */
    protected get loading(): boolean {
        return this.chatsPageLoading || this.chatsModelLoading || this.chatsProfilesLoading || this.chatDataLoading;
    }
    /** Pagination configuration for chat history */
    public page: DataPage = {
        page: 0,
        pageSize: 100
    };
    protected chatsTree!: TreeNode[];
    protected scrollOptions!: ScrollerOptions;
    /** Form group for RAG chat profile selection */
    protected formGroup: FormGroup = new FormGroup({
        chatProfileCode: new FormControl()

    });
    /** Form group for regular chat model selection */
    protected chatFormGroup: FormGroup = new FormGroup({
        chatModelCode: new FormControl()

    });
    /** Stores available chat profile configurations */
    protected chatProfilesData: GChatProfileConfiguration[] = [];
    /** Stores available chat models data */
    protected chatModelsData: { code?: string, description?: string }[] = [];
    protected chatUIOptions?: ChatUIOptions;
    protected id?: string;

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
        private actualRouting: ActivatedRoute,
        private router: Router,
        private geboRagChatControllerService: GeboRagChatControllerService,
        private geboUserChatsControllerService: GeboUserChatsControllerService,
        private geboChatModelsControllerService: ChatModelsLookupControllerService) {
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


    protected routeNewRagChat(): void {
        const value = this.formGroup.value;
        const chatProfileCode = value.chatProfileCode;
        this.chatDataLoading = true;
        this.geboUserChatsControllerService.createCleanChatByChatProfileCode(chatProfileCode).subscribe({
            next: (chatInfo: GUserChatInfo) => {
                if (chatInfo.code) {
                    this.doOpenRagChat();
                    const route: string[] = ["/", "ui", "chat", chatInfo.code, "load"];
                    this.router.navigate(route, { replaceUrl: true, onSameUrlNavigation: "reload" }).then(ok => console.log('Navigation result:', ok)).catch(err => console.error('Navigation error:', err));
                }
            },
            error: () => {
                this.chatDataLoading = false;
            },
            complete: () => {
                this.chatDataLoading = false;
            }
        });

    }
    /**
     * Opens the RAG chat interface and hides other chat views
     */
    protected doOpenRagChat(): void {
        this.openRagChat = true;
        this.openChat = false;
        this.chatsListAndOptionsVisible = false;
    }


    protected routeNewChat(): void {
        const value = this.chatFormGroup.value;
        const chatModelCode = value.chatModelCode;
        this.chatDataLoading = true;
        this.geboUserChatsControllerService.createCleanChatByModelCode(chatModelCode).subscribe({
            next: (chatInfo: GUserChatInfo) => {
                if (chatInfo.code) {
                    this.doOpenChat();
                    const route: string[] = ["/", "ui", "chat", chatInfo.code, "load"];
                    this.router.navigate(route, { replaceUrl: true, onSameUrlNavigation: "reload" }).then(ok => console.log('Navigation result:', ok)).catch(err => console.error('Navigation error:', err));
                }
            },
            error: () => {
                this.chatDataLoading = false;
            },
            complete: () => {
                this.chatDataLoading = false;
            }
        });
    }
    /**
     * Opens the regular chat interface and hides other chat views
     */
    protected doOpenChat(): void {
        this.openChat = true;
        this.openRagChat = false;
        this.chatsListAndOptionsVisible = false;
    }
    protected scrollIndexChange(indexChange: TreeScrollIndexChangeEvent) {

    }
    protected routeToChat(chat: GUserChatInfo): void {
        if (chat.code) {
            const route: string[] = ["/", "ui", "chat", chat.code, "load"];
            this.router.navigate(route, { replaceUrl: true, onSameUrlNavigation: "reload" }).then(ok => console.log('Navigation result:', ok)).catch(err => console.error('Navigation error:', err));
        }
    }
    /**
     * Loads the user's chat history with pagination
     * Updates the chatsPage property with the retrieved data
     */
    protected loadChatList(): void {
        if (!this.chatsTree) {
            this.chatsTree = [{ label: "Chats", leaf: false, children: [], expanded: true }];

        }


        this.chatsPageLoading = true;
        this.geboUserChatsControllerService.getMyChats().subscribe({
            next: (chats) => {

                const childrens: GUserChatInfo[] = chats ? chats : [];
                const newItems: TreeNode<GUserChatInfo>[] = childrens.map(x => {
                    const item: TreeNode = {
                        label: x.description,
                        leaf: true,
                        data: toExtended(x),
                        icon: "pi pi-comments"
                    };

                    return item;
                });

                this.chatsTree[0].children = newItems;

            }, error: (err) => {
                this.chatsPageLoading = false;
            },
            complete: () => {
                this.chatsPageLoading = false;
            }
        });

    }
    protected onUpdatedChat(chatInfo: GUserChatInfo) {
        if (this.chatsTree && this.chatsTree.length !== undefined) {
            const childs = this.chatsTree[0].children as TreeNode<GUserChatInfo>[];
            if (childs) {
                for (let i: number = 0; i < childs.length; i++) {
                    if (childs[i]?.data?.code === chatInfo?.code) {
                        childs[i].data = chatInfo;
                    }
                }
            }
        }
        
            this.loadChatList();
       
    }

    protected openChatItem(event: TreeNodeSelectEvent) {
        if (event && event.node.data) {
            this.activateChat(event.node.data);
        }
    }
    /**
     * Closes the current chat, returns to the chat list view, and refreshes the chat history
     */
    protected reloadChatsAndCloseChat(): void {
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
    protected loadChatOptions(): void {
        this.chatsProfilesLoading = true;
        this.geboUserChatsControllerService.getUIConfig().subscribe({
            next: (options) => {
                this.chatUIOptions = options;
            }
        })
        this.geboChatModelsControllerService.getRuntimeConfiguredChatModelsLookup().subscribe({
            next: (value) => {
                this.chatModelsData = value;
                if (this.chatFormGroup && this.chatModelsData && this.chatModelsData.length) {
                    this.chatFormGroup.controls["chatModelCode"].setValue(this.chatModelsData[0].code);
                }
            },
            error: () => {
                this.chatsProfilesLoading = false;
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
            error: () => {
                this.chatsModelLoading = false;
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
        this.chatsTree = [{ label: "Chats", leaf: false, children: [], expanded: true }];
        this.chatsListAndOptionsVisible = true;
        this.loadChatOptions();
        this.loadChatList();
        this.actualRouting.params.subscribe({
            next: (params) => {
                this.id = params["id"];
                if (this.id) {
                    this.chatDataLoading = true;
                    this.geboUserChatsControllerService.getChatInfosByCode(this.id).subscribe({
                        next: (chatInfo: GUserChatInfo) => {

                            this.activateChat(chatInfo);
                        },
                        error: () => {
                            this.chatDataLoading = false;
                        },
                        complete: () => {
                            this.chatDataLoading = false;
                        }
                    });
                }
                if (params["chatProfile"]) {
                    const chatProfileCode = params["chatProfile"];
                    this.chatDataLoading = true;

                    this.geboUserChatsControllerService.createCleanChatByChatProfileCode(chatProfileCode).subscribe({
                        next: (chatInfo: GUserChatInfo) => {

                            this.activateChat(chatInfo);
                        },
                        error: () => {
                            this.chatDataLoading = false;
                        },
                        complete: () => {
                            this.chatDataLoading = false;
                        }
                    });
                }
                if (params["modelCode"]) {
                    const modelCode = params["modelCode"];
                    this.chatDataLoading = true;
                    this.geboUserChatsControllerService.createCleanChatByModelCode(modelCode).subscribe({
                        next: (chatInfo: GUserChatInfo) => {

                            this.activateChat(chatInfo);
                        },
                        error: () => {
                            this.chatDataLoading = false;
                        },
                        complete: () => {
                            this.chatDataLoading = false;
                        }
                    });
                }
            }
        });

    }

    /**
     * Sets the provided chat as the current chat and opens the appropriate interface
     * Opens RAG chat or regular chat based on the chat type
     * @param chat The chat information to activate
     */
    protected activateChat(chat: GUserChatInfo) {
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