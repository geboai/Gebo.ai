import { Injectable } from "@angular/core";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { MessageService, ToastMessageOptions } from "primeng/api";

@Injectable({
    providedIn: "root"
})
export class GeboAIRootNotificationService {
    constructor(private messageService: MessageService, private geboAiTranslationService: GeboAITranslationService) {
    }

    public addMessage(moduleId: string, entityId: string, componentId: string, msg: GUserMessage): void {
        const toast: ToastMessageOptions = {
            detail: msg.detail,
            id: msg.id,
            severity: msg.severity,
            summary: msg.summary,
            text: msg.detail,
            closable: true,
            sticky: true
        };
        this.geboAiTranslationService.translateMessage(moduleId, entityId, componentId, msg).subscribe({
            next: (data) => {
                this.messageService.add(data ? data : toast);
            }
        });
    }
    public addMessages(moduleId: string, entityId: string, componentId: string, msg: GUserMessage[]) {
        msg.forEach(m => {
            this.addMessage(moduleId, entityId, componentId, m);
        })
    }

}