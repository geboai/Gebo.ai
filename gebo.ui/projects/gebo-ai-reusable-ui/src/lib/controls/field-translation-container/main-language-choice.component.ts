import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboAITranslationService } from "./gebo-translation.service";
const GEBO_AI_LOCAL_STORAGE_LANG: string = "gebo.ai.lang";
interface LanguageSetting {
    langCode: string;
}
@Component({
    selector: "gebo-ai-main-language-choice",
    template: "<span [formGroup]='formGroup'><gebo-ai-language-choice-component formControlName='langCode'/></span>",
    standalone: false
})
export class GeboAIMainLanguageChoiceComponent implements OnInit {
    formGroup: FormGroup = new FormGroup({
        langCode: new FormControl()
    });
    constructor(private geboTranslationService: GeboAITranslationService) {

    }

    async ngOnInit() {
        await this.geboTranslationService.tryInit();
        let actualLanguage = "en";
        let language: LanguageSetting = {
            langCode: actualLanguage
        };
        const savedValue: string | null = localStorage.getItem(GEBO_AI_LOCAL_STORAGE_LANG);
        if (savedValue) {
            language = JSON.parse(savedValue);
        } else {
            let browserLanguage = this.geboTranslationService.browserLanguage;
            if (browserLanguage && browserLanguage.length > 2) {
                browserLanguage = browserLanguage.substring(0, 2);
            }
            if (browserLanguage) {
                browserLanguage = browserLanguage.toLowerCase();
            }
            const languages = this.geboTranslationService.languagesList;
            if (languages && languages.length && browserLanguage && languages.find(x => x.langCode === browserLanguage)) {
                language = {
                    langCode: browserLanguage
                };
            }
        }
        this.formGroup.setValue(language);
        if (language?.langCode && language?.langCode !== this.geboTranslationService.getActualLanguage()) {
            this.geboTranslationService.changeActualLanguage(language.langCode);
        }
        this.formGroup.valueChanges.subscribe({
            next: (data: LanguageSetting) => {
                const langCode = data?.langCode;
                if (langCode) {
                    this.geboTranslationService.changeActualLanguage(langCode);
                    localStorage.setItem(GEBO_AI_LOCAL_STORAGE_LANG, JSON.stringify(data));
                }
            }
        });
    }


}