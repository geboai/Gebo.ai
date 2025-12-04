import { AfterContentInit, ChangeDetectorRef, Directive, ElementRef, Host, Inject, OnInit, Optional, Self } from "@angular/core";
import { findMatchingTranlations, findMatchingTranslation, UIExistingText } from "./text-language-resources";
import { GEBO_AI_FIELD_HOST, GEBO_AI_MODULE, GeboAIFieldHost } from "../field-host-component-iface/field-host-component-iface";
import { GeboAITranslationService } from "./gebo-translation.service";
import { GEBO_MULILANGUAGE_TARGET, LabelTarget, safeSet } from "./primeng-components-multilanguage-adapters.directive";
import { BaseComponent } from "primeng/basecomponent";

@Directive({
    selector: '[gebo-ai-label]',
    standalone: false
})
export class GeboAILabelDirective implements AfterContentInit, OnInit {
    constructor(
        private el: ElementRef<HTMLElement>,
        private translationService: GeboAITranslationService,
        private changeDetectionRef: ChangeDetectorRef,
        @Optional() @Host() private baseComponent: BaseComponent,
        @Optional() @Inject(GEBO_MULILANGUAGE_TARGET) private target?: LabelTarget,
        @Optional() @Inject(GEBO_AI_MODULE) private moduleId?: string,
        @Optional() @Inject(GEBO_AI_FIELD_HOST) private host?: GeboAIFieldHost) {
        if (this.host) {
            if (Array.isArray(this.host)) {
                const array = Array.from(this.host);
                this.host = array.length ? array[0] : undefined;
            }
        }
        if (Array.isArray(this.moduleId)) {
            const modules = Array.from(this.moduleId);
            this.moduleId = modules[0];
        }
        
    }

    private componentId: string | undefined | null;
    private existingTexts: UIExistingText[] = [];
    async ngOnInit() {
        await this.translationService.tryInit();
    }
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
                        const matchings = findMatchingTranlations(this.existingTexts, resources);
                        if (matchings && matchings.length) {
                            matchings.forEach(matchText => {
                                if (matchText && matchText.translation) {
                                    this.el.nativeElement.setAttribute(matchText.key, matchText.translation);
                                    if (this.target) {
                                        this.target.set(matchText.key, matchText.translation);
                                    }
                                }

                            });
                            this.changeDetectionRef.detectChanges();

                        }
                        /*this.existingTexts.forEach(textRc => {
                            const matchText = findMatchingTranslation(textRc, resources);
                            if (matchText && matchText.translation) {
                                this.el.nativeElement.setAttribute(textRc.key, matchText.translation);
                            }
                        });*/

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