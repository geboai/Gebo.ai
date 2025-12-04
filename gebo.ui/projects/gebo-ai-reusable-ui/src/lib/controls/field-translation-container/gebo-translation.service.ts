import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, UIExistingText, UILanguageResources } from "./text-language-resources";
import { Observable, of, Subject, concat, map } from "rxjs";
import { UiTextResourcesControllerService, UIExistingText as LibraryUIExistingText, GUserMessage } from "@Gebo.ai/gebo-ai-rest-api";
import { InterpolatableTranslationObject, Language, TranslateService } from "@ngx-translate/core";
import { Confirmation, MegaMenuItem, MenuItem, ToastMessageOptions } from "primeng/api";
import { findMatchingTranlations } from "./text-language-resources";

import { HttpClient } from "@angular/common/http";
import { GeboLanguage } from "./language-choice.component";

export interface ExtendedConfirmation extends Confirmation {
    id: string;
}
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
    private static initialized: boolean = false;
    private static currentTextResources: UILanguageResources | undefined;
    private static languageChoices: GeboLanguage[] = [];
    constructor(private uiTextResourcesService: UiTextResourcesControllerService,
        private translateService: TranslateService,
        private httpClient: HttpClient) {
        this.translateService.addLangs(["ar", "cs", "de", "en", "es", "fi", "fr", "he", "hi", "it", "js", "ko", "nl", "no", "pt", "ro", "ru", "sv", "th", "tr", "vi", "zh"]);
    }
    protected get assetsUrl(): string {
        let host = document.location.hostname;
        let port = document.location.port;
        let protocol = document.location.protocol;
        return protocol + "//" + host + (port ? ":" + port : "") + "/assets/";
    }
    private loadLanguagesResource(fileName: string): Observable<GeboLanguage[]> {
        const url = this.assetsUrl + fileName;
        return this.httpClient.get<GeboLanguage[]>(url).pipe(map(data => data as GeboLanguage[]));
    }
    public get browserLanguage(): Language | undefined {
        return this.translateService.getBrowserLang();
    }
    public async tryInit() {
        if (GeboAITranslationService.initialized !== true) {
            try {
                const moduleConfiguration = await this.uiTextResourcesService.getUiTextResourcesModule().toPromise();
                GeboAITranslationService.recordingOn = moduleConfiguration?.enabled === true;

                try {
                    const langs = await this.loadLanguagesResource("languages-choice.json").toPromise();
                    if (langs && langs.length) {
                        GeboAITranslationService.languageChoices = langs;
                        this.translateService.addLangs(langs.map(x => x.langCode));
                    }
                } catch (w) {

                }
            } catch (e) {

            } finally {
                GeboAITranslationService.initialized = true;
            }
        }
    }
    public languageChanges: Subject<UILanguageResources | undefined> = new Subject();
    public get languagesList(): GeboLanguage[] {
        return GeboAITranslationService.languageChoices;
    }
    public translateOnActualLanguage(textResources: UIExistingText[]): Observable<UILanguageResources | undefined> {
        if (GeboAITranslationService.recordingOn === true && textResources && textResources.length) {
            const filtered: UIExistingText[] = textResources.filter(x => x.moduleId && x.entityId && x.componentId && x.fieldId);
            if (filtered && filtered.length) {
                this.uiTextResourcesService.updateUIExistingTexts(filtered as LibraryUIExistingText[]).subscribe({
                    next: (data) => {

                    }
                });
            }
        }


        return concat(of(GeboAITranslationService.currentTextResources), this.languageChanges);
    }
    public translateConfirmation(moduleId: string, entityId: string, confirmation: ExtendedConfirmation): Observable<ExtendedConfirmation|undefined> {
        const data: UIExistingText[] = [];
        if (confirmation.message) {
            data.push({
                moduleId: moduleId,
                entityId: entityId,
                componentId: confirmation.id,
                fieldId: "message",
                key: "message",
                text: confirmation.message
            });
        }
        if (confirmation.header) {
            data.push({
                moduleId: moduleId,
                entityId: entityId,
                componentId: confirmation.id,
                fieldId: "header",
                key: "header",
                text: confirmation.header
            });
        }
         if (confirmation.acceptLabel) {
            data.push({
                moduleId: moduleId,
                entityId: entityId,
                componentId: confirmation.id,
                fieldId: "acceptLabel",
                key: "acceptLabel",
                text: confirmation.acceptLabel
            });
        }
        if (confirmation.rejectLabel) {
            data.push({
                moduleId: moduleId,
                entityId: entityId,
                componentId: confirmation.id,
                fieldId: "rejectLabel",
                key: "rejectLabel",
                text: confirmation.rejectLabel
            });
        }
        return this.translateOnActualLanguage(data).pipe(map(resources=>{
            if (resources) {
                const translations=findMatchingTranlations(data,resources);
                if (translations) {
                    translations.forEach(entry=>{
                        (confirmation as any)[entry.fieldId]=entry.translation;
                    });
                }
                return confirmation;
            }else return undefined;

        }));
    
    }
    public translateText(moduleId: string, entityId: string, componentId: string, resources: { fieldId: string, text: string }[]): Observable<{ fieldId: string, translation: string }[] | undefined> {
        const data: UIExistingText[] = [];
        if (resources && resources.length) {
            resources.forEach(x => {
                data.push({
                    moduleId: moduleId,
                    entityId: entityId,
                    componentId: componentId,
                    fieldId: x.fieldId,
                    key: x.fieldId,
                    text: x.text
                });
            });
        }
        if (data && data.length) {
            return of(undefined);
        } else {
            return this.translateOnActualLanguage(data).pipe(map(rc => {
                const outData: { fieldId: string, translation: string }[] = [];
                if (rc) {
                    const translations = findMatchingTranlations(data, rc);
                    if (translations && translations.length) {
                        translations.forEach(x => {
                            if (x.translation) {
                                outData.push({
                                    fieldId: x.fieldId,
                                    translation: x.translation
                                });
                            }
                        });
                    }
                }
                return outData;
            }));
        }
    }
    public translateBackendMessage(message: GUserMessage): Observable<GUserMessage | undefined> {
        return this.translateBackendMessages([message]).pipe(map(x => x && x.length ? x[0] : undefined));
    }
    private declareBackendMessage(componentId: string, fieldId: string, text: string): UIExistingText {
        const data: UIExistingText = {
            moduleId: "BackendMSGSModule",
            entityId: "GUserMessage",
            componentId: componentId,
            fieldId: fieldId,
            key: fieldId,
            text: text
        };
        return data;
    }
    public translateBackendMessages(messages: GUserMessage[]): Observable<GUserMessage[] | undefined> {
        const textResource: UIExistingText[] = [];
        if (messages && messages.length) {
            messages.forEach(msg => {
                if (msg.summary) {
                    textResource.push(this.declareBackendMessage(msg.id, "summary", msg.summary));
                }
                if (msg.detail) {
                    textResource.push(this.declareBackendMessage(msg.id, "detail", msg.detail));
                }
            });
        }
        if (textResource && textResource.length) {
            return this.translateOnActualLanguage(textResource).pipe(map(resources => {
                if (resources) {
                    const matching = findMatchingTranlations(textResource, resources);
                    const clonedMessages: GUserMessage[] = [...messages];
                    if (matching && matching.length) {
                        matching.forEach(match => {
                            const found: any = clonedMessages.find(x => x.id === match.componentId);
                            if (found) {
                                found[match.fieldId] = match.translation;
                            }
                        });
                    }
                    return clonedMessages;
                } else { return undefined; }
            }));
        } else {
            return of(undefined);
        }

    }
    public translateMessage(moduleId: string, entityId: string, componentId: string, option: ToastMessageOptions): Observable<ToastMessageOptions | undefined> {
        const textResource: UIExistingText[] = [];
        if (option.summary) {
            const summary: UIExistingText = {
                moduleId: moduleId,
                componentId: componentId,
                entityId: entityId,
                fieldId: "summary",
                key: "summary",
                text: option.summary
            };
            textResource.push(summary);
        }
        if (option.detail) {
            const detail: UIExistingText = {
                moduleId: moduleId,
                componentId: componentId,
                entityId: entityId,
                fieldId: "detail",
                key: "detail",
                text: option.detail
            };
            textResource.push(detail);
        }
        if (textResource && textResource.length) {
            const observable = this.translateOnActualLanguage(textResource);
            return observable.pipe(map(data => {
                if (data) {
                    const matchings = findMatchingTranlations(textResource, data);
                    if (matchings && matchings.length) {
                        const outOption: ToastMessageOptions = {
                            ...option
                        };
                        matchings.filter(d => d.translation).forEach(t => {
                            (outOption as any)[t.key] = t.translation;
                        });
                        return outOption;
                    }
                }
                return option;

            }));
        } else return of(undefined);
    }
    public changeActualLanguage(language: string): void {
        GeboAITranslationService.actualLanguage = language;
        this.translateService.use(language).subscribe({
            next: (interpolationTranslateObject: InterpolatableTranslationObject) => {

                GeboAITranslationService.currentTextResources = interpolationTranslateObject as (UILanguageResources | undefined);
                if (GeboAITranslationService.currentTextResources)
                    this.languageChanges.next(GeboAITranslationService.currentTextResources);
            }
        })

    }
    public translateMegaMenuItems(moduleId: string, entityId: string, menuItems: MegaMenuItem[]): Observable<MegaMenuItem[]> {
        const resources: UIExistingText[] = [];
        if (menuItems) {
            menuItems.forEach(x => {
                if (x.label && x.id) {
                    resources.push({
                        moduleId: moduleId,
                        entityId: entityId,
                        componentId: x.id,
                        fieldId: "label",
                        key: "label",
                        text: x.label
                    });
                }
                if (x.title && x.id) {
                    resources.push({
                        moduleId: moduleId,
                        entityId: entityId,
                        componentId: x.id,
                        fieldId: "help",
                        key: "help",
                        text: x.title
                    });
                }

            });
        }
        return this.translateOnActualLanguage(resources).pipe(map(languageResource => {
            if (languageResource) {
                const translations = findMatchingTranlations(resources, languageResource);
                if (translations && translations.length) {
                    menuItems.forEach(menuEntry => {

                        const found = translations.filter(translation => menuEntry.id === translation.componentId);
                        if (found) {
                            found.forEach(fieldTranslation => {
                                if (fieldTranslation.translation)
                                    (menuEntry as any)[fieldTranslation.fieldId] = fieldTranslation.translation;
                            });
                        }

                    });
                }
            }
            return menuItems;
        }));
    }
    public translateMenuItems(moduleId: string, entityId: string, menuItems: MenuItem[]): Observable<MenuItem[]> {
        const resources: UIExistingText[] = [];
        if (menuItems) {
            menuItems.forEach(x => {
                if (x.label && x.id) {
                    resources.push({
                        moduleId: moduleId,
                        entityId: entityId,
                        componentId: x.id,
                        fieldId: "label",
                        key: "label",
                        text: x.label
                    });
                }
                if (x.title && x.id) {
                    resources.push({
                        moduleId: moduleId,
                        entityId: entityId,
                        componentId: x.id,
                        fieldId: "help",
                        key: "help",
                        text: x.title
                    });
                }
                if (x.tooltip && x.id) {
                    resources.push({
                        moduleId: moduleId,
                        entityId: entityId,
                        componentId: x.id,
                        fieldId: "tooltip",
                        key: "tooltip",
                        text: x.tooltip
                    });
                }
            });
        }
        return this.translateOnActualLanguage(resources).pipe(map(languageResource => {
            if (languageResource) {
                const translations = findMatchingTranlations(resources, languageResource);
                if (translations && translations.length) {
                    menuItems.forEach(menuEntry => {

                        const found = translations.filter(translation => menuEntry.id === translation.componentId);
                        if (found) {
                            found.forEach(fieldTranslation => {
                                if (fieldTranslation.translation)
                                    (menuEntry as any)[fieldTranslation.fieldId] = fieldTranslation.translation;
                            });
                        }

                    });
                }
            }
            return menuItems;
        }));
    }
    public getActualLanguage(): string {
        return this.translateService.getCurrentLang();
    }
    public download(): Observable<Blob> {
        return this.uiTextResourcesService.getI18n();
    }
    public getLanguages(): readonly string[] {
        const langs = this.translateService.getLangs();
        return langs;
    }
}