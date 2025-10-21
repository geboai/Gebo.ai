import { AfterContentInit, Directive, ElementRef, Inject, Optional } from "@angular/core";
import { findMatchingTranlations, findMatchingTranslation, UIExistingText } from "./text-language-resources";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "./gebo-translation.service";

@Directive({
    selector: '[gebo-ai-label]',
    standalone: false
})
export class GeboAILabelDirective implements AfterContentInit {
    constructor(
        private el: ElementRef<HTMLElement>,
        private translationService: GeboAITranslationService,
        @Optional() @Inject(GEBO_AI_MODULE) private moduleId?: string,
        @Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) {
        if (this.host) {
            if (Array.isArray(this.host)) {
                const array = Array.from(this.host);
                this.host = array.length ? array[array.length - 1] : undefined;
            }
        }
        if (this.moduleId && this.moduleId.length) {
            this.moduleId = this.moduleId[this.moduleId.length - 1];
        }
    }
    private componentId: string | undefined | null;
    private existingTexts: UIExistingText[] = [];
    ngAfterContentInit(): void {
        this.componentId = this.el.nativeElement.getAttribute("id");
        this.checkLabelAttribute("label", "label");
        this.checkLabelAttribute("legend", "label");
        this.checkLabelAttribute("header", "label");
        this.checkLabelAttribute("title", "help");
        this.checkLabelAttribute("placeholder", "placeholder");
        const substitutions = this.translationService.translateOnActualLanguage(this.existingTexts);
        if (substitutions) {
            substitutions.subscribe({
                next: (resources) => {
                    if (resources) {
                        this.existingTexts.forEach(textRc => {
                            const matchText = findMatchingTranslation(textRc, resources);
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
                    moduleId: this.moduleId,
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