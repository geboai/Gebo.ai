import { Injectable } from "@angular/core";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "@Gebo.ai/reusable-ui";
import { MessageService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map } from "rxjs";

@Injectable({
    providedIn: "root"
    
})
export class GeboAIRootNotificationService {
    constructor(private messageService: MessageService, private geboAiTranslationService: GeboAITranslationService) {
    }

    public addMessage(moduleId: string, entityId: string,  msg: GUserMessage): void {
        const toast: ToastMessageOptions = {
            detail: msg.detail,
            id: msg.id,
            severity: msg.severity,
            summary: msg.summary,
            text: msg.detail,
            closable: true,
            sticky: true
        };
        this.geboAiTranslationService.translateMessage(moduleId, entityId, msg.id, msg).subscribe({
            next: (data) => {
                this.messageService.add(data ? data : toast);
            }
        });
    }
    public addMessages(moduleId: string, entityId: string,  msg: GUserMessage[]) {
        const observables=msg.map(x=>this.geboAiTranslationService.translateMessage(moduleId,entityId,x.id,x).pipe(map(r=>r?r:x)));
        forkJoin(observables).subscribe({
            next:(tmsgs)=>{
                this.messageService.addAll(tmsgs);
            }
        })
    }

}