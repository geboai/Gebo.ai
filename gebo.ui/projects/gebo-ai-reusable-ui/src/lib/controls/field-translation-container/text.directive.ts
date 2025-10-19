import { AfterContentInit, Directive, ElementRef, Inject, Optional } from "@angular/core";
import { GeboAITranslationService } from "./gebo-translation.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { findMatchingTranslation, UIExistingText } from "./text-language-resources";

@Directive({
    selector: '[gebo-ai-text]',
    standalone: false
})
export class GeboAITextDirective implements AfterContentInit {
    private componentId: string | null | undefined;
    private existingTexts?: UIExistingText;
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
    ngAfterContentInit(): void {
        this.componentId = this.el.nativeElement.getAttribute("id");
        const text = this.el.nativeElement.innerText;
        if (this.componentId && text) {
            this.existingTexts = {
                moduleId: this.moduleId,
                componentId: this.componentId,
                key: "label",
                fieldId: "label",
                text: text,
                entityId: this.host?.getEntityName()

            };
            const substitutions = this.translationService.translateOnActualLanguage([this.existingTexts]);
            if (substitutions) {
                substitutions.subscribe({
                    next: (resources) => {
                        if (resources && resources?.length) {
                            if (this.existingTexts) {
                                const labelTranslation = findMatchingTranslation(this.existingTexts, resources);
                                if (labelTranslation && labelTranslation.translation) {
                                    this.el.nativeElement.innerText = labelTranslation.translation;
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}