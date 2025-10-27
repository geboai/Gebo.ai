import { Component, forwardRef, OnInit } from "@angular/core";
import { GeboAITranslationService } from "./gebo-translation.service";
import { ControlValueAccessor, FormControl, FormGroup, NG_VALUE_ACCESSOR, Validators } from "@angular/forms";
interface Language { langCode: string, description: string };
@Component({
    selector: "gebo-ai-language-choice-component",
    templateUrl: "language-choice.component.html",
    styleUrl: "language-choice.component.css",
    standalone: false,
    providers: [{
        provide: NG_VALUE_ACCESSOR,
        useExisting: forwardRef(() => GeboAILanguageChoiceComponent),
        multi: true
    }]
})
export class GeboAILanguageChoiceComponent implements OnInit, ControlValueAccessor {
    private currentLanguage: string = "en";
    private languages: Language[] = [
        { "langCode": "en", "description": "English" },
        { "langCode": "it", "description": "Italiano" },
        { "langCode": "es", "description": "Español" },
        { "langCode": "fr", "description": "Français" },
        { "langCode": "de", "description": "Deutsch" },
        { "langCode": "pt", "description": "Português" },
        { "langCode": "nl", "description": "Nederlands" },
        { "langCode": "pl", "description": "Polski" },
        { "langCode": "ru", "description": "Русский" },
        { "langCode": "zh", "description": "中文" },
        { "langCode": "ja", "description": "日本語" },
        { "langCode": "ko", "description": "한국어" },
        { "langCode": "ar", "description": "العربية" },
        { "langCode": "tr", "description": "Türkçe" },
        { "langCode": "hi", "description": "हिन्दी" },
        { "langCode": "id", "description": "Bahasa Indonesia" },
        { "langCode": "vi", "description": "Tiếng Việt" },
        { "langCode": "sv", "description": "Svenska" },
        { "langCode": "no", "description": "Norsk" },
        { "langCode": "da", "description": "Dansk" },
        { "langCode": "fi", "description": "Suomi" },
        { "langCode": "el", "description": "Ελληνικά" },
        { "langCode": "cs", "description": "Čeština" },
        { "langCode": "hu", "description": "Magyar" },
        { "langCode": "ro", "description": "Română" },
        { "langCode": "he", "description": "עברית" },
        { "langCode": "th", "description": "ไทย" },
        { "langCode": "uk", "description": "Українська" }
    ];
    protected choosableLanguages: Language[] = [];
    protected formGroup: FormGroup = new FormGroup({
        langCode: new FormControl()
    });
    constructor(private geboTranslationService: GeboAITranslationService) {
        this.formGroup.controls["langCode"].setValidators(Validators.required);
    }
  async  ngOnInit(){
        await this.geboTranslationService.tryInit();
        const langs = this.geboTranslationService.getLanguages();
        this.choosableLanguages = this.languages.filter(x => langs.includes(x.langCode));
        this.formGroup.controls["langCode"].valueChanges.subscribe({
            next: (data) => {
                if (data) {
                    this.onChange(data);
                }
            }
        });
    }
    writeValue(obj: any): void {
        this.currentLanguage = obj;
    }
    private onChange: (fn: any) => void = (fn: any) => { };
    registerOnChange(fn: any): void {
        this.onChange = fn;
    }
    private onTouched: (fn: any) => void = (fn: any) => { };
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }
    setDisabledState?(isDisabled: boolean): void {

    }

}