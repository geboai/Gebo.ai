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
 * This file implements a reactive service for handling chat interactions with the Gebo AI API.
 * It provides functionality for streaming chat responses from both regular and RAG (Retrieval Augmented Generation) endpoints.
 */

import { Inject, Injectable } from "@angular/core";
import { BASE_PATH, GeboChatRequest } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAIBaseStreamingService } from "../../services/base-streaming.service";
import { IGeboChatMessage } from "../../services/gebo-chat-message";

export interface AgenticChatRequestBody {
    request: GeboChatRequest;
    environment?:any;
}

/**
 * Service that handles streaming chat interactions with the Gebo AI API.
 * Uses fetch API to establish streaming connections and process server-sent events.
 */
@Injectable({
    providedIn: "root"
})
export class ReactiveRagChatService extends GeboAIBaseStreamingService{

    /**
     * Creates an instance of the ReactiveRagChatService.
     * 
     * @param basePath - Base URL path for API requests, injected from the BASE_PATH token
     * @param ragChatService - Service for RAG-based chat interactions
     * @param httpClient - Angular's HTTP client for making requests
     */
    constructor(@Inject(BASE_PATH) private basePath: string) {
        super();

    }

    /**
     * Initiates a streaming chat session with the direct model chat endpoint.
     * 
     * @param request - The chat request containing messages and parameters
     * @param onMessage - Callback function that processes each received message
     * @param onError - Optional callback function for handling errors
     */
    streamChat(request: GeboChatRequest, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        const apiUrl: string = this.basePath + "/api/users/GeboDirectModelChatController/streamResponse";
        this.internalStreamChat(apiUrl, request, onMessage, onError);
    }

    /**
     * Initiates a streaming chat session with the RAG (Retrieval Augmented Generation) endpoint.
     * RAG enhances responses with additional context retrieved from knowledge bases.
     * 
     * @param request - The chat request containing messages and parameters
     * @param onMessage - Callback function that processes each received message
     * @param onError - Optional callback function for handling errors
     */
    streamRagChat(request: GeboChatRequest, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        const apiUrl: string = this.basePath + "/api/users/GeboChatController/streamRagResponse";
        this.internalStreamChat(apiUrl, request, onMessage, onError);
    }

    streamAgenticChat(request: AgenticChatRequestBody, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        const apiUrl:string= this.basePath+"/api/users/GeboChatPipelinesController/streamChatPipeline";
         this.internalStreamChat(apiUrl, request, onMessage, onError);
    }

    
}