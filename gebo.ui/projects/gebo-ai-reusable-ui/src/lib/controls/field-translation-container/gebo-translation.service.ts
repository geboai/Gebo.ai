import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, UIExistingText, UILanguageResources } from "./text-language-resources";
import { Observable, of, Subject, concat, map } from "rxjs";
import { UiTextResourcesControllerService, UIExistingText as LibraryUIExistingText } from "@Gebo.ai/gebo-ai-rest-api";
import { InterpolatableTranslationObject, Language, TranslateService } from "@ngx-translate/core";
import { ToastMessageOptions } from "primeng/api";
import { findMatchingTranlations } from "./text-language-resources";

import { HttpClient } from "@angular/common/http";
import { GeboLanguage } from "./language-choice.component";

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
    public get browserLanguage():Language|undefined {
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
    public translateText(moduleId: string,entityId:string, componentId: string,resources:{fieldId:string,text:string}[]):Observable<{fieldId:string,translation:string}[]|undefined> {
        const data:UIExistingText[]=[];
        if (resources && resources.length) {
            resources.forEach(x=>{
                data.push({
                    moduleId:moduleId,
                    entityId:entityId,
                    componentId:componentId,
                    fieldId: x.fieldId,
                    key:x.fieldId,
                    text:x.text
                });
            });            
        }
        if (data && data.length) {
            return of(undefined);
        }else {
            return this.translateOnActualLanguage(data).pipe(map(rc=>{
                const outData:{fieldId:string,translation:string}[]=[];
                if (rc) {
                    const translations=findMatchingTranlations(data,rc);
                    if (translations && translations.length) {
                        translations.forEach(x=>{
                            if (x.translation) {
                                outData.push({
                                    fieldId:x.fieldId,
                                    translation:x.translation
                                });
                            }
                        });
                    }
                }
                return outData;
            }));
        }
    }
    public translateMessage(moduleId: string, componentId: string, option: ToastMessageOptions): Observable<ToastMessageOptions | undefined> {
        const textResource: UIExistingText[] = [];
        if (option.summary) {
            const summary: UIExistingText = {
                moduleId: moduleId,
                componentId: componentId,
                entityId: "message",
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
                entityId: "message",
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