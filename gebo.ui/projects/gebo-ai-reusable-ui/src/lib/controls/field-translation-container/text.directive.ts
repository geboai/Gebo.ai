import { AfterContentInit, Directive, ElementRef, Inject, OnInit, Optional } from "@angular/core";
import { GeboAITranslationService } from "./gebo-translation.service";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { findMatchingTranslation, UIExistingText } from "./text-language-resources";

@Directive({
    selector: '[gebo-ai-text]',
    standalone: false
})
export class GeboAITextDirective implements AfterContentInit, OnInit {
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
                this.host = array.length ? array[0] : undefined;
            }
        }
        if (Array.isArray(this.moduleId)) {
            const modules=Array.from(this.moduleId);
            this.moduleId = modules[0];
        }
    }
    async ngOnInit() {
        await this.translationService.tryInit();
    }
    ngAfterContentInit(): void {
        this.componentId = this.el.nativeElement.getAttribute("id");
        // textContent, not innerText: innerText is empty while the element is not
        // rendered or visibility:hidden (ng-diagram keeps its nodes hidden until it
        // has measured them), and the text would never be translated
        const text = (this.el.nativeElement.textContent || "").replace(/\s+/g, " ").trim();
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
                        if (resources) {
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