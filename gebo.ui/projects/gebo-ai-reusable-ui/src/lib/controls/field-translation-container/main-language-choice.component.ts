import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboAITranslationService } from "./gebo-translation.service";
import { fieldHostComponentName, GEBO_AI_FIELD_HOST, GEBO_AI_MODULE } from "@Gebo.ai/reusable-ui";
import { MegaMenuItem } from "primeng/api";
const GEBO_AI_LOCAL_STORAGE_LANG: string = "gebo.ai.lang";
interface LanguageSetting {
    langCode: string;
}
@Component({
    selector: "gebo-ai-main-language-choice",
    templateUrl: "main-language-choice.component.html",
    styleUrl: "language-choice.component.css",
    standalone: false,
    providers: [
        { provide: GEBO_AI_MODULE, useValue: "GeboAIMultilanguageModule", multi: false },
        { provide: GEBO_AI_FIELD_HOST, useValue: fieldHostComponentName("GeboAIMainLanguageChoiceComponent"), multi: false }
    ]
})
export class GeboAIMainLanguageChoiceComponent implements OnInit, OnChanges {
    formGroup: FormGroup = new FormGroup({
        langCode: new FormControl()
    });
    protected currentLanguageCodeIcon: string = "pi pi-question-circle";
    protected currentLanguageCode: string = "en";
    @Input() menuItem?: MegaMenuItem;
    @Input() appendTo?: any;
    @Output() languageChange: EventEmitter<string> = new EventEmitter();
    @ViewChild("languageChoice") languageChoice: any;
    constructor(private geboTranslationService: GeboAITranslationService) {

    }
    ngOnChanges(changes: SimpleChanges): void {
        if ((this.menuItem && changes["menuItem"]) || (this.appendTo && changes["appendTo"])) {
            if (this.menuItem) {
                this.menuItem.icon = this.currentLanguageCodeIcon;
                this.menuItem.command = (evt) => {
                    this.languageChoice?.toggle(evt);
                };
            }

        }
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
        this.currentLanguageCodeIcon = language?.langCode + '-flag';
        this.currentLanguageCode = language?.langCode;
        if (this.menuItem) {
            this.menuItem.icon = this.currentLanguageCodeIcon;
        }
        this.languageChange.emit(this.currentLanguageCodeIcon);
        this.formGroup.setValue(language);
        if (language?.langCode && language?.langCode !== this.geboTranslationService.getActualLanguage()) {
            this.geboTranslationService.changeActualLanguage(language.langCode);
        }
        this.formGroup.valueChanges.subscribe({
            next: (data: LanguageSetting) => {
                const langCode = data?.langCode;
                if (langCode) {
                    this.currentLanguageCodeIcon = langCode + '-flag';
                    this.currentLanguageCode = langCode;
                    if (this.menuItem) {
                        this.menuItem.icon = this.currentLanguageCodeIcon;
                    }
                    
                    this.geboTranslationService.changeActualLanguage(langCode);
                    localStorage.setItem(GEBO_AI_LOCAL_STORAGE_LANG, JSON.stringify(data));
                    this.languageChange.emit(this.currentLanguageCode);
                }
            }
        });
    }


}