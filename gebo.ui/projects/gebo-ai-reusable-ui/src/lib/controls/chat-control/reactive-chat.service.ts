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

import { HttpClient, HttpEvent, HttpEventType, HttpHeaders } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { BASE_PATH, GeboChatRequest, GeboRagChatControllerService } from "@Gebo.ai/gebo-ai-rest-api";
import { getAuth, getAuthHeader } from "../../infrastructure/gebo-credentials";
import { concat, concatMap, map, Observable, of, Subject } from "rxjs";

/**
 * Interface representing a message received from the Gebo Chat API.
 * Messages can contain different types of content and indicate if they're the last in a sequence.
 */
export interface IGeboChatMessage {
    content?: any;                // The actual content of the message
    contentObjectType?: string;   // Type descriptor for the content
    lastMessage?: boolean         // Flag indicating if this is the final message in a sequence
};

/**
 * Service that handles streaming chat interactions with the Gebo AI API.
 * Uses fetch API to establish streaming connections and process server-sent events.
 */
@Injectable({
    providedIn: "root"
})
export class ReactiveRagChatService {

    /**
     * Creates an instance of the ReactiveRagChatService.
     * 
     * @param basePath - Base URL path for API requests, injected from the BASE_PATH token
     * @param ragChatService - Service for RAG-based chat interactions
     * @param httpClient - Angular's HTTP client for making requests
     */
    constructor(@Inject(BASE_PATH) private basePath: string, private ragChatService: GeboRagChatControllerService, private httpClient: HttpClient) {


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

    /**
     * Core implementation of the streaming chat functionality.
     * Uses the Fetch API with streaming to process server-sent events in real-time.
     * 
     * @param apiUrl - The endpoint URL to connect to
     * @param request - The chat request to send
     * @param onMessage - Callback function for handling received messages
     * @param onError - Optional callback function for handling errors
     */
    private internalStreamChat(apiUrl: string, request: GeboChatRequest, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
        let auth = getAuthHeader();
        if (!auth) auth={};
        let headers: HeadersInit = {
            ...auth,
            'Content-Type': 'application/json'
        };
        
        let lastMessageReceived: boolean = false;
        fetch(apiUrl, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(request)
        })
            .then(response => {
                if (!response.body) {
                    throw new Error('No response body found');
                }
                const data: string = "data:";
                const dataLength: number = data.length;
                const reader = response.body.getReader();
                const decoder = new TextDecoder();
                
                /**
                 * Recursive function to process chunks of the stream as they arrive
                 */
                const processStream = () => {
                    reader.read().then(({ done, value }) => {
                        if (done) {
                            console.log('Stream completed');
                            return;
                        }

                        const chunk = decoder.decode(value, { stream: true });
                        // Handle new-line separated messages
                        const messages = chunk.split('\n').filter(line => line.trim() !== ''); 

                        messages.forEach(msg => {
                            try {
                                let json: string | undefined = undefined;
                                if (msg && msg.startsWith(data)) {
                                    json = msg.substring(dataLength);
                                } else if (msg) {
                                    json = msg;
                                }
                                try {
                                    if (json) {
                                        const msgObject: IGeboChatMessage = JSON.parse(json);
                                        lastMessageReceived = lastMessageReceived || msgObject?.lastMessage === true;
                                        onMessage(msgObject);
                                    }
                                } catch (e) {
                                    if (json) {
                                        onMessage(json);
                                    }
                                }
                                // Parse and emit each JSON message
                            } catch (error) {
                                console.error('Error parsing JSON:', error);
                            }
                        });

                        // Recursively process the next chunk
                        processStream(); 
                    }).catch(error => {
                        if (onError && lastMessageReceived !== true) onError(error);
                    });
                };

                processStream();
            })
            .catch(error => {
                if (onError && lastMessageReceived !== true) onError(error);
            });
    }
}