import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, findMatchingTranlations, UIExistingText } from "./text-language-resources";
import { Observable, of, Subject, concat } from "rxjs";
import { UiTextResourcesControllerService, UserControllerService, UIExistingText as LibraryUIExistingText } from "@Gebo.ai/gebo-ai-rest-api";
import { InterpolatableTranslationObject, TranslateService } from "@ngx-translate/core";

/*******************************************************************************
 * The actual UI resources are all written in english, used as a default language
 * resources source, once the language is changed this service will publish all
 * the translations and respond to immediate text resources lookups (translateOnActualLanguage)
 */
@Injectable({
    providedIn: "platform"
})
export class GeboAITranslationService {
    private static actualLanguage: string = DEFAULT_LANGUAGE;
    public static recordingOn: boolean = false;
    private static currentTextResources: UIExistingText[] = [];
    constructor(private uiTextResourcesService: UiTextResourcesControllerService,
        private userController: UserControllerService,
        private translateService: TranslateService) {
        this.userController.getCurrentUser().subscribe({
            next: (identity) => {
                const checkOnRegisteringMode: boolean = identity?.roles?.find(x => x === "ADMIN" || x === "ADMIN_ROLE") ? true : false;
                if (checkOnRegisteringMode === true) {
                    uiTextResourcesService.getUiTextResourcesModule().subscribe({
                        next: (value) => {
                            GeboAITranslationService.recordingOn = value?.enabled === true;
                        }
                    })
                }
            }
        });
        this.translateService.addLangs(["en","it","zh","ru"])
    }

    public languageChanges: Subject<UIExistingText[]> = new Subject();
    public translateOnActualLanguage(textResources: UIExistingText[]): Observable<UIExistingText[]> {
        if (GeboAITranslationService.recordingOn === true && textResources && textResources.length) {
            const filtered: UIExistingText[] = textResources.filter(x => x.moduleId && x.entityId && x.componentId && x.fieldId);
            if (filtered && filtered.length) {
                this.uiTextResourcesService.updateUIExistingTexts(filtered as LibraryUIExistingText[]).subscribe({
                    next: (data) => {

                    }
                });
            }
        }
        if (textResources) {
            textResources.forEach(x => {
                console.log(JSON.stringify(x));
            });
        }
        let texts: UIExistingText[] | undefined = [];
        if (GeboAITranslationService.currentTextResources && GeboAITranslationService.currentTextResources.length) {
            texts = findMatchingTranlations(textResources, GeboAITranslationService.currentTextResources);
        }
        return concat(of(texts ? texts : []), this.languageChanges);
    }
    public changeActualLanguage(language: string): void {
        GeboAITranslationService.actualLanguage = language;
        this.translateService.use(language).subscribe({
            next: (interpolationTranslateObject: InterpolatableTranslationObject) => {
                const moduleIds = Object.keys(interpolationTranslateObject);
                const currentResources: UIExistingText[] = [];
                moduleIds.forEach(moduleId => {
                    const module = interpolationTranslateObject[moduleId];
                    if (module) {
                        const entityIds = Object.keys(module);
                        entityIds.forEach(entityId => {
                            const entity = (module as any)[entityId];
                            if (entity) {
                                const componentsIds = Object.keys(entity);
                                componentsIds.forEach(componentId => {
                                    const component = entity[componentId];
                                    if (component) {
                                        const fields = Object.keys(component);
                                        fields.forEach(fieldId => {
                                            if (fieldId)
                                                currentResources.push({
                                                    moduleId: moduleId,
                                                    entityId: entityId,
                                                    componentId: componentId,
                                                    fieldId: fieldId,
                                                    key: "",
                                                    text: "",
                                                    translation: component[fieldId]
                                                });
                                        });
                                    }
                                })
                            }

                        });
                    }
                });
                GeboAITranslationService.currentTextResources = currentResources;
                this.languageChanges.next(GeboAITranslationService.currentTextResources);
            }
        })

    }
    public download(): Observable<Blob> {
        return this.uiTextResourcesService.getI18n();
    }
    public getLanguages(): readonly string[] {
        const langs = this.translateService.getLangs();
        return langs;
    }
}