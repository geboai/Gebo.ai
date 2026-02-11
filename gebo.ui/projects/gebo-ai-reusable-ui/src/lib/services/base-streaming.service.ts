import { getAuthHeader } from "../infrastructure/gebo-credentials";
import { IGeboChatMessage } from "./gebo-chat-message";

export class GeboAIBaseStreamingService {
    /**
         * Core implementation of the streaming chat functionality.
         * Uses the Fetch API with streaming to process server-sent events in real-time.
         * 
         * @param apiUrl - The endpoint URL to connect to
         * @param request - The chat request to send
         * @param onMessage - Callback function for handling received messages
         * @param onError - Optional callback function for handling errors
         */
        protected internalStreamChat(apiUrl: string, request: any, onMessage: (msg: IGeboChatMessage | string) => void, onError?: (err: any) => void): void {
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
                                            const msgObject: any = JSON.parse(json);
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