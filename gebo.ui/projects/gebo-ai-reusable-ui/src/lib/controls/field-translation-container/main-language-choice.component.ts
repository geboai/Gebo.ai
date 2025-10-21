import { Component, OnInit } from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { GeboAITranslationService } from "./gebo-translation.service";

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
    ngOnInit(): void {
        const actualLanguage = "en";
        this.formGroup.setValue({ langCode: actualLanguage });
        this.formGroup.valueChanges.subscribe({
            next: (data) => {
                const langCode = data?.langCode;
                if (langCode) {
                    this.geboTranslationService.changeActualLanguage(langCode);
                }
            }
        });
    }


}