import { Component } from "@angular/core";
import { BaseEntityEditingComponent } from "./base-entity-editing.component";
import { concatMap, map, Observable, of } from "rxjs";
import { DeletableStatus, GeboAngularFormGroupMetaInfoControllerService, SimpleGObjectRef } from "@Gebo.ai/gebo-ai-rest-api";
import { GeboAITranslationService } from "../field-translation-container/gebo-translation.service";


@Component({
    selector: "base-entity-auto-delete-component",
    template: "",
    standalone: false
})
export abstract class BaseEntityEditingComponentAutoDeleteCheck<RecordType extends { code?: string, description?: string }> extends BaseEntityEditingComponent<RecordType> {
    override canBeDeleted(value: RecordType): Observable<{ canBeDeleted: boolean; message: string; }> {
        if (value.code) {
            const service = this.injector.get(GeboAngularFormGroupMetaInfoControllerService);
            const simpleGObjectRef: SimpleGObjectRef = {
                code: value.code,
                simpleClassName: this.entityName
            };
            let observable: Observable<DeletableStatus> = service.checkDeletableBySimpleObjectRef(simpleGObjectRef);
            try {
                const languageService = this.injector.get(GeboAITranslationService);
                observable = observable.pipe(concatMap(deletableStatus => {
                    if (deletableStatus.userMessage)
                        return languageService.translateBackendMessage(deletableStatus.userMessage).pipe(map(message => {
                            const feedback: DeletableStatus = {
                                userMessage: message ? message : deletableStatus.userMessage,
                                deletable: deletableStatus.deletable
                            }
                            return feedback;
                        }));
                    else return of(deletableStatus);
                }));
            } catch (e) {

            }
            return observable.pipe(map(status => { return { canBeDeleted: status.deletable === true, message: status.userMessage?.summary ? status.userMessage?.summary : (status.deletable === true ? "You can delete" : "You CAN NOT delete") }; }));
        } else return of({ canBeDeleted: true, message: "" });
    }
}