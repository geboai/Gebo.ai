import { Injectable } from "@angular/core";
import { GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";

import { MessageService, ToastMessageOptions } from "primeng/api";
import { forkJoin, map } from "rxjs";
import { GeboAITranslationService } from "../controls/field-translation-container/gebo-translation.service";
import { ToastZIndexService } from "./toast-z-index.service";

@Injectable({
    providedIn: "root"

})
export class GeboAIRootNotificationService {

    constructor(private messageService: MessageService, 
        private geboAiTranslationService: GeboAITranslationService, 
        private zIndexService: ToastZIndexService) {
    }

    public addMessage(moduleId: string, entityId: string, msg: GUserMessage | ToastMessageOptions): void {
        const toast: ToastMessageOptions = {
            detail: msg.detail,
            id: msg.id,
            severity: msg.severity,
            summary: msg.summary,
            text: msg.detail,
            life: 10000
        };
        this.geboAiTranslationService.translateMessage(moduleId, entityId, msg.id, toast).subscribe({
            next: (data) => {
                const tmsg = data ? data : toast;
                tmsg.key = "global";
                this.messageService.add(tmsg);
            }
        });
    }
    public addMessages(moduleId: string, entityId: string, msg: GUserMessage[]) {
        if (msg && msg.length) {
            const observables = msg.map(x => this.geboAiTranslationService.translateMessage(moduleId, entityId, x.id, x).pipe(map(r => {
                const retMsg = r ? r : x;
                const toast: ToastMessageOptions = {
                    detail: retMsg.detail,
                    id: retMsg.id,
                    severity: retMsg.severity,
                    summary: retMsg.summary,
                    key: "global",
                    life: 10000
                };
                return toast;
            })));
            if (observables && observables.length === 1) {
                observables[0].subscribe({
                    next: (tmsgs) => {
                        this.zIndexService.bumpAboveOverlays();
                        this.messageService.add(tmsgs);
                    }
                });
            } else {
                forkJoin(observables).subscribe({
                    next: (tmsgs) => {
                        this.zIndexService.bumpAboveOverlays();
                        this.messageService.addAll(tmsgs);
                    },
                    error: (err) => {
                        this.messageService.addAll(msg);
                    }
                });
            }
        }
    }

}