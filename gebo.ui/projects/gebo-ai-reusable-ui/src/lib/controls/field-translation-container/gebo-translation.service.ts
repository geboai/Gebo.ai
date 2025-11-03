import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, UIExistingText, UILanguageResources } from "./text-language-resources";
import { Observable, of, Subject, concat } from "rxjs";
import { UiTextResourcesControllerService, UIExistingText as LibraryUIExistingText } from "@Gebo.ai/gebo-ai-rest-api";
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
    private static initialized: boolean = false;
    private static currentTextResources: UILanguageResources | undefined;
    constructor(private uiTextResourcesService: UiTextResourcesControllerService,
        private translateService: TranslateService) {
        this.translateService.addLangs(["ar","cs","de","en","es","fi","fr","he","hi","it","js","ko","nl","no","pt","ro","ru","sv","th","tr","vi","zh"])
    }
    public async tryInit() {
        if (GeboAITranslationService.initialized !== true) {
            try {
                const moduleConfiguration = await this.uiTextResourcesService.getUiTextResourcesModule().toPromise();
                GeboAITranslationService.recordingOn = moduleConfiguration?.enabled === true;
            } catch (e) {

            } finally {
                GeboAITranslationService.initialized = true;
            }
        }
    }
    public languageChanges: Subject<UILanguageResources | undefined> = new Subject();
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