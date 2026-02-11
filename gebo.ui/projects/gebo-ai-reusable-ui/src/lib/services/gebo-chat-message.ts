/**
 * Interface representing a message received from the Gebo Chat API.
 * Messages can contain different types of content and indicate if they're the last in a sequence.
 */

export interface IGeboChatMessage {
    content?: any; // The actual content of the message
    contentObjectType?: string; // Type descriptor for the content
    lastMessage?: boolean; // Flag indicating if this is the final message in a sequence
}
;
