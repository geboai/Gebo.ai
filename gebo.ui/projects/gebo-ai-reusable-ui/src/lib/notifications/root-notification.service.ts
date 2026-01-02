import { Injectable } from "@angular/core";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";

import {  MessageService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map } from "rxjs";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";

@Injectable({
    providedIn: "root"
    
})
export class GeboAIRootNotificationService {
    
    constructor(private messageService: MessageService, private geboAiTranslationService: GeboAITranslationService) {
    }

    public addMessage(moduleId: string, entityId: string,  msg: GUserMessage|ToastMessageOptions): void {
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
                const tmsg=data ? data : toast;
                tmsg.key="global";
                this.messageService.add(tmsg);
            }
        });
    }
    public addMessages(moduleId: string, entityId: string,  msg: GUserMessage[]) {
        const observables=msg.map(x=>this.geboAiTranslationService.translateMessage(moduleId,entityId,x.id,x).pipe(map(r=>{
            const retMsg=r?r:x;
            retMsg.key="global";
            return retMsg;
        })));
        forkJoin(observables).subscribe({
            next:(tmsgs)=>{
                this.messageService.addAll(tmsgs);
            }
        })
    }

}