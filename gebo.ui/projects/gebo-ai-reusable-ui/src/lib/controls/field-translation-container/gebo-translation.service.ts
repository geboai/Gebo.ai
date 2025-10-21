import { Injectable } from "@angular/core";
import { DEFAULT_LANGUAGE, findMatchingTranlations, UIExistingText, UILanguageResources } from "./text-language-resources";
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
    private static currentTextResources: UILanguageResources | undefined;
    constructor(private uiTextResourcesService: UiTextResourcesControllerService,
        private userController: UserControllerService,
        private translateService: TranslateService) {

        uiTextResourcesService.getUiTextResourcesModule().subscribe({
            next: (value) => {
                GeboAITranslationService.recordingOn = value?.enabled === true;
            }
        });



        this.translateService.addLangs(["en", "it", "zh", "ru"])
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
    public download(): Observable<Blob> {
        return this.uiTextResourcesService.getI18n();
    }
    public getLanguages(): readonly string[] {
        const langs = this.translateService.getLangs();
        return langs;
    }
}