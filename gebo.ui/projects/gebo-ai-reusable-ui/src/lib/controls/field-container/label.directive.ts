import { AfterContentInit, Directive, ElementRef, Inject, Optional } from "@angular/core";
import { findMatchingTranlations, findMatchingTranslation, UIExistingText } from "./text-language-resources";
import { GEBO_AI_FIELD_HOST, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "./gebo-translation.service";

@Directive({
    selector: '[gebo-ai-label]',
    standalone: false
})
export class GeboAILabelDirective implements AfterContentInit {
    constructor(
        private el: ElementRef<HTMLElement>,
        private translationService: GeboAITranslationService,
        @Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) {
    }
    private componentId?: string;
    private existingTexts: UIExistingText[] = [];
    ngAfterContentInit(): void {
        this.checkLabelAttribute("label", "label");
        this.checkLabelAttribute("legend", "label");
        this.checkLabelAttribute("title", "help");
        this.checkLabelAttribute("placeholder", "placeholder");
        const substitutions = this.translationService.translateOnActualLanguage(this.existingTexts);
        if (substitutions) {
            substitutions.subscribe({
                next: (resources) => {
                    const matching = findMatchingTranlations(this.existingTexts, resources);
                    if (matching && matching.length) {
                        this.existingTexts.forEach(textRc => {
                            const matchText = findMatchingTranslation(textRc, matching);
                            if (matchText && matchText.translation) {
                                this.el.nativeElement.setAttribute(textRc.key, matchText.translation);
                            }
                        });
                    }
                }
            });
        }
    }
    private checkLabelAttribute(labelFieldName: string, alias: string): void {

        const value = this.el.nativeElement.getAttribute(labelFieldName);
        if (value && value.length > 0) {
            if (!this.existingTexts.find(x => x.fieldId === alias)) {
                this.existingTexts.push({
                    entityId: this.host?.getEntityName(),
                    componentId: this.componentId,
                    key: labelFieldName,
                    fieldId: alias,
                    text: value
                });
            }
        }
    }
}